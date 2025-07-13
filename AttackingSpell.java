package a12229486;

import java.util.Set;

public class AttackingSpell extends Spell {
	private boolean type;
	private boolean percentage;
	private int amount;

	public AttackingSpell(String name, int manaCost, MagicLevel levelNeeded,boolean type, boolean percentage, int amount) {
		super(name, manaCost, levelNeeded);
		if((percentage &&(amount <0 || amount >100)) || amount<0)throw new IllegalArgumentException("Attacking Spell - Constructor - false amount ");
		this.type=type;
		this.percentage=percentage;
		this.amount=amount;
	}
	@Override
	public void doEffect(MagicEffectRealization target) {
		if(target.isProtected(this)) {
			target.removeProtection(Set.of(this));
			return;
		}
		if(type) {
			if(percentage)target.takeDamagePercent(amount);
			else target.takeDamage(amount); 
		}
		else if(percentage)target.weakenMagicPercent(amount);
		else target.weakenMagic(amount);
		}
	@Override
	public String additionalOutputString() {
		if(type) {
			if(percentage)return String.format("; -%d %% HP", amount);
			else return String.format("; -%d HP", amount);
		}
		if(percentage)return String.format("; -%d %% MP", amount);
		else  return String.format("; -%d MP", amount);	
	}
}
