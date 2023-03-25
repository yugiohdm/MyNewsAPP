package com.example.mynews.conservator.fragment.bottom.manager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mynews.R;
import com.example.mynews.consumer.fragment.buttom.my.Fragment_my_log_out;


public class Fragment_manager_news extends Fragment {

    private RadioGroup radioGroup1;
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    private Button news_manager_Button;

    private View view=null;

    FragmentManager fragmentManager;
    private FrameLayout news_manager_layout;

    RadioButton radioButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_manager_news, container, false);
            radioGroup1=view.findViewById(R.id.RadioGroup1);
            radioButton1=view.findViewById(R.id.RadioButton1);
            radioButton2=view.findViewById(R.id.RadioButton2);
            news_manager_Button=view.findViewById(R.id.news_manager_Button);
            news_manager_layout=view.findViewById(R.id.news_manager_layout);

            fragmentManager=getFragmentManager();

            if (radioButton1.isChecked()){
                fragmentManager.beginTransaction().replace(R.id.news_manager_layout, new Fragment_manager_publicnews()).commit();
            }

            radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    radioButton=view.findViewById(i);
                    radioButton.setChecked(true);
                    Log.i("--", String.valueOf(radioButton.getText()));
                }
            });


            news_manager_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(radioButton1.isChecked()){
                        Toast.makeText(getContext(),radioButton1.getText(),Toast.LENGTH_SHORT).show();
                        fragmentManager.beginTransaction().replace(R.id.news_manager_layout, new Fragment_manager_publicnews()).commit();
                    }else if(radioButton2.isChecked()){
                        Toast.makeText(getContext(),radioButton2.getText(),Toast.LENGTH_SHORT).show();
                        fragmentManager.beginTransaction().replace(R.id.news_manager_layout, new Fragment_manager_releasenews()).commit();
                    }
                }
            });
        }
        return view;
    }

}