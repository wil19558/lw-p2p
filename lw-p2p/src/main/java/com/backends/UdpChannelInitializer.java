package com.backends;

import com.backends.id.SocketIdTable;
import com.p2p.NettyServer;
import com.p2p.serializing.SerializingChannelHandler;
import com.p2p.serializing.SerializingTable;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpChannelInitializer extends ChannelInitializer<NioDatagramChannel>{
	
	private short clientId; 
	private SocketIdTable idTable;
	private SerializingTable serialTable;
	
	public UdpChannelInitializer(SocketIdTable idTable, SerializingTable serialTable, NettyServer server){
		this.idTable = idTable;
		this.clientId = server.getThisId().getClientId();
		this.serialTable = serialTable;
	}
	
	@Override
	protected void initChannel(NioDatagramChannel ch) throws Exception {
		ch.pipeline()
		//[I/O = DatagramPacket]							// 	Network layer		
		.addLast(new UdpPacketHandler(idTable))				// 	Direction:\/|/\, Creates and unpacks DatagramPacket, and appends useful info.
		//[/\ = MessageRequest] [\/ = RawMessage] 
		.addLast(new SerializingChannelHandler(serialTable))// 	Direction:\/|/\, Serializes and deserializes objects into the buffer stream.
		//[/\ = MessageRequest] [\/ = Objects] 
		.addLast(new MessageRequestIdAppender(clientId))	//  Direction:/\, Appends the local client id to the message request.
		//[/\ = MessageRequest] [\/ = Objects] 
		.addLast(new MessagePacker());						//  Direction:/\, Packs messages until a UdpFlushRequest is received.
		//[I/O = Objects] 									//  Application layer
	}
	
	

}
