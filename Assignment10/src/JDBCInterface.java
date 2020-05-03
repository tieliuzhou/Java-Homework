import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class JDBCInterface extends JFrame {
	private JPanel controlPanel;
	private JTextArea textQueryArea;
	private JTextField lastNameQuery;
	private JButton queryButton;

	private JTextField lastNameInsert;
	private JTextField firstNameInsert;
	private JTextField ageInsert;
	private JTextField cityInsert;
	private JButton insertButton;
	
	private Connection conn;
	private PreparedStatement queryStmtLastName;
	private PreparedStatement insertStmt;
	private PreparedStatement queryStmtAll;
	
	
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 400;
	final int AREA_ROWS = 20;
	final int AREA_COLUMNS = 40;

	private int numColumns;
	private String ColumnNames;

   
	public JDBCInterface() {

   	try {
			conn = DriverManager.getConnection("jdbc:sqlite:assignment.db");
			//conn.setAutoCommit(false);
			queryStmtLastName = conn.prepareStatement("SELECT * FROM People WHERE last = ?");
			queryStmtAll = conn.prepareStatement("SELECT * FROM People");
			insertStmt = conn.prepareStatement("INSERT INTO People (First,last,age,city) VALUES (?,?,?,?)");

			//get the column names
			Statement select = conn.createStatement();
			ResultSet resultSet = select.executeQuery("SELECT * FROM People");
			ResultSetMetaData rsmd = resultSet.getMetaData();
			numColumns = rsmd.getColumnCount();
			ColumnNames = "";
			for(int i = 1;i<=numColumns;i++)
				ColumnNames += rsmd.getColumnName(i) + "\t";
			ColumnNames += "\n";

		} catch (SQLException e) {
			System.err.println("Connection error: " + e);
			System.exit(1);
		}



		//
		createControlPanel();
		queryButton.addActionListener(new QueryButtonListener());
		insertButton.addActionListener(new InsertButtonListener());

		textQueryArea = new JTextArea(
				AREA_ROWS, AREA_COLUMNS);
		textQueryArea.setEditable(false);
	   
		/* scrollPanel is optional */
		 JScrollPane scrollPane = new JScrollPane(textQueryArea);
		 JPanel textPanel = new JPanel();
	   textPanel.add(scrollPane);
	   this.add(textPanel, BorderLayout.CENTER);
	   this.add(controlPanel, BorderLayout.NORTH);


	}
   
   private JPanel createControlPanel() {
	   
	   /* you are going to have to create a much more fully-featured layout */
		 controlPanel = new JPanel(new BorderLayout());

		 JPanel insertPanel = new JPanel(new GridLayout(2,2));

		 JPanel jp1 = new JPanel();
		 JLabel lbl1 = new JLabel("Last Name:");
		 jp1.add(lbl1);
		 lastNameInsert = new JTextField(10);
		 jp1.add(lastNameInsert);
		 insertPanel.add(jp1);

		 JPanel jp2 = new JPanel();
		 JLabel lbl2 = new JLabel("First Name:");
		 jp2.add(lbl2);
		 firstNameInsert = new JTextField(10);
		 jp2.add(firstNameInsert);
		 insertPanel.add(jp2);

		 JPanel jp3 = new JPanel();
		 JLabel lbl3 = new JLabel("Age:");
		 jp3.add(lbl3);
		 ageInsert = new JTextField(5) ;
		 ageInsert.addKeyListener(new KeyAdapter() {// To ensure the age is Integer type
			 @Override
			 public void keyTyped(KeyEvent e) {
				 char keyChar = e.getKeyChar();
				 if(!(keyChar >= '0' && keyChar <= '9')){
					 e.consume();
				 }
			 }
		 });
		 jp3.add(ageInsert);
		 insertPanel.add(jp3);

		 JPanel jp4 = new JPanel();
		 JLabel lbl4 = new JLabel("City:");
		 jp4.add(lbl4);
		 cityInsert = new JTextField(10);
		 jp4.add(cityInsert);
		 insertPanel.add(jp4);

	   JPanel inputPanel = new JPanel(new GridLayout(1,2));

	   JPanel jp5 = new JPanel();
	   JLabel lbl = new JLabel("Last Name:");
	   jp5.add(lbl);
	   lastNameQuery = new JTextField(10);
	   jp5.add(lastNameQuery);
	   inputPanel.add(jp5);

	   JPanel buttonPanel = new JPanel();
	   queryButton = new JButton("Execute Query");
	   buttonPanel.add(queryButton);
	   inputPanel.add(buttonPanel);

	   JPanel buttonInsert = new JPanel(new GridLayout(1,2));

	   JPanel insertButtonPanel = new JPanel();
	   insertButton = new JButton("Insert");
	   insertButtonPanel.add(insertButton);
	   buttonInsert.add(insertButtonPanel);
	   JPanel jp6 = new JPanel();
	   jp6.setVisible(false);
	   buttonInsert.add(jp6);

	   controlPanel.add(insertPanel,BorderLayout.NORTH);
	   controlPanel.add(buttonInsert,BorderLayout.CENTER);
	   controlPanel.add(inputPanel,BorderLayout.SOUTH);


	   return controlPanel;
	   
	   
   }
   
   class InsertButtonListener implements ActionListener {
	   	public void actionPerformed(ActionEvent event) {
		   	/* You will have to implement this */
			 	PreparedStatement stmt;

			 	String lastName = lastNameInsert.getText();
			 	String firstName = firstNameInsert.getText();
			 	String age = ageInsert.getText();
			 	String city = cityInsert.getText();
			 	if(lastName.isEmpty() || firstName.isEmpty() || age.isEmpty() || city.isEmpty()){
			 		JOptionPane.showMessageDialog(null,"All Fields must be filled");
			 	}
			 	else{
			 		stmt = insertStmt;
				 	try {
					 	stmt.setString(1,firstName);
					 	stmt.setString(2,lastName);
					 	stmt.setInt(3,Integer.parseInt(age));
					 	stmt.setString(4,city);
					 	stmt.executeUpdate();
						JOptionPane.showMessageDialog(null,"Inserted Successfully");
						//conn.commit();
				 	} catch (SQLException e) {
					 	e.printStackTrace();
						/*try {
							conn.rollback();
						} catch (SQLException roll_e) {
							roll_e.printStackTrace();
						}*/
					}

			 }


			 lastNameInsert.setText("");
			 firstNameInsert.setText("");
			 ageInsert.setText("");
			 cityInsert.setText("");
	   }
   }
   
   class QueryButtonListener implements ActionListener {
	   public void actionPerformed(ActionEvent event){
		   /* as far as the columns, it is totally acceptable to
		    * get all of the column data ahead of time, so you only
		    * have to do it once, and just reprint the string
		    * in the text area.
		    *
		    * you want to change things here so that if the text of the
		    * last name query field is empty, it should query for all rows.
		    * 
		    * For now, if the last name query field is blank, it will execute:
		    * SELECT * FROM People WHERE Last=''
		    * which will give no results
		    */
		   try{
				 	//textQueryArea.setText("");
		   		textQueryArea.setText(ColumnNames);

			   	PreparedStatement stmt;
			   	String lastNameText = lastNameQuery.getText();
			   	ResultSet reset;
			   	if(lastNameText.isEmpty()){
			   		stmt = queryStmtAll;
						//System.out.println(1);
					}
					else{
						stmt = queryStmtLastName;
						stmt.setString(1, lastNameText);
					}
				 	reset = stmt.executeQuery();
				 	//System.out.println("numcolumns is "+ numColumns);

				 	String rowString = "";
				 	while (reset.next()) {
				 		for (int i=1;i<=numColumns;i++) {
				 			Object o = reset.getObject(i);
				 			rowString += o.toString() + "\t";
				 		}
				 		rowString += "\n";
				 	}
					//System.out.print("rowString  is  " + rowString);
					textQueryArea.append(rowString);
		   } catch (SQLException e) {
			   	e.printStackTrace();
		   }
	   }
   }
    
   public static void main(String[] args)
	{  
	   JFrame frame = new JDBCInterface();
	   frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frame.setVisible(true);      
	}
}
