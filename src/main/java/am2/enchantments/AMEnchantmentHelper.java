package am2.enchantments;

import am2.api.enchantment.IAMEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class AMEnchantmentHelper implements IAMEnchantmentHelper{
	@Override
	public int getSoulboundID(){
		return AMEnchantments.soulbound.effectId;
	}

	@Override
	public int getMagicResistID(){
		return AMEnchantments.magicResist.effectId;
	}
	
	public static ItemStack smiteStack(ItemStack stack, int level){
		Map map = EnchantmentHelper.getEnchantments(stack);
		if (level > 0){
			map.put(Enchantment.smite.effectId, level);
		}else{
			map.remove(Enchantment.smite.effectId);
		}
		EnchantmentHelper.setEnchantments(map, stack);
		return stack;
	}
	
	public static ItemStack baneoaStack(ItemStack stack, int level){
		Map map = EnchantmentHelper.getEnchantments(stack);
		if (level > 0){
			map.put(Enchantment.baneOfArthropods.effectId, level);
		}else{
			map.remove(Enchantment.baneOfArthropods.effectId);
		}
		EnchantmentHelper.setEnchantments(map, stack);
		return stack;
	}

	public static ItemStack soulbindStack(ItemStack stack){
		Map map = EnchantmentHelper.getEnchantments(stack);
		map.put(AMEnchantments.soulbound.effectId, 1);
		EnchantmentHelper.setEnchantments(map, stack);
		return stack;
	}

	public static ItemStack silkTouchStack(ItemStack stack, int level){
		Map map = EnchantmentHelper.getEnchantments(stack);
		if (level > 0){
			map.put(Enchantment.silkTouch.effectId, level);
		}else{
			map.remove(Enchantment.silkTouch.effectId);
		}
		EnchantmentHelper.setEnchantments(map, stack);
		return stack;
	}

	public static ItemStack fortuneStack(ItemStack stack, int level){
		Map map = EnchantmentHelper.getEnchantments(stack);
		if (level > 0){
			map.put(Enchantment.fortune.effectId, level);
		}else{
			map.remove(Enchantment.silkTouch.effectId);
		}
		EnchantmentHelper.setEnchantments(map, stack);
		return stack;
	}

	public static ItemStack lootingStack(ItemStack stack, int level){
		Map map = EnchantmentHelper.getEnchantments(stack);
		if (level > 0){
			map.put(Enchantment.looting.effectId, level);
		}else{
			map.remove(Enchantment.silkTouch.effectId);
		}
		EnchantmentHelper.setEnchantments(map, stack);
		return stack;
	}

	public static void copyEnchantments(ItemStack source, ItemStack dest){
		Map map = EnchantmentHelper.getEnchantments(source);
		if (map != null){
			EnchantmentHelper.setEnchantments(map, dest);
		}
	}
}
