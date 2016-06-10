package blindingdark.person.calculator;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;
import android.content.ClipboardManager;

import blindingdark.person.calculator.configuration.Settings;
import blindingdark.person.calculator.tools.calculate.*;
import blindingdark.person.calculator.tools.system.CopyToClip;


public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    private void processIntent(Intent intent) {

        String action = intent.getAction();
        String strResult;

        if (Intent.ACTION_PROCESS_TEXT.equals(action)) {
            // Text shared with app via Intent
            CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
            if (!TextUtils.isEmpty(text)) {

                String significantSetting =  getSignificantSetting();
                strResult = Core.eval(text.toString(), significantSetting);

                //自动复制
                if (isAutoCopyOpen()) {
                    CopyToClip.copyResult(strResult, (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
                }
                Toast.makeText(getApplicationContext(), strResult, Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    private boolean isAutoCopyOpen() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String isAutoCopyOpen = preferences.getString(Settings.isAutoCopyOpen, "true");

        if ("false".equals(isAutoCopyOpen)) {
            return false;
        }

        return true;
    }

    private String getSignificantSetting() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String significant = preferences.getString(Settings.significantFigure, Settings.fifteen);
        return  significant;
    }
}
