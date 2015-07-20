package com.wenba.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Utils {
	/**
	 * 让frame居中显示
	 * @param frame
	 * @param width frame宽度
	 * @param height frame高度
	 */
	public static void setBounds(JFrame frame,int width ,int height)
	{
		Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();   
		frame.setBounds((int) (scrSize.getWidth()-width)/2, (int) (scrSize.getHeight()-height)/2  
				,width, height);
	}
}
