package ap.projects.scraper.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HtmlParser {

//    public static String getFirstUrl(String htmlLine) {
//        String url = null;
//        int startIndex = htmlLine.indexOf("=");
//        int endIndex = -1;
//        if (startIndex >= 0) {
//            try {
//
//                if (htmlLine.indexOf(".png") >= 0)
//                    endIndex = htmlLine.indexOf(".png\"", startIndex);
//                else if (htmlLine.indexOf(".jpg") >= 0)
//                    endIndex = htmlLine.indexOf(".jpg\"", startIndex);
//                else if (htmlLine.indexOf(".gif") >= 0)
//                    endIndex = htmlLine.indexOf(".gif\"", startIndex);
//
//                url = htmlLine.substring(startIndex + 2, endIndex + 4);
//
//            } catch (Exception e) {
//            }
//        }
//        return url;
//    }

    public static String getFirstUrl(String htmlLine) {
        String url = null;
        int startIndex = htmlLine.indexOf("href=\"");
        if (startIndex >= 0 && !htmlLine.contains("#")) {
            try {
                int hrefLength = "href\"".length();
                int endIndex = htmlLine.indexOf("\"", startIndex + hrefLength + 1);
                url = htmlLine.substring(startIndex + hrefLength + 1, endIndex);
            } catch (Exception e) {
            }
        }
        return url;
    }

    private static List<String> getAllUrlsFromHtmlLinesStream(Stream<String> htmlLinesStream) throws IOException {
        List<String> urls = htmlLinesStream
                .map(line -> getFirstUrl(line))
                .filter(s -> s != null)
                .collect(Collectors.toList());
        return urls;
    }

    public static List<String> getAllUrlsFromFile(String filePath) throws IOException {
        return getAllUrlsFromHtmlLinesStream(Files.lines(Path.of(filePath)));
    }

    public static List<String> getAllUrlsFromList(List<String> htmlLines) throws IOException {
        return getAllUrlsFromHtmlLinesStream(htmlLines.stream());
    }
}
