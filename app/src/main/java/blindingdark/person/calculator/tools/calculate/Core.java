package blindingdark.person.calculator.tools.calculate;


import android.util.Log;



import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.text.DecimalFormat;


import blindingdark.person.calculator.configuration.Calculator;

/**
 * Created by BlindingDark on 2016/6/7 0007.
 */
public class Core {

    static public String eval(String expression) {
        String strResult;
        double result = 0;

        try {
            Expression e = new ExpressionBuilder(expression).build();
            result = e.evaluate();
        } catch (Exception ee) {
            return Calculator.error;
        }
        strResult = new DecimalFormat("###,###.##########").format(result);


        return strResult;
    }

    static public String evalBigNumber(String expression) {
        // BigDecimal
        try {
            return  BigNumEval.eval(expression);
        }catch (Exception e){
            return Calculator.error;
        }
    }
}
