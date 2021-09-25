import java.util.Arrays;

public class ResizeableArrayBag<T> implements BagInterface<T> 
{
    private T[] bag;
    private static final int DEFAULT_CAPACITY = 25;
    private int numberOfEntries;
    private boolean integrityOK = false;
    private static final int MAX_CAPACITY = 10000;

    public ResizeableArrayBag()
    {
        this(DEFAULT_CAPACITY);
    }

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

    private void checkIntegrity()
    {
        if(!integrityOK)
        {
            throw new SecurityException("ArrayBag object is corrupt");
        }

    }

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

    public boolean isFull()
    {
        return numberOfEntries == bag.length;
    }

    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }

    public int getCurrentSize()
    {
        return numberOfEntries;
    }

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

    public T remove()
    {
        checkIntegrity();
        T result = removeEntry(numberOfEntries - 1);
        return result;
    }

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

    public boolean contains(T anEntry)
    {
        checkIntegrity();
        return getIndexOf(anEntry) > -1;
    }

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
