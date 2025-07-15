package a12229486;

public class HealthPotion extends Potion {
	private int health;

	public HealthPotion(String name, int usages, int price, int weight,int health){
		super(name,usages,price,weight);
		if(health<0)throw new IllegalArgumentException(" Health Potion - Constructor - false health ");
		this.health=health;
	}
	@Override
	public String additionalOutputString() {
		return String.format("; +%d HP",health);
	}
	@Override 
	public void useOn(MagicEffectRealization target) {
		if(this.tryUsage()){
			target.heal(health);
		}
		else {
			System.out.println("No usages left!");
		}
	}	
	@Override 
	public boolean provideMana(MagicLevel levelNeeded, int manaAmount) {
		return false;
	}
}
