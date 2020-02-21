package com.thgcode.thmidi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import com.thgcode.thmidi.R;

public abstract class Base extends Activity
{
    private Vibrator vibes=null;
    public void clickExit(MenuItem item)
    {
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    protected void vibrate(long millis)
    {
        if(vibes==null) {
            vibes=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        }
        vibes.vibrate(millis);
    }
}
