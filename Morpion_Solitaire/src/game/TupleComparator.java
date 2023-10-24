package game;

import java.util.Comparator;

public class TupleComparator implements Comparator<ScoreTuple> {
    @Override
    public int compare(ScoreTuple tuple1, ScoreTuple tuple2) {
        return tuple2.getFirst().compareTo(tuple1.getFirst());
    }
}

