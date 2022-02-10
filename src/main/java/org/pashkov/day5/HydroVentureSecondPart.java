package org.pashkov.day5;

import org.pashkov.day1.Utill;

import java.util.*;

public class HydroVentureSecondPart {
    public static void main(String[] args) {

//        String asd="6,4 -> 2,0";
//        String[] split = asd.split(" -> ");
//
//        List<String[]> allNeededCoordinates = findAllNeededCordinates(split);
//        for (String[]s : allNeededCoordinates){
//            System.out.println(Arrays.toString(s));
//        }


        Map<String, String[]> stringMap = putCoordinatesOntoDiagram();
        //count two lines overlap
        int sum = 0;
        for (Map.Entry<String, String[]> entry : stringMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            for (String s : value) {
                if (s.matches("[0-9]") && Integer.parseInt(s) > 1) {
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }

    private static Map<String, String[]> putCoordinatesOntoDiagram() {
        String[] allLines = findAllLines();
        List<String> emptyDiagram = createEmptyDiagram(allLines);
        Map<String, String[]> result;
        result = fillMapWithValues(emptyDiagram);
        for (String lineCoordinatesString : allLines) {
            String[] split = lineCoordinatesString.split(" -> ");
            List<String[]> allNeededCoordinates = findAllNeededCordinates(split);
            for (String[] arrCoordinates : allNeededCoordinates) {
                String x = arrCoordinates[0].trim();
                String y = arrCoordinates[1].trim();
                String[] strings = new String[0];
                if (result.containsKey(y)) {
                    strings = result.get(y);
                    for (int i = 0; i < strings.length; i++) {
                        if (x.equals(String.valueOf(i))) {
                            if (strings[i].matches("[*]")) strings[i] = "1";
                            else if (strings[i].matches("[0-9]"))
                                strings[i] = String.valueOf(Integer.parseInt(strings[i]) + 1);
                        }
                    }
                }
                result.put(y, strings);
            }
        }
        return result;
    }

    private static Map<String, String[]> fillMapWithValues(List<String> emptyDiagram) {
        Map<String, String[]> result = new HashMap<>();
        for (int i = 0; i < emptyDiagram.size(); i++) {
            result.put(String.valueOf(i), emptyDiagram.get(i).replaceAll("[\\]\\[\\s]", "").split(""));
        }
        return result;
    }

    private static List<String[]> findAllNeededCordinates(String[] split) {
        String[] x1y1 = split[0].split(",");
        String[] x2y2 = split[1].split(",");

        List<String[]> result = new LinkedList<>();
        result.add(x1y1);

        if(!x1y1[0].equals(x2y2[0]) && !x2y2[1].equals(x1y1[1])) {


            int x = Integer.parseInt(x1y1[0]);
            int y = Integer.parseInt(x1y1[1]);

            int xToReach = Integer.parseInt(x2y2[0]);
            int yToReach = Integer.parseInt(x2y2[1]);

            int xDirection = xToReach - x;
            int yDirection = yToReach - y;

            int count = Math.abs(xDirection);

            for (int i = 1; i < count; i++) {
                String[] strings = new String[2];
                if(xDirection<0){
                    x = x-1;
                } else x = x+1;
                if(yDirection<0){
                    y = y-1;
                } else y = y+1;

                strings[0] = String.valueOf(x);
                strings[1] = String.valueOf(y);
                result.add(strings);

            }


        }else {
            if (x1y1[0].equals(x2y2[0])) {
                Integer max = Collections.max(Arrays.asList(Integer.parseInt(x1y1[1]), Integer.parseInt(x2y2[1])));
                Integer min = Collections.min(Arrays.asList(Integer.parseInt(x1y1[1]), Integer.parseInt(x2y2[1])));
                for (int i = 1; i < max - min; i++) {
                    String x = x1y1[0];
                    String y = String.valueOf(min + i);

                    String[] newArr = new String[2];
                    newArr[0] = x;
                    newArr[1] = y;

                    result.add(newArr);
                }
            }
            if (x1y1[1].equals(x2y2[1])) {
                Integer max = Collections.max(Arrays.asList(Integer.parseInt(x1y1[0]), Integer.parseInt(x2y2[0])));
                Integer min = Collections.min(Arrays.asList(Integer.parseInt(x1y1[0]), Integer.parseInt(x2y2[0])));

                for (int i = 1; i < max - min; i++) {
                    String x = String.valueOf(min + i);
                    String y = x1y1[1];

                    String[] newArr = new String[2];
                    newArr[0] = x;
                    newArr[1] = y;

                    result.add(newArr);
                }
            }
        }
        result.add(x2y2);
        return result;
    }


    private static List<String> createEmptyDiagram(String[] allLines) {
        int maxX = findMaxNumber(allLines)[0];
        int maxY = findMaxNumber(allLines)[1];
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < maxY + 1; i++) {
            String[] dotString = new String[maxX + 1];
            for (int j = 0; j < dotString.length; j++) {
                dotString[j] = "*";
            }
            resultList.add(Arrays.toString(dotString).replaceAll(",", ""));
        }
        return resultList;
    }

    private static int[] findMaxNumber(String[] allLines) {
        int maxX = 0;
        int maxY = 0;
        for (String s : allLines) {
            String tempS = s.replaceAll(" -> ", ",");
            String[] tempArray = tempS.split(",");
            if (Integer.parseInt(tempArray[0]) > maxX) maxX = Integer.parseInt(tempArray[0]);
            if (Integer.parseInt(tempArray[2]) > maxX) maxX = Integer.parseInt(tempArray[2]);
            if (Integer.parseInt(tempArray[1]) > maxY) maxY = Integer.parseInt(tempArray[1]);
            if (Integer.parseInt(tempArray[3]) > maxY) maxY = Integer.parseInt(tempArray[3]);
        }
        int[] result = new int[2];
        result[0] = maxX;
        result[1] = maxY;
        return result;
    }

    private static String[] findAllLines() {
        String[] lines = coordinatesArray();
        String[] resultArray = new String[0];
        for (String s : lines) {
            String tempString = s.replaceAll(" -> ", ",");
            String[] tempArray = tempString.split(",");
            resultArray = addLineToArray(resultArray, s);
        }
        return resultArray;
    }

    private static String[] addLineToArray(String[] startArray, String s) {
        String[] result = Arrays.copyOf(startArray, startArray.length + 1);
        result[result.length - 1] = s;
        return result;
    }

    private static String[] coordinatesArray() {
        List<String> strings = Utill.readInputFromFile("input6.txt");
        String lines = strings.toString().replaceAll("[\\[\\]]", "");
        String[] split = lines.split(",\\s");
        return split;
    }
}
