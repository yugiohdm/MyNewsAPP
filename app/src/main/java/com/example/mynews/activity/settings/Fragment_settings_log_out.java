package com.example.mynews.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import com.example.mynews.R;
import com.example.mynews.util.SetLuminanceUtil;

public class Fragment_settings_log_out extends Fragment {
    private Switch  mainswitch;
    private SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_log_out, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainswitch=view.findViewById(R.id.mainswitch);

        preferences= getContext().getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();

        boolean isChecked=preferences.getBoolean("isChecked",false);
        mainswitch.setChecked(isChecked);

        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(getActivity(),brightness);

        mainswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    }




}