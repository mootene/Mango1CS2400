import java.util.Arrays;

public class ArrayBagTest 
{
    public static void main(String[] args)
    {
        BagInterface<String> bag1 = new ResizeableArrayBag<String>();
        BagInterface<String> bag2 = new ResizeableArrayBag<String>();

        bag1.add("a");
        bag1.add("b");
        bag1.add("c");
        bag1.add("d");
        bag1.add("e");
        bag2.add("e");
        bag2.add("c");
        bag2.add("f");
        bag2.add("h");
        bag2.add("i");

        System.out.println("Bag 1:" + Arrays.toString(bag1.toArray()));
        System.out.println("Bag 2:" + Arrays.toString(bag2.toArray()));

        System.out.println("Bag 1 and Bag 2 union:" + Arrays.toString(bag1.union(bag2).toArray()));
        System.out.println("Bag 1 and Bag 2 intersection:" + Arrays.toString(bag1.intersection(bag2).toArray()));
        System.out.println("Bag 1 and Bag 2 difference:" + Arrays.toString(bag1.difference(bag2).toArray()));
    }
}
