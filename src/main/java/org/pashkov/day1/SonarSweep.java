package org.pashkov.day1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Roman Pashkov
 */
public class SonarSweep
{
    public static void main( String[] args )
    {
        System.out.println(countIncreases(readInputFromFile()));
    }

    public static List<String> readInputFromFile(){
        Path path = Paths.get("input1.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int countIncreases(List<String>numberList){
        int result = 0;
        int toCompare = Integer.parseInt(numberList.get(0));
        for (int i = 1; i < numberList.size(); i++) {
            if(toCompare<Integer.parseInt(numberList.get(i))){
                result++;
            }
            toCompare = Integer.parseInt(numberList.get(i));
        }
        return result;
    }

}
