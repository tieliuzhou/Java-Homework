package finalproject.client;

import java.util.ArrayList;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

import finalproject.client.ClientInterface.ComboBoxItem;
import finalproject.db.DBInterface;
import finalproject.entities.Person;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientInterface extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_PORT = 8001;
	
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 400;
	final int AREA_ROWS = 10;
	final int AREA_COLUMNS = 40;

	JComboBox peopleSelect;
	JFileChooser jFileChooser;
	Socket socket;
	int port;
	
	public ClientInterface() {
		this(DEFAULT_PORT);
	}
	
	public ClientInterface(int port) {
		this.port = port;
		
	}
	

   public JMenu createFileMenu()
   {
      JMenu menu = new JMenu("File");
      menu.add(createFileOpenItem());
      menu.add(createFileExitItem());
      return menu;
   }
   
   
   private void fillComboBox() throws SQLException {
	   
	   List<ComboBoxItem> l = getNames();
	   peopleSelect.setModel(new DefaultComboBoxModel(l.toArray()));
		
	   
   }
   
   private JMenuItem createFileOpenItem() {
	   JMenuItem item = new JMenuItem("Open DB");
	   class OpenDBListener implements ActionListener
	      {
	         public void actionPerformed(ActionEvent event)
	         {
	 			int returnVal = jFileChooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + jFileChooser.getSelectedFile().getAbsolutePath());
					String dbFileName = jFileChooser.getSelectedFile().getAbsolutePath();
					try {
						connectToDB(dbFileName);
						dbName.setText(dbFileName.substring(dbFileName.lastIndexOf("/")+1));
						queryButtonListener.setConnection(conn);
						//clearComboBox();
						fillComboBox();
						
					} catch (Exception e ) {
						System.err.println("error connection to db: "+ e.getMessage());
						e.printStackTrace();
						dbName.setText("<None>");
						clearComboBox();
					}
					
				}
	         }
	      }
	   
	   item.addActionListener(new OpenDBListener());
	   return item;
   }
   
	class SendButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

	        try {
				
	        	// responses are going to come over the input as text, and that's tricky,
	        	// which is why I've done that for you:
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				// now, get the person on the object dropdownbox we've selected
				ComboBoxItem personEntry = (ComboBoxItem)peopleSelect.getSelectedItem();
				
				// That's tricky which is why I have included the code. the personEntry
				// contains an ID and a name. You want to get a "Person" object out of that
				// which is stored in the database
				
				// Send the person object here over an output stream that you got from the socket.
				
				String response = br.readLine();
				if (response.contains("Success")) {
					System.out.println("Success");
					// what do you do after we know that the server has successfully
					// received the data and written it to its own database?
					// you will have to write the code for that.
				} else {
					System.out.println("Failed");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
			
		}
		
	}
	
   private List<ComboBoxItem> getNames() throws SQLException {
	   
	   return null;
   }
	
	// a JComboBox will take a bunch of objects and use the "toString()" method
	// of those objects to print out what's in there. 
	// So I have provided to you an object to put people's names and ids in
	// and the combo box will print out their names. 
	// now you will want to get the ComboBoxItem object that is selected in the combo box
	// and get the corresponding row in the People table and make a person object out of that.
	class ComboBoxItem {
		private int id;
		private String name;
		
		public ComboBoxItem(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public int getId() {
			return this.id;
		}
		
		public String getName() {
			return this.name;
		}
		
		public String toString() {
			return this.name;
		}
	}
	
	/* the "open db" menu item in the client should use this ActionListener */
	   class OpenDBListener implements ActionListener
	      {
	         public void actionPerformed(ActionEvent event)
	         {
	 			int returnVal = jFileChooser.showOpenDialog(getParent());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " + jFileChooser.getSelectedFile().getAbsolutePath());
					String dbFileName = jFileChooser.getSelectedFile().getAbsolutePath();
					try {
						/* now that you have the dbFileName, you should probably connect to the DB */
						/* maybe think about filling the contents of the dropdown box listing names 
						 * and indicating the name of the Active DB
						 */
						
					} catch (Exception e ) {
						System.err.println("error connection to db: "+ e.getMessage());
						e.printStackTrace();

					}
					
				}
	         }
	      }
	
	public static void main(String[] args) {
		ClientInterface ci = new ClientInterface();
		ci.setVisible(true);
	}
}
