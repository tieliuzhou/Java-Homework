package finalproject.client;

import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
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
	final int AREA_COLUMNS = 52;

	//for UI
	private JPanel controlPanel;
	private JTextArea textQueryArea;
	private JScrollPane scrollPane;
	private JMenuBar menuBar;
	private JTextField dbName;
	private JTextField hostName;

	private JButton openConn;
	private JButton closeConn;
	private JButton sendData;
	private JButton queryDB;

	//for db
	private Connection conn;
	private PreparedStatement queryStmtAll;
	private PreparedStatement queryAvaliable;
	private PreparedStatement queryStmtPerson;
	private PreparedStatement updateStmt;
	private int numColumns;
	private String ColumnNames;
	private String spaceLine;
	private List<ComboBoxItem> ans;

	private JComboBox peopleSelect;
	private JFileChooser jFileChooser;

	// for socket
	private Socket socket;
	private int port;
	private String host;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private BufferedReader br;
	
	public ClientInterface() {
		this(DEFAULT_PORT);
	}
	
	public ClientInterface(int port) {
		this.port = port;
		ans = new ArrayList<>();
		this.host = "localhost";

		jFileChooser = new JFileChooser(".");
		createControlPanel();
		textQueryArea = new JTextArea(AREA_ROWS,AREA_COLUMNS);
		textQueryArea.setEditable(false);

		scrollPane = new JScrollPane(textQueryArea);
		//JPanel textPanel = new JPanel();
		//textPanel.add(scrollPane);

		this.setLayout(new GridLayout(2,1));
		this.add(controlPanel);
		//this.add(textPanel);
		this.add(scrollPane);

		queryDB.addActionListener(new QueryButtonListener());
		openConn.addActionListener(new OCButtonListener());
		closeConn.addActionListener((e) -> {
			try{
				socket.close();
				hostName.setText("<None>");
				System.out.println("connection closed");
			} catch (Exception e1){
				e1.printStackTrace();
			}
		});
		sendData.addActionListener(new SendButtonListener());
	}

	private JPanel createControlPanel() {
		controlPanel = new JPanel(new GridLayout(2,1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		createMenus();

		JPanel northPanel = new JPanel(new GridLayout(3,1));
		JPanel centerPanel = new JPanel(new GridLayout(2,1));

		JPanel np1 = new JPanel();
		JLabel np1lb = new JLabel("Active DB: ");
		dbName = new JTextField(10);
		dbName.setText("<None>");
		dbName.setEditable(false);
		np1.add(np1lb);
		np1.add(dbName);
		northPanel.add(np1);

		JPanel np2 = new JPanel();
		JLabel np2lb = new JLabel("Active Connection: ");
		hostName = new JTextField(10);
		hostName.setText("<None>");
		hostName.setEditable(false);
		np2.add(np2lb);
		np2.add(hostName);
		northPanel.add(np2);

		JPanel np3 = new JPanel();
		peopleSelect = new JComboBox();
		peopleSelect.addItem("<Empty>");
		peopleSelect.setEditable(false);
		np3.add(peopleSelect);
		northPanel.add(np3);
		
		JPanel cp1 = new JPanel();
		openConn = new JButton("Open Connection");
		closeConn = new JButton("Close Connection");
		cp1.add(openConn);
		cp1.add(closeConn);
		centerPanel.add(cp1);

		JPanel cp2 = new JPanel();
		sendData = new JButton("Send Data");
		queryDB = new JButton("Query DB Data");
		cp2.add(sendData);cp2.add(queryDB);
		centerPanel.add(cp2);
		

		controlPanel.add(northPanel);
		controlPanel.add(centerPanel);
		return controlPanel;
	}

	private void createMenus() {
		menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		this.setJMenuBar(menuBar);
	}

	public JMenu createFileMenu() {
      JMenu menu = new JMenu("File");
      menu.add(createFileOpenItem());
      menu.add(createFileExitItem());
      return menu;
   }

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		item.addActionListener(e -> System.exit(0));
		return item;
	}


	private void fillComboBox() throws SQLException {
	   List<ComboBoxItem> l = getNames();
	   peopleSelect.setModel(new DefaultComboBoxModel(l.toArray()));
	   if(l.isEmpty())
	   	peopleSelect.addItem("<Empty>");
   }
   
   private JMenuItem createFileOpenItem() {
	   JMenuItem item = new JMenuItem("Open DB");
	   class OpenDBListener implements ActionListener {
	   	public void actionPerformed(ActionEvent event) {
	   		int returnVal = jFileChooser.showOpenDialog(getParent());
	   		if (returnVal == JFileChooser.APPROVE_OPTION) {
	   			System.out.println("You chose to open this file: " + jFileChooser.getSelectedFile().getAbsolutePath());
	   			String dbFileName = jFileChooser.getSelectedFile().getAbsolutePath();
	   			try {
	   				connectToDB(dbFileName);
	   				dbName.setText(dbFileName.substring(dbFileName.lastIndexOf("\\")+1));
	   				/*queryButtonListener.setConnection(conn);*/
						clearComboBox();
						fillComboBox();
	   			} catch (Exception e ) {
	   				System.err.println("error connection to db: "+ e.getMessage());
	   				e.printStackTrace();
	   				dbName.setText("<None>");
						clearComboBox();
	   			}
	   			finally {
						textQueryArea.setText("");
					}
	   		}
	   	}
	   }
	   item.addActionListener(new OpenDBListener());
	   return item;
   }

	private void clearComboBox() {
		peopleSelect.removeAllItems();
		peopleSelect.addItem("<Empty>");
	}

	private void connectToDB(String dbFileName) {
		try{
			String url = "jdbc:sqlite:"+dbFileName;
			conn = DriverManager.getConnection(url);
			queryStmtAll = conn.prepareStatement("SELECT * FROM People");
			queryAvaliable = conn.prepareStatement("SELECT * FROM People WHERE sent = 0");
			updateStmt = conn.prepareStatement("UPDATE People SET sent = 1 WHERE id = ?");
			queryStmtPerson = conn.prepareStatement("SELECT * FROM People WHERE id = ?");

			//get the column names and a special space line
			Statement select = conn.createStatement();
			ResultSet resultSet = select.executeQuery("SELECT * FROM People");
			ResultSetMetaData rsmd = resultSet.getMetaData();
			numColumns = rsmd.getColumnCount();
			ColumnNames = "";
			spaceLine = "";
			for(int i = 1;i<=numColumns;i++){
				ColumnNames += rsmd.getColumnName(i) + "\t";
				for(int j = 0;j<rsmd.getColumnName(i).length();j++)
					spaceLine += "-";
				spaceLine += "\t";
			}
			ColumnNames += "\n";
			spaceLine += "\n";
		} catch (SQLException e){
			System.err.println("Connection error: " + e);
			System.exit(1);
		}
	}

	private class QueryButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				textQueryArea.setText(ColumnNames);
				textQueryArea.append(spaceLine);
				ResultSet reset = queryStmtAll.executeQuery();
				String rowString = "";
				while (reset.next()) {
					for (int i=1;i<=numColumns;i++) {
						Object o = reset.getObject(i);
						rowString += o.toString() + "\t";
					}
					rowString += "\n";
				}
				textQueryArea.append(rowString);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class OCButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				socket = new Socket(host, port);
				InetAddress inetAddress = socket.getInetAddress();
				//System.out.println(inetAddress.getHostName());
				hostName.setText(inetAddress.getHostName() +":"+port);
				toServer = new ObjectOutputStream(socket.getOutputStream());
				fromServer = new ObjectInputStream(socket.getInputStream());
				System.out.println("connected");
			} catch (IOException ioException) {
				ioException.printStackTrace();
				System.out.println("connection failure");
			}
		}
	}

	private class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				
				// responses are going to come over the input as text, and that's tricky,
				// which is why I've done that for you:
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				// now, get the person on the object dropdownbox we've selected
				ComboBoxItem personEntry = (ComboBoxItem)peopleSelect.getSelectedItem();
				
				// That's tricky which is why I have included the code. the personEntry
				// contains an ID and a name. You want to get a "Person" object out of that
				// which is stored in the database
				queryStmtPerson.setInt(1,personEntry.getId());
				ResultSet reset = queryStmtPerson.executeQuery();
				reset.next();
				Person p = new Person(reset.getString(1),reset.getString(2)
				,reset.getInt(3),reset.getString(4),reset.getInt(6));

				
				// Send the person object here over an output stream that you got from the socket.
				toServer.writeObject(p);
				toServer.flush();

				String response = null;
				try {
					response = br.readLine();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}

				/*
				try {
					response = (String) fromServer.readObject();
				} catch (ClassNotFoundException classNotFoundException) {
					classNotFoundException.printStackTrace();
				}
				*/

				if (response.contains("Success")) {
					// what do you do after we know that the server has successfully
					// received the data and written it to its own database?
					// you will have to write the code for that.
					try {
						System.out.println("Successfully sent data");
						updateStmt.setInt(1,p.getId());
						updateStmt.executeUpdate();
						System.out.println("Successfully updated data");
						clearComboBox();
						fillComboBox();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					System.out.println("Failed");
				}
			} catch (IOException | SQLException e1) {
				e1.printStackTrace();
			}
	        
			
		}
		
	}
	
   private List<ComboBoxItem> getNames() throws SQLException {
		 ans.removeAll(ans);
	   ResultSet reset = queryAvaliable.executeQuery();
	   while (reset.next()){
				String str = reset.getObject(1).toString()+" "+reset.getObject(2);
				int id = (Integer) reset.getObject(6);
				ComboBoxItem cbi = new ComboBoxItem(id,str);
				ans.add(cbi);
		 }
	   return ans;
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
	
	public static void main(String[] args) {
		ClientInterface ci = new ClientInterface();
		ci.setVisible(true);
	}



}
