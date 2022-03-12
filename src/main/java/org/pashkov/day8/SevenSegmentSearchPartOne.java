package org.pashkov.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SevenSegmentSearchPartOne {

    public static void main(String[] args) {
        System.out.println(countUniqueDigits());
    }

    public static int countUniqueDigits(){
        List<String> strings = readInputFromFile();
        int count = 0;
        for (String s: strings){
            String[]a = s.split("[|]");
            String tocheck = a[1];
            String[] fourDigits = tocheck.split(" ");
            for(String strToCheck : fourDigits){
                if(strToCheck.length()==2||strToCheck.length()==3||strToCheck.length()==4||strToCheck.length()==7){
                    count = count+1;
                }
            }
        }
        return count;
    }

    public static List<String> readInputFromFile(){
        Path path = Paths.get("input8_1.txt");
        try {
            List<String> strings = Files.readAllLines(path);
            return strings;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
