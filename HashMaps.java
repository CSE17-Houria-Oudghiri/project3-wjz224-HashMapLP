import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;
/***
 * Class to model the main class HashMaps
 * @author Wilson Zheng
 * @version 1.53 (Visual Studio Code)
 * Date of creation: May 3rd 2021
 * Last Date Modified: May 3rd, 2021
 */
public class HashMaps {
    public static double SCIterations = 0;
    public static double LPIterations = 0;
    public static double QPIterations = 0;
    public static void main(String[] args){
        // Reads the dictionary.txt file
        File file = new File("dictionary.txt");
        Scanner readFile = null;
        ArrayList<Pair<String, String>> words =  new ArrayList<>(); // Creating an ArrayList of type pair to store the words and definition in dictionary.txt 
        try{
            readFile = new Scanner(file);
        }
        catch(FileNotFoundException e){
            System.out.println("File not Found.");
            System.exit(0);
        }
        while(readFile.hasNextLine()){ // reads each line in the array 
            String line = readFile.nextLine();
            String[] tokens = line.split("\\|"); // splits up the line and stores them in an array to store the key and the definition.
            Pair<String, String> p = new Pair<>(tokens[0], tokens[1]);
            words.add(p);
        }
        

        readFile.close();
        java.util.Collections.shuffle(words); // shuffle the array of words
        // Initalizing and HashMap of SC LP and QP
        HashMapSC<String,String> SCDictionary = new HashMapSC<>(50000);
        HashMapLP<String,String> LPDictionary = new HashMapLP<>(50000);
        HashMapQP<String,String> QPDictionary = new HashMapQP<>(50000);
        // inputs the first 100 random words and their definitions into each respective dictionary
        for(int i = 0; i < words.size(); i++){
            Pair<String, String> p = words.get(i);
            SCDictionary.put(p.getFirst(), p.getSecond());
            LPDictionary.put(p.getFirst(), p.getSecond());
            QPDictionary.put(p.getFirst(), p.getSecond());
        }
        //
        Random random = new Random();
        double SCTotal = 0;
        double LPTotal = 0;
        double QPTotal = 0;
        System.out.println(String.format("%-20s\t%-20s\t%-20s\t%-20s","Word","Separate Chaining","Linear Probing", "Quadratic Probing"));
        for(int i =0; i < 100; i++){
            int index = random.nextInt(words.size());
            Pair<String,String> p = words.get(index); // 
            SCDictionary.get(p.getFirst()); // searching for word
            SCTotal += SCIterations; //  # of iterations it takes to get the word using SC
            LPDictionary.get(p.getFirst()); // searching for word
            LPTotal +=  LPIterations; // # of iterations it takes to get the word using LP
            QPDictionary.get(p.getFirst()); // searching for word
            QPTotal += QPIterations; // # of iterations it takes to get thhe word using QP
            System.out.println(String.format("%-20s\t%-20d\t%-20d\t%-20d","Word",(int)SCIterations, (int)LPIterations,(int)QPIterations)); // Printing the iterations from SC,LP,and QP for each word search
        }
        SCTotal = SCTotal/100; // printing the averages for SC hashMap
        LPTotal = LPTotal /100; // printing the averages for LP hashMap
        QPTotal = QPTotal /100; // printing the averages for QP hashMap
        System.out.println(String.format("%-20s\t%-20.2f\t%-20.2f\t%-20.2f","Averages:", SCTotal, LPTotal, QPTotal)); // Printing the average # of iteartions for SCTotal, LPTotal, QPTotal
      // the perforance of the three implementations have around 1 iterations, indicating a time complexity of O(1).
    }
}
