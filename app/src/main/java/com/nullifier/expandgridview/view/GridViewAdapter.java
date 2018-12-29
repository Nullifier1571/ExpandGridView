package com.nullifier.expandgridview.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nullifier.expandgridview.R;
import com.nullifier.expandgridview.view.entity.ExpandSecondLevelData;

import java.util.List;

/**
 * Created by nullifier on 2018/1/31.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<ExpandSecondLevelData> mExpandSecondLevelDataList;
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private String mSecondSelectedColor;
    private String mSecondUnSelectedColor;

    public GridViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mExpandSecondLevelDataList != null) {
            return mExpandSecondLevelDataList.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int i) {
        if (mExpandSecondLevelDataList != null) {
            return mExpandSecondLevelDataList.get(i);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        View rootView = View.inflate(mContext, R.layout.second_level_item_view, null);
        TextView tv_second_item = (TextView) rootView.findViewById(R.id.tv_second_item);
        if (mExpandSecondLevelDataList != null && mExpandSecondLevelDataList.size() != 0) {

            ExpandSecondLevelData expandSecondLevelData = mExpandSecondLevelDataList.get(i);
            tv_second_item.setText(expandSecondLevelData.tagName);
            rootView.setTag(expandSecondLevelData);
            if (expandSecondLevelData.isSelected) {
                tv_second_item.setBackgroundResource(R.drawable.shape_react_origin);
                tv_second_item.setTextColor(Color.parseColor(mSecondSelectedColor));
            } else {
                tv_second_item.setBackgroundResource(R.drawable.shape_react_secondlevel_gay);
                tv_second_item.setTextColor(Color.parseColor(mSecondUnSelectedColor));
            }
        }

        return rootView;
    }

    public void setData(List<ExpandSecondLevelData> expandSecondLevelDataList) {
        this.mExpandSecondLevelDataList = expandSecondLevelDataList;
    }


    public void clearData() {
        mExpandSecondLevelDataList = null;
    }

    public int getExpandSecondLevelDataListSize() {
        if (mExpandSecondLevelDataList == null) {
            return 0;
        }
        return mExpandSecondLevelDataList.size();
    }

    public List<ExpandSecondLevelData> getExpandSecondLevelDataList() {

        return mExpandSecondLevelDataList;
    }

    public void setSecondLevelItemWidthAndHeight(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public void setSecondLevelTextViewColor(String selectedColor, String unSelectedColor) {
        this.mSecondSelectedColor = selectedColor;
        this.mSecondUnSelectedColor = unSelectedColor;
    }

}
