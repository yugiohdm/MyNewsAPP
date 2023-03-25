package com.example.mynews.activity.login;

import static com.example.mynews.util.HideStatusBarUtils.hideStatusBar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.consumer.MenuActivity;
import com.example.mynews.R;
import com.example.mynews.consumer.RegisterActivity;
import com.example.mynews.RetrievePasswordActivity;
import com.example.mynews.bean.User;
import com.example.mynews.conservator.MenuActivity1;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.util.SetLuminanceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView goback_my_log_out_button;
    private EditText login_username_text;
    private EditText login_password_text;
    private Button btn_login;
    private Button btn_register;

    private ImageView phone_login_btn;

    private ImageView email_login_btn;

    private TextView forget_password;

    private SharedPreferences sharedPreferences;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(LoginActivity.this);
        setContentView(R.layout.activity_login);
        goback_my_log_out_button=findViewById(R.id.goback_my_log_out_login);
        login_password_text=findViewById(R.id.login_password);
        login_username_text=findViewById(R.id.login_username);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        phone_login_btn=findViewById(R.id.phone_login);
        email_login_btn=findViewById(R.id.email_login);
        forget_password=findViewById(R.id.forget_password);

        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        goback_my_log_out_button.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        phone_login_btn.setOnClickListener(this);
        email_login_btn.setOnClickListener(this);
        forget_password.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goback_my_log_out_login:
                finish();
                break;
            case R.id.btn_login:
                String username=login_username_text.getText().toString();
                String password=login_password_text.getText().toString();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    UsernameLogin(username, password, this);
                }
                break;
            case R.id.btn_register:
                Intent intent1=new Intent();
                intent1.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.phone_login:
                Intent intent2=new Intent();
                intent2.setClass(LoginActivity.this, PhoneLoginActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.email_login:
                Intent intent3=new Intent();
                intent3.setClass(LoginActivity.this, EmailLoginActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.forget_password:
                Intent intent4=new Intent();
                intent4.setClass(LoginActivity.this, RetrievePasswordActivity.class);
                intent4.putExtra("logintype","用户名");
                startActivity(intent4);
                break;
        }

    }

    public void UsernameLogin(String username, String password, Activity activity){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = LogicBackground.userurl +"/UsernameLogin";
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("用户名登录信息",response.toString());
                    int code= response.getInt("code");
                    Log.d("code", String.valueOf(code));
                    if (code==200) {
                        JSONObject data= response.getJSONObject("data");
                        Gson gson=new Gson();
                        User user=gson.fromJson(data.toString(), User.class);
                        Log.i("=user=", String.valueOf(user));

                        if(user.getType().equals("普通用户")) {

                            sharedPreferences = getSharedPreferences("user1", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("uid", user.getUid());
                            editor.putString("username", user.getUsername());
                            editor.putString("phone", user.getPhone());
                            editor.putString("email", user.getEmail());
                            editor.putString("password", user.getPassword());
                            editor.putString("sex", user.getSex());
                            editor.putString("type",user.getType());
                            editor.putString("birthday", user.getBirthday());
                            editor.putString("location", user.getLocation());
                            editor.putString("occupation", user.getOccupation());
                            editor.putInt("concerns", user.getConcerns());
                            editor.putInt("logintype", 1);
                            editor.commit();


                            preferences = getSharedPreferences("page", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = preferences.edit();
                            editor1.putInt("index", 2);
                            editor1.commit();

                            Intent intent = new Intent();
                            intent.setClass(activity, MenuActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }else if(user.getType().equals("管理员")){
                            sharedPreferences = getSharedPreferences("user1", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("uid", user.getUid());
                            editor.putString("username", user.getUsername());
                            editor.putString("phone", user.getPhone());
                            editor.putString("email", user.getEmail());
                            editor.putString("password", user.getPassword());
                            editor.putString("sex", user.getSex());
                            editor.putString("type",user.getType());
                            editor.putString("birthday", user.getBirthday());
                            editor.putString("location", user.getLocation());
                            editor.putString("occupation", user.getOccupation());
                            editor.putInt("concerns", user.getConcerns());
                            editor.putInt("logintype", 1);
                            editor.commit();


//                            preferences = getSharedPreferences("page", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor1 = preferences.edit();
//                            editor1.putInt("index", 2);
//                            editor1.commit();

                            Intent intent = new Intent();
                            intent.setClass(activity, MenuActivity1.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    }else if (code==100) {
                        Toast.makeText(activity, "用户名密码有误", Toast.LENGTH_SHORT).show();
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