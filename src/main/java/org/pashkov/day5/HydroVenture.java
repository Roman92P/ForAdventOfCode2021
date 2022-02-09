package org.pashkov.day5;

import org.pashkov.day1.Utill;

import java.util.*;

public class HydroVenture {

    public static void main(String[] args) {
        Map<String, String[]> stringMap = putCoordinatesOntoDiagram();
        //count two lines overlap
        int sum = 0;
        for (Map.Entry<String, String[]> entry : stringMap.entrySet()){
            String key = entry.getKey();
            String[] value = entry.getValue();
            for (String s : value){
                if(s.matches("[0-9]")&&Integer.parseInt(s)>1){
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }

    private static Map<String, String[]> putCoordinatesOntoDiagram() {
        String[] horizontalAndVerticalLines = findHorizontalAndVerticalLines();
        List<String> emptyDiagram = createEmptyDiagram(horizontalAndVerticalLines);
        Map<String, String[]> result;
        result = fillMapWithValues(emptyDiagram);
        for (String lineCoordinatesString : horizontalAndVerticalLines) {
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
                            else if (strings[i].matches("[0-9]")) strings[i] = String.valueOf(Integer.parseInt(strings[i]) + 1);
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
            result.put(String.valueOf(i), emptyDiagram.get(i).replaceAll("[\\]\\[\\s]","").split(""));
        }
        return result;
    }

    private static List<String[]> findAllNeededCordinates(String[] split) {
        String[] x1y1 = split[0].split(",");
        String[] x2y2 = split[1].split(",");

        List<String[]> result = new LinkedList<>();
        result.add(x1y1);
        //x same
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
        //y same
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
        result.add(x2y2);
        return result;
    }

    private static List<String> createDiagram() {
        String[] horizontalAndVerticalLines = findHorizontalAndVerticalLines();
        List<String> emptyDiagram = createEmptyDiagram(horizontalAndVerticalLines);
        return null;
    }

    private static List<String> createEmptyDiagram(String[] horizontalAndVerticalLines) {
        int maxX = findMaxNumber(horizontalAndVerticalLines)[0];
        int maxY = findMaxNumber(horizontalAndVerticalLines)[1];
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

    private static int[] findMaxNumber(String[] horizontalAndVerticalLines) {
        int maxX = 0;
        int maxY = 0;
        for (String s : horizontalAndVerticalLines) {
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

    private static String[] findHorizontalAndVerticalLines() {
        String[] lines = coordinatesArray();
        String[] resultArray = new String[0];
        for (String s : lines) {
            String tempString = s.replaceAll(" -> ", ",");
            String[] tempArray = tempString.split(",");
            if (horiZontalLineOrVertical(tempArray)) {
                resultArray = addLineToArray(resultArray, s);
            }
        }
        return resultArray;
    }

    private static String[] addLineToArray(String[] startArray, String s) {
        String[] result = Arrays.copyOf(startArray, startArray.length + 1);
        result[result.length - 1] = s;
        return result;
    }

    private static boolean horiZontalLineOrVertical(String[] tempArray) {
        String x1 = tempArray[0];
        String x2 = tempArray[2];
        String y1 = tempArray[1];
        String y2 = tempArray[3];
        if (x1.equals(x2)) return true;
        if (y1.equals(y2)) return true;
        return false;
    }

    private static String[] coordinatesArray() {
        List<String> strings = Utill.readInputFromFile("input5.txt");
        String lines = strings.toString().replaceAll("[\\[\\]]", "");
        String[] split = lines.split(",\\s");
        return split;
    }


}
