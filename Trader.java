package a12229486;

public interface Trader {
	
	boolean possesses(Tradeable item);
	
	boolean canAfford(int amount);
	
	boolean hasCapacity(int weight);
	boolean pay(int amount);

	boolean earn(int amount);
	
	boolean addToInventory(Tradeable item);
	boolean removeFromInventory(Tradeable item);
	default boolean canSteal() {
		return false;
	}
	boolean steal(Trader thief);

	default boolean isLootable(){
		return false;
	}
	default boolean canLoot(){
		return false;
	}
	boolean loot(Trader looter);
	boolean isDead();
}

