package com.thgcode.thmidi.midi;

public class MidiLayerEffect extends MidiEffect implements iMidiNoteListener
    {
    private iMidiOutput output;

    public MidiLayerEffect(int channel, iMidiOutput output)
        {
        super(channel);
        this.output = output;
        output.addMidiNoteListener(this);
    }

    public void delete()
        {
        output.removeMidiNoteListener(this);
    }

    public void onNoteOn(int channel, int note, int velocity)
        {
        // TODO add control to change velocity
        if (channel != this.channel)
            {
            output.noteOn(this.channel, note, 127);
        }
    }

    public void onNoteOff(int channel, int note)
        {
        if (channel != this.channel)
            {
            output.noteOff(this.channel, note);
        }
    }
}
