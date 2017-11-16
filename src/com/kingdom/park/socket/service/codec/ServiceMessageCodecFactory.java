package com.kingdom.park.socket.service.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.kingdom.park.socket.service.codec.ServiceDataDecoder;

/**
 * 解码器工厂类
 * @author YaoZhq
 */
public class ServiceMessageCodecFactory extends DemuxingProtocolCodecFactory {
	public ServiceMessageCodecFactory(){
		super.addMessageDecoder(ServiceDataDecoder.class);
	}
}
