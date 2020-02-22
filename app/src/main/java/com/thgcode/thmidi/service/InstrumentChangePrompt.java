package com.thgcode.thmidi.service;

public class InstrumentChangePrompt extends Prompt
    {
    public InstrumentChangePrompt(iController controller, int value)
        {
        super(controller, value);
    }

    @Override
    protected int getDigitsToClose()
        {
        return 3;
    }

    public void updateValue()
        {
        value = controller.changeInstrument(value);
    }

    @Override
    public boolean acceptsNumberInput()
        {
        return true;
    }
}
