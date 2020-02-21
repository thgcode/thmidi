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
import com.thgcode.thmidi.service.KeyboardMapping;
import com.thgcode.thmidi.R;
import org.billthefarmer.mididriver.MidiDriver;

public class Main extends Base
{
    private EditText etInput;
    private MidiDriver driver;
    private KeyboardMapping keyMap;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setContentView(R.layout.act_main);
        keyMap = new KeyboardMapping("zsxdcvgbhnjm,l.'/q2w3e4rt6y7ui9o0p-[]\\", 48);
        etInput = (EditText)findViewById(R.id.etInput);
        driver = new MidiDriver();
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
        sendMidi(0x90, note, 127);
    }

    protected void noteOff(int note)
        {
        sendMidi(0x90, note, 0);
    }

    private void processControlCommands(int keyCode, KeyEvent event)
        {
        switch (event.getKeyCode())
            {
            case 19: // up arrow
                keyMap.changeOctave(1);
                break;
            case 20: // down arrow
                keyMap.changeOctave(-1);
                break;
        }
    }

    private void processNote(KeyEvent event)
        {
        int keyCode = event.getUnicodeChar(event.getMetaState());
        if (event.getAction() == event.ACTION_DOWN && keyMap.contains(keyCode) && !keyMap.isPressed(keyCode))
            {
            keyMap.press(keyCode);
            noteOn(keyMap.getNote(keyCode));
        }
        else if (event.getAction() == event.ACTION_UP && keyMap.contains(keyCode))
            {
            keyMap.release(keyCode);
            noteOff(keyMap.getNote(keyCode));
        }
        else if (event.getAction() == event.ACTION_UP)
            {
            processControlCommands(keyCode, event);
        }
    }
}
