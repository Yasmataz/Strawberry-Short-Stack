import java.util.Arrays;
import java.util.Scanner;

public class Main {
	private static String[][] dataBase;

	public static void main(String[] args) {
		ReadIn read1 = new ReadIn();
		dataBase = read1.getData();
		searchRecipe();
		Scanner in = new Scanner(System.in);
		
		String key = "";
		while(!key.equals("end")) {
			key = in.nextLine();
			searchIngredient(key);
		}
		System.out.println(searchRecipe());
	}

	public static void searchIngredient(String ingredient) {
		for (int i = 0; i < dataBase.length; i++) {
			for (int j = 0; j < dataBase[0].length; j++) {
				if(dataBase[i][j] != null && dataBase[i][j].equals(ingredient)) {
					dataBase[i][j] = null;
				}
			}
		}
	}
	
	public static String searchRecipe() {
		for (int i = 0; i < dataBase.length; i++) {
			A:for (int j = 0; j < 19; j++) {
				if(dataBase[i][j+1] != null)
					break A;
				if(j == 18)
					return dataBase[i][0];
			}
		}
		return null;
	}
}
