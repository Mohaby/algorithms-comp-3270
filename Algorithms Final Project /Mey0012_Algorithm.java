/*
Name:Mohab Yousef
Email:Mey0012@auburn.edu
Id:904154154
Course:COMP3270 by Dr.Yilmaz
Final Project Assignment: Maximum Sum Contiguous Subvector (MSCS) Problem.
I have used sources from StockOver flow, and I have watched youtube videos by Abdul bari, Abna Colleage, and Greek for Greeks
to write this code.
I have compiled and ran this code using Code  Runner(APP) and Jgrasp to make sure it work properly.
this code compute the sum of the subsequence of numbers 
(a subsequence is a consecutive set of one or more numbers) in an array of numbers
*/ 





import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
   
public class Mey0012_Algorithm {      
   
   public static void main(String[] args) {
      try {
         // Read file phw_input.txt
         int[] inputArray = new int[10];
         File file = new File("phw_input.txt"); // read the file
         Scanner scanner = new Scanner(file); // scan the file
         String[] inputLine = scanner.nextLine().split(","); // split lines 
         for (int i = 0; i < 10; i++) {
            inputArray[i] = Integer.parseInt(inputLine[i]);
         }
         scanner.close();
         
         // print outputs
         System.out.println("MSCS determined by each of the algorithms for the input file are as follows: ");
         System.out.println("algorithm-1:\t" + algorithm1(inputArray));
         System.out.println("algorithm-2:\t" + algorithm2(inputArray));
         System.out.println("algorithm-3:\t" + algorithm3(inputArray, 0, inputArray.length - 1));
         System.out.println("algorithm-4:\t" + algorithm4(inputArray));
         
         // Generate 19 arrays with varing sizes and create an array, and generated random numbers to add them to the array
         int[][] inputArrays = new int[19][];
         Random random = new Random();
           for (int i = 0, size = 10; i < 19; i++, size += 5) {
           inputArrays[i] = new int[size];
            for (int j = 0; j < size; j++) {
                       inputArrays[i][j] = random.nextInt(601) - 500; // Random integers from -500 to 500
            }
         }
         
         // Measure and store average times for t1, t2, t3, and t4
         int[][] executionTimes = new int[19][4];
         int N = 500; // run each algorithm 500 times
         for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 4; j++) {
               long start = System.nanoTime();
               for (int k = 0; k < N; k++) {
                  switch (j) {
                     case 0:
                        algorithm1(inputArrays[i]);
                        break;
                     case 1:
                        algorithm2(inputArrays[i]);
                        break;
                     case 2:
                        algorithm3(inputArrays[i], 0, inputArrays[i].length - 1);
                        break;
                     case 3:
                        algorithm4(inputArrays[i]);
                        break;
                  }
               }
               long finish = System.nanoTime();
               executionTimes[i][j] = (int) ((finish - start) / N);
            }
         }
         
         // Calculate compleixity, T1(n), T2(n), T(3)n, and T4(n) and return the ceiling value of T(n)
         int[][] algo = new int[19][4];
         for (int i = 0; i < 19; i++) {
            int n = inputArrays[i].length;
            algo[i][0] = (int) 6 + ((7/6)*n*n*n) + ((41/6)*n) + (7*n*n); //T1(n)
            algo[i][1] = (int) (6*n*n) + (8*n) + 5; //T2(n)
            algo[i][2] = (int) (((14 * n) * Math.log(n) / Math.log(2)) + 14 * n); //T3(n)
            algo[i][3] = (int) (14*n) + 5; //T4(n)
         }
         
         // print out the results of each algorithm 
         //resulting out put file Mohab_Yousef_phw_output.txt showing 
         // the algorithms ouput after the calculation
         FileWriter writer = new FileWriter("Mohabe_Yousef_phw_output.txt");
         writer.write("algorithm-1,algorithm-2,algorithm-3,algorithm-4,T1(n),T2(n),T3(n),T4(n)\n");
         for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 4; j++) {
               writer.write(executionTimes[i][j] + ",");
            }
            for (int j = 0; j < 4; j++) {
               writer.write(algo[i][j] + ",");
            }
            writer.write("\n");
         }
         writer.close(); 
         
         
         // can not use try wihout catch
      } catch (FileNotFoundException e) {
         e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
           }
}
   

// Rewriting the Algorithms that was lissted in the assigment.
// Algorithm1
public static int algorithm1(int[] X) {
   int maxSoFar = 0;
   for (int L = 0; L < X.length; L++) {
      for (int U = L; U < X.length; U++) {
         int sum = 0;
         for (int I = L; I <= U; I++) {
            sum += X[I];
         }
         maxSoFar = Math.max(maxSoFar, sum);
      }
   }
   return Math.max(maxSoFar, 0);
}

// Algorithm2
public static int algorithm2(int[] X) {
   int maxSoFar = 0;
   for (int L = 0; L < X.length; L++) {
      int sum = 0;
      for (int U = L; U < X.length; U++) {
         sum += X[U];
         maxSoFar = Math.max(maxSoFar, sum);
      }
   }
   return Math.max(maxSoFar, 0);
}

// Algorithm3
public static int maxSum(int[] X, int L, int M, int U) {
   int sum = 0;
   int maxToLeft = 0;
   for (int I = M; I >= L; I--) {
      sum += X[I];
      maxToLeft = Math.max(maxToLeft, sum);
   }
   
   sum = 0;
   int maxToRight = 0;
   for (int I = M + 1; I <= U; I++) {
      sum += X[I];
      maxToRight = Math.max(maxToRight, sum);
   }
   
   return maxToLeft + maxToRight;
}

public static int algorithm3(int[] X, int L, int U) {
   if (L > U) {
      return 0;
   }
   
   if (L == U) {
      return Math.max(0, X[L]);
   }
   
   int M = (L + U) / 2;
   int maxSum = maxSum(X, L, M, U);
   int maxInA = algorithm3(X, L, M);
   int maxInB = algorithm3(X, M + 1, U);
   
   return Math.max(maxSum, Math.max(maxInA, maxInB));
}

// Algorithm4
public static int algorithm4(int[] X) {
   int maxSoFar = 0;
   int maxEndingHere = 0;
   
   for (int I = 0; I < X.length; I++) {
      maxEndingHere = Math.max(0, maxEndingHere + X[I]);
      maxSoFar = Math.max(maxSoFar, maxEndingHere);
   }
   
   return maxSoFar;
 }
}