
// I IMPLEMENTED THE FIRST AND SECOND EXTENSIONS
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import structure5.*;

import java.util.Comparator;
import java.util.HashMap;

public class ExamScheduler {
    // the vector of student's
    protected Vector<Student> students;
    // the Conflict graph containing all the classes
    protected GraphListUndirected<String, Integer> graph;

    // a hash map of courses and their slots;
    protected HashMap<String, Integer> slotsTable;

    // vector of courses
    protected Vector<String> courseList;

    // a list of colors to make the output more readable
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_RED = "\u001B[31m";
    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * constructor to the ExamSchedular class
     * 
     * @param s
     */
    public ExamScheduler(Scanner s) {
        s = new Scanner(System.in);
        students = new Vector<>();
        courseList = new Vector<>();
        slotsTable = new HashMap<>();
        graph = new GraphListUndirected<>();
        while (s.hasNextLine()) {
            Student student = new Student(s);

            students.add(student);
        }
        s.close();
    }

    /**
     * a function to print out all the students
     */
    public void getStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    /**
     * a function to add classes to the graph
     * 
     * @param student the student whose classes are to be added to the graph.
     */
    public void addStudentsClassesToGraph(Student student) {
        for (String course : student) {
            graph.add(course);
        }
        for (int i = 0; i < 4; i++) {
            String thisCourse = student.classes.get(i);
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    String thatCourse = student.classes.get(j);
                    Edge<String, Integer> edge = graph.getEdge(thisCourse, thatCourse);
                    if (edge != null) {
                        graph.addEdge(thisCourse, thatCourse, edge.label() + 1);
                    } else {
                        graph.addEdge(thatCourse, thisCourse, 1);
                    }
                }
            }

        }
    }

    /**
     * a function to iterate through the rest of the nodes
     * 
     * @param thisVertex the vertex to compare thre rest of the vertices to
     * @param slot       the slot to potentially add the vertex to
     */
    public void iterateOtherThroughVertices(String thisVertex, HashSet<String> slot) {
        for (String thatVertex : graph) { // iterate through other vertices in graph
            if (!graph.isVisited(thatVertex)) { // has the vertex been visited yet
                if (!thatVertex.equals(thisVertex)) { // are we looking at two identical vertices
                    Boolean hasConflict = false;
                    for (String vertex : slot) {
                        // check if this vertex conflicts with any of the vertices already in the slot
                        if (graph.containsEdge(vertex, thatVertex)) {
                            hasConflict = true;
                            break;
                        }
                    }
                    if (!hasConflict) { // if no conflict is detected, add the vertex to the slot
                        slot.add(thatVertex);
                    }
                }
            }
        }
    }

    /**
     * a function to generate the time slots
     * 
     * @return a hashset containing hashsets of time slots
     */
    public HashSet<HashSet<String>> createTimeSlots() {
        HashSet<HashSet<String>> slots = new HashSet<>();
        Integer numOfVisited = 0;
        // do this while there are some nodes not yet visited
        while (numOfVisited < graph.size()) {
            // iterate through vertices in the graph
            for (String thisVertex : graph) {
                if (!graph.isVisited(thisVertex)) { // is the vertex not yet visited?
                    HashSet<String> slot = new HashSet<>(); // create a new slot
                    slot.add(thisVertex); // add vertex to slot
                    iterateOtherThroughVertices(thisVertex, slot);
                    // add this slot to the list of slots
                    slots.add(slot);
                    for (String vertex : slot) {
                        // mark all the vertices in this slot as visited
                        graph.visit(vertex);
                        numOfVisited++;
                    }
                }

            }

        }
        return slots;
    }

    /**
     * 
     * @param i the index of the element to be swapped
     * @param j the index of the other element
     */
    public static <E> void swap(int i, int j, E[] courses) {
        E temp = courses[i];
        courses[i] = courses[j];
        courses[j] = temp;
    }

    /**
     * a function that sorts a generic array
     * 
     * @param c the comparator object
     */
    public static <E> void sort(Comparator<E> c, E[] courses) {
        int numUnsorted = courses.length;
        int i;
        int max;
        while (numUnsorted > 0) {
            max = 0;
            for (i = 1; i < numUnsorted; i++) {
                if (c.compare(courses[max], courses[i]) < 0) {
                    max = i;
                }
            }
            swap(max, numUnsorted - 1, courses);
            numUnsorted--;
        }
    }

    /**
     * a function to convert a set to an array
     * 
     * @param set the set to be converted to the array
     * @return the array containing the contents of the set
     */
    public String[] convertSetToArray(HashSet<String> set) {
        String courses[] = new String[set.size()];
        set.toArray(courses);
        return courses;
    }

    /**
     * a function to convert the vector to an array
     * 
     * @param students a vector containing the students
     * @return an array containing the students
     */
    public Student[] convertVectorToArray(Vector<Student> students) {
        Student[] studentArray = new Student[students.size()];
        students.toArray(studentArray);
        return studentArray;
    }

    /**
     * a function to convert the vector to an array
     * 
     * @param students a vector containing the students
     * @return an array containing the students
     */
    public String[] convertCourseVectorToArray(Vector<String> students) {
        String[] studentArray = new String[students.size()];
        students.toArray(studentArray);
        return studentArray;
    }

    /**
     * a function that returns the number of slots in the hashset of slots
     * 
     * @param slots the hashset containing the list of slots
     * @return an integer representing the number os slots
     */
    public Integer countSlots(HashSet<HashSet<String>> slots) {
        return slots.size();
    }

    /**
     * a function to print the slots in alphabetic order
     * 
     * @param slots the hashset containing all the slots
     */
    public void printSlotsInOrder(HashSet<HashSet<String>> slots) {
        int i = 1;
        // go through all the slots and print each course in the slot
        for (HashSet<String> slot : slots) {
            System.out.print(ANSI_GREEN + "Slot " + i + ": " + ANSI_RESET);
            String[] courses = convertSetToArray(slot);
            sort((String course1, String course2) -> course1.compareTo(course2), courses);
            for (int j = 0; j < courses.length; j++) {
                System.out.print(courses[j] + " ");
                slotsTable.put(courses[j], i);
                courseList.add(courses[j]);
            }
            i++;
            System.out.println(" ");
        }
    }

    /**
     * a function to print out all the students taking a specific course
     */
    public void printStudentsTakingCourse() {
        String[] courseListArray = convertCourseVectorToArray(courseList);
        sort((String course1, String course2) -> course1.compareTo(course2), courseListArray);
        // go through all the courses in the array and print students that are taking
        // that course
        for (int i = 0; i < courseListArray.length; i++) {
            System.out.println(" ");
            System.out.println(ANSI_GREEN + courseListArray[i] + ANSI_RESET);

            for (Student student : students) {
                if (student.isTakingCourse(courseListArray[i])) {
                    System.out.println(student.getName());
                }
            }
        }
    }

    /**
     * a function to print the exam schedule for each student's
     */
    public void printExamScheduleForStudents() {
        Student[] studentArray = convertVectorToArray(students);
        sort((Student student1, Student student2) -> student1.getName().compareTo(student2.getName()), studentArray);
        // go through the sorted list of students and and print out everyones schedule.
        for (int i = 0; i < studentArray.length; i++) {
            Student student = studentArray[i];
            System.out.println(" ");
            System.out.println(ANSI_GREEN + student.getName() + ANSI_RESET);
            System.out.println(ANSI_CYAN + "Student's schedule: " + ANSI_RESET);
            for (String course : student) {
                System.out
                        .println("Slot " + slotsTable.get(course) + " : Course: " + ANSI_YELLOW + course + ANSI_RESET);
            }
        }
    }

    /**
     * a function to make the graph
     */
    public void makeGraph() {
        for (Student student : students) {
            addStudentsClassesToGraph(student);
        }
    }

    /**
     * a function to convert t
     */
    public String toString() {
        String stringrepr = "";
        // go through each vertex in the graph and print its neighbors.
        for (String vertex : graph) {
            stringrepr += "Vertex: " + vertex + "\n";
            stringrepr += "Neighbors: " + "\n";
            Iterator<String> neighbors = graph.neighbors(vertex);
            while (neighbors.hasNext()) {
                stringrepr += neighbors.next() + "\n";
            }
            stringrepr += "\n";
        }
        return stringrepr;
    }

    /**
     * a function to handle user input
     * 
     * @param args the array containing user input
     */
    public void handleUserInput(String[] args) {
        if (args.length < 1) {
            System.out.println("Incorrect input format");
            System.out.println(" ");
            System.out.println("Usage: ");
            System.out.println("If you want to view the FIRST extension, input: ");
            System.out.println("java ExamScheduler 1 < <file.txt>");
            System.out.println(" ");
            System.out.println("If you want to view the SECOND extension, input: ");
            System.out.println("java ExamScheduler 2 < <file.txt>");
            System.out.println(" ");
            System.out.println("If you want to view the NO extension, input: ");
            System.out.println("java ExamScheduler 0 < <file.txt>");
            System.out.println(" ");
            System.exit(1);
        }
    }

    /**
     * entry point to the exam scheduler class
     * 
     * @param args
     */
    public static void main(String[] args) {

        Integer option = Integer.parseInt(args[0]);
        Scanner s = new Scanner(System.in);
        ExamScheduler scheduler = new ExamScheduler(s);
        scheduler.makeGraph();
        HashSet<HashSet<String>> slots = scheduler.createTimeSlots();
        // print ouput depending on user input
        if (option == 0) {
            scheduler.printSlotsInOrder(slots);
        } else if (option == 1) {
            scheduler.printSlotsInOrder(slots);
            scheduler.printStudentsTakingCourse();
        } else if (option == 2) {
            scheduler.printSlotsInOrder(slots);
            scheduler.printExamScheduleForStudents();
        }

    }
}