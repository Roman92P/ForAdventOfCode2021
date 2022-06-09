package org.pashkov.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class SyntaxScoringPartOne {
    private static final List<String> OPENING_CHARACTERS = Arrays.asList("(", "[", "{", "<");
    private static final List<String> CLOSING_CHARACTERS = Arrays.asList(")", "]", "}", ">");
    public static void main(String[] args) {
        List<String> corruptedLines = findCorruptedLines();
        System.out.println(corruptedLines);
        int i = calculateScoreForSyntaxErrors(summarizeCorruptedCharsIntoMap(corruptedLines));
        System.out.println(i);
    }

    public static List<String> findCorruptedLines() {
        List<String> strings = readInputFromFile();
        LinkedList<String> result = new LinkedList<>();
        for (String line : strings) {
            char corrupterChar = findCorruptedCharacterInLine(line);
            result.add(Character.toString(corrupterChar));
        }
        return result;
    }
    private static char findCorruptedCharacterInLine(String str) {
        char[] chars = str.toCharArray();
        int checkingIndex = 0;
        while (true) {
            try {
                System.out.println("Char arr looks like: " + Arrays.toString(chars));
                System.out.println("Checking index: " + checkingIndex);
                char c = chars[checkingIndex];
                int cInt = (int) c;
                System.out.println("First Character will be: " + c + ", with int val: " + cInt + ", index: " + checkingIndex);
                String indexOfCharToCheck = "";
                indexOfCharToCheck = findNotSpacialChar(chars, checkingIndex);
                int newIndex = Integer.parseInt(indexOfCharToCheck);
                char chToCheck = chars[newIndex];
                System.out.println("Second Character will be: " + chToCheck + ", with value: " + (int) chToCheck + ", index: " + newIndex);
                int numericValue = (int) chToCheck;
                int devideresult = numericValue - cInt;
                System.out.println("The result will be: " + devideresult);
                if (numericValue - cInt == 1 || numericValue - cInt == 2) {
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
        for (char x : chars) {
            System.out.print(x + " ");
        }
        System.out.print("\n");
        char wrongChar = findCorruptedCharacterInCharArray(chars);
        System.out.println("Wrong one: " + wrongChar);

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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(chars);
        String s = stringBuilder.toString();
        String s1 = s.replaceAll(" ", "");
        char[] chars1 = s1.toCharArray();
        return chars1;
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
    public static int calculateScoreForSyntaxErrors(Map<String, Integer> syntaxErrorAppearsMap) {
        Map<String, Integer> syntaxErrorScoreMap = new HashMap<>();
        syntaxErrorScoreMap.put(")", 3);
        syntaxErrorScoreMap.put("]", 57);
        syntaxErrorScoreMap.put("}", 1197);
        syntaxErrorScoreMap.put(">", 25137);
        int result = 0;
        for (Map.Entry<String, Integer> entry : syntaxErrorAppearsMap.entrySet()) {
            int characterScore = entry.getValue() * syntaxErrorScoreMap.get(entry.getKey());
            result = result + characterScore;
        }
        return result;
    }
    public static Map<String, Integer> summarizeCorruptedCharsIntoMap(List<String> allCorruptedChars) {
        Map<String, Integer> resultMap = new HashMap<>();
        int roundBrackets = 0;
        int squareBrackets = 0;
        int curlyBrackets = 0;
        int angleBrackets = 0;
        for (String s : allCorruptedChars) {
            if (s.equals(")")) roundBrackets++;
            if (s.equals("]")) squareBrackets++;
            if (s.equals("}")) curlyBrackets++;
            if (s.equals(">")) angleBrackets++;
        }
        resultMap.put(")", roundBrackets);
        resultMap.put("]", squareBrackets);
        resultMap.put("}", curlyBrackets);
        resultMap.put(">", angleBrackets);
        return resultMap;
    }
}
