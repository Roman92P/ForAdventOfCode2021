package org.pashkov.day11;

import org.pashkov.AoCUtills.AoCInputReader;

import java.util.Arrays;
import java.util.List;

/**
 * Roman Pashkov created on 13.06.2022 inside the package - org.pashkov.day11
 */
public class DumboOctupusPartTwo {
    public static void main(String[] args) {
        List<String> strings = AoCInputReader.readInputFromFile("input11.txt");
        int[][] mainarr = convertStringListTo2DArray(strings);
        int allFlashes = mainarr[0].length * mainarr.length;
        System.out.println("All flashes: "+allFlashes);
        int stepWithAllFlashes=0;
        String[][] mainStrArr = convertIntArrayToString(mainarr);
        int count = 1;
        int flashes = 0;
        while (true) {
            mainStrArr = increaseEachCharacterByOne(mainStrArr);
            mainStrArr = followFlashes(mainStrArr);
            flashes = flashes + countFlashes(mainStrArr);
            if(countFlashes(mainStrArr) == allFlashes){
                stepWithAllFlashes = count;
                break;
            }
            count++;

        }
        System.out.println("Flashes number: "+ flashes);
        System.out.println("Full flash step: "+ stepWithAllFlashes);
    }

    private static String[][] followFlashes(String[][] str2DArray) {
        for (int i = 0; i < str2DArray.length; i++) {
            for (int j = 0; j < str2DArray[i].length; j++) {
                String s = str2DArray[i][j];
                if (s.equals("0")) {
                    str2DArray[i][j] = "*";
                }
            }
        }
        int temp1FlashesNumber = countFlashes(str2DArray);
        for (int i = 0; i < str2DArray.length; i++) {
            for (int j = 0; j < str2DArray[i].length; j++) {
                String s = str2DArray[i][j];
                if (s.equals("*")) {
                    str2DArray = increaseAllAroundByOne(str2DArray, i, j);
                }
            }
        }
        for (int i = 0; i < str2DArray.length; i++) {
            for (int j = 0; j < str2DArray[i].length; j++) {
                String s = str2DArray[i][j];
                if (s.equals("*")) {
                    str2DArray[i][j] = "@";
                }
            }
        }
        if (temp1FlashesNumber < countFlashes(str2DArray)) {
            followFlashes(str2DArray);
        }
        if(temp1FlashesNumber == countFlashes(str2DArray)){
            for (int i = 0; i < str2DArray.length; i++) {
                for (int j = 0; j < str2DArray[i].length; j++) {
                    String s = str2DArray[i][j];
                    if (s.equals("@")) {
                        str2DArray[i][j] = "0";
                    }
                }
            }
        }
        return str2DArray;
    }

    private static int countFlashes(String[][] str2DArray) {
        int result = 0;
        for (String[] strings : str2DArray) {
            for (String string : strings) {
                if (string.equals("0")) result = result + 1;
            }
        }
        return result;
    }

    private static String[][] increaseAllAroundByOne(String[][] ints, int i, int j) {
        if (indexExist(ints, i - 1, j - 1) && !ints[i - 1][j - 1].equals("*") && !ints[i - 1][j - 1].equals("@")&& !ints[i - 1][j - 1].equals("0")) {
            ints[i - 1][j - 1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i - 1][j - 1])));
        }
        if (indexExist(ints, i - 1, j) && !ints[i - 1][j].equals("*") && !ints[i - 1][j].equals("@") && !ints[i - 1][j].equals("0")) {
            ints[i - 1][j] = String.valueOf(increaseByOne(Integer.parseInt(ints[i - 1][j])));
        }
        if (indexExist(ints, i - 1, j + 1) && !ints[i - 1][j + 1].equals("*") && !ints[i - 1][j + 1].equals("@") && !ints[i - 1][j + 1].equals("0")) {
            ints[i - 1][j + 1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i - 1][j + 1])));
        }
        if (indexExist(ints, i, j - 1) && !ints[i][j - 1].equals("*") && !ints[i][j - 1].equals("@") && !ints[i][j - 1].equals("0")) {
            ints[i][j - 1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i][j - 1])));
        }
        if (indexExist(ints, i, j + 1) && !ints[i][j + 1].equals("*") && !ints[i][j + 1].equals("@") && !ints[i][j + 1].equals("0")) {
            ints[i][j + 1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i][j + 1])));
        }
        if (indexExist(ints, i + 1, j - 1) && !ints[i + 1][j - 1].equals("*") && !ints[i + 1][j - 1].equals("@") && !ints[i + 1][j - 1].equals("0")) {
            ints[i + 1][j - 1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i + 1][j - 1])));
        }
        if (indexExist(ints, i + 1, j) && !ints[i + 1][j].equals("*") && !ints[i + 1][j].equals("@") && !ints[i + 1][j].equals("0")) {
            ints[i + 1][j] = String.valueOf(increaseByOne(Integer.parseInt(ints[i + 1][j])));
        }
        if (indexExist(ints, i + 1, j + 1) && !ints[i + 1][j + 1].equals("*") && !ints[i + 1][j + 1].equals("@") && !ints[i + 1][j + 1].equals("0")) {
            ints[i + 1][j + 1] = String.valueOf(increaseByOne(Integer.parseInt(ints[i + 1][j + 1])));
        }
        return ints;
    }

    private static int increaseByOne(int i) {
        return i + 1 == 10 ? 0 : i + 1;
    }

    private static String[][] increaseEachCharacterByOne(String[][] strings) {
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

    private static boolean indexExist(String[][] ints, int i, int j) {
        try {
            String s = ints[i][j];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private static void printListWithLines(String[][] str) {
        for (String[] strings : str) {
            System.out.println(Arrays.toString(strings));
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
}
