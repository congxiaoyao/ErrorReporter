package com.wenba.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MsgSender {
	private Linker linker;
	private int port;
	public MsgSender(Linker linker , int port) {
		this.linker = linker;
		this.port = port;
	}
	
	public void sendMSG(final String msg){
		Socket socket = null;
		try
		{
			socket = new Socket(linker.getServerAdd(),port);
			socket.setSoTimeout(1000);;
			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			outputStream.writeUTF(msg);
			outputStream.close();
			socket.close();
			sendSuccessed();

		} catch (UnknownHostException e)
		{
			new Linker(linker.getSrcport(), linker.getDstport(), linker.getUsername()){
				@Override
				protected void linkSuccessed() {
					linker = this;
					sendMSG(msg);
				}
				@Override
				protected void linkTimeOut() {
					serverLost();
				}
			};
			
			
		} catch (IOException e)
		{
			new MyOut();
			MyOut.println(e.toString());
			new Linker(linker.getSrcport(), linker.getDstport(), linker.getUsername()){
				@Override
				protected void linkSuccessed() {
					linker = this;
					sendMSG(msg);
				}
				@Override
				protected void linkTimeOut() {
					serverLost();
				}
			};
		}
	}
	protected void sendSuccessed() {
		// TODO Auto-generated method stub
		
	}

	protected void serverLost() {
		
	}
	
	
}
