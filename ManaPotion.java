package a12229486;

public class ManaPotion extends Potion {
	private  int mana;

	public ManaPotion(String name, int usages, int price, int weight, int mana) {
		super(name, usages, price, weight);
		if(mana<0)throw new IllegalArgumentException(" ManaPotion - Constructor - false mana ");
		this.mana=mana;
	}
	@Override 
	public String additionalOutputString() {
		return String.format("; +%d MP", mana);
	}
	@Override
	public void useOn(MagicEffectRealization target) {
		if(tryUsage()) {
			target.enforceMagic(mana);
		}
	}

	@Override
	public boolean provideMana(MagicLevel levelNeeded, int manaAmount) {
		// TODO Auto-generated method stub
		return false;
	}

}
