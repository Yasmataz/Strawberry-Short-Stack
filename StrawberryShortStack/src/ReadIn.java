import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class ReadIn {
	private static String[][] dataBase;
	private static String[] recipes;

		
	public ReadIn() {
	//public static void main(String[] args) {
		LineNumberReader lnr;
		try {
			lnr = new LineNumberReader(new FileReader("DataBase.txt"));
			lnr.skip(Long.MAX_VALUE);
			dataBase = new String[lnr.getLineNumber() + 1][20];
			recipes = new String[10];
			lnr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//readIngredient();
		//readRecipe();
		//	readCsv();
		//readRecipe();
		//readIngredient(0);
		
		//readCsvRecipe();
		readIngredient(0);
		readRafRecipe(0);
	}
	
	private static void readCsvIngredient() {//reads in books from text file
		String fileName = "DataBase.txt";
		BufferedReader br = null;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String[] ingredients = line.split(",");
				writeIngredients(ingredients);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeIngredients(String[] ingredients) {// Writes to binary RAF file
		String fileName = "InterSearch.txt";
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			raf.seek(raf.length());
			int ingLen = ingredients.length;
			
			int padIng = 0;
			if(ingLen>20)
				ingLen = 20;
			else
				padIng = 20-ingLen;
			for (int j = 0; j < ingredients.length; j++) {
				int nameLen = ingredients[j].length(); // determine if there are more than 20 characters
				int padLen = 0; // calculate the number of blanks that need to be
				if (nameLen > 20) // added to maintain a length of 20
					nameLen = 20;
				else
					padLen = 20 - nameLen;
				for (int i = 0; i < ingredients[j].length(); i++)
					raf.writeChar(ingredients[j].charAt(i));
				if (padLen > 0) { // write the extra blanks
					for (int i = 0; i < padLen; i++)
						raf.writeChar(' ');
				}
			}
			for (int j = 0; j < padIng; j++) {
					for (int i = 0; i < 20; i++)
						raf.writeChar('*');
				}
			raf.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

	}
	
	private static void readIngredient(int seek) {//Reads from RAF file
		String fileName = "pantryRaf.txt";
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			raf.seek(seek);
			String temp = "";
			
			for (int k = 0; k < dataBase.length; k++) {//when youre inefficient af
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 20; j++) {
						char nextChar = raf.readChar();
						if (nextChar != ('*')) {
							temp = temp + nextChar;
						}
					}
					//System.out.println(temp.trim());
					if(temp.trim().equals(""))
						dataBase[k][i] = null;
					else
						dataBase[k][i] = temp.trim();
					temp = "";
				}
			}
			System.out.println(Arrays.deepToString(dataBase));
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("erroe");
			e.printStackTrace();
		}

	}
	
	private static void readRafRecipe(int seek) {//Reads Raf file and saves to recipes array
		String fileName = "InstructionsRaf.txt";
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			raf.seek(seek);
			String temp = "";
			int index = 0;
			boolean nextRecipe = true; //have you reached the next recipe?
			for (int k = 0; k < raf.length()/2; k++) {
				char nextChar = raf.readChar();
				if (nextChar != ('*')) {
					nextRecipe = true;
					temp = temp + nextChar;
				}
				else{
					if(nextRecipe) {
						nextRecipe = false;
						recipes[index] = temp.trim();
						temp = "";
						index++;
					}
				}
			}
			System.out.println(Arrays.toString(recipes));
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("erroe");
			e.printStackTrace();
		}
	}

	
	private static void readCsvRecipe() {//reads in recipe from text file
		String fileName = "Instructions.txt";
		BufferedReader br = null;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String ingredients = line;
				writeRecipe(ingredients);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public static void writeRecipe(String instructions) {// Writes to binary RAF file
		String fileName = "InstructionsRAF.txt";
		try {
			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
			raf.seek(raf.length());
			int stringLength = instructions.length();
			
			int pading = 0;
			if(stringLength>500)
				stringLength = 500;
			else {
				pading = 500-stringLength;
				raf.writeChars(instructions);
			}
			
			for (int i = 0; i < pading; i++) {
					raf.writeChar('*');
			}
			
			raf.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

	}
	
	
	public String[][] getData(){
		return dataBase;
	}
	
	
	public String[] getRecipes(){
		return recipes;
	}
}
