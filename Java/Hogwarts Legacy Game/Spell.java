package a12229486;

public abstract class Spell {
	private String name;
	private int manaCost;
	private MagicLevel levelNeeded;
	
	public Spell(String name,int manaCost,MagicLevel levelNeeded) {
		if(name==null||name.isEmpty()||manaCost<0|| levelNeeded==null) {
			throw new IllegalArgumentException(" Spell - Constructor - false name/manaCost/levelNeeded ");
		}
		this.name=name;
		this.manaCost=manaCost;
		this.levelNeeded=levelNeeded;
	}
	public void cast(MagicSource source, MagicEffectRealization target) {
		if(source==null || target==null)throw new IllegalArgumentException(" Spell - cast() - null source/target ");
		if(source.provideMana(levelNeeded,manaCost))doEffect(target);
	}
	public abstract void doEffect(MagicEffectRealization target);
	
	public String additionalOutputString() {
		return "";
	}
	@Override 
	public String toString() {
		return String.format("[%s(%s): %d mana%s]",name, levelNeeded.toString(),manaCost,additionalOutputString());
	}
}
