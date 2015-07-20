package com.wenba.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.wenba.utils.TextAnalyzer;
import com.wenba.utils.Utils;

public class ErrorReportWindow extends JFrame{
	
	private static final long serialVersionUID = -97707244778080612L;
	
	private String[] errors;
	private String[] orderNumbers;

	private String teacherName;
	private String currentOrderNumber;
	private String errorInfo;
	private String details;
	
	private SelectBar errorBar;
	private SelectBar orderNumberBar;
	private JLabel label;
	private JTextArea textArea;
	private JButton button;
	
	private OnSubmitListener onSubmitListener;

	public ErrorReportWindow(String teacherName)
	{
		super();
		this.teacherName = teacherName;
		
		initData();
		initWidget();
		
		errorBar.getcomboBox().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1)
					errorInfo = e.getItem().toString();
			}
		});
		
		orderNumberBar.getcomboBox().addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1)
					currentOrderNumber = e.getItem().toString();
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				details = textArea.getText().replace('!', (char)0);
				if(onSubmitListener != null)
					onSubmitListener.onSubmit(generateMessage());
			}
		});
		
		setTitle(teacherName+"老师，请提交错误");
		Utils.setBounds(this , 400, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void initData()
	{
		errors = TextAnalyzer.getErrors();
		Arrays.sort(errors);
		orderNumbers = new String[50];
		for (int i = 0; i < 50; i++) {
			orderNumbers[i] = "第"+(i+1)+"单"; ;
		}
		currentOrderNumber = orderNumbers[0];
		errorInfo = errors[0];
	}
	
	private void initWidget()
	{
		errorBar = new SelectBar("请选择错误类型", errors);
		orderNumberBar = new SelectBar("       请选择订单号", orderNumbers);
		label = new JLabel("描述:");
		textArea = new JTextArea("选填");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true); 
		button = new JButton("提    交");
		
		Box box = Box.createVerticalBox();
		box.add(errorBar);
		box.add(orderNumberBar);
		add(box,BorderLayout.NORTH);
		box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(20));
		box.add(label);
		box.add(textArea);
		add(box,BorderLayout.CENTER);
		add(button,BorderLayout.SOUTH);
	}
	
	private String generateMessage()
	{
		return teacherName+"!"+currentOrderNumber+"!"+errorInfo+"!"+details;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
		this.onSubmitListener = onSubmitListener;
	}
	
	public interface OnSubmitListener
	{
		public void onSubmit(String message);
	}

}
