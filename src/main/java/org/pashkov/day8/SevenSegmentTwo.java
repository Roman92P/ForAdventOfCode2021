package org.pashkov.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SevenSegmentTwo {

    private static final String[][] emptyScreen = emptySevenSegmentDisplay();

    public static void main(String[] args) {
        List<String> inputLines = readInputFromFile();
        List<String> finalListBeforeSum = new LinkedList<>();
        for (int i = 0; i < inputLines.size(); i++) {
            String oneInputLine = inputLines.get(i);
            for (String[] arr1 : emptyScreen) {
            }
            String[][] mappedDisplay = displaysWiresSegmentMapping(oneInputLine);
            for (String[] arr1 : emptyScreen) {
                System.out.println(Arrays.toString(arr1));
            }
            String[][]newMappingArray = checkMapping(mappedDisplay, oneInputLine);
            //reading output
            String outputString = inputLines.get(i).split("[|]")[1].trim();
            String[] finalOutputArray = outputString.split(" ");
            StringBuilder resultStringBuilder = new StringBuilder();
            for (int j = 0; j < finalOutputArray.length; j++) {
                String s = readDigitFromDisplay(newMappingArray, finalOutputArray[j]);
                resultStringBuilder.append(s);
            }
            String resultString = resultStringBuilder.toString();
            finalListBeforeSum.add(resultString);
        }
        System.out.println(calculateResult(finalListBeforeSum));
    }

    private static int calculateResult(List<String> finalListBeforeSum) {
        return finalListBeforeSum.stream().mapToInt(Integer::parseInt).sum();
    }

    // read digits from seven-segment display
    private static String readDigitFromDisplay(String[][] wiresMapping, String inputDigit) {
        String[][] displayModel = {
                {"-", "01", "02", "03", "04", "-",},
                {"10", "-", "-", "-", "-", "15"},
                {"20", "-", "-", "-", "-", "25"},
                {"-", "31", "32", "33", "34", "-"},
                {"40", "-", "-", "-", "-", "45"},
                {"50", "-", "-", "-", "-", "55"},
                {"-", "61", "62", "63", "64", "-"},
        };
        String zero = "01020304101520254045505561626364";
        String one = "15254555";
        String two = "01020304152531323334405061626364";
        String three = "01020304152531323334455561626364";
        String four = "10152025313233344555";
        String five = "01020304102031323334455561626364";
        String six = "010203041020313233344045505561626364";
        String seven = "0102030415254555";
        String eight = "0102030410152025313233344045505561626364";
        String nine = "010203041015202531323334455561626364";


        String[] splitedInputDigit = inputDigit.split("");
        System.out.println("To be read from display: " + Arrays.toString(splitedInputDigit));
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < splitedInputDigit.length; j++) {
            for (int k = 0; k < wiresMapping.length; k++) {
                for (int l = 0; l < wiresMapping[k].length; l++) {
                    if (wiresMapping[k][l].equals(splitedInputDigit[j])) {
                        stringBuilder.append(k).append(l);
                    }
                }
            }
        }
        String string = stringBuilder.toString();
        String stringToAdd = sortString(string);
        System.out.println("String to Add: " + stringToAdd);

        List<String> modelList = new LinkedList<>();
        modelList.add(zero);
        modelList.add(one);
        modelList.add(two);
        modelList.add(three);
        modelList.add(four);
        modelList.add(five);
        modelList.add(six);
        modelList.add(seven);
        modelList.add(eight);
        modelList.add(nine);

        int count = 0;
        for (String s : modelList) {
            if (s.equals(stringToAdd)) {
                return String.valueOf(count);
            }
            count++;
        }

        return "";
    }

    private static String sortString(String string) {
        List<String> strings = new ArrayList<>(Arrays.asList(string.split("")));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            if (i % 2 == 0) stringBuilder.append(",");
            stringBuilder.append(strings.get(i));
        }
        String substring = stringBuilder.toString().substring(1);
        String[] splited = substring.split(",");
        List<String> collect = Arrays.stream(splited).sorted().collect(Collectors.toList());
        return collect.toString().replaceAll("[\\]\\[,\\s]", "");
    }

    //wires/segment seven-segment display
    private static String[][] displaysWiresSegmentMapping(String oneInputLine) {
        String[][] resultDisplay = emptyScreen;
        String[] splitedInputLine = oneInputLine.split("[|]");
        System.out.println("First input line part: " + splitedInputLine[0].trim());
        System.out.println("Second input line part: " + splitedInputLine[1].trim());
        String firstPartInput = splitedInputLine[0].trim();
        String secondPartInput = splitedInputLine[1].trim();
        List<List<String>> uniqueAndNonUnique = findUniqueDigitsFromInput(firstPartInput);
        resultDisplay = mapDigitWiresSegments(uniqueAndNonUnique, emptyScreen);


        return resultDisplay;
    }

    //map particular digit
    private static String[][] mapDigitWiresSegments(List<List<String>> uniqueAndNonUnique, String[][] currentDisplay) {
        List<String> uniqueDigits = uniqueAndNonUnique.get(0);
        List<String> nonUniqueDigits = uniqueAndNonUnique.get(1);
        int[] tempIntArr = {1, 7, 4, 8};
        for (int i = 0; i < tempIntArr.length; i++) {
            String s1 = uniqueDigits.stream().filter(s -> s.length() == 2).collect(Collectors.toList()).toString().replaceAll("[\\]\\[]", "");
            String s2 = uniqueDigits.stream().filter(s -> s.length() == 3).collect(Collectors.toList()).toString().replaceAll("[\\]\\[]", "");
            String s3 = uniqueDigits.stream().filter(s -> s.length() == 4).collect(Collectors.toList()).toString().replaceAll("[\\]\\[]", "");
            String s4 = uniqueDigits.stream().filter(s -> s.length() == 7).collect(Collectors.toList()).toString().replaceAll("[\\]\\[]", "");

            s2 = uniqueCharacters(s1, s2);
            s3 = uniqueCharacters(s2 + s1, s3);
            s4 = uniqueCharacters(s1 + s2 + s3, s4);
            //.println("Mapping for one: " + s1);
            currentDisplay[1][5] = String.valueOf(s1.charAt(0));
            currentDisplay[2][5] = String.valueOf(s1.charAt(0));
            currentDisplay[4][5] = String.valueOf(s1.charAt(1));
            currentDisplay[5][5] = String.valueOf(s1.charAt(1));

            //System.out.println("Mapping for seven: " + s2);
            currentDisplay[0][1] = s2;
            currentDisplay[0][2] = s2;
            currentDisplay[0][3] = s2;
            currentDisplay[0][4] = s2;

            //System.out.println("Mapping for four: " + s3);
            currentDisplay[1][0] = String.valueOf(s3.charAt(0));
            currentDisplay[2][0] = String.valueOf(s3.charAt(0));
            currentDisplay[3][1] = String.valueOf(s3.charAt(1));
            currentDisplay[3][2] = String.valueOf(s3.charAt(1));
            currentDisplay[3][3] = String.valueOf(s3.charAt(1));
            currentDisplay[3][4] = String.valueOf(s3.charAt(1));

            //System.out.println("Mapping for seven: " + s4);
            currentDisplay[4][0] = String.valueOf(s4.charAt(0));
            currentDisplay[5][0] = String.valueOf(s4.charAt(0));
            currentDisplay[6][1] = String.valueOf(s4.charAt(1));
            currentDisplay[6][2] = String.valueOf(s4.charAt(1));
            currentDisplay[6][3] = String.valueOf(s4.charAt(1));
            currentDisplay[6][4] = String.valueOf(s4.charAt(1));
        }
        return currentDisplay;
    }

    private static String uniqueCharacters(String s1, String s2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            if (!s1.contains(String.valueOf(c))) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    //find unique digits
    public static List<List<String>> findUniqueDigitsFromInput(String firstPartInput) {
        //System.out.println("Finding unique digits:");
        String[] s = firstPartInput.split(" ");
        List<String> uniqueDigitsList = new LinkedList<>();
        List<String> nonUniqueDigitsList = new LinkedList<>();
        for (String s1 : s) {
            if (s1.length() == 2 || s1.length() == 3 || s1.length() == 4 || s1.length() == 7) {
                uniqueDigitsList.add(s1);
            } else {
                nonUniqueDigitsList.add(s1);
            }
        }
        Comparator<String> compByLength = (aName, bName) -> aName.length() - bName.length();
        List<String> sortedUniqueWires = uniqueDigitsList.stream().sorted(compByLength).collect(Collectors.toList());
        List<String> sortedNonUniqueWires = nonUniqueDigitsList.stream().sorted(compByLength).collect(Collectors.toList());
        return Arrays.asList(sortedUniqueWires, sortedNonUniqueWires);
    }

    // read input
    public static List<String> readInputFromFile() {
        Path path = Paths.get("input8_1.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // empty seven segment display filled with "-"
    private static String[][] emptySevenSegmentDisplay() {
        String[][] result = new String[7][6];
        for (String[] strings : result) {
            Arrays.fill(strings, "-");
        }
        return result;
    }

    private static List<String> readDigitFromDisplay2(String[][] mappedDisplay, List<String> filtered) {
        String[][] displayModel = {
                {"-", "01", "02", "03", "04", "-",},
                {"10", "-", "-", "-", "-", "15"},
                {"20", "-", "-", "-", "-", "25"},
                {"-", "31", "32", "33", "34", "-"},
                {"40", "-", "-", "-", "-", "45"},
                {"50", "-", "-", "-", "-", "55"},
                {"-", "61", "62", "63", "64", "-"},
        };
        String zero = "01020304101520254045505561626364";
        String one = "15254555";
        String two = "01020304152531323334405061626364";
        String three = "01020304152531323334455561626364";
        String four = "10152025313233344555";
        String five = "01020304102031323334455561626364";
        String six = "010203041020313233344045505561626364";
        String seven = "0102030415254555";
        String eight = "0102030410152025313233344045505561626364";
        String nine = "010203041015202531323334455561626364";

        List<String> result = new ArrayList<>();
        for (String inputDigitStr : filtered) {
            String[] splitedInputDigit = inputDigitStr.split("");
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < splitedInputDigit.length; j++) {
                for (int k = 0; k < mappedDisplay.length; k++) {
                    for (int l = 0; l < mappedDisplay[k].length; l++) {
                        if (mappedDisplay[k][l].equals(splitedInputDigit[j])) {
                            stringBuilder.append(k).append(l);
                        }
                    }
                }
            }
            String string = stringBuilder.toString();
            String stringToAdd = sortString(string);

            List<String> modelList = new LinkedList<>();
            modelList.add(zero);
            modelList.add(one);
            modelList.add(two);
            modelList.add(three);
            modelList.add(four);
            modelList.add(five);
            modelList.add(six);
            modelList.add(seven);
            modelList.add(eight);
            modelList.add(nine);

            int count = 0;
            for (String s : modelList) {
                if (s.equals(stringToAdd)) {
                    System.out.println("Added: " + s);
                    result.add(String.valueOf(count));
                }
                count++;
            }
        }
        return result;
    }

    private static String[][] checkMapping(String[][] mappedDisplay, String oneInputLine) {
        String[] splitedInputLine = oneInputLine.split("[|]");
        String[] s = splitedInputLine[1].trim().split(" ");
        List<String> filtered = Arrays.stream(s).filter(a -> a.length() == 5 || a.length() == 6).collect(Collectors.toList());
        System.out.println("Numbers to be found in mapping: " + filtered);

        List<String> tempArr = new ArrayList<>();
        
        String[][]copy = new String [7][6];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[i].length; j++) {
                copy[i][j] = mappedDisplay[i][j];
            }
        }
        int round = 0;
        tempArr = readDigitFromDisplay2(mappedDisplay, filtered);

        while (tempArr.size() != filtered.size()) {
            mappedDisplay = mixDisplaySegments2(mappedDisplay, round, copy);
            tempArr = readDigitFromDisplay2(mappedDisplay, filtered);
            round++;
            if(round>6){
                break;
            }
        }
        System.out.println("Readed number: " + tempArr);
        return mappedDisplay;
    }

    private static String[][] mixDisplaySegments2(String[][] mappedDisplay, int round, String[][] copy) {
//                {"-", "01", "02", "03", "04", "-",},
//                {"10", "-", "-", "-", "-", "15"},
//                {"20", "-", "-", "-", "-", "25"},
//                {"-", "31", "32", "33", "34", "-"},
//                {"40", "-", "-", "-", "-", "45"},
//                {"50", "-", "-", "-", "-", "55"},
//                {"-", "61", "62", "63", "64", "-"},

        for (int i = 0; i < mappedDisplay.length; i++) {
            for (int j = 0; j < mappedDisplay[i].length; j++) {
                mappedDisplay[i][j] = copy[i][j];
            }
        }

        System.out.println("Round: "+ round);
        String s1 = mappedDisplay[1][0];
        String s2 = mappedDisplay[3][1];

        String s3 = mappedDisplay[4][0];
        String s4 = mappedDisplay[6][1];

        String s5 = mappedDisplay[1][5];
        String s6 = mappedDisplay[4][5];

        switch (round) {
            case 0:
                //change top left
                mappedDisplay[1][0] = s2;
                mappedDisplay[2][0] = s2;
                mappedDisplay[3][1] = s1;
                mappedDisplay[3][2] = s1;
                mappedDisplay[3][3] = s1;
                mappedDisplay[3][4] = s1;
                break;
            case 1:
                //change down left
                mappedDisplay[4][0] = s4;
                mappedDisplay[5][0] = s4;
                mappedDisplay[6][1] = s3;
                mappedDisplay[6][2] = s3;
                mappedDisplay[6][3] = s3;
                mappedDisplay[6][4] = s3;
                break;
            case 2:
                //change right
                mappedDisplay[1][5] = s6;
                mappedDisplay[2][5] = s6;
                mappedDisplay[4][5] = s5;
                mappedDisplay[5][5] = s5;

                break;
            case 3:
                //change two left segments
                mappedDisplay[1][0] = s2;
                mappedDisplay[2][0] = s2;
                mappedDisplay[3][1] = s1;
                mappedDisplay[3][2] = s1;
                mappedDisplay[3][3] = s1;
                mappedDisplay[3][4] = s1;

                mappedDisplay[4][0] = s4;
                mappedDisplay[5][0] = s4;
                mappedDisplay[6][1] = s3;
                mappedDisplay[6][2] = s3;
                mappedDisplay[6][3] = s3;
                mappedDisplay[6][4] = s3;
                break;
            case 4:
                // change down left and right
                mappedDisplay[4][0] = s4;
                mappedDisplay[5][0] = s4;
                mappedDisplay[6][1] = s3;
                mappedDisplay[6][2] = s3;
                mappedDisplay[6][3] = s3;
                mappedDisplay[6][4] = s3;

                mappedDisplay[1][5] = s6;
                mappedDisplay[2][5] = s6;
                mappedDisplay[4][5] = s5;
                mappedDisplay[5][5] = s5;
                break;
            case 5:
                //change top left and right
                mappedDisplay[1][0] = s2;
                mappedDisplay[2][0] = s2;
                mappedDisplay[3][1] = s1;
                mappedDisplay[3][2] = s1;
                mappedDisplay[3][3] = s1;
                mappedDisplay[3][4] = s1;

                mappedDisplay[1][5] = s6;
                mappedDisplay[2][5] = s6;
                mappedDisplay[4][5] = s5;
                mappedDisplay[5][5] = s5;
                break;
            case 6:
                // chamge all
                mappedDisplay[1][0] = s2;
                mappedDisplay[2][0] = s2;
                mappedDisplay[3][1] = s1;
                mappedDisplay[3][2] = s1;
                mappedDisplay[3][3] = s1;
                mappedDisplay[3][4] = s1;

                mappedDisplay[4][0] = s4;
                mappedDisplay[5][0] = s4;
                mappedDisplay[6][1] = s3;
                mappedDisplay[6][2] = s3;
                mappedDisplay[6][3] = s3;
                mappedDisplay[6][4] = s3;

                mappedDisplay[1][5] = s6;
                mappedDisplay[2][5] = s6;
                mappedDisplay[4][5] = s5;
                mappedDisplay[5][5] = s5;
                break;
        }
        System.out.println("After display mixing:");
        for (String[] arr1 : mappedDisplay) {
            System.out.println(Arrays.toString(arr1));
        }
        return mappedDisplay;
    }
}
