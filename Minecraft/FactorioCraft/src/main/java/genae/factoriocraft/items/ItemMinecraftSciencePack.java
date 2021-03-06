package genae.factoriocraft.items;

import genae.factoriocraft.FactorioCraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMinecraftSciencePack extends Item {
    public ItemMinecraftSciencePack() {
        setRegistryName("minecraftsciencepack");        // The unique name (within your mod) that identifies this item
        setUnlocalizedName(FactorioCraft.MODID + ".minecraftsciencepack");     // Used for localization (en_US.lang)
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        setCreativeTab(FactorioCraft.CUSTOM_TAB);
    }
}
