package org.pashkov.day3;

import org.pashkov.day1.Utill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryDiagnostic {
    public static void main(String[] args) {
        List<int[]> gammaAndEpsilonBinarArrays = getGammaAndEpsilonBinarArrays();

        List<Integer> decimalList = convertBinareToDecimal(gammaAndEpsilonBinarArrays);

        System.out.println(decimalList.get(0)*decimalList.get(1));
    }

    private static List<Integer> convertBinareToDecimal(List<int[]> gammaAndEpsilonBinarArrays) {
        List<Integer> resultList = new ArrayList<>();
        for (int[] a : gammaAndEpsilonBinarArrays){
            String s = Arrays.toString(a);
            int i = Integer.parseInt(s.replaceAll("\\D",""), 2);
            resultList.add(i);
        }
        return resultList;
    }

    public static List<int []> getGammaAndEpsilonBinarArrays(){
        List<String> strings = Utill.readInputFromFile("input3.txt");
        List<String[]> collect = strings.stream().map(s -> s.split("")).collect(Collectors.toList());
        int[] arrWithSum = new int[collect.get(0).length];

        for (int i = 0; i < collect.size(); i++) {
            String[] collectArray = collect.get(i);
            for (int j = 0; j < collectArray.length; j++) {
                arrWithSum[j] = arrWithSum[j]+Integer.parseInt(collectArray[j]);
            }
        }
        int[]resultGamma = new int[arrWithSum.length];
        int[]resultEpsilon = new int[arrWithSum.length];
        for (int i = 0; i < arrWithSum.length; i++) {
            double temp =  (double) arrWithSum[i]/collect.size();
            if(temp>=0.5){
                resultGamma[i]=1;
                resultEpsilon[i]=0;
            }
            else {
                resultGamma[i]=0;
                resultEpsilon[i]=1;
            }
        }
        return Arrays.asList(resultGamma,resultEpsilon);
    }
}
