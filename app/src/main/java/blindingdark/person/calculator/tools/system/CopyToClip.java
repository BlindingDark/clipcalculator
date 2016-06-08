package blindingdark.person.calculator.tools.system;


import android.content.ClipData;
import android.content.ClipboardManager;


import blindingdark.person.calculator.configuration.Calculator;

/**
 * Created by BlindingDark on 2016/6/8 0008.
 */
public class CopyToClip {

    public static void copyResult(String strResult, ClipboardManager clip) {
        //2016/6/7 0007 自动复制到剪切板
        if (!(Calculator.error.equals(strResult))) {
            clip.setPrimaryClip(ClipData.newPlainText(Calculator.result, strResult));
        }
    }


}
