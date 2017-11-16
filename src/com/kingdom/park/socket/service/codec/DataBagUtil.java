package com.kingdom.park.socket.service.codec;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.kingdom.park.socket.service.util.IConstants;

/**
 * 包处理工具类
 * @author YaoZhq
 */
public class DataBagUtil implements IConstants {

	public static final Logger logger = Logger.getLogger(DataBagUtil.class);



	/**
	 * 求得要发出的数据包的校验码
	 * @param data
	 * @return
	 */
	public static int getOutValidateCode(byte[] data) {
		int code = data[0];
		for (int i = 1; i < data.length; i ++) {
			code = code ^ data[i];
		}
		return code;
	}

	/**
	 *
	 * 求得收到的数据包中的校验码
	 * @param data
	 * @return
	 */
	public static int getInValidateCode(byte[] data) {
		int code = data[1];
		for (int i = 2; i < data.length - 2; i ++) {
			//System.out.println("i:"+i +":"+data[i]);
			code = code ^ data[i];
		}
		//boolean yes = code == data[data.length - 2];
		////System.out.println("校验码是否相符：" + yes);

		return code;
	}


	/**
	 * CRC校验生成校验码
	 * 从消息头开始，与后一个字节异或，只到校验码前一位止
	 * @param data
	 * @return
	 */
	public static byte getCRCCodeByte(byte[] data) {
		byte code = data[0];
		for (int i = 2; i < data.length - 3; i ++) {
			code = (byte) (code ^ data[i]);
		}
		return code;
	}

//	/**
//	 * CRC校验生成校验码(未检验是否有效)
//	 * 从消息头开始，与后一个字节异或，只到校验码前一位止
//	 * @param data
//	 * @return
//	 */
//	public static short getCRCCodeByte(byte[] data) {
//		short tmp = 0;
//		if(data.length<2){
//			return 0;
//		}
//	    short all = (short) ((((data[0] << 8) & 0xFF00) | (data[1] & 0xFF)) & 0xFFFF);
//	    if(data.length==2){
//	    	return all;
//	    }
//	    for (int i=2; i<data.length; i+=1) {
//	        tmp = (short)(data[i]);
//	        all ^= tmp;
//	    }
//		return all;
//	}



//	/**
//	 * 对要发出的数据包进行转义处理
//	 * @param data
//	 * @return
//	 */
//	public static byte[] outEscapesBag(byte[] data) {
//		byte[] array = new byte[data.length];
//		array[0] = data[0];
//		int idx = 1;
//		for (int i = 1; i < data.length - 1; i ++) {
//			byte b = data[i];
//			if (b == d_0x7e) {
//				byte[] temp = new byte[array.length + 1];
//				System.arraycopy(array, 0, temp, 0, idx);
//				temp[idx] = d_0x7d;
//				idx ++;
//				temp[idx] = d_0x02;
//				idx ++;
//				array = temp;
//			} else if (b == d_0x7d) {
//				byte[] temp = new byte[array.length + 1];
//				System.arraycopy(array, 0, temp, 0, idx);
//				temp[idx] = d_0x7d;
//				idx ++;
//				temp[idx] = d_0x01;
//				idx ++;
//				array = temp;
//			} else {
//				array[idx] = b;
//				idx ++;
//			}
//		}
//		array[array.length - 1] = d_0x7e;
//		return array;
//	}
//
	/**
	 * 对收到的数据包进行转义处理
	 * @param data
	 * @return
	 */
	public static byte[] inEscapesBag(byte[] data) {
		byte[] array = new byte[data.length];
//		//要转义的字节数
//		int decCount = 0;
//		int idx = 1;
//		for (int i = 1; i < data.length - 1; i ++) {
//			byte b = data[i];
//			//把0x7d 0x02转义成0x7e
//			if (b == d_0x7d && i + 1 < data.length - 1 && data[i + 1] == d_0x02) {
//				//填充0x7e到新数组中
//				Arrays.fill(array, idx, idx + 1, d_0x7e);
//				//忽略下一个字节
//				i ++;
//				//转义数加1
//				decCount ++;
//			//把0x7d 0x01转义成0x7d
//			} else if (b == d_0x7d && i + 1 < data.length - 1 && data[i + 1] == d_0x01) {
//				//填充0x7d到新数组中
//				Arrays.fill(array, idx, idx + 1, d_0x7d);
//				//忽略下一个字节
//				i ++;
//				//转义数加1
//				decCount ++;
//			} else {
//				//不需要转义
//				Arrays.fill(array, idx, idx + 1, b);
//			}
//			idx ++;
//		}
//		//有转义的字节
//		if (decCount > 0) {
//			byte[] newArray = new byte[array.length - decCount];
//			//复制新的字节数组
//			System.arraycopy(array, 0, newArray, 0, newArray.length);
//			//加入包头包尾
//			Arrays.fill(newArray, 0, 1, d_0x7e);
//			Arrays.fill(newArray, newArray.length - 1, newArray.length, d_0x7e);
//			return newArray;
//			////System.out.println(newArray);
//		}
		return data;
	}





