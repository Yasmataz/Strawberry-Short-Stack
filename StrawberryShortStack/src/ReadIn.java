import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.*;
import java.util.Arrays;

public class ReadIn {
	private static String[][] dataBase;

	public ReadIn() {
		LineNumberReader lnr;
		try {
			lnr = new LineNumberReader(new FileReader("DataBase.txt"));
			lnr.skip(Long.MAX_VALUE);
			dataBase = new String[lnr.getLineNumber() + 1][20];
			lnr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		read();
	}

	private void read() {
		String csvFile = "DataBase.txt";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] sep = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			for (int i = 0; i < dataBase.length; i++) {
				
				if ((line = br.readLine()) != null) {
					// use comma as separator
					sep = line.split(cvsSplitBy);
					dataBase[i][0] = sep[0];
					System.out.println(Arrays.toString(sep));

				}

				for (int j = 0; j < dataBase[0].length; j++) {
					try {
					dataBase[i][j+1] = sep[j+1];
					} catch(IndexOutOfBoundsException e) {
						if(j<19)
							dataBase[i][j+1] = null;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	System.out.println(Arrays.deepToString(dataBase));
	}
	
	public String[][] getData(){
		return dataBase;
	}
}
