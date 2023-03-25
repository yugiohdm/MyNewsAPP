package com.example.mynews.consumer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.R;
import com.example.mynews.api.OnTimeSelectListener;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.util.DateUtil;
import com.example.mynews.util.JsonFileReader;
import com.example.mynews.api.OnOptionsSelectChangeListener;
import com.example.mynews.api.OnOptionsSelectListener;
import com.example.mynews.util.OptionsPickerBuilder;
import com.example.mynews.util.SetLuminanceUtil;
import com.example.mynews.util.StatusBarUtils;
import com.example.mynews.util.TimePickerBuilder;
import com.example.mynews.view.OptionsPickerView;
import com.example.mynews.view.TimePickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EditdataActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences preferences;

    private ImageView goback_editdata_activity;
    boolean iChecked;

    private RelativeLayout edit_username_layout;
    private RelativeLayout edit_sex_layout;
    private RelativeLayout edit_birthday_layout;
    private RelativeLayout edit_location_layout;
    private RelativeLayout edit_occupation_layout;

    private TextView username_text;
    private TextView sex_text;
    private TextView birthday_text;
    private TextView location_text;
    private TextView occupation_text;


    OptionsPickerView pvOptions;
    OptionsPickerView pvOptions1;

    OptionsPickerView pvOptions2;
    TimePickerView pvTime;
    //  省份
    ArrayList<String> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();



    ArrayList<String> professionBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> types;
    ArrayList<List<String>> typeList = new ArrayList<>();

    String username1;
    String useothertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        iChecked=preferences.getBoolean("isChecked",false);
        StatusBarUtils.setWindowStatusBarColor(EditdataActivity.this,R.color.white,iChecked);

        goback_editdata_activity=findViewById(R.id.goback_editdata_activity);
        edit_username_layout=findViewById(R.id.edit_username_layout);
        edit_sex_layout=findViewById(R.id.edit_sex_layout);
        edit_birthday_layout=findViewById(R.id.edit_birthday_layout);
        edit_location_layout=findViewById(R.id.edit_location_layout);
        edit_occupation_layout=findViewById(R.id.edit_occupation_layout);
        username_text=findViewById(R.id.username_text);
        sex_text=findViewById(R.id.sex_text);
        birthday_text=findViewById(R.id.birthday_text);
        location_text=findViewById(R.id.location_text);
        occupation_text=findViewById(R.id.occupation_text);

        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        setgender();
        setdate();
        setprovince();
        setprofession();
        setMyContent();

        goback_editdata_activity.setOnClickListener(this);
        edit_username_layout.setOnClickListener(this);
        edit_sex_layout.setOnClickListener(this);
        edit_birthday_layout.setOnClickListener(this);
        edit_location_layout.setOnClickListener(this);
        edit_occupation_layout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.goback_editdata_activity:
                finish();
                break;
            case R.id.edit_username_layout:
                break;
            case R.id.edit_sex_layout:
                pvOptions1.show();
                break;
            case R.id.edit_birthday_layout:
                pvTime.show();
                break;
            case R.id.edit_location_layout:
                pvOptions.show();
                break;
            case R.id.edit_occupation_layout:
                pvOptions2.show();
                break;

        }
    }







    //  解析json填充集合
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void parseJson1(String str){
        //  获取json中的数组
        try {
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取职业的对象
                JSONObject professionObject = jsonArray.optJSONObject(i);
                //  获取职业名称放入集合
                String Name = professionObject.getString("occupationname");
                professionBeanList.add(Name);

                types=new ArrayList<>();
                //  获取职业类型数组
                JSONArray typeArray = professionObject.optJSONArray("occupationtype");

                //  遍历区县数组，获取到区县名称并放入集合
                for (int k = 0; k < typeArray.length(); k++) {
                    String typeName = typeArray.getString(k);
                    types.add(typeName);
                }

                typeList.add(types);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private void setgender(){
        List<String> genderlist=new ArrayList<>();
        genderlist.add("男");
        genderlist.add("女");
        genderlist.add("保密");

        pvOptions1=new OptionsPickerBuilder(EditdataActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String sex1=genderlist.get(options1);
                sex_text.setText(sex1);
                sex_text.setTextColor(Color.parseColor("#272727"));

                SharedPreferences sharedPreferences=getSharedPreferences("user1",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("sex",sex1);
                editor.commit();

                setUserother(username1, sex1,0, EditdataActivity.this);
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {

            }
        }).setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("性别选择")//标题文字
                .isCenterLabel(false)//是否只显示中间选中项的label文字，false则每项item全部都带有label
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        pvOptions1.setPicker(genderlist);
    }



    private void setdate(){
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();


        //正确设置方式 原因：注意事项有说
        startDate.set(1900,0,1);
        endDate.set(2100,11,31);

        pvTime = new TimePickerBuilder(EditdataActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                 String date1=DateUtil.dateToString(date);
                 birthday_text.setText(date1);
                 birthday_text.setTextColor(Color.parseColor("#272727"));

                SharedPreferences sharedPreferences=getSharedPreferences("user1",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("birthday",date1);
                editor.commit();

                setUserother(username1, date1,1, EditdataActivity.this);
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("时间选择")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有lab
                .build();
    }

    private void setprovince(){
        //  获取json数据
        String province_json = JsonFileReader.getJson1(EditdataActivity.this, "province.json");
        //  解析json数据
        parseJson(province_json);
        Log.i("provinceBeanList", String.valueOf(provinceBeanList));
        Log.i("cityList", String.valueOf(cityList));
        Log.i("districtList", String.valueOf(districtList));


        //  监听确定选择按钮
        pvOptions=new OptionsPickerBuilder(EditdataActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String city = provinceBeanList.get(options1);
                String address;
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门特别行政区".equals(city) || "香港特别行政区".equals(city)) {
                    address = provinceBeanList.get(options1)
                            + " " + districtList.get(options1).get(options2).get(options3);
                } else {
                    address = provinceBeanList.get(options1)
                            + " " + cityList.get(options1).get(options2)
                            + " " + districtList.get(options1).get(options2).get(options3);
                }
                location_text.setText(address);
                location_text.setTextColor(Color.parseColor("#272727"));

                SharedPreferences sharedPreferences=getSharedPreferences("user1",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("location",address);
                editor.commit();

                setUserother(username1, address,2, EditdataActivity.this);
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {

                    }
                }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("选择城市")//标题
                .setTitleSize(20)//标题文字大小
                .isCenterLabel(false)//是否只显示中间选中项的label文字，false则每项item全部都带有label
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

                //  设置三级联动效果
                pvOptions.setPicker(provinceBeanList, cityList, districtList);

    }



    private void setprofession(){
        String profession_json = JsonFileReader.getJson1(EditdataActivity.this, "profession.json");
        //  解析json数据
        parseJson1(profession_json);

        Log.i("professionBeanList", String.valueOf(professionBeanList));
        Log.i("typeList", String.valueOf(typeList));


        //  监听确定选择按钮
        pvOptions2=new OptionsPickerBuilder(EditdataActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String typename=typeList.get(options1).get(options2);
                occupation_text.setText(typename);
                occupation_text.setTextColor(Color.parseColor("#272727"));

                SharedPreferences sharedPreferences=getSharedPreferences("user1",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("occupation",typename);
                editor.commit();

                setUserother(username1, typename,3, EditdataActivity.this);
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {

                    }
                }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("选择职业")//标题
                .setTitleSize(20)//标题文字大小
                .isCenterLabel(false)//是否只显示中间选中项的label文字，false则每项item全部都带有label
                .setSelectOptions(0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        //  设置二级联动效果
        pvOptions2.setPicker(professionBeanList, typeList);

    }





    private void setMyContent(){

        preferences=getSharedPreferences("user1",Context.MODE_PRIVATE);
         username1=preferences.getString("username","");
        if (!username1.equals("")){
            username_text.setText(username1);
            username_text.setTextColor(Color.parseColor("#272727"));
        }
        String date1=preferences.getString("birthday","");
        if(!date1.equals("")){
            birthday_text.setText(date1);
            birthday_text.setTextColor(Color.parseColor("#272727"));
        }
        String sex1=preferences.getString("sex","");
        if(!sex1.equals("")){
            sex_text.setText(sex1);
            sex_text.setTextColor(Color.parseColor("#272727"));
        }
        String location1=preferences.getString("location","");
        if(!location1.equals("")){
            location_text.setText(location1);
            location_text.setTextColor(Color.parseColor("#272727"));
        }
        String occupation1=preferences.getString("occupation","");
        if(!occupation1.equals("")){
            occupation_text.setText(occupation1);
            occupation_text.setTextColor(Color.parseColor("#272727"));
        }
    }


    private void setUserother(String username, String other,int type, Activity activity){
        JSONObject jsonObject = new JSONObject();
        try {
            if(type==0){
                jsonObject.put("username", username);
                jsonObject.put("sex", other);
                useothertype="UpdateUserSex";
            }else if(type==1){
                jsonObject.put("username", username);
                jsonObject.put("birthday", other);
                useothertype="UpdateUserBirthday";
            }else if(type==2){
                jsonObject.put("username", username);
                jsonObject.put("location", other);
                useothertype="UpdateUserLocation";
            }else if(type==3){
                jsonObject.put("username", username);
                jsonObject.put("occupation", other);
                useothertype="UpdateUserOccupation";
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = LogicBackground.userurl +"/"+useothertype;
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("用户名其他信息",response.toString());
                    int code= response.getInt("code");
                    String message=response.getString("message");
                    Log.d("code", String.valueOf(code));
                    Log.d("message", message);
                    if (code==101) {
                        Toast.makeText(activity, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if (code==202) {
                        Toast.makeText(activity, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(activity, "网络出错", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}