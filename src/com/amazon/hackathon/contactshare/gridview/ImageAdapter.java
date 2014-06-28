package com.amazon.hackathon.contactshare.gridview;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.amazon.hackathon.contactshare.utils.User;
import com.amazon.hackathon.contactshare.utils.addAUrlImage;
import com.amazon.hackathoncontactshare.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.hackathon.contactshare.utils.ImageDownloaderTask;;

public class ImageAdapter extends BaseAdapter {
	  private Context mContext;
	  private ArrayList<User> userList;
	  int layoutResourceId;  
	    public ImageAdapter(int layoutResourceId,Context c, ArrayList<User> userList) {
	        mContext = c;
	        this.setUserList(userList);
	        this.layoutResourceId=layoutResourceId;
	    }

	    public int getCount() {
	        return userList.size();
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    
	    static class UserProfileHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
	    }
	    
	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	
	        View row = convertView;
	        UserProfileHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new UserProfileHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
	            
	            row.setTag(holder);
	        }
	        
	        else
	        {
	            holder = (UserProfileHolder)row.getTag();
	        }
	        
	        User user = userList.get(position);
	        holder.txtTitle.setText(user.getUsername());
	        new ImageDownloaderTask(holder.imgIcon).execute(this.userList.get(position).getImageUrl());
	        return row;
	        
	    	
	    	
	    	
	    	/*
	        ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }
	        new ImageDownloaderTask(imageView).execute(this.userList.get(position).getImageUrl());
	        return imageView;*/
	    }

	    public ArrayList<User> getUserList() {
			return userList;
		}

		public void setUserList(ArrayList<User> userList) {
			this.userList = userList;
		}
}