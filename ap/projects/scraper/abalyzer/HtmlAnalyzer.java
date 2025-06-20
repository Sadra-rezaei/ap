package ap.projects.scraper.abalyzer;

import ap.projects.scraper.Conf;
import ap.projects.scraper.DomainHtmlScraper;
import ap.projects.scraper.parser.HtmlParser;
import ap.projects.scraper.store.PackageManager;
import ap.projects.scraper.utils.DirectoryTools;
import ap.projects.scraper.utils.FileTools;
import ap.projects.scraper.utils.ObjectCounter;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HtmlAnalyzer {
    private static List<String> fileList = DirectoryTools.getFilesAbsolutePathInDirectory(Conf.SAVE_DIRECTORY);

    public static List<String> getAllUrls() {
        List<String> urls = fileList.stream()
                .map(fileAddress -> FileTools.getTextFileLines(fileAddress))
                .filter(s -> s != null)
                .flatMap(s -> s.stream())
                .map(s -> HtmlParser.getFirstUrl(s))
                .filter(s -> s != null)
                .filter(s -> s.length() > 1)
                .distinct()
                .collect(Collectors.toList());
        return urls;
    }

    public static List<String> getTopUrls(int k){
        Map<String, Long> urlCount = getAllUrls().stream()
                .collect(Collectors.groupingBy(
                        s -> s,
                        Collectors.counting()
                ));

        List<String> topUrls = urlCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(k)
                .map(s -> s.getKey())
                .collect(Collectors.toList());

        return topUrls;
    }
    public static void printTopCountUrls(int k){
        ObjectCounter<String> urlCounter=new ObjectCounter<>();
        getAllUrls().forEach(s -> urlCounter.add(s));
        for (Map.Entry<String, Integer> urlCountEntry : urlCounter.getTop(k)) {
            System.out.println(urlCountEntry.getKey()+" -> "+urlCountEntry.getValue());
        }
    }

    public static void printAllUrls(){
        for (String Url : getAllUrls()) {
            System.out.println(Url);
        }
    }

    public static void main(String[] args) {

//        HtmlAnalyzer.printAllUrls();
        FileTools.save(getAllUrls(),Conf.URLS_TXT);

        for (String url : getAllUrls()) {
            String savePath = PackageManager.packagePathName(url);
            if (url.indexOf("/") == 0)
                url = Conf.DOMAIN_ADDRESS + url;
            DomainHtmlScraper dm = new DomainHtmlScraper(url,savePath);
            try {
                dm.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

//        System.out.println("____________________");
//        HtmlAnalyzer.getTopUrls(10).forEach(s -> System.out.println(s));

    }
}
