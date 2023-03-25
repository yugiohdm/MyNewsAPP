package com.example.mynews.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mynews.consumer.EditdataActivity;
import com.example.mynews.consumer.MenuActivity;
import com.example.mynews.R;
import com.example.mynews.db.CollectibleDBManager;
import com.example.mynews.db.HistoryDBManager;
import com.example.mynews.util.SetLuminanceUtil;

public class Fragment_settings_have_logged_on extends Fragment {

    private LinearLayout  exit_login_layout;

    private SharedPreferences preferences;

    private RelativeLayout edit_data_layout;

    private Switch mainswitch1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings_have_logged_on, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exit_login_layout=view.findViewById(R.id.exit_login);
        mainswitch1=view.findViewById(R.id.mainswitch1);
        edit_data_layout=view.findViewById(R.id.edit_data_layout);

        preferences= getContext().getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();

        boolean isChecked=preferences.getBoolean("isChecked",false);
        mainswitch1.setChecked(isChecked);

        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(getActivity(),brightness);

        mainswitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    SetLuminanceUtil.setScreenBrightness(getActivity(),0);
                    editor.putBoolean("isChecked",true);
                    editor.putInt("brightness",0);
                    editor.commit();
                }else{
                    SetLuminanceUtil.setScreenBrightness(getActivity(),-1);
                    editor.putBoolean("isChecked",false);
                    editor.putInt("brightness",-1);
                    editor.commit();
                }
            }
        });

        exit_login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user1", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();

//                HistoryDBManager.initHistoryDB(getContext());
//                HistoryDBManager.DeleteHistoryList();

//                CollectibleDBManager.initCollectibleDB(getContext());
//                CollectibleDBManager.DeleteCollectiblePublicList();

                preferences=getActivity().getSharedPreferences("page", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putInt("index",2);
                editor1.commit();

                Intent intent = new Intent(getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        edit_data_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditdataActivity.class);
                startActivity(intent);
            }
        });
    }
}