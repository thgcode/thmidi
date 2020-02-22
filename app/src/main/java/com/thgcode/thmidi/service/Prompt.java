package com.thgcode.thmidi.service;

public abstract class Prompt
    {
    private String digits;
    protected iController controller;
    protected int value;

    public Prompt(iController controller, int value)
        {
        digits = "";
        this.controller = controller;
        this.value = value;
        controller.setPrompt(this);
    }

    protected int getDigitsToClose()
        {
        return 1;
    }

    public void addDigit(String digit)
        {
        digits += digit;
        if (digits.length() >= getDigitsToClose())
            {
            value = Integer.parseInt(digits);
            updateValue();
            close();
        }
    }

    protected abstract void updateValue();

    protected void close()
        {
        controller.setPrompt(null);
    }

    public void increaseOrDecreaseValue(int increment)
        {
        value += increment;
        updateValue();
    }

    public boolean acceptsNumberInput()
        {
        return true;
    }
}
