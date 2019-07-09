package com.example.sufimage;

import java.io.File;
import java.io.FileNotFoundException;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.util.DataStorage;
import com.example.util.HttpReques;
import com.example.util.MyAppLication;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView listView;
	private RequestQueue queues;
	private LinearLayout layout;
	private HttpReques http;
	private Handler handler;
	private TextView toast;
	private static SharedPreferences shar;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) this.findViewById(R.id.listView1);
		toast=(TextView) this.findViewById(R.id.textView1);
		layout = (LinearLayout) this.findViewById(R.id.LinearLayout1);
		queues = Volley.newRequestQueue(getApplicationContext());// 创建网络请求队
		shar = MyAppLication.getContext().getSharedPreferences("IMAGE",
				Context.MODE_PRIVATE);
		toast.setBackgroundColor(Color.GRAY);
		DataStorage da = new DataStorage(1);// 创建文件夹
		// 设置背景图片
		try {
			Bitmap b = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(
							Uri.fromFile(new File(
									"/storage/emulated/0/aJavaHelp/bug.jpg"))));
			layout.setBackground(new BitmapDrawable(b));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 更新界面
		init();

		// ListView列表点击事件
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						TextActivity.class);
				intent.putExtra("ListId", arg2);
				startActivity(intent);
			}
		});
		//更新UI
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 1:
					init();
					break;
				case 2:
						toast.setVisibility(View.VISIBLE);
					break;
				case 3:
					toast.setVisibility(View.GONE);
					break;
				}
			};
		};
	}

	// 更新Ui
	public void init() {
		String str = shar.getString("RequesTest", "");
		String[] listTitle = str.substring(str.lastIndexOf("*") + 1).split(",");// 获取ListViewTitle
		ArrayAdapter<String> arr = new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_expandable_list_item_1, listTitle);
		listView.setAdapter(arr);
	};

	// 更新资源
	private void upDate() {
		http = new HttpReques();
		http.getImageList(queues, listView,handler);
		http.getImageArr(queues);

	}

	// 更新菜单点击
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.action_update:
			upDate();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (queues != null) {
			queues.cancelAll("MyTag");
		}
	}

}
