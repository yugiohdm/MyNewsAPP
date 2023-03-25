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
import com.example.mynews.conservator.MenuActivity1;
import com.example.mynews.consumer.MenuActivity;
import com.example.mynews.R;
import com.example.mynews.RetrievePasswordActivity;
import com.example.mynews.bean.User;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.util.SetLuminanceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneLoginActivity extends AppCompatActivity {
    private ImageView goback_my_log_out_login2_button;

    private Button btn_login2;

    private EditText login_phone_text;

    private EditText login_password1_text;
    private SharedPreferences sharedPreferences;

    private SharedPreferences preferences;

    private TextView forget_password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(PhoneLoginActivity.this);
        setContentView(R.layout.activity_phone_login);
        goback_my_log_out_login2_button=findViewById(R.id.goback_my_log_out_login2);
        login_phone_text=findViewById(R.id.login_phone);
        login_password1_text=findViewById(R.id.login_password1);
        btn_login2=findViewById(R.id.btn_login2);
        forget_password2=findViewById(R.id.forget_password2);

        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        goback_my_log_out_login2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(PhoneLoginActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=login_phone_text.getText().toString();
                String password1=login_password1_text.getText().toString();
                if (phone.equals("") || password1.equals("")) {
                    Toast.makeText(PhoneLoginActivity.this, "手机号密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if(phone.length()==11) {
                        PhoneLogin(phone, password1, PhoneLoginActivity.this);
                    }else{
                        Toast.makeText(PhoneLoginActivity.this, "手机号少于11位数或者多于11位数", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        forget_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(PhoneLoginActivity.this, RetrievePasswordActivity.class);
                intent.putExtra("logintype","手机号");
                startActivity(intent);
            }
        });

    }


    public void PhoneLogin(String phone, String password, Activity activity){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = LogicBackground.userurl +"/PhoneLogin";
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("手机号登录信息",response.toString());
                    int code= response.getInt("code");
                    Log.d("code", String.valueOf(code));
                    if (code==2000) {
                        JSONObject data= response.getJSONObject("data");
                        Gson gson=new Gson();
                        User user=gson.fromJson(data.toString(), User.class);
                        Log.i("=user=", String.valueOf(user));
                        if(user.getType().equals("普通用户")) {
                            sharedPreferences = getSharedPreferences("user1", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("uid", user.getUid());
                            editor.putString("phone", user.getPhone());
                            editor.putString("username", user.getUsername());
                            editor.putString("email", user.getEmail());
                            editor.putString("password", user.getPassword());
                            editor.putString("sex", user.getSex());
                            editor.putString("type", user.getType());
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
//                        intent.putExtra("user1",user1);
                            activity.startActivity(intent);
                            activity.finish();
                        }else if(user.getType().equals("管理员")) {
                            sharedPreferences = getSharedPreferences("user1", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("uid", user.getUid());
                            editor.putString("username", user.getUsername());
                            editor.putString("phone", user.getPhone());
                            editor.putString("email", user.getEmail());
                            editor.putString("password", user.getPassword());
                            editor.putString("sex", user.getSex());
                            editor.putString("type", user.getType());
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
                    }else if (code==1000) {
                        Toast.makeText(activity, "手机号密码有误", Toast.LENGTH_SHORT).show();
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