	//分包工具
	public static List<byte[]> subpackage(byte[] array ){
		int pi = -1;
		int num2424 = 0;
		int baglen = nextBaglen(array, 39);//第一个单包长度

		List<byte[]> byteList = new ArrayList<byte[]>();
		for(int start = 0;start < array.length-1;start++){
			//判断是否为两个字节是否为包头标识符
			if(is2424head(array[start],array[start+1] )) {
				if(num2424 == 0 ) {
					num2424 = 1;
					pi = start -1;
				}else{
					if((start - pi) == 2){//防止包体有2424（包头）
						pi = start -2;
						continue;
					}

				}
			}
			//当存在一个包头的时候
			if(num2424>0){
				if(is0D0Aend(array[start],array[start+1],start,baglen)){
					if (array.length>baglen+40) {
						baglen = baglen+nextBaglen(array, start+41);
					}
					//pi - > start
					byte[] arrayTem = new byte[start+1 - pi];
					System.arraycopy(array, pi+1, arrayTem, 0, start - pi+1);//复制
					if(start < array.length-1){
						if(0x0D==array[start]&&0x0A==array[start+1]) {
							byteList.add(arrayTem);
						}else{
							pi = start-1;
						}
					}else{
						byteList.add(arrayTem);
						pi = start;
					}
					num2424 = 0;
				}
			}
		}
		return byteList;
	}

	//处理释放包的长度
	public static int freeLengBag(byte[] array ){
		int pi = -1;//释放长度
		int num2424 = 0;
		int baglen = nextBaglen(array, 39);//第一个单包长度
		for(int start = 0;start < array.length-1;start++){
			//判断是否为两个字节是否为包头标识符
			if(is2424head(array[start],array[start+1] )) {
				if(num2424 == 0 ) {
					num2424 = 1;
					pi = start -1;
				}else{
					if((start - pi) == 2){
						pi = start -2;
						continue;
					}
				}
			}
			//当存在一个包头的时候
			if(num2424>0){
				if(is0D0Aend(array[start],array[start+1],start,baglen)){
					if (array.length>baglen+40) {
						baglen = baglen+nextBaglen(array, start+41);
					}
					if(start+1 < array.length-1){
						pi=start+2;    //此处pi为数组的游标
					}else{
						pi = start+2;  //此处pi为释放的长度(最后一个字节的游标+1)
						return pi;
					}
					num2424 = 0;
				}
			}
		}
		return -1;

	}


