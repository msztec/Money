package pl.com.tt.autoboxing;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class BoxedTypes {

    public static void main(String[] args) {

        //    What will be printed ?
        shortIteration();

        intigerIteration();

        fixedShortIteration();


        // Broken comparator - can you spot the flaw?
        boxedComperator();

        fixedBoxedComperator();
    }

    private static void boxedComperator() {
        Comparator<Integer> naturalOrder = new Comparator<Integer>() {
            public int compare(Integer first, Integer second) {
                return first < second ? -1 : (first == second ? 0 : 1);
            }
        };
        /* Lambda version
        * Comparator<Integer> naturalOrder = (first, second) -> first < second ? -1 : (first == second ? 0 : 1);
        * */
        int result = naturalOrder.compare(new Integer(42), new Integer(42));
        System.out.println(result);
    }

    private static void fixedBoxedComperator() {
        Comparator<Integer> naturalOrder = new Comparator<Integer>() {
            public int compare(Integer first, Integer second) {
                int firstValue = first;
                int secondValue = second;
                return firstValue < secondValue ? -1 : (firstValue == secondValue ? 0 : 1);
            }
        };

        int result = naturalOrder.compare(new Integer(42), new Integer(42));
        System.out.println(result);
    }

    private static void fixedShortIteration() {
        Set<Short> ints = new HashSet<>();

        for (short i = 0; i < 100; i++) {
            ints.add(i);
            ints.remove((short) (i - 1)); //fix
        }
        System.out.println(ints.size()); // ?
    }

    private static void intigerIteration() {
        Set<Integer> ints = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            ints.add(i);
            ints.remove(i - 1);
        }
        System.out.println(ints.size()); // ?
    }

    private static void shortIteration() {
        Set<Short> shorts = new HashSet<>();
        for (short i = 0; i < 100; i++) {
            shorts.add(i);
            shorts.remove(i - 1);
        }
        System.out.println(shorts.size());  //???
    }
}
