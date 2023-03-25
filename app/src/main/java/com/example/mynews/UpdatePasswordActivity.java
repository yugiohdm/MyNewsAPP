package com.example.mynews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.activity.login.EmailLoginActivity;
import com.example.mynews.activity.login.LoginActivity;
import com.example.mynews.activity.login.PhoneLoginActivity;
import com.example.mynews.dao.LogicBackground;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdatePasswordActivity extends AppCompatActivity {

    private String[] mItems=new String[3];

    private Button  btn_update;

    private TextView update_password;

    private TextView update_password1;

    private ImageView goback_update_password;

    private boolean isOpenEye1 = false;

    private boolean isOpenEye2 = false;

    private ImageView ivEye1;

    private ImageView ivEye2;

    String logincontent;

    String logintype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        btn_update=findViewById(R.id.btn_update);
        update_password=findViewById(R.id.update_password);
        update_password1=findViewById(R.id.update_password1);
        goback_update_password=findViewById(R.id.goback_update_password);
        ivEye1=findViewById(R.id.iv_eye1);
        ivEye2=findViewById(R.id.iv_eye2);
        mItems = getResources().getStringArray(R.array.logintype);


        for (int i=0;i<mItems.length;i++) {
           String logincontent2 = getIntent().getStringExtra(mItems[i]);
            if(logincontent2==null){
                 continue;
            }
            logintype=mItems[i];
            logincontent=logincontent2;
        }

        Log.i("===",""+logincontent);
        Log.i("===",""+logintype);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String password=update_password.getText().toString();
               String password1=update_password1.getText().toString();
               if(password.equals(password1)){
                   updatePassword(logintype,logincontent,password,UpdatePasswordActivity.this);
               }else{
                   Toast.makeText(UpdatePasswordActivity.this,"两次输入的密码不一致!",Toast.LENGTH_SHORT).show();
               }
            }
        });


        goback_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ivEye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpenEye1) {
                    ivEye1.setSelected(true);
                    isOpenEye1 = true;
                    //密码可见
                    update_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    ivEye1.setSelected(false);
                    isOpenEye1 = false;
                    //密码不可见
                    update_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        ivEye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpenEye2) {
                    ivEye2.setSelected(true);
                    isOpenEye2 = true;
                    //密码可见
                    update_password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    ivEye2.setSelected(false);
                    isOpenEye2 = false;
                    //密码不可见
                    update_password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }
      private void updatePassword(String logintype1,String logincontent1,String updatepassword, Activity activity){
          JSONObject jsonObject = new JSONObject();
          try {
              if (logintype1.equals("用户名")) {
                  jsonObject.put("username",logincontent1);
                  jsonObject.put("password",updatepassword);
              }else if(logintype1.equals("手机号")){
                  jsonObject.put("phone",logincontent1);
                  jsonObject.put("password",updatepassword);
              }else if(logintype1.equals("邮箱账号")) {
                  jsonObject.put("email", logincontent1);
                  jsonObject.put("password",updatepassword);
              }
          } catch (JSONException e) {
              throw new RuntimeException(e);
          }

          String url = LogicBackground.userurl +"/UpdatePassword";
          RequestQueue requestQueue = Volley.newRequestQueue(activity);
          JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                  try {
                      Log.d("修改信息",response.toString());
                      int code = response.getInt("code");
                      String message=response.getString("message");
                      Log.d("code", String.valueOf(code));

                      if (code==111) {
                          Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
                          Log.d("+password+",response.getString("data"));

                          new AlertDialog.Builder(activity).setTitle("您的新密码为"+response.getString("data")+",并选择要返回的登录页面")//设置对话框标题
                                  .setSingleChoiceItems(new String[]{"用户名登录","手机号登录","邮箱账号登录"}, -1, new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                          switch (i) {
                                              case 0:
                                                  startActivity(new Intent(activity, LoginActivity.class));
                                                  activity.finish();
                                                  break;
                                              case 1:
                                                  startActivity(new Intent(activity, PhoneLoginActivity.class));
                                                  activity.finish();
                                                  break;
                                              case 2:
                                                  startActivity(new Intent(activity, EmailLoginActivity.class));
                                                  activity.finish();
                                                  break;
                                              default: break;
                                          }
                                         dialogInterface.dismiss();
                                      }
                                  }).setNegativeButton("取消",null).show();//在按键响应事件中显示此对话框
                      }else if(code==222){
                          Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
                      }
                  } catch (JSONException e) {
                      throw new RuntimeException(e);
                  }
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError volleyError) {
                  Toast.makeText(activity, "网络出错", Toast.LENGTH_SHORT).show();
              }
          });
          requestQueue.add(jsonObjectRequest);
      }

}