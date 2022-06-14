package org.pashkov.day11;

import org.apache.commons.lang3.ArrayUtils;
import org.pashkov.AoCUtills.AoCInputReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Roman Pashkov created on 13.06.2022 inside the package - org.pashkov.day11
 */
public class DumboOctopusPartOne {
    public static void main(String[] args) {
        List<String> strings = AoCInputReader.readInputFromFile("input11_1.txt");
        int count = 0;
        while (count < 100) {
            strings = increaseEachCharacterByOne(strings);
            printListWithLines(strings);
            System.out.println("-----------------------");
            count++;
        }
    }

    private static List<String> increaseEachCharacterByOne(List<String> strings) {
        return strings.stream()
                .map(String::toCharArray)
                .map(chars -> {
                    for (int i = 0; i < chars.length; i++) {
                        char aChar = chars[i];
                        int i1 = Integer.parseInt(String.valueOf(aChar));
                        i1 = i1 + 1;
                        if(i1>9) i1 = 0;
                        chars[i] = String.valueOf(i1).toCharArray()[0];
                    }
                    return chars;
                })
                .map(chars -> Arrays.toString(chars).replaceAll("[\\]\\[,\\s]", ""))
                .collect(Collectors.toList());
    }

    private static void printListWithLines(List<String> strings) {
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
