package org.pashkov.day2;

import org.pashkov.day1.Utill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartTwoDive {
    public static void main(String[] args) {
        Map<String, Integer> stringIntegerMap = calculateFinalPosition();
        System.out.println(multHorizontDepth(stringIntegerMap));
    }
    public static Map<String, Integer> calculateFinalPosition(){
        List<String> lines = Utill.readInputFromFile("input2.txt");
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("horizontal", 0);
        resultMap.put("depth", 0);
        resultMap.put("aim", 0);

        for (String str : lines){
            String[] tempStrArray = str.split(" ");
            switch (tempStrArray[0]){
                case "forward":
                    resultMap.put("horizontal", resultMap.get("horizontal")+Integer.parseInt(tempStrArray[1]));
                    resultMap.put("depth", Integer.parseInt(tempStrArray[1])*resultMap.get("aim")+resultMap.get("depth"));
                    break;
                case "up":
                    resultMap.put("aim", resultMap.get("aim")-Integer.parseInt(tempStrArray[1]));
                    break;
                case "down":
                    resultMap.put("aim", resultMap.get("aim")+Integer.parseInt(tempStrArray[1]));
                    break;
            }
        }
        return resultMap;
    }
    public static int multHorizontDepth(Map<String,Integer> mapHorizontDepth){
        return mapHorizontDepth.get("horizontal")*mapHorizontDepth.get("depth");
    }
}
