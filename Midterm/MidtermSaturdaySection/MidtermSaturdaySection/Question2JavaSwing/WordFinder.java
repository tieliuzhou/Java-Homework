import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordFinder extends JFrame {

	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	
	JFileChooser jFileChooser;
	private JPanel topPanel; // the top line of objects is going to go here
	WordList wordList;
	

	public WordFinder() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// set the size correctly
		
		jFileChooser = new JFileChooser(".");
		wordList = new WordList();
		topPanel = new JPanel();
		createMenus();
		
		// there should be objects in the top panel
		
		// There should probably be something passed into the JScrollPane
		
		JScrollPane listScroller = new JScrollPane();
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		// and of course you will want them to be properly aligned in the frame
		

	}
	
	private void createMenus() {
		
		menuBar = new JMenuBar();
		
		/* add a "File" menu with:
		 * "Open" item which allows you to choose a new file
		 * "Exit" item which ends the process with System.exit(0);
		 * Key shortcuts are optional
		 */

		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
	}
	
	class SearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			List searchResult = null; // figure out from WordList how to get this

			for (Object s : searchResult) {

			}
		}
	}
	
	class OpenFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int returnVal = jFileChooser.showOpenDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chose to open this file: " + jFileChooser.getSelectedFile().getAbsolutePath());
				
				InputStream in = new FileInputStream(jFileChooser.getSelectedFile().getAbsolutePath());
				wordList.load(in);

				List searchResult = null; // figure out from WordList how to get this
				for (Object s : searchResult) {
					
				}
			}
		}
	}


	public static void main(String[] args) {

		WordFinder wordFinder = new WordFinder();
		wordFinder.setVisible(true);
	}
}
