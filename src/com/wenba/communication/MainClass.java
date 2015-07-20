package com.wenba.communication;

import java.util.Scanner;

public class MainClass {
	public static void main(String arg[]) {
		 new Linker(65534,65535,"ܳ���ү"){
			@Override
			protected void linkSuccessed() {
				MsgSender msgSender = new MsgSender(this, 60000){
					@Override
					protected void sendSuccessed() {
						new MyOut();
						MyOut.println("���ͳɹ�");
					}
					@Override
					protected void serverLost() {
						new MyOut();
						MyOut.println("����������");
					}
				};
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(System.in);
				new MyOut();
				MyOut.println("��ʼ���룺");
				while(scanner.hasNextLine())
				{
					new MyOut();
					MyOut.println("���ڷ���");
					msgSender.sendMSG(scanner.nextLine());
				}
			}
			@Override
			protected void linkTimeOut() {
				new MyOut();
				MyOut.println("���ӳ�ʱ");
			}
		};
	}
}
