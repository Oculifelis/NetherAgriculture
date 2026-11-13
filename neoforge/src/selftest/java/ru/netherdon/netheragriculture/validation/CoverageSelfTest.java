package ru.netherdon.netheragriculture.validation;

import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

/** Development-only registry, recipe, placement, loot and asset checks on a dedicated server. */
@EventBusSubscriber(modid = "netheragriculture")
public final class CoverageSelfTest {
    private static final String MOD = "netheragriculture";
    private static final List<String> errors = new ArrayList<>();
    private static final Map<String, Integer> counts = new LinkedHashMap<>();
    private static final List<Path> roots = Arrays.stream(System.getProperty("coverage.resources").split("\\|"))
            .map(Path::of).toList();
    private static int passed;

    private interface Check { void run() throws Exception; }
    private static void test(String name, Check body) {
        try {
            body.run();
            passed++;
            System.out.println("SELFTEST PASS " + name);
        } catch (Throwable failure) {
            errors.add(name + ": " + failure);
            System.out.println("SELFTEST FAIL " + name + ": " + failure);
        }
    }
    private static void require(boolean value, String reason) {
        if (!value) throw new AssertionError(reason);
    }
    private static JsonObject json(String path) throws IOException {
        for (Path root : roots) {
            Path file = root.resolve(path);
            if (Files.isRegularFile(file)) {
                try (Reader reader = Files.newBufferedReader(file)) {
                    return JsonParser.parseReader(reader).getAsJsonObject();
                }
            }
        }
        try (InputStream stream = CoverageSelfTest.class.getResourceAsStream("/" + path)) {
            if (stream == null) throw new FileNotFoundException(path);
            return JsonParser.parseReader(new InputStreamReader(stream, java.nio.charset.StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }
    private static String assetPath(String kind, String id) {
        String[] parts = id.contains(":") ? id.split(":", 2) : new String[] {"minecraft", id};
        return "assets/" + parts[0] + "/" + kind + "/" + parts[1] + ".json";
    }
    private static void model(String id, Set<String> chain) throws IOException {
        if (!id.startsWith(MOD + ":")) return;
        require(chain.add(id), "model parent cycle: " + chain + " -> " + id);
        JsonObject data = json(assetPath("models", id));
        if (data.has("parent")) model(data.get("parent").getAsString(), chain);
        if (data.has("overrides")) {
            for (JsonElement override : data.getAsJsonArray("overrides"))
                model(override.getAsJsonObject().get("model").getAsString(), new HashSet<>(chain));
        }
        if (data.has("textures")) {
            for (JsonElement texture : data.getAsJsonObject("textures").asMap().values()) {
                String ref = texture.getAsString();
                if (!ref.startsWith(MOD + ":")) continue;
                String path = assetPath("textures", ref).replaceFirst("\\.json$", ".png");
                require(roots.stream().anyMatch(root -> Files.isRegularFile(root.resolve(path))), "missing texture " + ref);
            }
        }
        chain.remove(id);
    }
    private static void itemDefinition(JsonElement node) throws IOException {
        if (node.isJsonArray()) {
            for (JsonElement child : node.getAsJsonArray()) itemDefinition(child);
        } else if (node.isJsonObject()) {
            for (var entry : node.getAsJsonObject().entrySet()) {
                if (entry.getKey().equals("model") && entry.getValue().isJsonPrimitive())
                    model(entry.getValue().getAsString(), new HashSet<>());
                else itemDefinition(entry.getValue());
            }
        }
    }
    private static void collectLootItems(JsonElement node, Set<String> names) {
        if (node.isJsonArray()) {
            for (JsonElement child : node.getAsJsonArray()) collectLootItems(child, names);
        } else if (node.isJsonObject()) {
            JsonObject obj = node.getAsJsonObject();
            if (obj.has("type") && obj.get("type").isJsonPrimitive()
                    && obj.get("type").getAsString().equals("minecraft:item")) names.add(obj.get("name").getAsString());
            for (JsonElement child : obj.asMap().values()) collectLootItems(child, names);
        }
    }

    @SubscribeEvent
    public static void started(ServerStartedEvent event) {
        if (!Boolean.getBoolean("coverage.enabled")) return;
        try { verify(event.getServer()); }
        finally { event.getServer().halt(false); }
    }

    public static JsonObject verify(MinecraftServer server) {
        errors.clear();
        counts.clear();
        passed = 0;
        JsonObject result = new JsonObject();
        try {
            ServerLevel level = server.overworld();
            var items = BuiltInRegistries.ITEM.stream().filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(MOD)).toList();
            var blocks = BuiltInRegistries.BLOCK.stream().filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(MOD)).toList();
            counts.put("items", items.size());
            counts.put("blocks", blocks.size());
            test("registries", () -> require(net.neoforged.fml.ModList.get().isLoaded(MOD), "mod did not load"));

            var resources = server.getResourceManager().listResources("recipe", id -> id.getNamespace().equals(MOD) && id.getPath().endsWith(".json"));
            counts.put("recipes", resources.size());
            counts.put("conditionalRecipes", 0);
            var ops = server.registryAccess().createSerializationContext(JsonOps.INSTANCE);
            for (var entry : resources.entrySet()) test("recipe " + entry.getKey(), () -> {
                JsonObject data;
                try (Reader reader = entry.getValue().openAsReader()) { data = JsonParser.parseReader(reader).getAsJsonObject(); }
                var decoded = ICondition.getConditionally(Recipe.CODEC, ops, data);
                String path = entry.getKey().getPath();
                var id = Identifier.fromNamespaceAndPath(MOD, path.substring("recipe/".length(), path.length() - 5));
                boolean loaded = server.getRecipeManager().byKey(ResourceKey.create(Registries.RECIPE, id)).isPresent();
                require(loaded == decoded.isPresent(), "recipe load disagrees with codec/conditions");
                if (decoded.isEmpty()) counts.compute("conditionalRecipes", (key, value) -> value + 1);
            });

            BlockPos pos = new BlockPos(0, 200, 0);
            List<ItemStack> tools = new ArrayList<>(List.of(ItemStack.EMPTY, new ItemStack(Items.SHEARS)));
            for (Item item : List.of(Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_HOE)) {
                ItemStack tool = new ItemStack(item);
                tool.enchant(server.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), 1);
                tools.add(tool);
            }
            for (Block block : blocks) test("block " + BuiltInRegistries.BLOCK.getKey(block), () -> {
                var original = level.getBlockState(pos);
                try {
                    level.setBlock(pos, block.defaultBlockState(), 2);
                    require(level.getBlockState(pos).is(block), "block did not place");
                    var key = block.getLootTable().orElse(null);
                    if (key == null || key.identifier().toString().equals("minecraft:empty")) {
                        require(block.asItem() == Items.AIR, "item-bearing block has no loot table");
                        counts.merge("intentionalNoLoot", 1, Integer::sum);
                        return;
                    }
                    var table = server.reloadableRegistries().getLootTable(key);
                    require(table != LootTable.EMPTY, "loot table missing or failed to parse: " + key);
                    Set<String> allowed = new HashSet<>();
                    String tableId = key.identifier().toString();
                    String[] parts = tableId.split(":", 2);
                    JsonObject data = json("data/" + parts[0] + "/loot_table/" + parts[1] + ".json");
                    collectLootItems(data, allowed);
                    if (allowed.isEmpty()) {
                        require(!data.has("pools") || data.getAsJsonArray("pools").isEmpty(), "loot has no testable item entries");
                        require(block.asItem() == Items.AIR, "item-bearing block has an empty loot table");
                        counts.merge("intentionalNoLoot", 1, Integer::sum);
                        return;
                    }
                    for (var state : block.getStateDefinition().getPossibleStates()) {
                        level.setBlock(pos, state, 2);
                        for (ItemStack tool : tools) {
                            for (int attempt = 0; attempt < 16; attempt++) {
                                var drops = Block.getDrops(state, level, pos, level.getBlockEntity(pos), null, tool);
                                if (drops.isEmpty()) continue;
                                for (ItemStack drop : drops)
                                    require(!drop.isEmpty() && allowed.contains(BuiltInRegistries.ITEM.getKey(drop.getItem()).toString()), "unexpected loot " + drop);
                                return;
                            }
                        }
                    }
                    throw new AssertionError("no drops for any state/tool; expected one of " + allowed);
                } finally { level.setBlock(pos, original, 2); }
            });

            JsonObject lang = json("assets/" + MOD + "/lang/en_us.json");
            for (Item item : items) test("item " + BuiltInRegistries.ITEM.getKey(item), () -> {
                String key = item.getDescriptionId();
                require(lang.has(key) && !lang.get(key).getAsString().isBlank() && !lang.get(key).getAsString().equals(key), "missing English name: " + key);
                String id = BuiltInRegistries.ITEM.getKey(item).toString();
                itemDefinition(json(assetPath("items", id)));
            });
        } catch (Throwable failure) {
            errors.add("suite: " + failure);
            failure.printStackTrace();
        } finally {
            result.addProperty("passed", passed);
            result.addProperty("failed", errors.size());
            result.add("counts", new Gson().toJsonTree(counts));
            result.add("errors", new Gson().toJsonTree(errors));
            try { Files.writeString(Path.of(System.getProperty("coverage.result")), new GsonBuilder().setPrettyPrinting().create().toJson(result)); }
            catch (IOException failure) { failure.printStackTrace(); }
            System.out.println("SELFTEST DONE " + passed + "/" + (passed + errors.size()) + " failed=" + errors.size() + " " + counts);
        }
        return result;
    }
}
