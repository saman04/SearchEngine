package cs600;

import java.util.Comparator;

public class PageComparatorMulti implements Comparator<PageRankingAlgo> {

    @Override
    public int compare(PageRankingAlgo o1, PageRankingAlgo o2) {
        if (o1.getWordsNum().compareTo(o2.getWordsNum()) == 0) {
            return o2.getRank().compareTo(o1.getRank());
        } else {
            return o2.getWordsNum().compareTo(o1.getWordsNum());
        }
    }
}
