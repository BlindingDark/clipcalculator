package blindingdark.person.calculator.tools.calculate;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;

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
            return "error";
        }
        strResult = new DecimalFormat("###,###.##########").format(result);
        return strResult;
    }
}
