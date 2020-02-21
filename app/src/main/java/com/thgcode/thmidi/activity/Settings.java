package com.thgcode.thmidi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import com.thgcode.thmidi.App;
import com.thgcode.thmidi.R;

public class Settings extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager()
            .beginTransaction()
            .replace(android.R.id.content, new com.thgcode.thmidi.fragment.Settings())
            .commit();
    }
}
