//NICHOLAS, ADRIEN; CS610; PRP 1; 6291 
import java.util.Arrays;

public class MinHeap_6291 {
	private int currentSize = 0;
	private Node_6291[] array;

	public MinHeap_6291() {
		array = new Node_6291[20];
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public String toString() {
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < currentSize; i++) {
			Node_6291 node = array[i];
			temp = temp.append(node.toString());
		}
		return temp.toString();
	}

	public void insert(Node_6291 candidate) {
		if (currentSize + 1 >= array.length) {
			array = Arrays.copyOf(array, array.length * 2);
		}
		array[currentSize] = candidate;
		heapifyUp(currentSize);
		currentSize = currentSize + 1;
	}

	private void heapifyUp(int index) {
		Node_6291 parent = (hasParent(index) == true) ? getParent(index) : null;
		if (parent != null) {
			if (parent.getCount() > array[index].getCount()) {
				swap(parentIndex(index), index);
				heapifyUp(parentIndex(index));
			}
		}
	}

	private void heapifyDown(int index) {
		Node_6291 right = (hasRightChild(index) == true) ? array[rightIndex(index)] : null;
		Node_6291 left = (hasLeftChild(index) == true) ? array[leftIndex(index)] : null;

		int smallestIndex = leftIndex(index);

		if (right != null && right.getCount() < array[smallestIndex].getCount()) {
			smallestIndex = rightIndex(index);
		}

		if (left != null && array[smallestIndex].getCount() < array[index].getCount()) {
			swap(index, smallestIndex);
			if (hasLeftChild(smallestIndex)) {
				heapifyDown(smallestIndex);
			}
		}
	}

	private void swap(int a, int b) {
		Node_6291 c = array[a];
		array[a] = array[b];
		array[b] = c;
	}

	public Node_6291 removeMin() {
		Node_6291 candidate = null;
		if (currentSize > 0) {
			candidate = array[0];
			array[0] = array[currentSize - 1];
			array[currentSize - 1] = null;
			currentSize = currentSize - 1;
			heapifyDown(0);
			if (currentSize * 2 < array.length && currentSize > 20) {
				array = Arrays.copyOf(array, Math.max(currentSize * 2, 20));
			}
		}
		return candidate;
	}

	private boolean hasParent(int i) {
		return i > 0;
	}

	private Node_6291 getParent(int i) {
		return array[parentIndex(i)];
	}

	private int parentIndex(int i) {
		return i / 2;
	}

	private int leftIndex(int i) {
		return i * 2 + 1;
	}

	private int rightIndex(int i) {
		return i * 2 + 2;
	}

	private boolean hasLeftChild(int i) {
		return (leftIndex(i) < currentSize);
	}

	private boolean hasRightChild(int i) {
		return (rightIndex(i) < currentSize);
	}

}
