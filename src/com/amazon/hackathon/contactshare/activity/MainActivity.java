package com.amazon.hackathon.contactshare.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.hackathon.contactshare.gridview.GridViewGenerator;
import com.amazon.hackathon.contactshare.login.LoginModel;
import com.amazon.hackathon.contactshare.personalarchiveactivity.CustomUI;
import com.amazon.hackathon.contactshare.utils.ImageDownloaderTask;
import com.amazon.hackathon.contactshare.utils.User;
import com.amazon.hackathoncontactshare.R;

public class MainActivity extends ActionBarActivity {

  private SharedPreferences sharedPreferences;
  private AlertDialog login_dialog;
  private View DialogView;
  private ProgressDialog p_dialog;
  private EditText userID, passwd;
  private CheckBox rememberPassword;
  
  private Button test;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sharedPreferences = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    LayoutInflater factory = LayoutInflater.from(MainActivity.this);
    DialogView = factory.inflate(R.layout.dialog_login, null);
    userID = (EditText) DialogView.findViewById(R.id.userid);
    passwd = (EditText) DialogView.findViewById(R.id.passwd);
    rememberPassword = (CheckBox) DialogView.findViewById(R.id.rememberPassword);

    test = (Button) findViewById(R.id.button1);
//    adapter = new SocialAuthAdapter(new ResponseListener());
//    
// 
//    
//   test.setOnClickListener(new OnClickListener() 
//   {
//      public void onClick(View v) 
//      {
//          adapter.authorize(ProviderUI.this, Provider.FACEBOOK);
//      }
//  });
   
   
   
   
    
    setupOnClickListener();
    setupDialog();

    showLoginDialog();

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment())
          .commit();
    }
    
    //grid view
    GridView gridview = (GridView) findViewById(R.id.gridview);
    GridViewGenerator gvg = new GridViewGenerator(gridview,MainActivity.this);
    
    //Add nearby people usernames and images
    String username="chaoran";
    String url="http://icons.iconarchive.com/icons/crountch/one-piece-jolly-roger/72/Luffys-flag-2-icon.png";
    User user1 = new User(username,url,"idchaoran");
    User user2 = new User("jiahuan",url,"idjiahuan");
    User user3 = new User("zhonghu",url,"idzhonghu");
    User user4 = new User("xuwei",url,"idxuwei");
    User user5 = new User("huayang",url,"idhuayang");
    User user6 = new User("laji",url,"idlaji");
    gvg.addUser(user1);
    gvg.addUser(user2);
    gvg.addUser(user3);
    gvg.addUser(user4);
    gvg.addUser(user5);
    gvg.addUser(user6);
    gvg.removeUser("idlaji");
    
    ArrayList<User> newList = new ArrayList<User>();
    
    User user7 = new User(username,url,"idchaoran");
    User user8 = new User("jiahuan",url,"idjiahuan");
    User user9 = new User("zhonghu",url,"idzhonghu");
    
    User user10 = new User("sdfsdfsd",url,"dedeedd");
    newList.add(user7);newList.add(user8);newList.add(user9);newList.add(user10);
    
    //gvg.updateUsers(newList);
    
    //add self image and username
    User currentUser = new User("AmazonUser",url,"idAmazonUser");
    ImageView iv= (ImageView)findViewById(R.id.imageView1);
    url="http://icons.iconarchive.com/icons/crountch/one-piece-jolly-roger/72/Luffys-flag-2-icon.png";
    new ImageDownloaderTask(iv).execute(url);
    TextView tx=(TextView)findViewById(R.id.txtTitle1);
    tx.setText(currentUser.getUsername());
  }
  
  
  

  private void showLoginDialog() {
    // TODO Auto-generated method stub
    if (!LoginModel.isLogin()) {
      if (sharedPreferences.getBoolean("rememberPassword", false)) {
        userID.setText(sharedPreferences.getString("userID", null));
        passwd.setText(sharedPreferences.getString("passwd", null));
        rememberPassword.setChecked(true);
      }
    }
    login_dialog.show();
  }

  private void setupDialog() {
    // TODO Auto-generated method stub
    InitLoginDialog();

  }

  private void InitLoginDialog() {
    // TODO Auto-generated method stub

    login_dialog = new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.login))
        .setView(DialogView)
        .setPositiveButton(getString(R.string.login), new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
            final CharSequence strDialogTitle = getString(R.string.LoginWaitDialogTitle);
            final CharSequence strDialogBody = getString(R.string.LoginWaitDialogBody);
            p_dialog = ProgressDialog.show(MainActivity.this, strDialogTitle, strDialogBody, true);
            new Thread() {
              public void run() {
                try {
                  submit();
                } catch (Exception e) {
                  e.printStackTrace();
                } finally {
                  p_dialog.dismiss();
                }
              }
            }.start();
          }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            userID.setText("");
            passwd.setText("");
            dialog.dismiss();
          }
        }).create();
  }

  private int RememberPasswordDeal(String name, String pswd) {
    if (name.trim().equals(""))
      return 4;
    if (pswd.trim().equals(""))
      return 5;
    if (rememberPassword.isChecked()) {
      Editor editor = sharedPreferences.edit();
      editor.putString("userID", name);
      editor.putString("passwd", pswd);
      editor.putBoolean("rememberPassword", true);
      editor.commit();
    } else {
      Editor editor = sharedPreferences.edit();
      editor.putString("userID", null);
      editor.putString("passwd", null);
      editor.putBoolean("rememberPassword", false);
      editor.commit();
    }
    return 0;
  }

  private int submit() {
    String name = userID.getText().toString().trim();
    String pswd = passwd.getText().toString().trim();
    return RememberPasswordDeal(name, pswd);

  }

  private void setupOnClickListener() {
    // TODO Auto-generated method stub
    test.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, CustomUI.class);
        startActivity(intent);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      return rootView;
    }
  }

  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
    }
  };
  

}
