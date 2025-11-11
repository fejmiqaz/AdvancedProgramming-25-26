package Labs.Lab2.Generics;

import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
class ResizableArray<T> {
    private T[] elements;
    private int size;

    public ResizableArray(){
        elements = (T[]) new Object[10];
        size = 0;
    }

    public void addElement(T element){
        if(size == elements.length){
            T[] newArray = (T[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, newArray, 0, size);
            elements = newArray;
        }
        elements[size] = element;
        size++;
    }

    public boolean removeElement(T element){
        int index = -1;

        for(int i = 0; i < size; i++){
            if(elements[i] == null){
                if(element == null){
                    index = i;
                    break;
                }
            }else if(elements[i].equals(element)){
                index = i;
                break;
            }
        }

        if(index == -1){
            return false;
        }

        for(int i = index; i < size-1; i++){
            elements[i] = elements[i + 1];
        }

        size--;
        elements[size] = null;

        if(size > 0 && size < elements.length / 4){
            T[] newArray = (T[]) new Object[elements.length / 2];
            for(int i = 0; i < size; i++){
                newArray[i] = elements[i];
            }
            elements = newArray;
        }

        return true;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            T current = elements[i];
            if(current != null && current.equals(element)){
                return true;
            }
        }
        return false;
    }

    public Object[] toArray(){
        return Arrays.stream(elements).toArray();
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int count(){
        return size;
    }

    public T elementAt(int index){
        return elements[index];
    }
    public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src){
        int n = src.count();
        for(int i = 0; i < n; i++){
            dest.addElement(src.elementAt(i));
        }
    }
}
class IntegerArray extends ResizableArray<Integer> {
    public double sum(){
        double total = 0.0;
        for(int i = 0; i < this.count(); i++){
            if(this.elementAt(i) != null){
                total += this.elementAt(i);
            }
        }
        return total;
    }
    public double mean(){
        double total = 0.0;
        for(int i = 0; i < this.count(); i++){
            if(this.elementAt(i) != null){
                total += this.elementAt(i);
            }
        }
        return total/this.count();
    }

    public int countNonZero(){
        int counter = 0;
        for(int i = 0; i < this.count(); i++){
            if(this.elementAt(i) != null){
                if(this.elementAt(i) != 0){
                    counter++;
                }
            }
        }
        return counter;
    }

    public IntegerArray distinct(){
        IntegerArray unique = new IntegerArray();

        for(int i = 0; i < this.count(); i++){
            Integer current = this.elementAt(i);

            boolean exists = false;

            for(int j = 0; j < unique.count(); j++){
                if(unique.elementAt(j).equals(current) && unique.elementAt(j)!=null){
                    exists = true;
                    break;
                }
            }

            if(!exists){
                unique.addElement(current);
            }
        }
        return unique;

    }

    public IntegerArray increment(int offset) {
        IntegerArray array = new IntegerArray();

        for (int i = 0; i < count(); i++) {
            array.addElement(elementAt(i) + offset);
        }
        return array;
    }

}


public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if ( test == 0 ) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while ( jin.hasNextInt() ) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if ( test == 1 ) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for ( int i = 0 ; i < 4 ; ++i ) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if ( test == 2 ) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while ( jin.hasNextInt() ) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if ( a.sum() > 100 )
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if ( test == 3 ) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for ( int w = 0 ; w < 500 ; ++w ) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k =  2000;
                int t =  1000;
                for ( int i = 0 ; i < k ; ++i ) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for ( int i = 0 ; i < t ; ++i ) {
                    a.removeElement(k-i-1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}
