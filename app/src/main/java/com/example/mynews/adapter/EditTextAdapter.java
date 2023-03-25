package com.example.mynews.adapter;



import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;



import com.example.mynews.R;
import com.example.mynews.bean.ItemBean;

import java.util.List;

public class EditTextAdapter extends BaseAdapter {

    private List<ItemBean> mData;
    private Context mContext;

    String title;

    public EditTextAdapter(Context mContext, List<ItemBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.title_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.editText.requestFocus();

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int count=getCount();
                if(charSequence==null){
                    title="";
                }else{
                    title=charSequence.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return convertView;

    }

    public String getTitle(){
        return title;
    }


    private class ViewHolder {
        private EditText editText;

        public ViewHolder(View convertView) {
            editText = (EditText) convertView.findViewById(R.id.title_editext);
        }
    }
}