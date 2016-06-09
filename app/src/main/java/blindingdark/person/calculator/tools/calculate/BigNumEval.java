package blindingdark.person.calculator.tools.calculate;

import java.util.regex.*;
import java.math.BigDecimal;

/**
 * 利用正则式计算表达式的值
 *
 * @author BlindingDark
 *         修改于 6.9.2016
 *         使之适用于大数计算
 * @author: Eastsun
 * @version: 0.5 07.2.26
 */
public class BigNumEval {

    public static final String NUM_PATTERN = // 数字的匹配模式
            "(?:(?<=[\\+\\-\\*\\(/]|^)[+-]|)" // 前缀判断,判断前面的+-是否为符号
                    + "(?:" // 数字及小数点部分, 0. 以及 .0 都是合法的数字
                    + "(?:\\d*\\.)?\\d+|" // .0 的情形
                    + "\\d+(?:\\.\\d*)?" // 0. 的情形
                    + ")" + "(?!\\d|\\.)"; // 边界条件
    public static final String BRA_PATTERN = "\\((NUM)\\)".replace("NUM", NUM_PATTERN);
    public static final String ADD_PATTERN = "(NUM)\\+(NUM)".replace("NUM", NUM_PATTERN);
    public static final String SUB_PATTERN = "(NUM)-(NUM)".replace("NUM", NUM_PATTERN);
    public static final String MUL_PATTERN = "(NUM)\\*(NUM)".replace("NUM", NUM_PATTERN);
    public static final String DIV_PATTERN = "(NUM)/(NUM)".replace("NUM", NUM_PATTERN);
    public static final Pattern BRA = Pattern.compile(BRA_PATTERN);
    public static final Pattern ADD_OR_SUB = Pattern
            .compile("(?<=[^\\+\\-\\*/]|^)(?:" + ADD_PATTERN + "|" + SUB_PATTERN + ")(?=[^\\*/]|$)");
    public static final Pattern MUL_OR_DIV = Pattern.compile("(?<![\\*/])(?:" + MUL_PATTERN + "|" + DIV_PATTERN + ")");

    private static BigDecimal operator(Matcher m, int index) {
        // index = 0 表示是加减法，2表示乘法，3表示除法

        index = index * 2;
        BigDecimal a;
        BigDecimal b;
        BigDecimal r = null;

        if (m.group(1) != null) {
            a = new BigDecimal(m.group(1));
            b = new BigDecimal(m.group(2));
        } else {
            index++;
            a = new BigDecimal(m.group(3));
            b = new BigDecimal(m.group(4));
        }

        switch (index) {
            case 0:
                r = a.add(b);
                break;
            case 1:
                r = a.subtract(b);
                break;
            case 2:
                r = a.multiply(b);
                break;
            case 3:
                try {
                    r = a.divide(b, BigDecimal.ROUND_UNNECESSARY);
                } catch (Exception e) {
                    r = a.divide(b, 16, BigDecimal.ROUND_HALF_UP);
                }

                break;
        }
        return r;
    }

    public static String eval(String str) {
        StringBuilder sb = new StringBuilder(str.replaceAll("\\s+", ""));
        while (true) {
            Matcher m = BRA.matcher(sb);
            if (m.find()) {
                sb.replace(m.start(), m.end(), m.group(1));
            } else {
                int index = 1;
                m = MUL_OR_DIV.matcher(sb);
                if (!m.find()) {
                    index--;
                    m = ADD_OR_SUB.matcher(sb);
                    if (!m.find())
                        break;
                }

                sb.replace(m.start(), m.end(), "" + operator(m, index).stripTrailingZeros().toPlainString());
            }
        }
        return new BigDecimal(sb.toString()).stripTrailingZeros().toPlainString();
    }

}
