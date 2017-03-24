//NICHOLAS, ADRIEN; CS610; PRP 1; 6291 
public class Prefix_6291 {
	private String character = null;
	private String prefix = null;
	private Byte value = null;
	private int count = 0;

	public Prefix_6291(String prefix, Byte value) {
		this.setCharacter("" + ((char) value.shortValue()));
		this.setPrefix(prefix);
		this.setValue(value);
	}

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
