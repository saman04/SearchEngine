package cs600;

import java.util.Comparator;

public class PageComparator implements Comparator<PageRankingAlgo> {

    @Override
    public int compare(PageRankingAlgo o1, PageRankingAlgo o2) {
        return o2.getRank().compareTo(o1.getRank());
    }
}
