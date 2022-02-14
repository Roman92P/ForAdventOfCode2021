package org.pashkov.day6;

import org.pashkov.day1.Utill;

import java.util.*;

public class Lanternfish {

    private static final int lifeCyckleDays = 6;

    public static void main(String[] args) {
        Map<String, String> stringStringMap = lunchLanternFishLifeForNDays(80);
        for (Map.Entry<String, String> entry : stringStringMap.entrySet()) {
            System.out.println( entry.getKey() + " "+ entry.getValue());
        }
    }

    private static Map<String, String> lunchLanternFishLifeForNDays(int days){
        List<Integer> integers = readLaternfishCurrentState();
        Map<String, String> fishLifeMap;
        fishLifeMap = initialFillTheMap(integers);
        int daysCount = days;


        while (daysCount>0) {
            boolean createNewFish = false;
            int newCount = 0;
            Iterator<Map.Entry<String, String>> it = fishLifeMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> next = it.next();
                String value = next.getValue();
                String key = next.getKey();
                if (value.matches("New8")) {
                    String substring = value.substring(3);
                    next.setValue(String.valueOf(Integer.parseInt(substring) - 1));
                } else {
                    if (value.matches("[1-7]")) {
                        next.setValue(String.valueOf(Integer.parseInt(value) - 1));
                    }
                    if (value.matches("[0]")) {
                        next.setValue(String.valueOf(lifeCyckleDays));
                        createNewFish = true;
                        newCount++;
                    }
                }
            }
            if(createNewFish){
                for (int i = 0; i < newCount; i++) {
                    fishLifeMap.put(String.valueOf(fishLifeMap.size()+1), "New8");
                }
                createNewFish = false;
                newCount = 0;
            }
            daysCount--;
        }
        return fishLifeMap;
    }

    private static Map<String, String> initialFillTheMap(List<Integer> integers) {
        Map<String, String> fishLifeMap = new LinkedHashMap<>();
        for (int i = 1; i < integers.size()+1; i++) {
            fishLifeMap.put(String.valueOf(i), String.valueOf(integers.get(i-1)));
        }
        return  fishLifeMap;
    }

    private static List<Integer> readLaternfishCurrentState() {
        List<String> strings = Utill.readInputFromFile("input7.txt");
        List<Integer> objects = new LinkedList<>();
        for (String s : strings){
            String[] split = s.split(",");
            for (String str: split){
                objects.add(Integer.parseInt(str));
            }
        }
        return objects;
    }
}
