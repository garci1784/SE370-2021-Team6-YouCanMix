
import JDBC.JDBCUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.*;
//import java.util.*;
import java.sql.*;



public class GUI extends JFrame{
	
	
	
	
	
	private Drink currentDrink;
	private JFrame catalogueFrame = new JFrame();
	private JFrame displayDrinkFrame = new JFrame();
	private JFrame searchDrinkFrame = new JFrame();
	protected JPanel Rating;
	protected JPanel search;

	private String Parameter = "*";
	private boolean bk = true;
	private boolean active = true;
	private int clicks = 0;
	private int select;
	
	private int num_Drink = 100;
	private int currentSize = 0;
	
	private String[] currentDrinkNames = new String[num_Drink];
	private String[] currentIngredients = new String[num_Drink];
	private String[] currentQuantities = new String[num_Drink];
	private int[] currentRatings = new int[num_Drink];
	
	private String[] currentIngredient = new String[num_Drink];
    private String[] currentQuantity = new String[num_Drink];
	private int[] currentRating = new int[num_Drink];

	//private JToolBar ratingTool = new JToolBar();
	

	private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private JTable drinkTable = new JTable(defaultTableModel);
    
	
	private int x = 1; //ingredient counter
	protected String FullIngredient = "";//Holds all the ingredients
	protected String FullQuantity = "";  //quantities
	
	//text fields to search/ enter info
    private JTextField findTextField = new JTextField(30);
    private JTextField textCocktailName = new JTextField(30);
    
    //TEXT FIELDS FOR DIFFERENT INGREDIENTS/QUANTITIES
    private JTextField tIngredient = new JTextField(30);
    private JTextField tQuantity = new JTextField(30);

    
    // create a new buttons
    private	JButton Yes = new JButton("Yes");
    private	JButton No = new JButton("No");
 	private	JButton Menu = new JButton("Main Menu");
 	private	JButton Exit = new JButton("Exit");
 	private	JButton View = new JButton("View Drinks");
 	private	JButton Search = new JButton("Search For Drinks");
 	private	JButton Rate = new JButton("Rate Drinks");
 	private	JButton Create = new JButton("Create a Drink");
 	private	JButton findButton = new JButton("Search");
 	private	JButton Back = new JButton("Back");

 	
 	//ADD DRINK BUTTONS
 	private JButton Enter = new JButton("Enter");
    private JButton moreIngredients = new JButton("More Ingredients");
    
    
	private Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Josef\\Desktop\\YouCanMixFolder\\icon.jpg");    
	private Image star = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Josef\\Desktop\\YouCanMixFolder\\Star.jpg");    
	private Image NoStar = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Josef\\Desktop\\YouCanMixFolder\\NoStar.jpg");    
	
	
 	
