//NICHOLAS, ADRIEN; CS610; PRP 1; 6291 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class FileUtils_6291 {

	private static String fileName;

	public static boolean isBinaryFile(File f) throws IOException {
		String type = Files.probeContentType(f.toPath());
		if (type == null) {
			// type couldn't be determined, assume binary
			return true;
		} else if (type.startsWith("text")) {
			return false;
		} else {
			// type isn't text
			return true;
		}
	}

	public static byte[] readBinaryFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		return Files.readAllBytes(path);
	}

	public static List<Node_6291> bitCount(byte[] byteArray) {
		List<Node_6291> histogram = new ArrayList<Node_6291>();
		for (byte singleByte : byteArray) {
			Node_6291 candidate = new Node_6291(singleByte);

			boolean found = false;
			for (Node_6291 node : histogram) {
				if (node.getValue() == candidate.getValue()) {
					node.incrementCount();
					found = true;
					break;
				}
			}
			if (!found) {
				candidate.incrementCount();
				histogram.add(candidate);
			}
		}
		return histogram;
	}

	public static Node_6291 buildMinHeap(List<Node_6291> histogram) {
		MinHeap_6291 initialHeap = new MinHeap_6291();

		// buildMinHeap
		for (Node_6291 node : histogram) {
			initialHeap.insert(node);
		}

		while (initialHeap.getCurrentSize() > 1) {
			Node_6291 z = new Node_6291(null);
			z.setDerived(true);

			Node_6291 left = null;
			Node_6291 right = null;

			left = initialHeap.removeMin();
			right = initialHeap.removeMin();

			if (null != left) {
				z.setLeft(left);
				z.setCount(z.getCount() + left.getCount());
			}

			if (null != right) {
				z.setRight(right);
				z.setCount(z.getCount() + right.getCount());
			}
			initialHeap.insert(z);
		}
		Node_6291 rootNode = initialHeap.removeMin();
		return rootNode;

	}

	public static void decodeFile() {
		BitSet obs = null;
		Node_6291 decodedRootNode = null;
		Boolean isEmpty = false;

		try {
			File file = new File(fileName);
			if (file.length() < 1) {
				isEmpty = true;
			} else {
				FileInputStream fin = new FileInputStream(fileName);
				// fin.re
				ObjectInputStream ois = new ObjectInputStream(fin);
				obs = (BitSet) ois.readObject();
				decodedRootNode = (Node_6291) ois.readObject();
				ois.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!isEmpty) {
			Node_6291 trans = decodedRootNode;
			List<Byte> bytes = new ArrayList<Byte>();
			StringBuffer obsString = new StringBuffer("");
			for (int i = 0; i <= obs.length(); i++) {
				if (obs.get(i)) {
					trans = trans.getRight();
					obsString = obsString.append("1");
				} else {
					trans = trans.getLeft();
					obsString = obsString.append("0");
				}
				if (!trans.isDerived()) {
					// System.out.print(trans.toString().charAt(0));
					bytes.add(trans.getValue());
					trans = decodedRootNode;
				}
			}
			// System.out.print(obsString.length() + " compression length: " +
			// obsString);

			int hufExtention = fileName.indexOf(".huf");
			String targetFileName = fileName.substring(0, hufExtention);
			Path path = Paths.get(targetFileName);
			byte[] aBytes = new byte[bytes.size()];
			for (int i = 0; i < bytes.size(); i++) {
				aBytes[i] = bytes.get(i);
			}
			try {
				Files.write(path, aBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			writeEmptyDecodedFile();
		}
	}

	public static void generatePrefix(Node_6291 candidate, String[] code, int depth, List<Prefix_6291> prefixes) {
		if (candidate.getRight() == null && candidate.getLeft() == null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < depth; i++) {
				sb = sb.append(code[i]);
			}
			Prefix_6291 p = new Prefix_6291(sb.toString(), candidate.getValue());
			p.setCount(candidate.getCount());
			prefixes.add(p);
		}
		if (candidate != null && candidate.getLeft() != null) {
			code[depth] = "0";
			generatePrefix(candidate.getLeft(), code, depth + 1, prefixes);
		}
		if (candidate != null && candidate.getRight() != null) {
			code[depth] = "1";
			generatePrefix(candidate.getRight(), code, depth + 1, prefixes);
		}
	}

	public static void writeEncodedFile(byte[] byteArray, Node_6291 rootNode, List<Prefix_6291> prefixes) {
		StringBuffer compressionString = new StringBuffer();
		for (byte b : byteArray) {
			boolean found = false;
			for (Prefix_6291 _p : prefixes) {
				if (_p.getValue().byteValue() == b) {
					found = true;
					compressionString = compressionString.append(_p.getPrefix());
				}
				if (found) {
					break;
				}
			}
		}

		int size = compressionString.length();
		BitSet bs = new BitSet(size / 256);
		for (int i = 0; i < size; i++) {
			boolean set = (compressionString.charAt(i) == '1') ? true : false;
			if (set) {
				bs.set(i);
			} else {
				bs.clear(i);
			}
		}

		try {
			FileOutputStream fout = new FileOutputStream(FileUtils_6291.getFileName() + ".huf");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(bs);
			oos.writeObject(rootNode);
			oos.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeEmptyEncodedFile() {
		try {
			FileOutputStream fout = new FileOutputStream(FileUtils_6291.getFileName() + ".huf");
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeEmptyDecodedFile() {
		try {
			FileOutputStream fout = new FileOutputStream(FileUtils_6291.getFileName());
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readEncoded(String decodeFile) {

		BitSet obs = null;
		Node_6291 decodedRootNode = null;

		try {
			FileInputStream fin = new FileInputStream(decodeFile);
			ObjectInputStream ois = new ObjectInputStream(fin);
			obs = (BitSet) ois.readObject();
			decodedRootNode = (Node_6291) ois.readObject();

			ois.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(obs.cardinality() + " " + obs + " " + obs.size());
		Node_6291 trans = decodedRootNode;
		List<Byte> bytes = new ArrayList<Byte>();
		StringBuffer obsString = new StringBuffer();
		for (int i = 0; i <= obs.length(); i++) {
			if (obs.get(i)) {
				trans = trans.getRight();
				obsString = obsString.append("1");
			} else {
				trans = trans.getLeft();
				obsString = obsString.append("0");
			}
			if (!trans.isDerived()) {
				bytes.add(trans.getValue());
				trans = decodedRootNode;
			}
		}
		// System.out.print(obsString.length() + " compression length: " +
		// obsString);

		Path path = Paths.get(decodeFile);
		byte[] aBytes = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++) {
			aBytes[i] = bytes.get(i);
		}
		try {
			Files.write(path, aBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // creates, overwrites

	}

	public static void deleteFile(String aFileName) {
		try {
			Path path = Paths.get(aFileName);
			Files.deleteIfExists(path);
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", aFileName);
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", aFileName);
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		FileUtils_6291.fileName = fileName;
	}
}
