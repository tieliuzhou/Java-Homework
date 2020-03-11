package PartTwo;
import java.io.*;

public class Exercise2 {
  public static void main(String[] args) {
    int n=10;
    int[] v = new int[n];
    FileReader f = null;
    try {
      f = new FileReader("data.txt");
    } catch (FileNotFoundException e) {
      System.err.println("Caught FileNotFoundException: " + e.getMessage());
    }
    BufferedReader in = new BufferedReader(f);
    int i=0;
    String linea = null;
    try {
      linea = in.readLine();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    while (linea!=null) {
      v[i] = Integer.parseInt(linea);
      try {
        linea = in.readLine();
      } catch (IOException e) {
        System.err.println("Caught IOException: " + e.getMessage());
      }
      i++;
    }
    try {
      f.close();
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
  }
}

