package com.example.sufimage;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
/**
 * 初始化界面
 * @author Administrator
 *
 */
public class Init {
	
	/**
	 * 初始化主界面
	 * @param listview
	 * @param context
	 */
	public void MainInit(ListView listview,Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("IMAGE", Context.MODE_PRIVATE);
		String str=sharedPreferences.getString("RequesTest", "");
		String[] listTitle=str.substring(str.lastIndexOf("*")+1).split(",");//获取ListViewTitle
		ArrayAdapter<String> arr=new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1,listTitle);
		listview.setAdapter(arr);
	}
}
