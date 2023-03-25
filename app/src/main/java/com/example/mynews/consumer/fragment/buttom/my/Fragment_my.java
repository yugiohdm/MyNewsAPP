package com.example.mynews.consumer.fragment.buttom.my;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.mynews.R;


public class Fragment_my extends Fragment {//我的
    int type_login;
    FragmentManager fragmentManager;
    private View view=null;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_my, container, false);

            fragmentManager=getFragmentManager();
            sharedPreferences =getContext().getSharedPreferences("user1", Context.MODE_PRIVATE);
            type_login=sharedPreferences.getInt("logintype",0);
            if (type_login==0) {
                fragmentManager.beginTransaction().replace(R.id.myfragment, new Fragment_my_log_out()).commit();
            }else if(type_login==1){
                fragmentManager.beginTransaction().replace(R.id.myfragment, new Fragment_my_have_logged_on()).commit();
            }
        }
        return view;
    }




}
