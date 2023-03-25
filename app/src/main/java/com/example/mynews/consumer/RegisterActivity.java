package com.example.mynews.consumer;

import static com.example.mynews.util.HideStatusBarUtils.hideStatusBar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.activity.login.LoginActivity;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.util.SetLuminanceUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private ImageView goback_my_log_out_register_button;
    private EditText register_password;

    private EditText register_username;

    private EditText register_phone;

    private EditText register_email;

    private  EditText register_type;

    private Button register_button;

    private ImageView ivEye;
    private boolean isOpenEye = false;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(RegisterActivity.this);
        setContentView(R.layout.activity_register);
        goback_my_log_out_register_button=findViewById(R.id.goback_my_log_out_register);
        register_username=findViewById(R.id.register_username);
        register_password=findViewById(R.id.register_password);
        register_phone=findViewById(R.id.register_phone);
        register_email=findViewById(R.id.register_email);
        register_type=findViewById(R.id.register_type);
        register_button=findViewById(R.id.register_button);
        ivEye=findViewById(R.id.iv_eye);

        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        goback_my_log_out_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ivEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpenEye) {
                    ivEye.setSelected(true);
                    isOpenEye = true;
                    //密码可见
                    register_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    ivEye.setSelected(false);
                    isOpenEye = false;
                    //密码不可见
                    register_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  register_username1=register_username.getText().toString();
                String  register_password1=register_password.getText().toString();
                String  register_phone1=register_phone.getText().toString();
                String  register_email1=register_email.getText().toString();
                String  register_type1=register_type.getText().toString();
                if (register_username1.equals("") || register_password1.equals("")) {
                    Toast.makeText(RegisterActivity.this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Register(register_username1, register_password1,register_phone1, register_email1,register_type1,RegisterActivity.this);
                }
            }
        });



    }


    public void Register(String register_username, String register_password,String register_phone,String register_email,String register_type,Activity activity){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",register_username);
            jsonObject.put("password", register_password);
            jsonObject.put("phone", register_phone);
            jsonObject.put("email", register_email);
            jsonObject.put("type", register_type);
            jsonObject.put("likes",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = LogicBackground.userurl +"/Register";
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("注册信息",response.toString());
                    int code= response.getInt("code");
                    String message=response.getString("message");
                    Log.d("code", String.valueOf(code));
                    if (code==22) {
                        Toast.makeText(activity,message , Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent();
//                        intent.setClass(activity, LoginActivity.class);
//                        activity.startActivity(intent);
//                        activity.finish();
                    }else if (code==11) {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
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