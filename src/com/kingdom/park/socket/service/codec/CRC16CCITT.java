package com.kingdom.park.socket.service.codec;

/** 
 * 类说明 
 * @author 作者:yaozq 
 * @version 创建时间：2016-11-7 上午09:38:04 
 * 
  	************************************************************************* 
	 *  Compilation:  javac CRC16CCITT.java 
	 *  Execution:    java CRC16CCITT s 
	 *  Dependencies:  
	 *   
	 *  Reads in a sequence of bytes and prints out its 16 bit 
	 *  Cylcic Redundancy Check (CRC-CCIIT 0xFFFF). 
	 * 
	 *  1 + x + x^5 + x^12 + x^16 is irreducible polynomial. 
	 * 
	 *  % java CRC16-CCITT 123456789 
	 *  CRC16-CCITT = 29b1 
	 * 
	 *************************************************************************
 * 
 */

	public class CRC16CCITT {   
	    
		/**
		 * 字符串转CRC校验码
		 * @param crcStr 要编码的字符串
		 * @return 两个字节的长度范围的值
		 */
	    public static int codeString(String crcStr) {   
	        int crc = 0xFFFF;          // initial value  
	        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)   
	  
	        // byte[] testBytes = "123456789".getBytes("ASCII");  
	  
	        byte[] bytes = crcStr.getBytes();  
	  
	        for (byte b : bytes) {  
	            for (int i = 0; i < 8; i++) {  
	                boolean bit = ((b   >> (7-i) & 1) == 1);  
	                boolean c15 = ((crc >> 15    & 1) == 1);  
	                crc <<= 1;  
	                if (c15 ^ bit) crc ^= polynomial;  
	             }  
	        }  
	  
	        crc &= 0xffff;  
	        System.out.println("CRC16-CCITT-String = " + Integer.toHexString(crc));  
	        bytes=null;
	        //System.gc();
	        return crc;
	    }  

		/**
		 * 字节码转CRC校验码
		 * @param crcStr 字节码
		 * @return 两个字节的长度范围的值
		 */
	    public static int codeBytes(byte[] bytes) {   
	        int crc = 0xFFFF;          // initial value  
	        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)   
	  
	        // byte[] testBytes = "123456789".getBytes("ASCII");  
	        for (byte b : bytes) {  
	            for (int i = 0; i < 8; i++) {  
	                boolean bit = ((b   >> (7-i) & 1) == 1);  
	                boolean c15 = ((crc >> 15    & 1) == 1);  
	                crc <<= 1;  
	                if (c15 ^ bit) crc ^= polynomial;  
	             }  
	        }  
	  
	        crc &= 0xffff;  
	        System.out.println("CRC16-CCITT-Bytes = " + Integer.toHexString(crc));  
	        return crc;
	    } 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		code("123456789");

	}

}
