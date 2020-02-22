package com.thgcode.thmidi.service;

public class OctavePrompt extends Prompt
    {
    public OctavePrompt(iController controller, int value)
        {
        super(controller, value);
    }

    public void updateValue()
        {
    value = controller.changeOctave(value);
    }

    @Override
    public boolean acceptsNumberInput()
        {
        return false;
    }
}
