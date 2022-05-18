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
        //printMapContent(pointAndAllAroundNumbers2);

        List<String> basinSize = findBasinSize(pointAndAllAroundNumbers2);

        List<Integer> collect = basinSize.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        Collections.reverse(collect);

        int result = 1;
        for (int i = 0; i < 3; i++) {
            result = collect.get(i)*result;
        }

        System.out.println(result);
    }

    private static List<String> findBasinSize(Map<String, List<String>> lowPointsMap) {
        List<String> strings = readInputFromFile();
        String[][] tempArray = convertTo2DArray(strings);

        List<String> basinSizes = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : lowPointsMap.entrySet()) {
            String lowPointKey = entry.getKey();
            String[] splitedKeyByColon = lowPointKey.split(":");
            String s = splitedKeyByColon[1];
            System.out.println("Low point coordinates: "+ s);

            String[] coordinatesOfLowPoint = s.split("-");
            // get x and y for low point
            int lowPointX = Integer.parseInt(coordinatesOfLowPoint[0]);
            int lowPointY = Integer.parseInt(coordinatesOfLowPoint[1]);

            int incrementCHeck = 0;
            List<String> tempCoordinates = new ArrayList<>();
            tempCoordinates.add(lowPointX + " " + lowPointY);
            
            while (listHasUncheckedCoordinates(tempCoordinates)) {
                for (int i = 0; i < tempCoordinates.size(); i++) {
                    if(!tempCoordinates.get(i).matches(".*CHECKED")){
                        String[] splitTempCoordinatesElement = tempCoordinates.get(i).split(" ");
                        int X = Integer.parseInt(splitTempCoordinatesElement[0]);
                        int Y = Integer.parseInt(splitTempCoordinatesElement[1]);
                        tempCoordinates.remove(tempCoordinates.get(i));
                        tempCoordinates.add(X+" "+Y+" CHECKED");

                        int[] ups = move("up", X, Y, tempArray);
                        if(ups.length==2 && alreadyNotHaveSuchCoordinates(tempCoordinates,ups)){
                            tempCoordinates.add(ups[0]+" "+ups[1]);
                            System.out.println("Went up");
                        }
                        int[] downs = move("down", X, Y, tempArray);
                        if(downs.length==2 && alreadyNotHaveSuchCoordinates(tempCoordinates,downs)){
                            tempCoordinates.add(downs[0]+" "+downs[1]);
                            System.out.println("Went down");
                        }
                        int[] rights = move("right", X, Y, tempArray);
                        if(rights.length==2 && alreadyNotHaveSuchCoordinates(tempCoordinates,rights)){
                            tempCoordinates.add(rights[0]+" "+rights[1]);
                            System.out.println("Went right");
                        }
                        int[] lefts = move("left", X, Y, tempArray);
                        if(lefts.length==2 && alreadyNotHaveSuchCoordinates(tempCoordinates,lefts)){
                            tempCoordinates.add(lefts[0]+" "+lefts[1]);
                            System.out.println("Went left");
                        }
                    }
                    System.out.println("Current tempCoordinates array: "+ tempCoordinates);
                }
            }
            basinSizes.add(String.valueOf(tempCoordinates.size()));
        }
        return basinSizes;
    }

    private static boolean alreadyNotHaveSuchCoordinates(List<String> tempCoordinates, int[] coordinateCandidate) {
        for(String s : tempCoordinates){
            if(s.equals(coordinateCandidate[0]+" "+coordinateCandidate[1]) || s.equals(coordinateCandidate[0]+" "+coordinateCandidate[1] + " CHECKED")){
                return false;
            }
        }
        return true;
    }

    private static int[] move(String direction, int x, int y, String[][] tempArray) {
        int [] additionalCoordinates = new int[2];
        String point = "";
        try {
            switch (direction) {
                case "up":
                    point = tempArray[x-1][y];
                    additionalCoordinates[0] = x-1;
                    additionalCoordinates[1] = y;
                    break;
                case "down":
                    point = tempArray[x+1][y];
                    additionalCoordinates[0] = x+1;
                    additionalCoordinates[1] = y;
                    break;
                case "right":
                    point = tempArray[x][y+1];
                    additionalCoordinates[0] = x;
                    additionalCoordinates[1] = y+1;
                    break;
                case "left":
                    point = tempArray[x][y-1];
                    additionalCoordinates[0] = x;
                    additionalCoordinates[1] = y-1;
                    break;
            }
            if(point.equals("9")){
                return new int[0];
            }
        }catch (IndexOutOfBoundsException e){
            return new int[0];
        }
        System.out.println("Found point: "+ point);
        return additionalCoordinates;
    }

    private static boolean listHasUncheckedCoordinates(List<String> tempCoordinates) {
        for (String s : tempCoordinates){
            if(!s.matches(".*CHECKED")) return true;
        }
        return false;
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

    private static Map<String, List<String>> getPointAndAllAroundNumbers2() {
        Map<String, List<String>> result = new HashMap<>();
        List<String> strings = readInputFromFile();
        String[][] tempArray = convertTo2DArray(strings);

        for (int i = 0; i < tempArray.length; i++) {
            for (int j = 0; j < tempArray[i].length; j++) {
                String workingStr = tempArray[i][j];
                List<String> temList = new ArrayList<>();

                List<String> neighbours = new ArrayList<>();
                if (i != 0 && j != 0 && i < tempArray.length - 1 && j < tempArray[i].length - 1) {
                    neighbours.add(tempArray[i - 1][j] + ":" + (i - 1) + "-" + j);
                    neighbours.add(tempArray[i][j - 1] + ":" + (i) + "-" + (j - 1));
                    neighbours.add(tempArray[i][j + 1] + ":" + i + "-" + (j + 1));
                    neighbours.add(tempArray[i + 1][j] + ":" + (i + 1) + "-" + j);
                } else if (i == 0 && j != 0 && j < tempArray[i].length - 1) {
                    neighbours.add(tempArray[i][j - 1] + ":" + i + "-" + (j - 1));
                    neighbours.add(tempArray[i][j + 1] + ":" + i + "-" + (j + 1));
                    neighbours.add(tempArray[i + 1][j] + ":" + (i + 1) + "-" + j);
                } else if (i == tempArray.length - 1 && j != 0 && j < tempArray[i].length - 1) {
                    neighbours.add(tempArray[i][j - 1] + ":" + i + "-" + (j - 1));
                    neighbours.add(tempArray[i][j + 1] + ":" + i + "-" + (j + 1));
                    neighbours.add(tempArray[i - 1][j] + ":" + (i - 1) + "-" + j);
                } else if (i == 0 && j == 0) {
                    neighbours.add(tempArray[i][j + 1] + ":" + i + "-" + (j + 1));
                    neighbours.add(tempArray[i + 1][j] + ":" + (i + 1) + "-" + j);
                } else if (i == 0 && j == tempArray[i].length - 1) {
                    neighbours.add(tempArray[i][j - 1] + ":" + i + "-" + (j - 1));
                    neighbours.add(tempArray[i + 1][j] + ":" + (i + 1) + "-" + j);
                } else if (i == tempArray.length - 1 && j == 0) {
                    neighbours.add(tempArray[i][j + 1] + ":" + i + "-" + (j + 1));
                    neighbours.add(tempArray[i - 1][j] + ":" + (i - 1) + "-" + j);
                } else if (i == tempArray.length - 1 && j == tempArray[i].length - 1) {
                    neighbours.add(tempArray[i][j - 1] + ":" + i + "-" + (j - 1));
                    neighbours.add(tempArray[i - 1][j] + ":" + (i - 1) + "-" + j);
                } else if (i != 0 && i < tempArray.length - 1 && j == 0) {
                    neighbours.add(tempArray[i - 1][j] + ":" + (i - 1) + "-" + j);
                    neighbours.add(tempArray[i][j + 1] + ":" + i + "-" + (j + 1));
                    neighbours.add(tempArray[i + 1][j] + ":" + (i + 1) + "-" + j);
                } else if (i != 0 && i < tempArray.length - 1 && j == tempArray[i].length - 1) {
                    neighbours.add(tempArray[i - 1][j] + ":" + (i - 1) + "-" + j);
                    neighbours.add(tempArray[i][j - 1] + ":" + i + "-" + (j - 1));
                    neighbours.add(tempArray[i + 1][j] + ":" + (i + 1) + "-" + j);
                }
                int i1 = neighbours.stream().map(s -> s.split(":")[0]).collect(Collectors.toList())
                        .stream().mapToInt(Integer::parseInt).min().orElseThrow(NoSuchElementException::new);
                if (Integer.parseInt(workingStr) < i1) {
                    UUID uuid = UUID.randomUUID();
                    result.put(workingStr + ":" + i + "-" + j, neighbours);
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
        Path path = Paths.get("input9_2.txt");
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
