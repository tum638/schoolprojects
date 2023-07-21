import structure5.*;
import java.util.Comparator;
import java.util.Random;

public class MyVector<E> extends Vector<E> {
    /**
     * the constructor for the vector class, calls super()
     */
    public MyVector() {
        super();
    }

    /**
     * 
     * @param i the index of the element to be swapped
     * @param j he
     */
    public void swap(int i, int j) {
        E temp = this.get(i);
        this.set(i, this.get(j));
        this.set(j, temp);
    }

    /**
     * a function that sorts a generic vector
     * 
     * @param c the comparator object
     */
    public void sort(Comparator<E> c) {
        int numUnsorted = this.size();
        int i;
        int max;
        while (numUnsorted > 0) {
            max = 0;
            for (i = 1; i < numUnsorted; i++) {
                if (c.compare(this.get(max), this.get(i)) < 0) {
                    max = i;
                }
            }
            swap(max, numUnsorted - 1);
            numUnsorted--;
        }
    }

    /**
     * an entry point function to the program
     * 
     * @param args a list containing the users arguments entered at run time
     * 
     */
    public static void main(String[] args) {
        MyVector<Integer> v = new MyVector<>();
        Random r = new Random();
        for (int i = 10; i > 0; i--) {
            v.add(r.nextInt(1, 10));
        }
        System.out.println(v);
        v.sort((Integer i, Integer j) -> {
            Integer difference = i - j;
            return difference;
        });
        System.out.println(v);
    }
}

class NameComparator implements Comparator<Student> {
    public int compare(Student first, Student second) {
        return first.toString().compareTo(second.toString());
    }
}

class VowelsComparator implements Comparator<Student> {
    public static int countVowels(String first) {
        int numVowels = 0;
        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) == 'a' ||
                    first.charAt(i) == 'e' ||
                    first.charAt(i) == 'i' ||
                    first.charAt(i) == 'o' ||
                    first.charAt(i) == 'u') {
                numVowels++;
            }
        }
        return numVowels;
    }

    /**
     * a function the length of the longer string
     * 
     * @param first  the first string
     * @param second the other string
     * @return the length of the longer string
     */
    public int getMaxLength(String first, String second) {
        if (first.length() > second.length()) {
            return first.length();
        }
        return second.length();
    }

    /**
     * a function that compares the first and second student
     * 
     */
    public int compare(Student first, Student second) {
        int firstLength = countVowels(first.toString());
        int secondLength = countVowels(second.toString());
        return firstLength - secondLength;

    }
}

class AssociationComparator<E> implements Comparator<Association<E, Integer>> {
    public int compare(Association<E, Integer> firstAdress,
            Association<E, Integer> secondAddress) {
        return secondAddress.getValue() - firstAdress.getValue();
    }
}

class SUboxNumberComparator implements Comparator<Student> {
    public int compare(Student first, Student second) {
        return first.getSUboxNumber() - second.getSUboxNumber();
    }
}
