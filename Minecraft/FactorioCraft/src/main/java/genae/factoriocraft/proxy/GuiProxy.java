package genae.factoriocraft.proxy;

import genae.factoriocraft.blocks.consumer.ContainerConsumerT1;
import genae.factoriocraft.blocks.consumer.GuiConsumerT1;
import genae.factoriocraft.blocks.consumer.TileEntityConsumerT1;
import genae.factoriocraft.blocks.generator.ContainerGeneratorT1;
import genae.factoriocraft.blocks.generator.GuiGeneratorT1;
import genae.factoriocraft.blocks.generator.TileEntityGeneratorT1;
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
        if (te instanceof TileEntityConsumerT1) {
            return new ContainerConsumerT1(player.inventory, (TileEntityConsumerT1) te);
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
        if (te instanceof TileEntityConsumerT1) {
            TileEntityConsumerT1 containerTileEntity = (TileEntityConsumerT1) te;
            return new GuiConsumerT1(containerTileEntity, new ContainerConsumerT1(player.inventory, containerTileEntity));
        }
        return null;
    }
}
