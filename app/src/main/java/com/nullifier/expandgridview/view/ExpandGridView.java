package com.nullifier.expandgridview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;


import com.nullifier.expandgridview.view.entity.ExpandFirstLevelData;
import com.nullifier.expandgridview.view.entity.ExpandSecondLevelData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nullifier on 2018/1/31.
 * ExpandGridView自定义组件
 */

public class ExpandGridView extends LinearLayout {
    private List<ExpandFirstLevelData> mListData;
    private String Tag = "ExpandGridView";
    private int mColumnNum = 3;//每行多少个
    private Context mContext;
    private int currentSelectFirstLevelIndex = -1;
    private List<FirstLevelRelativeLayout> firstLevelViewRelativeLayoutList = new ArrayList<FirstLevelRelativeLayout>();
    private int mLeft;
    private int mRight;
    private int mSecondLevelNumCloum = 3;
    private int mSecondLevelCloumSpace;
    private OnSecondLevelItemClickedListener mOnSecondLevelItemClickedListener;
    private int mSecondLevelGridViewPadding;
    private OnFirstLevelItemClickedListener mOnFirstLevelItemClickedListener;
    private String mSelectedColor = "#ff552e";
    private String mUnSelectedColor = "#666666";
    private String mSecondSelectedColor = "#ff552e";
    private String mSecondUnSelectedColor = "#666666";


    public ExpandGridView(Context context) {
        super(context);
        init(context);
    }

    public ExpandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpandGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    //设置数据源
    public void setListData(List<ExpandFirstLevelData> listData) {
        this.mListData = listData;
        fillFirstItem();

    }

    //填充第一级条目
    private void fillFirstItem() {
        //移除所有的子View
        removeAllViews();
        if (mListData != null && mListData.size() > 0) {
            //计算出一共需要多少行
            int row = mListData.size() / mColumnNum;
            if (mListData.size() % mColumnNum > 0) {
                row += 1;
            }
            for (int i = 0; i < row; i++) {
                final FirstLevelRelativeLayout firstLevelRelativeLayout = new FirstLevelRelativeLayout(mContext);
                firstLevelRelativeLayout.setPadding(0, mLeft, 0, mRight);
                firstLevelRelativeLayout.setNumCloum(mSecondLevelNumCloum);
                firstLevelRelativeLayout.setItemSpace(mLeft, mRight);

                firstLevelRelativeLayout.setSecondLevelClomunSpace(mSecondLevelCloumSpace);
                firstLevelRelativeLayout.setSecondLevelGridViewPadding(mSecondLevelGridViewPadding);
                //将第一级条目add到这个Linerlayout上
                addView(firstLevelRelativeLayout);

                firstLevelRelativeLayout.setFirstLevelTextViewColor(mSelectedColor, mUnSelectedColor);
                firstLevelRelativeLayout.setSecondLevelTextViewColor(mSecondSelectedColor, mSecondUnSelectedColor);
                firstLevelRelativeLayout.setData(i, mColumnNum, mListData);
                firstLevelRelativeLayout.setOnFirstItemClickListener(new FirstLevelRelativeLayout.OnItemClickListener() {
                    @Override
                    public void onFirstItemClick(ExpandFirstLevelData expandFirstLevelData, View view) {
                        closeAllItem();
                        if (expandFirstLevelData.tagIndex == currentSelectFirstLevelIndex) {
                            currentSelectFirstLevelIndex = -1;
                        } else {
                            firstLevelRelativeLayout.openGridView(expandFirstLevelData, view);
                            currentSelectFirstLevelIndex = expandFirstLevelData.tagIndex;
                        }
                        if (mOnFirstLevelItemClickedListener != null) {
                            mOnFirstLevelItemClickedListener.onOnFirstLevelItemClickedListener(expandFirstLevelData);
                        }
                    }

                    @Override
                    public void onSecondItemClick(ExpandSecondLevelData expandSecondLevelData) {
                        if (mOnSecondLevelItemClickedListener != null) {
                            mOnSecondLevelItemClickedListener.onSecondLevelItemClickedListener(expandSecondLevelData);
                        }

                    }
                });

                firstLevelViewRelativeLayoutList.add(firstLevelRelativeLayout);
            }

        } else {
            Log.e(Tag, "fillFirstItem()=>mListData==null");
        }
    }


    /**
     * 设置一级标题一行多少个
     *
     * @param columnNum
     */
    public void setColumnNum(int columnNum) {
        this.mColumnNum = columnNum;
    }

    private void closeAllItem() {
        for (int i = 0; i < firstLevelViewRelativeLayoutList.size(); i++) {
            firstLevelViewRelativeLayoutList.get(i).closeGridView();
        }
    }


    /**
     * 设置一级标题左右间距
     *
     * @param left
     * @param right
     */
    public void setFirstLevelClomunSpace(int left, int right) {
        this.mLeft = left;
        this.mRight = right;
    }

    /**
     * 设置二级条目之间的间距
     *
     * @param numCloumSpace
     */
    public void setSecondLevelClomunSpace(int numCloumSpace) {
        this.mSecondLevelCloumSpace = numCloumSpace;
    }

    /**
     * 设置二级gridView左右距离屏幕的间距
     *
     * @param secondLevelGridViewPadding
     */
    public void setSecondLevelGridViewPadding(int secondLevelGridViewPadding) {
        this.mSecondLevelGridViewPadding = secondLevelGridViewPadding;
    }

    /**
     * 设置二级标题一行多少个
     *
     * @param numCloum
     */
    public void setSecondLevelNumCloum(int numCloum) {
        this.mSecondLevelNumCloum = numCloum;
    }


    public interface OnSecondLevelItemClickedListener {
        public void onSecondLevelItemClickedListener(ExpandSecondLevelData expandSecondLevelData);
    }

    public interface OnFirstLevelItemClickedListener {
        public void onOnFirstLevelItemClickedListener(ExpandFirstLevelData expandFirstLevelData);
    }

    /**
     * 设置第二级条目被点击的点击事件
     *
     * @param onSecondLevelItemClickedListener
     */
    public void setOnSecondLevelItemClickedListener(OnSecondLevelItemClickedListener onSecondLevelItemClickedListener) {
        this.mOnSecondLevelItemClickedListener = onSecondLevelItemClickedListener;
    }

    public void setOnFirstLevelItemClickedListener(OnFirstLevelItemClickedListener onFirstLevelItemClickedListener) {
        this.mOnFirstLevelItemClickedListener = onFirstLevelItemClickedListener;
    }

    /**
     * 清除二级标题的按钮状态
     */
    public void clearSecondLevelButtonState() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView instanceof FirstLevelRelativeLayout) {
                //把这些子View都拿出来
                ((FirstLevelRelativeLayout) childView).clearSecondLevelButtonState();
            }
        }
    }

    public void clearFirstLevelButtonState() {
        currentSelectFirstLevelIndex = -1;
    }


    public void setFirstLevelTextViewColor(String selectedColor, String unSelectedColor) {
        this.mSelectedColor = selectedColor;
        this.mUnSelectedColor = unSelectedColor;
    }


    public void setSecondLevelTextViewColor(String selectedColor, String unSelectedColor) {
        this.mSecondSelectedColor = selectedColor;
        this.mSecondUnSelectedColor = unSelectedColor;
    }

}
