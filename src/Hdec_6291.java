//NICHOLAS, ADRIEN; CS610; PRP 1; 6291 

import java.io.IOException;

public class Hdec_6291 {
	public static void main(String... aArgs) throws IOException {
		String file = aArgs[0];
		FileUtils_6291.setFileName(file);
		FileUtils_6291.decodeFile();
		FileUtils_6291.deleteFile(file);
	}
}
