package org.pashkov.day2;

import org.pashkov.day1.Utill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Roman Pashkov
 */
public class Dive {
    public static void main(String[] args) {
        Map<String, Integer> stringIntegerMap = calculateFinalPosition();

        int i = multHorizontDepth(stringIntegerMap);

        System.out.println(i);

    }
    public static Map<String, Integer> calculateFinalPosition(){
        List<String> lines = Utill.readInputFromFile("input2.txt");
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("horizontal", 0);
        resultMap.put("depth", 0);
        for (String str : lines){
            String[] tempStrArray = str.split(" ");
            switch (tempStrArray[0]){
                case "forward":
                    resultMap.put("horizontal", resultMap.get("horizontal")+Integer.parseInt(tempStrArray[1]));
                    break;
                case "up":
                    resultMap.put("depth", resultMap.get("depth")-Integer.parseInt(tempStrArray[1]));
                    break;
                case "down":
                    resultMap.put("depth", resultMap.get("depth")+Integer.parseInt(tempStrArray[1]));
                    break;
            }
        }
        return resultMap;
    }

    public static int multHorizontDepth(Map<String,Integer> mapHorizontDepth){
        return mapHorizontDepth.get("horizontal")*mapHorizontDepth.get("depth");
    }
}
