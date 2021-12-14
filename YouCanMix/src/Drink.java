
public class Drink {
    private String drinkName;
    private String ingredients;
    private String quantities;
    private int rating;
    
    //DEFAULT CONSTRUCTOR
    public Drink() {
    	super();
    	this.drinkName = "";
        this.ingredients = "";
        this.quantities = "";
        this.rating = 0;
    }

    //CONSTRUCTOR
    public Drink(String drinkName, String ingredients, String quantities, int rate) {
        if (drinkName != null && ingredients != null && quantities != null)
        {
            this.drinkName = drinkName;
            this.ingredients = ingredients;
            this.quantities = quantities;
            this.rating = rate;
        }
        
    }
    
    public String getDrinkName() {
    	return this.drinkName;
    }
    public String getIngredients() {
    	return this.ingredients;
    }
    public String getQuantities() {
    	return this.quantities;
    }
    public int getRating() {
    	return this.rating;
    }

}
