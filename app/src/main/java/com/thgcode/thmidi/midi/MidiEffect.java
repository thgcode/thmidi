package com.thgcode.thmidi.midi;

public abstract class MidiEffect
    {
    protected int channel;

    public MidiEffect(int channel)
        {
        this.channel = channel;
    }

    public abstract void delete();

    public int getChannel()
        {
        return channel;
    }
}
