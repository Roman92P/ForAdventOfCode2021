package org.pashkov.day6;

import org.pashkov.day1.Utill;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day6_2 {
    public static void main(String[] args) {

        long run = run(256);
        System.out.println(run);
    }

    private static long run(int days){

        Map<Integer, Long> fishTable = getTempFishTable();
        List<Integer> fish = readLaternfishCurrentState();

        for(int num : fish) {
            fishTable.put(num, fishTable.get(num) + 1);
        }

        for(int i = 0; i < days; i++) {
            Map<Integer, Long> tempFishTable = getTempFishTable();
            for(Map.Entry<Integer, Long> e : fishTable.entrySet()) {
                if(e.getKey() == 0 && e.getValue() > 0) {
                    tempFishTable.put(8, e.getValue());
                    tempFishTable.put(6, e.getValue());
                } else if (e.getKey() > 0 && e.getValue() > 0) {
                    tempFishTable.put(e.getKey() - 1, tempFishTable.get(e.getKey() - 1) + e.getValue());
                }
            }

            fishTable = tempFishTable;

        }

        return fishTable.entrySet().stream().map(e -> e.getValue()).mapToLong(e -> e).sum();
    }

    private static Map<Integer,Long> getTempFishTable() {
        Map<Integer, Long> tempFishTable = new HashMap<>();
        for(int i = 0; i <= 8; i++) {
            tempFishTable.put(i, 0L);
        }
        return tempFishTable;
    }

    private static List<Integer> readLaternfishCurrentState() {
        List<String> strings = Utill.readInputFromFile("input7.txt");
        List<Integer> objects = new LinkedList<>();
        for (String s : strings) {
            String[] split = s.split(",");
            for (String str : split) {
                objects.add(Integer.parseInt(str));
            }
        }
        return objects;
    }
}
