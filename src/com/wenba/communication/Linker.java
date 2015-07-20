package com.wenba.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
/**
 * 
 * @author NB111
 *@version 0.0.1
 */
public class Linker extends Thread{

	private InetAddress serverAdd;
	private InetAddress group;
	//	private String broadCastAdd;
	private String username;
	private int dstport;

	private int srcport;
	private int linktime = 0;

	/**
	 * 
	 * @param srcport 
	 * @param dstport
	 * @param username
	 */
	public Linker(int srcport, int dstport, String username){
		try {
			this.dstport = dstport;
			this.srcport = srcport;
			this.username = username;

			group = InetAddress.getByName("255.255.255.255");

			//			broadCastAdd = this.getLocalBroadCastAdd();
			//			if(broadCastAdd != null){
			//				this.start();	
			//			}else {
			//				NetInterfaceNotFound();
			//			}
		} catch (UnknownHostException e) {
			MyOut.println(e.toString());
		} 
		start();
	}

	@Override
	public void run(){
		while (true) {
			try {
				DatagramSocket socketSender = new DatagramSocket();
				byte[] dataRequest = new String("001"+username).getBytes();
				DatagramPacket request = new DatagramPacket(dataRequest, dataRequest.length, group, dstport);
				socketSender.send(request);
				socketSender.close();
				MyOut.println("发送成功:"+new String(dataRequest));
			} catch (IOException e) {
			}
			/*
			 * 接收服务器IP，超时重新发送请求.
			 */
			DatagramSocket socketReciever = null ;
			try {
				socketReciever = new DatagramSocket(srcport); 
				byte[] dataResponce = new byte[1024];
				DatagramPacket reply = new DatagramPacket(dataResponce, dataResponce.length);
				socketReciever.setSoTimeout(500);
				socketReciever.receive(reply);
				String massage = byteToString(reply.getData());
				System.out.println(massage);
				serverAdd = reply.getAddress();
				linkSuccessed();
				MyOut.println("找到服务器，IP："+serverAdd.getHostAddress());
				socketReciever.close();
				Thread.sleep(3600000);

			} catch (IOException | InterruptedException e) {
				MyOut.println(e.toString()+"服务器查找超时，重试中...");
				linktime++;
				if(linktime == 20){
					socketReciever.close();
					linkTimeOut();
					break;
				}
				socketReciever.close();
			}

		}
	}

	public static String byteToString(byte[] bs)
	{
		String result = new String(bs);
		return result.trim();
	}

	public boolean isReply(String s){
		return s.equals("001");

	}

	public int getDstport() {
		return dstport;
	}

	public void setDstport(int dstport) {
		this.dstport = dstport;
	}

	public int getSrcport() {
		return srcport;
	}

	public void setSrcport(int srcport) {
		this.srcport = srcport;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	protected void linkSuccessed() {

	}

	protected void linkTimeOut() {

	}

	protected void NetInterfaceNotFound() {

	}

	public InetAddress getServerAdd() {
		return serverAdd;
	}

	public String getLocalBroadCastAdd(){
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface netInterface;
			netInterface = NetworkInterface.getByInetAddress(address);
			if (!netInterface.isLoopback() && netInterface.isUp()) {
				List<InterfaceAddress> interfaceAddresses = netInterface.getInterfaceAddresses();
				for (InterfaceAddress interfaceAddress : interfaceAddresses) {
					if (interfaceAddress.getBroadcast() != null) {
						new MyOut();
						MyOut.println(interfaceAddress.getBroadcast().getHostAddress());// 输出广播地址
						return interfaceAddress.getBroadcast().getHostAddress();
					}
				}
			}
		} catch (SocketException | UnknownHostException e) {
			return null;
		}
		return null;
	}

	public void setUserName(String userName)
	{
		this.username = userName;
	}

}
