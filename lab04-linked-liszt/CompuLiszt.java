import java.util.Random;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class CompuLiszt {
    // denominators representing durations
    private static int[] denominators = { 2, 4, 6, 8, 16 };
    // random object
    private static Random r = new Random();
    // the loudness of a note
    private static final int LOUDNESS = 100;
    // the length of a piece of music
    private static final int MUSIC_LENGTH = 100;
    private static final String[] pitches = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "rest" };

    // returns a random pitch number
    private static String getPitch() {
        int i = r.nextInt(0, 12);
        return pitches[i];

    }

    // returns a random octave
    private static String getOctave() {
        String octave = String.valueOf(r.nextInt(1, 10));
        return octave;

    }

    // returns a random duration
    public static String getDuration() {
        int randIndex = r.nextInt(denominators.length);
        String duration = "";
        switch (randIndex) {
            case 0:
                duration = "1/2";
                break;
            case 1:
                duration = "1/4";
            case 2:
                duration = "1/6";
            case 3:
                duration = "1/8";
            case 4:
                duration = "1/8";
        }
        return duration;

    }

    /**
     * 
     * @param filename the name of the path to the file.
     */
    private static void createFile(String filename) {
        // create a new file object.
        File file = new File("scores/" + filename + ".mus");
        try {
            // check if file creation was successful.
            if (file.createNewFile()) {
                System.out.println("File successfully created, now lets make the 'music', or is it really?");

            } else {
                System.out.println("That file alrealdy exist");
                System.exit(1);
            }

        } catch (IOException e) {
            System.out.println("An error occured while creating the file.");
        }
    }

    /**
     * A function that adds music to the file.
     * 
     * @param filename the name of the file to add music to
     * @param debug    prints out the notes being added to the file if true
     */
    private static void addMusicToFile(String filename, boolean debug) {
        // add MUSIC_LENGTH lines to the file
        for (int i = 0; i <= MUSIC_LENGTH; i++) {
            String pitch = getPitch(); // get a random pitch number
            String octave = getOctave(); // get a random octave
            String duration = getDuration(); // get a random duration
            String line = "";
            // check if the pithc is a rest
            if (pitch.equals("rest")) {

                line = pitch + " " + duration + " " + LOUDNESS; // omit octave it pitch is a rest
            } else {
                line = pitch + octave + " " + duration + " " + LOUDNESS; // include octave if pitch is not a rest
            }

            try {
                // initialize buffer writer
                BufferedWriter writer = new BufferedWriter(new FileWriter("scores/" + filename + ".mus", true));
                writer.write(line); // write line into file
                writer.newLine(); // move to next line
                writer.close(); // close buffer
                if (debug) { // print the notes being appended to the file if debug is true
                    System.out.println("(" + line + ") " + "appended to scores/" + filename + ".mus");
                }

            } catch (IOException e) {
                System.out.println("An error occured while trying to append music to scores/" + filename + ".mus");
            }
        }
    }

    public static void main(String[] args) {
        // check if the args array is empty, terminate program if it is empty.
        if (args.length == 0) {
            System.out.println(" ");
            System.out.println("Please give your music file a name");
            System.out.println(" ");
            System.out.println("Usage: ");
            System.out.println("javac CompuLiszt.java && java CompuLiszt <filename>");
            System.out.println(" ");
            System.exit(1);
        }
        // create a music file using the file name in args
        createFile(args[0]);
        // add notes to the file
        addMusicToFile(args[0], false);
        // get notes list from function in LinkedLiszt class
        SinglyLinkedList<Note> notes = LinkedLiszt.loadScoreFromFile("scores/" + args[0] + ".mus");
        // play the notes from a file.
        Note.play(notes, false);

    }

}