	//处理释放包的长度
	public static int freeLengBagAction(byte[] array){

		int pi = -1;//释放长度
		int num7e = 0;
		String ret = "";

        for (int i = 0; i < array.length; i++) {
            String hex = Integer.toHexString(array[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }

        int index = ret.indexOf("7E");
        int last = ret.lastIndexOf("7E");
        if(index == last){
        	return -1;
        }else{
        	String before = ret.substring(last -2,last);
        	if(before.equals("7E")) {
        		pi = (last)/2;
        	}else{
        		pi = (last+2)/2;
        	}
        	logger.info(" pi :" +pi+" ret:"+ret +" last:"+last +" before:"+before);
        	return pi;
        }

/*		if((pi-1 > 0) && (array[pi-1] == array[pi-2])) pi-=2;

		if(num7e >= 2){
			return pi;
		}else{
			return -1;
		}*/

	}

	//打印byte信息
	public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += " "+hex.toUpperCase();
        }
        return ret;
    }

	public static String prinltnBytes2HexString(byte[] b){
		String str = Bytes2HexString(b);
		int indexOf = str.indexOf("7E");
		int lastIndexOf = str.lastIndexOf("7E");
		if(str.length() >4){
			if((indexOf == 1) && (lastIndexOf == str.length()-2)) return " OK ";
		}
		return str;

	}


	public static void prinltnBytes2HexString(String head,byte[] b){
		String str = Bytes2HexString(b);
		int indexOf = str.indexOf("7E");
		int lastIndexOf = str.lastIndexOf("7E");
		if(str.length() >4){
			if((indexOf != 1) || (lastIndexOf != str.length()-2) || (indexOf == lastIndexOf) ) {
				logger.info(head+" : "+str);
			}
		}
	}

	public static void prinltnBytes2HexString(String head,byte[] b,byte[] array){
		String str = Bytes2HexString(b);
		int indexOf = str.indexOf("24 24");
		int lastIndexOf = str.lastIndexOf("0D 0A");
		if(str.length() >10){
			if((indexOf != 1) || (lastIndexOf != str.length()-2)) {
				logger.info(head+" : "+str+" >>: "+ Bytes2HexString(array) );
			}
		}
	}


