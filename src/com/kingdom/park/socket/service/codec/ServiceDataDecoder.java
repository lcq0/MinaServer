package com.kingdom.park.socket.service.codec;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import com.kingdom.park.socket.service.util.IConstants;

/**
 * 解码器
 * @author YaoZhq
 */
public class ServiceDataDecoder implements MessageDecoder, IConstants {
	
	public static final Logger logger = Logger.getLogger(ServiceDataDecoder.class);
	
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in1) {
		MessageDecoderResult result = MessageDecoderResult.NOT_OK;
		String txt = in1.getHexDump(in1.remaining());
		logger.info("===[解码器]=完整的原始数据Hex码-全包：："+txt);
		byte[] array = new byte[in1.remaining()];
		System.arraycopy(in1.array(), 0, array, 0, array.length);
		
		if(!DataBagUtil.is2424(array)&&!DataBagUtil.is0D0A(array) && array.length <47) return MessageDecoderResult.NEED_DATA ;
		
		int numbagpi = DataBagUtil.freeLengBag(array);
		if(numbagpi == -1){
			DataBagUtil.prinltnBytes2HexString("free read:",array);
			return MessageDecoderResult.NEED_DATA ;
		}
		//分包
		int isNum = 0 ;
		for(byte sing : array ){
			if(sing == d_0x24) isNum ++;
		}
		
		if(isNum > 2){
			List<byte[]> byteList = DataBagUtil.subpackage(array);
			for(byte[] singbyte :byteList){
				 result =   decodableSing(singbyte);
			}
		 }else{
			 byte[] arraySing = new byte[array.length];
			 System.arraycopy(array, 0, arraySing, 0, arraySing.length);
			 result =   decodableSing(arraySing);
			 arraySing=null;
		 }
		
//		if (in1 != null) in1.free();
		array=null;
//		System.gc();
		return result;

	 }

	private MessageDecoderResult decodableSing(byte[] array) {
		DataBagUtil.prinltnBytes2HexString("subbag..test:  ",array);
		String txt = DataBagUtil.Bytes2HexString(array);
		
		array = DataBagUtil.inEscapesBag(array);
		
		 IoBuffer in = IoBuffer.wrap(array);
		try {
			ServiceDataBag bag = new ServiceDataBag();

			//包头
			Short head = in.getShort();
			if (head != s_0x2424) {
				logger.info("===[解码器]=包头不符合：："+txt);
				return MessageDecoderResult.NOT_OK;
			}
			bag.setBagHead(String.valueOf(head));
			//----------消息体属性------------
			//协议指令码
			byte code=in.get();
			
			//包长
			int bagLength=in.getInt();
			
			//授权码
			byte[] licenseByte=new byte[32];
			in.get(licenseByte);
			
			//文本长度
			int texLength=in.getInt();
			
			//文本内容
			if(texLength>0){
				int remainLen=in.remaining();
				if(texLength>(remainLen-3)){
					logger.info("===[解码器]=文本长度与文本内容不符合：："+txt);
					return MessageDecoderResult.NOT_OK;
				}
				byte[] textContent=new byte[texLength];
				in.get(textContent);
				bag.setTextContentBytes(textContent);
				bag.setTextContentBytesHex(Coding.byte2HexStr(textContent));
				String textContentStr=new String(textContent,"UTF-8");
				bag.setTextContent(textContentStr);
				textContentStr=null;
				textContent=null;
			}
			
			//校验码,从消息头开始，与后一个字节异或，只到校验码前一位止
			short crc=in.getShort();
			byte[] codea = new byte[texLength+43];
			System.arraycopy(array, 0, codea, 0, codea.length);
			int validateCode=CRC16CCITT.codeBytes(codea);
			if(crc!=validateCode&&validateCode!=Coding.getUnsignedShort(crc)){
				logger.info("subbag..校验码不符合:"+validateCode+"|"+crc+" ->"+txt);
				return MessageDecoderResult.NOT_OK;
			}
			bag.setCrc(Short.toString(crc));
			
			byte[] end=new byte[2];
			in.get(end);
			bag.setBagEnd(Coding.byte2HexStr(end));
			
			
			licenseByte=null;
			codea=null;
			end=null;
		} catch (Exception e) {
			logger.error("===[解码器]=解析包异常：："+e.getMessage(),e);
			e.printStackTrace();
			return MessageDecoderResult.NOT_OK;
		}
//		if(in != null ) in.free();
		
//		System.gc();
		return MessageDecoderResult.OK;
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in1, ProtocolDecoderOutput out) throws Exception {
		MessageDecoderResult result = MessageDecoderResult.NOT_OK;
		int freeint = 0;
		
		byte[] array = new byte[in1.remaining()];
		System.arraycopy(in1.array(), 0, array, 0, array.length);
		
		DataBagUtil.prinltnBytes2HexString("decode: free: ",array);
		
		//分包
		int isNum = 0 ;
		for(byte sing : array ){
			if(sing == d_0x24) isNum ++;
		}
		
		if(isNum > 2){
			List<byte[]> byteList = DataBagUtil.subpackage(array);
			for(byte[] singbyte :byteList){
				 freeint += singbyte.length;
				 result =  decodeSing(singbyte,out);
			}
		 }else{
			 byte[] arraySing = new byte[array.length];
			 System.arraycopy(array, 0, arraySing, 0, arraySing.length);
			 freeint += arraySing.length;
			 result =  decodeSing(arraySing,out);
			 arraySing=null;
		 }
		
		//释放掉数据包
		int numbagpi = DataBagUtil.freeLengBag(array);
		if(numbagpi != -1){
			if(in1.limit() > 0) in1.position(0);
			byte[] free = new byte[numbagpi];
			in1.get(free);
			if(freeint != free.length){
				logger.info("===[解码器]=释放数据包：：freeint:"+freeint +" free.length:"+free.length +"\n"+DataBagUtil.Bytes2HexString(free)+"\n"+DataBagUtil.Bytes2HexString(array));
			}else{
				DataBagUtil.prinltnBytes2HexString("numbagpi: "+numbagpi+" free: ",free,array);
			}
			if (in1 != null) {
				in1.free();
			}
			free=null;
			array=null;
//			System.gc();
			return result;
		}else{
			if (in1 != null) {
				in1.free();
			}
			return MessageDecoderResult.NEED_DATA ;
		}
	}
		
		
	private MessageDecoderResult decodeSing( byte[] array , ProtocolDecoderOutput out){
		String txt = DataBagUtil.Bytes2HexString(array);
		array = DataBagUtil.inEscapesBag(array);
		IoBuffer in = IoBuffer.wrap(array);
		try {
			ServiceDataBag bag = new ServiceDataBag();
			
			//包头
			Short head = in.getShort();
			if (head != s_0x2424) {
				return MessageDecoderResult.NOT_OK;
			}
			bag.setBagHead(String.valueOf(head));
			//----------消息体属性------------
			//协议指令码
			byte code=in.get();
			bag.setBagCode(Coding.toHexStringSingleByte(code));
			
			//包长
			int bagLength=in.getInt();
			bag.setBagLength(bagLength);
			
			//授权码
			byte[] licenseByte=new byte[32];
			in.get(licenseByte);
			String license=new String(licenseByte,"utf-8");
			bag.setLicenseKey(license);
			
			
			//文本长度
			int texLength=in.getInt();
			bag.setTextLength(texLength);
			
			//文本内容
			if(texLength>0){
				byte[] textContent=new byte[texLength];
				in.get(textContent);
				bag.setTextContentBytes(textContent);
				bag.setTextContentBytesHex(Coding.byte2HexStr(textContent));
				String textContentStr=new String(textContent,"UTF-8");
				bag.setTextContent(textContentStr);
				
				textContentStr=null;
				textContent=null;
			}
			
			//校验码,从消息头开始，与后一个字节异或，只到校验码前一位止
			short crc=in.getShort();
			byte[] codea = new byte[texLength+43];
			System.arraycopy(array, 0, codea, 0, codea.length);
			int validateCode=CRC16CCITT.codeBytes(codea);
			if(crc!=validateCode&&validateCode!=Coding.getUnsignedShort(crc)){
				logger.info("subbag..校验码不符合:"+validateCode+"|"+crc+" ->"+txt);
				return MessageDecoderResult.NOT_OK;
			}
			bag.setCrc(Short.toString(crc));
			
			byte[] end=new byte[2];
			in.get(end);
			bag.setBagEnd(Coding.byte2HexStr(end));
			
			//重要，不能删除
			out.write(bag);
			
			license=null;
			licenseByte=null;
			codea=null;
			end=null;
		} catch (Exception e) {
			return MessageDecoderResult.NOT_OK;
		} 
//		 if (in != null) 	in.free();
//		System.gc();
		return MessageDecoderResult.OK;
	}
	
	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {

	}
	
	
}
