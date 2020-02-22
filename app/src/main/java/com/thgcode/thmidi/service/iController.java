package com.thgcode.thmidi.service;

public interface iController
    {
    public void setPrompt(Prompt prompt);
    public int changeOctave(int octave);
    public int changeInstrument(int instrument);
    public int changeChannel(int channel);
}
