package re.geist.bananapiano;

import re.geist.bananapiano.serialreader.EventHandler;

import javax.sound.midi.*;

public class GenericPianoEventHandler implements EventHandler{
    public static final String sounds = "CDEFGAHc";
    private Synthesizer synth = MidiSystem.getSynthesizer();
    private final  MidiChannel[] mc = synth.getChannels();
    private final Instrument[] instr = synth.getDefaultSoundbank().getInstruments();

    public GenericPianoEventHandler(int instrument) throws MidiUnavailableException {
        synth.open();
        synth.loadInstrument(instr[90]);
        mc[5].noteOn(60,600);
    }

    public void reloadInstrument(){

    }

    @Override
    public void handle(String event) {
        int index = sounds.indexOf(event.trim());
    }

    @Override
    public void terminate() {

    }


}
