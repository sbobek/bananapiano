package re.geist.bananapiano;

import re.geist.bananapiano.serialreader.EventHandler;

import javax.sound.midi.*;
import java.util.HashMap;

public class GenericPianoEventHandler implements EventHandler{
    public static final String[] sounds = new String[]{"C","#C","D","#D","E","F","#F","G","#G","A","#A","H","CC"};
    private static final int DEFAULT_INSTRUMENT = 0;
    private static final int SCALE_BEGINNING = 60; // Middle C
    private static final int STRENGTH = 600;
    private static final int DEFAULT_CHANNEL = 1;
    private static final int NOTE_IDX = 0;
    private static final int PRESS_IDX = 1;
    private static final int DECAY = 200;


    private Synthesizer synth = MidiSystem.getSynthesizer();
    private final  MidiChannel[] mc = synth.getChannels();
    private final Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
    private int currentInstrument = DEFAULT_INSTRUMENT;

    public GenericPianoEventHandler(int instrument) throws MidiUnavailableException, InvalidMidiDataException {
        synth.open();
        reloadInstrument(instrument);

    }

    public GenericPianoEventHandler() throws MidiUnavailableException, InvalidMidiDataException {
        this(DEFAULT_INSTRUMENT);
    }


    public Instrument[] getInstrumentList(){
        return instr;
    }
    public void reloadInstrument(int instrumentId) throws InvalidMidiDataException {
        synth.unloadInstrument(instr[currentInstrument]);
        synth.loadInstrument(instr[instrumentId]);
        currentInstrument=instrumentId;

        synth.getChannels()[DEFAULT_CHANNEL].programChange(currentInstrument);
    }

    private int indexOf(String note){
        for(int i = 0; i < sounds.length; i++){
            if(sounds[i].equals(note)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void handle(String event) {
        String[] eventString= event.trim().split(":");
        int index = indexOf(eventString[NOTE_IDX].trim());

        //channel, note, strength
        if(eventString[PRESS_IDX].equals("P")) {
            mc[DEFAULT_CHANNEL].noteOn(SCALE_BEGINNING + index, STRENGTH);
        }else{
            mc[DEFAULT_CHANNEL].noteOff(SCALE_BEGINNING + index, DECAY);
        }
    }

    @Override
    public void terminate() {
        synth.close();
    }


}
