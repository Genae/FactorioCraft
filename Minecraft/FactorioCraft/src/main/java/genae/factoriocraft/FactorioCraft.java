package genae.factoriocraft;

import genae.factoriocraft.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = FactorioCraft.MODID, name = FactorioCraft.MODNAME, version = FactorioCraft.MODVERSION, dependencies = "required-after:forge@[14.23.5.2772,)", useMetadata = true)
public class FactorioCraft {
    public static final String MODID = "factoriocraft";
    public static final String MODNAME = "FactorioCraft";
    public static final String MODVERSION = "0.0.1";

    public static final CreativeTabs CUSTOM_TAB = (new CreativeTabs("tabFactorioCraft") {

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.minecraftSciencePack);
        }

    });

    @SidedProxy(clientSide = "genae.factoriocraft.proxy.ClientProxy", serverSide = "genae.factoriocraft.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static FactorioCraft instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
