package com.kingdom.park.socket.service.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;


public class Coding {
	
	public static final Logger logger = Logger.getLogger(Coding.class);
	
	public static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * @notes 字节数组转化为Hex字符串
	 * @param bytes
	 * @return String
	 */
	public static String toHexString(byte[] bytes) {
		char[] chars = new char[bytes.length * 2];

		for (int i = 0; i < bytes.length; i++) {
			int b = bytes[i];
			chars[i * 2] = hexDigits[(b & 0xF0) >> 4];
			chars[i * 2 + 1] = hexDigits[b & 0x0F];
		}

		return new String(chars);
	}
	
	/**
	 * @notes 单字节转化为Hex字符串
	 * @param bytes
	 * @return String
	 */
	public static String toHexStringSingleByte(byte singleByte) {
		char[] chars = new char[2];
		int b = singleByte;
		chars[0] = hexDigits[(b & 0xF0) >> 4];
		chars[1] = hexDigits[b & 0x0F];
		return new String(chars);
	}

	/**
	 * 转化字符串为十六进制编码
	 * 
	 * @param s
	 *            普通的字符串
	 * @return
	 */
	public static String toHexString(String s) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str.append(s4);
		}
		return str.toString();
	}

	/**
	 * 转化十六进制编码为字符串
	 * 
	 * @param s
	 *            十六进制的字符串
	 * @return
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
						i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * @notes 字节数组转化为Ascii字符串
	 * @param bytes
	 * @return String
	 */
	public static String toAsciiString(byte[] bytes) {
		String str = "";
		for (int i = 0; i < bytes.length; i++) {
			int n = bytes[i];
			char c = (char) n;
			str += c;
		}
		return str;
	}

	/**
	 * @notes 字符串转化为字节数组
	 * @param str
	 * @return byte[]
	 */
	public static byte[] toByteArray(String str) {
		int length = str.length() / 2;
		byte[] bytes = new byte[length];
		byte[] source = str.getBytes();

		for (int i = 0; i < bytes.length; ++i) {
			byte bh = Byte.decode(
					"0x" + new String(new byte[] { source[i * 2] }))
					.byteValue();
			bh = (byte) (bh << 4);
			byte bl = Byte.decode(
					"0x" + new String(new byte[] { source[i * 2 + 1] }))
					.byteValue();
			bytes[i] = (byte) (bh ^ bl);
		}

		return bytes;
	}

	public static Timestamp toTime(String hms, String ymd) {
		Date dt;
		SimpleDateFormat format;
		try {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int h = Integer.parseInt(hms.substring(0, 2));
			int m = Integer.parseInt(hms.substring(2, 4));
			int s = (int) Double.parseDouble(hms.substring(4));

			int y = Integer.parseInt("20" + ymd.substring(4));
			int M = Integer.parseInt(ymd.substring(2, 4));
			int d = Integer.parseInt(ymd.substring(0, 2));

			dt = format.parse(y + "-" + M + "-" + d + " " + h + ":" + m + ":"
					+ s);
			return new Timestamp(dt.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	public static Date toDate(String s) {
		Date dt;
		SimpleDateFormat format;
		// //System.out.println("-----------"+s+"***********");
		try {
			if (s.length() > 10) {
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dt = format.parse("20" + s.substring(0, 2) + "-"
						+ s.substring(2, 4) + "-" + s.substring(4, 6) + " "
						+ s.substring(6, 8) + ":" + s.substring(8, 10) + ":"
						+ s.substring(10, 12));
			} else {
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int y = Integer.parseInt(s.substring(0, 2), 16);
				int M = Integer.parseInt(s.substring(2, 4), 16);
				int d = Integer.parseInt(s.substring(4, 6), 16);

				int h = Integer.parseInt(s.substring(6, 8), 16);
				int m = Integer.parseInt(s.substring(8, 10), 16);

				String yy = (y > 9) ? String.valueOf(y) : "0"
						+ String.valueOf(y);
				String MM = (M > 9) ? String.valueOf(M) : "0"
						+ String.valueOf(M);
				String dd = (d > 9) ? String.valueOf(d) : "0"
						+ String.valueOf(d);
				String hh = (h > 9) ? String.valueOf(h) : "0"
						+ String.valueOf(h);
				String mm = (m > 9) ? String.valueOf(m) : "0"
						+ String.valueOf(m);

				dt = format.parse("20" + yy + "-" + MM + "-" + dd + " " + hh
						+ ":" + mm + ":" + "00");
			}
		} catch (Exception e) {
			return null;
		}
		return dt;
	}

	public static double toLongitude(String s, String flag) {
		double d = Double.valueOf(s.substring(0, 3));
		double m = Double.valueOf(s.substring(3));
		m = m / 60;
		if (flag.equals("E"))
			return d + m;
		else
			return 0 - (d + m);
	}

	public static double toLongitude(String s) {
		// //System.out.println(s);
		double d = Double.valueOf(s.substring(0, 4));
		double m = Double.valueOf("0." + s.substring(4));
		m = m * 100 / 60;
		return d + m;
	}

	public static double toLatitude(String s, String flag) {
		double d = Double.valueOf(s.substring(0, 2));
		double m = Double.valueOf(s.substring(2));
		m = m / 60;
		if (flag.equals("N"))
			return d + m;
		else
			return 0 - (d + m);
	}

	public static double toLatitude(String s) {
		double d = Double.valueOf(s.substring(0, 2));
		double m = Double.valueOf("0." + s.substring(2));
		m = m * 100 / 60;
		return d + m;
	}

	public static String stringToAscii(String str) {
		String b = "";
		for (int i = 0; i < str.length(); i++) {
			int c = str.charAt(i);
			b += Integer.toString(c);
		}
		return b;
	}
	
	public static byte[] shortToByteArray(short s) {
		byte[] shortBuf = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (shortBuf.length - 1 - i) * 8;
			shortBuf[i] = (byte) ((s >>> offset) & 0xff);
		}
		return shortBuf;
	}

	public static final int byteArrayToShort(byte[] b) {
		return (b[0] << 8) + (b[1] & 0xFF);
	}

	public static byte[] intToByteArray(int value) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}

	public static final int byteArrayToInt(byte[] b) {
		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8)
				+ (b[3] & 0xFF);
	} 

	
	public static String binFromString(String binString) {
		if (binString.length() % 4 != 0) {
			throw new RuntimeException("[" + binString + "]必须是四个字符或四个字符的整数倍。");
		}
		int cnt = binString.length() / 4;
		StringBuffer sb = new StringBuffer();
		StringBuilder sbs = new StringBuilder(binString);
		if (cnt > 0) {
			for (int i = 0; i < cnt; i ++) {
				String s = sbs.substring(0, 4);
				if ("0000".equals(s)) {
					sb.append(0);
				} else if ("0001".equals(s)) {
					sb.append(1);
				} else if ("0001".equals(s)) {
					sb.append(1);
				} else if ("0010".equals(s)) {
					sb.append(2);
				} else if ("0011".equals(s)) {
					sb.append(3);
				} else if ("0100".equals(s)) {
					sb.append(4);
				} else if ("0101".equals(s)) {
					sb.append(5);
				} else if ("0110".equals(s)) {
					sb.append(6);
				} else if ("0111".equals(s)) {
					sb.append(7);
				} else if ("1000".equals(s)) {
					sb.append(8);
				} else if ("1001".equals(s)) {
					sb.append(9);
				} else if ("1010".equals(s)) {
					sb.append('a');
				} else if ("1011".equals(s)) {
					sb.append('b');
				} else if ("1100".equals(s)) {
					sb.append('c');
				} else if ("1101".equals(s)) {
					sb.append('d');
				} else if ("1110".equals(s)) {
					sb.append('e');
				} else if ("1111".equals(s)) {
		  			sb.append('f');
				}
				sbs.delete(0,  4);
			}
		}
		return sb.toString();
	}

	/**
	 * 把数字字符串转换成二进制字符串
	 * @param str
	 * @return
	 */
	public static String StringToBin(String str) {
		String b = "";
		str = str.toLowerCase();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '0':
				b = b + "0000";
				break;
			case '1':
				b = b + "0001";
				break;
			case '2':
				b = b + "0010";
				break;
			case '3':
				b = b + "0011";
				break;
			case '4':
				b = b + "0100";
				break;
			case '5':
				b = b + "0101";
				break;
			case '6':
				b = b + "0110";
				break;
			case '7':
				b = b + "0111";
				break;
			case '8':
				b = b + "1000";
				break;
			case '9':
				b = b + "1001";
				break;
			case 'a':
				b = b + "1010";
				break;
			case 'b':
				b = b + "1011";
				break;
			case 'c':
				b = b + "1100";
				break;
			case 'd':
				b = b + "1101";
				break;
			case 'e':
				b = b + "1110";
				break;
			case 'f':
				b = b + "1111";
				break;
			default:
				break;
			}

		}
		return b;
	}

	public static byte toByteXor(byte[] bytes) {
		int b = (bytes[0] ^ bytes[1]);
		for (int i = 2; i < bytes.length; i++) {
			b = (b ^ bytes[i]);
		}
		return (byte) b;
	}

	public static short crc_ccitt(byte[] args) {
		int crc = 0xFFFF; // initial value
		int polynomial = 0x1021; // 0001 0000 0010 0001 (0, 5, 12)

		// byte[] testBytes = "123456789".getBytes("ASCII");

		byte[] bytes = args;

		for (byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((crc >> 15 & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit)
					crc ^= polynomial;
			}
		}

		crc &= 0xffff;
		//System.out.println("CRC16-CCITT = " + Integer.toHexString(crc));
		return (short) crc;

	}

	/**
	 * 字符串转换成十六进制字符串
	 * 
	 * @param String
	 *            str 待转换的ASCII字符串
	 * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
	 */
	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;

		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			sb.append(' ');
		}
		return sb.toString().trim();
	}

	/**
	 * 十六进制转换字符串
	 * 
	 * @param String
	 *            str Byte字符串(Byte之间无分隔符 如:[616C6B])
	 * @return String 对应的字符串
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * bytes转换成十六进制字符串
	 * 
	 * @param byte[] b byte数组
	 * @return String 每个Byte值之间空格分隔
	 */
	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	/**
	 * bytes字符串转换为Byte值
	 * 
	 * @param String
	 *            src Byte字符串，每个Byte之间没有分隔符
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		//System.out.println(l);
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = Byte.decode("0x" + src.substring(i * 2, m)
					+ src.substring(m, n));
		}
		return ret;
	}

	/**
	 * String的字符串转换成unicode的String
	 * 
	 * @param String
	 *            strText 全角字符串
	 * @return String 每个unicode之间无分隔符
	 * @throws Exception
	 */
	public static String strToUnicode(String strText) throws Exception {
		char c;
		StringBuilder str = new StringBuilder();
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128)
				str.append("\\u" + strHex);
			else
				// 低位在前面补00
				str.append("\\u00" + strHex);
		}
		return str.toString();
	}

	/**
	 * unicode的String转换成String的字符串
	 * 
	 * @param String
	 *            hex 16进制值字符串 （一个unicode为2byte）
	 * @return String 全角字符串
	 */
	public static String unicodeToString(String hex) {
		int t = hex.length() / 6;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++) {
			String s = hex.substring(i * 6, (i + 1) * 6);
			// 高位需要补上00再转
			String s1 = s.substring(2, 4) + "00";
			// 低位直接转
			String s2 = s.substring(4);
			// 将16进制的string转为int
			int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
			// 将int转换为字符
			char[] chars = Character.toChars(n);
			str.append(new String(chars));
		}
		return str.toString();
	}

	/**
	 * 二进制,字节数组,字符,十六进制,BCD编码转换 把16进制字符串转换成字节数组
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static final Object bytesToObject(byte[] bytes) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(in);
		Object o = oi.readObject();
		oi.close();
		return o;
	}

	public static final byte[] objectToBytes(Serializable s) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream ot = new ObjectOutputStream(out);
		ot.writeObject(s);
		ot.flush();
		ot.close();
		return out.toByteArray();
	}

	public static final String objectToHexString(Serializable s)
			throws IOException {
		return bytesToHexString(objectToBytes(s));
	}

	public static final Object hexStringToObject(String hex)
			throws IOException, ClassNotFoundException {
		return bytesToObject(hexStringToByte(hex));
	}

	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	public static String MD5EncodeToHex(String origin) {
		return bytesToHexString(MD5Encode(origin));
	}

	public static byte[] MD5Encode(String origin) {
		return MD5Encode(origin.getBytes());
	}

	public static byte[] MD5Encode(byte[] bytes) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			return md.digest(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}

	}

	private static String toHexUtil(int n){
		String rt="";
		switch(n){
		case 10:rt+="0A";break;
		case 11:rt+="0B";break;
		case 12:rt+="0C";break;
		case 13:rt+="0D";break;
		case 14:rt+="0E";break;
		case 15:rt+="0F";break;
		default:
			rt+=n;
		}
		return rt;
	}
	
	public static String toHex(int n){
		StringBuilder sb=new StringBuilder();
		if(n/16==0){
			return toHexUtil(n);
		}else{
			String t=toHex(n/16);
			int nn=n%16;
			sb.append(t).append(toHexUtil(nn));
		}
		return sb.toString();
	}
	
	/**
	 * 十六进制转ASCII
	 * @param str
	 * @return
	 */
	public static String parseAscii(String str){
		StringBuilder sb=new StringBuilder();
		byte[] bs=str.getBytes();
		for(int i=0;i<bs.length;i++)
			sb.append(toHex(bs[i]));
		return sb.toString();
	}
	
	/**
	 * 把int转换成32位的二进制码
	 * @param num
	 * @return
	 */
	public static String integer2binary(int num) {
		String ns = Integer.toBinaryString(num);
		StringBuilder sb = new StringBuilder("00000000000000000000000000000000");
		sb.delete(sb.length() - ns.length(), sb.length());
		sb.append(ns);
		return sb.toString();
	}
