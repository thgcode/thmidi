package com.thgcode.thmidi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import com.thgcode.thmidi.App;
import com.thgcode.thmidi.midi.iMidiNoteListener;
import com.thgcode.thmidi.midi.iMidiOutput;
import com.thgcode.thmidi.midi.MidiConstants;
import com.thgcode.thmidi.midi.MidiEffect;
import com.thgcode.thmidi.midi.MidiLayerEffect;
import com.thgcode.thmidi.service.iController;
import com.thgcode.thmidi.service.ChannelChangePrompt;
import com.thgcode.thmidi.service.InstrumentChangePrompt;
import com.thgcode.thmidi.service.KeyboardMapping;
import com.thgcode.thmidi.service.OctavePrompt;
import com.thgcode.thmidi.service.Prompt;
import com.thgcode.thmidi.R;
import java.util.ArrayList;
import java.util.List;
import org.billthefarmer.mididriver.MidiDriver;

public class Main extends Base implements iController, iMidiOutput
{
    private EditText etInput;
    private MidiDriver driver;
    private KeyboardMapping keyMap;
    private Prompt currentPrompt;
    private int channel;
    private int []instruments;
    private MidiEffect []effects;
    private List <iMidiNoteListener> listeners;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setContentView(R.layout.act_main);
        keyMap = new KeyboardMapping("zsxdcvgbhnjm,l.'/q2w3e4rt6y7ui9o0p-[]\\", 48);
        setPrompt(null);
        etInput = (EditText)findViewById(R.id.etInput);
        driver = new MidiDriver();
        channel = 0;
        instruments = new int[MidiConstants.maxMidiChannels];
        for (int i = 0; i < instruments.length; i++)
            {
            instruments[i] = 0;
        }
        effects = new MidiEffect[MidiConstants.maxMidiChannels];
        for (int i = 0; i < effects.length; i++)
            {
            effects[i] = null;
        }
    listeners = new ArrayList <iMidiNoteListener>();
        etInput.setOnKeyListener(new EditText.OnKeyListener()
            {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                processNote(event);
                return true;
            }
        });
    }

    @Override
    protected void onPause()
        {
        super.onPause();
        driver.stop();
    }

    @Override
    protected void onResume()
        {
        super.onResume();
        driver.start();
    }

    protected void sendMidi(int m, int n)
    {
        byte msg[] = new byte[2];
        msg[0] = (byte) m;
        msg[1] = (byte) n;
        driver.write(msg);
    }

    protected void sendMidi(int m, int n, int v)
    {
        byte msg[] = new byte[3];
        msg[0] = (byte) m;
        msg[1] = (byte) n;
        msg[2] = (byte) v;
        driver.write(msg);
    }

    protected void noteOn(int note)
        {
        noteOn(this.channel, note, 127);
    }

    protected void noteOff(int note)
        {
        noteOff(this.channel, note);
    }

    public void noteOn(int channel, int note, int velocity)
        {
        sendMidi(0x90 + channel, note, velocity);
    }

    public void noteOff(int channel, int note)
        {
        sendMidi(0x90 + channel, note, 0);
    }

    private boolean processControlKeys(int keyCode, KeyEvent event)
        {
        if (currentPrompt.acceptsNumberInput() && event.getKeyCode() >= KeyEvent.KEYCODE_0 && event.getKeyCode() <= KeyEvent.KEYCODE_9)
            {
            currentPrompt.addDigit(new Integer(event.getKeyCode() - KeyEvent.KEYCODE_0).toString());
            return true;
        }
        switch (event.getKeyCode())
            {
            case KeyEvent.KEYCODE_F1:
                new ChannelChangePrompt(this, channel);
                return true;
            case KeyEvent.KEYCODE_F2:
                new InstrumentChangePrompt(this, instruments[channel]);
                return true;
            case KeyEvent.KEYCODE_F10:
                if (effects[channel] != null)
                    {
                    removeEffect(channel);
                }
                else
                    {
                    addEffect(new MidiLayerEffect(channel, this));
                }
                return true;
            case 19: // up arrow
                currentPrompt.increaseOrDecreaseValue(1);
                return true;
            case 20: // down arrow
                currentPrompt.increaseOrDecreaseValue(-1);
                return true;
        }
        return false;
    }

    private void processNote(KeyEvent event)
        {
        int keyCode = event.getUnicodeChar(event.getMetaState());
        if (event.getAction() == event.ACTION_DOWN && !processControlKeys(keyCode, event) && keyMap.contains(keyCode) && !keyMap.isPressed(keyCode))
            {
            int note = keyMap.getNote(keyCode);
            keyMap.press(keyCode);
            noteOn(note);
            for (iMidiNoteListener listener: listeners)
                {
                listener.onNoteOn(channel, note, 127);
            }
        }
        else if (event.getAction() == event.ACTION_UP && keyMap.contains(keyCode))
            {
            int note = keyMap.getNote(keyCode);
            keyMap.release(keyCode);
            noteOff(note);
            for (iMidiNoteListener listener: listeners)
                {
                listener.onNoteOff(channel, note);
            }
        }
    }

    public int changeOctave(int octave)
        {
        keyMap.changeOctave(octave);
        return keyMap.getOctave();
    }

    public int changeInstrument(int instrument)
        {
        if (instrument < 0)
            {
            instrument = 0;
        }
        else if (instrument > MidiConstants.maxInstruments)
            {
            instrument = MidiConstants.maxInstruments;
        }
        sendMidi(0xC0 + channel, instrument);
        instruments[channel] = instrument;
        return instrument;
    }

    public int changeChannel(int channel)
        {
        if (channel < 0)
            {
            channel = 0;
        }
        else if (channel > MidiConstants.maxMidiChannels)
            {
            channel = MidiConstants.maxMidiChannels;
        }
        this.channel = channel;
        return channel;
    }

    public void setPrompt(Prompt prompt)
        {
        if (prompt == null)
            {
            new OctavePrompt(this, keyMap.getOctave());
        }
        else
            {
            currentPrompt = prompt;
        }
    }

    private boolean addEffect(MidiEffect effect)
        {
        if (effects[effect.getChannel()] != null)
            {
            return false;
        }
        effects[effect.getChannel()] = effect;
        return true;
    }

    private boolean removeEffect(int channel)
        {
        if (effects[channel] == null)
            {
            return false;
        }
        effects[channel].delete();
        effects[channel] = null;
        return true;
    }

    public void addMidiNoteListener(iMidiNoteListener listener)
        {
        listeners.add(listener);
    }

    public void removeMidiNoteListener(iMidiNoteListener listener)
        {
        listeners.remove(listener);
    }
}
