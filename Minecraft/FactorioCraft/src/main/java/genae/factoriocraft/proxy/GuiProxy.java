package genae.factoriocraft.proxy;

import genae.factoriocraft.blocks.ContainerGeneratorT1;
import genae.factoriocraft.blocks.GuiGeneratorT1;
import genae.factoriocraft.blocks.TileEntityGeneratorT1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityGeneratorT1) {
            return new ContainerGeneratorT1(player.inventory, (TileEntityGeneratorT1) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityGeneratorT1) {
            TileEntityGeneratorT1 containerTileEntity = (TileEntityGeneratorT1) te;
            return new GuiGeneratorT1(containerTileEntity, new ContainerGeneratorT1(player.inventory, containerTileEntity));
        }
        return null;
    }
}
