package genae.factoriocraft;

import genae.factoriocraft.blocks.consumer.BlockConsumerT1;
import genae.factoriocraft.blocks.generator.BlockGeneratorT1;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("factoriocraft:generatort1")
    public static BlockGeneratorT1 generatorT1;
    @GameRegistry.ObjectHolder("factoriocraft:consumert1")
    public static BlockConsumerT1 consumerT1;


    @SideOnly(Side.CLIENT)
    public static void initModels() {

        generatorT1.initModel();
        consumerT1.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
    }
}
