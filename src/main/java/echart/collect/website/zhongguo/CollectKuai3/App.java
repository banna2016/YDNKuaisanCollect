package main.java.echart.collect.website.zhongguo.CollectKuai3;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.StringUtils;

public class App
{
  static String maxIssueId = null;
  static String lineCount = null;
//  static String srcNumberTbName = null;
  static String descNumberTbName = null;
  static String descMissTbName = null;
  static String maxMissIssueId = null;
  private static final String name = "k3hb";
  
  private static void initParam()
  {
    Properties p = new Properties();
    InputStream is = App.class.getClassLoader().getResourceAsStream("db.properties");
    try
    {
      p.load(is);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    lineCount = p.getProperty("lineCount", "79");
//    srcNumberTbName = p.getProperty("srcNumberTbName");
    descNumberTbName = p.getProperty("descNumberTbName");
    descMissTbName = p.getProperty("descMissTbName");
  }
  
  public static void main(String[] args)
  {
    initParam();
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
		
		@Override
		public void run() {
			try
	          {
	            App.parseDocumentFromUrl("k3hb");
	            App.missAnalysis();
	          }
	          catch (Exception e)
	          {
	            e.printStackTrace();
	          }
			
		}
	}, new Date(), 10000L);
  }
  
  public static void missAnalysis()
  {
	  Data2Db data2Db = new Data2Db();
	  String missIssueNumber = data2Db.findMaxIssueIdFromDesMissTable();
	    if (missIssueNumber != null)
	    {
	     SrcDataBean  srcDataBean = data2Db.getRecordByDesIssueNumber(getNextIssueNumber(missIssueNumber));
	      if (srcDataBean != null) {
	        data2Db.insertMissData(srcDataBean);
	      }
	    }
  }
  
  private static void parseDocumentFromUrl(String name)
  {
    Document doc = null;
    try
    {
      Connection con = Jsoup.connect("https://chart.ydniu.com/trend/k3heb/");
      con.header("User-Agent", 
        "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
      con.timeout(10000);
      doc = con.get();
      Data2Db dataToDb = new Data2Db();
      if (StringUtils.isNullOrEmpty(maxIssueId))
      {
        String issueMax = dataToDb.findMaxIssueIdFromDescDb();
        if (issueMax != null) {
          maxIssueId = issueMax;
        }
      }
      SrcDataBean srcDataBean = getKuaisanLotteryNumberList(doc);
      if ((srcDataBean.getIssueId() != null) && (!srcDataBean.getIssueId().equals(maxIssueId)))
      {
        maxIssueId = srcDataBean.getIssueId();
        dataToDb.insertBaseData(srcDataBean);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  private static SrcDataBean getKuaisanLotteryNumberList(Document doc)
  {
    SrcDataBean srcDataBean = new SrcDataBean();
    Elements lastDate = doc.getElementsByAttributeValueContaining("id", "tdMaxIsuse");
    Elements parent = lastDate.parents();
    Element element = parent.get(0);
    Elements list = element.getElementsByClass("flanse");
    if (list.isEmpty()) {
      list = element.getElementsByClass("fhuang");
    }
    String issueId = lastDate.text().substring(2);
    String calIssueId = issueId.substring(0, 6) + "0" + issueId.substring(6);
    if (!calIssueId.equals(maxIssueId))
    {
      srcDataBean.setIssueId(calIssueId);
      for (int i = 0; i < list.size(); i++) {
        switch (i)
        {
	        case 0: 
	          srcDataBean.setNo1(list.get(i).html() != null ? Integer.parseInt(list.get(i).html()) : null);
	          break;
	        case 1: 
	          srcDataBean.setNo2(list.get(i).html() != null ? Integer.parseInt(list.get(i).html()) : null);
	          break;
	        case 2: 
	          srcDataBean.setNo3(list.get(i).html() != null ? Integer.parseInt(list.get(i).html()) : null);
        }
      }
    }
    return srcDataBean;
  }
  
 /* private static void collectData()
  {
    Data2Db data2Db = new Data2Db();
    if (StringUtils.isNullOrEmpty(maxIssueId)) {
      maxIssueId = data2Db.findMaxIssueIdFromDescDb();
    }
    String issueNumber = data2Db.findMaxIssueIdFromSrcDb();
    if (!maxIssueId.equals(issueNumber))
    {
      maxIssueId = issueNumber;
      SrcDataBean srcDataBean = data2Db.getRecordByIssueId(issueNumber);
      data2Db.insertBaseData(srcDataBean);
    }
    if (StringUtils.isNullOrEmpty(maxMissIssueId)) {
      maxMissIssueId = data2Db.findMaxIssueIdFromDesMissTable();
    }
    SrcDataBean srcMissData = data2Db.getRecordByIssueId(getNextIssueNumber(maxMissIssueId));
    if ((srcMissData != null) && (!StringUtils.isNullOrEmpty(srcMissData.getIssueId())))
    {
      maxMissIssueId = srcMissData.getIssueId();
      data2Db.insertMissData(srcMissData);
    }
  }*/
  
  public static String getNextIssueNumber(String issueNumber)
  {
    String nextIssueNumber = null;
    String issueCode = issueNumber.substring(issueNumber.length() - 2, issueNumber.length());
    if (issueCode.equals(lineCount))
    {
      nextIssueNumber = getNextDay(issueNumber.substring(0, 6)) + "001";
    }
    else
    {
      int codeInt = Integer.parseInt(issueCode) + 1;
      if (codeInt < 10) {
        nextIssueNumber = issueNumber.substring(0, issueNumber.length() - 2) + "0" + codeInt;
      } else {
        nextIssueNumber = issueNumber.substring(0, issueNumber.length() - 2) + codeInt;
      }
    }
    return nextIssueNumber;
  }
  
  public static String getNextDay(String day)
  {
    SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
    Calendar calendar = new GregorianCalendar();
    String dateString = null;
    try
    {
      Date date = formatter.parse(day);
      calendar.setTime(date);
      calendar.add(5, 1);
      date = calendar.getTime();
      dateString = formatter.format(date);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return dateString;
  }
}
