import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AcademicAdvisors
{
  static public final String EOF = null; // constant used as EOF marker
  static public final int ARRAY_SIZE = 500; // to dimension inputArray
  static public final int REQUIRED_TOKEN_SIZE = 5; // used for field count
  static public File inputFile; // will hold path to input file
  static public File goodFile = new File(windowsMachine("c:") + File.separator
    + "student" + File.separator + "advisors.txt"); //valid file path
  static public File errorFile = new File(windowsMachine("c:") + File.separator
    + "student" + File.separator + "advisorSuspense.txt");//suspense file path
  static public BufferedReader inStream; // used for reading input file
  static public BufferedWriter goodFileOut, errorFileOut; // for writing files
  static public String inputArray[]; // holds initial input from file

  public static void main(String args[])
  {
    getFileNameAndPath();
    openInputFile();
    fillInputArray();
    openGoodFile();
    openErrorFile();
    //System.out.println(inputArray[1]);
    //System.out.println(inputArray[2]);
    //System.out.println(inputArray[3]);
    validateData();
    closeOutputFiles();
    System.exit(0);
  } // end main method()
  public static void getFileNameAndPath()
  {
    String inputData = null;
    String fileName = null;
    String filePath = null;
    Scanner scanIn = new Scanner(System.in);
    System.out.println("\nPlease Enter the full absolute path of the file "
        + "\nThen, without spaces, enter ~ "
        + "Then enter the name of the file.\n"
        + "i.e.  /Users/marien/Desktop/CIT388/project2/~csvData.txt\n");
    inputData = scanIn.nextLine();
    String[] inputDataArray = inputData.split("~");
    filePath = inputDataArray[0];
    fileName = inputDataArray[1];
    //The following comment lines can be used as a check for this method
    //System.out.println("Path:  " + filePath);
    //System.out.println("Name:  " + fileName);
    //System.out.println("You entered the path:  " + scanIn.next()
      //              + "You entered the name:  " + scanIn.next());
    inputFile = new File(filePath, fileName);
    scanIn.close();
  }  // end method getFileNameAndPath()
  public static void openInputFile()
  {
    try
    {
        FileReader fileReader = new FileReader(inputFile);
        inStream = new BufferedReader(fileReader);
        // the following comments lines can be used to check this method
        /*
        if (inStream.ready())
        {
          System.out.println("GOT IT!!!!!");
        }
        */
    }
    catch (IOException ex)
      {
      System.out.println(ex);
      }

  } // end method openInputFile()

  private static void fillInputArray()
  {

    inputArray = new String[ARRAY_SIZE];
    String line = "";
    int i = 0;
    try
    {
      while(inStream.ready())
        {
          //process the line
          //System.out.println(line);
          line = inStream.readLine();
          inputArray[i] = line;
          //System.out.println(line);
          i++;
        }
      inStream.close();
    }
    catch (IOException ex)
    {
      System.out.println(ex);
    }
  } // end readInputFile method

  private static void openGoodFile()
  {
    //the following comment lines can be used to check
    //String content1 = inputArray[0];
    //String content3 = inputArray[2];
    // If file exists, truncate and write to it
    try
    {
      FileWriter writer = new FileWriter("goodData.log");
      goodFileOut = new BufferedWriter(writer);
      //the following comment lines can be used to check
      //goodFileOut.write(content1);
      //goodFileOut.write("\n");
      //goodFileOut.write(content3);
      //goodFileOut.close();
      //System.out.println("succesfully written to a file");
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
  } // end openGoodFile

  private static void openErrorFile()
  {
    try
    {
      FileWriter writer2 = new FileWriter("badData.log");
      errorFileOut = new BufferedWriter(writer2);
      //System.out.println("succesful_Bad_file_Open");
    }
    catch (IOException e)
    {
      System.err.format("IOException: %s%n", e);
    }
  } // end openGoodFile

  private static void validateData()
  {
    String temp_fName = "";
    String temp_lName = "";
    String temp_id = "";
    String temp_major = "";
    String advi = "";
    int temp_advisees;
    StringTokenizer arrayStrT = new StringTokenizer(inputArray[0], ",");
    String line = inputArray[0];;
    if (arrayStrT.countTokens() != REQUIRED_TOKEN_SIZE)
    {
      writeLineToErrorFile(line);
    }
    else
    {
      temp_fName = arrayStrT.nextToken();
      temp_lName = arrayStrT.nextToken();
      temp_id = arrayStrT.nextToken();
      temp_major = arrayStrT.nextToken();
      advi = arrayStrT.nextToken();
      temp_advisees = Integer.parseInt(advi);

      writeLineToGoodFile(temp_lName, temp_fName, temp_id, temp_major, temp_advisees);
    }
  } // end method validateData()

  public static void writeLineToErrorFile(String badLine)
  {
    try
    {
      errorFileOut.write(badLine);
    }
    catch(IOException e)
    {
      System.out.println(e);
    }
  } // end method writeLineToErrorFille

  public static void writeLineToGoodFile(String lName, String fName,
                                          String id, String major, int advisees)
  {

    String content = String.format("%1$s, %2$s|%1$s|%2$s|%3$s|%4$s|%5$d",
                    lName, fName, id, major, advisees);
    try
    {
      goodFileOut.write(content);
    }
    catch(IOException e)
    {
      System.out.println(e);
    }
  } // end method writeLineToGoodFille

  public static void closeOutputFiles()
  {
    try
    {
    goodFileOut.close();
    errorFileOut.close();
    }
    catch(IOException e)
    {
      System.out.println(e);
    }
  } // end method closeOutputFiles()

  public static String windowsMachine(String drive)
  {
    if(System.getProperty("os.name").toLowerCase().contains("windows"))
      return drive;
     else
      return "";
  } // end method windowsMachine
} // end class StudentServices
