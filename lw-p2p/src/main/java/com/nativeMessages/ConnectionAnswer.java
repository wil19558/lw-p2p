package com.nativeMessages;

import java.util.ArrayList;
import java.util.List;

import com.backends.id.SocketId;
import com.p2p.NetworkInformation;
import com.p2p.Peer;
import com.sun.org.apache.bcel.internal.generic.RETURN;

/**Message sent back by a peer to a client that is attempting connection.
 *	Contains the connection result and all of the peers if the connection
 *	was successful and it was requested.
 * 
 * 
 * @author William Laroche
 *
 */
public class ConnectionAnswer {
	public static enum Answer{
		SUCCESS, FAILURE;
	}
	
	private SocketId[] peerIds;
	private NetworkInformation info;
	private Answer answer;
	
	public ConnectionAnswer(Answer answer){
		this.answer = answer;
	}
	
	public void setPeerIds(SocketId ... ids){
		peerIds = ids;
	}
	
	public void setPeerIds(Iterable<SocketId> peers){
		ArrayList<SocketId> tempPeers = new ArrayList<SocketId>();;
		for(SocketId id : peers){
			tempPeers.add(id);
		}
		this.peerIds = new SocketId[tempPeers.size()]; 
		for(int i = 0; i < tempPeers.size(); i++){
			peerIds[i] = tempPeers.get(i);
		}
	}
	
	public void setPeers(List<Peer> peers){
		this.peerIds = new SocketId[peers.size()]; 
		for(int i = 0; i < peers.size(); i++){
			this.peerIds[i] = peers.get(i).getSocketId();
		}
	}
	
	public void setNetworkInformation(NetworkInformation info){
		this.info = info;
	}
	
	public NetworkInformation getNetworkInformation(){
		return info;
	}
	
	public Answer getAnswer(){
		return answer;
	}
	
	public SocketId[] getPeerIds(){
		return peerIds;
	}
	
}
