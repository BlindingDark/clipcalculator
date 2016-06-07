package blindingdark.person.calculator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import blindingdark.person.calculator.service.ClipboardService;

/**
 * Created by BlindingDark on 2016/6/7 0007.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            ClipboardService.start(context);
        }
    }
}

