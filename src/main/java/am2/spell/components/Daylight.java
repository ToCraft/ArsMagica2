package am2.spell.components;

import java.util.EnumSet;
import java.util.Random;

import am2.AMCore;
import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.items.ItemsCommonProxy;
import lotr.common.LOTRTime;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Daylight implements ISpellComponent{

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				new ItemStack(ItemsCommonProxy.itemOre, 1, ItemsCommonProxy.itemOre.META_SUNSTONE),
				Items.clock,
				new ItemStack(ItemsCommonProxy.essence, 1, ItemsCommonProxy.essence.META_PURE)
		};
	}

	@Override
	public int getID(){
		return 64;
	}

	private boolean setDayTime(World world, EntityLivingBase caster){
		if (world.isDaytime())
			return false;
		if (!world.isRemote && AMCore.foundLotRMod && caster.dimension == 100){
			LOTRTime.setWorldTime(Math.round(LOTRTime.DAY_LENGTH * 0.03));
			return true;
		}
		else if (!world.isRemote){
			long curTime = ((WorldServer)world).getWorldTime();
			int day = (int)Math.ceil((curTime / 24000));
			((WorldServer)world).setWorldTime(day * 24000);
		}
		return true;
	}

	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, int blockx, int blocky, int blockz, int blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		return setDayTime(world, caster);
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		return setDayTime(world, caster);
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 25000;
	}

	@Override
	public float burnout(EntityLivingBase caster){
		return 800;
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){

	}

	@Override
	public EnumSet<Affinity> getAffinity(){
		return EnumSet.of(Affinity.NONE);
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0;
	}

}
