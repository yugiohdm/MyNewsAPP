package com.example.mynews;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mynews.app.Config;
import com.example.mynews.bean.News1;
import com.example.mynews.bean.News2;
import com.example.mynews.bean.NewsBaseModel;
import com.example.mynews.bean.News;
import com.example.mynews.bean.Page;
import com.example.mynews.consumer.MenuActivity;
import com.example.mynews.dao.LogicBackground;
import com.example.mynews.db.HistoryDBManager;
import com.example.mynews.util.FileUtil;
import com.example.mynews.util.MyKey;
import com.example.mynews.util.SetLuminanceUtil;
import com.example.mynews.util.StatusBarUtils;
import com.example.mynews.util.StringUtils;
import com.example.mynews.view.WordWrapView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity  implements TextWatcher, TextView.OnEditorActionListener{
    private ImageView goback_search_activity;
    private EditText mEtSearch;
    private WordWrapView mSearchHistory;
    private WordWrapView mHotSearch;
    private ImageView mIvSearchTextClear;
    private TextView search_button;
    Set<Integer> set;

    private SharedPreferences preferences;



    boolean iChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        preferences=getSharedPreferences("appcompat", Context.MODE_PRIVATE);
        iChecked=preferences.getBoolean("isChecked",false);

        StatusBarUtils.setWindowStatusBarColor(SearchActivity.this,R.color.white,iChecked);
        //设置状态栏文字颜色及图标为深色


        goback_search_activity=findViewById(R.id.goback_search_activity);
        mEtSearch=findViewById(R.id.search);
        mSearchHistory=findViewById(R.id.wwv_search_history);
        mHotSearch=findViewById(R.id.wwv_hot_search);
        mIvSearchTextClear=findViewById(R.id.iv_search_clear);
        search_button=findViewById(R.id.search_button);


        int brightness=preferences.getInt("brightness",-1);
        SetLuminanceUtil.setScreenBrightness(this,brightness);

        //禁止输入空格
        StringUtils.setEditTextInhibitInputSpace(mEtSearch);
        //输入框设置文本改变事件监听
        mEtSearch.addTextChangedListener(SearchActivity.this);
        //输入框软键盘回车键监听
        mEtSearch.setOnEditorActionListener(SearchActivity.this);
        //设置历史搜索 记录显示
        setHistoryView();
        //设置热门搜索
        setHotSearch();

        goback_search_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences=getSharedPreferences("page", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putInt("index",0);
                editor1.commit();

                Intent intent=new Intent();
                intent.setClass(SearchActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        mIvSearchTextClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSearchRecord();
                mSearchHistory.removeAllViews();
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            mIvSearchTextClear.setVisibility(View.VISIBLE);
        } else {
            mIvSearchTextClear.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    /**
     * 搜索新闻，即带数据 跳转界面 并保存搜索内容
     */
    private void search() {
        String content = mEtSearch.getText().toString();
        //去掉空格
        if (content.equals("")) {

        } else {
            //保存搜索的 文本内容
            saveSearchRecord(content);
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            String searchContent = mEtSearch.getText().toString();
            intent.putExtra("SEARCH", searchContent);
            startActivity(intent);
        }
    }


    /**
     * 保存搜索的文本记录
     *
     * @param content 文件名
     */
    private void saveSearchRecord(String content) {
        //文件保存在 /data/data/PACKAGE_NAME/files 目录下 并以 #' 分割
        FileUtil.write(this, Config.SEARCHFILENAME, content + "#'");
    }

    /**
     * 删除搜索的文本记录
     */
    private void deleteSearchRecord() {
        File file = this.getFilesDir();
        File newFile = new File(file, Config.SEARCHFILENAME);
        //删除文件
        newFile.delete();
    }

    /**
     * 得到搜索记录的 文本数据集合
     *
     * @return
     */
    private List<String> getSearchRecord() {
        List<String> list = new ArrayList<>();
        String content = FileUtil.read(this, Config.SEARCHFILENAME);
        Log.e(TAG, "------------------------" + content);
        //将得到的文本 以 #' 分割出来
        String[] array = content.split("#'");
        //逆序添加数据 进集合，最后搜索的 第一个显示
        for (int i = array.length - 1; i >= 0; i--) {
            if (!array[i].equals("")) {
                list.add(array[i]);
            }
        }
        return list;
    }

    /**
     * 设置历史记录
     */
    private void setHistoryView() {
                    List<String> searchHistory = getSearchRecord();
                    if (searchHistory != null && searchHistory.size() > 0) {
                        //清空子View后再次 设置新的
                        mSearchHistory.removeAllViewsInLayout();
                        for (int i = 0; i < searchHistory.size(); i++) {
                            //搜索的内容
                            final String searchText = searchHistory.get(i);
                            //新建LinearLayout 将XML布局 添加到此View中 然后添加到 WordWrapView自动换行布局中
                            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(SearchActivity.this).inflate(
                                    R.layout.item_search_history, null);
//                //设置 控件之间的间隔
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                params.setMargins(5,5,5,5);
//                linearLayout.setLayoutParams(params);
                            //设置文字内容
                            TextView textView = (TextView) linearLayout.findViewById(R.id.tv_search_history);
                            textView.setText(searchText);
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                            //跳转界面
                                            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                                            intent.putExtra("SEARCH", searchText);
                                            startActivity(intent);
                                        }
                            });
                            mSearchHistory.addView(linearLayout);
                        }
    }
}



    /**
     * 设置热门搜索
     */


        private void setHotSearch(){
            JSONObject jsonObject = new JSONObject();
            String type=null;
            try {
                type = URLEncoder.encode("头条", "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = LogicBackground.newsurl+"/SelectNewsByPage?pagecurrent=1&pagesize=30&type="+type+"";
            RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("分页信息", response.toString());

                    Gson gson=new Gson();
                    Page newsPage=gson.fromJson(response.toString(), Page.class);

                    Log.d("新闻信息", newsPage.getRecords().toString());
                    List<News2> newsList1=newsPage.getRecords();
                    List<News> newsList=new ArrayList<>();

                    for (int k=0;k<newsList1.size();k++){
                        News news=new News();
                        news.setTitle(newsList1.get(k).getTitle());
                        news.setUniquekey(newsList1.get(k).getUniquekey());
                        news.setCategory(newsList1.get(k).getCategory());
                        news.setDate(newsList1.get(k).getDate());
                        news.setAuthor_name(newsList1.get(k).getAuthorName());
                        news.setUrl(newsList1.get(k).getUrl());
                        news.setThumbnail_pic_s(newsList1.get(k).getThumbnailPicS());
                        news.setThumbnail_pic_s02(newsList1.get(k).getThumbnailPicS02());
                        news.setThumbnail_pic_s03(newsList1.get(k).getThumbnailPicS03());
                        news.setIs_content(newsList1.get(k).getIsContent());
                        newsList.add(news);
                    }

                    Log.i("==", String.valueOf(newsList));

                    set = new HashSet<>();
                    while (true) {
                        int position = (int) (Math.random() * (29 - 0 + 1));
                        set.add(position);
                        if (set.size() >= 5)
                            break;
                    }

                    for (int n : set) {
                        for (int i = 0; i < newsList.size(); i++) {
                            if (i == n) {
                                final String hotSearch = newsList.get(i).getTitle();
                                //新建LinearLayout 将XML布局 添加到此View中 然后添加到 WordWrapView自动换行布局中
                                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(SearchActivity.this).inflate(
                                        R.layout.item_search_history, null);
                                //设置文字内容
                                TextView textView = (TextView) linearLayout.findViewById(R.id.tv_search_history);
                                textView.setText(hotSearch);

                                List<News> finalNewsList = newsList;  int position = i;
                                preferences= getSharedPreferences("user1", Context.MODE_PRIVATE);
                                String username=preferences.getString("username","");
                                String password=preferences.getString("password","");
                                int uid=preferences.getInt("uid",0);

                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //跳转界面
                                        saveSearchRecord(hotSearch);
                                        if(!username.equals("") && !password.equals("")){
                                            setHistory(finalNewsList.get(position),uid);
                                        }
                                        Intent intent = new Intent(SearchActivity.this, TestActivity.class);
                                        intent.putExtra("url", finalNewsList.get(position).getUrl());
                                        intent.putExtra("title", hotSearch);
                                        intent.putExtra("uniquekey",finalNewsList.get(position).getUniquekey());
                                        intent.putExtra("image1",finalNewsList.get(position).getThumbnail_pic_s());
                                        intent.putExtra("image2",finalNewsList.get(position).getThumbnail_pic_s02());
                                        intent.putExtra("image3",finalNewsList.get(position).getThumbnail_pic_s03());
                                        intent.putExtra("author",finalNewsList.get(position).getAuthor_name());
                                        intent.putExtra("date",finalNewsList.get(position).getDate());
                                        startActivity(intent);
                                    }
                                });
                                mHotSearch.addView(linearLayout);
                            }
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(SearchActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }

    public void setHistory(News news,int uid){
        News1 news1=new News1(news.getTitle(),news.getDate(),news.getAuthor_name(),news.getUrl(),news.getThumbnail_pic_s(),news.getThumbnail_pic_s02(),news.getThumbnail_pic_s03());
        news1.setUid(uid);
        news1.setUniquekey(news.getUniquekey());
        HistoryDBManager.initHistoryDB(SearchActivity.this);
        HistoryDBManager.SaveHistoryList(news1);
    }

    }


