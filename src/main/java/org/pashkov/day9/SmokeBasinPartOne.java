package org.pashkov.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmokeBasinPartOne {
    public static void main(String[] args) {

        Map<String, List<String>> pointsMap = getPointAndAllAroundNumbers();

        for (Map.Entry<String, List<String>> entry : pointsMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            System.out.println(key + " : " + value);
        }
    }

    private static Map<String, List<String>> getPointAndAllAroundNumbers() {
        Map<String, List<String>> result = new HashMap<>();
        List<String> strings = readInputFromFile();
        String[][] tempArray = convertTo2DArray(strings);

        for (int i = 0; i < tempArray.length; i++) {
            String toCompare = tempArray[i][0];
            for (int j = 1; j < tempArray[i].length; j++) {
                boolean addedPrevious = false;
                if (Integer.parseInt(tempArray[i][j]) < Integer.parseInt(toCompare)) {
                    toCompare = tempArray[i][j];
                    if((j+1)>=tempArray[i].length){
                        String mapKey = tempArray[i][j];
                        List<String> temList = new ArrayList<>();
                        temList.add(tempArray[i][j-1]);
                        if ((i + 1) < tempArray.length) {
                            temList.add(tempArray[i + 1][j - 1]);
                            if (addedPrevious) {
                                temList.add(tempArray[i + 1][j - 2]);
                            }
                            temList.add(tempArray[i + 1][j]);
                        }
                        if ((i - 1) >= 0) {
                            temList.add(tempArray[i - 1][j - 1]);
                            if (addedPrevious) {
                                temList.add(tempArray[i - 1][j - 2]);
                            }
                            temList.add((tempArray[i-1][j]));
                        }
                        result.put(mapKey, temList);
                    }
                } else {
                    String mapKey = tempArray[i][j - 1];
                    List<String> temList = new ArrayList<>();
                    if (j > 1) {
                        temList.add(tempArray[i][j - 2]);
                        addedPrevious = true;
                    }
                    temList.add(tempArray[i][j]);
                    if ((i + 1) < tempArray.length) {
                        temList.add(tempArray[i + 1][j - 1]);
                        if (addedPrevious) {
                            temList.add(tempArray[i + 1][j - 2]);
                        }
                        temList.add(tempArray[i + 1][j]);
                    }
                    if ((i - 1) >= 0) {
                        temList.add(tempArray[i - 1][j - 1]);
                        if (addedPrevious) {
                            temList.add(tempArray[i - 1][j - 2]);
                        }
                        temList.add((tempArray[i-1][j]));
                    }
                    result.put(mapKey, temList);
                }
            }
        }
        return result;
    }

    private static String[][] convertTo2DArray(List<String> strings) {
        String[][] result = new String[strings.size()][strings.get(0).length()];
        for (int i = 0; i < strings.size(); i++) {
            result[i] = strings.get(i).split("");
        }
        return result;
    }

    // read input
    public static List<String> readInputFromFile() {
        Path path = Paths.get("input9.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //print 2D array
    public static void print2DArray(String[][] toPrint) {
        for (int i = 0; i < toPrint.length; i++) {
            for (int j = 0; j < toPrint[i].length; j++) {
                System.out.print(toPrint[i][j]);
            }
            System.out.println("\n");
        }
    }
}
