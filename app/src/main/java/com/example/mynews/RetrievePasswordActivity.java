package com.example.mynews;

import static com.example.mynews.util.HideStatusBarUtils.hideStatusBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.activity.login.LoginActivity;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.util.SetLuminanceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RetrievePasswordActivity extends AppCompatActivity {
   private Spinner spinnertype;

   private ImageView goback_retrieve_password;

   private EditText retrieve_type;
   private String[] mItems=new String[3];

    private SharedPreferences preferences;

    private Button btn_next;

    String logintype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar(RetrievePasswordActivity.this);
        setContentView(R.layout.activity_retrieve_password);
        goback_retrieve_password=findViewById(R.id.goback_retrieve_password);
        spinnertype=findViewById(R.id.spinnertype);
        retrieve_type=findViewById(R.id.retrieve_type);
        btn_next=findViewById(R.id.btn_next);

        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        logintype=getIntent().getStringExtra("logintype");

        goback_retrieve_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent();
//                intent.setClass(RetrievePasswordActivity.this, LoginActivity.class);
//                startActivity(intent);
                finish();
            }
        });


        mItems = getResources().getStringArray(R.array.logintype);
//        mItems[0]="用户名";mItems[1]="手机号";mItems[2]="邮箱账号";

        Log.i("==", ""+String.valueOf(mItems[0]));
        Log.i("==", ""+String.valueOf(mItems[1]));
        Log.i("==", ""+String.valueOf(mItems[2]));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RetrievePasswordActivity.this, android.R.layout.simple_spinner_item,mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertype.setAdapter(adapter);
        for(int i=0;i<mItems.length;i++) {
            if (logintype.equals(mItems[i])) {
                spinnertype.setSelection(i);
            }
        }
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                     if(i==0){
                         retrieve_type.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(30)});
                         retrieve_type.setHint("请输入您的用户名");
                         btn_next.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 String textcontent=retrieve_type.getText().toString();
                                 String logintype=mItems[0];
                                 findPassword(textcontent,logintype,RetrievePasswordActivity.this);
                             }
                         });
                     }else if(i==1){
                         retrieve_type.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(11)});
                         retrieve_type.setHint("请输入您的11位电话号码");
                         btn_next.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 String textcontent=retrieve_type.getText().toString();
                                 String logintype=mItems[1];
                                 findPassword(textcontent,logintype,RetrievePasswordActivity.this);
                             }
                         });
                     }else if(i==2){
                         retrieve_type.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(50)});
                         retrieve_type.setHint("请输入您的邮箱账号");
                         btn_next.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 String textcontent=retrieve_type.getText().toString();
                                 String logintype=mItems[2];
                                 findPassword(textcontent,logintype,RetrievePasswordActivity.this);
                             }
                         });
                     }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    private void findPassword(String logincontent, String logintype, Activity activity){
        JSONObject jsonObject = new JSONObject();
        try {
            if (logintype.equals("用户名")) {
                jsonObject.put("username",logincontent);
            }else if(logintype.equals("手机号")){
                jsonObject.put("phone",logincontent);
            }else if(logintype.equals("邮箱账号")) {
                jsonObject.put("email", logincontent);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String url = LogicBackground.userurl +"/SelectPassword";
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("查找信息",response.toString());
                    int code = response.getInt("code");
                    String message=response.getString("message");
                    Log.d("code", String.valueOf(code));
                    if (code==141) {
                        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
                        Log.d("+password+",response.getString("data"));

                        new AlertDialog.Builder(activity).setTitle("您的密码为"+response.getString("data"))//设置对话框标题
                                .setMessage("是否确认修改密码？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {//添加确定按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                        Intent intent=new Intent();
                                        intent.setClass(activity,UpdatePasswordActivity.class);
                                        intent.putExtra(logintype,logincontent);
                                        startActivity(intent);
//                                        activity.finish();
                                    }
                                }).setNegativeButton("否", null).show();//在按键响应事件中显示此对话框

                    }else if(code==142){
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