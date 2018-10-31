package genae.factoriocraft;

import genae.factoriocraft.items.ItemMinecraftSciencePack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("factoriocraft:minecraftsciencepack")
    public static ItemMinecraftSciencePack minecraftSciencePack;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        minecraftSciencePack.initModel();
    }
}
