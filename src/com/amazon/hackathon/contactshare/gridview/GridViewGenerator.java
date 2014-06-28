package com.amazon.hackathon.contactshare.gridview;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.amazon.hackathon.contactshare.utils.User;
import com.amazon.hackathon.contactshare.gridview.ImageAdapter;

public class GridViewGenerator{
	private ArrayList<User> userList;
	public GridViewGenerator(GridView gridview, final Context context){
		this.userList= new ArrayList<User>();
		gridview.setAdapter(new ImageAdapter(context,this.userList));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(context, position, Toast.LENGTH_SHORT).show();
	        }
	    });
	}
	public ArrayList<User> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}
	public void addUser(User user) {
		// TODO Auto-generated method stub
		this.userList.add(user);
	}
	public void removeUser(String username){
		for(int i=0;i<userList.size();i++){
			if(userList.get(i).getUsername().equals(username)){
				userList.remove(i);
				return;
			}
		}
	}
	
	

}
