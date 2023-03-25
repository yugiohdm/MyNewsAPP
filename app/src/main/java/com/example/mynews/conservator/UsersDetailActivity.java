package com.example.mynews.conservator;

import static com.example.mynews.dao.LogicBackground.userurl;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
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
import com.example.mynews.R;
import com.example.mynews.bean.BaseModel;
import com.example.mynews.bean.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class UsersDetailActivity extends AppCompatActivity {


    private ImageView goback_users_detail_activity;
    private EditText users_detail_id;
    private EditText users_detail_username;
    private EditText users_detail_password;
    private EditText users_detail_sex;
    private EditText users_detail_birthday;
    private EditText users_detail_location;
    private EditText users_detail_phone;
    private EditText users_detail_email;
    private EditText users_detail_occupation;
    private EditText users_detail_concerns;
    private EditText users_detail_type;
    private Button users_detail_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_detail);
        goback_users_detail_activity=findViewById(R.id.goback_users_detail_activity);
        users_detail_id=findViewById(R.id.users_detail_id);
        users_detail_username=findViewById(R.id.users_detail_username);
        users_detail_password=findViewById(R.id.users_detail_password);
        users_detail_sex=findViewById(R.id.users_detail_sex);
        users_detail_birthday=findViewById(R.id.users_detail_birthday);
        users_detail_location=findViewById(R.id.users_detail_location);
        users_detail_phone=findViewById(R.id.users_detail_phone);
        users_detail_email=findViewById(R.id.users_detail_email);
        users_detail_occupation=findViewById(R.id.users_detail_occupation);
        users_detail_concerns=findViewById(R.id.users_detail_concerns);
        users_detail_type=findViewById(R.id.users_detail_type);
        users_detail_update=findViewById(R.id.users_detail_update);


        setUsersData();

        goback_users_detail_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        users_detail_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int users_id= Integer.parseInt(users_detail_id.getText().toString());
                String users_username=users_detail_username.getText().toString();
                String users_password=users_detail_password.getText().toString();
                String users_sex=users_detail_sex.getText().toString();
                String users_birthday=users_detail_birthday.getText().toString();
                String users_location=users_detail_location.getText().toString();
                String users_phone= users_detail_phone.getText().toString();
                String users_email= users_detail_email.getText().toString();
                String users_occupation=users_detail_occupation.getText().toString();
                int  users_concerns=Integer.parseInt(users_detail_concerns.getText().toString());
                String users_type=users_detail_type.getText().toString();
                UpdateUsersDetail(users_id,users_username,users_password,users_sex,users_birthday,users_location,users_phone,users_email,users_occupation,users_concerns,users_type);
            }
        });

    }

    private void  setUsersData(){
        User user= (User) getIntent().getSerializableExtra("users");
        users_detail_id.setText(user.getUid()+"");
        users_detail_username.setText(user.getUsername());
        users_detail_username.requestFocus();
        users_detail_password.setText(user.getPassword());
        users_detail_password.requestFocus();
        users_detail_sex.setText(user.getSex());
        users_detail_sex.requestFocus();
        users_detail_birthday.setText(user.getBirthday());
        users_detail_birthday.requestFocus();
        users_detail_location.setText(user.getLocation());
        users_detail_location.requestFocus();
        users_detail_phone.setText(user.getPhone());
        users_detail_phone.requestFocus();
        users_detail_email.setText(user.getEmail());
        users_detail_email.requestFocus();
        users_detail_occupation.setText(user.getOccupation());
        users_detail_occupation.requestFocus();
        users_detail_concerns.setText(user.getConcerns()+"");
        users_detail_concerns.requestFocus();
        users_detail_type.setText(user.getType());
        users_detail_type.requestFocus();
    }


    private void UpdateUsersDetail(int id,String username,String password,String sex,String birthday,String location,String phone,String email,String occupation,int concerns,String type){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid",id);
            jsonObject.put("username",username);
            jsonObject.put("password",password);
            jsonObject.put("sex",sex);
            jsonObject.put("birthday",birthday);
            jsonObject.put("location",location);
            jsonObject.put("phone",phone);
            jsonObject.put("email",email);
            jsonObject.put("occupation",occupation);
            jsonObject.put("concerns",concerns);
            jsonObject.put("type",type);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=userurl+"/UpdateUser";
        RequestQueue requestQueue = Volley.newRequestQueue(UsersDetailActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                BaseModel baseModel=gson.fromJson(response.toString(),BaseModel.class);
                Log.i("==", String.valueOf(baseModel));
                int code=baseModel.getCode();
                if(code==10){
                    Toast.makeText(UsersDetailActivity.this, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }else if(code==20){
                    Toast.makeText(UsersDetailActivity.this, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UsersDetailActivity.this, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UsersDetailActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }




}