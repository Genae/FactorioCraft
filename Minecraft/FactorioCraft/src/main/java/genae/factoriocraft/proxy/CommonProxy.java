package genae.factoriocraft.proxy;

import genae.factoriocraft.Config;
import genae.factoriocraft.FactorioCraft;
import genae.factoriocraft.ModBlocks;
import genae.factoriocraft.blocks.consumer.BlockConsumerT1;
import genae.factoriocraft.blocks.consumer.TileEntityConsumerT1;
import genae.factoriocraft.blocks.generator.BlockGeneratorT1;
import genae.factoriocraft.blocks.generator.TileEntityGeneratorT1;
import genae.factoriocraft.items.ItemMinecraftSciencePack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {

    // Config instance
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "modtut.cfg"));
        Config.readConfig();

        // Initialize our packet handler. Make sure the name is
        // 20 characters or less!
        /*PacketHandler.registerMessages("modtut");

        // Initialization of blocks and items typically goes here:
        ModEntities.init();
        ModDimensions.init();

        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTOP();*/

    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(FactorioCraft.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockGeneratorT1());
        GameRegistry.registerTileEntity(TileEntityGeneratorT1.class, new ResourceLocation(FactorioCraft.MODID + "_generatort1"));
        event.getRegistry().register(new BlockConsumerT1());
        GameRegistry.registerTileEntity(TileEntityConsumerT1.class, new ResourceLocation(FactorioCraft.MODID + "_consumert1"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemMinecraftSciencePack());
        event.getRegistry().register(new ItemBlock(ModBlocks.generatorT1).setRegistryName(ModBlocks.generatorT1.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.consumerT1).setRegistryName(ModBlocks.consumerT1.getRegistryName()));
    }

}
