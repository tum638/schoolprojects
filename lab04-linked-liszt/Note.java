
/**
 * A Note class that represents a musical note, along with
 * subroutines for playing notes using Java's MIDI implementation.
 * 
 * Written 2023 Dan Barowy
 */

import javax.sound.midi.*;

public class Note {
    public final boolean isRest; // true if a rest should be played
    public final String pitch; // the pitch string
    public final int midiNote; // the pitch as a MIDI integer value
    public final int loudness; // the loudness: 0 = soft, 127 = loud
    public final Duration duration; // the length of the note

    /**
     * Constructs an object representing a single musical note.
     * 
     * @param pitch    A string representing the pitch, like "A#4".
     * @param loudness A number between 0 and 127, representing the loudness, where
     *                 0 is silence and 127 is loud.
     * @param duration An enum value representing the length of the note.
     */
    public Note(String pitch, int loudness, Duration duration) {
        this.pitch = pitch;
        // is this a rest?
        if (pitch.equals("rest")) {
            this.midiNote = 0;
            this.isRest = true;
        } else {
            this.midiNote = toMIDI(pitch);
            this.isRest = false;
        }

        this.duration = duration;
        if (loudness < 0) {
            this.loudness = 0;
        } else if (loudness > 127) {
            this.loudness = 127;
        } else {
            this.loudness = loudness;
        }
    }

    /**
     * A type representing the length to play the note.
     */
    public enum Duration {
        Whole,
        Half,
        Quarter,
        Sixth,
        Eighth,
        Sixteenth;

        /**
         * Converts a duration value into a time in milliseconds,
         * needed by the Java MIDI interface. Can be adjusted for
         * faster or slower tempos by multiplying by the `tempo`
         * parameter, which is a simple time multiplier.
         * 
         * @param d     A Duration value.
         * @param tempo A tempo, which is a duration multiplier.
         * @return The duration in milliseconds.
         */
        private static long toMillis(Duration d, double tempo) {
            double quarter_ms = tempo * 1000.0;
            double tinyrest = tinyRest(d, tempo);
            double millis = 0;
            switch (d) {
                case Whole:
                    millis = quarter_ms * 4.0;
                    break;
                case Half:
                    millis = quarter_ms * 2.0;
                    break;
                case Quarter:
                    millis = quarter_ms;
                    break;
                case Sixth:
                    millis = quarter_ms * 2.0 / 3.0;
                    break;
                case Eighth:
                    millis = quarter_ms / 2.0;
                    break;
                default:
                    millis = quarter_ms / 4.0;
                    break;
            }
            long actual = Math.round(millis - tinyrest);
            return actual;
        }

        /**
         * Computes a small rest to play at the end of each note;
         * needed because Java would otherwise slur notes together.
         * Parameters have the same meanings as in the
         * {@link #toMillis(Duration, double)} method.
         * 
         * @param d     A Duration value.
         * @param tempo A tempo, which is a duration multiplier.
         * @return The duration in milliseconds.
         */
        private static long tinyRest(Duration d, double tempo) {
            double quarter_ms = tempo * 1000.0;
            double millis = 0;
            switch (d) {
                case Whole:
                    millis = quarter_ms * 4.0;
                    break;
                case Half:
                    millis = quarter_ms * 2.0;
                    break;
                case Quarter:
                    millis = quarter_ms;
                    break;
                case Sixth:
                    millis = quarter_ms * 2.0 / 3.0;
                    break;
                case Eighth:
                    millis = quarter_ms / 2.0;
                    break;
                default:
                    millis = quarter_ms / 4.0;
            }
            long actual = Math.round(millis * 0.25);
            return actual;
        }
    }

    /**
     * Plays the given sequence of notes.
     * 
     * @param notes A list of notes.
     * @param debug If true, prints notes to the console as they are being
     *              played.
     */
    public static void play(IList<Note> notes, boolean debug) {
        double tempo = 1.0 / 3.5; // empirically, this is good

        try {
            Synthesizer synth = MidiSystem.getSynthesizer();

            synth.open();

            MidiChannel[] channels = synth.getChannels();
            channels[0].programChange(0); // Set instrument to piano

            // for reasons completely unclear to me, the first sleep duration
            // is not correct; so sleep now, before we start playing
            Thread.sleep(Duration.toMillis(Duration.Sixteenth, tempo));

            for (int i = 0; i < notes.size(); i++) {
                Note n = notes.get(i);
                if (n.isRest) {
                    long playFor = Duration.toMillis(n.duration, tempo);
                    long restFor = Duration.tinyRest(n.duration, tempo);
                    if (debug)
                        System.out.println(
                                n.duration + " rest playing for " + (playFor + restFor) + " ms.");
                    Thread.sleep(playFor + restFor);
                } else {
                    long playFor = Duration.toMillis(n.duration, tempo);
                    long restFor = Duration.tinyRest(n.duration, tempo);
                    if (debug)
                        System.out.println(
                                n.duration + " playing for " + playFor + " ms with tiny rest of " + restFor + " ms at "
                                        + n.pitch + " = "
                                        + n.midiNote + " midi pitch.");
                    channels[0].noteOn(n.midiNote, n.loudness);
                    Thread.sleep(playFor);
                    channels[0].noteOff(n.midiNote);
                    Thread.sleep(restFor);
                }
            }

            // The last note does not play unless we sleep again... weird.
            Thread.sleep(Duration.toMillis(Duration.Half, tempo));
            synth.close();
        } catch (MidiUnavailableException e) {
            System.out.println("No MIDI. Bummer.");
        } catch (InterruptedException e) {
            System.out.println("Should not happen.");
        }
    }

    /**
     * Converts a Note's pitch to a MIDI value.
     * 
     * @param note A Note object.
     * @return The integer value for the pitch.
     * @throws IllegalArgumentException
     */
    private static int toMIDI(String note) throws IllegalArgumentException {
        String[] noteParts = note.split("(?<=\\D)(?=\\d)");
        if (noteParts.length != 2) {
            throw new IllegalArgumentException("Invalid note format");
        }
        String pitchName = noteParts[0].toUpperCase();
        int octave = Integer.parseInt(noteParts[1]);
        if (octave < 0 || octave > 10) {
            throw new IllegalArgumentException("Invalid octave number");
        }

        int pitch;
        switch (pitchName) {
            case "C":
                pitch = 0;
                break;
            case "C#":
                pitch = 1;
                break;
            case "D":
                pitch = 2;
                break;
            case "D#":
                pitch = 3;
                break;
            case "E":
                pitch = 4;
                break;
            case "F":
                pitch = 5;
                break;
            case "F#":
                pitch = 6;
                break;
            case "G":
                pitch = 7;
                break;
            case "G#":
                pitch = 8;
                break;
            case "A":
                pitch = 9;
                break;
            case "A#":
                pitch = 10;
                break;
            case "B":
                pitch = 11;
                break;
            default:
                throw new IllegalArgumentException("Invalid pitch name: '" + pitchName + "'");
        }

        return octave * 12 + pitch + 12;
    }
}