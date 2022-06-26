package org.pashkov.day11;

import org.apache.commons.lang3.ArrayUtils;
import org.pashkov.AoCUtills.AoCInputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
        while (count < 2) {
            strings = increaseEachCharacterByOne(strings);
            printListWithLines(strings);
            strings = correctByZero(strings);
            System.out.println("All flashes: " + flashes);
            int tempFlashes = countFlashes(strings);
            System.out.println("New flashes: " + tempFlashes);
            flashes = flashes + tempFlashes;
            count++;
            System.out.println("-----------------------");
        }
    }

    private static List<String> correctByZero(List<String> strings) {
        int[][] ints = convertStringListTo2DArray(strings);
        System.out.println(Arrays.deepToString(ints));
        for (int i = 0; i < ints.length; i++) {
            int[] intRow = ints[i];
            System.out.println("Input row: "+Arrays.toString(intRow));
            if(ArrayUtils.contains(intRow, 0)){
                for (int j = 0; j < intRow.length; j++) {
                    int toCheck = intRow[j];
                    if (toCheck == 0) {
                        try {
                            if (ints[i][j + 1] != 0)
                                ints[i][j + 1] = increaseByOne(ints[i][j + 1]);
                            if (ints[i][j - 1] != 0)
                                ints[i][j - 1] = increaseByOne(ints[i][j - 1]);
                            if (ints[i + 1][j] != 0)
                                ints[i + 1][j] = increaseByOne(ints[i + 1][j]);
                            if (ints[i - 1][j] != 0)
                                ints[i - 1][j] = increaseByOne(ints[i - 1][j]);
                            if (ints[i + 1][j - 1] != 0)
                                ints[i + 1][j - 1] = increaseByOne(ints[i + 1][j - 1]);
                            if (ints[i + 1][j + 1] != 0)
                                ints[i + 1][j + 1] = increaseByOne(ints[i + 1][j + 1]);
                            if (ints[i - 1][j - 1] != 0)
                                ints[i - 1][j - 1] = increaseByOne(ints[i - 1][j - 1]);
                            if (ints[i - 1][j + 1] != 0)
                                ints[i - 1][j + 1] = increaseByOne(ints[i - 1][j + 1]);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("Output row: "+Arrays.toString(intRow));
        }
        System.out.print(Arrays.deepToString(ints));

        return convert2DArrayToList(ints);
    }

    private static int increaseByOne(int i) {
        return i+1 == 10? 0: i+1;
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
                        if (i1 > 9) i1 = 0;
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

    public static int[][] convertStringListTo2DArray(List<String> list) {
        int[][] ints = new int[list.size()][list.get(0).length()];
        for (int i = 0; i < ints.length; i++) {
            char[] chars = list.get(i).toCharArray();
            for (int j = 0; j < ints[i].length; j++) {
                ints[i][j] = Character.getNumericValue(chars[j]);
            }
        }
        return ints;
    }

    public static List<String> convert2DArrayToList(int[][]ints) {
        List<String>result = new LinkedList<>();
        for (int i = 0; i < ints.length; i++) {
            String s = Arrays.toString(ints[i]).replaceAll("[\\]\\[,\\s]", "");
            result.add(s);
        }
        return result;
    }
}
