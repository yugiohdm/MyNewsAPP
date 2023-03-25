package com.example.mynews.consumer.fragment.buttom.my;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynews.activity.login.LoginActivity;
import com.example.mynews.R;
import com.example.mynews.activity.settings.SettingsActivity;
import com.example.mynews.util.SetLuminanceUtil;


public class Fragment_my_log_out extends Fragment {//未登录我的页面
    private ImageView set;
    private ImageView sweep;

    private TextView loginbotton;

    private Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_log_out, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        set=view.findViewById(R.id.set);
        sweep=view.findViewById(R.id.sweep);
        loginbotton=view.findViewById(R.id.loginbotton);


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getContext(), SettingsActivity.class);
                intent.putExtra("set","aa");
                startActivity(intent);
            }
        });

        sweep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        loginbotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getContext(), LoginActivity.class);
//                getActivity().overridePendingTransition(R.anim.activity_open,R.anim.activity_close);
                startActivity(intent);
            }
        });

    }
}