package a12229486;

import java.util.Set;

public interface MagicEffectRealization {
	default void takeDamage(int amount) {
		if(amount<0)throw new IllegalArgumentException(" MagicEffectRealization - takeDamage- false amount ");
	}
	default void takeDamagePercent(int percentage) {
		if(percentage<0 ||percentage>100)throw new IllegalArgumentException(" MagicEffectRealization - takeDamagePercent - false amount");
	}
	default void weakenMagic(int amount) {
		if(amount<0)throw new IllegalArgumentException("MagicEffectRealization - weakenMagic - false amount");
	}
	default void weakenMagicPercent(int percentage) {
		if(percentage<0 ||percentage>100)throw new IllegalArgumentException(" MagicEffectRealization - weakenMagicPercent - false amount");
	}
	default void heal(int amount) {
		if(amount<0)throw new IllegalArgumentException(" MagicEffectRealization - heal- false amount ");
	}
	default void healPercent(int percentage) {
		if(percentage<0 ||percentage>100)throw new IllegalArgumentException(" MagicEffectRealization - healPercent - false amount");
	}
	default void enforceMagic(int amount) {
		if(amount<0)throw new IllegalArgumentException(" MagicEffectRealization - enforceMagic- false amount ");
	}
	default void enforceMagicPercent(int percentage){
		if(percentage<0 ||percentage>100)throw new IllegalArgumentException(" MagicEffectRealization - enforceMagicPercent - false amount");
	}
	default boolean isProtected(Spell s) {
		if(s==null)throw new IllegalArgumentException(" MagicEffectRealization -  isProtected - null spell ");
		return false;
	}
	default void setProtection(Set<AttackingSpell> attacks) {
		if(attacks==null)throw new IllegalArgumentException(" MagicEffectRealization -  setProtection - null attacks ");
	}
	default void removeProtection(Set<AttackingSpell> attacks) {
		if(attacks==null)throw new IllegalArgumentException(" MagicEffectRealization -  removeProtection - null attacks ");
	}
	
}
