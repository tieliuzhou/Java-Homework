package PartTwo;
import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

public class ListOfNumbers {
  private List<Integer> list;
  private static final int SIZE = 10;

  public ListOfNumbers () {
    list = new ArrayList<Integer>(SIZE);
    for (int i = 0; i < SIZE; i++)
      list.add((i));
  }
  public void writeList() {
    PrintWriter out = null;
    try {
      System.out.println("Entering try statement");
      out = new PrintWriter(new FileWriter("OutFile.txt"));
      for (int i = 0; i < SIZE; i++)
        out.println("Value at: " + i + " = " + list.get(i));
    } catch (IndexOutOfBoundsException e) {
      System.err.println("Caught IndexOutOfBoundsException: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    } finally {
      if (out != null) {
        out.close();
        System.out.println("Closing PrintWriter");
      } else {
        System.out.println("PrintWriter not open");
      }
    }
  }

  public void readList(String filename){
    FileReader f = null;
    try{
      System.out.println("Entering read statement");
      f = new FileReader(filename);
    }catch(FileNotFoundException e){
      System.err.println("Caught FileNotFoundException: " + e.getMessage());
    }

    BufferedReader in = new BufferedReader(f);
    String linea = null;
    try {
      while( ( linea = in.readLine() ) != null){
        int temp = Integer.parseInt(linea);
        list.add(temp);
        System.out.println(temp);
      }
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }catch (ArrayIndexOutOfBoundsException e){
      System.err.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
    }finally {
      if (f != null) {
        try {
          in.close();
          f.close();
          System.out.println("Closing FileReader");
        } catch (IOException e) {
          System.err.println("Caught IOException: " + e.getMessage());
        }
      } else {
        System.out.println("FileReader not open");
      }
    }

  }

  public static void cat(File file){
    RandomAccessFile input = null;
    String line = null;

    try {
      input = new RandomAccessFile(file, "r");
      while ((line = input.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    } finally {
      try{
        if (input != null) {
          input.close();
        }
      }catch (IOException e) {
        System.err.println("Caught IOException: " + e.getMessage());
      }
    }
  }


  public static void main(String[] args){//test
    ListOfNumbers lon = new ListOfNumbers();
    lon.readList("data.txt");
    lon.writeList();
  }



}
