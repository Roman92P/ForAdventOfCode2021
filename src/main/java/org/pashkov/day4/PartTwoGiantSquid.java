package org.pashkov.day4;

import org.pashkov.day1.Utill;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PartTwoGiantSquid {

    private static final String[] keysNumbers = getKeysNumbers();
    private static final List<String[]> bingoCards = getBingoCards();
    private static final List<String[]> bingoCardsWithString = bingoCardsWithLines(bingoCards);


    public static int drawNumbers() {
        List<String> toCompareList = new LinkedList<>();
        int bingoCardScore = 0;


        Map<Integer, String[]> cardAndIndexes = new HashMap<>();
        int[]bingoCardArray = new int[bingoCards.size()];
        List<Integer>bingoCardIds=new ArrayList<>();
        int keyNumberCounter = 0;
        int bingoCounter = 0;
        List<String[]> workBingoCards = bingoCardsWithLines(getBingoCards());

        while (cardAndIndexes.size()<100){
            toCompareList.add(keysNumbers[keyNumberCounter]);
            Map<Integer, String[]> bingo = findBingo(toCompareList, workBingoCards);
            System.out.println("Number of bingos: " + bingo.size());
            if(bingo.size()>0){
                for(Map.Entry<Integer, String[]>entry : bingo.entrySet()){
                    cardAndIndexes.put(entry.getKey(), entry.getValue());
                    System.out.println("Card id: "+ entry.getKey()+" "+ ". To avoid: "+ Arrays.toString(entry.getValue()));
                    bingoCardArray[bingoCounter]=entry.getKey();
                    bingoCardIds.add(entry.getKey());
                    String[] strings = PartTwoGiantSquid.bingoCards.get(entry.getKey());

                    for (int i = 0; i < workBingoCards.size(); i++) {
                        if(Arrays.toString(workBingoCards.get(i)).equals(Arrays.toString(strings))) {
                            workBingoCards.remove(workBingoCards.get(i));
                            System.out.println("workCardList after remove: "+workBingoCards.size());
                        }
                    }
                }
                bingoCounter++;
            }
            keyNumberCounter++;
        }
        bingoCardScore = calculateFinalScore(toCompareList, cardAndIndexes, bingoCardIds);
        return bingoCardScore;
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

    private static Map<Integer, String[]> findBingo(List<String> toCompareList, List<String[]> bingoCards) {
        Map<Integer, String[]> resultMap = new HashMap<>();
        Map<Integer,String> indexes = new LinkedHashMap<>();

        for (int i = 0; i < bingoCards.size(); i++) {
            String[] strings = bingoCards.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < strings.length; j++) {
                if (toCompareList.contains(strings[j])) {
                    stringBuilder.append(j).append(" ");
                }
            }
            int index = -1;
            for (int j = 0; j < PartTwoGiantSquid.bingoCards.size(); j++) {
                String[] strings1 = PartTwoGiantSquid.bingoCards.get(j);
                if(Arrays.toString(strings1).equals(Arrays.toString(strings))){
                    index = j;
                }
            }
            indexes.put(index,stringBuilder.toString());
        }

        for (Map.Entry<Integer, String> entry : indexes.entrySet()){
            String strWithIndexes = entry.getValue();
            String[] split = strWithIndexes.split("\\s");
            if (hasFiveCorrectIndexes(split)) {
                resultMap.put(entry.getKey(), split);
            }
        }
        return resultMap;
    }
    public static void main(String[] args) {
        System.out.println(drawNumbers());
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
        int tempCounter = 0;
        for (int i = 1; i < indexesListToCheck.size(); i++) {
            if(indexesListToCheck.contains(String.valueOf(Integer.parseInt(fiveIncrementIndexToCheck)+7))){
                fiveIncreaseCount++;
                fiveIncrementIndexToCheck = String.valueOf(Integer.parseInt(fiveIncrementIndexToCheck)+7);
            } else {
                fiveIncreaseCount = 0;
                tempCounter++;
                fiveIncrementIndexToCheck = indexesListToCheck.get(tempCounter);
            }
            if (fiveIncreaseCount == 4) {
                fiveTrueOrFalse = true;
            }
        }
        if (oneTrueOrFalse || fiveTrueOrFalse) result = true;
        return result;
    }

    private static int calculateFinalScore(List<String> toCompareList, Map<Integer, String[]> cardAndIndexes, List<Integer> bingoCardArray) {
        System.out.println("All drawn keys: "+toCompareList);
        System.out.println("All bingo cards: "+bingoCardArray);
        System.out.println("All bingo cards number: "+bingoCardArray.size());
        int result = 0;
        int bingoCardNumber = bingoCardArray.get(bingoCardArray.size()-1);
        System.out.println("Last bingo card: "+ bingoCardNumber);
        String[]indexesToAvoidInCalculation = new String[0];
        for(Map.Entry<Integer, String[]>entry : cardAndIndexes.entrySet()){
            if(entry.getKey()==bingoCardNumber){
                indexesToAvoidInCalculation = entry.getValue();
            }
        }
        String[] strings = bingoCardsWithString.get(bingoCardNumber);
        System.out.println("Card: "+Arrays.toString(strings));
        System.out.println("To avoid in card: "+ Arrays.toString(indexesToAvoidInCalculation));
        int sumOfNotChoosen= 0;
        List<String> indexesToAvoidList = Arrays.stream(indexesToAvoidInCalculation).collect(Collectors.toList());
        for (int i = 0; i < strings.length; i++) {
            if(!indexesToAvoidList.contains(String.valueOf(i)) && !strings[i].equals("*") ){
                sumOfNotChoosen = sumOfNotChoosen+Integer.parseInt(strings[i]);
            }
        }

        System.out.println("Sum of not chosen: "+sumOfNotChoosen);
        String lastChosen = toCompareList.get(toCompareList.size()-1);
        System.out.println("Last chosen: "+lastChosen);
        result = sumOfNotChoosen * Integer.parseInt(lastChosen);
        return result;
    }

    public static String[] getKeysNumbers() {
        List<String> strings = Utill.readInputFromFile("input4.txt");
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
