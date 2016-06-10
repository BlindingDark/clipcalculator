package blindingdark.person.calculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import blindingdark.person.calculator.configuration.Settings;
import blindingdark.person.calculator.service.ClipboardService;


public class MainActivity extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Switch clipServerSwitch;
    Switch autoCopySwitch;

    private boolean significantFigureOnItemSelectedListenerRegisteredFirst = true;

    private boolean significantFigureOnItemSelectedListenerRegistered = false;

    AdapterView.OnItemSelectedListener significantFigureOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView tv = (TextView) view;
            tv.setTextSize(13.0f);    //设置大小
            tv.setGravity(android.view.Gravity.RIGHT);   //设置居中
            if (!significantFigureOnItemSelectedListenerRegisteredFirst) {
                switch (position) {
                    case 0:
                        editor.putString(Settings.significantFigure, Settings.ten);
                        break;
                    case 1:
                        editor.putString(Settings.significantFigure, Settings.fifteen);
                        break;
                    case 2:
                        editor.putString(Settings.significantFigure, Settings.twenty);
                        break;
                    case 3:
                        editor.putString(Settings.significantFigure, Settings.bigNumMode);
                        break;
                }
                editor.commit();
                return;
            }
            significantFigureOnItemSelectedListenerRegisteredFirst = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        clipServerSwitch = (Switch) findViewById(R.id.clip_isOpen);
        autoCopySwitch = (Switch) findViewById(R.id.autoCopy_isOpen);

        String clipIsOpen = preferences.getString(Settings.isClipServerOpen, "true");
        String isAutoCopyOpen = preferences.getString(Settings.isAutoCopyOpen, "true");


        Spinner spinner = (Spinner) findViewById(R.id.significant_figure_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.significant_figure, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        synchronized (significantFigureOnItemSelectedListener) {
            if (!significantFigureOnItemSelectedListenerRegistered) {
                spinner.setOnItemSelectedListener(significantFigureOnItemSelectedListener);
                significantFigureOnItemSelectedListenerRegistered = true;
            }

        }


        // switch clipServer
        if ("true".equals(clipIsOpen)) {
            clipServerSwitch.setChecked(true);
            ClipboardService.start(this);
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

        //switch significantFigure
        String significant = preferences.getString(Settings.significantFigure, Settings.fifteen);
        if (Settings.bigNumMode.equals(significant)){
            spinner.setSelection(3);
        }
        if (Settings.ten.equals(significant)){
            spinner.setSelection(0);
        }
        if (Settings.fifteen.equals(significant)){
            spinner.setSelection(1);
        }
        if (Settings.twenty.equals(significant)){
            spinner.setSelection(2);
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
