package org.pashkov.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TheTreacheryofWhales {
    public static void main(String[] args) {

        run();
    }

    public static int run(){

        List<String> crabSubmarinesPositions = readInputFromFile("input7_7.txt");

        int sum = crabSubmarinesPositions.stream().mapToInt(Integer::parseInt).sum();
        System.out.println("Sum od all elements: "+sum);
        long count = crabSubmarinesPositions.size();
        System.out.println("Number of elements: "+count);
        int avg = (int) (sum/count);
        System.out.println("Avg: "+avg);

        List<Integer> integerList= crabSubmarinesPositions.stream().map(Integer::parseInt).collect(Collectors.toList());

        Set<Integer> unique = new HashSet<Integer>(integerList);
        Integer devider = 0;
        List<Integer> sortedUnique = unique.stream().sorted().collect(Collectors.toList());

        System.out.println(sortedUnique);
// avg + lewe + prawe
        List<Integer> resultList = integerList.stream()
                .map(a -> Math.abs(a - 2))
                .collect(Collectors.toList());

        int sum1 = resultList.stream().mapToInt(Integer::intValue).sum();

        System.out.println(sum1);


        return  0;
    }

    public static List<String> readInputFromFile(String fileName){
        Path path = Paths.get(fileName);
        try {
            List<String> strings = Files.readAllLines(path);
            String s = strings.get(0);
            String[] split = s.split(",");
            List<String> objects = new ArrayList<>();
            for (String a : split) objects.add(a);
            return objects;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
