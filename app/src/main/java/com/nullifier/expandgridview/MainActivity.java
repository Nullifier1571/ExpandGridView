package com.nullifier.expandgridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nullifier.expandgridview.view.ExpandGridView;
import com.nullifier.expandgridview.view.entity.ExpandFirstLevelData;
import com.nullifier.expandgridview.view.entity.ExpandSecondLevelData;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ExpandGridView.OnFirstLevelItemClickedListener, ExpandGridView.OnSecondLevelItemClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /********************************基础用法start*****************************************/
        ExpandGridView expandGridView = findViewById(R.id.expand_gridview);

        List<ExpandFirstLevelData> listData = new ArrayList<ExpandFirstLevelData>();

        for (int i = 0; i < 7; i++) {
            ExpandFirstLevelData expandFirstLevelData = new ExpandFirstLevelData();
            expandFirstLevelData.tagIndex = i;
            expandFirstLevelData.tagName = ("一级标题" + i);
            expandFirstLevelData.secondDataLevelList = new ArrayList<ExpandSecondLevelData>();
            for (int j = 0; j < 6; j++) {
                ExpandSecondLevelData expandSecondLevelData = new ExpandSecondLevelData();
                expandSecondLevelData.firstLevelTagIndex = i;
                expandSecondLevelData.secondLevelTagIndex = j;
                expandSecondLevelData.tagName = (i + "二级标题" + j);
                expandFirstLevelData.secondDataLevelList.add(expandSecondLevelData);
            }

            listData.add(expandFirstLevelData);
        }

        //设置一级标题颜色，需要在setListData方法之前调用
        expandGridView.setFirstLevelTextViewColor("#ff552e", "#666666");
        //设置二级标题颜色，需要在setListData方法之前调用
        expandGridView.setSecondLevelTextViewColor("#ff552e", "#666666");
        //设置二级按钮一行几个
        expandGridView.setSecondLevelNumCloum(3);
        //设置一级标题行间距 px
        expandGridView.setFirstLevelClomunSpace(30, 30);
        //设置二级标题上下间距 px
        expandGridView.setSecondLevelClomunSpace(30);
        expandGridView.setSecondLevelGridViewPadding(30);
        //设置数据源
        expandGridView.setListData(listData);
        //设置一级标题点击事件
        expandGridView.setOnFirstLevelItemClickedListener(this);
        //设置二级标题点击事件
        expandGridView.setOnSecondLevelItemClickedListener(this);
        /********************************基础用法end*****************************************/


    }

    @Override
    public void onOnFirstLevelItemClickedListener(ExpandFirstLevelData expandFirstLevelData) {
        Log.e("MainActivity", "onOnFirstLevelItemClickedListener" + expandFirstLevelData);
    }

    @Override
    public void onSecondLevelItemClickedListener(ExpandSecondLevelData expandSecondLevelData) {
        Log.e("MainActivity", "onSecondLevelItemClickedListener" + expandSecondLevelData);
    }
}
