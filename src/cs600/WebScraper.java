package cs600;

import java.util.ArrayList;
import java.util.TreeMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * class to scrape through the input links
 */
public class WebScraper {
    //Function will be called each time for the no. of input links.
    //Search for the word through trie.
    //Display each word count through their respective maps found in the respective pages 

    public Integer getData(String link, String searchWord) {
        int count = 0;
        try {
            Trie myTrie = new Trie();

            //Using the jsoup to read through each webpage.
            Document document = Jsoup.connect(link).get();

            //Reading only the paragraph tags from the specified link.
            Elements paragraph = document.select("p");
            TreeMap<String, Integer> freqData = new TreeMap<String, Integer>();

            //Looping through all the paragraphs
            for (Element para : paragraph) {
                String p = para.text();
                p = p.replaceAll("[,.!?:;()-]", "\\s");//removing all the punctuations and replacing with blank spaces.
                //for each of the above paragraph extract individual words and insert them in trie data structure.

                for (String word : p.split(" ")) {

                    if (StopWord.is(word.toLowerCase())) {
                        continue;
                    }
                    myTrie.insert(word.toLowerCase());
                    count = PageRankingAlgo.getCount(word, freqData) + 1;
                    freqData.put(word, count);

                }
            }
            int temp = PageRankingAlgo.getCount(searchWord, freqData);
            count = temp;
//			System.out.println(searchWord+" found in "+ link + " "+myTrie.search(searchWord));
//			System.out.println(searchWord+" occurred "+temp+" times ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //Function overloaded to calculate frequency of all words.
    public void getData(String link, TreeMap<String, Integer> freqData) {
        int count = 0;
        try {
            Trie myTrie = new Trie();
            Document document = Jsoup.connect(link).get();
            Elements paragraph = document.select("p");

            for (Element para : paragraph) {
                String p = para.text();
                p = p.replaceAll("[,.!?:;()-]", "\\s");

                for (String word : p.split(" ")) {

                    if (StopWord.is(word.toLowerCase())) {
                        continue;
                    }
                    myTrie.insert(word.toLowerCase());
                    count = PageRankingAlgo.getCount(word, freqData) + 1;
                    freqData.put(word, count);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
