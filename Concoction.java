package a12229486;

import java.util.ArrayList;
import java.util.List;

public class Concoction extends Potion {
	private int health;
	private int mana;
	private List<Spell>spells;
	
	public Concoction(String name, int usages, int price, int weight, int health, int mana, List<Spell>spells) {
		super(name, usages, price, weight);
		if(spells==null)throw new IllegalArgumentException(" Concoction - Constructor - null spells ");
		if(health==0 && mana==0 && spells.isEmpty())throw new IllegalArgumentException(" Concoction - Constructor - no effects ");
		this.health=health;
		this.mana=mana;
		this.spells=new ArrayList<>(spells);
	}
	@Override 
	public String additionalOutputString() {
		StringBuilder output= new StringBuilder();
		
		if(health!=0)if(health<0)output.append(String.format("; %d HP",health));
		else output.append(String.format("; +%d HP",health));
		if(mana!=0)if(mana<0)output.append(String.format("; %d MP",mana));
		else output.append(String.format("; +%d MP",mana));
		if(!spells.isEmpty())output.append("; cast ").append(spells.toString());
		return output.toString();
	}
	@Override
	public void useOn(MagicEffectRealization target) {
		if(tryUsage()) {
			if(health>0)target.heal((health));
			else target.takeDamage(Math.abs(health));
			if(mana>0)target.enforceMagic(mana);
			else target.weakenMagic(Math.abs(mana));
			for (Spell spell : spells) {
			    if (spell != null) {
			        spell.cast(this, target);
			    }
			}
		}
	}
}
