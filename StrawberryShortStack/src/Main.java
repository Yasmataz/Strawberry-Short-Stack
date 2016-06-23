import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
	private static String[][] dataBase;
	private static int[] recipeIndex;
	private static int[] missingRecipeIndex;
	private static String[] recipes;
	private static String[] pantry;
	private static String missingIng;
	private static ReadIn read1;
	private static Interfacing interfacing;
	private static LinkedList<String> shoppingList = new LinkedList<String>();
	static int line = 0;

	public static void main(String[] args) {
		read1 = new ReadIn();
		interfacing = new Interfacing();
		dataBase = read1.getData(); // 2D array of all ingredients
		recipes = read1.getRecipes(); // array of all recipes
		recipeIndex = new int[read1.getRecipes().length];// index of recipes with no missing ingredients
		missingRecipeIndex = new int[read1.getRecipes().length];// index of recipes with one missing ingredient

		for (int i = 0; i < recipeIndex.length; i++) {//must be set to -1, 0 is an index
			recipeIndex[i] = -1;
			missingRecipeIndex[i] = -1;
		}

		loadIngredients(); // Add ingredients
		
		for (int i = 0; i < pantry.length; i++) {// sets all ingredients you have to null
			searchIngredient(pantry[i]);
		}
		
		int i = 0;
		while (line != dataBase.length) {// Find recipes with no missing ingredients
			int pos = searchRecipe(line);
			if (pos != -1) {
				System.out.println(dataBase[pos][0]);
				recipeIndex[i] = pos;
			}
			i++;
		}
		getRecipes(recipeIndex); //prints all recipes in the array

		line = 0;
		Scanner in = new Scanner(System.in);
		while (line != dataBase.length) {// Finds recipes with 1 ingredient missing
			int pos = suggestedRecipes(line);
			if (pos != -1) {
				System.out.println("suggested: ");
				System.out.println(dataBase[pos][0]);
				System.out.println("Missing Ingredient: " + missingIng);
				missingRecipeIndex[i] = pos;
				
				System.out.println("Add ingredient to shopping list? y/n");
				if (in.nextLine().equals("y")) {
					shoppingList.add(missingIng);
				}
			}
			i++;
		}
		getRecipes(missingRecipeIndex); //prints all recipes in the array
		viewShoppingList();
	}
	
	/*
	 * @params nothing
	 * @return nothing
	 * Determines if user wants to add/remove ingredients from pantry
	 * reads in pantry file
	 */
	public static void loadIngredients() {
		Scanner in = new Scanner(System.in);
		System.out.println("Intredients: ");
		String csvFile = "Pantry.txt";
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				pantry = line.split(",");
				if(pantry!=null) {
					for (int j = 0; j < pantry.length; j++) {
							System.out.println(pantry[j]);
					}
				}
			}
			System.out.println("Add Ingredient? y/n");
			if(in.next().equals("y")) {
				//br.close();
				addIngredients();
			}	
			else {
				System.out.println("Remove Ingredient? y/n");
				if(in.next().equals("y"))
					removeIngredient();
			}
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Appends ingredients to pantry file
	 * updates arrays
	 * @exception IOException On input error.
	 * @see IOException
	 */
	public static void addIngredients() { 
		Scanner in = new Scanner(System.in);
		String key = "";
		try {
			File file;
			file = new File("pantry.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);

			while (!key.equals("end")) {
				System.out.println("enter Ingredient ");
				key = in.nextLine();
				
				if (!file.exists()) // if file doesnt exists, then create it
					file.createNewFile();
				if (!key.equals("end")) {
					bw.append(key+",");
					for (int i = 0; i < pantry.length; i++) {
						if(pantry[i] == null)
							pantry[i] = key;
					}
				}
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @params ingredient
	 * @return nothing
	 * finds and sets all searched ingredients to null
	 */
	public static void searchIngredient(String ingredient) { 
		for (int i = 0; i < dataBase.length; i++) {
			for (int j = 0; j < dataBase[0].length; j++) {
				if (dataBase[i][j] != null && dataBase[i][j].equals(ingredient)) {
					dataBase[i][j] = null;
				}
			}
		}
	}

	/*
	 * @params start
	 * @return int
	 * Returns i index of recipies with all null arrays
	 */
	public static int searchRecipe(int start) {
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

	/*
	 * @params start
	 * @return int
	 * Returns i index of recipies with 1 missing ingredient
	 */
	public static int suggestedRecipes(int start) {
		int missingCnt = 0;// Number of missing ingredients
		missingIng = "";// The missing ingredient
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
					interfacing.addToShoppingList(missingIng);
					return i; // returns index of recipe with missing ingredient
				}
			}
		}
		return -1;
	}
	
	/*
	 * @params recipeIndex[]
	 * @return nothing
	 * prints the recipes at recipeIndex
	 */
	public static void getRecipes(int recipeIndex[]) { 
		for (int i = 0; i < recipes.length; i++) {
			if (recipeIndex[i] != -1)
				System.out.println(recipes[recipeIndex[i]]);
		}
	}

	/*
	 * @params nothing
	 * @return nothing
	 * adds ingredients to write to file to an array, and calls write method
	 */
	public static void addRecipeIngredients() {
		String[] ingredients = new String[20];
		Scanner in = new Scanner(System.in);
		for (int i = 0; i < ingredients.length; i++) {
			String word = in.nextLine();
			if(!word.equals("end")) {
				ingredients[i] = in.nextLine();
			}
			else {
				for (int j = 0; j < ingredients.length-i; j++) {
					ingredients[i+j] = null;
				}
			}
		}
		read1.writeIngredients(ingredients);
		addRecipeInstructions();
	}

	/*
	 * @params nothing
	 * @return nothing
	 * Searches for recipe based on search name
	 */
	public static void searchRecipe() {
		Scanner in = new Scanner(System.in);
		String search = in.nextLine();
		for (int i = 0; i < dataBase.length; i++) {
			if(dataBase[i][0].equals(search)) {
				System.out.println(recipes[i]);
				System.out.println("Remove recipe? y/n");
				if(in.nextLine().equals("y")) {
					for (int j = 0; j < dataBase[0].length; j++) {
						dataBase[i][j] = null;
					}
					recipes[i] = null;
					read1.removeRecipe(i);
				}
			}
		}
	}
	
	/*
	 * @params nothing
	 * @return nothing
	 * send instructions to be written to write method
	 */
	public static void addRecipeInstructions() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter instructions");
		String instructions = in.nextLine();
		read1.writeRecipe(instructions);
	}
	
	/*
	 * @params nothing
	 * @return nothing
	 * Searches for index to be removed and sends to remove method
	 */
	public static void removeIngredient() {
		System.out.println("Remove ingredient");
		Scanner in = new Scanner(System.in);
		String toRemove = in.nextLine();
		int index = -1;
		for (int i = 0; i < pantry.length; i++) {
			if(pantry[i].equals(toRemove)) {
				index = i;
				pantry[i] = null;
			}
		}
		
		//Rewrite the file withhout ingredient being removed
		PrintWriter writer;
		try {
			File file;
			file = new File("pantry.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			fw.write("");
			
			for (int i = 0; i < pantry.length; i++) {
				// if file doesnt exists, then create it
				if (!file.exists())
					file.createNewFile();
				if (pantry[i] != null) {
					bw.append(pantry[i]+",");
				}
			}
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @params nothing
	 * @return nothing
	 * reads through shopping list
	 */
	public static void viewShoppingList() {
		ListIterator<String> listIterator = shoppingList.listIterator();
		 while (listIterator.hasNext()) {
	            System.out.println(listIterator.next());
	        }
	}
	
	/*
	 * @params nothing
	 * @return nothing
	 * removes item from shopping list
	 */
	public static void removeFromShoppingList(String toRemove) {
		shoppingList.remove(toRemove);
	}
}
