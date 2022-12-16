package am2.buffs;

import net.minecraft.entity.EntityLivingBase;

public class BuffEffectSwiftSwim extends BuffEffect{

	public BuffEffectSwiftSwim(int duration, int amplifier){
		super(BuffList.swiftSwim.id, duration, amplifier);
	}

	@Override
	public void applyEffect(EntityLivingBase entityliving){
	}

	@Override
	public void stopEffect(EntityLivingBase entityliving){
	}

	@Override
	protected String spellBuffName(){
		return "Swift Swim";
	}

}
