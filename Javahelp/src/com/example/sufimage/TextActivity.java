package com.example.sufimage;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TextActivity extends Activity {

	BaseDragZoomImageView imageView;
	SharedPreferences shar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);
		
		
		imageView=(BaseDragZoomImageView) this.findViewById(R.id.imageview);
		shar=getApplication().getSharedPreferences("IMAGE",Context.MODE_PRIVATE);
		Intent intent=getIntent();
		int imgId=intent.getIntExtra("ListId", 0);
		DataStorage da=new DataStorage();
//		Toast.makeText(TextActivity.this,imgId+":"+da.getImageUrl(imgId, shar), Toast.LENGTH_LONG).show();
		imageView.setImageURI(Uri.fromFile(new File(da.getImageUrl(imgId, shar))));

		
		
	}
}
