package org.pashkov.day1;

import java.util.List;

/**
 * created by Roman Pashkov
 */
public class PartTwoSonarSweep {
    public static void main(String[] args) {

        System.out.println(compereSumOfThree());
    }
    public static int compereSumOfThree(){
        List<String> strings = Utill.readInputFromFile("input1.txt");
        int toCompare = Integer.parseInt(strings.get(0)) +
                        Integer.parseInt(strings.get(1)) +
                        Integer.parseInt(strings.get(2));
        int result = 0;
        for (int i = 1; i < strings.size()-2; i++) {
            int tempInt =   Integer.parseInt(strings.get(i)) +
                            Integer.parseInt(strings.get(i+1)) +
                            Integer.parseInt(strings.get(i+2));
            if(toCompare<tempInt){
                result++;
            }
            toCompare = tempInt;
        }
        return result;
    }
}
