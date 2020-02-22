package com.thgcode.thmidi.service;

public class ChannelChangePrompt extends Prompt
    {
    public ChannelChangePrompt(iController controller, int value)
        {
        super(controller, value);
    }

    @Override
    protected int getDigitsToClose()
        {
        return 2;
    }

    public void updateValue()
        {
        value = controller.changeChannel(value);
    }

    @Override
    public boolean acceptsNumberInput()
        {
        return true;
    }
}
