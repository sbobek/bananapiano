package re.geist.bananapiano;

import edu.princeton.cs.introcs.StdAudio;
import re.geist.bananapiano.serialreader.EventHandler;
import re.geist.bananapiano.synthesizer.MusicString;


public class BasicPianoEventHandler implements EventHandler {
    public static final String sounds = "CDEFGAHc";
    private MusicString [] string;
    private Thread musicThread = null;
    private boolean stop = false;

    public BasicPianoEventHandler() {
        string = new MusicString[sounds.length()];
        for (int i = 0; i < string.length; i += 1) {
            string[i] = new MusicString(440.0 * Math.pow(2, (i - 24) / 12.));
        }

        musicThread = new Thread() {
            @Override
            public void run() {
                while(!isStopped()) {
                    advancePlaying();
                }
            }
        };

        musicThread.start();
    }

    private synchronized void advancePlaying(){
        double sample = .0;
        for (MusicString ms : string) {
            sample += ms.sample();
        }
        StdAudio.play(sample);

        for (MusicString ms : string) {
            ms.advance();
        }
    }

    private synchronized boolean isStopped(){
        return stop;
    }

    @Override
    public synchronized void handle(String event) {
        int index = sounds.indexOf(event.trim());
        string[index].pluck();
    }

    @Override
    public synchronized void terminate() {
        stop = true;
    }


}
