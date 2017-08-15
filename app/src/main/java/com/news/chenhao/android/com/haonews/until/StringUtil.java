package com.news.chenhao.android.com.haonews.until;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringUtil {

	/**
	 * �������ַ�
	 * 
	 * @param length
	 *            ����ַ�ĳ���
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static Date getDateAdd(Date date, int month, int day) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		if (month != 0)
			rightNow.add(Calendar.MONTH, month);
		if (day != 0)
			rightNow.add(Calendar.DAY_OF_YEAR, day);
		return rightNow.getTime();
	}

	public static int compareDate(String pattern, String date1, String date2) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * ��ʽ����ǰʱ���ַ�
	 *                  etStartDate.setText(StringUtil.getNowDateString("yyyy年01月01日"));
	 * @param pattern
	 *            yyyy-MM-dd HH:mm:ss  参数1 是格式 得到当前时间，并且可以改动
	 * @return
	 */
	public static String getNowDateString(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
		return format.format(new Date());
	}

	/**
	 * ��ʽ��ʱ���ַ�          Calendar calendar = Calendar.getInstance();
	                         calendar.add(Calendar.MONTH, -1);
	                         etStartDate.setText(StringUtil.getDataFormatString("yyyy年MM月dd日", calendar.getTime()));
	 * 
	 * @param pattern
	 * @param date    //给指定的一个data    参数1是格式
	 * @return
	 */
	public static String getDataFormatString(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
		return format.format(date);
	}

	public static String getDataFormatString(String pattern1, String pattern2,
											 String str) {
		SimpleDateFormat format = new SimpleDateFormat(pattern1, Locale.CHINA);
		try {
			return format.format(getDateFromString(pattern2, str));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * ��������ַ������ڶ���
	 * 
	 * @param pattern
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateFromString(String pattern, String str)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
		return format.parse(str);
	}

	public static String getStringToUTF(byte[] strs) {
		try {
			return new String(strs, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getGBKToUTF(String str) {
		try {
			return new String(str.getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isEmpty(String str) {
		if (null == str) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean equals(String str1, String str2) {
		return str1.equals(str2);
	}

	public static boolean equalsCaseIgnore(String str1, String str2) {
		return str1.toLowerCase(Locale.CHINA).equals(
				str2.toLowerCase(Locale.CHINA));
	}

	public static boolean isNullString(String str) {
		if (null == str) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean compareVersion(String newVersion, String curVersion) {

		if (newVersion == null || curVersion == null)
			return false;

		String[] newArr = newVersion.split("[^a-zA-Z0-9]+"), curArr = curVersion
				.split("[^a-zA-Z0-9]+");

		int i1, i2;

		for (int index = 0, max = Math.min(newArr.length, curArr.length); index <= max; index++) {

			if (index == curArr.length) {

				if (index < newArr.length) {

					return true;
				}
			}

			try {
				i1 = Integer.parseInt(newArr[index]);
			} catch (Exception x) {
				i1 = Integer.MAX_VALUE;
			}

			try {
				i2 = Integer.parseInt(curArr[index]);
			} catch (Exception x) {
				i2 = Integer.MAX_VALUE;
			}

			if (i1 > i2)
				return true;
		}

		return false;
	}

	// 字符序列转换为16进制字符串
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString().toUpperCase(Locale.CHINA);
	}

	public static byte[] gZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	public static byte[] unGZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}
}
