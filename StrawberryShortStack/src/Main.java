import java.util.Arrays;
import java.util.Scanner;

public class Main {
	private static String[][] dataBase;
	private static int[] recipeIndex;
	private static String[] recipes;

	static int line = 0;
	
	public static void main(String[] args) {
		ReadIn read1 = new ReadIn();
		dataBase = read1.getData();
		recipes = read1.getRecipes();
		Scanner in = new Scanner(System.in);
		recipeIndex = new int[read1.getRecipes().length];
		
		for (int i = 0; i < recipeIndex.length; i++) {
			recipeIndex[i] = -1;
		}
		
		String key = "";
		while(!key.equals("end")) {
			key = in.nextLine();
			searchIngredient(key);
		}
		int i = 0;
		while(line!=dataBase.length){
			int pos = searchRecipe(line);
			if (pos!=-1) {
				System.out.println(dataBase[pos][0]);
				recipeIndex[i] = pos;
			}
			i++;
		}
		getRecipes();
		
		line=0;
		while(line!=dataBase.length){
			int pos = suggestedRecipes(line);
			if (pos!=-1) {
				System.out.println("suggested: ");
				System.out.println(dataBase[pos][0]);
				//recipeIndex[i] = pos;
			}
			i++;
		}
	in.close();
	}

	public static void searchIngredient(String ingredient) {//finds and sets all searched ingredients to null
		for (int i = 0; i < dataBase.length; i++) {
			for (int j = 0; j < dataBase[0].length; j++) {
				if(dataBase[i][j] != null && dataBase[i][j].equals(ingredient)) {
					dataBase[i][j] = null;
				}
			}
		}
	}
	
	public static int searchRecipe(int start) {//Returns i index of recipies with all null arrays
		for (int i = start; i < dataBase.length; i++) {
			line++;
			A:for (int j = 0; j < 19; j++) {
				if(dataBase[i][j+1] != null)
					break A;
				if(j == 18)
					//return dataBase[i][0];
					return i;
			}
		}
		return -1;
	}
	
	public static int suggestedRecipes(int start) {
		int missingCnt = 0;
		for (int i = start; i < dataBase.length; i++) {
			line++;
			A:for (int j = 0; j < 19; j++) {
				if(dataBase[i][j+1] != null) {
					missingCnt+=1;
					if(missingCnt >1) {
						missingCnt=0;
						break A;
					}
				}
				if(j == 18 && missingCnt ==1)
					//return dataBase[i][0];
					return i;
			}
		}
		return -1;
	}
	
	public static void getRecipes() {
		for (int i = 0; i < recipes.length; i++) {
			if(recipeIndex[i]!= -1)
				System.out.println(recipes[recipeIndex[i]]);
		}
	}
}
