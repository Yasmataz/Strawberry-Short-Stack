import java.util.LinkedList;

public class Interfacing {
	private static LinkedList<String> shoppingList = new LinkedList<String>();
	private static LinkedList<String> recipeList = new LinkedList<String>();
	private static LinkedList<String> recipeInstrucitionList = new LinkedList<String>();
	public Interfacing() {
		
	}
	
	public void addToShoppingList(String ingredient) {
		shoppingList.add(ingredient);
	}
	
	public void addToRecipeList(String recipe) {
		recipeList.add(recipe);
	}
	
	public void addToRecipeInstrucitionList(String instructions) {
		recipeInstrucitionList.add(instructions);
	}
}
