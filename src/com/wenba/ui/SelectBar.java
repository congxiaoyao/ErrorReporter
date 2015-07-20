package com.wenba.ui;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectBar extends JPanel{
	
	private static final long serialVersionUID = 2296655820031163785L;
	private JComboBox<String> comboBox;

	public SelectBar(String text , String[] items)
	{
		comboBox = getComboBox(items);
		JLabel label = new JLabel(text);
		add(label);
		add(comboBox);
	}
	
	public JComboBox<String> getcomboBox()
	{
		return comboBox;
	}
	
	/**
	 * 生成一个下拉框
	 * @param items
	 * @return
	 */
	private JComboBox<String> getComboBox(String[] items)
	{
		JComboBox<String> comboBox = new JComboBox<String>();
		for (String item : items) {
			comboBox.addItem(item);
		}
		comboBox.setPreferredSize(new Dimension(200,22));
		return comboBox;
	}

}
