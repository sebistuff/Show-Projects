package a12229486;

public class HealingSpell extends Spell {
	private boolean type;
	private boolean percentage;
	private int amount;

	public HealingSpell(String name, int manaCost, MagicLevel levelNeeded, boolean type, boolean percentage, int amount) {
		super(name, manaCost, levelNeeded);
		if(amount<0 ||(percentage &&(amount <0 || amount >100)))throw new IllegalArgumentException("Attacking Spell - Constructor - false amount ");
		this.type=type;
		this.percentage=percentage;
		this.amount=amount;
	}

	@Override
	public void doEffect(MagicEffectRealization target) {
		if (target == null) throw new IllegalArgumentException("HealingSpell - doEffect - Target must not be null");
		if(type) {
			if(percentage)target.healPercent(amount);
			else target.heal(amount);
		}
		else if(percentage)target.enforceMagicPercent(amount);
		else target.enforceMagic(amount);
	}
	@Override 
	public String additionalOutputString() {
		if(type) {
			if(percentage)return String.format("; +%d %% HP", amount);
			else return String.format("; +%d HP",amount);
		}
		else if(percentage)return String.format("; +%d %% MP", amount);
		else return String.format("; +%d MP", amount);
	}

}
