package com.immersivetanks;

import com.immersivetanks.client.model.bt_5;
import com.immersivetanks.client.renderer.BT5Renderer;
import com.immersivetanks.entity.custom.BT5;
import com.immersivetanks.entity.custom.TankBase;
import com.immersivetanks.entity.projectiles.projectile45;
import com.immersivetanks.item.custom.Bt5Item;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmersiveTanks.MODID)
public class ImmersiveTanks
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "immersivetanks";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold entities
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<EntityType> SHELL45 = ENTITY_TYPES.register("shell45", () ->  EntityType.Builder.of(projectile45::new, MobCategory.MISC).sized(0.045f, 0.045f).build("shell45"));
    public static final RegistryObject<EntityType> BT5_ENTITY = ENTITY_TYPES.register("bt_5_entity", () -> EntityType.Builder.of(BT5::new, MobCategory.MISC).sized(2.5F, 2.0f).build("bt5"));
    public static final RegistryObject<Item> BT_5_TURRET = ITEMS.register("bt5_turret", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BT_5_HULL = ITEMS.register("bt5_hull", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENGINE = ITEMS.register("engine", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BT5_ITEM = ITEMS.register("bt_5", () -> new Bt5Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AMMO45MM = ITEMS.register("ammo45mm", () -> new Item(new Item.Properties()));
    public static final RegistryObject<CreativeModeTab> IMMERSIVE_TANKS = CREATIVE_MODE_TABS.register("immersive_tanks", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + MODID + ".immersive_tanks"))
            .icon(() -> new ItemStack(AMMO45MM.get()))
            // Add default items to tab
            .displayItems((params, output) -> {
                output.accept(ENGINE.get());
                output.accept(AMMO45MM.get());
                output.accept(BT_5_HULL.get());
                output.accept(BT_5_TURRET.get());
                output.accept(BT5_ITEM.get());
            })
            .build()
    );




    public ImmersiveTanks(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register Entities
        ENTITY_TYPES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        // event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
            if (event.getEntity().getVehicle() instanceof TankBase) {
                event.setCanceled(true);
            }
        }
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(bt_5.LAYER_LOCATION, bt_5::createBodyLayer);
        }
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(BT5_ENTITY.get(), BT5Renderer::new);
            event.registerEntityRenderer(SHELL45.get(), (context) ->
                    new ThrownItemRenderer<>(context, 1.0F, true));
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
