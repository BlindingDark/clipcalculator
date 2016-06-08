package blindingdark.person.calculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;


import blindingdark.person.calculator.configuration.ClipServer;
import blindingdark.person.calculator.service.ClipboardService;


public class MainActivity extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Switch clipServerSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ClipboardService.start(this);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        clipServerSwitch = (Switch) findViewById(R.id.clip_isOpen);
        String clipIsOpen = preferences.getString(ClipServer.isOpen, "noSet");

        if ("noSet".equals(clipIsOpen)){
            editor = preferences.edit();
            editor.putString(ClipServer.isOpen,"true");
            editor.commit();
        }

        if("true".equals(clipIsOpen)){
            clipServerSwitch.setChecked(true);
        }

        if("false".equals(clipIsOpen)){
            clipServerSwitch.setChecked(false);
        }

    }


    public void clipOpenChange(View view) {
        if (clipServerSwitch.isChecked()) {
            editor.remove(ClipServer.isOpen);
            editor.putString(ClipServer.isOpen,"true");
            editor.commit();
        }else{
            editor.remove(ClipServer.isOpen);
            editor.putString(ClipServer.isOpen,"false");
            editor.commit();
        }
    }

}
