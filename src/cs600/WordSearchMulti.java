package cs600;

import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WordSearchMulti {

    //Starting point of the application
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TreeMap<String, Integer> freqData = new TreeMap<String, Integer>();

        //links to read data from
        ArrayList<String> links = new ArrayList<String>();
        links.add("https://en.wikipedia.org/wiki/Main_Page");

        WebScraper d = new WebScraper();
        try {
           
            Document document = Jsoup.connect(links.get(0)).get();
            Elements hylinks = document.select("a");
            for (Element hylin : hylinks) {
                String hyper = hylin.attr("abs:href");
                if (!(hyper == null || hyper.length() == 0)) {
                    links.add(hyper);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        //looping through the above links and calling the function to scrape only paragraphs through them.
        //Below is the call to store the words and find the word occurences.
        for (String link : links) {
            System.out.print(link + "\n");
        }
        for (String link : links) {
            if (!(link == null || link.length() == 0)) {
                d.getData(link, freqData);
            }
        }

        //Printing the counts for all words.
        PageRankingAlgo.printAllCounts(freqData);
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the word you want to search : ");
            String line = scan.nextLine();

            // To stop asking for words
            if (line.equals("exit")) {
                break;
            }

            List<PageRankingAlgo> pages = new ArrayList<>();
            Map<String, PageRankingAlgo> pMap = new HashMap<>();

            //Below looping is done to find through each page whether the requested word  is present or not with their respective count.
            for (String word : line.split(" ")) {
                if (StopWord.is(word)) {
                    continue;
                }
                for (int i = 0; i < links.size(); i++) {
                    Integer count = d.getData(links.get(i), word);
                    if (count > 0) {
                        PageRankingAlgo pr = pMap.get(links.get(i));
                        if (pr == null) {
                            pMap.put(links.get(i), new PageRankingAlgo(links.get(i)));
                            pr = pMap.get(links.get(i));
                            pages.add(pr);
                        }
                        pr.insertWord(word, count);
                    }
                }
            }

            pages.sort(new PageComparatorMulti());
            System.out.println("Rank\t" + "Word\t\t" + "Links\n" + "---------------------------------");
            for (PageRankingAlgo page : pages) {
                System.out.println(page.getRank() + "\t" + page.getWords() + "\t\t" + page.getName());
            }
        }
        scan.close();
        System.exit(0);
    }
}
