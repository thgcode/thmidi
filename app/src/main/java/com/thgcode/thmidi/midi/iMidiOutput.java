package com.thgcode.thmidi.midi;

public interface iMidiOutput
    {
    public void noteOn(int channel, int note, int velocity);
    public void noteOff(int channel, int note);
    public void addMidiNoteListener(iMidiNoteListener listener);
    public void removeMidiNoteListener(iMidiNoteListener listener);
}
