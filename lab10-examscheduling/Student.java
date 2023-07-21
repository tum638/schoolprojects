
// Students, fill this in.
import java.util.Scanner;

import java.util.Iterator;

import structure5.*;
import java.lang.Iterable;

public class Student implements Iterable<String> {
    // the student's name
    protected String name;

    // the classes the student is taking
    protected Vector<String> classes;

    // the scanner object
    protected Scanner s;

    /**
     * The constructor to the student class
     * 
     * @param s The scanner object
     */
    public Student(Scanner s) {
        name = s.nextLine();
        classes = new Vector<>();
        for (int i = 0; i < 4; i++) {
            classes.add(s.nextLine());
        }
    }

    /**
     * a function to get the name of the student
     * 
     * @return a string representing the name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * a function to determine whether a student is taking a course or not
     * 
     * @param course the course whose relationship with the student is to be
     *               determined
     * @return true if the student is taking the class, false if the student isn't
     *         taking the class
     */
    public Boolean isTakingCourse(String course) {
        return classes.contains(course);
    }

    /**
     * the iterator to the student's classes
     */
    public Iterator<String> iterator() {
        return classes.iterator();
    }

    /**
     * a function that returns the string representation of the student
     */
    public String toString() {
        String studentString = "";
        studentString += "Student name: " + this.name + "\n";
        studentString += "\n";
        studentString += "student classes: " + "\n";
        for (String studentclass : classes) {
            studentString += studentclass + "\n";
        }
        return studentString;
    }

    /**
     * entry point to the student class
     * 
     * @param args
     */
    public static void main(String[] args) {

    }
}