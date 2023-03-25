package com.example.mynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynews.R;
import com.example.mynews.bean.User;
import com.example.mynews.conservator.UsersDetailActivity;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.view.ConfirmPopWindow;

import java.util.ArrayList;
import java.util.List;

public class UsersManagerItemAdapter extends BaseAdapter {
    Context context;
    List<User> userList=new ArrayList<>();




    public UsersManagerItemAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }



    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UserViewHolder userViewHolder;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.users_manager_item, null);
            userViewHolder = new UserViewHolder();
            userViewHolder.users_manager_id = view.findViewById(R.id.users_manager_id);
            userViewHolder.users_manager_username = view.findViewById(R.id.users_manager_username);
            userViewHolder.users_manager_type = view.findViewById(R.id.users_manager_type);
            userViewHolder.users_detail = view.findViewById(R.id.users_detail);
            userViewHolder.users_delete = view.findViewById(R.id.users_delete);
            view.setTag(userViewHolder);
        }else{
            userViewHolder=(UserViewHolder) view.getTag();
        }

        userViewHolder.users_manager_id.setText(userList.get(i).getUid()+"");
        userViewHolder.users_manager_username.setText(userList.get(i).getUsername());
        userViewHolder.users_manager_type.setText(userList.get(i).getType());

        userViewHolder.users_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, UsersDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("users",userList.get(i));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


        userViewHolder.users_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ConfirmPopWindow confirmPopWindow=new ConfirmPopWindow(context);
               confirmPopWindow.showAtBottom(view);
               ConfirmPopWindow.ll_delete.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
//                      Toast.makeText(context,"删除",Toast.LENGTH_SHORT).show();
                      LogicBackground.DeleteUsers(context,userList.get(i).getUid());
                      removeItem(i);
                      confirmPopWindow.dismiss();
                  }
              });

              ConfirmPopWindow.ll_cancel.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
//                      Toast.makeText(context,"取消",Toast.LENGTH_SHORT).show();
                      confirmPopWindow.dismiss();
                  }
              });
            }
        });

        return view;
    }

    public void removeData(){
        userList.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int i){
        userList.remove(i);
        notifyDataSetChanged();
    }

    class UserViewHolder{
        private TextView users_manager_id;
        private TextView users_manager_username;
        private TextView users_manager_type;
        private TextView release_date;
        private Button users_detail;
        private ImageView users_delete;
    }
}
