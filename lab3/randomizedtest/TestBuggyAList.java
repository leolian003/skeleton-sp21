package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */


public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> left = new AListNoResizing<>();
        BuggyAList<Integer> right = new BuggyAList<>();
        left.addLast(4);
        left.addLast(5);
        left.addLast(6);
        left.removeLast();
        right.addLast(4);
        right.addLast(5);
        right.addLast(6);
        right.removeLast();
        assertEquals(left.removeLast(),right.removeLast());
        assertEquals(left.removeLast(),right.removeLast());


    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> R = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                R.addLast(randVal);
                assertEquals(L.getLast(),R.getLast());
            } else if ((operationNumber == 1) & (L.size()>0)) {
                // size
                assertEquals(L.getLast(),R.getLast());
            }
            else if (L.size()>0){
                assertEquals(L.removeLast(),R.removeLast());

            }
        }
    }
}
