
import java.util.Scanner;
import structure5.Association;

public class Questions {

    // color coding for answers
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_RED = "\u001B[31m";
    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_CYAN = "\u001B[36m";

    /**
     * a function that passes lines of a file to create a student object
     * 
     * @param s scanner object containing contents of file
     * @return returns a student object
     */
    public static Student getStudent(Scanner s) {
        String name = s.nextLine();
        String address = s.nextLine();
        Long telephoneNumber = s.nextLong();
        Short SUboxNumber = s.nextShort();
        Long homePhoneNumber = s.nextLong();
        Student student = new Student(name, address, telephoneNumber, SUboxNumber, homePhoneNumber);
        return student;
    }

    /**
     * an entry point function to the program
     * 
     * @param args a list containing the users arguments entered at run time
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        MyVector<Student> v = addStudentsToVector(s);
        answerQuestions(v, s);

    }

    /**
     * a function that prints out questions and answers to the terminal
     * 
     * @param v the vector containing the students
     * @param s the scanner object
     */
    public static void answerQuestions(MyVector<Student> v, Scanner s) {
        System.out.println(
                "1. Which student appears first when sorted by name? (i.e., in ascending order, so that a comes before b)");
        System.out.println(ANSI_GREEN + getFirstByName(s, v) + ANSI_RESET);
        System.out.println(" ");
        System.out.println("2(a).  Which student has the smallest SU box number? ");
        System.out.println(ANSI_GREEN + getExtremeSUboxNUmber(s, 0, v) + ANSI_RESET);
        System.out.println("2(b). Which student has the largest SU box number?");
        System.out.println(ANSI_GREEN + getExtremeSUboxNUmber(s, v.size() - 1, v) + ANSI_RESET);
        System.out.println(" ");
        System.out.println(
                "3. Which student has the greatest number of vowels in their full name? You may ignore ys when counting vowels.");
        System.out.println(ANSI_GREEN + getMostVowelsStudent(s, v) + ANSI_RESET);
        System.out.println(" ");
        System.out.println("4. Which address is shared by the most students, and what are their names? ");
        printStudentsAtPopularAddress(v);
        System.out.println(" ");
        System.out.println("5. What are the ten most common area codes for student home phone numbers?");
        printAreaCodesAndStudentCounts(sortAreaCodes(getAreaCodeCounts(v)));

    }

    /**
     * a function that prints out students at the most popular address
     * 
     * @param v a vector containing the students.
     */
    public static void printStudentsAtPopularAddress(MyVector<Student> v) {
        String address = sortAddresses(getAddressCount(v)).get(0).getKey();
        retrieveStudentsAtAddress(v, address);

    }

    /**
     * prints out the names of students at the most popular address
     * 
     * @param v       the student vector
     * @param address the specified address
     */
    public static void retrieveStudentsAtAddress(MyVector<Student> v, String address) {
        System.out.println("Top Address: " + ANSI_GREEN + address + ANSI_RESET);
        System.out.println(" ");
        System.out.println("The students at the address are: ");
        for (int i = 0; i < v.size(); i++) {
            Student student = v.get(i);
            if (student.getCampusAddress().equals(address)) {
                System.out.println(ANSI_GREEN + student.toString() + ANSI_RESET);
            }
        }
        System.out.println(" ");
    }

    /**
     * a function to sort addresses
     * 
     * @param v the vector containing the students
     * @return an association of address and the and number of people at the address
     */
    public static MyVector<Association<String, Integer>> sortAddresses(MyVector<Association<String, Integer>> v) {
        AssociationComparator<String> a = new AssociationComparator<>();
        v.sort(a);
        return v;
    }

    /**
     * a function that accumulates unique addresses
     * 
     * @param v the vector containing students
     * @return a vector containing unique addresses
     */
    public static MyVector<String> getUniqueAddresses(MyVector<Student> v) {
        MyVector<String> addresses = new MyVector<>();
        for (int i = 0; i < v.size(); i++) {
            String address = v.get(i).getCampusAddress();
            if (addresses.contains(address)) {
                continue;
            }
            addresses.add(address);
        }
        return addresses;
    }

    /**
     * 
     * @param v the vector containing the students
     * @return a vector containing the unique area codes
     */
    public static MyVector<Long> getUniqueAreaCodes(MyVector<Student> v) {
        MyVector<Long> areaCodes = new MyVector<>();
        for (int i = 0; i < v.size(); i++) {
            Long code = v.get(i).getHomePhoneNumber() / 10000000;
            if (areaCodes.contains(code)) {
                continue;
            }
            areaCodes.add(code);
        }
        return areaCodes;
    }

