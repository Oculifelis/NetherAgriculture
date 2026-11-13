package ru.netherdon.netheragriculture.validation;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.netherdon.netheragriculture.neoforge.registries.NAEntityAttachments;
import ru.netherdon.netheragriculture.registries.NAMobEffects;

@Mod(StriderGameTests.ID)
public final class StriderGameTests {
    public static final String ID = "netheragriculture_checks";
    private static final DeferredRegister<Consumer<GameTestHelper>> FUNCTIONS = DeferredRegister.create(Registries.TEST_FUNCTION, ID);
    private static final List<String> TESTS = List.of("registries_recipes_loot_and_assets", "internal_heat_and_saved_effects", "spores_collected_with_a_bottle");
    static {
        FUNCTIONS.register(TESTS.get(0), () -> StriderGameTests::coverage);
        FUNCTIONS.register(TESTS.get(1), () -> StriderGameTests::heat);
        FUNCTIONS.register(TESTS.get(2), () -> StriderGameTests::spores);
    }

    public StriderGameTests(IEventBus bus) {
        FUNCTIONS.register(bus);
        bus.addListener(StriderGameTests::registerTests);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ID, path);
    }

    private static void registerTests(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> environment = event.registerEnvironment(id("default"), new TestEnvironmentDefinition.AllOf(List.of()));
        for (String name : TESTS) {
            ResourceKey<Consumer<GameTestHelper>> function = ResourceKey.create(Registries.TEST_FUNCTION, id(name));
            event.registerTest(id(name), new FunctionGameTestInstance(function, new TestData<>(environment, id("arena"), 100, 0, true)));
        }
    }

    private static void coverage(GameTestHelper h) {
        var report = CoverageSelfTest.verify(h.getLevel().getServer());
        h.assertTrue(report.get("failed").getAsInt() == 0 && report.get("passed").getAsInt() > 0, report.toString());
        h.succeed();
    }

    private static void heat(GameTestHelper h) {
        var strider = h.spawn(EntityType.STRIDER, 1, 1, 1);
        strider.setNoAi(false);
        strider.tick();
        h.assertTrue(!strider.getData(NAEntityAttachments.INTERNAL_HEAT), "Default internal heat is false");
        h.assertTrue(strider.isSuffocating(), "The dry fixture starts cold");
        strider.addEffect(new MobEffectInstance(NAMobEffects.INTERNAL_HEAT, 200));
        strider.tick();
        h.assertTrue(strider.getData(NAEntityAttachments.INTERNAL_HEAT), "Internal Heat updates the attachment");
        h.assertTrue(!strider.isSuffocating(), "Internal Heat prevents the cold penalty");

        var output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, h.getLevel().registryAccess());
        strider.saveWithoutId(output);
        var restored = EntityType.STRIDER.create(h.getLevel(), EntitySpawnReason.COMMAND);
        h.assertTrue(restored != null, "A fresh Strider can be created");
        restored.load(TagValueInput.create(ProblemReporter.DISCARDING, h.getLevel().registryAccess(), output.buildResult()));
        h.assertTrue(restored.hasEffect(NAMobEffects.INTERNAL_HEAT), "Saved mob effects retain Internal Heat");
        h.assertTrue(!restored.getData(NAEntityAttachments.INTERNAL_HEAT), "The derived flag remains transient");
        restored.tick();
        h.assertTrue(restored.getData(NAEntityAttachments.INTERNAL_HEAT) && !restored.isSuffocating(), "The first restored tick rebuilds heat from the saved effect");
        restored.removeEffect(NAMobEffects.INTERNAL_HEAT);
        restored.tick();
        h.assertTrue(!restored.getData(NAEntityAttachments.INTERNAL_HEAT) && restored.isSuffocating(), "Removing Internal Heat restores the cold penalty");
        restored.discard();
        strider.discard();
        h.succeed();
    }

    private static void spores(GameTestHelper h) {
        var level = h.getLevel();
        var position = h.absolutePos(new net.minecraft.core.BlockPos(1, 1, 1));
        var player = net.neoforged.neoforge.common.util.FakePlayerFactory.getMinecraft(level);
        var previous = player.getMainHandItem().copy();
        boolean creative = player.getAbilities().instabuild;
        try {
            player.getAbilities().instabuild = false;
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.GLASS_BOTTLE));
            level.setBlock(position, ru.netherdon.netheragriculture.registries.NABlocks.GLOWING_SPORES.value().defaultBlockState(), 3);
            var state = level.getBlockState(position);
            var hit = new net.minecraft.world.phys.BlockHitResult(net.minecraft.world.phys.Vec3.atCenterOf(position), net.minecraft.core.Direction.UP, position, false);
            state.useItemOn(player.getMainHandItem(), level, player, net.minecraft.world.InteractionHand.MAIN_HAND, hit);
            h.assertTrue(player.getMainHandItem().is(ru.netherdon.netheragriculture.registries.NAItems.GLOWING_SPORES_IN_A_BOTTLE.value()), "A glass bottle collects the spores");
            h.assertTrue(player.getMainHandItem().getCount() == 1, "One bottle returns one filled bottle");
            h.assertTrue(level.isEmptyBlock(position), "Collecting removes the spore cloud");
            h.succeed();
        } finally {
            player.getAbilities().instabuild = creative;
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, previous);
        }
    }
}
