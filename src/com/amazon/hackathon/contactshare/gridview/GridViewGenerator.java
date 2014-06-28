package com.amazon.hackathon.contactshare.gridview;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.amazon.hackathon.contactshare.utils.User;
import com.amazon.hackathon.contactshare.gridview.ImageAdapter;
import com.amazon.hackathoncontactshare.R;

public class GridViewGenerator{
	private ArrayList<User> userList;
	public GridViewGenerator(GridView gridview, final Context context){
		this.userList= new ArrayList<User>();
		gridview.setAdapter(new ImageAdapter(R.layout.user_profile,context,this.userList));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            //Toast.makeText(context, position, Toast.LENGTH_SHORT).show();
	        	System.out.println(userList.get(position).getUsername()+" clicked!");
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
	public void removeUser(String userId){
		for(int i=0;i<this.userList.size();i++){
			if(this.userList.get(i).getUserId().equals(userId)){
				this.userList.remove(i);
				return;
			}
		}
	}
	public void updateUsers(ArrayList<User> userList){
		
		
		for(int i=0;i<userList.size();i++){
			boolean contains=false;
			for(int j=0;j<this.userList.size();j++){
				//if old contains, break;
				if(  this.userList.get(j).getUserId().equals(userList.get(i).getUserId()) ){
					contains=true; break;
				}
			}
			//if old not contains new, add
			if(!contains) this.userList.add(userList.get(i));
		}
		
		//if old contains something new not exist remove
		for(int i=0;i<this.userList.size();i++){
			boolean contains=false;
			for(int j=0;j<userList.size();j++){
				if(  this.userList.get(i).getUserId().equals(userList.get(j).getUserId()) ){
					contains=true; break;
				}
			if(!contains) this.userList.remove(userList.get(i).getUserId());
			}
		}
	
	}

}
