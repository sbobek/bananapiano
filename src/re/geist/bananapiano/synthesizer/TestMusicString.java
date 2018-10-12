package re.geist.bananapiano.synthesizer;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestMusicString {
    @Test
    public void testTic() {
        // Create a GuitarString of frequency 11025, which
        // is an ArrayRingBuffer of length 4.
        MusicString s = new MusicString(11025);
        s.pluck();

        // Record the front four values
        double s1 = s.sample();
        s.advance();
        double s2 = s.sample();
        s.advance();
        double s3 = s.sample();
        s.advance();
        double s4 = s.sample();

        // If we advance once more, it should be equal to 0.996*0.5*(s1 + s2)
        s.advance();

        double s5 = s.sample();
        double expected = 0.996 * 0.5 * (s1 + s2);

        // Check that new sample is correct, using tolerance of 0.001.
        assertEquals(expected, s5, 0.001);

    }
}
