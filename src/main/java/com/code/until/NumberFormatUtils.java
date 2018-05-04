package com.code.until;

import java.math.BigDecimal;

/**
 * @author zhangjiaying
 *
 * 保留2位小数
 */
public class NumberFormatUtils {

    private NumberFormatUtils(){}
    private static NumberFormatUtils Instance=new NumberFormatUtils();
    public static NumberFormatUtils getInstance(){
        return Instance;
    }
    /**
     * double类型的数据保留num位小数
     * @param value
     * @param num
     * @return
     */
    private  double format(Double value,int num){
        if (value == Double.valueOf(value).intValue()){
            return Double.valueOf(value).intValue();
        }else {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
            return bigDecimal.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    public static void main(String args[]) {
        System.out.println(NumberFormatUtils.getInstance().format(new Double(15000.2653),2));
    }


}
