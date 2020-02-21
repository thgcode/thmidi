package com.thgcode.thmidi.service;

import java.util.HashMap;
import java.util.Map;

public class KeyboardMapping
    {
    protected Map <Integer, Integer> keys;
    protected Map <Integer, Boolean> pressed;
    protected String mapping;
    protected int baseNote;

    public KeyboardMapping(String mapping, int baseNote)
        {
        pressed = new HashMap <Integer, Boolean>();
        keys = new HashMap <Integer, Integer>();
        this.mapping = mapping;
        this.baseNote = baseNote;
        remap();
    }

    protected void remap()
        {
        for (int i = 0; i < mapping.length(); i++)
            {
            keys.put((int)mapping.charAt(i), baseNote + i);
            pressed.put((int)mapping.charAt(i), false);
        }
    }

    public boolean isPressed(int key)
        {
        return pressed.get(key);
    }

    public void press(int key)
        {
        pressed.put(key, true);
    }

    public void release(int key)
        {
        pressed.put(key, false);
    }

    public boolean contains(int key)
        {
        return keys.containsKey(key);
    }

    public int getNote(int key)
        {
        return keys.get(key);
    }

    public void changeOctave(int increment)
        {
        int newOctave = (baseNote / 12) + increment;
        if (newOctave > 8)
            newOctave = 8;
        else if (newOctave < 0)
            newOctave = 0;
        baseNote = newOctave * 12;
        remap();
    }
}
