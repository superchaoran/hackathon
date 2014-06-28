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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazon.hackathon.contactshare.gridview.GridViewGenerator;
import com.amazon.hackathon.contactshare.gridview.ImageAdapter;
import com.amazon.hackathon.contactshare.login.LoginModel;
import com.amazon.hackathon.contactshare.utils.User;
import com.amazon.hackathon.contactshare.utils.addAUrlImage;
import com.amazon.hackathoncontactshare.R;

public class MainActivity extends ActionBarActivity {

  private Button buttonGoToPersonalArchiveActivity;
  private SharedPreferences sharedPreferences;
  private AlertDialog login_dialog;
  private View DialogView;
  private ProgressDialog p_dialog;
  private EditText userID, passwd;
  private CheckBox rememberPassword;




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

    //setupOnClickListener();
    setupDialog();

    showLoginDialog();

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment())
          .commit();
    }
    
    //grid view
    GridView gridview = (GridView) findViewById(R.id.gridview);
    ArrayList<User> userList = new ArrayList<User>();
    new GridViewGenerator(userList,gridview,this);
    
    //add a URL image
    ImageView iv= (ImageView)findViewById(R.id.imageView1);
    String url="http://icons.iconarchive.com/icons/crountch/one-piece-jolly-roger/72/Luffys-flag-2-icon.png";
    (new Thread(new addAUrlImage (iv,url))).start();

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
    buttonGoToPersonalArchiveActivity.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, PersonalArchiveActivity.class);
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
