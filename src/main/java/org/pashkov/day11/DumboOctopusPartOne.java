package org.pashkov.day11;

import org.pashkov.AoCUtills.AoCInputReader;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Roman Pashkov created on 13.06.2022 inside the package - org.pashkov.day11
 */
public class DumboOctopusPartOne {

    public static void main(String[] args) {
        List<String> strings = AoCInputReader.readInputFromFile("input11_1.txt");
        int[][] mainarr = convertStringListTo2DArray(strings);
        String[][] mainStrArr = convertIntArrayToString(mainarr);
        int count = 0;
        int flashes = 0;
        while (count < 1) {
            System.out.println("Before increase by one: ");
            printListWithLines(mainStrArr);
            mainStrArr = increaseEachCharacterByOne2(mainStrArr);
            System.out.println("After increase by one: ");
            printListWithLines(mainStrArr);
            mainStrArr = followFlashes(mainStrArr);
            System.out.println("After adopting: ");
            printListWithLines(mainStrArr);
            count++;
        }
    }


    private static String[][] followFlashes(String[][] str2DArray){
        for (int i = 0; i < str2DArray.length; i++) {
            for (int j = 0; j < str2DArray[i].length; j++) {
                String s = str2DArray[i][j];
                if(s.equals("0")){
                    str2DArray[i][j] = "*";
                }
            }
        }
        int temp1FlashesNumber = countFlashes(str2DArray);
        for (int i = 0; i < str2DArray.length; i++) {
            for (int j = 0; j < str2DArray[i].length; j++) {
                String s = str2DArray[i][j];
                if(s.equals("*")){
                    str2DArray = increaseAllAroundByOne(str2DArray, i, j);
//                    List<String> newFlashesAround = findNewFlashesIfOccur(str2DArray, i, j);
//                    for (String newFlashCoordinates : newFlashesAround){
//                        String[] coordinates = newFlashCoordinates.split(" : ");
//                        int x = Integer.parseInt(coordinates[0]);
//                        int y = Integer.parseInt(coordinates[1]);
//                        str2DArray = followFlashes(str2DArray, x, y);
//                    }
                }
            }
        }
        if(temp1FlashesNumber != countFlashes(str2DArray))
            followFlashes(str2DArray);
        return str2DArray;
    }

    private static int countFlashes(String[][] str2DArray) {
        int result = 0;
        for (int i = 0; i < str2DArray.length; i++) {
            for (int j = 0; j < str2DArray[i].length; j++) {
                if(str2DArray[i][j].equals("0")) result = result + 1;
            }
        }
        return result;
    }

    private static List<String> findNewFlashesIfOccur(String[][] ints, int i, int j) {
        List<String> result = new ArrayList<>();
        if (indexExist(ints, i - 1, j - 1) && ints[i - 1][j - 1].equals("0")) {
            int i1 = i - 1;
            int j1 = j - 1;
            result.add(i1+" : "+j1);
        }
        if (indexExist(ints, i - 1, j) && ints[i - 1][j].equals("0")) {
            int i1 = i - 1;
            int j1 = j;
            result.add(i1+" : "+j1);
        }
        if (indexExist(ints, i - 1, j + 1) && ints[i - 1][j + 1].equals("0")) {
            int i1 = i - 1;
            int j1 = j + 1;
            result.add(i1+" : "+j1);
        }
        if (indexExist(ints, i, j - 1) && ints[i][j - 1].equals("0")) {
            int i1 = i;
            int j1 = j - 1;
            result.add(i1+" : "+j1);
        }
        if (indexExist(ints, i, j + 1) && ints[i][j + 1].equals("0")) {
            int i1 = i;
            int j1 = j + 1;
            result.add(i1+" : "+j1);
        }
        if (indexExist(ints, i + 1, j - 1) && ints[i + 1][j - 1].equals("0")) {
            int i1 = i + 1;
            int j1 = j - 1;
            result.add(i1+" : "+j1);
        }
        if (indexExist(ints, i + 1, j) && ints[i + 1][j].equals("0")) {
            int i1 = i + 1;
            int j1 = j;
            result.add(i1+" : "+j1);
        }
        if (indexExist(ints, i + 1, j + 1) && ints[i + 1][j + 1].equals("0")) {
            int i1 = i + 1;
            int j1 = j + 1;
            result.add(i1+" : "+j1);
        }
        return result;
    }

