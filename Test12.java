package com.demo.test;

import java.math.BigDecimal;

public class Test12 {
    
    private static final char[] CN_UPPER_NUMBER = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
    
    private static final char[] CN_UPPER_MONETRAY_UNIT = { '分', '角', '园',
                         '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟', '兆', '拾',
                         '佰', '仟' };
    
    public static String conversion(BigDecimal numberOfMoney)
    {
        StringBuilder result = new StringBuilder();
        
        long number = numberOfMoney.movePointRight(2).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        long scale = number % 100;
        
        int numIndex = 0;
        int numUnit = 0;
        
        if (scale == 0)
        {
            numIndex = 2;
            number /= 100;
            result.insert(0, '整');
        }
        else if (scale % 10 == 0)
        {
            numIndex = 1;
            number /= 10;
        }
        
        boolean lastZero = false;
        int zeroSize = 0;
        while (true)
        {
            numUnit = (int) (number % 10);
            if (numUnit > 0)
            {
                zeroSize = 0;
                if (numIndex > 6 && numIndex < 10 && zeroSize > 0)
                {
                    result.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                }
                result.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                result.insert(0, CN_UPPER_NUMBER[numUnit]);
                lastZero = false;
            }
            else
            {
                if (numIndex == 2 || /*numIndex == 6 || */numIndex == 10)
                {
                    result.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                }
                if (numIndex != 2 && !lastZero && numIndex != 6 && numIndex != 10)
                {
                    result.insert(0, CN_UPPER_NUMBER[numUnit]);
                }
                lastZero = true;
                
                zeroSize++;
            }
            
            numIndex++;
            number /= 10;
            
            if (number <= 0)
            {
                break;
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) throws Exception
    {
        BigDecimal numberOfMoney = new BigDecimal("20600.89");
        System.out.println(conversion(numberOfMoney));
    }
}
