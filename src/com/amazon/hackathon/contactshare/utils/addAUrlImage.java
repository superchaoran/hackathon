package com.amazon.hackathon.contactshare.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;


public class addAUrlImage implements Runnable {
	ImageView i;
	String url;
	public addAUrlImage(ImageView i,String url){
		this.i = i;
		this.url = url;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//iv = (ImageView) findViewById(R.id.imageView1);
		//iv.setImageResource(R.drawable.ic_launcher);
			try {
				  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
				  i.setImageBitmap(bitmap); 
				} catch (MalformedURLException e) {
				  e.printStackTrace();
				} catch (IOException e) {
				  e.printStackTrace();
				}
		}
		
	}
