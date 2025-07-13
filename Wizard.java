package a12229486;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Wizard implements MagicSource, Trader, MagicEffectRealization {
	private String name;
	private MagicLevel level;
	private int basicHP;
	private int HP;
	private int basicMP;
	private int MP;
	private int money;
	private Set<Spell>knownSpells;
	private Set<AttackingSpell>protectedFrom;
	private int carryingCapacity;
	private Set<Tradeable> inventory;
	
	
	public Wizard(String name, MagicLevel level, int basicHP,int HP,int basicMP,int MP,int money,Set<Spell> knownSpells, Set<AttackingSpell> protectedFrom, int carryingCapacity, Set<Tradeable> inventory ) {
		if(name==null||name.isEmpty())throw new IllegalArgumentException(" Wizard - Constructor - false/null name ");
		if(level==null)throw new IllegalArgumentException(" Wizard - Constructor - null level ");
		if(basicHP<0)throw new IllegalArgumentException(" Wizard - Constructor - false basicHP ");
		if(HP < 0 || HP > basicHP)throw new IllegalArgumentException(" Wizard - Constructor - false HP ");
		if(basicMP < level.toMana())throw new IllegalArgumentException(" Wizard - Constructor - false basicMP ");
		if(MP < 0 || MP > basicMP)throw new IllegalArgumentException(" Wizard - Constructor - false MP ");
		if(money<0)throw new IllegalArgumentException(" Wizard - Constructor - false money ");
		if(knownSpells==null)throw new IllegalArgumentException(" Wizard - Constructor - null knownsSpells ");
		if(protectedFrom==null)throw new IllegalArgumentException(" Wizard - Constructor - null protectedSpell ");
		if(carryingCapacity<0)throw new IllegalArgumentException(" Wizard - Constructor - false carryingCapacity ");
		if(inventory==null)throw new IllegalArgumentException(" Wizard - Constructor - null invetory ");
		this.inventory=new HashSet<>(inventory);
		if(inventoryTotalWeight()>carryingCapacity)throw new IllegalArgumentException(" Wizard - Constructor - Weight of SumItem > CarryingCapacity ");
		
		this.name=name;
		this.level=level;
		this.basicHP=basicHP;
		this.HP=HP;
		this.basicMP=basicMP;
		this.MP=MP; //wieso funktioniert ManaPotion test nur, wenn MP nicht mit basicMP instanziirt wird??
		this.money=money;
		this.knownSpells=new HashSet<>(knownSpells);
		this.protectedFrom=new HashSet<>(protectedFrom);
		this.carryingCapacity=carryingCapacity;
	}
	public boolean isDead() {
		return HP==0;
	}
	private int inventoryTotalWeight() {
		int totalWeight=inventory.stream().mapToInt(Tradeable::getWeight).sum();
		return totalWeight;
	}
	public boolean learn(Spell s) {
		if(s==null)throw new IllegalArgumentException(" Wizard - learn() - null Spell ");
		if(this.isDead())return false;
		return knownSpells.add(s);
	}
	public boolean forget(Spell s) {
		if(s==null)throw new IllegalArgumentException(" Wizard - learn() - null Spell ");
		if(this.isDead())return false;
		return knownSpells.remove(s);
	}
	public boolean castSpell(Spell s, MagicEffectRealization target) {
		if(s==null || target==null)throw new IllegalArgumentException(" Wizard - learn() - null Spell/target ");
		if(this.isDead())return false;
		if(!knownSpells.contains(s))return false;
		s.cast(this, target);
		return true;
	}
	public boolean castRandomSpell(MagicEffectRealization target) {
		if(knownSpells.isEmpty())return false;
		int randomIndex= new Random().nextInt(knownSpells.size());
		Spell randomSpell=knownSpells.stream().skip(randomIndex).findFirst().orElse(null);
		return castSpell(randomSpell, target);
	}
	public boolean useItem(Tradeable item, MagicEffectRealization target) {
		if(item==null || target==null)throw new IllegalArgumentException(" Wizard - useItem() - null item/target ");
		if(this.isDead())return false;
		if(!inventory.contains(item))return false;
		item.useOn(target);
		return true;
	}
	public boolean useRandomItem(MagicEffectRealization target) {
		if(inventory.isEmpty())return false;
		int randomIndex=new Random().nextInt(inventory.size());
		Tradeable randomItem=inventory.stream().skip(randomIndex).findFirst().orElse(null);
		return useItem(randomItem, target);
	}
	public boolean sellItem(Tradeable item, Trader target) {
		if(item==null || target==null)throw new IllegalArgumentException(" Wizard - sellItem() - null item/target ");
		if(this.isDead())return false;
		return item.purchase(this, target);
	}
	public boolean sellRandomItem(Trader target) {
		if(inventory.isEmpty())return false;
		int randomIndex= new Random().nextInt(inventory.size());
		Tradeable randomItem=inventory.stream().skip(randomIndex).findFirst().orElse(null);
		return sellItem(randomItem,target);
	}
	@Override
		public String toString() {
		String currencyString = money == 1 ? "Knut" : "Knuts";
		return String.format("[%s(%s): %d/%d %d/%d; %d %s; knows %s; carries %s]",name, level.toString(),HP,basicHP,MP,basicMP,money,currencyString,knownSpells.toString(),inventory.toString() );
	}
	@Override
	public boolean provideMana(MagicLevel levelNeeded, int manaAmount) {
		if(levelNeeded==null)throw new IllegalArgumentException(" Wizard - provideMana() -null levelNeeded ");
		if(manaAmount<0)throw new IllegalArgumentException(" Wizard - provideMana() -false manaAmount ");
		if(isDead()) return false;
		if(level.compareTo(levelNeeded)<0)return false;
		if(MP<manaAmount)return false;
		MP-=manaAmount;
		return true;
	}
	@Override
	public boolean possesses(Tradeable item) {
		if(item==null)throw new IllegalArgumentException(" Wizard - posseses() - null item ");
		return inventory.contains(item);
	}

	@Override
	public boolean canAfford(int amount) {
		if (amount<0)throw new IllegalArgumentException(" Wizard - canAfford() - false amount ");
		if(money>=amount)return true;
		return false;
	}

	@Override
	public boolean hasCapacity(int weight) {
		if(weight<0)throw new IllegalArgumentException(" Wizard - hasCapacity() - false amount ");
		System.out.println(carryingCapacity);
		return((inventoryTotalWeight() + weight) <= carryingCapacity);
	}

	@Override
	public boolean pay(int amount) {
		if(amount<1)throw new IllegalArgumentException(" Wizard - pay() - false amount ");
		if(isDead()) return false;
		if(canAfford(amount)) {
			money-=amount;
			return true;
		}
		return false;
	}

	@Override
	public boolean earn(int amount) {
		if(amount<1)throw new IllegalArgumentException(" Wizard - earn() - false amount ");
		if(isDead())return false; 
		
		money+=amount;
		return true;
	}

	@Override
	public boolean addToInventory(Tradeable item) {
		  if (item == null)throw new IllegalArgumentException("Wizard - addToInventory() - item is null");
		if(hasCapacity(item.getWeight())) {
			return inventory.add(item);
			}
		return false;
	}

	@Override
	public boolean removeFromInventory(Tradeable item) {
		if(item==null)throw new IllegalArgumentException(" Wizard - removeFromInventory() - null item");
		return inventory.remove(item);
	}
	@Override
	public boolean canSteal() {
		return !isDead();
	}
	@Override
	public boolean steal(Trader thief) {
		if(thief==null)throw new IllegalArgumentException(" Wizard - steal() - null thief");
		if(!thief.canSteal())return false;
		if(inventory.isEmpty())return false;
		int randomIndex=new Random().nextInt(inventory.size());
		Tradeable randomItem=inventory.stream().skip(randomIndex).findFirst().orElse(null);
		if(randomItem==null)return false;
		if(!thief.hasCapacity(randomItem.getWeight())) {
			 this.removeFromInventory(randomItem);
			 return false;
		}
		thief.addToInventory(randomItem);
		return this.removeFromInventory(randomItem);
		
	}
	@Override
	public boolean isLootable() {
		return isDead();
	}
	@Override
	public boolean canLoot() {
		return !isDead();
	}
	@Override
	public boolean loot(Trader looter) {
		if(looter==null)throw new IllegalArgumentException(" Wizard - loot() - null looter ");
		if(!looter.canLoot())return false;
		if(this.isLootable()) {
			List<Tradeable> itemsToRemove = new ArrayList<>();
		    for (Tradeable item : inventory) {
		    	if (looter.addToInventory(item)) {
		            itemsToRemove.add(item); 
		            }
		        }
		        inventory.clear();
		        return true;
		    }
		    return false;
		}
	@Override
	public void takeDamage(int amount) {
		if(amount<0)throw new IllegalArgumentException("Wizard - takeDamage() - false amount ");
		HP = Math.max(HP - amount, 0);
	}
	@Override
	public void takeDamagePercent(int percentage) {
		if (percentage < 0 || percentage > 100)throw new IllegalArgumentException("Wizard - takeDAmagePercent() - false percentage ");
		int damage = (int) (basicHP * (percentage / 100.0)); 
		HP=(HP-damage)>0? HP-damage : 0;
	}
	@Override
	public void weakenMagic(int amount) {
		if(amount<0)throw new IllegalArgumentException("Wizard - weakenMagic() - false amount ");
		MP=(MP-amount)>0?MP-amount : 0;
	}
	@Override
	public void weakenMagicPercent(int percentage) {
		if (percentage < 0 || percentage > 100)throw new IllegalArgumentException("Wizard - weakenMagicPercent() - false percentage ");
		int damage = (int) (basicMP * (percentage / 100.0)); 
		MP=(MP-damage)>0? MP-damage : 0;
	}
	@Override
	public void heal(int amount) {
		if(amount <0)throw new IllegalArgumentException("Wizard - heal() - false amount ");
		HP = HP+amount;
	}
	@Override 
	public void healPercent(int percentage) {
		if (percentage < 0 || percentage > 100)throw new IllegalArgumentException("Wizard - healPercent() - false percentage ");
		int healing = (int) (basicHP * (percentage / 100.0)); 
		HP+=healing;
	}
	@Override 
	public void enforceMagic(int amount) {
		if(amount<0)throw new IllegalArgumentException("Wizard - enforceMagic() - false amount ");
		MP = MP + amount;
	}
	@Override 
	public void enforceMagicPercent(int percentage) {
		if (percentage < 0 || percentage > 100)throw new IllegalArgumentException("Wizard - enforceMagicPercent() - false percentage ");
		int enforcing = (int) (basicMP * (percentage / 100.0)); 
		MP+=enforcing;
	}
	@Override
	public boolean isProtected(Spell s) {
		if(s==null)throw new IllegalArgumentException("Wizard - isProtected() - null spell ");
		return protectedFrom.contains(s);
	}
	@Override
	public void setProtection(Set<AttackingSpell> attacks) {
		if(attacks==null)throw new IllegalArgumentException("Wizard - setProtection() - null attacks ");
		for(AttackingSpell att:attacks) {
			protectedFrom.add(att);
		}
	}
	@Override 
	public void removeProtection(Set<AttackingSpell> attacks) {
		if(attacks==null)throw new IllegalArgumentException("Wizard - removeProtection() - null attacks ");
		for(AttackingSpell att:attacks) {
			protectedFrom.remove(att);
		}
	}
}
