package org.pashkov.day4;

import org.pashkov.day1.Utill;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GiantSquid {

   private static final String[] keysNumbers = getKeysNumbers();
   private static final List<String[]> bingoCards = getBingoCards();
   private static final List<String[]> bingoCardsWithString = bingoCardsWithLines(bingoCards);

    public static void main(String[] args) {
        System.out.println(drawNumbers());
    }

    private static List<String[]> bingoCardsWithLines(List<String[]> bingoCards) {
        for (int i = 0; i < bingoCards.size(); i++) {
            String[] strings = bingoCards.get(i);
            String[] newOne = new String[strings.length + 10];
            int count = 0;
            int stringsIndex = 0;
            for (int j = 0; j < newOne.length; j++) {
                if (stringsIndex == 0) {
                    newOne[j] = "*";
                    stringsIndex++;
                } else if (stringsIndex == 6) {
                    newOne[j] = "*";
                    stringsIndex = 0;
                } else {
                    newOne[j] = strings[count];
                    count++;
                    stringsIndex++;
                }
            }
            bingoCards.set(i, newOne);
        }
        return bingoCards;
    }

    public static int drawNumbers() {

        List<String> toCompareList = new LinkedList<>();

        int bingocardScore = 0;

        Map<Integer, String[]> cardAndIndexes = new HashMap<>();
        int keyNumberCounter = 0;
        while (cardAndIndexes.size() < 1) {
            toCompareList.add(keysNumbers[keyNumberCounter]);
            if (toCompareList.size() >= 5) {
                cardAndIndexes = findBingo(toCompareList, bingoCards);
                for (Map.Entry<Integer, String[]> entry : cardAndIndexes.entrySet()) {
                    System.out.println("Bingocard number: "+entry.getKey() + " " + "Indexes" + Arrays.toString(entry.getValue()));
                }
            }
            keyNumberCounter++;
        }
        System.out.println("All drawn numbers: "+toCompareList);
        bingocardScore = calculateFinalScore(toCompareList, cardAndIndexes);
        return bingocardScore;
    }

    private static int calculateFinalScore(List<String> toCompareList, Map<Integer, String[]> cardAndIndexes) {
        int result = 0;
        int bingoCardNumber = -1;
        String[]indexesToAvoidInCalculation = new String[0];
        for(Map.Entry<Integer, String[]>entry : cardAndIndexes.entrySet()){
           bingoCardNumber = entry.getKey();
           indexesToAvoidInCalculation = entry.getValue();
        }

        String[] strings = bingoCardsWithString.get(bingoCardNumber);
        System.out.println("Winner card: "+ Arrays.toString(strings));
        int sumOfNotChoosen= 0;
        List<String> indexesToAvoidList = Arrays.stream(indexesToAvoidInCalculation).collect(Collectors.toList());
        for (int i = 0; i < strings.length; i++) {
            if(!indexesToAvoidList.contains(String.valueOf(i)) && !strings[i].equals("*") ){
                sumOfNotChoosen = sumOfNotChoosen+Integer.parseInt(strings[i]);
            }
        }
        String lastChosen = toCompareList.get(toCompareList.size()-1);
        System.out.println("Sum of not drawed: "+sumOfNotChoosen);
        System.out.println("Last chosen: "+lastChosen);
// 0    1   2   3   4   5    6
//[*,   9, 87, 28, 94, 66,   *,
// 7           10           13
// *,   3, 10, 27, 13, 54,   *,
// 14                        20
// *,   40, 0, 43, 35, 85,   *,
// 21                   26   27
// *,   67, 34, 81, 92, 58,   *,
// 28           31             34
// *,   21, 53, 79, 6, 19,     *]
        //656
        result = sumOfNotChoosen * Integer.parseInt(lastChosen);
        return result;
    }

    private static Map<Integer, String[]> findBingo(List<String> toCompareList, List<String[]> bingoCards) {
        Map<Integer, String[]> resultMap = new HashMap<>();
        //String s = toCompareList.toString().replaceAll("[\\[\\]\\s]", "");
        List<String> indexes = new LinkedList<>();
        for (int i = 0; i < bingoCards.size(); i++) {
            String[] strings = bingoCards.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < strings.length; j++) {
                if (toCompareList.contains(strings[j])) {
                    stringBuilder.append(j).append(" ");
                }
            }
            indexes.add(stringBuilder.toString());
        }
        for (int i = 0; i < indexes.size(); i++) {
            String strWithIndexes = indexes.get(i);
            String[] split = strWithIndexes.split("\\s");
            if (split.length >= 5 && hasFiveCorrectIndexes(split)) {
                resultMap.put(i, split);
                System.out.println(Arrays.toString(bingoCards.get(i)));
            }
        }
        return resultMap;
    }

    private static boolean hasFiveCorrectIndexes(String[] split) {
        boolean result = false;
        List<String> indexesListToCheck = Arrays.stream(split).collect(Collectors.toList());
        int oneIncreaseCount = 0;
        boolean oneTrueOrFalse = false;
        String oneIncrementIndexToCheck = indexesListToCheck.get(0);
        for (int i = 1; i < indexesListToCheck.size(); i++) {
            if (Integer.parseInt(indexesListToCheck.get(i)) - Integer.parseInt(oneIncrementIndexToCheck) == 1) {
                oneIncreaseCount++;
            } else {
                oneIncreaseCount = 0;
            }
            oneIncrementIndexToCheck = indexesListToCheck.get(i);
            if (oneIncreaseCount == 4) {
                oneTrueOrFalse = true;
            }
        }

        String fiveIncrementIndexToCheck = indexesListToCheck.get(0);
        int fiveIncreaseCount = 0;
        boolean fiveTrueOrFalse = false;
        for (int i = 1; i < indexesListToCheck.size(); i++) {
            if (Integer.parseInt(indexesListToCheck.get(i)) - Integer.parseInt(fiveIncrementIndexToCheck) == 7) {
                fiveIncreaseCount++;
            } else {
                fiveIncreaseCount = 0;
            }
            fiveIncrementIndexToCheck = indexesListToCheck.get(i);
            if (fiveIncreaseCount == 4) {
                fiveTrueOrFalse = true;
            }
        }
        if (oneTrueOrFalse || fiveTrueOrFalse) result = true;
        return result;
    }

    public static String[] getKeysNumbers() {
        List<String> strings = Utill.readInputFromFile("input4.txt");
        System.out.println(strings);
        return strings.get(0).split(",");
    }

    public static List<String[]> getBingoCards() {
        File fl = new File("input4.txt");
        StringBuffer sb = null;
        try {
            FileReader fr = new FileReader(fl);
            BufferedReader br = new BufferedReader(fr);
            sb = new StringBuffer();
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (count > 1 & !line.isEmpty())
                    sb.append(line + " ");
                count++;
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] splitedInput = sb.toString().split("\\s");
        splitedInput = removeEmpty(splitedInput);
        List<String[]> resultList = new LinkedList<>();
        int count = 0;
        String[] tempArray = new String[25];
        for (int i = 0; i < splitedInput.length; i++) {
            tempArray[count] = splitedInput[i];
            if (count == 24) {
                resultList.add(tempArray);
                tempArray = new String[25];
                count = 0;
            } else
                count++;
        }
        return resultList;
    }

    private static String[] removeEmpty(String[] split) {
        List<String> tempList = Arrays.stream(split).collect(Collectors.toList());
        List<String> withoutEmptyStr = tempList.stream().filter(e -> !e.isEmpty()).collect(Collectors.toList());
        String s = withoutEmptyStr.toString().replaceAll("[\\[\\]\\s]", "");
        return s.split(",");
    }
}
