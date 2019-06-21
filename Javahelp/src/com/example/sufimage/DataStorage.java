package com.example.sufimage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.android.volley.RequestQueue;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.Toast;

/**
 * 数据存储
 * @author Administrator
 *
 */
public class DataStorage extends Application{
	/**
	 * sharedPreferences是一个活动类，不能直接调用，需要传一个Context
	 * @param context
	 * @param name sharedPreferences名
	 * @return 
	 */ 
	String fileurl="/storage/emulated/0/aJavaHelp";
	public DataStorage(int i) {
		// TODO Auto-generated constructor stub
		createFolder();
	}
	
	 	public DataStorage() {
		super();
		// TODO Auto-generated constructor stub
	}

	//存图片
	   public void dataImg(Bitmap bitmap,String imgName,SharedPreferences shar){
		   SharedPreferences.Editor ed=shar.edit();
		   //创建文件，要保存png，这里后缀就是png，要保存jpg，后缀就用jpg
	        File file=new File(fileurl+"/"+imgName+".png");
	        try {
	            //文件输出流
	            FileOutputStream fileOutputStream=new FileOutputStream(file);
	            //压缩图片，如果要保存png，就用Bitmap.CompressFormat.PNG，要保存jpg就用Bitmap.CompressFormat.JPEG,质量是100%，表示不压缩
	            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
	            //写入，这里会卡顿，因为图片较大
	            fileOutputStream.flush();
	            //记得要关闭写入流
	            fileOutputStream.close();
	            //成功的提示，写入成功后，请在对应目录中找保存的图片            
	            ed.putString("Message", "正在下载，请勿关闭应用");            
	            ed.putString(imgName, fileurl+"/"+imgName+".png");
	            ed.commit();
	        } catch (FileNotFoundException e) {	            
	            ed.putString("Message", "数据更新失败");
	            e.printStackTrace();
	            ed.commit();
	            //失败的提示
	        } catch (IOException e) {	            
	            //失败的提示
	            ed.putString("Message", "数据更新失败");
	            e.printStackTrace();
	            ed.commit();
	        }
            ed.clear();
	    }
	   //取图片
	   public String getImageUrl(int imgNameId,SharedPreferences shar){
		  String url="";
		  String str=shar.getString("RequesTest","");		
		  String[] imgNameArr=str.substring(0,str.indexOf("*")).split(",");
		  url=shar.getString(imgNameArr[imgNameId], "");
		 return url;
	   }
	   //创建文件夹
	   private void createFolder() {
	        //新建一个File，传入文件夹目录
	        File file = new File("/storage/emulated/0/aJavaHelp");
	        //判断文件夹是否存在，如果不存在就创建，否则不创建
	        if (!file.exists()) {
	            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
	            file.mkdirs();
	        }
	    }
}
