package com.wenba.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TextAnalyzer {
	
	public static String[] getTeacherNames()
	{
		String src = readTxtFile("infos//teacherNames.txt");
		String[] teacherNames = src.split(",");
		return teacherNames;
	}
	
	public static String[] getErrors()
	{
		String src = readTxtFile("infos//errors.txt");
		String[] errors = src.split(",");
		return errors;
	}
	
    private static String readTxtFile(String filePath){
    	String result = "";
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	result += lineTxt;
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错"+e.toString());
        }
        return result;
    }

}
