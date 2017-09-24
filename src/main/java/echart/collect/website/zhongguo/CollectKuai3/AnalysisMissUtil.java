package main.java.echart.collect.website.zhongguo.CollectKuai3;

import java.util.Arrays;

public class AnalysisMissUtil
{
  public static String getAllNumMiss(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int[] noArr = CalculationUtil.getIntArr(srcDataBean);
    String group = Integer.toString(noArr[0]) + Integer.toString(noArr[1]) + Integer.toString(noArr[2]);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE < 6 AND GROUP_NUMBER = " + group;
    return rtnSql;
  }
  
  public static String getAllGroupMiss(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int[] noArr = CalculationUtil.getIntArr(srcDataBean);
    String inStr = CalculationUtil.getGroupByNumber(noArr);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE IN (6,7) AND GROUP_NUMBER IN (" + inStr + ")";
    return rtnSql;
  }
  
  public static String getTwoSameGroupMiss(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    
    int status = CalculationUtil.getNumberForm(srcDataBean);
    if (status == 2)
    {
      int[] noArr = CalculationUtil.getIntArr(srcDataBean);
      String inStr = CalculationUtil.getGroupByNumber(noArr);
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 8 AND GROUP_NUMBER IN (" + inStr + ")";
    }
    return rtnSql;
  }
  
  public static String getFourNumGroupMiss(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int[] noArr = CalculationUtil.getIntArr(srcDataBean);
    String group = noArr[0] + "%" + noArr[1] + "%" + noArr[2];
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 5 AND GROUP_NUMBER LIKE '%" + group + "%'";
    return rtnSql;
  }
  
  public static String getSmallOrBigMiss(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int[] noArr = CalculationUtil.getIntArr(srcDataBean);
    if (noArr[2] < 4) {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 9 ";
    } else if (noArr[0] > 3) {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 10 ";
    } else if (noArr[1] > 3) {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 11 ";
    } else {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 12 ";
    }
    return rtnSql;
  }
  
  public static String getOddOrEvenMiss(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int odd = 0;
    int[] noArr = CalculationUtil.getIntArr(srcDataBean);
    for (int i = 0; i < noArr.length; i++) {
      if (noArr[i] % 2 == 1) {
        odd++;
      }
    }
    if (odd == 0) {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 14 ";
    } else if (odd == 3) {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 13 ";
    } else if (odd == 2) {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 15 ";
    } else {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 16 ";
    }
    return rtnSql;
  }
  
  public static String getZeroOneTwo(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int sum = CalculationUtil.getAdditionValue(srcDataBean);
    int zot = CalculationUtil.checkZeroOneTwoRoad(sum);
    if (zot == 0) {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 17 ";
    } else if (zot == 1) {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 18 ";
    } else {
      rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 19 ";
    }
    return rtnSql;
  }
  
  public static String getLottoryNum012(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int one = CalculationUtil.checkZeroOneTwoRoad(srcDataBean.getNo1());
    int two = CalculationUtil.checkZeroOneTwoRoad(srcDataBean.getNo2());
    int three = CalculationUtil.checkZeroOneTwoRoad(srcDataBean.getNo3());
    int[] arr = { one, two, three };
    Arrays.sort(arr);
    String group = Integer.toString(arr[0]) + Integer.toString(arr[1]) + Integer.toString(arr[2]);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 20 AND GROUP_NUMBER =  '" + group + "'";
    return rtnSql;
  }
  
  public static String getAndValueMiss(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int sum = CalculationUtil.getAdditionValue(srcDataBean);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 21 AND GROUP_NUMBER='" + sum + "' ";
    return rtnSql;
  }
  
  public static String getOddEvenOfAndValue(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int sum = CalculationUtil.getAdditionValue(srcDataBean);
    int oddOrEven = CalculationUtil.checkOddOrEven(sum);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 22 AND GROUP_NUMBER='" + oddOrEven + "' ";
    return rtnSql;
  }
  
  public static String getSmallOrBigOfAndValue(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int sum = CalculationUtil.getAdditionValue(srcDataBean);
    int bigOrSmall = CalculationUtil.checkBigOrSmall(sum, 10);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 23 AND GROUP_NUMBER='" + bigOrSmall + "' ";
    return rtnSql;
  }
  
  public static String getSpanValueMiss(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int spanVal = CalculationUtil.getSpanValue(srcDataBean);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 25 AND GROUP_NUMBER='" + spanVal + "' ";
    return rtnSql;
  }
  
  public static String getSmallOrBigOfSpanValue(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int spanVal = CalculationUtil.getSpanValue(srcDataBean);
    int bigOrSmall = CalculationUtil.checkBigOrSmall(spanVal, 2);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 26 AND GROUP_NUMBER='" + bigOrSmall + "' ";
    return rtnSql;
  }
  
  public static String getOddEvenOfSpanValue(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int spanVal = CalculationUtil.getSpanValue(srcDataBean);
    int oddOrEven = CalculationUtil.checkOddOrEven(spanVal);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 27 AND GROUP_NUMBER='" + oddOrEven + "' ";
    return rtnSql;
  }
  
  public static String getZeroOneTwoRoadOfSpanValue(SrcDataBean srcDataBean)
  {
    String rtnSql = null;
    int spanVal = CalculationUtil.getSpanValue(srcDataBean);
    int zot = CalculationUtil.checkZeroOneTwoRoad(spanVal);
    rtnSql = "UPDATE " + App.descMissTbName + " SET CURRENT_MISS = 0 WHERE TYPE = 28 AND GROUP_NUMBER='" + zot + "' ";
    return rtnSql;
  }
}
