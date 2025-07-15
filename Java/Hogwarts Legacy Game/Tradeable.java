package a12229486;

public interface Tradeable {
	 int getPrice();
	 int getWeight();
	private boolean transfer(Trader from, Trader to){
		if(from.removeFromInventory(this)&&to.addToInventory(this))return true;
		return false;
	}
	default boolean give(Trader giver, Trader taker) {
		if(giver==null || taker==null||giver==taker)throw new IllegalArgumentException(" Tradeable - give() - null giver/taker ");
		if(!giver.possesses(this))return false;
		if(!taker.hasCapacity(this.getWeight()))return false;
		return this.transfer(giver,taker);
	}
	default boolean purchase(Trader seller, Trader buyer) {
		if(seller==null ||buyer==null ||seller==buyer)throw new IllegalArgumentException(" Tradeable - purchase() - null seller/taker ");
		if(!seller.possesses(this))return false;
		if(!buyer.hasCapacity(this.getWeight()))return false;
		if(!buyer.canAfford(this.getPrice()))return false;
		
		buyer.pay(this.getPrice());
		seller.earn(this.getPrice());
		return this.transfer(seller, buyer);
	}
	
	 void useOn(MagicEffectRealization target);
}
