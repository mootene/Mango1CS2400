import java.util.Arrays;

public class LinkedBagTest
{
    public static void main(String[] args)
    {
        BagInterface<Integer> bag1 = new LinkedBag<Integer>();
        BagInterface<Integer> bag2 = new LinkedBag<Integer>();

        //initializing linked bag 1
        for (int i = 1; i < 6; i++)
        {
            bag1.add(i);
        }
        for (int i = 1; i < 4; i++)
        {
            bag1.add(11);
        }
        //initializing linked bag 2
        for (int i = 4; i < 11; i++)
        {
            bag2.add(i);
        }
        for(int i = 1; i < 5; i++)
        {
            bag2.add(11);
        }
            bag2.add(4);

        //printing contents of bags 1 and 2
        System.out.println(Arrays.toString(bag1.toArray()));
        System.out.println(Arrays.toString(bag2.toArray()));

        //union test
        BagInterface<Integer> unionBag = bag1.union(bag2);
        System.out.println("bag 1 union bag 2:\n" + Arrays.toString(unionBag.toArray()));

        //intersection test
        BagInterface<Integer> intersectBag = bag1.intersection(bag2);
        System.out.println("bag 1 intersect bag 2:\n" + Arrays.toString(intersectBag.toArray()));

        //difference test
        BagInterface<Integer> differenceBag = bag1.difference(bag2);
        System.out.println("bag 1 difference bag 2:\n" + Arrays.toString(differenceBag.toArray()));
        differenceBag = bag2.difference(bag1);
        System.out.println("bag 2 difference bag 1:\n" + Arrays.toString(differenceBag.toArray()));
    }
}