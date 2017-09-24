package main.java.echart.collect.website.zhongguo.CollectKuai3;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.StringUtils;

public class Data2Db
{
  /*public String findMaxIssueIdFromSrcDb()
  {
    Connection srcConn = ConnectSrcDb.getSrcConnection();
    String issueId = null;
    PreparedStatement pstmt = null;
    String sql = "SELECT MAX(ISSUE_ID) FROM " + App.srcNumberTbName;
    try
    {
      pstmt = (PreparedStatement)srcConn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        issueId = rs.getString(1);
      }
      if ((!StringUtils.isNullOrEmpty(issueId)) && 
        (!judgeIssueNumber(issueId))) {
        issueId = null;
      }
      if ((rs != null) && (!rs.isClosed())) {
        rs.close();
      }
    }
    catch (SQLException e)
    {
      LogUtil.error(e.getMessage());
    }
    return issueId;
  }*/
	
	
  
  public String findMaxIssueIdFromDescDb()
  {
    Connection srcConn = ConnectDesDb.getDesConnection();
    String issueId = null;
    PreparedStatement pstmt = null;
    String sql = "SELECT max(issue_number) FROM " + App.descNumberTbName;
    try
    {
      pstmt = (PreparedStatement)srcConn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        issueId = rs.getString(1);
      }
      if ((rs != null) && (!rs.isClosed())) {
        rs.close();
      }
    }
    catch (SQLException e)
    {
      LogUtil.error(e.getMessage());
    }
    return issueId;
  }
  
  public boolean judgeIssueNumber(String issueNumber)
  {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(issueNumber);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }
  
  private boolean match(String regex, String str)
  {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);
    return matcher.matches();
  }
  
 /* public List<SrcDataBean> getAllRecord()
  {
    Connection srcConn = ConnectSrcDb.getSrcConnection();
    List<SrcDataBean> srcList = new ArrayList();
    PreparedStatement pstmt = null;
    String sql = "SELECT issue_id,no_1,no_2,no_3 FROM " + App.srcNumberTbName;
    try
    {
      pstmt = (PreparedStatement)srcConn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
        SrcDataBean srcDataBean = new SrcDataBean();
        srcDataBean.setIssueId(rs.getString(1));
        srcDataBean.setNo1(rs.getInt(2));
        srcDataBean.setNo2(rs.getInt(3));
        srcDataBean.setNo3(rs.getInt(4));
        srcList.add(srcDataBean);
      }
      if ((rs != null) && (!rs.isClosed())) {
        rs.close();
      }
    }
    catch (SQLException e)
    {
      LogUtil.error(e.getMessage());
    }
    return srcList;
  }*/
  
 /* public SrcDataBean getRecordByIssueId(String issueId)
  {
    Connection srcConn = ConnectSrcDb.getSrcConnection();
    SrcDataBean srcDataBean = null;
    PreparedStatement pstmt = null;
    String sql = "SELECT issue_id,no_1,no_2,no_3 FROM " + App.srcNumberTbName + " where issue_id = ?";
    try
    {
      pstmt = (PreparedStatement)srcConn.prepareStatement(sql);
      pstmt.setString(1, issueId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
        srcDataBean = new SrcDataBean();
        srcDataBean.setIssueId(rs.getString(1));
        srcDataBean.setNo1(rs.getInt(2));
        srcDataBean.setNo2(rs.getInt(3));
        srcDataBean.setNo3(rs.getInt(4));
      }
      if ((rs != null) && (!rs.isClosed())) {
        rs.close();
      }
    }
    catch (SQLException e)
    {
      LogUtil.error(e.getMessage());
    }
    return srcDataBean;
  }*/
  
  public SrcDataBean getRecordByDesIssueNumber(String issueNumber)
  {
    Connection srcConn = ConnectDesDb.getDesConnection();
    SrcDataBean srcDataBean = null;
    PreparedStatement pstmt = null;
    String sql = "SELECT issue_number,no1,no2,no3 FROM " + App.descNumberTbName + " where issue_number = ?";
    try
    {
      pstmt = (PreparedStatement)srcConn.prepareStatement(sql);
      pstmt.setString(1, issueNumber);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
      {
        srcDataBean = new SrcDataBean();
        srcDataBean.setIssueId(rs.getString(1));
        srcDataBean.setNo1(rs.getInt(2));
        srcDataBean.setNo2(rs.getInt(3));
        srcDataBean.setNo3(rs.getInt(4));
      }
      if ((rs != null) && (!rs.isClosed())) {
        rs.close();
      }
    }
    catch (SQLException e)
    {
      LogUtil.error(e.getMessage());
    }
    return srcDataBean;
  }
  
  public SrcDataBean caluExtentInfo(SrcDataBean srcDataBean)
  {
    int oneInt = srcDataBean.getNo1();
    int twoInt = srcDataBean.getNo2();
    int threeInt = srcDataBean.getNo3();
    int threeSpan = 0;int threeSum = 0;int bigNumber = 0;int smallNumber = 0;int oddNumber = 0;int evenNumber = 0;int noStatus = 0;int bigCount = 0;int smallCount = 0;
    threeSum = oneInt + twoInt + threeInt;
    int[] b = { oneInt, twoInt, threeInt };
    for (int i = 0; i < b.length; i++) {
      if (b[i] <= 3) {
        smallCount++;
      } else {
        bigCount++;
      }
    }
    Arrays.sort(b);
    bigNumber = b[(b.length - 1)];
    smallNumber = b[0];
    threeSpan = bigNumber - smallNumber;
    if (oneInt % 2 == 0) {
      evenNumber++;
    } else {
      oddNumber++;
    }
    if (twoInt % 2 == 0) {
      evenNumber++;
    } else {
      oddNumber++;
    }
    if (threeInt % 2 == 0) {
      evenNumber++;
    } else {
      oddNumber++;
    }
    if ((oneInt != twoInt) && (twoInt != threeInt) && (oneInt != threeInt)) {
      noStatus = 3;
    } else if ((oneInt == twoInt) && (twoInt == threeInt) && (oneInt == threeInt)) {
      noStatus = 1;
    } else {
      noStatus = 2;
    }
    srcDataBean.setBigNum(bigCount);
    srcDataBean.setEvenNum(evenNumber);
    srcDataBean.setNoStatus(noStatus);
    srcDataBean.setOddNum(oddNumber);
    srcDataBean.setSmallNum(smallCount);
    srcDataBean.setThreeSpan(threeSpan);
    srcDataBean.setThreeSum(threeSum);
    return srcDataBean;
  }
  
  public void insertBaseData(SrcDataBean srcDataBean)
  {
    try
    {
      Connection condes = ConnectDesDb.getDesConnection();
      if (!haveDataInIssueId(srcDataBean.getIssueId(), condes))
      {
        srcDataBean = caluExtentInfo(srcDataBean);
        if (null!=srcDataBean.getNo1() &&0!=srcDataBean.getNo1()&& null!=srcDataBean.getNo2() && 0!=srcDataBean.getNo2()
        		&& null!=srcDataBean.getNo3()&& 0!=srcDataBean.getNo3()) 
        {
          insertData(srcDataBean, condes);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void insertMissData(SrcDataBean srcDataBean)
  {
	  try
	    {
	      Connection condes = ConnectDesDb.getDesConnection();
	      if (!haveMissDataInIssueId(srcDataBean.getIssueId(), condes)) {
	        batchUpdateMiss(srcDataBean, condes);
	      }
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
  }
  
  private void insertData(SrcDataBean srcDataBean, Connection conn)
    throws SQLException
  {
    String sql = "insert into " + App.descNumberTbName + "(issue_number,no1,no2,no3,three_sum,three_span,big_count,small_count,odd_count,even_count,num_status,create_time,origin) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement pstmt = null;
    try
    {
      pstmt = (PreparedStatement)conn.prepareStatement(sql);
      pstmt.setString(1, srcDataBean.getIssueId());
      pstmt.setInt(2, srcDataBean.getNo1());
      pstmt.setInt(3, srcDataBean.getNo2());
      pstmt.setInt(4, srcDataBean.getNo3());
      pstmt.setInt(5, srcDataBean.getThreeSum());
      pstmt.setInt(6, srcDataBean.getThreeSpan());
      pstmt.setInt(7, srcDataBean.getBigNum());
      pstmt.setInt(8, srcDataBean.getSmallNum());
      pstmt.setInt(9, srcDataBean.getOddNum());
      pstmt.setInt(10, srcDataBean.getEvenNum());
      pstmt.setInt(11, srcDataBean.getNoStatus());
      pstmt.setTimestamp(12, new Timestamp(new Date().getTime()));
      pstmt.setInt(13, 1);
      pstmt.executeUpdate();
    }
    catch (SQLException e)
    {
      LogUtil.error("插入基础数据表异常!" + e.getCause());
    }
    finally
    {
      if ((!pstmt.isClosed()) && (pstmt != null)) {
        pstmt.close();
      }
    }
  }
  
  private boolean haveDataInIssueId(String issueId, Connection conn)
    throws SQLException
  {
    boolean flag = false;
    int count = 0;
    String sql = "SELECT COUNT(*) FROM " + App.descNumberTbName + " WHERE ISSUE_NUMBER = " + issueId;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try
    {
      pstmt = (PreparedStatement)conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        count = rs.getInt(1);
      }
      if (count > 0) {
        flag = true;
      }
    }
    catch (SQLException e)
    {
      LogUtil.error("haveDataInIssueId方法异常！" + e.getCause());
    }
    finally
    {
      if ((rs != null) && (!rs.isClosed())) {
        rs.close();
      }
      if ((pstmt != null) && (!pstmt.isClosed())) {
        pstmt.close();
      }
    }
    return flag;
  }
  
  public String findMaxIssueIdFromDesMissTable()
  {
    Connection srcConn = ConnectDesDb.getDesConnection();
    String issueId = null;
    PreparedStatement pstmt = null;
    String sql = "SELECT MAX(ISSUE_NUMBER) FROM " + App.descMissTbName;
    try
    {
      pstmt = (PreparedStatement)srcConn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        issueId = rs.getString(1);
      }
      if ((!StringUtils.isNullOrEmpty(issueId)) && 
        (!judgeIssueNumber(issueId))) {
        issueId = null;
      }
      if ((rs != null) && (!rs.isClosed())) {
        rs.close();
      }
    }
    catch (SQLException e)
    {
      LogUtil.error(e.getMessage());
    }
    return issueId;
  }
  
  private boolean haveMissDataInIssueId(String issueId, Connection conn)
    throws SQLException
  {
    boolean flag = false;
    int count = 0;
    String sql = "SELECT COUNT(*) FROM " + App.descMissTbName + " WHERE ISSUE_NUMBER = '" + issueId + "'";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try
    {
      pstmt = (PreparedStatement)conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        count = rs.getInt(1);
      }
      if (count > 0) {
        flag = true;
      }
    }
    catch (SQLException e)
    {
      LogUtil.error("查询分析表是否存在数据异常!" + e.getCause());
    }
    finally
    {
      if ((rs != null) && (!rs.isClosed())) {
        rs.close();
      }
      if ((pstmt != null) && (!pstmt.isClosed())) {
        pstmt.close();
      }
    }
    return flag;
  }
  
  private void batchUpdateMiss(SrcDataBean srcDataBean, Connection conn)
		    throws SQLException
		  {
		    PreparedStatement stmt = null;
		    try
		    {
		      DatabaseMetaData dbmd = conn.getMetaData();
		      boolean a = dbmd.supportsBatchUpdates();
		      if (a)
		      {
		        boolean booleanautoCommit = conn.getAutoCommit();
		        
		        conn.setAutoCommit(false);
		        stmt = (PreparedStatement)conn.prepareStatement("");
		        
		        stmt.addBatch("UPDATE " + App.descMissTbName + " SET ISSUE_NUMBER = " + srcDataBean.getIssueId() + ",CURRENT_MISS = CURRENT_MISS+1;");
		        stmt.addBatch(AnalysisMissUtil.getAllGroupMiss(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getAllNumMiss(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getFourNumGroupMiss(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getOddOrEvenMiss(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getSmallOrBigMiss(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getTwoSameGroupMiss(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getZeroOneTwo(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getLottoryNum012(srcDataBean));
		        
		        stmt.addBatch(AnalysisMissUtil.getAndValueMiss(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getOddEvenOfAndValue(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getSmallOrBigOfAndValue(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getSpanValueMiss(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getOddEvenOfSpanValue(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getSmallOrBigOfSpanValue(srcDataBean));
		        stmt.addBatch(AnalysisMissUtil.getZeroOneTwoRoadOfSpanValue(srcDataBean));
		        
		        stmt.addBatch("UPDATE " + App.descMissTbName + " SET MAX_MISS = CURRENT_MISS WHERE CURRENT_MISS > MAX_MISS AND CURRENT_MISS != 0;");
		        stmt.executeBatch();
		        
		        conn.commit();
		        conn.setAutoCommit(booleanautoCommit);
		      }
		    }
		    catch (SQLException sqlEx)
		    {
		      LogUtil.error("batchUpdateMiss执行异常！" + sqlEx.getCause());
		    }
		    finally
		    {
		      if ((stmt != null) && (!stmt.isClosed())) {
		        stmt.close();
		      }
		    }
		  }
}
