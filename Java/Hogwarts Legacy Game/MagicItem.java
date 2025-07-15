package a12229486;

public abstract class MagicItem implements Tradeable, MagicEffectRealization, MagicSource {
	private String name;
	private int usages;
	private int price;
	private int weight;
	
	public MagicItem(String name,int usages,int price,int weight) {
		if(name==null||name.isEmpty()||usages<0 ||price<0 || weight<0)throw new IllegalArgumentException(" MagicItem - MagicItem() - false Konstruktor Parameter ");
		this.name=name;
		this.usages=usages;
		this.price=price;
		this.weight=weight;
	}
	public int getUsages() {
		return usages;
	}
	public boolean tryUsage() {
		if(usages<=0)return false;
		--usages;
		return true;
	}
	public String usageString() {
		if(usages==1)return "use";
		return "uses";
	}
	public String additionalOutputString() {
		return"";
	}
	@Override 
	public String toString() {
		String currString=price==1 ?"Knut":"Knuts";
		return String.format("[%s; %d g; %d %s; %d %s%s]",
				name, weight, price, currString, usages,usageString(),additionalOutputString());
	}
	@Override
	public int getPrice() {
		return price;
	}
	@Override 
	public int getWeight() {
		return weight;
	}
	@Override
	public boolean provideMana(MagicLevel levelNeeded, int amount){
		if(levelNeeded==null || amount<0)throw new IllegalArgumentException(" MagicItem - provideMana() - false levelNeeded/amount ");
		return true;
	}
	@Override
	public void takeDamagePercent(int percentage) {
		if(percentage<0 || percentage>100 )throw new IllegalArgumentException(" MagicItem - takeDamagePercent() - false percentage ");
		usages= (int)(usages*(1-(percentage/100.)));
	}
}
	