//
//	/**
//	 * 把short转换成16位的二进制码
//	 * @param num
//	 * @return
//	 */
//	public static String short2binary(short num) {
//		double term;
//		String binaryStr = " ";
//		if (num < 0) {// 这个数为负数
//			num = (short) Math.abs(num);
//			String negStr = short2binary(num);
//			StringBuffer strBuf = new StringBuffer(negStr);// 先得到这个数绝对值的二进制串
//			char[] binChar = { '0', '1' };
//			// ///////////////取这个数的补码，既是这个数绝对值的二进制的补码//////////////////
//			// ///////////先取反码/////////////////////////////
//			for (int j = 0; j <= 15; j++) {
//				if (strBuf.charAt(j) == binChar[0]) {
//					strBuf.setCharAt(j, binChar[1]);
//				} else {
//					strBuf.setCharAt(j, binChar[0]);
//				}
//			}
//			// ////////////反码家1/////////////////////////////
//
//			if (strBuf.charAt(15) == binChar[0]) {
//				strBuf.setCharAt(15, binChar[1]);
//				return strBuf.toString();
//			} else {
//				strBuf.setCharAt(15, binChar[0]);
//				for (int i = 15; i > 0; i--) {
//					if (strBuf.charAt(i - 1) == binChar[1]) {
//						strBuf.setCharAt(i - 1, binChar[0]);
//						if (i == 1) {
//							return strBuf.toString();
//						} else {
//							continue;
//						}
//
//					} else {
//						strBuf.setCharAt(i - 1, binChar[1]);
//						return strBuf.toString();
//					}
//
//				}
//
//			}
//		}
//		for (int i = 15; i >= 0; i--) {// 求取这个树的二进制串
//			term = Math.pow(2, i);
//			if (num == term) {
//				binaryStr += "1 ";
//				for (int j = 0; j < i; j++)
//					binaryStr += "0 ";
//				return binaryStr;
//			} else if (num > term) {
//				binaryStr += "1 ";
//				num = (short) (num - term);
//			} else {
//				binaryStr += "0 ";
//			}
//		}
//		return binaryStr;
//	}

	/**
	 * 16进制转换为二进制
	 */
	 public static String hexString2binaryString(String hexString)  
	    {  
	        if (hexString == null || hexString.length() % 2 != 0)  
	            return null;  
	        String bString = "", tmp;  
	        for (int i = 0; i < hexString.length(); i++)  
	        {  
	            tmp = "0000"  
	                    + Integer.toBinaryString(Integer.parseInt(hexString  
	                            .substring(i, i + 1), 16));  
	            bString += tmp.substring(tmp.length() - 4);  
	        }  
	        return bString;  
	    }  	 
	
	
	/**
	 * 把short转换成16位的二进制码(new)
	 * @param num
	 * @return
	 */
	public static String short2binary(short num) {
		double term;
		String binaryStr = " ";
		if (num < 0) {// 这个数为负数
			if(num==-32768){
				return "1000000000000000";
			}
			num = (short) Math.abs(num);
			String negStr = short2binary(num).replace(" ","");
			StringBuffer strBuf = new StringBuffer(negStr);// 先得到这个数绝对值的二进制串
			char[] binChar = { '0', '1' };
			// ///////////////取这个数的补码，既是这个数绝对值的二进制的补码//////////////////
			// ///////////先取反码/////////////////////////////
			for (int j = 0; j <= 15; j++) {
				if (strBuf.charAt(j) == binChar[0]) {
					strBuf.setCharAt(j, binChar[1]);
				} else {
					strBuf.setCharAt(j, binChar[0]);
				}
			}
			// ////////////反码家1/////////////////////////////

			if (strBuf.charAt(15) == binChar[0]) {
				strBuf.setCharAt(15, binChar[1]);
				return strBuf.toString();
			} else {
				strBuf.setCharAt(15, binChar[0]);
				for (int i = 15; i > 0; i--) {
					if (strBuf.charAt(i - 1) == binChar[1]) {
						strBuf.setCharAt(i - 1, binChar[0]);
						if (i == 1) {
							return strBuf.toString();
						} else {
							continue;
						}

					} else {
						strBuf.setCharAt(i - 1, binChar[1]);
						return strBuf.toString();
					}

				}

			}
		}
		for (int i = 15; i >= 0; i--) {// 求取这个树的二进制串
			term = Math.pow(2, i);
			if (num == term) {
				binaryStr += "1 ";
				for (int j = 0; j < i; j++)
					binaryStr += "0 ";
				return binaryStr;
			} else if (num > term) {
				binaryStr += "1 ";
				num = (short) (num - term);
			} else {
				binaryStr += "0 ";
			}
		}
		return binaryStr;
	}
	
	/**
	 * 数字转换成二进制码
	 * @param num 数值 
	 * @param radix 使用几进制，二进制：2，八进制：8，十进制：10，十六进制：16
	 * @return
	 */
	public static String numberToBinnaryString(String num) {
		BigInteger src = new BigInteger(num);
		String str = src.toString(2);
		return str;
	}

	
	/**
	 * 二进制码转换成数字
	 * @param num 数值 
	 * @param radix 使用几进制，二进制：2，八进制：8，十进制：10，十六进制：16
	 * @return
	 */
	public static String binnaryToNumberString(String num) {
		BigInteger src = new BigInteger(num, 2);
		String str = src.toString();
		return str;
	}
	
	
	/**
	 * 获取无符号数
	 * 将data字节型数据转换为0~255 (0xFF即BYTE)
	 * @param data
	 * @return
	 */
	public static int getUnsignedByte(byte data) { 
												
		return data & 0x0FF;
	}

	/**
	 * 获取无符号数
	 * @param data
	 * @return
	 */
	public static int getUnsignedShort(short data) {
		return data & 0xFFFF;
	}

	/**
	 * 获取无符号数
	 * @param data
	 * @return
	 */
	public static long getUnsignedInt(int data) {
		return data & 0xFFFFFF;
	}
	
	
	
	
	
	public static void main(String[] args){
//		String str="e10adc3949ba59abbe56e057f20f883e";
//		String hexStr=str2HexStr(str);
//		System.out.println("+++++++"+hexStr);
		
//		String ss="{\"version\": \"1.4\",\"uptime\": \"20160722183000\",\"freecount\": \"99\"}";
//		String hexss=str2HexStr(ss);
//		System.out.println("+++++"+hexss);
		
//		String ss="7B 22 72 65 73 75 6C 74 22 3A 22 59 22 2C 22 64 61 74 61 22 3A 5B 7B 22 72 65 74 5F 63 6F 64 65 22 3A 22 31 22 2C 22 64 6F 77 6E 74 69 6D 65 22 3A 22 32 30 31 36 31 31 31 31 30 39 35 36 32 37 22 2C 22 70 61 72 6B 63 6F 64 65 22 3A 22 36 31 30 31 31 33 32 30 30 30 30 35 22 7D 5D 2C 22 65 72 72 6F 72 5F 63 6F 64 65 22 3A 22 38 30 30 30 30 30 30 30 22 2C 22 65 72 72 6F 72 5F 6D 73 67 22 3A 22 B3 C9 B9 A6 22 7D";
//		String ss="B9 A6 22 7D B0 8F 0D 0A";
//		String ss="7B 22 72 65 73 75 6C 74 22 3A 22 59 22 2C 22 64 61 74 61 22 3A 5B 7B 22 61 73 73 65 74 73 5F 73 75 62 74 79 70 65 22 3A 22 31 22 2C 22 63 61 72 5F 69 64 22 3A 22 B6 F5 41 32 32 31 37 39 22 2C 22 66 75 6E 64 62 61 6C 22 3A 22 31 2E 39 36 22 2C 22 61 73 73 65 74 73 5F 73 65 67 6D 65 6E 74 22 3A 22 31 32 35 32 38 35 22 2C 22 61 73 73 65 74 73 5F 74 79 70 65 22 3A 22 31 22 2C 22 63 61 72 74 79 70 65 22 3A 22 31 22 7D 5D 2C 22 65 72 72 6F 72 5F 63 6F 64 65 22 3A 22 38 30 30 30 30 30 30 30 22 2C 22 65 72 72 6F 72 5F 6D 73 67 22 3A 22 B3 C9 B9 A6 22 7D";
//		String ss="24 24 25 00 00 03 80 34 62 64 38 37 66 30 37 30 65 32 30 64 36 31 61 31 31 35 35 31 65 63 63 34 34 65 32 66 34 38 37 00 00 03 51 7B 22 74 6D 70 5F 73 6D 61 6C 6C 5F 63 6F 75 6E 74 22 3A 35 2C 22 66 6C 61 67 22 3A 22 22 2C 22 73 65 6C 66 5F 62 69 67 5F 63 6F 75 6E 74 22 3A 30 2C 22 6D 6F 6E 74 68 5F 73 6D 61 6C 6C 5F 63 6F 75 6E 74 22 3A 30 2C 22 6C 69 63 65 6E 73 65 6B 65 79 22 3A 22 34 62 64 38 37 66 30 37 30 65 32 30 64 36 31 61 31 31 35 35 31 65 63 63 34 34 65 32 66 34 38 37 22 2C 22 73 65 6C 66 5F 61 63 74 75 61 6C 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 62 61 6C 61 6E 63 65 5F 73 6D 61 6C 6C 5F 63 6F 75 6E 74 22 3A 30 2C 22 74 6D 70 5F 74 6F 74 61 6C 5F 73 68 6F 75 6C 64 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 65 78 63 65 70 74 69 6F 6E 5F 73 68 6F 75 6C 64 5F 61 63 63 6F 75 6E 74 22 3A 22 22 2C 22 73 65 6C 66 5F 74 6F 74 61 6C 5F 63 6F 75 6E 74 22 3A 30 2C 22 74 6F 74 61 6C 5F 73 68 6F 75 6C 64 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 62 61 6C 61 6E 63 65 5F 61 63 74 75 61 6C 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 65 78 63 65 70 74 69 6F 6E 5F 63 6F 75 6E 74 22 3A 30 2C 22 74 6D 70 5F 74 6F 74 61 6C 5F 63 6F 75 6E 74 22 3A 35 2C 22 6D 6F 6E 74 68 5F 73 68 6F 75 6C 64 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 62 61 74 63 68 5F 6E 6F 22 3A 22 22 2C 22 6D 6F 6E 74 68 5F 6D 69 64 5F 63 6F 75 6E 74 22 3A 30 2C 22 74 6F 74 61 6C 5F 61 63 74 75 61 6C 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 62 61 6C 61 6E 63 65 5F 6D 69 64 5F 63 6F 75 6E 74 22 3A 30 2C 22 6D 6F 6E 74 68 5F 74 6F 74 61 6C 5F 63 6F 75 6E 74 22 3A 30 2C 22 74 6D 70 5F 62 69 67 5F 63 6F 75 6E 74 22 3A 30 2C 22 76 65 72 73 69 6F 6E 22 3A 22 31 2E 34 22 2C 22 6D 6F 6E 74 68 5F 62 69 67 5F 63 6F 75 6E 74 22 3A 30 2C 22 6F 63 63 75 72 5F 64 61 74 65 22 3A 22 32 30 31 36 31 31 31 31 22 2C 22 74 6D 70 5F 6D 69 64 5F 63 6F 75 6E 74 22 3A 30 2C 22 62 61 6C 61 6E 63 65 5F 73 68 6F 75 6C 64 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 73 65 6C 66 5F 6D 69 64 5F 63 6F 75 6E 74 22 3A 30 2C 22 74 6D 70 5F 74 6F 74 61 6C 5F 61 63 74 75 61 6C 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 6D 6F 6E 74 68 5F 61 63 74 75 61 6C 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 73 65 6C 66 5F 73 6D 61 6C 6C 5F 63 6F 75 6E 74 22 3A 30 2C 22 62 61 6C 61 6E 63 65 5F 62 69 67 5F 63 6F 75 6E 74 22 3A 30 2C 22 65 78 63 65 70 74 69 6F 6E 5F 61 63 74 75 61 6C 5F 61 63 63 6F 75 6E 74 22 3A 22 22 2C 22 62 61 6C 61 6E 63 65 5F 74 6F 74 61 6C 5F 63 6F 75 6E 74 22 3A 30 2C 22 73 65 6C 66 5F 73 68 6F 75 6C 64 5F 61 63 63 6F 75 6E 74 22 3A 30 2C 22 74 6F 6C 6C 6E 6F 22 3A 22 34 30 32 38 38 31 38 39 35 38 34 65 36 64 30 39 30 31 35 38 34 65 36 64 39 36 36 64 30 30 30 30 22 2C 22 74 6F 74 61 6C 5F 63 61 72 5F 63 6F 75 6E 74 22 3A 35 7D F0 2F 0D 0A";
		String ss="24 24 12 00 00 00 7A 34 32 30 31 30 36 32 30 30 30 31 37 32 30 31 36 31 32 33 31 32 33 35 39 35 39 39 30 36 31 34 31 00 00 00 4B 7B 22 72 65 73 75 6C 74 22 3A 22 4E 22 2C 22 64 61 74 61 22 3A 5B 5D 2C 22 65 72 72 6F 72 5F 63 6F 64 65 22 3A 22 38 30 30 30 30 30 30 31 22 2C 22 65 72 72 6F 72 5F 6D 73 67 22 3A 22 E7 B3 BB E7 BB 9F E5 BC 82 E5 B8 B8 22 7D B8 DD 0D 0A";
		String mm=hexStr2Str(ss.replace(" ", ""));
		System.out.println("===="+mm);
		
	}
	
	
	
}
