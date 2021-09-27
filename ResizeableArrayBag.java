import java.util.Arrays;

public class ResizeableArrayBag<T> implements BagInterface<T> 
{
    private T[] bag;
    private static final int DEFAULT_CAPACITY = 25;
    private int numberOfEntries;
    private boolean integrityOK = false;
    private static final int MAX_CAPACITY = 10000;

    /**Creates an empty bag whose initial capacity is 25. */
    public ResizeableArrayBag()
    {
        this(DEFAULT_CAPACITY);
    }
    /**Creates an empty bag having a given capacity.
     * @param desiredCapacity The integer capacity desired. 
     */

    public ResizeableArrayBag(int desiredCapacity)
    {
       if(desiredCapacity <= MAX_CAPACITY)
       {
            @SuppressWarnings("unchecked")
            T[] tempBag = (T[])new Object[desiredCapacity];
            bag = tempBag;
            numberOfEntries = 0;
            integrityOK = true;
       }
       else
       {
            throw new IllegalStateException("Attempt to create a bag whose capacity exceds allowed maximum.");
       }

    }
    // Throws an exception if the client requests a capacity that is too large.

    private void checkIntegrity()
    {
        if(!integrityOK)
        {
            throw new SecurityException("ArrayBag object is corrupt");
        }

    }
    /** Adds a new entry to this bag.
     * @param newEntry the object to be added as a new entry
     * @return true if the addtion is successful, or false if not.
     */

    public boolean add(T newEntry)
    {
        checkIntegrity();
        boolean result = true;
        if (isArrayFull())
        {
            result = false;
        }
        else
        {
            bag[numberOfEntries] = newEntry;
            numberOfEntries++;
        }

        return result;
    }
    /** Retrieves all entries that are in this bag.
     * @return a newly allocated array of all the entries in the bag.
     */

    public T[] toArray()
    {
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[numberOfEntries];
        for (int index = 0; index < numberOfEntries; index ++)
        {
            result[index] = bag[index];
        }

        return result;
    }
    /**Retrieves all entries that are in this bag.
     * @return a newly allocated array of all the entries in this bag.
     */

    public boolean isFull()
    {
        return numberOfEntries == bag.length;
    }
    /** Sees whether this bag is empty.
    @return  True if this bag is empty, or false if not. */

    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }
    /** Gets the current number of entries in this bag.
    @return  The integer number of entries currently in this bag. */

    public int getCurrentSize()
    {
        return numberOfEntries;
    }
    /** Counts the number of times a given entry appears in this bag.
    @param anEntry  The entry to be counted.
    @return  The number of times anEntry appears in this bag. */

    public int getFrequencyOf(T anEntry)
    {
        checkIntegrity();
        int counter = 0;

        for(int index = 0; index < numberOfEntries; index++)
        {
            if(anEntry.equals(bag[index]))
            {
                counter++;
            }
        }

        return counter;
    }
    /** Removes one occurrence of a given entry from this bag.
    @param anEntry  The entry to be removed.
    @return  True if the removal was successful, or false if not. */

    public boolean remove(T anEntry)
    {
        checkIntegrity();
        int index = getIndexOf(anEntry);
        T result = removeEntry(index);
        return anEntry.equals(result);
    }

    private int getIndexOf(T anEntry)
    {
        int num = -1;
        boolean found = false;
        int index = 0;
        while(!found && (index < numberOfEntries))
        {
            if(anEntry.equals(bag[index]))
            {
                found = true;
                num = index;
            }

            index++;
        }
        return num;
    }
    // Removes and returns the entry at a given index within the array bag.
    // If no such entry exists, returns null.
    // Preconditions: 0 <= givenIndex < numberOfEntries;
    //                checkIntegrity has been called.

    private T removeEntry(int givenIndex)
    {
        T result = null;

        if (!isEmpty() && (givenIndex >= 0))
        {
            result = bag[givenIndex];
            bag[givenIndex] = bag[numberOfEntries - 1];
            bag[numberOfEntries - 1] = null;
            numberOfEntries--;
        }

        return result;
    }
    /** Removes one unspecified entry from this bag, if possible.
    @return  Either the removed entry, if the removal was successful,
    or null otherwise. */

    public T remove()
    {
        checkIntegrity();
        T result = removeEntry(numberOfEntries - 1);
        return result;
    }
    /** Removes all entries from this bag. */

    public void clear()
    {
        while(!isEmpty())
        {
            remove();
        }

    }

    public boolean isArrayFull()
    {
        return numberOfEntries == bag.length;
    }
    /** Tests whether this bag contains a given entry.
    @param anEntry  The entry to locate.
    @return  True if this bag contains anEntry, or false otherwise. */
    
    public boolean contains(T anEntry)
    {
        checkIntegrity();
        return getIndexOf(anEntry) > -1;
    }

    /**creates a bag that has the union of both this bag and another bag
     * @param other the second bag
     * @return result of both bags
     */

    public BagInterface<T>union(BagInterface<T>other)
    {
        ResizeableArrayBag<T> result = new ResizeableArrayBag<T>();

        for(int i = 0; i<numberOfEntries; i++)
        {
            result.add(bag[i]);
        }

        for(Object ob: other.toArray())
        {
            result.add((T) ob);
        }

        return result;
    }

    /**creates a bag that has the intersection of this bag and the other bag
     * @param other the other bag
     * @return a bag that has the intersection
     */
    public BagInterface<T>intersection(BagInterface<T>other)
    {
        ResizeableArrayBag<T> result = new ResizeableArrayBag<T>();

        for(int i = 0; i < numberOfEntries; i++)
        {
            T item = bag[i];

            if(!result.contains(item) && other.contains(item))
            {
                int count = Math.min(getFrequencyOf(item), other.getFrequencyOf(item));

                for(int j = 0; j < count; j++)
                {
                    result.add(item);
                }
            }

            
        }
        return result;
    }

    /**creates a bag that has the difference between the first bag and the other bag
     * @param other the other bag gets taken out from the first bag
     * @return a new bag that has the difference
     */
    public BagInterface<T>difference(BagInterface<T>other)
    {
        ResizeableArrayBag<T>result = new ResizeableArrayBag<T>();
        
        for(int i = 0; i < numberOfEntries; i++)
        {
            T item = bag[i];

            if(!result.contains(item))
            {
                int difference = getFrequencyOf(item) - other.getFrequencyOf(item);

                for(int j = 0; j < difference; j++)
                {
                    result.add(item);
                }
            }


        }
        return result;
    }
    

}
