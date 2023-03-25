package com.example.mynews.conservator.fragment.bottom.manager;

import static com.example.mynews.dao.LogicBackground.userurl;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.adapter.UsersManagerItemAdapter;
import com.example.mynews.bean.User;
import com.example.mynews.bean.UserModel;
import com.example.mynews.consumer.WriteActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_manager_users extends Fragment {

    private View view=null;

    private EditText users_mananger_edittext;

    private ListView users_manager_listview;

    UsersManagerItemAdapter usersManagerItemAdapter;


    List<User> userList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_manager_users, container, false);
            users_manager_listview=view.findViewById(R.id.users_manager_listview);
            users_mananger_edittext=view.findViewById(R.id.users_mananger_edittext);

            Drawable drawable=getResources().getDrawable(R.drawable.search);
            drawable.setBounds(0,0,55,55);
            users_mananger_edittext.setCompoundDrawables(drawable,null,null,null);

            initView();
            initData("");

            users_mananger_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                       String text = String.valueOf(charSequence);
                       usersManagerItemAdapter.removeData();
                       initData(text);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        return view;
    }

    private void initView(){
        users_manager_listview.setVerticalScrollBarEnabled(false);
        usersManagerItemAdapter=new UsersManagerItemAdapter(getContext(),userList);
        users_manager_listview.setAdapter(usersManagerItemAdapter);
    }

    private  void initData(String username){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url=userurl+"/SelectUserByUsername";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson=new Gson();
                UserModel userModel=gson.fromJson(response.toString(),UserModel.class);
                int code=userModel.getCode();
                Log.i("==", String.valueOf(userModel.getDatas()));
                if(code==171){
                    Toast.makeText(getContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                    List<User> users=userModel.getDatas();

                    for(int i=0;i<users.size();i++){
                        User user=new User();
                        user.setUid(users.get(i).getUid());
                        user.setUsername(users.get(i).getUsername());
                        user.setType(users.get(i).getType());
                        user.setSex(users.get(i).getSex());
                        user.setPhone(users.get(i).getPhone());
                        user.setPassword(users.get(i).getPassword());
                        user.setOccupation(users.get(i).getOccupation());
                        user.setLocation(users.get(i).getLocation());
                        user.setEmail(users.get(i).getEmail());
                        user.setConcerns(users.get(i).getConcerns());
                        user.setBirthday(users.get(i).getBirthday());
                        userList.add(user);
                    }

                    usersManagerItemAdapter.notifyDataSetChanged();

                }else if(code==172){
                    Toast.makeText(getContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}