package seraphaestus.historicizedmedicine.Mob;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import seraphaestus.historicizedmedicine.HMedicineMod;

public class VillageStall extends Village {
	
	private EnumFacing facing;
	private Random rand;
	
	public VillageStall()
	{
	}
	public VillageStall(Start villagePiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, EnumFacing facing)
	{
		super(villagePiece, par2);
		this.facing = facing;
		this.setCoordBaseMode(facing);
		this.rand = par3Random;
		this.boundingBox = par4StructureBoundingBox;
	}

	private int groundLevel = -1;
	@SuppressWarnings("deprecation")
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox box)
	{
		if(groundLevel < 0)
		{
			groundLevel = this.getAverageGroundLevel(world, box);
			if(groundLevel<0)
				return true;
			boundingBox.offset(0, groundLevel - boundingBox.maxY+10-1-7, 0);
		}

		//Clear Space
		this.fillWithBlocks(world, box, 0,0,0, 3,2,2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		this.fillWithBlocks(world, box, 0,1,0, 3,1,2, Blocks.TRIPWIRE.getDefaultState(), Blocks.TRIPWIRE.getDefaultState(), false);
		//roof
		this.fillWithBlocks(world, box, 0,2,0, 0,2,2, Blocks.CARPET.getStateFromMeta(7), Blocks.CARPET.getStateFromMeta(7), false);
		this.fillWithBlocks(world, box, 1,2,0, 1,2,2, Blocks.CARPET.getStateFromMeta(8), Blocks.CARPET.getStateFromMeta(8), false);
		this.fillWithBlocks(world, box, 2,2,0, 2,2,2, Blocks.CARPET.getStateFromMeta(7), Blocks.CARPET.getStateFromMeta(7), false);
		this.fillWithBlocks(world, box, 3,2,0, 3,2,2, Blocks.CARPET.getStateFromMeta(8), Blocks.CARPET.getStateFromMeta(8), false);
		//
		this.fillWithBlocks(world, box, 0,0,0, 3,0,2, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 1,0,1, 2,0,1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		IBlockState stair = Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, this.facing);
		this.fillWithBlocks(world, box, 1,1,0, 2,1,0, Blocks.CARPET.getStateFromMeta(12), Blocks.CARPET.getStateFromMeta(12), false);
		this.fillWithBlocks(world, box, 0,0,0, 0,0,0, stair, stair, false);
		this.setBlockState(world, ForgeRegistries.BLOCKS.getValue(new ResourceLocation(HMedicineMod.MODID, "crafting_table")).getDefaultState(), 1,0,0, box);
		this.setBlockState(world, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, this.facing.rotateY().rotateY().rotateY()), 2,0,0, box);
		this.setBlockState(world, Blocks.CRAFTING_TABLE.getDefaultState(), 3,0,0, box);
		
		//pillars
		this.fillWithBlocks(world, box, 0,1,0, 0,1,0, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 0,1,2, 0,1,2, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 3,1,0, 3,1,0, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
		this.fillWithBlocks(world, box, 3,1,2, 3,1,2, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
		
		for(int zz=0; zz<=2; zz++)
			for(int xx=0; xx<=3; xx++)
			{
				this.clearCurrentPositionBlocksUpwards(world, xx,10,zz, box);
				this.replaceAirAndLiquidDownwards(world, Blocks.DIRT.getDefaultState(), xx, -1,zz, box);
			}

		this.spawnVillagers(world, box, 1, 0, 1, 1);
		return true;
	}

	@Override
	protected VillagerProfession chooseForgeProfession(int count, net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession prof)
	{
		if(rand.nextBoolean() == true) {
			return VillagerProfessions.PlagueDoctorProfession;
		}
			return VillagerProfessions.ApothecarianProfession;
	}

	public static class VillageManager implements IVillageCreationHandler
	{
		@Override
		public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces, Random random, int p1, int p2, int p3, EnumFacing facing, int p5)
		{
			StructureBoundingBox box = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 4, 3, 3, facing);
			return (!canVillageGoDeeper(box)) || (StructureComponent.findIntersecting(pieces, box) != null) ? null : new VillageStall(startPiece, p5, random, box, facing);
		}
		@Override
		public PieceWeight getVillagePieceWeight(Random random, int i)
		{
			return new PieceWeight(VillageStall.class, 15, MathHelper.getInt(random, 0 + i, 1 + i));
		}
		@Override
		public Class<?> getComponentClass()
		{
			return VillageStall.class;
		}
	}
}
