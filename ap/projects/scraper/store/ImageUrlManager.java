package ap.projects.scraper.store;

import ap.projects.scraper.utils.DirectoryTools;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ImageUrlManager {

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
