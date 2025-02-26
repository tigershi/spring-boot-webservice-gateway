package cn.picc.finance.sample.util;

import cn.picc.finance.sample.conf.TransConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class PostDataUtils {
    private static Logger logger = LoggerFactory.getLogger(PostDataUtils.class);
	private static ArrayList<String[]> transList;
	static{
		transList = new ArrayList<String[]>();
		String[] transArray = TransConstant.NSFF_SQL_RESULTS_TRANS.split("::");
		for (int j = 0; j < transArray.length; j++) {
			String[] transListItem = transArray[j].split("-");
			transList.add(transListItem);
		}

	}

	// conf目录枚举。用;分隔 本地环境地址 Windows
	//public static String p_confpathEnum = "E:\\sapmnt\\PIQ\\global\\nsffconf;E:\\sapmnt\\XT1\\global\\nsffconf;E:\\sapmnt\\XP1\\global\\nsffconf";
	// 服务器环境地址 Windows
	//public static String p_confpathEnum = "Z:\\sapmnt\\PIQ\\global\\nsffconf;Z:\\sapmnt\\XT1\\global\\nsffconf;Z:\\sapmnt\\XP1\\global\\nsffconf";

	// sql结果特殊字符替换相关
	//private static String confFileName = "trans";
 //trans.properties   trans.status
	/**
	static {
		System.out.println("---------------------11111111111111-----------------");
		// sql结果特殊字符替换相关
		String sourceFile = "";
		String confStatusFile = "";
		String[] p_confpathArray = p_confpathEnum.split(";");
		for (int i = 0; i < p_confpathArray.length; i++) {
			if (new File(p_confpathArray[i]).exists()) {
				sourceFile = p_confpathArray[i] + "/" + confFileName
						+ ".properties";
				confStatusFile = p_confpathArray[i] + "/" + confFileName
						+ ".status";
				break;
			}
		}
		try {

			Properties p = new Properties();
			FileReader reader = new FileReader(sourceFile);
			p.load(reader);

			transList = new ArrayList<String[]>();
			//  TODO -------------------------
			String[] transArray = p.getProperty("NSFF.SQL.RESULTS.TRANS")
					.split("::");
			for (int j = 0; j < transArray.length; j++) {
				String[] transListItem = transArray[j].split("-");
				transList.add(transListItem);
			}


			// 写trans.status日志
			StringBuffer logSB = new StringBuffer();
			logSB.append("\n\n");
			logSB.append(DateUtils.formatString("yyyy-MM-dd HH:mm:ss"));
			logSB.append("transEngine is OK.");
			WriteLogUtils.writeLog(logSB, new File(confStatusFile));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			// 写trans.status日志
			StringBuffer expLogSB = new StringBuffer();
			expLogSB.append("\n\n");
			expLogSB.append(DateUtils.formatString("yyyy-MM-dd HH:mm:ss"));
			expLogSB.append("transEngine is failure, Exception:"
					+ e.getLocalizedMessage());

		}

	}
	 **/

	// 通过http发送数据到pi
	public static void postData(String postURL, StringBuffer buf, String msgId, String messageId) {

		URL hurl;
		HttpURLConnection con = null;

		if (null != msgId && (!"".equals(msgId))) {
			postURL = postURL + "&msgguid=" + msgId;
		}
		logger.info("发送请求----------------{}", postURL);

		try {
			hurl = new URL(postURL);
			con = (HttpURLConnection) hurl.openConnection();
			con.setDoInput(true);
			// con.setRequestProperty("Content-Type", "text/xml,charset=utf-8");
			con.setDoOutput(true);
		//	System.out.println("发送数据xml----------------\n"+buf.toString());
			// 将字符串转换为指定编码的字节数组
			//logger.info("ReceiveMessageId:{}----PostMsgID(msgguid):{}--发送的xml:\n{}", messageId, msgId, buf.toString());
			byte[] bytes = buf.toString().getBytes(StandardCharsets.UTF_8);
			// 将字节数组转换为指定编码的字符串
			//String newStr = new String(bytes, Charset.forName("UTF_8"));
						//doPost(con, buf.toString().getBytes());
			doPost(con, bytes);
			//doPost(con, buf.toString().getBytes());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("PostData Fail: {}", stringFilter(e.getLocalizedMessage()));
		}finally {
			if (con != null){
				con.disconnect();
			}

		}

	}

	// Create http url connection
	private static void doPost(HttpURLConnection connection, byte[] message)
			throws Exception {
		Date now = new Date(System.currentTimeMillis());
		PrintStream ps = new PrintStream(connection.getOutputStream());
		ps.write(message);
		ps.flush();
		int respCode = connection.getResponseCode();
		logger.info("Http response code:{}", respCode);
		InputStream is = connection.getInputStream();
		if (respCode != 200){
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder sbf = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sbf.append(temp);
				sbf.append("\n");
			}
			logger.info("Http response content:{}", sbf.toString());
		}
	}

	// 对xml文档非法字符过滤
	public static String stringFilter(String srcString) {
		if (srcString == null)
			return "";
		return transSqlFieldValue(srcString);
	}

	private static String transSqlFieldValue(String fieldValue) {

		for (int k = 0; k < transList.size(); k++) {
			fieldValue = fieldValue.replaceAll(transList.get(k)[0], transList.get(k)[1]);
		}
		return fieldValue;
	}
}
