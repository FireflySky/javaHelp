package com.example.sufimage;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;

import com.android.volley.RequestQueue;

/**
 * 更新
 * @author Administrator
 *
 */
public class UpDate extends Thread{
	/**
	 * 更新数据并重新初始化
	 * @param queues 网络请求队列
	 * @param listview
	 * @param context
	 */
	public UpDate(RequestQueue queues,ListView listview,SharedPreferences shar) {
		// TODO Auto-generated constructor stub
		HttpReques http=new HttpReques();
		http.getImageList(queues,shar);
		this.start();
		http.getImageArr(queues, shar);
	}
	@Override
	public void run() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.run();
	}
}
