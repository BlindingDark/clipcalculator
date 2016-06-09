package blindingdark.person.calculator.service;


import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import blindingdark.person.calculator.configuration.Calculator;
import blindingdark.person.calculator.configuration.Settings;
import blindingdark.person.calculator.tools.calculate.Core;
import blindingdark.person.calculator.tools.system.CopyToClip;


public class ClipboardService extends Service {
    private boolean mRegistered = false;

    public ClipboardService() {
    }

    public void onCreate() {
        synchronized (mClipListener) {
            if (!mRegistered) {
                ClipboardManager clipboardManager =
                        (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.addPrimaryClipChangedListener(mClipListener);
                mRegistered = true;
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    ClipboardManager.OnPrimaryClipChangedListener mClipListener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {

                    if (!isClipServerOpen()) {
                        return;
                    }

                    final ClipboardManager clipboard =
                            (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData data = clipboard.getPrimaryClip();

                    if (data == null) {
                        return;
                    }

                    CharSequence CSlabel = data.getDescription().getLabel();
                    if (CSlabel != null) {
                        if (Calculator.result.equals(CSlabel.toString())) {
                            return;
                        }
                    }

                    String text = clipboard.getPrimaryClip().getItemAt(0).getText().toString();

                    if (text != null) {
                        text = text.trim();
                    }

                    if (!TextUtils.isEmpty(text)) {
                        String strResult;
                        if (isBigNumMode()){
                            strResult = Core.evalBigNumber(text.toString());
                        }else{
                            strResult = Core.eval(text.toString());
                        }
                        //复制到剪切板
                        if(isAutoCopyOpen()){
                            CopyToClip.copyResult(strResult, (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));
                        }
                        Toast.makeText(getApplicationContext(), strResult, Toast.LENGTH_SHORT).show();
                    }
                }

            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRegistered) {
            ClipboardManager clipboardManager =
                    (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.removePrimaryClipChangedListener(mClipListener);
        }
    }

    public static void start(Context context) {
        context.startService(new Intent(context, ClipboardService.class));
    }

    private boolean isClipServerOpen() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String clipIsOpen = preferences.getString(Settings.isClipServerOpen, "true");

        if ("false".equals(clipIsOpen)) {
            return false;
        }

        return true;
    }

    private boolean isAutoCopyOpen() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String isAutoCopyOpen = preferences.getString(Settings.isAutoCopyOpen, "true");

        if ("false".equals(isAutoCopyOpen)) {
            return false;
        }

        return true;
    }

    private boolean isBigNumMode() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String isBigNumMode = preferences.getString(Settings.isBigNumMode, "true");

        if ("false".equals(isBigNumMode)) {
            return false;
        }
        return true;
    }
}

