package blindingdark.person.calculator.tools.calculate;


import android.util.Log;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Set;


import blindingdark.person.calculator.configuration.Calculator;
import blindingdark.person.calculator.configuration.Settings;

/**
 * Created by BlindingDark on 2016/6/7 0007.
 */
public class Core {

    static public String eval(String expression, String significantSetting) {

        if (Settings.bigNumMode.equals(significantSetting)) {
            return evalBigNumber(expression);
        }


        String strResult = Calculator.error;
        double result = 0;

        try {
            Expression e = new ExpressionBuilder(expression).build();
            result = e.evaluate();
        } catch (Exception ee) {
            return strResult;
        }

        if (Settings.ten.equals(significantSetting)) {
            strResult = new DecimalFormat("###,###.##########").format(result);
        }
        if (Settings.fifteen.equals(significantSetting)) {
            strResult = new DecimalFormat("###,###.###############").format(result);
        }
        //兼容旧版
        if (Settings.twenty.equals(significantSetting)) {
            strResult = new DecimalFormat("###,###.###############").format(result);
        }

        return strResult;
    }


    static public String eval(String expression) {
        return eval(expression, Settings.fifteen);
    }

    static public String evalBigNumber(String expression) {
        // BigDecimal
        try {
            return BigNumEval.eval(expression);
        } catch (Exception e) {
            return Calculator.error;
        }
    }
}
