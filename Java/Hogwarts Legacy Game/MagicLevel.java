package a12229486;

public enum MagicLevel {
	NOOB(50,"*"),
	ADEPT(100,"**"),
	STUDENT(200,"***"),
	EXPERT(500,"****"),
	MASTER(1000,"*****");
	
	private final int mp;
	private final String display;
	
	MagicLevel(int mp, String display){
		this.mp=mp;
		this.display=display;
	}
	public int toMana() {
		return mp;
	}
	@Override
	public String toString() {
		return display;
	}
}
