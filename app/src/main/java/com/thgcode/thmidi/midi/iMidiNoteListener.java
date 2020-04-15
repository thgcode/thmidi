package com.thgcode.thmidi.midi;

public interface iMidiNoteListener
    {
    public void onNoteOn(int channel, int note, int velocity);
    public void onNoteOff(int channel, int note);
}
