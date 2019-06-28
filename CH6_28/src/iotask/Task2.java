package iotask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 *  利用程序读取 test.txt 文件的内容, 并在控制台打印
 * @author Administrator
 *
 */
public class Task2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		read();
	}
	public static String read(){
		File f = new File("src/message/test.txt");
		BufferedReader read=null;
		StringBuffer buf=new StringBuffer();
		try {
			if(!f.exists()){
				System.out.println("文件不存在");
				return null;
			}
			read=new BufferedReader(new FileReader(f));
			String len=null;
			while ((len=read.readLine())!=null) {
				System.out.println(len);
				buf.append(len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(read!=null)
				read.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return buf.toString();
	}

}
