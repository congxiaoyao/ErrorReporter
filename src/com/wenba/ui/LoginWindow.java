package com.wenba.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.wenba.utils.TextAnalyzer;
import com.wenba.utils.Utils;

public class LoginWindow extends JFrame{

	private static final long serialVersionUID = 4960361751091298037L;
	private JComboBox<String> comboBox;
	private JButton button;
	private JTextArea textArea;
	private SelectBar selectBar;
	
	private String currentName;
	private onLoginListener onLoginListener;
	
	public LoginWindow()
	{
		super();
		
		String[] teacherNames = TextAnalyzer.getTeacherNames();
		Arrays.sort(teacherNames);
		
		if(teacherNames.length > 0)
			currentName  = teacherNames[0];
		
		selectBar = new SelectBar("教师姓名", teacherNames);
		comboBox = selectBar.getcomboBox();
		comboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1)
					currentName = e.getItem().toString();
			}
		});
		
		button = getButon("登    陆");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(onLoginListener!=null)
				{
					onLoginListener.onLogin(currentName);
				}
			}
		});
		
		textArea = new JTextArea("请选择自己的姓名并登陆");
		textArea.setEditable(false);
		
		add(selectBar, BorderLayout.NORTH);
		add(button,BorderLayout.SOUTH);
		add(new JScrollPane(textArea),BorderLayout.CENTER);
		
		
		Utils.setBounds(this , 400, 200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	
	/**
	 * 生成一个按钮
	 * @param name
	 * @return
	 */
	public JButton getButon(String name) {
		JButton button = new JButton();
		button.setText(name);
		button.setPreferredSize(new Dimension(100,32));
		return button;
	}
	
	/**
	 * 向文本区域中添加一条信息
	 * @param info
	 */
	public void addInfo(String info)
	{
		textArea.append("\n"+info);
	}
	
	public void setOnLoginListener(onLoginListener onLoginListener) {
		this.onLoginListener = onLoginListener;
	}
	
	public interface onLoginListener
	{
		public void onLogin(String teacherName);
	}
}

