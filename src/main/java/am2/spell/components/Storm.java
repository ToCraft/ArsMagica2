package am2.spell.components;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import am2.AMCore;
import am2.api.ArsMagicaApi;
import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.items.ItemsCommonProxy;
import am2.particles.AMParticle;
import am2.particles.ParticleOrbitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class Storm implements ISpellComponent{
	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, int blockx, int blocky, int blockz, int blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		applyEffect(caster, world);
		return true;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target){
		applyEffect(caster, world);
		return true;
	}

	private void applyEffect(EntityLivingBase caster, World world){
		if (world.getWorldInfo().isThundering()){
			if (!world.isRemote){
				int xzradius = 50;
				int random = world.rand.nextInt(100);
				if (random < 20){
					int randPosX = (int)caster.posX + world.rand.nextInt(xzradius * 2) - xzradius;
					int randPosZ = (int)caster.posZ + world.rand.nextInt(xzradius * 2) - xzradius;
					int posY = (int)caster.posY;

					while (!world.canBlockSeeTheSky(randPosX, posY, randPosZ)){
						posY++;
					}
					while (world.getBlock(randPosX, posY - 1, randPosZ) == Blocks.air){
						posY--;
					}

					EntityLightningBolt bolt = new EntityLightningBolt(world, randPosX, posY, randPosZ);
					world.addWeatherEffect(bolt);
				}else if (random < 80){
					List<Entity> entities = world.getEntitiesWithinAABB(IMob.class, caster.boundingBox.expand(xzradius, 10D, xzradius));
					List<Entity> playerList = world.getEntitiesWithinAABB(EntityPlayer.class, caster.boundingBox.expand(xzradius, 10D, xzradius));
					if (playerList.contains(caster)) playerList.remove(caster);
					entities.addAll(playerList);
					if (entities.size() <= 0){
						return;
					}
					Entity target = entities.get(world.rand.nextInt(entities.size()));
					if (target != null && world.canBlockSeeTheSky((int)target.posX, (int)target.posY, (int)target.posZ)){
						if (caster instanceof EntityPlayer){
							target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)caster), 1);
						}
						EntityLightningBolt bolt = new EntityLightningBolt(world, target.posX, target.posY, target.posZ);
						world.addWeatherEffect(bolt);
					}
				}
			}
		}else if (!world.isRemote) {
			if (!world.getWorldInfo().isRaining()) {
				if (!world.getWorldInfo().isThundering() && world.rand.nextBoolean()) world.getWorldInfo().setThundering(true);
				world.getWorldInfo().setRaining(true);
			}
		}
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 15;
	}

	@Override
	public float burnout(EntityLivingBase caster){
		return ArsMagicaApi.getBurnoutFromMana(manaCost(caster));
	}

	@Override
	public ItemStack[] reagents(EntityLivingBase caster){
		return null;
	}

	@Override
	public void spawnParticles(World world, double x, double y, double z, EntityLivingBase caster, Entity target, Random rand, int colorModifier){
		AMParticle particle = (AMParticle)AMCore.proxy.particleManager.spawn(world, "symbols", x, y - 1, z);
		if (particle != null){
			particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.2f, 2, false).SetTargetDistance(1));
			particle.setMaxAge(40);
			particle.setParticleScale(0.1f);
			if (colorModifier > -1){
				particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
			}
		}
	}

	@Override
	public EnumSet<Affinity> getAffinity(){
		return EnumSet.of(Affinity.LIGHTNING, Affinity.NATURE);
	}

	@Override
	public int getID(){
		return 52;
	}

	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				new ItemStack(ItemsCommonProxy.rune, 1, ItemsCommonProxy.rune.META_YELLOW),
				new ItemStack(ItemsCommonProxy.itemOre, 1, ItemsCommonProxy.itemOre.META_BLUETOPAZ),
				Items.ghast_tear
		};
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.00001f;
	}
}
