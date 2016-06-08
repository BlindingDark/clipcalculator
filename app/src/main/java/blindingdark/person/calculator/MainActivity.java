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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ClipboardService.start(this);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        clipServerSwitch = (Switch) findViewById(R.id.clip_isOpen);
        autoCopySwitch = (Switch) findViewById(R.id.autoCopy_isOpen);

        String clipIsOpen = preferences.getString(Settings.isClipServerOpen, "true");
        String isAutoCopyOpen = preferences.getString(Settings.isAutoCopyOpen, "true");

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
}