	/**
	 * 判断数组中是否存在包头标识0x2424
	 * @param b
	 * @return
	 */
	public static boolean is2424(byte[] b){

		int isNum = 0 ;
		for(byte sing : b ){
			if(sing == d_0x24) isNum ++;
		}

		if(isNum <2 || (isNum % 2) != 0) return false;

		String str = Bytes2HexString(b);
		int indexOf = str.indexOf("24");
		int lastIndexOf = str.lastIndexOf("24");
		if(str.length() >4){
			if((indexOf != 1) || (lastIndexOf != str.length()-2) || (indexOf == lastIndexOf)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是2424开头
	 * @param b1 第一个字节
	 * @param b2 第二个字节
	 * @return
	 */
	public static boolean is2424head(byte b1,byte b2){
		if(b1==0x24&&b2==0x24){
			return true;
		}
		return false;
	}

	/**
	 * 判断数组中是否存在包头标识0x0D0A
	 * @param b
	 * @return
	 */
	public static boolean is0D0A(byte[] b){

		int isNum = 0 ;
		for(byte sing : b ){
			if(sing == d_0x0d) isNum ++;
			if(sing == d_0x0a) isNum ++;
		}

		if(isNum <2 || (isNum % 2) != 0) return false;

		String str = Bytes2HexString(b);
		int indexOf = str.indexOf("0D");
		int lastIndexOf = str.lastIndexOf("0A");
		if(str.length() >4){
			if((indexOf != 1) || (lastIndexOf != str.length()-2) || (indexOf == lastIndexOf)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是0x0D0A结尾
	 * @param b1 第一个字节
	 * @param b2 第二个字节
	 * @param bagindex 第一个字节下表
	 * @param baglen 包长度
	 * @return
	 */
	public static boolean is0D0Aend(byte b1,byte b2,int bagindex,int baglen){
		if(b1==0x0d&&b2==0x0a&&(bagindex+2)==baglen){
			return true;
		}
		return false;
	}
//	public static boolean is0D0Aend(byte b1,byte b2){
//		if(b1==0x0d&&b2==0x0a){
//			return true;
//		}
//		return false;
//	}
	public static int nextBaglen(byte[] array,int start) {
		int b1 = (array[start])<0?Coding.getUnsignedByte(array[start]):array[start];
		int b2 = (array[start+1])<0?Coding.getUnsignedByte(array[start+1]):array[start+1];
		int b3 = (array[start+2])<0?Coding.getUnsignedByte(array[start+2]):array[start+2];
		int b4 = (array[start+3])<0?Coding.getUnsignedByte(array[start+3]):array[start+3];
		int textlen=Integer.valueOf(Integer.toHexString(b1)+"000000", 16)+Integer.valueOf(Integer.toHexString(b2)+"0000", 16)+
				Integer.valueOf(Integer.toHexString(b3)+"00", 16)+Integer.valueOf(Integer.toHexString(b4), 16);
		int baglen = 2+1+4+32+4+textlen+2+2;//第一个单包长度
		return baglen;
	}


	public static void  main(String[] args){
//		String hex="24 24 24 00 00 00 00 38 34 34 61 65 61 30 62 37 32 65 32 31 32 61 31 37 30 31 38 61 31 37 33 35 36 30 65 61 34 39 62 00 00 03 4E 7B 22 72 65 73 75 6C 74 22 3A 22 59 22 2C 22 64 61 74 61 22 3A 5B 7B 22 69 6E 73 65 72 69 61 6C 6E 6F 22 3A 22 34 34 30 33 30 35 30 30 30 30 30 31 30 30 32 30 39 35 30 30 30 38 35 38 22 2C 22 66 6C 6F 77 5F 69 64 22 3A 22 36 33 37 37 36 38 22 7D 5D 2C 22 65 72 72 6F 72 5F 63 6F 64 65 22 3A 22 38 30 30 30 30 30 30 30 22 2C 22 65 72 72 6F 72 5F 6D 73 67 22 3A 22 00 00 00 00 00 00 22 7D 24 24 0D 0A";
		String hex="24 24 24 00 00 00 00 38 34 34 61 65 61 30 62 37 32 65 32 31 32 61 31 37 30 31 38 61 31 37 33 35 36 30 65 61 34 39 62 00 00 00 10 7B 22 72 65 73 75 6C 74 22 3A 22 59 22 2C 22 64 0D 0A 0D 0A";
		String hex1=" 24 24 24 00 00 00 00 38 34 34 61 65 61 30 62 37 32 65 32 31 32 61 31 37 30 31 38 61 31 37 33 35 36 30 65 61 34 39 62 00 00 00 12 7B 22 72 65 73 75 6C 74 22 3A 22 59 22 2C 22 64 22 64 0D 01 0D 0A";
		byte[] bb=Coding.hexStr2Bytes((hex+hex1).replaceAll(" ", ""));
		System.out.println(bb.length);
		List<byte[]> byteList=subpackage(bb);
		System.out.println("==="+byteList.size()+"==="+byteList.get(0).length);

		String dString = Integer.toHexString(-17);

		int s = Integer.valueOf("ffffffd0", 16);

		String hString = "7B 22 70 61 72 6B 74 69 6D 65 22 3A 22 31 30 30 34 34 22 2C 22 69 6E 73 65 72 69 61 6C 6E 6F 22 3A 22 36 31 30 31 38 33 32 30 30 30 30 31 30 30 31 31 35 37 30 31 34 38 35 34 22 2C 22 6F 75 74 6C 6F 67 5F 62 61 74 63 68 6E 6F 22 3A 22 36 31 30 31 38 33 32 30 30 30 30 31 30 30 31 31 38 37 22 2C 22 70 61 79 73 74 61 74 65 22 3A 22 32 22 2C 22 6F 75 74 70 68 6F 74 6F 22 3A 22 68 74 74 70 3A 2F 2F 6B 69 6E 67 64 6F 6D 70 61 72 6B 2E 6F 73 73 2D 63 6E 2D 73 68 65 6E 7A 68 65 6E 2E 61 6C 69 79 75 6E 63 73 2E 63 6F 6D 2F 36 31 30 31 38 33 32 30 30 30 30 31 2F 70 61 63 6B 69 6D 61 67 65 2F 32 30 31 36 2F 31 32 2F 31 32 2F 36 31 30 31 38 33 32 30 30 30 30 31 2D 41 32 41 47 36 36 2D 32 30 31 36 2D 31 32 2D 31 32 2D 30 35 2D 30 36 2D 33 30 2D 30 37 30 2D 62 2E 6A 70 67 22 2C 22 63 61 72 5F 69 64 22 3A 22 E9 99 95 41 32 41 47 36 36 22 2C 22 6F 75 74 6F 70 65 72 74 69 6F 6E 74 79 70 65 6E 61 6D 65 22 3A 22 30 30 22 2C 22 69 6E 70 61 72 6B 74 72 61 63 65 22 3A 22 36 31 30 31 38 33 32 30 30 30 30 31 30 30 31 31 35 37 22 2C 22 70 61 79 64 61 74 65 74 69 6D 65 22 3A 22 32 30 31 36 31 32 31 32 31 37 30 36 33 36 22 2C 22 63 61 72 74 79 70 65 22 3A 22 31 22 2C 22 63 68 61 72 67 65 6D 6F 6E 65 79 22 3A 22 30 2E 30 22 2C 22 70 61 79 6D 6F 6E 65 79 22 3A 22 33 34 33 2E 30 22 2C 22 76 65 72 73 69 6F 6E 22 3A 22 31 2E 34 22 2C 22 6F 75 74 6F 70 65 72 6E 6F 22 3A 22 30 30 38 22 2C 22 72 65 67 69 6F 6E 5F 63 6F 64 65 22 3A 22 36 31 30 31 38 33 32 30 30 30 30 31 30 31 22 2C 22 70 61 79 74 79 70 65 22 3A 22 38 22 2C 22 6F 75 74 73 6D 61 6C 6C 70 68 6F 74 6F 22 3A 22 68 74 74 70 3A 2F 2F 6B 69 6E 67 64 6F 6D 70 61 72 6B 2E 6F 73 73 2D 63 6E 2D 73 68 65 6E 7A 68 65 6E 2E 61 6C 69 79 75 6E 63 73 2E 63 6F 6D 2F 36 31 30 31 38 33 32 30 30 30 30 31 2F 70 61 63 6B 69 6D 61 67 65 2F 32 30 31 36 2F 31 32 2F 31 32 2F 36 31 30 31 38 33 32 30 30 30 30 31 2D 41 32 41 47 36 36 2D 32 30 31 36 2D 31 32 2D 31 32 2D 30 35 2D 30 36 2D 32 37 2D 31 39 33 2D 73 2E 6A 70 67 22 2C 22 6F 75 74 74 79 70 65 22 3A 22 31 22 2C 22 6F 75 74 61 63 63 65 70 74 65 64 22 3A 22 31 22 2C 22 6F 75 74 6F 70 65 72 74 69 6F 6E 74 69 6D 65 22 3A 22 32 30 31 36 31 32 31 32 31 37 30 36 33 36 22 2C 22 6C 69 63 65 6E 73 65 6B 65 79 22 3A 22 32 63 37 64 37 65 30 38 36 65 34 31 31 61 32 65 37 39 63 61 62 65 38 66 63 66 64 65 34 63 36 33 22 2C 22 6F 75 74 63 6F 6E 66 69 64 65 6E 63 65 22 3A 22 30 2E 38 34 37 22 2C 22 69 6E 74 69 6D 65 22 3A 22 32 30 31 36 31 32 30 35 31 37 34 32 33 36 22 2C 22 6F 75 74 74 69 6D 65 22 3A 22 32 30 31 36 31 32 31 32 31 37 30 36 32 37 22 7D";
		byte[] bb1=Coding.hexStr2Bytes(hString.replaceAll(" ", ""));
		System.out.println(bb1.length);

	}

}
