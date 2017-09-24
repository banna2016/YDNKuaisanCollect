package main.java.echart.collect.website.zhongguo.CollectKuai3;

import java.util.Arrays;

public class CalculationUtil
{
  public static int getAdditionValue(SrcDataBean srcDataBean)
  {
    return srcDataBean.getNo1() + srcDataBean.getNo2() + srcDataBean.getNo3();
  }
  
  public static int checkOddOrEven(int sum)
  {
    return sum % 2;
  }
  
  public static int checkBigOrSmall(int sum, int smallBoundary)
  {
    return sum > smallBoundary ? 1 : 0;
  }
  
  public static int getSpanValue(SrcDataBean srcDataBean)
  {
    int[] noArr = { srcDataBean.getNo1(), srcDataBean.getNo2(), srcDataBean.getNo3() };
    Arrays.sort(noArr);
    return noArr[2] - noArr[0];
  }
  
  public static int checkZeroOneTwoRoad(int cal)
  {
    return cal % 3;
  }
  
  public static int[] getIntArr(SrcDataBean srcDataBean)
  {
    int[] noArr = { srcDataBean.getNo1(), srcDataBean.getNo2(), srcDataBean.getNo3() };
    Arrays.sort(noArr);
    return noArr;
  }
  
  public static String getGroupByNumber(int[] arr)
  {
    StringBuffer rtnStr = new StringBuffer();
    if (arr.length > 0) {
      rtnStr.append(arr[0] * 10 + arr[1]).append(",").append(arr[0] * 10 + arr[2]).append(",").append(arr[1] * 10 + arr[2]);
    }
    return rtnStr.toString();
  }
  
  public static int getNumberForm(SrcDataBean srcDataBean)
  {
    int status = 0;
    if ((srcDataBean.getNo1() == srcDataBean.getNo2()) && (srcDataBean.getNo2() == srcDataBean.getNo3())) {
      status = 1;
    } else if ((srcDataBean.getNo1() != srcDataBean.getNo2()) && 
      (srcDataBean.getNo2() != srcDataBean.getNo3()) && 
      (srcDataBean.getNo3() != srcDataBean.getNo1())) {
      status = 3;
    } else {
      status = 2;
    }
    return status;
  }
  
  public static String ArrToStr(int[] arr)
  {
    StringBuffer rtnStr = new StringBuffer();
    if ((arr != null) && (arr.length > 0)) {
      for (int i = 0; i < arr.length; i++) {
        rtnStr.append(arr[i]);
      }
    }
    return rtnStr != null ? rtnStr.toString() : null;
  }
}
