package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestCopyFile {

	public static void main(String[] args) {
		
		String oldFile = "C:\\Users\\RYX\\Desktop\\old.txt";
		String newFile = "C:\\Users\\RYX\\Desktop\\new.txt";
		copyFile(oldFile, newFile);
		
	}
	
	public static void copyFile(String filePath_old  ,String filePath_new){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        
        try {
			inputStream = new FileInputStream(filePath_old);
			
			int size = inputStream.available();
			byte[] by = new byte[size];
			inputStream.read(by);
			
			outputStream = new FileOutputStream(filePath_new);
			
			outputStream.write(by);
			outputStream.flush();
		} catch (Exception e) {
			
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        
        
    }

}
