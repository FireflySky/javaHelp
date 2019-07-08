package com.example.util;

import android.app.Application;
import android.content.Context;

/**
 * 定义全局的Context
 * @author Administrator
 *
 */
public class MyAppLication extends Application{
	private static Context context;
	@Override
	public void onCreate() {
		context=getApplicationContext();
	}
	/**
	 * 获取Context
	 * @return
	 */
	public static Context getContext(){
		return context;
	}
}
