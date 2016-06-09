package blindingdark.person.calculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;


import blindingdark.person.calculator.configuration.Settings;
import blindingdark.person.calculator.service.ClipboardService;


public class MainActivity extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Switch clipServerSwitch;
    Switch autoCopySwitch;
    Switch bigNumModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ClipboardService.start(this);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        clipServerSwitch = (Switch) findViewById(R.id.clip_isOpen);
        autoCopySwitch = (Switch) findViewById(R.id.autoCopy_isOpen);
        bigNumModeSwitch = (Switch) findViewById(R.id.bigNumber_isOpen);

        String clipIsOpen = preferences.getString(Settings.isClipServerOpen, "true");
        String isAutoCopyOpen = preferences.getString(Settings.isAutoCopyOpen, "true");
        String isBigNumMode = preferences.getString(Settings.isBigNumMode, "true");

        // switch clipServer
        if ("true".equals(clipIsOpen)) {
            clipServerSwitch.setChecked(true);
        }

        if ("false".equals(clipIsOpen)) {
            clipServerSwitch.setChecked(false);
        }

        // switch autoCopy
        if ("true".equals(isAutoCopyOpen)) {
            autoCopySwitch.setChecked(true);
        }

        if ("false".equals(isAutoCopyOpen)) {
            autoCopySwitch.setChecked(false);
        }

        // switch bigNumMode
        if ("true".equals(isBigNumMode)) {
            bigNumModeSwitch.setChecked(true);
        }

        if ("false".equals(isBigNumMode)) {
            bigNumModeSwitch.setChecked(false);
        }

    }


    public void clipOpenChange(View view) {
        editor.remove(Settings.isClipServerOpen);
        if (clipServerSwitch.isChecked()) {
            editor.putString(Settings.isClipServerOpen, "true");
        } else {
            editor.putString(Settings.isClipServerOpen, "false");
        }
        editor.commit();
    }

    public void autoCopyChange(View view) {

        editor.remove(Settings.isAutoCopyOpen);
        if (autoCopySwitch.isChecked()) {
            editor.putString(Settings.isAutoCopyOpen, "true");
        } else {
            editor.putString(Settings.isAutoCopyOpen, "false");
        }
        editor.commit();
    }

    public void bigNumberModeChange(View view) {

        editor.remove(Settings.isBigNumMode);
        if (bigNumModeSwitch.isChecked()) {
            editor.putString(Settings.isBigNumMode, "true");
        } else {
            editor.putString(Settings.isBigNumMode, "false");
        }
        editor.commit();
    }
}
