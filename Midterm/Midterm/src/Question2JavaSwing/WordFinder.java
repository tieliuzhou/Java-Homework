package Question2JavaSwing;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import java.util.Objects;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class WordFinder extends JFrame {
  private static final int FRAME_WIDTH = 600;
  private static final int FRAME_HEIGHT = 400;
  private static final int FIELD_WIDTH = 30;
  JMenuBar menuBar;
  JTextArea textArea;
  JTextField SearchField;
  JLabel FindLabel;
  JButton Clear;
  JScrollPane listScroller;
  //JMenu menu;
  //JMenuItem menuItem;

  JFileChooser jFileChooser;
  private JPanel topPanel; // the top line of objects is going to go here
  WordList wordList;


  public WordFinder() {

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set the size correctly
    this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    jFileChooser = new JFileChooser(".");
    wordList = new WordList();
    topPanel = new JPanel();
    createMenus();

    // there should be objects in the top panel
    FindLabel = new JLabel("Find: ");
    SearchField = new JTextField(FIELD_WIDTH);
    Clear = new JButton("Clear");
    topPanel.add(FindLabel);
    topPanel.add(SearchField);
    topPanel.add(Clear);
    this.add(topPanel, BorderLayout.NORTH);

    // There should probably be something passed into the JScrollPane
    textArea = new JTextArea();// size???
    textArea.setEditable(false);
    listScroller = new JScrollPane(textArea);
    listScroller.setPreferredSize(new Dimension(250, 80));
    this.add(listScroller);
    // and of course you will want them to be properly aligned in the frame

    SearchField.addCaretListener(new SearchListener());
    Clear.addActionListener(e -> SearchField.setText(null));

  }

  private void createMenus() {

    menuBar = new JMenuBar();

    /* add a "File" menu with:
     * "Open" item which allows you to choose a new file
     * "Exit" item which ends the process with System.exit(0);
     * Key shortcuts are optional
     */
    menuBar.add(createFileMenu());
    this.setJMenuBar(menuBar);

  }
  public JMenu createFileMenu(){
    JMenu menu = new JMenu("File");
    menu.add(createOpenFileItem());
    menu.add(createEXitItem());
    return menu;
  }
  public JMenuItem createOpenFileItem(){
    JMenuItem item = new JMenuItem("Open");
    item.addActionListener(new OpenFileListener());
    return item;
  }
  public JMenuItem createEXitItem(){
    JMenuItem item = new JMenuItem(("Exit"));
    item.addActionListener((e) -> System.exit(0));
    return item;
  }

  class SearchListener implements CaretListener {
    public void caretUpdate(CaretEvent e) {

      textArea.setText(null);
      List searchResult = wordList.find(SearchField.getText()); // figure out from WordList how to get this
      for (Iterator i = searchResult.iterator(); i.hasNext(); ) {
        textArea.append(i.next() + "\n");
      }
      textArea.setCaretPosition(0);
    }
  }

  class OpenFileListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int returnVal = jFileChooser.showOpenDialog(getParent());
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        System.out.println("You chose to open this file: " + jFileChooser.getSelectedFile().getAbsolutePath());

        InputStream in = null;
        try {
          in = new FileInputStream(jFileChooser.getSelectedFile().getAbsolutePath());
        } catch (FileNotFoundException ex) {
          ex.printStackTrace();
        }
        try {
          wordList.load(in);
        } catch (IOException ex) {
          ex.printStackTrace();
        }

        textArea.setText(null);
        List searchResult = wordList.find(""); // figure out from WordList how to get this
        for (Iterator i = searchResult.iterator(); i.hasNext(); ) {
          textArea.append(i.next() + "\n");
        }
        textArea.setCaretPosition(0);
      }
    }
  }


  public static void main(String[] args) {

    WordFinder wordFinder = new WordFinder();
    wordFinder.setVisible(true);
  }
}
