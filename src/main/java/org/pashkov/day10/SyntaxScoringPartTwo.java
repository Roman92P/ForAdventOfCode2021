package org.pashkov.day10;

import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class SyntaxScoringPartTwo {
    private static final List<String> OPENING_CHARACTERS = Arrays.asList("(", "[", "{", "<");
    private static final List<String> CLOSING_CHARACTERS = Arrays.asList(")", "]", "}", ">");
    public static void main(String[] args) {
        List<String> strings = generateLinesEndings();
        long integers = calculateScoreForLineEnding(strings);
        System.out.println(integers);
    }

    public static List<String> generateLinesEndings(){
        LinkedList<String> result = new LinkedList<>();
        List<String> notCorruptedLines = findNotCorruptedLines();
        for (String s : notCorruptedLines){
            String fitString = removeNoNeedChars(s);
            String correctOrderString = setCorrectOrder(fitString);
            result.add(correctOrderString);
        }
        return result;
    }

    private static String setCorrectOrder(String fitString) {
        char[] chars = fitString.replaceAll("[,\\s]","").toCharArray();
        ArrayUtils.reverse(chars);
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            String strToFind = Character.toString(c);
            int i1 = OPENING_CHARACTERS.indexOf(strToFind.trim());
            char[] chars1 = CLOSING_CHARACTERS.get(i1).toCharArray();
            chars[i] = chars1[0];
        }
        String s = Arrays.toString(chars);
        return s.substring(1, s.length() - 1).replaceAll("[,\\s]", "");
    }

    private static String removeNoNeedChars(String s1) {
        char[] chars = s1.toCharArray();
        int checkingIndex = 0;
        while (true) {
            try {
                char c = chars[checkingIndex];
                String indexOfCharToCheck = "";
                indexOfCharToCheck = findNotSpacialChar(chars, checkingIndex);
                int newIndex = Integer.parseInt(indexOfCharToCheck);
                char chToCheck = chars[newIndex];
                if ((int) chToCheck - (int) c == 1 || (int) chToCheck - (int) c == 2) {
                    chars[newIndex] = ' ';
                    chars[checkingIndex] = ' ';
                    chars = removeSpacesFromChArray(chars);
                    checkingIndex = newIndex - 2;
                    if (checkingIndex < 0) checkingIndex = 0;
                } else {
                    checkingIndex = newIndex;
                }
            } catch (NumberFormatException e) {
                break;
            }
        }
        //ArrayUtils.reverse(chars);
        String s = Arrays.toString(chars);
        return s.substring(1,s.length()-1);
    }

    public static List<String> findNotCorruptedLines() {
        List<String> strings = readInputFromFile();
        LinkedList<String> result = new LinkedList<>();
        for (String line : strings) {
            char corrupterChar = findCorruptedCharacterInLine(line);
            result.add(Character.toString(corrupterChar));
        }
        List<String> notCorrupted = new LinkedList<>();
        for (int i = 0; i < result.size(); i++) {
            if(result.get(i).equals("@")){
                notCorrupted.add(strings.get(i));
            }
        }
        return notCorrupted;
    }
    private static char findCorruptedCharacterInLine(String str) {
        char[] chars = str.toCharArray();
        int checkingIndex = 0;
        while (true) {
            try {
                char c = chars[checkingIndex];
                String indexOfCharToCheck = "";
                indexOfCharToCheck = findNotSpacialChar(chars, checkingIndex);
                int newIndex = Integer.parseInt(indexOfCharToCheck);
                char chToCheck = chars[newIndex];
                if ((int) chToCheck - (int) c == 1 || (int) chToCheck - (int) c == 2) {
                    chars[newIndex] = ' ';
                    chars[checkingIndex] = ' ';
                    chars = removeSpacesFromChArray(chars);
                    checkingIndex = newIndex - 2;
                    if (checkingIndex < 0) checkingIndex = 0;
                } else {
                    checkingIndex = newIndex;
                }
            } catch (NumberFormatException e) {
                break;
            }
        }
        char wrongChar = findCorruptedCharacterInCharArray(chars);
        return wrongChar;
    }
    private static char findCorruptedCharacterInCharArray(char[] chars) {
        int index = 0;
        for (int i = 1; i < chars.length - 1; i++) {
            char testChar = chars[index];
            char c = chars[i];
            if (CLOSING_CHARACTERS.contains(Character.toString(c)) && c - testChar != 1 || CLOSING_CHARACTERS.contains(Character.toString(c)) && c - testChar != 2) {
                return c;
            }
            index = index + 1;
        }
        return '@';
    }
    private static char[] removeSpacesFromChArray(char[] chars) {
        String s = String.valueOf(chars);
        String s1 = s.replaceAll(" ", "");
        return s1.toCharArray();
    }
    private static String findNotSpacialChar(char[] chars, int i) {
        for (int j = i + 1; j < chars.length; j++) {
            String s = Character.toString(chars[j]);
            if (!s.equals(" ")) return String.valueOf(j);
        }
        return null;
    }
    public static List<String> readInputFromFile() {
        Path path = Paths.get("input10_1.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static long calculateScoreForLineEnding(List<String>lineEnding) {
        Map<Character, Integer> syntaxErrorScoreMap = new HashMap<>();
        syntaxErrorScoreMap.put(')', 1);
        syntaxErrorScoreMap.put(']', 2);
        syntaxErrorScoreMap.put('}', 3);
        syntaxErrorScoreMap.put('>', 4);
        List<Long> scoreList = new ArrayList<>();
        for (String s: lineEnding){
            char[] chars = s.toCharArray();
            long tempMult = 0;
            for (char aChar : chars) {
                tempMult = tempMult * 5 + syntaxErrorScoreMap.get(aChar);
            }
            scoreList.add(tempMult);
        }
        List<Long> sortedScores = scoreList.stream().sorted().collect(Collectors.toList());
        return sortedScores.get((sortedScores.size() / 2));
    }
}
