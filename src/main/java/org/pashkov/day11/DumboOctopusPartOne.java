package org.pashkov.day11;

import org.pashkov.AoCUtills.AoCInputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Roman Pashkov created on 13.06.2022 inside the package - org.pashkov.day11
 */
public class DumboOctopusPartOne {

    //7605365445
    //4967076933
    //7486778395
    //8363558368
    //8579507690
    //6389746867
    //4398063943
    //8004003356
    //6068060776
    //7405973748
    public static void main(String[] args) {
        List<String> strings = AoCInputReader.readInputFromFile("input11_2.txt");
        int count = 0;
        int flashes = 0;
        while (count < 100) {
            strings = increaseEachCharacterByOne(strings);
            printListWithLines(strings);
            int tempFlashes = countFlashes(strings);
            System.out.println("New flashes: "+tempFlashes);
            flashes = flashes + tempFlashes;
            System.out.println("All flashes: "+ flashes);
            System.out.println("-----------------------");
             correctByZero(strings);
            count++;
        }
    }

    private static List<String> correctByZero(List<String> strings) {
      int [][] array =  new int[strings.size()][strings.get(0).length()];
        for (int i = 0; i < strings.size(); i++) {
            char[] chars = strings.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                array[i][j] = Character.getNumericValue(chars[j]);
                int toCheck = array[i][j];
                if(toCheck==0){
                    try{
                        if(array[i][j+1] != 0)
                            array[i][j+1] = Character.getNumericValue(array[i][j+1])+1;
                        if(array[i][j-1] != 0)
                            array[i][j-1] = Character.getNumericValue(array[i][j-1])+1;
                        if(array[i+1][j] != 0)
                            array[i+1][j] = Character.getNumericValue(array[i+1][j])+1;
                        if(array[i-1][j] != 0)
                            array[i-1][j] = Character.getNumericValue(array[i-1][j])+1;
                        if(array[i+1][j-1] != 0)
                            array[i+1][j-1] = Character.getNumericValue(array[i+1][j-1])+1;
                        if(array[i+1][j+1] != 0)
                            array[i+1][j+1] = Character.getNumericValue(array[i+1][j+1])+1;
                        if(array[i-1][j-1] != 0)
                            array[i-1][j-1] = Character.getNumericValue(array[i-1][j-1])+1;
                        if(array[i-1][j+1] != 0)
                            array[i-1][j+1] = Character.getNumericValue(array[i-1][j+1])+1;
                    }catch (IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.print(Arrays.deepToString(array));

      return null;
    }

    private static int countFlashes(List<String> strings) {
        List<String> collect = strings.stream()
                .map(String::toCharArray)
                .map(chars -> {
                    List<Integer> tempList = new ArrayList<>();
                    int tempInt = 0;
                    for (int i = 0; i < chars.length; i++) {
                        char aChar = chars[i];
                        int i1 = Integer.parseInt(String.valueOf(aChar));
                        if (i1 == 0) {
                            tempInt = tempInt + 1;
                        }
                    }
                    tempList.add(tempInt);
                    String s = tempList.toString().replaceAll("[\\]\\[,]", "");
                    chars = s.toCharArray();
                    return chars;
                })
                .map(chars -> Arrays.toString(chars).replaceAll("[\\]\\[,\\s]", ""))
                .collect(Collectors.toList());

        int sum = collect.stream().mapToInt(Integer::parseInt).sum();
        return sum;
    }
    private static List<String> increaseEachCharacterByOne(List<String> strings) {
        return strings.stream()
                .map(String::toCharArray)
                .map(chars -> {
                    for (int i = 0; i < chars.length; i++) {
                        char aChar = chars[i];
                        int i1 = Integer.parseInt(String.valueOf(aChar));
                        i1 = i1 + 1;
                        if(i1>9) i1 = 0;
                        chars[i] = String.valueOf(i1).toCharArray()[0];
                    }
                    return chars;
                })
                .map(chars -> Arrays.toString(chars).replaceAll("[\\]\\[,\\s]", ""))
                .collect(Collectors.toList());
    }

//    private static void get

    private static void printListWithLines(List<String> strings) {
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
