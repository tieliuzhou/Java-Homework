package PartTwo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class Matrix {
  private int N,M;
  private int[][] matrix;

  public Matrix(int n,int m){
    N = n;
    M = m;
    matrix = new int[n][m];
  }
  public Matrix(Matrix m){
    this.N = m.N;
    this.M = m.M;
    this.matrix = m.matrix;
  }

  public void save(String filename){
    PrintWriter out = null;
    try{
      System.out.println("Entering PrintWriter statement");
      out = new PrintWriter(new FileWriter(filename));
      for(int i = 0 ; i < this.N ; i++){//Print matrix
        for( int j = 0 ; j < this.M ; j++){
          out.print(this.getMatrix(i,j) + " ");
        }
        out.println("");
      }
    }catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }catch (IndexOutOfBoundsException e){
      System.err.println("Caught IndexOutOfBoundsException: " + e.getMessage());
    }finally {
      if (out != null) {
        out.close();
        System.out.println("Closing PrintWriter");
      } else {
        System.out.println("PrintWriter not open");
      }
    }
  }

  public static Matrix read(String filename){
    //Generate a matrix based on the dimensions of the data in the file
    int n = 0,m = 0;
    Vector tmatrix = new Vector();//Store matrix values in a file with a two-dimensional vector

    FileReader f = null;
    try {
      f = new FileReader(filename);
      System.out.println("Entering read statement");
    } catch (FileNotFoundException e) {
      System.err.println("Caught FileNotFoundException: " + e.getMessage());
    }
    BufferedReader in = new BufferedReader(f);
    String linea = null;
    try{
      int k = 0 , lengthcol = 0;
      while ( ( linea = in.readLine() ) != null){
        String[] rows = linea.split(" ");//Read in and split a row of Integer data

        if(k>0){
          if(lengthcol != rows.length)
            throw new ExceptionWrongMatrixValues("data are not "
                + "consistent with the form of a matrix");//
        }

        lengthcol = rows.length;
        //System.out.println("lengthcol = "+ lengthcol);
        Vector t = new Vector();
        t.clear();
        for(int i = 0 ; i < rows.length ; i++){
          t.add(Integer.parseInt(rows[i]));//NumberFormatException
        }
        /*for(Object i : t){
          System.out.println(i);
        }*/
        tmatrix.add(t);
        k++;
      }
    }catch(IOException e){
      System.err.println("Caught IOException: " + e.getMessage());
    }catch (ArrayIndexOutOfBoundsException e){
      System.err.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
    }catch (ExceptionWrongMatrixValues e){
      System.err.println("Caught ExceptionWrongMatrixValues: " + e.getMsgDes());
    }catch (NumberFormatException e){//As requested, this is also the ExceptionWrongMatrixValues
      System.err.println("Caught ExceptionWrongMatrixValues: " + e.getMessage());
    }finally {
      try {
        in.close();
        f.close();
      } catch (IOException e) {
        System.err.println("Caught IOException: " + e.getMessage());
      }
    }
    /*for(Object i : tmatrix)
      System.out.println(i);*/
    n = tmatrix.size();
    m = ((Vector)tmatrix.get(0)).size();

    //System.out.println(n+" "+m);
    Matrix temp = new Matrix(n,m);
    try{
      for(int i = 0 ; i < n ; i++) {//Assigning values to matrices
        Vector tt = (Vector)tmatrix.get(i);
      /*for(Object k:tt)
        System.out.println(k);*/
        for (int j = 0; j < m; j++) {
          //System.out.println(tt.get(j));
          temp.setMatrix(i,j,(int)tt.get(j));
        }
      }
    }catch(ArrayIndexOutOfBoundsException e){
      System.err.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
    }catch (IndexOutOfBoundsException e){
      System.err.println("Caught IndexOutOfBoundsException: " + e.getMessage());
    }

    /*for(int i = 0 ; i < temp.N ; i++)
      for(int j = 0 ; j < temp.M ; j++)
        System.out.println(temp.getMatrix(i,j));
    */

    return temp;
  }

  public static Matrix read(int n, int m, String filename){
    //Overloading the read method
    //Generate a matrix based on a preset matrix dimension and read the data from the file

    Vector tmatrix = new Vector();

    FileReader f = null;
    try {
      f = new FileReader(filename);
      System.out.println("Entering read statement");
    } catch (FileNotFoundException e) {
      System.err.println("Caught FileNotFoundException: " + e.getMessage());
    }
    BufferedReader in = new BufferedReader(f);
    String linea = null;
    try{
      int k = 0 , lengthcol = 0;
      while ( ( linea = in.readLine() ) != null){
        String[] rows = linea.split(" ");

        if(k>0){
          if(lengthcol != rows.length)
            throw new ExceptionWrongMatrixValues("data are not "
                + "consistent with the form of a matrix");//
        }

        lengthcol = rows.length;
        Vector t = new Vector();
        t.clear();
        for(int i = 0 ; i < rows.length ; i++){
          t.add(Integer.parseInt(rows[i]));//NumberFormatException
        }
        tmatrix.add(t);
        k++;
      }
    }catch(IOException e){
      System.err.println("Caught IOException: " + e.getMessage());
    }catch (ArrayIndexOutOfBoundsException e){
      System.err.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
    }catch (ExceptionWrongMatrixValues e){
      System.err.println("Caught ExceptionWrongMatrixValues: " + e.getMsgDes());
    }catch (NumberFormatException e){
      System.err.println("Caught ExceptionWrongMatrixValues: " + e.getMessage());
    }finally {
      try {
        in.close();
        f.close();
      } catch (IOException e) {
        System.err.println("Caught IOException: " + e.getMessage());
      }
    }
    try {
      if( n != tmatrix.size() || m != ((Vector)tmatrix.get(0)).size())
        throw new ExceptionWrongMatrixDimension("the data on the file "
            + "do not correspond to the dimension of the matrix");
    } catch (ExceptionWrongMatrixDimension e){
      System.err.println("Caught ExceptionWrongMatrixDimension: " + e.getMsgDes());
    }


    Matrix temp = new Matrix(n,m);
    try{
      for(int i = 0 ; i < n ; i++) {
        Vector tt = (Vector)tmatrix.get(i);
      /*for(Object k:tt)
        System.out.println(k);*/
        for (int j = 0; j < m; j++) {
          //System.out.println(tt.get(j));
          temp.setMatrix(i,j,(int)tt.get(j));
        }
      }
    }catch(ArrayIndexOutOfBoundsException e){
      System.err.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
    }catch (IndexOutOfBoundsException e){
      System.err.println("Caught IndexOutOfBoundsException: " + e.getMessage());
    }

    return temp;
  }

  public Matrix sum(Matrix m){
    if(this.N != m.N || this.M != m.M)
      return null;
    Matrix temp = new Matrix(this.N,this.M);
    for(int i = 0 ; i < N ; i++){
      for(int j = 0 ; j < M ; j++){
        temp.setMatrix(i,j,this.getMatrix(i,j)+m.getMatrix(i,j));
      }
    }
    return temp;
  }

  public Matrix product(Matrix m){
    if(this.M != m.N)
      return null;

    Matrix temp = new Matrix(this.N,m.M);
    for(int i = 0 ; i < temp.N ; i++){
      for(int j = 0 ; j < temp.M ; j++){
        int t = 0;
        for(int k = 0 ; k < this.M ; k++)
          t += this.getMatrix(i,k) * m.getMatrix(k,j);
        temp.setMatrix(i,j,t);
      }
    }

    return temp;
  }

  public int getMatrix(int i, int j){
    return this.matrix[i][j];
  }
  public void setMatrix(int i,int j, int val){
    this.matrix[i][j] = val;
  }


  public static void main(String[] args){

    Matrix m = new Matrix( Matrix.read("matrix_in.txt") );

    Matrix m2 = new Matrix(2,2);
    m2 = Matrix.read(2,2,"matrix2_in.txt");

    m.save("matrix_out.txt");
    m2.save("matrix2_out.txt");
  }
}

class ExceptionWrongMatrixValues extends RuntimeException{
  private String retCd ;  //return code
  private String msgDes;  //description message of exception

  public ExceptionWrongMatrixValues() {
    super();
  }

  public ExceptionWrongMatrixValues(String message) {
    super(message);
    msgDes = message;
  }

  public ExceptionWrongMatrixValues(String retCd, String msgDes) {
    super();
    this.retCd = retCd;
    this.msgDes = msgDes;
  }

  public String getRetCd() {
    return retCd;
  }

  public String getMsgDes() {
    return msgDes;
  }
}

class ExceptionWrongMatrixDimension extends RuntimeException{
  private String retCd ;
  private String msgDes;

  public ExceptionWrongMatrixDimension() {
    super();
  }

  public ExceptionWrongMatrixDimension(String message) {
    super(message);
    msgDes = message;
  }

  public ExceptionWrongMatrixDimension(String retCd, String msgDes) {
    super();
    this.retCd = retCd;
    this.msgDes = msgDes;
  }

  public String getRetCd() {
    return retCd;
  }

  public String getMsgDes() {
    return msgDes;
  }
}
