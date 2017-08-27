import java.sql.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    private static int getDataFromUser(){
        System.out.print("\033[H\033[2J");
        int limit = 0;
        boolean dataIsInvalid = true;
        while (dataIsInvalid){
            Scanner reader = new Scanner(System.in);
            System.out.println("Please, enter a positive integer:");
            try {
                limit = reader.nextInt();
                if (limit > 1){
                    dataIsInvalid = false;
                } else {
                    System.out.println("The number must be greater than 1.\n");
                }
            } catch (Exception error) {
                System.out.println("We work with numbers, mate, not with letters.\n");
            }
        }
        return limit;
    }

    public static void main(String[] args) {
        int upperValue = getDataFromUser();

        // solution with simple array
        long start = System.nanoTime();
        long startInMillisec = start / 1000000;

        boolean[] booleanValues = new boolean[upperValue+1];
        for (int index = 2; index <= upperValue; index++){
            booleanValues[index] = true;
        }

        for (int index = 2; index < Math.sqrt(upperValue); index++){
            if (booleanValues[index] == true){
                for (int innerIndex = index*index; innerIndex<=upperValue; innerIndex = innerIndex+index){
                    booleanValues[innerIndex] = false;
                }
            }
        }

        int counterOfPrimes = 0;
        for (int i = 2; i <= upperValue; i++) {
            if (booleanValues[i]){
                counterOfPrimes++;
            }
        }

        long end = System.nanoTime();
        long endInMillisec = end / 1000000;
        displayResult(counterOfPrimes, end-start, endInMillisec-startInMillisec, "simple array");


        // solution with simple array, without counter loop at the end (faster solution)
        start = System.nanoTime();
        startInMillisec = start / 1000000;

        boolean[] booleanValues2 = new boolean[upperValue+1];
        int counter = upperValue-1;
        for (int index = 2; index <= upperValue; index++){
            booleanValues2[index] = true;
        }
        for (int index = 2; index*index <= upperValue; index++){
            if (booleanValues2[index] == true){
                for (int innerIndex = index; innerIndex*index<=upperValue; innerIndex++){
                    if(booleanValues2[innerIndex*index]){
                        counter--;
                    }
                    booleanValues2[innerIndex*index] = false;
                }
            }
        }

        end = System.nanoTime();
        endInMillisec = end / 1000000;
        displayResult(counter, end-start, endInMillisec-startInMillisec, "simple array without counter loop at the end");


        // solution with TreeMap
        start = System.nanoTime();
        startInMillisec = start / 1000000;

        TreeMap<Integer, Boolean> booleanTreeMap = new TreeMap<>();
        booleanTreeMap.put(0, false);
        booleanTreeMap.put(1, false);
        for (int key = 2; key <= upperValue; key++){
            booleanTreeMap.put(key, true);
        }

        for (int key = 2; key < Math.sqrt(upperValue); key++){
            if(booleanTreeMap.get(key) == true){
                for (int innerKey = key*key; innerKey<=upperValue; innerKey = innerKey+key){
                    booleanTreeMap.put(innerKey, false);
                }
            }
        }

        counterOfPrimes = 0;
        for (int key = 2; key <= upperValue; key++) {
            if (booleanTreeMap.get(key)){
                counterOfPrimes++;
            }
        }

        end = System.nanoTime();
        endInMillisec = end / 1000000;
        displayResult(counterOfPrimes, end-start, endInMillisec-startInMillisec, "TreeMap");
    }

    private static void displayResult(int counter, long nano, long milli, String type){
        System.out.println("Solution with " + type);
        System.out.println("\nNumber of primes: " + counter);
        System.out.println("\nProcessing time: " + nano + " nanosec, " + milli + " millisec");
        System.out.println("==========================================");
    }
}
