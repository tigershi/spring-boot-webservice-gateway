package cn.picc.finance.sample.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnectUtils {

	//private static StringBuffer fileBuf = new StringBuffer();
	//public static String p_logpath = "";
    private static Logger logger = LoggerFactory.getLogger(JdbcConnectUtils.class);
	public static Connection getSapConnection(String driver, String dbUrl,
	                                    String user, String pwd ,String logFileName) throws Exception {
		//File myFile = new File(p_logpath + DateUtils.formatString("yyyyMMdd") + "/"+ logFileName + ".txt");
		// String url = decodeJdbcUrl(dbUrl);
		String decodedPassword = new String(CryptUtils.decode(pwd));
		//String decodedPassword = pwd;
		logger.info("SAP==="+ logFileName+"===DB connection decodedPassword:" + decodedPassword);
		//WriteLogUtils.writeLog(fileBuf, myFile);
		Connection conn;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, user, decodedPassword);
			logger.info("SAP==="+ logFileName+"===DB connection success:" + decodedPassword);
			//WriteLogUtils.writeLog(fileBuf, myFile);
		} catch (Exception e) {
			// 出现异常再取1次
			// Thread.sleep(1000);
			// for (int i = 1; i < 100000; i++) {
			// String temp = new String("123");
			// temp = null;
			// }
			try {
				Class.forName(driver);
				conn = DriverManager
						.getConnection(dbUrl, user, decodedPassword);
				logger.info("SAP==="+ logFileName+"===for DB connection success:"
						+ decodedPassword);
				//WriteLogUtils.writeLog(fileBuf, myFile);
			} catch (Exception e2) {
				//fileBufAppend("for DB ERROR=====" + e2.getLocalizedMessage());
				logger.error("for DB ERROR=="+logFileName+"===" + e2.getLocalizedMessage());
				//WriteLogUtils.writeExpLog(logFileName, fileBuf);
				throw e2;

			}
			logger.info("DB ERROR=="+logFileName+"===" + e.getLocalizedMessage());
			//WriteLogUtils.writeExpLog(logFileName, fileBuf);
		}

		return conn;
	}

	//private static void fileBufAppend(String message) {fileBuf.append("\n" + message);}
}
