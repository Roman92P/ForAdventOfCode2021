package org.pashkov.day3;

import org.pashkov.day1.Utill;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PartTwoBinaryDiagnostic {

    public static void main(String[] args) {

        int[] ints = calculateOxygenAndCo2Ratings();
        System.out.println(ints[0]*ints[1]);
    }

    public static int[] calculateOxygenAndCo2Ratings(){
        List<String[]> inputBinaryNumbersForOxygen = getInputBinaryNumbers();
        int countA = inputBinaryNumbersForOxygen.get(0).length - 1;
        //calculate oxygen
        int count1 = 0;
        while (inputBinaryNumbersForOxygen.size()>1){
            System.out.println("1");
            inputBinaryNumbersForOxygen = getOxygenGeneratorRating(inputBinaryNumbersForOxygen,count1);
            count1++;
        }
        List<String[]> inputBinaryNumbersForCO2 = getInputBinaryNumbers();
        int count2 = 0;
        //calculate co2
        while (inputBinaryNumbersForCO2.size()>1){
            System.out.println("2");
            inputBinaryNumbersForCO2 = getCO2ScrubberRating(inputBinaryNumbersForCO2,count2);
            count2++;
        }

        int[] resultArr = new int[2];
        resultArr[0] = calculateValueFromBinary(inputBinaryNumbersForOxygen);
        resultArr[1] = calculateValueFromBinary(inputBinaryNumbersForCO2);

        return resultArr;
    }

    public static int calculateValueFromBinary(List<String[]>binnaryNumber){
        if(binnaryNumber.size()==1){
            return Integer.parseInt(Arrays.toString(binnaryNumber.get(0)).replaceAll("\\D",""),2);
        }
        return 0;
    }
// most common
    public static List<String[]> getOxygenGeneratorRating(List<String[]>inputBinaryList, int bitPossition){
        List<String[]> result = new LinkedList<>();
        int controlSum = calculateSumOfBitsOnXPossition(inputBinaryList, bitPossition);
        double i = (double) controlSum / inputBinaryList.size();
        System.out.println(i);
        if(i>=0.5){
         for (String[]sArr : inputBinaryList){
             if(Integer.parseInt(sArr[bitPossition])==1) result.add(sArr);
         }
        }
        if(i<0.5){
            for (String[]sArr : inputBinaryList){
                if(Integer.parseInt(sArr[bitPossition])==0) result.add(sArr);
            }
        }
        return result;
    }

    //  least common
    public static List<String[]> getCO2ScrubberRating(List<String[]>inputBinaryList, int bitPossition){
        List<String[]> result = new LinkedList<>();
        int controlSum = calculateSumOfBitsOnXPossition(inputBinaryList, bitPossition);
        double i = (double) controlSum / inputBinaryList.size();
        if(i>=0.5){
            for (String[]sArr : inputBinaryList){
                if(Integer.parseInt(sArr[bitPossition])==0) result.add(sArr);
            }
        }
        if(i<0.5){
            for (String[]sArr : inputBinaryList){
                if(Integer.parseInt(sArr[bitPossition])==1) result.add(sArr);
            }
        }
        return result;
    }

    public static List<String[]>getInputBinaryNumbers(){
        List<String> strings = Utill.readInputFromFile("input3.txt");
        List<String[]> collect = strings.stream().map(s -> s.split("")).collect(Collectors.toList());
        return collect;
    }

    private static int calculateSumOfBitsOnXPossition(List<String[]> inputBinaryList, int bitPossition) {
        int resultSum = 0;
        for (int i = 0; i < inputBinaryList.size(); i++) {
            resultSum = resultSum + Integer.parseInt(inputBinaryList.get(i)[bitPossition]);
        }
        return resultSum;
    }

}

