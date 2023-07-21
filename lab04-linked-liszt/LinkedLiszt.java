import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;

// import Note.Duration;

/**
 * The LinkedLiszt music player. Reads ".mus" files and plays them.
 * 
 * A ".mus" file is a text file with one note per line. Each line is formatted
 * as a space-separate three tuple in the following form:
 * 
 * <pitch> <duration> <loudness>
 * 
 * where pitch is a value like A#4, representing the pitch,
 * where duration is a value like 1/4, representing the duration,
 * and where loudness is a value between 0 and 127, where 0 is silence
 * and 127 is very loud.
 * 
 * Alternatively, a line may also start with a # character, meaning that
 * the rest of the line is a comment.
 * 
 * See the Note class documentation for the valid values of the above
 * pitch, duration, and loudness parameters.
 * 
 * The {@link #main} method of this class will load the given ".mus" file
 * into a singly linked list, and then call the {@link Note#play} method
 * with the list to play it.
 */
class LinkedLiszt {

    /**
     * a function that places durations into the array list
     */

    /**
     * Reads a ".mus" file from the given path and returns a singly linked
     * list filled with note objects. This method performs input validation
     * on the given ".mus" file. If the file does not exist, the user is
     * notified and the program is shut down with System.exit(1). If any
     * of the notes are malformed, the user is notified and the program
     * is shut down with System.exit(1).
     * 
     * @param path A string representing a path to a ".mus" file.
     * @return The list of notes in the ".mus" file.
     */
    public static SinglyLinkedList<Note> loadScoreFromFile(String path) {
        SinglyLinkedList<Note> notes = new SinglyLinkedList<>();// instantiate the Sinlgy Linked list to store notes.
        Scanner s = getScanner(path);
        while (s.hasNextLine()) { // check if it is not the end of the file.
            String line = s.nextLine();// check if a line is a comment, if it is, skip through it
            if (line.substring(0, 1).equals("#")) {
                continue; // do nothing if line is a comment, go to next iteration of loop
            }
            String[] arr = line.split(" ");
            checkCases(arr[1], arr, notes);
        }
        return notes;
    }

    /**
     * a function that creates a scanner object based on the file path where music
     * is to be played from
     * 
     * @param path the path to a file of music to be played
     * @return a scanner object reading from the file
     */
    private static Scanner getScanner(String path) {
        try {
            Scanner s; // initialize scanner
            s = new Scanner(new FileInputStream(path));// make scanner read input from a file.
            return s;
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, we couldn't find the file you specified");
            System.exit(1);
        }
        return null;

    }

    /**
     * a function that infers the duration of a note
     * 
     * @param duration the duration of a note
     * @param arr      a list containing the attributes of a note
     * @param notes    the list where notes are added to
     */
    private static void checkCases(String duration, String[] arr, IList<Note> notes) {
        switch (duration) {
            case "1/1":
                addNote(arr, Note.Duration.Whole, notes);
                break;
            case "1/2":
                addNote(arr, Note.Duration.Half, notes);
                break;
            case "1/4":
                addNote(arr, Note.Duration.Quarter, notes);
                break;
            case "1/6":
                addNote(arr, Note.Duration.Sixth, notes);
                break;
            case "1/8":
                addNote(arr, Note.Duration.Eighth, notes);
                break;
            case "1/16":
                addNote(arr, Note.Duration.Sixteenth, notes);
                break;
            default:
                System.out.println("something went wrong");
                System.exit(1);
                break;
        }

    }

    /**
     * a function that creates a note and adds it to the list.
     * 
     * @param arr      the list containing the attributes of a note
     * @param duration the duration of a note
     * @param list     the list where notes are being added to
     */
    private static void addNote(String[] arr, Note.Duration duration, IList<Note> list) {
        Note note = new Note(arr[0], Integer.parseInt(arr[2]), duration);
        list.addFirst(note);
    }

    /**
     * 
     * @param path the path to the music file to be played
     * @return whether a path is valid or not
     */
    public static boolean isValidInput(String path) {
        String pattern = "^\\\\?.*\\.mus$"; // the file path should end with .mus and can either start with a slash or
                                            // no
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(path);
        // check if the path the user entered matches the required format.
        if (m.matches()) {
            return true;
        }
        System.out.println("Invalid path to mus file");
        return false;
    }

    /**
     * Plays the given music file.
     * 
     * Usage:
     * 
     * $ java LinkedLiszt <file.mus>
     * 
     * @param args An array of length one, where the sole element of the
     *             array is the path to the file to play.
     */
    public static void main(String[] args) {
        // check if user provided any input.
        if (args.length == 0) {
            System.out.println("Sorry, your input format is incorrect");
            System.out.println("Usage: ");
            System.out.println("javac LinkedLiszt.java && java LinkedLiszt <path/to/score.mus>");
            System.exit(0);
        }
        // check if user provided input but got the second parameter wrong.
        if (args.length == 2 && (!args[1].equals("backwards"))) {
            System.out.println("To play the file backwards, type the following: ");
            System.out.println("javac LinkedLiszt.java && java LinkedLiszt <path/to/file.mus> backwards");
            System.out.println("");
            System.out.println("Otherwise, just type:");
            System.out.println("javac LinkedLiszt.java && java LinkedLiszt <path/to/file.mus>");
            System.exit(1);
        } else if (args.length == 2 && args[1].equals("backwards")) {
            // check if file path format is valid.
            if (!isValidInput(args[0])) {
                System.exit(1);
            }
        } else {
            // check if file path format is valid.
            if (!isValidInput(args[0])) {
                System.exit(1);
            }
        }
        // instantiate a list called notes from the output of the loadScore file
        SinglyLinkedList<Note> notes = loadScoreFromFile(args[0]);
        if (args.length == 2) {
            notes.reverse();
        }
        // play song.
        Note.play(notes, false);

    };

}
