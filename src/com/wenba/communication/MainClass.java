package com.wenba.communication;

import java.util.Scanner;

public class MainClass {
	public static void main(String arg[]) {
		 new Linker(65534,65535,"艹你大爷"){
			@Override
			protected void linkSuccessed() {
				MsgSender msgSender = new MsgSender(this, 60000){
					@Override
					protected void sendSuccessed() {
						new MyOut();
						MyOut.println("发送成功");
					}
					@Override
					protected void serverLost() {
						new MyOut();
						MyOut.println("主机已下线");
					}
				};
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);
				new MyOut();
				MyOut.println("开始输入：");
				while(scanner.hasNextLine())
				{
					new MyOut();
					MyOut.println("正在发送");
					msgSender.sendMSG(scanner.nextLine());
				}
			}
			@Override
			protected void linkTimeOut() {
				new MyOut();
				MyOut.println("连接超时");
			}
		};
	}
}
