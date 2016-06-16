import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
	private static String[][] dataBase;
	private static int[] recipeIndex;
	private static int[] missingRecipeIndex;
	private static String[] recipes;

	static int line = 0;

	public static void main(String[] args) {

		Gui frame = new Gui();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(500, 500);

		ReadIn read1 = new ReadIn();
		dataBase = read1.getData(); // 2D array of all ingredients
		recipes = read1.getRecipes(); // array of all recipes
		recipeIndex = new int[read1.getRecipes().length];// index of recipes with no missing ingredients
		missingRecipeIndex = new int[read1.getRecipes().length];// index of recipes with one missing ingredient

		for (int i = 0; i < recipeIndex.length; i++) {//must be set to -1, 0 is an index
			recipeIndex[i] = -1;
			missingRecipeIndex[i] = -1;
		}

		loadIngredients();

		int i = 0;
		while (line != dataBase.length) {// Find recipes with no missing ingredients
			int pos = searchRecipe(line);
			if (pos != -1) {
				System.out.println(dataBase[pos][0]);
				recipeIndex[i] = pos;
			}
			i++;
		}
		getRecipes(recipeIndex);

		line = 0;
		while (line != dataBase.length) {// Finds recipes with 1 ingredient missing
			int pos = suggestedRecipes(line);
			if (pos != -1) {
				System.out.println("suggested: ");
				System.out.println(dataBase[pos][0]);
				missingRecipeIndex[i] = pos;
			}
			i++;
		}
		getRecipes(missingRecipeIndex);
	}
	
	public static void loadIngredients() {
		Scanner in = new Scanner(System.in);
		System.out.println("Intredients: ");
		String csvFile = "Pantry.txt";
		BufferedReader br = null;
		String line = "";
		String[] sep = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			for (int i = 0; i < dataBase.length; i++) {
				if ((line = br.readLine()) != null) {
					// use comma as separator
					sep = line.split(",");
				}
				if(sep!=null) {
					for (int j = 0; j < sep.length; j++) {
							System.out.println(sep[j]);
					}
				}
			}
			System.out.println("Add Ingredient? y/n");
			if(in.next().equals("y")) {
				//br.close();
				addIngredients();
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addIngredients() { // Appends ingredients to a file
		Scanner in = new Scanner(System.in);
		String key = "";
		try {
			File file;
			file = new File("pantry.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);

			while (!key.equals("end")) {
				key = in.nextLine();
				// if file doesnt exists, then create it
				if (!file.exists())
					file.createNewFile();
				if (!key.equals("end")) {
					bw.append(key+",");
					//bw.append("\n");
				}
				searchIngredient(key);
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//in.close();
	}

	public static void searchIngredient(String ingredient) {// finds and sets all searched ingredients to null
		for (int i = 0; i < dataBase.length; i++) {
			for (int j = 0; j < dataBase[0].length; j++) {
				if (dataBase[i][j] != null && dataBase[i][j].equals(ingredient)) {
					dataBase[i][j] = null;
				}
			}
		}
	}

	public static int searchRecipe(int start) {// Returns i index of recipies with all null arrays
		for (int i = start; i < dataBase.length; i++) {
			line++;
			A: for (int j = 0; j < 19; j++) {
				if (dataBase[i][j + 1] != null)
					break A;
				if (j == 18)
					// return dataBase[i][0];
					return i; // Returns index of recipe you can make
			}
		}
		return -1;
	}

	public static int suggestedRecipes(int start) {// Finds all recipes with 1 missing ingredient
		int missingCnt = 0;// Number of missing ingredients
		String missingIng = "";// The missing ingredient
		for (int i = start; i < dataBase.length; i++) {
			line++;
			A: for (int j = 0; j < 19; j++) {
				if (dataBase[i][j + 1] != null) {
					missingCnt += 1;
					missingIng = dataBase[i][j + 1];
					if (missingCnt > 1) {
						missingCnt = 0;
						break A;
					}
				}
				if (j == 18 && missingCnt == 1) {
					System.out.println("missing " + missingIng);
					return i; // returns index of recipe with missing ingredient
				}
			}
		}
		return -1;
	}

	public static void getRecipes(int recipeIndex[]) { // prints the recipes at recipeIndex
		for (int i = 0; i < recipes.length; i++) {
			if (recipeIndex[i] != -1)
				System.out.println(recipes[recipeIndex[i]]);
		}
	}
}