	public GUI() {
		// displays YouCanMix title on every window
		super("YouCanMix");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setIconImage(icon);


		
		//Sets actions for each button
		Yes.addActionListener(new ActionListener() {//	WHEN YES BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				BuildTable();
				mainMenu();
			}
		}); 
		No.addActionListener(new ActionListener() {//	WHEN NO BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		}); 
		Menu.addActionListener(new ActionListener() {//	WHEN MENU BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				catalogueFrame.removeAll();
				catalogueFrame.setVisible(false);
				searchDrinkFrame.removeAll();
				searchDrinkFrame.setVisible(false);
				deleteAllRows();
				mainMenu();
			}
		}); 
		Exit.addActionListener(new ActionListener() {//	WHEN EXIT BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		Search.addActionListener(new ActionListener() {//	WHEN SEARCH FOR DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				bk = false;
				searchDrinks();
			}
		});
	
	
		View.addActionListener(new ActionListener() {//	WHEN VIEW DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					generateTable(Parameter);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				bk = true;
				drinkCatalogue();
			}
		});	
		ListSelectionModel click = drinkTable.getSelectionModel();
		click.addListSelectionListener(new ListSelectionListener() {// WHEN AN ROW IS SELECTED
			@Override
	        public void valueChanged(ListSelectionEvent le) {
				if(active) {
					catalogueFrame.removeAll();
					catalogueFrame.setVisible(false);
					searchDrinkFrame.removeAll();
					searchDrinkFrame.setVisible(false);

					select = click.getMinSelectionIndex();
					System.out.println("Selection: " + select);
					if(select >= 0 && select < 100)viewDrink(select);
				}
	        }
	    });
		Back.addActionListener(new ActionListener() {//	WHEN BACK BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				displayDrinkFrame.removeAll();
				displayDrinkFrame.setVisible(false);				
				//System.out.println("Selection: " + currentIngredient + "/" + currentQuantity);
				if(bk == true) drinkCatalogue();
				else {
					deleteAllRows();
					searchDrinks();
				}
				Parameter = "*";
			}
		});
		
		
		
		findButton.addActionListener(new ActionListener() {//	WHEN SEARCH BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteAllRows();
				searchDrinkFrame.removeAll();
				searchDrinkFrame.setVisible(false);
				Parameter = findTextField.getText();
				try {
					generateTable(Parameter);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				findTextField.setText(null);
				searchDrinks();
				Parameter = "*";
			}
		}); 
		Rate.addActionListener(new ActionListener() {//	WHEN RATE DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				rateDrinks();
			}
		});
		
		
		
		//CREATE DRINKS FUNCTION CALLS
		Create.addActionListener(new ActionListener() {//	WHEN CREATE DRINKS BUTTON IS PRESSED
			@Override
			public void actionPerformed(ActionEvent ae) {
				createDrinks();
			}
		}); 
		moreIngredients.addActionListener(new ActionListener() {//WHEN MORE INGREDIENTS BUTTON IS PRESSED
	        @Override
	        public void actionPerformed(ActionEvent event) {
	    		x++;
	    		FullIngredient += tIngredient.getText() + ", ";
	    		FullQuantity += tQuantity.getText() + ", ";
	    		tIngredient.setText(null);//clears out ingredient and quantity text box
		        tQuantity.setText(null);
	    		if(x < 10)createDrinks();
	    		else Error();
	        }
	    });
		Enter.addActionListener(new ActionListener() {//WHEN ENTER BUTTON IS PRESSED
	        @Override
	        public void actionPerformed(ActionEvent event) {
	        	FullIngredient += tIngredient.getText(); 
	        	FullQuantity += tQuantity.getText();
		        try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					System.out.println("Test2: " + FullIngredient + " : " + FullQuantity);
					currentDrink = new Drink(textCocktailName.getText(), FullIngredient, FullQuantity );
					currentDrink.insertDrink();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		        
		        //clears out ingredient, quantity and cocktail name text box
		        tIngredient.setText(null);
		        tQuantity.setText(null);
		        textCocktailName.setText(null);
		        
		        //clear out FullIngredient, FullQuantity and resets x counter
		        FullIngredient = "";
		        FullQuantity = "";
				x = 1;
				mainMenu();
	        }
	    });
		
		//Displays the verify screen to start
		Verify();
		
	}
	
	public void Verify() {
		
		JPanel verify = new JPanel(new GridBagLayout());	//panel to display menu
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        
        JLabel age = new JLabel("Are You 21 Year's of Age or Older?");
        
        constraints.gridx = 0;
        constraints.gridy = 0;  
        verify.add(age, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        verify.add(Yes, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        verify.add(No, constraints);
        
        // set border for the panel
        verify.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Verify"));
         
        // add the panel to this frame
        add(verify);
         
        pack();
        setLocationRelativeTo(null);
        		
	}
	
	//interface for the main menu
	public void mainMenu() {	
		
		getContentPane().removeAll();//clears frame
		System.out.println("IN MAIN MENU FUNCTION");

		//Main menu
		
		JPanel menu = new JPanel(new GridBagLayout());	//panel to display menu
			
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
         
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        menu.add(View, constraints);
 
        constraints.gridx = 1;
        menu.add(Search, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        menu.add(Rate, constraints);
         
        constraints.gridx = 1;
        menu.add(Create, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        menu.add(Exit, constraints);
         
        // set border for the panel
        menu.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Main Menu"));
         
        // add the panel to this frame
        add(menu);
        
		setVisible(true);
        pack();
        setLocationRelativeTo(null);
	}
	
	//interface to display all the drinks
	public void drinkCatalogue(){ 
		
		getContentPane().removeAll();//clears frame
		setVisible(false);

		
		//View Drinks
		catalogueFrame = new JFrame();
		catalogueFrame.setLayout(new FlowLayout());
		catalogueFrame.setIconImage(icon);
		catalogueFrame.setTitle("Drink Catalogue");


		
		JPanel catalogue = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		catalogue.add(new JScrollPane(drinkTable));
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
       
        
        constraints.gridx = 0;
        constraints.gridy = 2; 
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        catalogue.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2; 
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        catalogue.add(Exit, constraints);
        
        catalogueFrame.setSize(800, (currentSize*75));
        
        // add the panel to this frame
        catalogueFrame.add(catalogue);
         
        
        catalogueFrame.pack();
        catalogueFrame.setLocationRelativeTo(null);
        catalogueFrame.setVisible(true);
        catalogueFrame.validate();
	}
	
	//interface to allow user to search for drinks
	public void searchDrinks() { 
		
		getContentPane().removeAll();//clears frame
		setVisible(false);


		//Search for drinks
		
		searchDrinkFrame = new JFrame();
		searchDrinkFrame.setLayout(new FlowLayout());
		searchDrinkFrame.setIconImage(icon);
		searchDrinkFrame.setTitle("YouCanMix");
			
		JPanel searching = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		searchBar();
		
		constraints.gridx = 0;
        constraints.gridy = 0;
        searching.add(search, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 1;
		if(Parameter != "*") {
			searching.add(new JScrollPane(drinkTable), constraints);
		}
		else {
			searching.setBorder(BorderFactory.createTitledBorder(
	                BorderFactory.createEtchedBorder(), "Search For Drinks"));
		}
		
		//add menu and exit button to panel
		constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        searching.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        searching.add(Exit, constraints);
			
        searchDrinkFrame.setSize(800, (currentSize*75));
        
        // add the panel to this frame
        searchDrinkFrame.add(searching);
         
        
        searchDrinkFrame.pack();
        searchDrinkFrame.setLocationRelativeTo(null);
        searchDrinkFrame.setVisible(true);
        searchDrinkFrame.validate();
		
	}
	
	//interface for user to rate drinks
	public void rateDrinks() { 
	
		getContentPane().removeAll();//clears frame
		
		//Rate Drinks
		
		JPanel rating = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		searchBar();// either returns value or the list of drinks **tbd
		
		//add list of drinks here*********
		//placement on panel to be updated
		constraints.gridx = 0;
        constraints.gridy = 2;
        rating.add(Search, constraints);
		
		
		//add menu and exit button to panel
		constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        rating.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        rating.add(Exit, constraints);
			
     // set border for the panel
        rating.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Rate Drinks"));
         
        // add the panel to this frame
        add(rating);
         
        pack();
        setLocationRelativeTo(null);
	}
	
	
	public void generateTable(String para) throws ClassNotFoundException {
		
       
       System.out.println("Test: " + para);
       
		try {
	        Connection conn = JDBCUtil.getConnection();
	        ResultSet rs;
	    	
	    	Statement stmt = conn.createStatement();
	    	if(para == "*") {
	    		rs = stmt.executeQuery("SELECT * FROM Drinks");
	    	}
	    	else {
	    		rs = stmt.executeQuery("SELECT * FROM Drinks WHERE Ingredients LIKE \"%" + para +
	    				"%\" or Drink_Name LIKE \"%" + para +"%\"");
	    	}
	    	
	    	while ( rs.next() ) {
	    		//gets drink name and ingredients and add them to table display
	            String drinkName = rs.getString("Drink_Name");
	            String ingredients = rs.getString("Ingredients");
	            defaultTableModel.addRow(new Object[]{drinkName, ingredients});
	            
	            //get quantities, rating and adds everything to respective list
	            String quantity = rs.getString("Quantity");
	            int rating = rs.getInt("Rating");
	            
	            currentDrinkNames[currentSize] = drinkName;
	            currentIngredients[currentSize] = ingredients;
	            currentQuantities[currentSize] = quantity;
	            currentRatings[currentSize] = rating;
	            currentSize++;
	            
	        }
	        conn.close();
	    	
	    	}
	    	catch (SQLException e) {
	    		e.printStackTrace();
	    	}
		
		
		
	}

	
	
	
	//interface for user to create a drink
	public void createDrinks() { 
		
		getContentPane().removeAll();//clears frame
		//currentDrink = new Drink();
		//System.out.println("Test1: " + FullIngredient + " : " + FullQuantity);
		//Create Drinks
		
				
		JPanel creating = new JPanel(new GridBagLayout());//panel to prompt user entry		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		//Display prompt for cocktail name
		JLabel cocktailName = new JLabel("Cocktail Name:");

		//Display prompt for ingredients
	    JLabel ingredient = new JLabel("Enter Ingredient " + x + ": ");
	    JLabel quantity = new JLabel("Enter Ingredient " + x + "\'s Quantity: ");
	    
	    
	 // initial components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        creating.add(cocktailName, constraints); //LABEL FOR NAME OF DRINK
 
        constraints.gridx = 1;
        creating.add(textCocktailName, constraints); //TEXT BOX FOR NAME OF DRINK
     
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        creating.add(ingredient, constraints); //LABEL FOR INGREDIENT X
         
        constraints.gridx = 1;
        creating.add(tIngredient, constraints);//TEXT BOX FOR INGREDIENTS
        
        constraints.gridx = 0;
        constraints.gridy = 2;     
        creating.add(quantity, constraints); //LABEL FOR QUANTITIES
        
        constraints.gridx = 1;
        creating.add(tQuantity, constraints); //TEXT BOX FOR QUANTITIES
        
        constraints.gridx = 2;
        creating.add(moreIngredients, constraints); //ALLOWS USER TO ADD MORE INGREDIENTS
          	
        
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        creating.add(Enter, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        creating.add(Menu, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        creating.add(Exit, constraints);
         
        // set border for the panel
        creating.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Create a Drink"));
         
        // add the panel to this frame
        add(creating);
         
        pack();
        setLocationRelativeTo(null);
	 
	}
	
	//function that displays search bar and searches for the drink
	public void searchBar() {
		search = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		JLabel findLabel = new JLabel("Search:");
	    
	    constraints.gridx = 0;
        constraints.gridy = 0;     
        search.add(findLabel, constraints);
 
        constraints.gridx = 1;
        search.add(findTextField, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.EAST;
        search.add(findButton, constraints);
	 
	}
	
	public void viewDrink(int s) {
		
		System.out.println("IN VIEW DRINK FUNCTION");
		getContentPane().removeAll();//clears frame
		//setVisible(true);
		
		displayDrinkFrame = new JFrame();
		displayDrinkFrame.setLayout(new FlowLayout());
		displayDrinkFrame.setIconImage(icon);
		displayDrinkFrame.setTitle("Drink Recipe");

		
		JPanel displayCocktail = new JPanel(new GridBagLayout());//panel to display list of drinks
		
		GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 2, 10, 2);       
        
        currentIngredient = currentIngredients[s].split(",");
        currentQuantity = currentQuantities[s].split(",");
        
        System.out.print("Test1: ");
        for(int y = 0; y < 6; y++) {
        	System.out.println(currentDrinkNames[y]);
        }
        
     
       
        JLabel cocktailName = new JLabel("Cocktail Name: " + currentDrinkNames[s]);
        

        constraints.gridx = 0;
        constraints.gridy = 0;
        displayCocktail.add(cocktailName, constraints); //DISPLAYS NAME OF DRINK
        
        

        int p = 1;
        
        while( p <= currentIngredient.length ) {
        	//Display ingredients and quantities
        	JLabel ingredient = new JLabel("Ingredient " + p + ": " + currentIngredient[p-1]);
        	JLabel quantity = new JLabel("Quantity: " + currentQuantity[p-1]);
        			//+ "  " + 
        	//JLabel quantity = new JLabel();
        	//JLabel xIngredient = new JLabel(currentIngredient[p-1]);
        	//xQuantity = new JLabel(currentQuantity[p-1]);
        	

        	constraints.gridx = 0;
            constraints.gridy = p; 

            displayCocktail.add(ingredient, constraints); //LABEL FOR INGREDIENT X
                 
            constraints.gridx = 1;
            constraints.gridy = p;

            displayCocktail.add(quantity, constraints); //LABEL FOR QUANTITIY X
            
            
      	    p++;
        }
        
        ratingGUI();
        
        
        constraints.gridx = 0;
        constraints.gridy = p;
        constraints.gridwidth = 10;
        displayCocktail.add(Rating, constraints);

        
        //add menu and exit button to panel
        constraints.gridx = 0;
        constraints.gridy = p + 1;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.WEST;
        displayCocktail.add(Back, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = p + 1 ;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.EAST;
        displayCocktail.add(Exit, constraints);
      			
        
        displayDrinkFrame.setSize(600, (p*85));
               	
        // add the panel to this frame
        displayDrinkFrame.add(displayCocktail);
        
        
        displayDrinkFrame.pack();
        displayDrinkFrame.setLocationRelativeTo(null);
        displayDrinkFrame.setVisible(true);
        displayDrinkFrame.validate();
      		
		
	}
	
	public void ratingGUI() {
		//JLabel Rating1 = new JLabel();
		Rating = new JPanel(new GridBagLayout());//panel to display list of drinks
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
		
		JLabel title = new JLabel("Rating: ");
        constraints.gridx = 0;
        Rating.add(title);

		
		JLabel Rating1 = new JLabel();
        Rating1.setIcon(new ImageIcon(star));
        constraints.gridx = 1;
        Rating.add(Rating1);
        
        JLabel Rating2 = new JLabel();
        Rating2.setIcon(new ImageIcon(star));
        constraints.gridx = 2;
        Rating.add(Rating2);
        
        JLabel Rating3 = new JLabel();
        Rating3.setIcon(new ImageIcon(star));
        constraints.gridx = 3;
        Rating.add(Rating3);
        
        JLabel Rating4 = new JLabel();
        Rating4.setIcon(new ImageIcon(NoStar));
        constraints.gridx = 4;
        Rating.add(Rating4);
        
        JLabel Rating5 = new JLabel();
        Rating5.setIcon(new ImageIcon(NoStar));
        constraints.gridx = 5;
        Rating.add(Rating5);
        

	
	}
	
	public void BuildTable() {
		
		//makes Table to Display all the drinks
	       drinkTable.setPreferredScrollableViewportSize(new Dimension(600, 200));
	       drinkTable.setFillsViewportHeight(true);
	       defaultTableModel.addColumn("Drink Name");
	       defaultTableModel.addColumn("Ingredients");    
	       
	       //sets the width of each column
	       TableColumnModel colModel = drinkTable.getColumnModel();
	       TableColumn col = colModel.getColumn(0);
	       col.setPreferredWidth(200);
	       col = colModel.getColumn(1);
	       col.setPreferredWidth(200);
	}
	
	public void deleteAllRows() {
		active = false;
		for(int g = currentIngredient.length-1; g >= 0; g--) {
			currentIngredient[g] = "";
			currentQuantity[g] = "";
		}
		for(int t = currentDrinkNames.length-1; t >= 0; t--) {
			currentDrinkNames[t] = "";
	        currentIngredients[t] = "";
	        currentQuantities[t] = "";
	        //currentRatings[t] = "";
		}
		while(defaultTableModel.getRowCount() > 0) {
			defaultTableModel.removeRow(0);
		}
		currentSize = 0;
		active = true;
	}

	
	public void Error() {
		JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "Can't Add Anymore Ingredients!", "ERROR", JOptionPane.ERROR_MESSAGE);	
	}
	
	
	
}
