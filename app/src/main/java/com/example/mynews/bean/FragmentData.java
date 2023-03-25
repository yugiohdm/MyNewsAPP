package com.example.mynews.bean;

import androidx.fragment.app.Fragment;

import com.example.mynews.fragment.top.Fragment_automobile;
import com.example.mynews.fragment.top.Fragment_fashion;
import com.example.mynews.fragment.top.Fragment_finance;
import com.example.mynews.fragment.top.Fragment_game;
import com.example.mynews.fragment.top.Fragment_health;
import com.example.mynews.fragment.top.Fragment_inland;
import com.example.mynews.fragment.top.Fragment_international;
import com.example.mynews.fragment.top.Fragment_military;
import com.example.mynews.fragment.top.Fragment_recommend;
import com.example.mynews.fragment.top.Fragment_recreation;
import com.example.mynews.fragment.top.Fragment_sports;
import com.example.mynews.fragment.top.Fragment_technology;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentData  implements Serializable {

    private String title;

    private Fragment fragment;



    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FragmentData(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        return "FragmentData{" +
                "title='" + title + '\'' +
                ", fragment=" + fragment +
                '}';
    }

    public  static List<FragmentData> getFragmentList(){
        List<FragmentData>  fragmentList=new ArrayList<>();

        Fragment_recommend fragment_recommend=new Fragment_recommend();
        Fragment_health fragment_health =new Fragment_health();
        Fragment_sports fragment_sports=new Fragment_sports();
        Fragment_automobile fragment_automobile=new Fragment_automobile();
        Fragment_recreation fragment_recreation=new Fragment_recreation();
        Fragment_technology fragment_technology=new Fragment_technology();
        Fragment_finance fragment_finance=new Fragment_finance();
        Fragment_military fragment_military=new Fragment_military();
        Fragment_game fragment_game=new Fragment_game();
        Fragment_inland fragment_inland=new Fragment_inland();
        Fragment_international fragment_international=new Fragment_international();
        Fragment_fashion fragment_fashion=new Fragment_fashion();

        FragmentData fragmentData1=new FragmentData("推荐",fragment_recommend);
        FragmentData fragmentData2=new FragmentData("健康",fragment_health);
        FragmentData fragmentData3=new FragmentData("体育",fragment_sports);
        FragmentData fragmentData4=new FragmentData("汽车",fragment_automobile);
        FragmentData fragmentData5=new FragmentData("娱乐",fragment_recreation);
        FragmentData fragmentData6=new FragmentData("科技",fragment_technology);
        FragmentData fragmentData7=new FragmentData("财经",fragment_finance);
        FragmentData fragmentData8=new FragmentData("军事",fragment_military);
        FragmentData fragmentData9=new FragmentData("游戏",fragment_game);
        FragmentData fragmentData10=new FragmentData("国内",fragment_inland);
        FragmentData fragmentData11=new FragmentData("国际",fragment_international);
        FragmentData fragmentData12=new FragmentData("时尚",fragment_fashion);

        fragmentList.add(fragmentData1);
        fragmentList.add(fragmentData2);
        fragmentList.add(fragmentData3);
        fragmentList.add(fragmentData4);
        fragmentList.add(fragmentData5);
        fragmentList.add(fragmentData6);
        fragmentList.add(fragmentData7);
        fragmentList.add(fragmentData8);
        fragmentList.add(fragmentData9);
        fragmentList.add(fragmentData10);
        fragmentList.add(fragmentData11);
        fragmentList.add(fragmentData12);

        return  fragmentList;
    }
}
