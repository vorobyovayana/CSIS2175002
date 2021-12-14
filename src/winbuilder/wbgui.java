package winbuilder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class wbgui extends JFrame {

	private JPanel contentPane;
	private JList MenuList;
	private JList OrderList;
	private JTextField txtName;
	private JLabel lblName;
	private static JRadioButton rdbtnTake;
	private static JRadioButton rdbtnDine;
	private JButton btnCheckout;
	private JButton btnAdd;
	// An array of pizzas from the DB
	static Pizza[] pizzas = new Pizza[10000];
	// An array of pizzas selected by user
	private String[] chosenPizzas = new String[10000];
	// Number of pizzas in pizzas array
	private static int pizzaCount = 0;
	// Number of pizzas in chosenPizzas array
	private static int newPizzaCount = 0;
	// Total amount charged
	private double Total;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					wbgui frame = new wbgui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// This method gets the pizzas from the DB and write them to the pizzas array
	public static void getPizzas() {
	Scanner input = new Scanner(System.in);	
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null; 
		String msAccDB = "/Users/yanavorobeva/Downloads/menu.accdb";
		String dbURL ="jdbc:ucanaccess://" + msAccDB;
			
				try {
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
	
				try {
					connection = DriverManager.getConnection(dbURL);			
					statement = connection.createStatement();
					String sqlStr = "SELECT * FROM Menu";
					resultSet = statement.executeQuery(sqlStr);
					while(resultSet.next()) {
						String name = resultSet.getString(2);					
						double price = resultSet.getDouble(3);
						Pizza pizza = new Pizza(name, price);
						System.out.println(name+price);
						pizzas[pizzaCount]= pizza;
						pizzaCount++;
				}
					}
					catch(SQLException e) {
					e.printStackTrace();
					
				} finally {				
					try {
						if(connection != null) {
							resultSet.close();
							statement.close();
							connection.close();
						}
					} catch(SQLException e) {
						
					}								
				}
	}
	
	// This method checks what radio button is selected and returns the text value of the selected one
	public static String getDiningOpt() {
		if (rdbtnTake.isSelected()) {
            return rdbtnTake.getText();
        }
		else if (rdbtnDine.isSelected()) {
			return rdbtnDine.getText();
		}
		else {
			return null;
		}

	}
	
	// This method sets up the components of the JFrame
	public void setupComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// Create list model and fill it with pizzas
		DefaultListModel<String> menuList = new DefaultListModel<>();
		
		for(int i = 0; i< pizzaCount; i++) {
			menuList.addElement(pizzas[i].getpName()+" Unit price: "+pizzas[i].getPrice());
		}
		MenuList = new JList(menuList);
		
		// Initialize chosenPizzas array
		chosenPizzas = new String[pizzaCount];
		
		btnAdd = new JButton("Add");		
		
		rdbtnTake = new JRadioButton("Take out");
		rdbtnDine = new JRadioButton("Dine in");
		
		// add radiobuttons to the group
		ButtonGroup G = new ButtonGroup();
		G.add(rdbtnTake);
		G.add(rdbtnDine);
		
		OrderList = new JList();
		txtName = new JTextField();
		txtName.setColumns(10);
		
		lblName = new JLabel("Name: ");
		btnCheckout = new JButton("Check out");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblName)
							.addGap(121)
							.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(103)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(rdbtnTake)
										.addComponent(rdbtnDine)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(307)
									.addComponent(btnCheckout)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(MenuList, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)))
							.addGap(344)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(OrderList, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblName)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(17)
							.addComponent(rdbtnTake)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(6)
									.addComponent(rdbtnDine)
									.addGap(12)
									.addComponent(btnAdd))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
									.addComponent(MenuList, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(222)
							.addComponent(OrderList, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)))
					.addGap(70)
					.addComponent(btnCheckout, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(246))
		);
		contentPane.setLayout(gl_contentPane);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(MenuList, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(OrderList, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
									.addGap(14))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblName)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(6)
									.addComponent(btnCheckout))))
						.addComponent(rdbtnTake)
						.addComponent(rdbtnDine))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnTake)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnDine)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(OrderList, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addComponent(MenuList, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdd))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCheckout)
					.addGap(15))
		);
		getContentPane().setLayout(groupLayout);	
	}
	
	
	public void createEvents() {
		
		//Event handler for Checkout btn
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the selected radio button
				String diningOpt = getDiningOpt();
				if(diningOpt!=null) {
				JOptionPane.showMessageDialog(null,
						"Hi "+ txtName.getText()+ "!"+"\n"+
				"You have ordered "+ OrderList.getModel().getSize()+" items for "+ getDiningOpt()+ ". The total cost is "+
								String.format("%.2f", Total)+".");}
				
				// if nothing selected show this warning
				else {
					JOptionPane.showMessageDialog(null, "Please choose Take out or Dine in");	
				}
			}
		});
		// Event handler for the Add btn
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//set total to zero to prevent it from adding up previous choices
				//Total = 0;
				// get the value of the selected pizza and add it to chosenPizzas array
				String picked = MenuList.getSelectedValue().toString();
				// add the price of the picked pizza to total + tax
				Total += pizzas[MenuList.getSelectedIndex()].getPrice()*1.05;
				chosenPizzas[newPizzaCount] = picked;
				newPizzaCount++;
				// Create list model and fill it with chosen pizzas
				DefaultListModel<String> selectedPizzasList = new DefaultListModel<>();
				for(int i = 0; i< newPizzaCount; i++) {
					selectedPizzasList.addElement(chosenPizzas[i]);
				}
				OrderList.setModel(selectedPizzasList);
			}
		});	
	}

	/**
	 * Create the frame.
	 */
	public wbgui() {
		getPizzas();
		setupComponents();
		createEvents();
		
		
	}
}
