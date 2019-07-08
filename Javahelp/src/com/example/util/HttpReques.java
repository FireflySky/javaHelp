package com.example.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sufimage.DataStorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 获取网络请求
 * 
 * @author Administrator
 * 
 */
public class HttpReques {
	private static final String TAG = "MyTag";
	private SharedPreferences shar = MyAppLication.getContext()
			.getSharedPreferences("IMAGE", Context.MODE_PRIVATE);
	private BufferedWriter buf;
	private Handler handler=null;

	/**
	 * 获取图片
	 * 
	 * @return String
	 */
	public void getImageList(RequestQueue queues,final ListView view,final Handler handler) {
		this.handler=handler;
		final SharedPreferences.Editor editor = shar.edit();

		StringRequest request = new StringRequest(Method.POST,
				"http://225b8u4018.iask.in/JavaHelps/servlet/HttpReques",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						editor.putString("RequesTest", response);
						editor.commit();
						Message msg=new Message();
						msg.what=1;
						handler.sendMessage(msg);
						Log log=new Log("网络连接成功:"+response);
						log.start();
						runUi(view);
						editor.clear();
					}
				}, new Response.ErrorListener() {// 请求失败
					@Override
					public void onErrorResponse(VolleyError error) {
						Log log=new Log("网络请求失败");
						log.start();
						Toast.makeText(MyAppLication.getContext(), "网络连接中断",
								Toast.LENGTH_LONG).show();
					}
				});
		// 设置tag标签
		request.setTag(TAG);
		// 加入请求队列
		queues.add(request);

	}

	/**
	 * 获取图片
	 * 
	 * @return bitmap
	 */
	private void getImage(RequestQueue queues, final String imgName) {
		ImageRequest imageRequest = new ImageRequest(
				"http://225b8u4018.iask.in/JavaHelps/" + imgName + ".png",
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						DataStorage da = new DataStorage();
						da.dataImg(response, imgName, shar);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log log=new Log(imgName+"图片请求失败");
						log.start();
					}
				});
		queues.add(imageRequest);
	}

	//将图片保存	 
	public void getImageArr(RequestQueue queues) {
		String str = shar.getString("RequesTest", "");
		if (!str.equals("")) {
			String[] imgName = str.substring(0, str.indexOf("*")).split(",");
			for (int i = 0; i < imgName.length; i++) {

				this.getImage(queues, imgName[i]);
			}
		}
	}
	//更新UI
	private void runUi(ListView view){
		String str = shar.getString("RequesTest", "");
		String[] listTitle = str.substring(str.lastIndexOf("*") + 1).split(",");// 获取ListViewTitle
		ArrayAdapter<String> arr = new ArrayAdapter<String>(MyAppLication.getContext(),
				android.R.layout.simple_expandable_list_item_1, listTitle);
		view.setAdapter(arr);
	}
	
	//程序运行日志
	 private Date date=new Date();
	 private SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private class Log extends Thread{
		String msg=null;
		private Log(String msg){
			this.msg=msg;
		}
		@Override
		public void run() {
			String dat=sd.format(date);
			try {
				buf = new BufferedWriter(new FileWriter(
						"/storage/emulated/0/aJavaHelp/Log.txt", true));
				if (buf != null) {
					buf.write(dat+" \\"+msg);
					buf.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (buf != null)
						buf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