    /**
     * a function that counts the number of students at an address
     * 
     * @param v the vector containing students
     * @return a vector containing associations of an addresses and the
     *         corresponding counts at that address
     */
    public static MyVector<Association<String, Integer>> getAddressCount(MyVector<Student> v) {
        MyVector<String> uniqueAddresses = getUniqueAddresses(v);
        MyVector<Association<String, Integer>> addresses = new MyVector<>();
        for (int i = 0; i < uniqueAddresses.size(); i++) {
            if (uniqueAddresses.get(i).equals("UNKNOWN")) {
                continue;
            }
            Association<String, Integer> address = new Association<String, Integer>(v.get(i).getCampusAddress(), 0);
            for (int j = 0; j < v.size(); j++) {
                if (v.get(j).getCampusAddress().equals(uniqueAddresses.get(i))) {
                    int count = address.getValue() + 1;
                    address.setValue(count);
                }
            }
            addresses.add(address);
        }
        return addresses;
    }

    /**
     * a function that calculates counts of area codes
     * 
     * @param v the vector containing students
     * @return a vector containing associations of an area code and corresponding
     *         counts of students at that address
     */
    public static MyVector<Association<Long, Integer>> getAreaCodeCounts(MyVector<Student> v) {
        MyVector<Long> uniqueAreaCodes = getUniqueAreaCodes(v);
        MyVector<Association<Long, Integer>> codes = new MyVector<>();
        for (int i = 0; i < uniqueAreaCodes.size(); i++) {
            Long areacode = uniqueAreaCodes.get(i);
            if ((areacode / 100) < 1) {
                continue;
            }
            Association<Long, Integer> codecount = new Association<Long, Integer>(areacode, 0);
            for (int j = 0; j < v.size(); j++) {
                Long studentAreaCode = v.get(j).getHomePhoneNumber() / 10000000;
                if (areacode.compareTo(studentAreaCode) == 0) {
                    int count = codecount.getValue() + 1;
                    codecount.setValue(count);
                }
            }
            codes.add(codecount);
        }
        return codes;
    }

    /**
     * 
     * @param v a vector containing associations of student area codes and count of
     *          students in that area code
     * @return a vector containing an associatinon
     */
    public static MyVector<Association<Long, Integer>> sortAreaCodes(MyVector<Association<Long, Integer>> v) {
        AssociationComparator<Long> a = new AssociationComparator<>();
        v.sort(a);
        return v;
    }

    /**
     * a function that prints out the area code and number of students at an area
     * code
     * 
     * @param v a vector containing associations of an area code and the count of
     *          students at that area code
     */
    public static void printAreaCodesAndStudentCounts(MyVector<Association<Long, Integer>> v) {
        for (int i = 0; i < 10; i++) {
            Long areaCode = v.get(i).getKey();
            Integer numberOfStudents = v.get(i).getValue();
            System.out.println(
                    "Number " + (i + 1) + " Area Code: (" + ANSI_GREEN + areaCode + ANSI_RESET
                            + ") Number of Students: " + ANSI_GREEN
                            + numberOfStudents + ANSI_RESET);
        }
        System.out.println(' ');
    }

    /**
     * a function that adds students to a vector
     * 
     * @param s the scanner object containing file info
     * @return a vector containing students
     */
    public static MyVector<Student> addStudentsToVector(Scanner s) {
        MyVector<Student> v = new MyVector<>();
        while (s.hasNextLine()) {
            Student student = getStudent(s);
            v.add(student);
            s.nextLine();
            s.nextLine();
        }
        s.close();
        return v;
    }

    /**
     * 
     * @param s the scanner object containing the file info
     * @param v the vector of students
     * @return a student object
     */
    public static Student getFirstByName(Scanner s, MyVector<Student> v) {
        NameComparator n = new NameComparator();
        v.sort(n);
        return v.get(0);
    }

    /**
     * 
     * @param s    the scanner object
     * @param flag the parameter determinining wether the first or last element
     *             should be retrived
     * @param v    the vector of students
     * @return a student object
     */
    public static Student getExtremeSUboxNUmber(Scanner s, int flag, MyVector<Student> v) {
        SUboxNumberComparator b = new SUboxNumberComparator();
        v.sort(b);
        return v.get(flag);
    }

    /**
     * a function that to get student with most vowels in their name
     * 
     * @param s the scanner object
     * @param v the vector containing the students
     * @return a student object
     */
    public static Student getMostVowelsStudent(Scanner s, MyVector<Student> v) {
        VowelsComparator y = new VowelsComparator();
        v.sort(y);
        return v.get(v.size() - 1);
    }
}