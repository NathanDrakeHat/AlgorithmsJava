package algorithm;

import org.junit.jupiter.api.Test;

import static algorithm.ActivitySelection.greedyActivitySelector;
import static algorithm.ActivitySelection.recursiveActivitySelector;
import static org.junit.jupiter.api.Assertions.*;

class ActivitySelectionTest {

    private static class Data{
        public static int[] s = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
        public static int[] f = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
    }

    @Test
    void testRecursive() {
        var res = recursiveActivitySelector(Data.s, Data.f);
        assertArrayEquals(res.getResult(), new int[]{0, 3, 7, 10});
    }

    @Test
    void testGreedy(){
        var res = greedyActivitySelector(Data.s, Data.f);
        assertArrayEquals(res.getResult(), new int[]{0, 3, 7, 10});
    }
}