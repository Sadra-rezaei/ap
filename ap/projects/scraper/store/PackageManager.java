package ap.projects.scraper.store;

import ap.projects.scraper.Conf;
import ap.projects.scraper.DomainHtmlScraper;
import ap.projects.scraper.abalyzer.HtmlAnalyzer;

import java.io.IOException;

public class PackageManager {
    public static String packagePathName(String url) throws NullPointerException {

            if (url.contains("https://znu.ac.ir/") || url.contains("http://znu.ac.ir/")) {
                int startIndex;
                if (url.contains("https"))
                    startIndex = "https://znu.ac.ir/".length() -1;
                else
                    startIndex = "http://znu.ac.ir/".length() -1;

                int endIndex = url.indexOf("/", startIndex);

                String savePath;
                if (endIndex == -1)
                    savePath = Conf.SAVE_DIRECTORY_2 +"/"+ url.substring(startIndex);
                else
                    savePath = Conf.SAVE_DIRECTORY_2 +"/"+ url.substring(startIndex, endIndex);
                return savePath;

            } else if (url.contains("https://www.znu.ac.ir") || url.contains("http://www.znu.ac.ir")) {
                int startIndex;
                if (url.contains("https"))
                    startIndex = "https://www.znu.ac.ir/".length() -1;
                else
                    startIndex = "http://www.znu.ac.ir/".length() -1;

                int endIndex = url.indexOf("/", startIndex);

                String savePath;
                if (endIndex == -1)
                    savePath = Conf.SAVE_DIRECTORY_2 +"/"+ url.substring(startIndex);
                else
                    savePath = Conf.SAVE_DIRECTORY_2 +"/"+ url.substring(startIndex, endIndex);
                return savePath;

            } else if ((url.contains("https://")|| url.contains("http://")) && url.contains(".znu")) {
                int startIndex;
                if (url.contains("https"))
                    startIndex = "https://".length() -1;
                else
                    startIndex = "http://".length() -1;

                int endIndex = url.indexOf("znu", startIndex);
                String savePath = Conf.SAVE_DIRECTORY_2 +"/"+ url.substring(startIndex, endIndex);
                return savePath;

            } else if (url.indexOf("/") == 0) {
                int startIndex = 1;
                int endIndex = url.indexOf("/", startIndex);
                String savePath = Conf.SAVE_DIRECTORY_2 +"/"+ url.substring(startIndex, endIndex);
                return savePath;

            }else {
                return Conf.SAVE_DIRECTORY_2;
            }
    }
}
