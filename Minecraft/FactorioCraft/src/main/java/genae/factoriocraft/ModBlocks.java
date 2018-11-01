package genae.factoriocraft;

import genae.factoriocraft.blocks.BlockGeneratorT1;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("factoriocraft:generatort1")
    public static BlockGeneratorT1 generatorT1;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        generatorT1.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
    }
}
