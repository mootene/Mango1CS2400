
public class LinkedBag<T> implements BagInterface<T>
{
    private Node<T> firstNode;
    private int numberOfEntries;

    public LinkedBag()
    {
        firstNode = null;
        numberOfEntries = 0;
    }
    public int getCurrentSize()
    {
        return numberOfEntries;
    }
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }
    public boolean add(T newEntry)
    {
        if (isArrayFull())
            throw new ArrayIndexOutOfBoundsException();
        Node<T> newFirst = new Node<>(newEntry);
        newFirst.next = firstNode;
        firstNode = newFirst;
        numberOfEntries++;
        return true;
    }

    public T remove()
    {
        T result = null;
        if (firstNode != null)
        {
            result = firstNode.getData();
            firstNode=firstNode.getNextNode();
            numberOfEntries--;
        }
        return result;
    }

    private Node getReferenceTo(T anEntry)
    {
        Node<T> currentNode = firstNode;
        while (currentNode != null)
        {
            if (anEntry.equals(currentNode.getData()))
            {
                return currentNode;
            }
            currentNode = currentNode.getNextNode();
        }
        return currentNode;
    }

    /**
     *  removes first occurence of a given
     *  entry from bag if possible.
     */
    public boolean remove(T anEntry)
    {
        Node<T> beforeNode;
        Node<T> currentNode = firstNode;
        if (firstNode.getData().equals(anEntry))
        {
            firstNode = firstNode.getNextNode();
            numberOfEntries--;
            return true;
        }
        currentNode = firstNode.getNextNode();
        beforeNode = firstNode;
        while(currentNode != null)
        {
            if (currentNode.getData().equals(anEntry))
            {
                beforeNode.setNextNode(currentNode.getNextNode());
                numberOfEntries--;
                return true;
            }
            currentNode = currentNode.getNextNode();
        }
        return false;
    }

    public void clear()
    {
        while (!isEmpty())
            remove();
    }

    public int getFrequencyOf(T anEntry)
    {
        int frequency = 0;
        Node<T> currentNode = firstNode;
        while (currentNode != null)
        {
            frequency += anEntry.equals(currentNode.getData()) ? 1 : 0;
            currentNode = currentNode.getNextNode();
        }
        return frequency;
    }
    
    public boolean contains(T anEntry)
    {
        Node<T> currentNode = firstNode;
        while (currentNode != null)
        {
            if (currentNode.getData().equals(anEntry))
                return true;
        }
        return false;
    }

    public T[] toArray()
    {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) new Object[numberOfEntries];
        int position = 0;
        Node<T> currentNode = firstNode;
        while(currentNode != null)
        {
            array[position++] = currentNode.getData();
            currentNode = currentNode.getNextNode();
        }
        return array;
    }

    /**
    * checks that numberOfEntries is not more than maximum integer for indexing
    */
    public boolean isArrayFull()
    {
        return (numberOfEntries == Integer.MAX_VALUE);
    }

    /**
     * add another Linked Bag to this bag
     * @param other (the other bag to add)
     */
    public void add(T[] otherArray)
    {
        for (T element : otherArray)
        {
            add(element);
        }
    }

    /**
     * creates a new bag containing the union of this bag and another bag
     * @param other (the other bag)
     * @return (the union of this bag and other)
     */
    public LinkedBag<T> union(BagInterface<T> other)
    {
        LinkedBag<T> newBag = new LinkedBag<>();
        newBag.add(this.toArray());
        newBag.add(other.toArray());
        return newBag;
    }

    /**
     * creates a new bag containing the intersection of this bag and another bag
     * @param other the other bag being compared
     * @return a bag containing the intersection
     */
   public LinkedBag<T> intersection(BagInterface<T> other)
   {
       LinkedBag<T> newBag = new LinkedBag<>();
       for (Node<T> currentNode = firstNode;
        currentNode != null;
        currentNode = currentNode.getNextNode())
        {
            T thisData = currentNode.getData();
            int newFrequency = newBag.getFrequencyOf(thisData);
            if (newFrequency > 0)
                continue;
            int thisFrequency = getFrequencyOf(thisData);
            int otherFrequency = other.getFrequencyOf(thisData);
            while (newFrequency < Math.min(thisFrequency, otherFrequency))
            {
                newBag.add(thisData);
                newFrequency++;
            }
        }
        return newBag;
   }

    /**
     * creates new LinkedBag containing difference between this bag and another bag
     * @param other the bag being subtracted from this bag
     * @return new bag containing difference
     */
   public LinkedBag<T> difference (BagInterface<T> other)
   {
    LinkedBag<T> newBag = new LinkedBag<>();
    for (Node<T> currentNode = firstNode;
     currentNode != null;
     currentNode = currentNode.getNextNode())
     {
         T thisData = currentNode.getData();
         int thisFrequency = getFrequencyOf(thisData);
         int otherFrequency = other.getFrequencyOf(thisData);
         int newFrequency = newBag.getFrequencyOf(thisData);
         while (newFrequency < (thisFrequency - otherFrequency))
         {
             newBag.add(thisData);
             newFrequency++;
         }
     }
     return newBag;
   }

    private class Node<U>
    {
        private U data;
        private Node<U> next;
        private Node(U data)
        {
            this(data, null);
        }
        private Node(U data, Node<U> nextNode)
        {
            this.data = data;
            next = nextNode;
        }
        private U getData()
        {
            return data;
        }
        private void setData(U newData)
        {
            data = newData;
        }
        private Node<U> getNextNode()
        {
            return next;
        }
        private void setNextNode(Node<U> nextNode)
        {
            next = nextNode;
        }
    }
}