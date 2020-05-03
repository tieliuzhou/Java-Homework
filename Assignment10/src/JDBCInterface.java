import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
	
	private Connection conn;
	private PreparedStatement queryStmtLastName;
	
	
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 400;
	final int AREA_ROWS = 20;
	final int AREA_COLUMNS = 40;
   
   public JDBCInterface() {


		try {
			conn = DriverManager.getConnection("jdbc:sqlite:assignment.db");
			queryStmtLastName = conn.prepareStatement("Select * from People WHERE Last = ?");
			
		} catch (SQLException e) {
			System.err.println("Connection error: " + e);
			System.exit(1);
		}
		
	   createControlPanel();
	   queryButton.addActionListener(new QueryButtonListener());

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
	   
	   controlPanel = new JPanel();
	   
	   JPanel inputPanel = new JPanel();
	   JLabel lbl = new JLabel("Last Name:");
	   inputPanel.add(lbl);
	   lastNameQuery = new JTextField(10);
	   inputPanel.add(lastNameQuery);
	   
	   JPanel buttonPanel = new JPanel();
	   queryButton = new JButton("Execute Query");
	   buttonPanel.add(queryButton);
	   
	   controlPanel.add(inputPanel);
	   controlPanel.add(buttonPanel);
	   
	   return controlPanel;
	   
	   
   }
   
   class InsertButtonListener implements ActionListener {
	   public void actionPerformed(ActionEvent event) {
		   /* You will have to implement this */
	   }
   }
   
   class QueryButtonListener implements ActionListener {
	   public void actionPerformed(ActionEvent event)
       {
		   /* as far as the columns, it is totally acceptable to
		    * get all of the column data ahead of time, so you only
		    * have to do it once, and just reprint the string
		    * in the text area.
		    */
		   
		   /* you want to change things here so that if the text of the 
		    * last name query field is empty, it should query for all rows.
		    * 
		    * For now, if the last name query field is blank, it will execute:
		    * SELECT * FROM People WHERE Last=''
		    * which will give no results
		    */
		   try {
			   textQueryArea.setText("");
			   PreparedStatement stmt = queryStmtLastName;
			   String lastNameText = lastNameQuery.getText();
				stmt.setString(1, lastNameText);
				ResultSet rset = stmt.executeQuery();
				ResultSetMetaData rsmd = rset.getMetaData();
				int numColumns = rsmd.getColumnCount();
				System.out.println("numcolumns is "+ numColumns);
	
				String rowString = "";
				while (rset.next()) {
					for (int i=1;i<=numColumns;i++) {
						Object o = rset.getObject(i);
						rowString += o.toString() + "\t";
					}
					rowString += "\n";
				}
				System.out.print("rowString  is  " + rowString);
				textQueryArea.setText(rowString);
		   } catch (SQLException e) {
			   // TODO Auto-generated catch block
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
