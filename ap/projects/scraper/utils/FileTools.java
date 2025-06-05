package ap.projects.scraper.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileTools {

    public static List<String> getTextFileLines(String filePath){
        try {
            return Files.lines(Paths.get(filePath))
                    .collect(Collectors.toList());
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return null;
        }
    }

    public static void save(List<String> urls,String filePath) {

        try {
            PrintWriter writer = new PrintWriter(filePath);

            for (String url : urls) {
                writer.println(url);
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




}
