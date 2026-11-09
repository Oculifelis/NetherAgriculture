package ru.netherdon.netheragriculture.registries;

import ru.netherdon.netheragriculture.blocks.NetherRootsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import ru.netherdon.netheragriculture.blocks.*;
import ru.netherdon.netheragriculture.services.RegistryManager;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public final class NABlocks
{
    public static final IRegistryProvider<Block> REGISTER = RegistryManager.getOrCreate(BuiltInRegistries.BLOCK);

    public static final Holder<NetherFarmlandBlock> CRIMSON_FARMLAND = registerBlock("crimson_farmland", NetherFarmlandBlock::new,
        BlockBehaviour.Properties.of()
            .strength(0.4F)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.CRIMSON_NYLIUM)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.NYLIUM)
    );

    public static final Holder<NetherFarmlandBlock> WARPED_FARMLAND = registerBlock("warped_farmland", NetherFarmlandBlock::new,
        BlockBehaviour.Properties.of()
            .strength(0.4F)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.WARPED_NYLIUM)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.NYLIUM)
    );

    public static final Holder<BlackFurnaceBlock> BLACK_FURNACE = registerBlock("black_furnace", BlackFurnaceBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.5F)
            .lightLevel(lit(13))
    );

    public static final Holder<MortofructBlock> MORTOFRUCT = registerBlock("mortofruct", MortofructBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .strength(1.5f)
            .sound(SoundType.WOOD)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<DeadVinesBlock> DEAD_VINES = registerBlock("dead_vines", DeadVinesBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.TERRACOTTA_RED)
            .instabreak()
            .noCollision()
            .randomTicks()
            .sound(SoundType.TWISTING_VINES)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<NetherCropBlock> CRIMSON_BERRY_ROOTS = registerBlock("crimson_berry_roots", CrimsonBerryRootsBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.NETHER)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<NetherBerrySproutsBlock> CRIMSON_BERRY_SPROUTS = registerBlock("crimson_berry_sprouts", NetherBerrySproutsBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.NETHER)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<NetherCropBlock> WARPED_BERRY_ROOTS = registerBlock("warped_berry_roots", WarpedBerryRootsBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_CYAN)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<NetherBerrySproutsBlock> WARPED_BERRY_SPROUTS = registerBlock("warped_berry_sprouts", NetherBerrySproutsBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_CYAN)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<NetherCropBlock> LOTHUNS = registerBlock("lothuns", LothunBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_CYAN)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<WildLothunBlock> WILD_LOTHUN = registerBlock("wild_lothun", WildLothunBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_CYAN)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<NetherCropBlock> BLAZE_FRUIT = registerBlock("blaze_fruit", BlazeFruitBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_CYAN)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .pushReaction(PushReaction.DESTROY)
            .lightLevel((state) -> ((NetherCropBlock)state.getBlock()).isMaxAge(state) ? 10 : 0)
    );

    public static final Holder<SinfulEyesBlock> SINFUL_EYES = registerBlock("sinful_eyes", SinfulEyesBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.GOLD)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<WildSinfulEyesBlock> WILD_SINFUL_EYES = registerBlock("wild_sinful_eyes", WildSinfulEyesBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.GOLD)
            .noCollision()
            .instabreak()
            .sound(SoundType.ROOTS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<NetherRootsBlock> NETHER_ROOTS = registerBlock("nether_roots", NetherRootsBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.NETHER)
            .instabreak()
            .noCollision()
            .replaceable()
            .sound(SoundType.ROOTS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<TallRootsBlock> TALL_CRIMSON_ROOTS = registerBlock("tall_crimson_roots", TallRootsBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.NETHER)
            .instabreak()
            .noCollision()
            .replaceable()
            .sound(SoundType.ROOTS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<TallRootsBlock> TALL_WARPED_ROOTS = registerBlock("tall_warped_roots", TallRootsBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.NETHER)
            .instabreak()
            .noCollision()
            .replaceable()
            .sound(SoundType.ROOTS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<NetherFungusBlock> GLOWING_FUNGUS = REGISTER.register("glowing_fungus", (id) -> new GlowingFungusBlock(
        NAConfiguredFeatures.GLOWING_FUNGUS,
        id(id, BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .instabreak()
            .noCollision()
            .sound(SoundType.FUNGUS)
            .lightLevel(maxLight())
            .pushReaction(PushReaction.DESTROY))
    ) {});

    public static final Holder<Block> GLOWING_WART_BLOCK = registerSimpleBlock("glowing_wart_block",
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .strength(1f)
            .lightLevel(maxLight())
            .sound(SoundType.WART_BLOCK)
    );

    public static final Holder<Block> GLOWING_STEM = registerBlock("glowing_stem", RotatedPillarBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2f)
            .lightLevel(maxLight())
            .sound(SoundType.STEM)
    );

    public static final Holder<Block> GLOWING_HYPHAE = registerBlock("glowing_hyphae", RotatedPillarBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2f)
            .lightLevel(maxLight())
            .sound(SoundType.STEM)
    );

    public static final Holder<Block> STRIPPED_GLOWING_STEM = registerBlock("stripped_glowing_stem", RotatedPillarBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2f)
            .lightLevel(maxLight())
            .sound(SoundType.STEM)
    );

    public static final Holder<Block> STRIPPED_GLOWING_HYPHAE = registerBlock("stripped_glowing_hyphae", RotatedPillarBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2f)
            .lightLevel(maxLight())
            .sound(SoundType.STEM)
    );

    public static final Holder<Block> GLOWING_PLANKS = registerSimpleBlock("glowing_planks",
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2f, 3f)
            .lightLevel(maxLight())
            .sound(SoundType.NETHER_WOOD)
    );

    public static final Holder<StairBlock> GLOWING_STAIRS = REGISTER.register("glowing_stairs", (id) -> new StairBlock(
        GLOWING_PLANKS.value().defaultBlockState(),
        id(id, BlockBehaviour.Properties.ofFullCopy(GLOWING_PLANKS.value()))
    ) {});

    public static final Holder<SlabBlock> GLOWING_SLAB = registerBlock("glowing_slab", SlabBlock::new,
        () -> BlockBehaviour.Properties.ofFullCopy(GLOWING_PLANKS.value())
    );

    public static final Holder<FenceBlock> GLOWING_FENCE = registerBlock("glowing_fence", FenceBlock::new,
        () -> BlockBehaviour.Properties.ofFullCopy(GLOWING_PLANKS.value())
    );

    public static final Holder<FenceGateBlock> GLOWING_FENCE_GATE = REGISTER.register("glowing_fence_gate", (id) -> new FenceGateBlock(
        NAWoodTypes.GLOWING,
        id(id, BlockBehaviour.Properties.ofFullCopy(GLOWING_PLANKS.value()))
    ));

    public static final Holder<DoorBlock> GLOWING_DOOR = REGISTER.register("glowing_door", (id) -> new DoorBlock(
        NAWoodTypes.GLOWING_BLOCK_SET,
        id(id, BlockBehaviour.Properties.of()
            .mapColor(GLOWING_PLANKS.value().defaultMapColor())
            .instrument(NoteBlockInstrument.BASS)
            .strength(3f)
            .lightLevel(maxLight())
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY))
    ) {});

    public static final Holder<TrapDoorBlock> GLOWING_TRAPDOOR = REGISTER.register("glowing_trapdoor", (id) -> new TrapDoorBlock(
        NAWoodTypes.GLOWING_BLOCK_SET,
        id(id, BlockBehaviour.Properties.of()
            .mapColor(GLOWING_PLANKS.value().defaultMapColor())
            .instrument(NoteBlockInstrument.BASS)
            .strength(3f)
            .lightLevel(maxLight())
            .noOcclusion()
            .isValidSpawn(NABlocks::never))
    ) {});

    public static final Holder<PressurePlateBlock> GLOWING_PRESSURE_PLATE = REGISTER.register("glowing_pressure_plate", (id) -> new PressurePlateBlock(
        NAWoodTypes.GLOWING_BLOCK_SET,
        id(id, BlockBehaviour.Properties.of()
            .mapColor(GLOWING_PLANKS.value().defaultMapColor())
            .forceSolidOn()
            .instrument(NoteBlockInstrument.BASS)
            .noCollision()
            .strength(0.5f)
            .lightLevel(maxLight())
            .pushReaction(PushReaction.DESTROY))
    ) {});

    public static final Holder<ButtonBlock> GLOWING_BUTTON = REGISTER.register("glowing_button", (id) -> new ButtonBlock(
        NAWoodTypes.GLOWING_BLOCK_SET,
        30,
        id(id, BlockBehaviour.Properties.of()
            .noCollision()
            .strength(0.5f)
            .lightLevel(maxLight())
            .pushReaction(PushReaction.DESTROY))
    ) {});

    public static final Holder<CustomStandingSignBlock> GLOWING_SIGN = REGISTER.register("glowing_sign", (id) -> new CustomStandingSignBlock(
        NAWoodTypes.GLOWING,
        id(id, BlockBehaviour.Properties.of()
            .mapColor(GLOWING_PLANKS.value().defaultMapColor())
            .instrument(NoteBlockInstrument.BASS)
            .forceSolidOn()
            .noCollision()
            .strength(1f)
            .lightLevel(maxLight()))
    ));

    public static final Holder<CustomWallSignBlock> GLOWING_WALL_SIGN = REGISTER.register("glowing_wall_sign", (id) -> new CustomWallSignBlock(
        NAWoodTypes.GLOWING,
        id(id, BlockBehaviour.Properties.ofFullCopy(GLOWING_SIGN.value())
            .overrideLootTable(GLOWING_SIGN.value().getLootTable()))
    ));

    public static final Holder<CustomCeilingHangingSignBlock> GLOWING_HANGING_SIGN = REGISTER.register("glowing_hanging_sign", (id) -> new CustomCeilingHangingSignBlock(
        NAWoodTypes.GLOWING,
        id(id, BlockBehaviour.Properties.of()
            .mapColor(GLOWING_PLANKS.value().defaultMapColor())
            .forceSolidOn()
            .instrument(NoteBlockInstrument.BASS)
            .noCollision()
            .strength(1f)
            .lightLevel(maxLight()))
    ));

    public static final Holder<CustomWallHangingSignBlock> GLOWING_WALL_HANGING_SIGN = REGISTER.register("glowing_wall_hanging_sign", (id) -> new CustomWallHangingSignBlock(
        NAWoodTypes.GLOWING,
        id(id, BlockBehaviour.Properties.ofFullCopy(GLOWING_HANGING_SIGN.value())
            .overrideLootTable(GLOWING_HANGING_SIGN.value().getLootTable()))
    ));

    public static final Holder<GlowingSporesBlock> GLOWING_SPORES = registerBlock("glowing_spores",
        (properties) -> new GlowingSporesBlock(NAItemKeys.GLASS_BOTTLE, properties),
        BlockBehaviour.Properties.of()
            .instabreak()
            .noCollision()
            .lightLevel(maxLight())
            .randomTicks()
            .replaceable()
            .pushReaction(PushReaction.DESTROY)
    );

    public static final Holder<AzureMelonBlock> AZURE_MELON = registerBlock("azure_melon", AzureMelonBlock::new, NABlockProperties.AZURE_MELON);
    public static final Holder<AzureMelonCropBlock> AZURE_MELON_CROP = registerBlock("azure_melon_crop", AzureMelonCropBlock::new, NABlockProperties.AZURE_MELON);
    public static final Holder<WildAzureMelonBlock> WILD_AZURE_MELON = registerBlock("wild_azure_melon", WildAzureMelonBlock::new, NABlockProperties.WILD_AZURE_MELON);
    public static final Holder<AzureMelonStemBlock> AZURE_MELON_STEM = registerBlock("azure_melon_stem", AzureMelonStemBlock::new, NABlockProperties.AZURE_MELON_STEM);
    public static final Holder<AttachedAzureMelonStemBlock> ATTACHED_AZURE_MELON_STEM = registerBlock("attached_azure_melon_stem", AttachedAzureMelonStemBlock::new, NABlockProperties.AZURE_MELON_STEM);

    public static final Holder<Block> CRIMSON_CRATE = registerBlock("crimson_crate", CrateBlock::new, NABlockProperties.CRIMSON_CRATE);
    public static final Holder<SlabBlock> SMALL_CRIMSON_CRATE = registerBlock("small_crimson_crate", SmallCrateBlock::new, NABlockProperties.CRIMSON_CRATE);
    public static final Holder<Block> WARPED_CRATE = registerBlock("warped_crate", CrateBlock::new, NABlockProperties.WARPED_CRATE);
    public static final Holder<SlabBlock> SMALL_WARPED_CRATE = registerBlock("small_warped_crate", SmallCrateBlock::new, NABlockProperties.WARPED_CRATE);
    public static final Holder<Block> GLOWING_CRATE = registerBlock("glowing_crate", CrateBlock::new, NABlockProperties.GLOWING_CRATE);
    public static final Holder<SlabBlock> SMALL_GLOWING_CRATE = registerBlock("small_glowing_crate", SmallCrateBlock::new, NABlockProperties.GLOWING_CRATE);

    public static final Holder<Block> CRIMSON_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlock("crimson_crate_of_crimson_berries", NABlockProperties.CRIMSON_CRATE);
    public static final Holder<SlabBlock> SMALL_CRIMSON_CRATE_OF_CRIMSON_BERRIES = registerBlock("small_crimson_crate_of_crimson_berries", SlabBlock::new, NABlockProperties.CRIMSON_CRATE);
    public static final Holder<Block> WARPED_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlock("warped_crate_of_crimson_berries", NABlockProperties.WARPED_CRATE);
    public static final Holder<SlabBlock> SMALL_WARPED_CRATE_OF_CRIMSON_BERRIES = registerBlock("small_warped_crate_of_crimson_berries", SlabBlock::new, NABlockProperties.WARPED_CRATE);
    public static final Holder<Block> GLOWING_CRATE_OF_CRIMSON_BERRIES = registerSimpleBlock("glowing_crate_of_crimson_berries", NABlockProperties.GLOWING_CRATE);
    public static final Holder<SlabBlock> SMALL_GLOWING_CRATE_OF_CRIMSON_BERRIES = registerBlock("small_glowing_crate_of_crimson_berries", SlabBlock::new, NABlockProperties.GLOWING_CRATE);

    public static final Holder<Block> CRIMSON_CRATE_OF_WARPED_BERRIES = registerSimpleBlock("crimson_crate_of_warped_berries", NABlockProperties.CRIMSON_CRATE);
    public static final Holder<SlabBlock> SMALL_CRIMSON_CRATE_OF_WARPED_BERRIES = registerBlock("small_crimson_crate_of_warped_berries", SlabBlock::new, NABlockProperties.CRIMSON_CRATE);
    public static final Holder<Block> WARPED_CRATE_OF_WARPED_BERRIES = registerSimpleBlock("warped_crate_of_warped_berries", NABlockProperties.WARPED_CRATE);
    public static final Holder<SlabBlock> SMALL_WARPED_CRATE_OF_WARPED_BERRIES = registerBlock("small_warped_crate_of_warped_berries", SlabBlock::new, NABlockProperties.WARPED_CRATE);
    public static final Holder<Block> GLOWING_CRATE_OF_WARPED_BERRIES = registerSimpleBlock("glowing_crate_of_warped_berries", NABlockProperties.GLOWING_CRATE);
    public static final Holder<SlabBlock> SMALL_GLOWING_CRATE_OF_WARPED_BERRIES = registerBlock("small_glowing_crate_of_warped_berries", SlabBlock::new, NABlockProperties.GLOWING_CRATE);

    public static final Holder<Block> CRIMSON_CRATE_OF_LOTHUN = registerSimpleBlock("crimson_crate_of_lothun", NABlockProperties.CRIMSON_CRATE);
    public static final Holder<SlabBlock> SMALL_CRIMSON_CRATE_OF_LOTHUN = registerBlock("small_crimson_crate_of_lothun", SlabBlock::new, NABlockProperties.CRIMSON_CRATE);
    public static final Holder<Block> WARPED_CRATE_OF_LOTHUN = registerSimpleBlock("warped_crate_of_lothun", NABlockProperties.WARPED_CRATE);
    public static final Holder<SlabBlock> SMALL_WARPED_CRATE_OF_LOTHUN = registerBlock("small_warped_crate_of_lothun", SlabBlock::new, NABlockProperties.WARPED_CRATE);
    public static final Holder<Block> GLOWING_CRATE_OF_LOTHUN = registerSimpleBlock("glowing_crate_of_lothun", NABlockProperties.GLOWING_CRATE);
    public static final Holder<SlabBlock> SMALL_GLOWING_CRATE_OF_LOTHUN = registerBlock("small_glowing_crate_of_lothun", SlabBlock::new, NABlockProperties.GLOWING_CRATE);

    public static final Holder<Block> CRIMSON_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerBlock("crimson_crate_of_blazing_golden_lothun", BurningCrateBlock::new, NABlockProperties.GLOWING_CRIMSON_CRATE);
    public static final Holder<SlabBlock> SMALL_CRIMSON_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerBlock("small_crimson_crate_of_blazing_golden_lothun", SmallBurningCrateBlock::new, NABlockProperties.GLOWING_CRIMSON_CRATE);
    public static final Holder<Block> WARPED_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerBlock("warped_crate_of_blazing_golden_lothun", BurningCrateBlock::new, NABlockProperties.GLOWING_WARPED_CRATE);
    public static final Holder<SlabBlock> SMALL_WARPED_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerBlock("small_warped_crate_of_blazing_golden_lothun", SmallBurningCrateBlock::new, NABlockProperties.GLOWING_WARPED_CRATE);
    public static final Holder<Block> GLOWING_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerBlock("glowing_crate_of_blazing_golden_lothun", BurningCrateBlock::new, NABlockProperties.GLOWING_CRATE);
    public static final Holder<SlabBlock> SMALL_GLOWING_CRATE_OF_BLAZING_GOLDEN_LOTHUN = registerBlock("small_glowing_crate_of_blazing_golden_lothun", SmallBurningCrateBlock::new, NABlockProperties.GLOWING_CRATE);

    public static final Holder<Block> CRIMSON_CRATE_OF_BLAZE_FRUIT = registerBlock("crimson_crate_of_blaze_fruit", BurningCrateBlock::new, NABlockProperties.GLOWING_CRIMSON_CRATE);
    public static final Holder<SlabBlock> SMALL_CRIMSON_CRATE_OF_BLAZE_FRUIT = registerBlock("small_crimson_crate_of_blaze_fruit", SmallBurningCrateBlock::new, NABlockProperties.GLOWING_CRIMSON_CRATE);
    public static final Holder<Block> WARPED_CRATE_OF_BLAZE_FRUIT = registerBlock("warped_crate_of_blaze_fruit", BurningCrateBlock::new, NABlockProperties.GLOWING_WARPED_CRATE);
    public static final Holder<SlabBlock> SMALL_WARPED_CRATE_OF_BLAZE_FRUIT = registerBlock("small_warped_crate_of_blaze_fruit", SmallBurningCrateBlock::new, NABlockProperties.GLOWING_WARPED_CRATE);
    public static final Holder<Block> GLOWING_CRATE_OF_BLAZE_FRUIT = registerBlock("glowing_crate_of_blaze_fruit", BurningCrateBlock::new, NABlockProperties.GLOWING_CRATE);
    public static final Holder<SlabBlock> SMALL_GLOWING_CRATE_OF_BLAZE_FRUIT = registerBlock("small_glowing_crate_of_blaze_fruit", SmallBurningCrateBlock::new, NABlockProperties.GLOWING_CRATE);

    public static final Holder<Block> CRIMSON_CRATE_OF_SINFUL_EYES = registerSimpleBlock("crimson_crate_of_sinful_eyes", NABlockProperties.CRIMSON_CRATE);
    public static final Holder<SlabBlock> SMALL_CRIMSON_CRATE_OF_SINFUL_EYES = registerBlock("small_crimson_crate_of_sinful_eyes", SlabBlock::new, NABlockProperties.CRIMSON_CRATE);
    public static final Holder<Block> WARPED_CRATE_OF_SINFUL_EYES = registerSimpleBlock("warped_crate_of_sinful_eyes", NABlockProperties.WARPED_CRATE);
    public static final Holder<SlabBlock> SMALL_WARPED_CRATE_OF_SINFUL_EYES = registerBlock("small_warped_crate_of_sinful_eyes", SlabBlock::new, NABlockProperties.WARPED_CRATE);
    public static final Holder<Block> GLOWING_CRATE_OF_SINFUL_EYES = registerSimpleBlock("glowing_crate_of_sinful_eyes", NABlockProperties.GLOWING_CRATE);
    public static final Holder<SlabBlock> SMALL_GLOWING_CRATE_OF_SINFUL_EYES = registerBlock("small_glowing_crate_of_sinful_eyes", SlabBlock::new, NABlockProperties.GLOWING_CRATE);


    private static ToIntFunction<BlockState> maxLight()
    {
        return light(15);
    }

    private static ToIntFunction<BlockState> light(int level)
    {
        return (state) -> level;
    }

    private static ToIntFunction<BlockState> lit(int lightLevel)
    {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightLevel : 0;
    }

    private static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType)
    {
        return false;
    }

    private static Holder<Block> registerSimpleBlock(String name, BlockBehaviour.Properties properties)
    {
        return registerBlock(name, Block::new, properties);
    }

    private static Holder<Block> registerSimpleBlock(String name, Supplier<BlockBehaviour.Properties> properties)
    {
        return registerBlock(name, Block::new, properties);
    }

    private static BlockBehaviour.Properties id(Identifier id, BlockBehaviour.Properties properties)
    {
        return properties.setId(ResourceKey.create(Registries.BLOCK, id));
    }

    private static <T extends Block> Holder<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> constructor, BlockBehaviour.Properties properties)
    {
        return registerBlock(name, constructor, () -> properties);
    }

    private static <T extends Block> Holder<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> constructor, Supplier<BlockBehaviour.Properties> properties)
    {
        return REGISTER.register(name, (id) -> constructor.apply(id(id, properties.get())));
    }

    public static void initialize() {}
}
