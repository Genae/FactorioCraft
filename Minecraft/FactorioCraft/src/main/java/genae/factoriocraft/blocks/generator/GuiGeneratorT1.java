package genae.factoriocraft.blocks.generator;

import genae.factoriocraft.FactorioCraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiGeneratorT1 extends GuiContainer {
    private final TileEntityGeneratorT1 te;

    private static final ResourceLocation TEXTURES = new ResourceLocation(FactorioCraft.MODID, "textures/gui/containergeneratort1.png");

    public GuiGeneratorT1(TileEntityGeneratorT1 tileEntity, ContainerGeneratorT1 container) {
        super(container);
        te = tileEntity;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(Integer.toString(this.te.energyStorage.getEnergyStored()), 115, 52, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft + 116, this.guiTop + 25, 180, 14, l + 1, 16);

        int k = this.getEnergyStoredScaled(55);
        this.drawTexturedModalRect(this.guiLeft + 151, this.guiTop + 6, 180, 31, 16, 55 - k);

        this.drawTexturedModalRect(this.guiLeft + 100, this.guiTop + 25, 180, 0, 13, 13 * (this.te.cooking ? 1 : 0));
    }

    private int getEnergyStoredScaled(int pixels)
    {
        int i = this.te.energyStorage.getEnergyStored();
        int j = this.te.energyStorage.getMaxEnergyStored();
        return i != 0 && j != 0 ? i * pixels / j : 0;
    }

    private int getCookProgressScaled(int pixels)
    {
        int i = this.te.cookTimeMax - this.te.cookTime;
        return i != 0 && this.te.cookTimeMax != 0 ? i * pixels / this.te.cookTimeMax : 0;
    }
}
