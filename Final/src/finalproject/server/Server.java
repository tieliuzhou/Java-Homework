package finalproject.server;

//import finalproject.client.ClientInterface.QueryButtonListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import finalproject.db.DBInterface;
import finalproject.entities.Person;
import javax.swing.JTextField;

public class Server extends JFrame {

	public static final int DEFAULT_PORT = 8001;
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 800;
	final int AREA_ROWS = 10;
	final int AREA_COLUMNS = 40;
	private int clientNo = 0;

	private JPanel controlPanel;
	private JTextArea textQueryArea;
	private JMenuBar menuBar;
	private JTextField dbName;
	private JScrollPane scrollPane;
	private JButton queryDB;

	private Connection conn;
	private PreparedStatement queryStmtAll;
	private PreparedStatement insertStmt;
	private int numColumns;
	private String ColumnNames;
	private String spaceLine;

	private int port;
	private Socket socket;
	private String host;


	public Server() throws IOException, SQLException {
		this(DEFAULT_PORT, "server.db");
	}
	
	public Server(String dbFile) throws IOException, SQLException {
		this(DEFAULT_PORT, dbFile);
	}

	public Server(int port, String dbFile) throws IOException, SQLException {

		this.host = "localhost";
		this.port = port;
		this.setSize(Server.FRAME_WIDTH, Server.FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createMenus();
		createControlPanle();
		textQueryArea = new JTextArea(AREA_ROWS,AREA_COLUMNS);
		textQueryArea.setEditable(false);

		scrollPane = new JScrollPane(textQueryArea);
		this.setLayout(new BorderLayout());
		this.add(controlPanel,BorderLayout.NORTH);
		this.add(scrollPane,BorderLayout.CENTER);

		connectToDB(dbFile);
		textQueryArea.setText("Listening on port "+port + "\n");
		queryDB.addActionListener(new QueryButtonListener());

		this.init();
	}

	private void init() {
		new Thread(()->{
			try {
				ServerSocket serverSocket = new ServerSocket(port);

				while(true){
					socket = serverSocket.accept();

					clientNo++;

					textQueryArea.append("Starting thread for client " + clientNo +
							" at " + new Date() + '\n');

					InetAddress inetAddress = socket.getInetAddress();
					textQueryArea.append("Client " + clientNo + "'s host name is "
							+ host + "\n");
					textQueryArea.append("Client " + clientNo + "'s host name is "
							+ inetAddress.getHostName() + "\n");
					textQueryArea.append("Client " + clientNo + "'s IP Address is "
							+ inetAddress.getHostAddress() + "\n");
					textQueryArea.append("listening for input from from client "
							+ clientNo + "\n");

					new Thread(new HandleAClient(socket, clientNo)).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private class HandleAClient implements Runnable {
		private Socket socket; // A connected socket
		private int clientNum;
		ObjectInputStream inputFromClient;
		ObjectOutputStream outputToClient;

		/** Construct a thread */
		public HandleAClient(Socket socket, int clientNum) {
			this.socket = socket;
			this.clientNum = clientNum;
			try {
				// Create data input and output streams
				inputFromClient = new ObjectInputStream(
						socket.getInputStream());
				outputToClient = new ObjectOutputStream(
						socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/** Run a thread */
		public void run() {
			// Continuously serve the client
			while (true) {
				try {
					// Create data output streams using OutputStream
					OutputStream outputStream = socket.getOutputStream();
					try {
						Object object = inputFromClient.readObject();
						Person p = (Person) object;
						textQueryArea.append("got person " + p.toString() +
								" from client " + clientNum + " inserting into db " + '\n');

						insertStmt.setString(1, p.getFirst());
						insertStmt.setString(2, p.getLast());
						insertStmt.setInt(3, p.getAge());
						insertStmt.setString(4, p.getCity());
						insertStmt.setInt(5, p.getId());
						insertStmt.executeUpdate();

						textQueryArea.append("Inserted successfully" + '\n');

						// Send back to the client
						String response = "Success" + "\n";
						/*outputToClient.writeObject(response);
						outputToClient.flush();*/

						outputStream.write(response.getBytes());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						String response = "Failed" + "\n";
						/*outputToClient.writeObject(response);
						outputToClient.flush();*/
						outputStream.write(response.getBytes());
					} catch (SQLException e1) {
						e1.printStackTrace();
						String response = "Failed" + "\n";
						/*outputToClient.writeObject(response);
						outputToClient.flush();*/
						outputStream.write(response.getBytes());
					}
				} catch (IOException e2){
					//If there are IO errors, in the Streams or the Sockets,
					// assume there is some kind of network problem
					// and exit the thread by having a using a “break”
					// inside the while loop.
					e2.printStackTrace();
					break;
				}
			}
		}
	}

	private JPanel createControlPanle() {
		controlPanel = new JPanel(new GridLayout(2,1));

		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();

		JLabel lb1 = new JLabel("DB: ");
		dbName = new JTextField(10);
		dbName.setText("<None>");
		dbName.setEditable(false);
		jp1.add(lb1);
		jp1.add(dbName);

		queryDB = new JButton("Query DB");
		jp2.add(queryDB);

		controlPanel.add(jp1);
		controlPanel.add(jp2);
		return controlPanel;
	}

	private void createMenus() {
		menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		this.setJMenuBar(menuBar);
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		return menu;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		item.addActionListener(e -> System.exit(0));
		return item;
	}

	private void connectToDB(String dbFile){
		String url = "jdbc:sqlite:"+dbFile;
		try {
			conn = DriverManager.getConnection(url);
			queryStmtAll = conn.prepareStatement("SELECT * FROM People");
			insertStmt = conn.prepareStatement("INSERT INTO People (first,last,age,city,sent,id) "
					+ "VALUES (?,?,?,?,1,?)");
			dbName.setText("server.db");

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
		} catch (SQLException e) {
			System.err.println("Connection error: " + e);
			System.exit(1);
		}
	}

	private class QueryButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//textQueryArea.setText("Listening on port "+port + "\n");
				textQueryArea.append("DB Results:"+"\n");
				textQueryArea.append(ColumnNames);
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

	public static void main(String[] args) {
		Server sv;
		try {
			sv = new Server("server.db");
			sv.setVisible(true);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}


