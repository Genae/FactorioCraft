package genae.factoriocraft.blocks;

import genae.factoriocraft.FactorioCraft;
import genae.factoriocraft.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockGeneratorT1 extends Block implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyInteger STATE = PropertyInteger.create("state", 0, 4);
    public static final int GUI_ID = 1;

    public BlockGeneratorT1() {
        super(Material.ROCK);
        setUnlocalizedName(FactorioCraft.MODID + ".generatort1");
        setRegistryName("generatort1");

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(STATE, 0));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public static void setBlockState(boolean enabled, boolean cooking, boolean full, World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);

        int renderState = cooking ? 2 : (full && enabled ? 3 : (full ? 4 : (enabled ? 1 : 0)));
        world.setBlockState(pos, ModBlocks.generatorT1.getDefaultState().withProperty(STATE, renderState).withProperty(FACING, state.getValue(FACING)));
        if(te != null){
            te.validate();
            world.setTileEntity(pos, te);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        TileEntityGeneratorT1 te = getTileEntity(worldIn, pos);
        return state.withProperty(STATE, te.getBlockState());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
    }

    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
                .withProperty(FACING, EnumFacing.getFront(meta & 7))
                .withProperty(STATE, 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGeneratorT1();
    }

    private TileEntityGeneratorT1 getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityGeneratorT1) world.getTileEntity(pos);
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        TileEntityGeneratorT1 te = getTileEntity(worldIn, pos);
        for(int i = 0; i < TileEntityGeneratorT1.SIZE; i++)
            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), te.itemStackHandler.getStackInSlot(i)));
        super.breakBlock(worldIn, pos, state);
    }
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        player.openGui(FactorioCraft.instance, GUI_ID, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
