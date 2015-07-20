package com.wenba;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.wenba.communication.Linker;
import com.wenba.communication.MsgSender;
import com.wenba.ui.ErrorReportWindow;
import com.wenba.ui.ErrorReportWindow.OnSubmitListener;
import com.wenba.ui.LoginWindow;
import com.wenba.ui.LoginWindow.onLoginListener;

public class MainClass {
	public static void main(String[] args) {
		
		final LoginWindow loginWindow = new LoginWindow();
		loginWindow.setOnLoginListener(new onLoginListener() {
			@Override
			public void onLogin(final String teacherName) {
				new Linker(65534, 65535, teacherName)
				{
					@Override
					protected void linkSuccessed() {
						loginWindow.dispose();
						showErrorSubmitWindow(teacherName , this);
					}
					@Override
					protected void linkTimeOut() {
						loginWindow.dispose();
					}
				};
			}
		});
	}
	
	private static void showErrorSubmitWindow(String teacherName , final Linker linker) {
		final ErrorReportWindow errorWindow = new ErrorReportWindow(teacherName);
		errorWindow.setOnSubmitListener(new OnSubmitListener() {
			
			private MsgSender sender = new MsgSender(linker, 60000)
			{
				@Override
				protected void sendSuccessed() {
					JOptionPane.showMessageDialog(null,
							"�ύ�ɹ�����ע�⼰ʱ�ϴ���Ƶ", "�ύ�ɹ�", JOptionPane.PLAIN_MESSAGE);
					errorWindow.getButton().setText("��    ��");
				}
			};
			
			@Override
			public void onSubmit(final String message) {
				final JButton button = errorWindow.getButton();
				button.setText("�����ύ...");
				sender.sendMSG(message);
			}
		});
	}
}
