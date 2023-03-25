package com.example.mynews.conservator.fragment.bottom;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mynews.R;
import com.example.mynews.consumer.MenuActivity;

import org.w3c.dom.Text;


public class Fragment_mine extends Fragment {
    private LinearLayout exit_admin_login;

    private TextView mine_username;
    private SharedPreferences preferences;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exit_admin_login=view.findViewById(R.id.exit_admin_login);
        mine_username=view.findViewById(R.id.mine_username);

        sharedPreferences=getContext().getSharedPreferences("user1", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username","");
        mine_username.setText(username);

        exit_admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user1", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();

                preferences=getActivity().getSharedPreferences("page", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putInt("index",2);
                editor1.commit();

                Intent intent = new Intent(getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}