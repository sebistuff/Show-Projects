package a12229486;

import java.util.HashSet;
import java.util.Set;

public class ProtectingSpell extends Spell {
	private Set<AttackingSpell> attacks;

	public ProtectingSpell(String name, int manaCost, MagicLevel levelNeeded, Set<AttackingSpell> attacks) {
		super(name, manaCost, levelNeeded);
		if(attacks==null || attacks.isEmpty())throw new IllegalArgumentException(" Protectingspell - Construcotr - false attacks ");
		this.attacks=new HashSet<>(attacks);
	}

	@Override
	public void doEffect(MagicEffectRealization target) {
		if(target==null)throw new IllegalArgumentException("ProtectingSpell - doEffect - null Target");
		target.setProtection(attacks);
	}
	@Override 
	public String additionalOutputString() {
		return String.format("; protects against %s", attacks.toString());
	}
}
