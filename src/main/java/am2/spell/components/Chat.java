package am2.spell.components;

import java.util.EnumSet;
import java.util.Random;

import am2.api.spell.component.interfaces.ISpellComponent;
import am2.api.spell.enums.Affinity;
import am2.spell.SpellUtils;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Chat implements ISpellComponent {
	@Override
	public boolean applyEffectBlock(ItemStack stack, World world, int blockx, int blocky, int blockz, int blockFace, double impactX, double impactY, double impactZ, EntityLivingBase caster){
		return false;
	}

	@Override
	public boolean applyEffectEntity(ItemStack stack, World world, EntityLivingBase caster, Entity target) {
		return applyEffect(stack, target);
	}
	
	public boolean applyEffect(ItemStack stack, Entity target) {
		String chatMessage = SpellUtils.instance.getSpellMetadata(stack, "Message");
		FMLLog.info("Message: " + chatMessage);

		if (target instanceof EntityPlayer) {
			((EntityPlayer)target).addChatMessage(new ChatComponentText(chatMessage));
			return true;
		}
		else return false;
	}

	@Override
	public float manaCost(EntityLivingBase caster){
		return 0;
	}

	@Override
	public float burnout(EntityLivingBase caster){
		return 0;
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
	public int getID(){
		return 554;
	}

	/*
	 * Rename the sign to the chat message that will be shown on the target
	 */
	@Override
	public Object[] getRecipeItems(){
		return new Object[]{
				Items.sign,
				Items.redstone
		};
	}

	@Override
	public float getAffinityShift(Affinity affinity){
		return 0.0f;
	}
	
	public void setMessage(ItemStack stack, ItemStack signStack){
		FMLLog.info("Got this far...");
		SpellUtils.instance.setSpellMetadata(stack, "Message", signStack.getDisplayName());
	}
}
