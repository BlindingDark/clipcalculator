package blindingdark.person.calculator;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import android.content.ClipboardManager;


import blindingdark.person.calculator.configuration.Calculator;
import blindingdark.person.calculator.tools.calculate.*;


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

        if (Intent.ACTION_PROCESS_TEXT.equals(action)) {
            // Text shared with app via Intent
            CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
            if (!TextUtils.isEmpty(text)) {
                String strResult = Core.eval(text.toString());
                //2016/6/7 0007 自动复制到剪切板
                if (!(Calculator.error.equals(strResult))) {
                    ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clip.setPrimaryClip(ClipData.newPlainText(Calculator.result, strResult));
                }

                Toast.makeText(getApplicationContext(), strResult, Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }


}
