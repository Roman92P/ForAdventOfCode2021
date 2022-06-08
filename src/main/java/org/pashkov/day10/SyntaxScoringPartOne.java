package org.pashkov.day10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SyntaxScoringPartOne {
    //Example
    //{ ( [ ( < { } [ < > [ ] } > { [ ] { [ ( < ( ) >

    private static final List<String> OPENING_CHARACTERS = Arrays.asList("(","[","{","<");
    private static final List<String> CLOSING_CHARACTERS = Arrays.asList(")","]","}",">");
    private static final List<String> OPENING_CHARACTERS_OCT = Arrays.asList("50","133","173","74");
    private static final List<String> CLOSING_CHARACTERS_OCT = Arrays.asList("51","135","175","76");
    public static void main(String[] args) {
//        Map<String, Integer> a = new HashMap<>();
//        a.put("()",2);
//        a.put("[]",1);
//        a.put("{}",1);
//        a.put("<>",1);
//        System.out.print(calculateScoreForSyntaxErrors(a));
        //System.out.println(readInputFromFile());
        String sTest = "{([(<{}[<>[]}>{[]{[(<()>";
        char[] chars = sTest.toCharArray();
        int c = (int) chars[0];
        for (int i = 1; i < chars.length-1; i++) {
            int numericValue = (int) chars[i];
            System.out.print(numericValue+ " ");
            if((int)chars[i]-c == 1 || (int)chars[i]-c == 2){
                chars[i-1] = ' ';
                chars[i] = ' ';
            }
            c = (int)chars[i];
        }
        System.out.println("\n");

        for (char x : chars){
            System.out.print(x+" ");
        }
    }
    public static List<String> findCorruptedLines(){

        List<String> strings = readInputFromFile();
        for (String line : strings){

        }

        return null;
    }
    public static List<String> readInputFromFile() {
        Path path = Paths.get("input10.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int calculateScoreForSyntaxErrors(Map<String,Integer>syntaxErrorAppearsMap){
        Map<String, Integer> syntaxErrorScoreMap = new HashMap<>();
        syntaxErrorScoreMap.put("()",3);
        syntaxErrorScoreMap.put("[]",57);
        syntaxErrorScoreMap.put("{}",1197);
        syntaxErrorScoreMap.put("<>",25137);
        int result = 0;
        for(Map.Entry<String,Integer>entry : syntaxErrorAppearsMap.entrySet()){
            int characterScore = entry.getValue() * syntaxErrorScoreMap.get(entry.getKey());
            result = result + characterScore;
        }
        return result;
    }
}
