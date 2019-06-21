package com.example.sufimage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private ListView listView;
	private RequestQueue queues;
	private LinearLayout layout;
	private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView=(ListView) this.findViewById(R.id.listView1);
        layout=(LinearLayout) this.findViewById(R.id.LinearLayout1);
        queues = Volley.newRequestQueue(getApplicationContext());//创建网络请求队   
        sharedPreferences=getApplication().getSharedPreferences("IMAGE",Context.MODE_PRIVATE);
        DataStorage da=new DataStorage();
        try {
			Bitmap b=BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(Uri.fromFile(new File("/storage/emulated/0/aJavaHelp/bug.jpg"))));
			layout.setBackground(new BitmapDrawable(b));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Init init=new Init();
        init.MainInit(listView, MainActivity.this);
        
        //ListView列表点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,TextActivity.class);
				intent.putExtra("ListId", arg2);
				startActivity(intent);
			}
		});
        
    }
    
    //更新菜单点击
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	int itemId=item.getItemId();
    	switch (itemId) {
		case R.id.action_update:
			UpDate update=new UpDate(queues, listView, sharedPreferences);
			Thre thread=new Thre();
			thread.start();
			Toast.makeText(MainActivity.this,sharedPreferences.getString("Message", ""),
					Toast.LENGTH_LONG).show();
			break;
		}
    	
    	return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	if(queues != null){
    		queues.cancelAll("MyTag");
    	}
    }
    
}
class Thre extends Thread{
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.run();
	}
}

