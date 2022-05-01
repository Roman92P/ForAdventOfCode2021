package org.pashkov.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SmokeBasinPartTwo {
    public static void main(String[] args) {
        Map<String, List<String>> pointAndAllAroundNumbers2 = getPointAndAllAroundNumbers2();
        printMapContent(pointAndAllAroundNumbers2);

        List<String> basinSize = findBasinSize(pointAndAllAroundNumbers2);

    }

    private static int calculateBasinSize(Map<String, List<String>> lowestPoints) {
        int result = 0;
        for (Map.Entry<String, List<String>> entry : lowestPoints.entrySet()) {
            String s = entry.getKey().substring(0, 1);
            System.out.println(s);
            result = result + Integer.parseInt(s) + 1;
        }
        return result;
    }

    private static List<String> findBasinSize(Map<String, List<String>> lowPointsMap){
        List<String> strings = readInputFromFile();
        String[][] tempArray = convertTo2DArray(strings);

        return null;
    }

    private static Map<String, List<String>> getPointAndAllAroundNumbers2() {
        Map<String, List<String>> result = new HashMap<>();
        List<String> strings = readInputFromFile();
        String[][] tempArray = convertTo2DArray(strings);

        for (int i = 0; i < tempArray.length; i++) {
            for (int j = 0; j < tempArray[i].length; j++) {
                String workingStr = tempArray[i][j];
                List<String> temList = new ArrayList<>();

                List<String> neighbours = new ArrayList<>();
                if(i != 0 && j != 0 && i < tempArray.length-1 && j < tempArray[i].length-1){
                    neighbours.add(tempArray[i-1][j] +":"+(i-1)+"-"+j);
                    neighbours.add(tempArray[i][j-1] +":"+(i)+"-"+(j-1));
                    neighbours.add(tempArray[i][j+1] +":"+i+"-"+(j+1));
                    neighbours.add(tempArray[i+1][j] +":"+(i+1)+"-"+j);
                } else if(i == 0 && j != 0 && j < tempArray[i].length-1){
                    neighbours.add(tempArray[i][j-1] +":"+i+"-"+(j-1));
                    neighbours.add(tempArray[i][j+1] +":"+i+"-"+(j+1));
                    neighbours.add(tempArray[i+1][j] +":"+(i+1)+"-"+j);
                } else if (i == tempArray.length-1 && j != 0 && j < tempArray[i].length-1){
                    neighbours.add(tempArray[i][j-1] +":"+i+"-"+(j-1));
                    neighbours.add(tempArray[i][j+1] +":"+i+"-"+(j+1));
                    neighbours.add(tempArray[i-1][j] +":"+(i-1)+"-"+j);
                } else if (i == 0 && j == 0){
                    neighbours.add(tempArray[i][j+1] +":"+i+"-"+(j+1));
                    neighbours.add(tempArray[i+1][j] +":"+(i+1)+"-"+j);
                }else if(i == 0 && j == tempArray[i].length-1){
                    neighbours.add(tempArray[i][j-1] +":"+i+"-"+(j-1));
                    neighbours.add(tempArray[i+1][j] +":"+(i+1)+"-"+j);
                }else if (i == tempArray.length-1 && j == 0){
                    neighbours.add(tempArray[i][j+1] +":"+i+"-"+(j+1));
                    neighbours.add(tempArray[i-1][j] +":"+(i-1)+"-"+j);
                }else if (i == tempArray.length-1 && j == tempArray[i].length-1){
                    neighbours.add(tempArray[i][j-1] +":"+i+"-"+(j-1));
                    neighbours.add(tempArray[i-1][j] +":"+(i-1)+"-"+j);
                } else if (i !=0 && i < tempArray.length-1 && j ==0){
                    neighbours.add(tempArray[i-1][j] +":"+(i-1)+"-"+j);
                    neighbours.add(tempArray[i][j+1] +":"+i+"-"+(j+1));
                    neighbours.add(tempArray[i+1][j] +":"+(i+1)+"-"+j);
                } else if (i !=0 && i < tempArray.length-1 && j == tempArray[i].length-1){
                    neighbours.add(tempArray[i-1][j] +":"+(i-1)+"-"+j);
                    neighbours.add(tempArray[i][j-1] +":"+i+"-"+(j-1));
                    neighbours.add(tempArray[i+1][j] +":"+(i+1)+"-"+j);
                }
                int i1 = neighbours.stream().map(s -> s.split(":")[0]).collect(Collectors.toList())
                        .stream().mapToInt(Integer::parseInt).min().orElseThrow(NoSuchElementException::new);
                if(Integer.parseInt(workingStr)<i1){
                    UUID uuid = UUID.randomUUID();
                    result.put(workingStr+":"+i+"-"+j, neighbours);
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
        Path path = Paths.get("input9_3.txt");
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

    private static void printMapContent(Map<?, ?> mapToBePrinted) {
        for (Map.Entry<?, ?> entry : mapToBePrinted.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " : " + value);
        }
    }
}