    private static String[][] increaseAllAroundByOne(String[][] ints, int i, int j) {
        if (indexExist(ints, i - 1, j - 1) && !ints[i - 1][j - 1].equals("*")) {
            ints[i-1][j-1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i-1][j-1])));
        }
        if (indexExist(ints, i - 1, j) && !ints[i - 1][j].equals("*")) {
            ints[i-1][j] = String.valueOf(increaseByOne(Integer.parseInt(ints[i-1][j]+1)));
        }
        if (indexExist(ints, i - 1, j + 1) && !ints[i - 1][j + 1].equals("*")) {
            ints[i-1][j+1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i-1][j+1]+1)));
        }
        if (indexExist(ints, i, j - 1) && !ints[i][j - 1].equals("*")) {
            ints[i][j-1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i][j-1]+1)));
        }
        if (indexExist(ints, i, j + 1) && !ints[i][j + 1].equals("*")) {
            ints[i][j+1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i][j+1]+1)));
        }
        if (indexExist(ints, i + 1, j - 1) && !ints[i + 1][j - 1].equals("*")) {
            ints[i+1][j-1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i+1][j-1]+1)));
        }
        if (indexExist(ints, i + 1, j) && !ints[i + 1][j].equals("*")) {
            ints[i+1][j] = String.valueOf(increaseByOne(Integer.parseInt(ints[i+1][j]+1)));
        }
        if (indexExist(ints, i + 1, j + 1) && !ints[i + 1][j + 1].equals("*")) {
            ints[i+1][j+1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i+1][j+1]+1)));
        }
        return ints;
    }

    private static String[][] increaseEachCharacterByOne2(String[][] strings) {
        int[][] ints = convertString2DToInt(strings);
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                int workingInt = ints[i][j];
                if (workingInt + 1 > 9) {
                    ints[i][j] = 0;
                } else
                    ints[i][j] = workingInt + 1;
            }
        }
        return convertIntArrayToString(ints);
    }

    private static String[][] adoptArrayToExistingFlashes(String[][] ints) {
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                String s = ints[i][j];
                if (s.equals("*")) {
                    ints[i][j] = "@";
                }
            }
        }

        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                String s = ints[i][j];
                if (!s.equals("@")) {
                    int intVal = Integer.parseInt(s);
                    int numberOfFlashesNear = findNumberOfFlashesNear(ints, i, j);
                    if (intVal + numberOfFlashesNear > 9) {
                        ints[i][j] = "*";
                    } else {
                        ints[i][j] = String.valueOf(intVal + numberOfFlashesNear);
                    }
                }
            }
        }
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                if (ints[i][j].equals("@")) ints[i][j] = "*";
                else if (ints[i][j].equals("0")) ints[i][j] = "*";
                else if (ints[i][j].equals("*")) ints[i][j] = "0";
            }
        }

        System.out.println(Arrays.deepToString(ints));
        return ints;
    }

    private static int[][] convertString2DToInt(String[][] ints) {
        int[][] result = new int[ints.length][ints[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = Integer.parseInt(ints[i][j]);
            }
        }
        return result;
    }

    private static String[][] convertIntArrayToString(int[][] ints) {
        String[][] result = new String[ints.length][ints[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = String.valueOf(ints[i][j]);
            }
        }
        return result;
    }

    private static int increaseWithValue(int i, int toAdd) {
        return i + toAdd > 9 ? 0 : i + toAdd;
    }

    private static int findNumberOfFlashesNear(String[][] ints, int i, int j) {
        int result = 0;
        if (indexExist(ints, i - 1, j - 1) && ints[i - 1][j - 1].equals("0")) {
            result = result + 1;
        }
        if (indexExist(ints, i - 1, j) && ints[i - 1][j].equals("0")) {
            result = result + 1;
        }
        if (indexExist(ints, i - 1, j + 1) && ints[i - 1][j + 1].equals("0")) {
            result = result + 1;
        }
        if (indexExist(ints, i, j - 1) && ints[i][j - 1].equals("0")) {
            result = result + 1;
        }
        if (indexExist(ints, i, j + 1) && ints[i][j + 1].equals("0")) {
            result = result + 1;
        }
        if (indexExist(ints, i + 1, j - 1) && ints[i + 1][j - 1].equals("0")) {
            result = result + 1;
        }
        if (indexExist(ints, i + 1, j) && ints[i + 1][j].equals("0")) {
            result = result + 1;
        }
        if (indexExist(ints, i + 1, j + 1) && ints[i + 1][j + 1].equals("0")) {
            result = result + 1;
        }
        return result;
    }

    private static boolean arrayContainFlashes(String[][] ints) {
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints[i].length; j++) {
                if (ints[i][j].equals("0")) return true;
            }
        }
        return false;
    }

    private static int increaseByOne(int i) {
        return i + 1 == 10 ? 0 : i + 1;
    }

    private static boolean indexExist(String[][] ints, int i, int j) {
        try {
            String i1 = ints[i][j];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
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
                        if (i1 > 9) {
                            i1 = 9;
                        }
                        chars[i] = String.valueOf(i1).toCharArray()[0];
                    }
                    return chars;
                })
                .map(chars -> Arrays.toString(chars).replaceAll("[\\]\\[,\\s]", ""))
                .collect(Collectors.toList());
    }


    private static void printListWithLines(String[][] str) {
        for (int i = 0; i < str.length; i++) {
            System.out.println(Arrays.toString(str[i]));
        }
        System.out.println("________________");
    }

    public static int[][] convertStringListTo2DArray(List<String> list) {
        int[][] ints = new int[list.size()][list.get(0).length()];
        for (int i = 0; i < ints.length; i++) {
            char[] chars = list.get(i).toCharArray();
            for (int j = 0; j < ints[i].length; j++) {
                ints[i][j] = Integer.parseInt(Character.toString(chars[j]));
            }
        }
        return ints;
    }

    public static List<String> convert2DArrayToList(int[][] ints) {
        List<String> result = new LinkedList<>();
        for (int i = 0; i < ints.length; i++) {
            String s = Arrays.toString(ints[i]).replaceAll("[\\]\\[,\\s]", "");
            result.add(s);
        }
        return result;
    }
}
