//NICHOLAS, ADRIEN; CS610; PRP 1; 6291 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Henc_6291 {

	public static void main(String... aArgs) throws IOException {
		String file = aArgs[0];
		FileUtils_6291.setFileName(file);
		byte[] byteArray = FileUtils_6291.readBinaryFile(file);

		if (byteArray != null && byteArray.length > 0) {
			List<Node_6291> histogram = FileUtils_6291.bitCount(byteArray);
			Node_6291 rootNode = FileUtils_6291.buildMinHeap(histogram);

			List<Prefix_6291> prefixes = new ArrayList<Prefix_6291>();
			FileUtils_6291.generatePrefix(rootNode, new String[256], 0, prefixes);
			FileUtils_6291.writeEncodedFile(byteArray, rootNode, prefixes);
			FileUtils_6291.deleteFile(file);
		} else {
			FileUtils_6291.writeEmptyEncodedFile();
		}
	}
}