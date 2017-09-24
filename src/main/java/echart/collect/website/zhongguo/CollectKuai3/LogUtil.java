package main.java.echart.collect.website.zhongguo.CollectKuai3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil
{
  public static synchronized void info(String info) {}
  
  public static synchronized void error(String error) {}
  
  private static String getNowTimeStr()
  {
    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    return dateFormat.format(now);
  }
}
