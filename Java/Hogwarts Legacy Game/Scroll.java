package a12229486;

public class Scroll extends MagicItem {
	private Spell spell;

	public Scroll(String name, int usages, int price, int weight, Spell spell) {
		super(name, usages, price, weight);
		if(spell==null)throw new IllegalArgumentException(" Scroll - Constructor - null Spell ");
		this.spell=spell;
	}
	@Override
	public String additionalOutputString() {
		return String.format("; casts %s",spell.toString());
	}
	@Override
	public void useOn(MagicEffectRealization target) {
		if(target==null)throw new IllegalArgumentException(" Scroll - useOn() - null target ");
		if(tryUsage()){
			spell.cast(this, target);
		}
	}

}
