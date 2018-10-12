package re.geist.bananapiano;

import edu.princeton.cs.introcs.StdAudio;
import re.geist.bananapiano.serialreader.EventHandler;
import re.geist.bananapiano.synthesizer.MusicString;


public class PianoEventHandler implements EventHandler {
    public static final String sounds = "CDEFGAHc";
    private MusicString [] string;

    public PianoEventHandler(){
        string = new MusicString[sounds.length()];
        for (int i = 0; i < string.length; i += 1) {
            string[i] = new MusicString(440.0 * Math.pow(2, (i - 24) / 12.));
        }
    }

    @Override
    public void handle(String event) {
        int index = sounds.indexOf(event);
        string[index].pluck();
        double sample = .0;
        for (MusicString ms : string) {
            sample += ms.sample();
        }
        StdAudio.play(sample);

        for (MusicString ms : string) {
            ms.advance();
        }
    }


}
