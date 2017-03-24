import java.io.Serializable;

//NICHOLAS, ADRIEN; CS610; PRP 1; 6291 
public class Node_6291 implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private Byte value = null;
	private int count = 0;
	private boolean isDerived = false;
	private Node_6291 left = null;
	private Node_6291 right = null;

	public Node_6291(char ch) {
		value = (byte) ch;
	}

	public Node_6291(Byte value) {
		this.value = value;
	}

	public void incrementCount() {
		count = count + 1;
	}

	public Byte getValue() {
		return value;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String toString() {
		if (this.isDerived) {
			return "derived[" + count + "]";
		} else {
			return "" + ((char) value.shortValue()) + "[" + count + "]";
		}
	}

	public boolean isDerived() {
		return isDerived;
	}

	public void setDerived(boolean isDerived) {
		this.isDerived = isDerived;
	}

	public Node_6291 getLeft() {
		return left;
	}

	public void setLeft(Node_6291 left) {
		this.left = left;
	}

	public Node_6291 getRight() {
		return right;
	}

	public void setRight(Node_6291 right) {
		this.right = right;
	}
}
