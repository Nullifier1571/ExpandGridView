package com.nullifier.expandgridview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nullifier.expandgridview.R;
import com.nullifier.expandgridview.view.entity.ExpandFirstLevelData;
import com.nullifier.expandgridview.view.entity.ExpandSecondLevelData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nullifier on 2018/1/31.
 * 第一级的组件
 */

public class FirstLevelRelativeLayout extends RelativeLayout implements AdapterView.OnItemClickListener {
    private LinearLayout mColumnDataLinerLayout;
    private GridView mSecondLevelGridView;
    private Context mContext;
    private GridViewAdapter mGridViewAdapter;
    private List<ExpandFirstLevelData> mExpandFirstLevelDataList = new ArrayList<ExpandFirstLevelData>();
    private OnItemClickListener mOnItemClickListener;
    private List<FirstLevelItemView> mTextViewList = new ArrayList<FirstLevelItemView>();
    private int mLeft;
    private int mRight;
    private int mNumCloum = 3;
    private String mSelectedColor;
    private String mUnSelectedColor;
    private String mSecondSelectedColor;
    private String mSecondUnSelectedColor;


    public FirstLevelRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public FirstLevelRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FirstLevelRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View myExpandItem = View.inflate(getContext(), R.layout.expand_item, this);
        mColumnDataLinerLayout = (LinearLayout) myExpandItem.findViewById(R.id.ll_column_data);
        mSecondLevelGridView = (GridView) myExpandItem.findViewById(R.id.gv_second_item);
        mSecondLevelGridView.setOnItemClickListener(this);
    }

    /**
     * @param row        该条目是在第几行
     * @param mColumnNum //一行是多少个元素
     * @param mListData  //数据
     */
    public void setData(int row, int mColumnNum, List<ExpandFirstLevelData> mListData) {

        mExpandFirstLevelDataList.clear();

        for (int i = 0; i < mColumnNum; i++) {
            int index = row * mColumnNum + i;
            if (index < mListData.size()) {
                //如果最后一行不足clom数就不要添加了
                ExpandFirstLevelData expandFirstLevelData = mListData.get(index);
                //把角标也扔进去
                expandFirstLevelData.tagIndex = index;
                //向二级数据添加一级角标和二级角标
                if (expandFirstLevelData.secondDataLevelList != null) {
                    for (int j = 0; j < expandFirstLevelData.secondDataLevelList.size(); j++) {
                        ExpandSecondLevelData expandSecondLevelData = expandFirstLevelData.secondDataLevelList.get(j);
                        expandSecondLevelData.firstLevelTagIndex = index;
                        expandSecondLevelData.secondLevelTagIndex = j;
                        if (expandSecondLevelData.isSelected && mOnItemClickListener != null) {
                            mOnItemClickListener.onSecondItemClick(expandSecondLevelData);
                        }
                    }
                }
                mExpandFirstLevelDataList.add(expandFirstLevelData);
            }
        }
        //填充条目上边的组件
        fillItemData();
        setGridViewHeight();
    }

    /**
     * 设置gridView的高度
     */
    private void setGridViewHeight() {
        // 获取ListView对应的Adapter
        if (mGridViewAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int len = mGridViewAdapter.getExpandSecondLevelDataListSize() / mNumCloum;
        if (mGridViewAdapter.getExpandSecondLevelDataListSize() % mNumCloum != 0) {
            len += 1;
        }

        View listItem = mGridViewAdapter.getView(0, null, mSecondLevelGridView);
        listItem.measure(0, 0); // 计算子项View 的宽高
        totalHeight = listItem.getMeasuredHeight() * len; // 统计总高度
        if (len - 1 > 0) {
            //需要加上行边距，这里加了5个px的误差值
            totalHeight += (mSecondLevelGridView.getHorizontalSpacing() + 5) * (len - 1);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSecondLevelGridView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, totalHeight);
        } else {
            layoutParams.height = totalHeight;
        }
        mSecondLevelGridView.setLayoutParams(layoutParams);
    }


    //填充条目，上边第一条是Linerlayout，下边是gridView
    private void fillItemData() {
        mGridViewAdapter = new GridViewAdapter(mContext);
        mSecondLevelGridView.setAdapter(mGridViewAdapter);

        for (int i = 0; i < mExpandFirstLevelDataList.size(); i++) {
            final ExpandFirstLevelData expandFirstLevelData = mExpandFirstLevelDataList.get(i);
            FirstLevelItemView textView = new FirstLevelItemView(mContext);
            textView.setText(expandFirstLevelData.tagName);
            textView.setPadding(mLeft, 0, mRight, 0);
            textView.setFirstLevelTextViewColor(mSelectedColor, mUnSelectedColor);
            textView.setSelectState(expandFirstLevelData.isSelected);

            mColumnDataLinerLayout.addView(textView);
            textView.setTag(expandFirstLevelData);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExpandFirstLevelData expandFirstLevelData = (ExpandFirstLevelData) view.getTag();
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onFirstItemClick(expandFirstLevelData, view);
                    }
                }
            });
            if (expandFirstLevelData.isSelected && mOnItemClickListener != null) {
                mOnItemClickListener.onFirstItemClick(expandFirstLevelData, textView);
            }
            mTextViewList.add(textView);
        }

        //针对不满一行的单独处理，计算出少几个View就单独在add进去几个空View
        int cloumDiff = 0;
        if (mExpandFirstLevelDataList.size() < mNumCloum) {
            cloumDiff = mNumCloum - mExpandFirstLevelDataList.size();
        }

        for (int i = 0; i < cloumDiff; i++) {
            FirstLevelItemView textView = new FirstLevelItemView(mContext);
            textView.setPadding(mLeft, 0, mRight, 0);
            textView.fillBlockView();
            mColumnDataLinerLayout.addView(textView);
        }
        delFirstLevelItemHeight(false);
        mGridViewAdapter.setSecondLevelTextViewColor(mSecondSelectedColor, mSecondUnSelectedColor);

    }

    /**
     * 设置二级条目一行多少个
     *
     * @param numCloum
     */
    public void setNumCloum(int numCloum) {
        this.mNumCloum = numCloum;
        mSecondLevelGridView.setNumColumns(numCloum);
    }

    /**
     * 设置二级标题行间距
     *
     * @param numCloumSpace
     */
    public void setSecondLevelClomunSpace(int numCloumSpace) {
        mSecondLevelGridView.setHorizontalSpacing(numCloumSpace);
    }

    /**
     * 设置二级标题左右距离屏幕的距离
     *
     * @param secondLevelGridViewPadding
     */
    public void setSecondLevelGridViewPadding(int secondLevelGridViewPadding) {
        mSecondLevelGridView.setPadding(secondLevelGridViewPadding, 0, secondLevelGridViewPadding, 0);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mOnItemClickListener != null) {
            ExpandSecondLevelData expandSecondLevelData = (ExpandSecondLevelData) view.getTag();
            if (expandSecondLevelData != null) {
                //将二级标题的是否选择状态置反
                expandSecondLevelData.isSelected = !expandSecondLevelData.isSelected;
                //把除了点击的这个按钮的其他元素值都整成false，保证一次只有一个二级元素选中
                List<ExpandSecondLevelData> expandSecondLevelDataList = mGridViewAdapter.getExpandSecondLevelDataList();
                if (expandSecondLevelDataList != null) {
                    for (int j = 0; j < expandSecondLevelDataList.size(); j++) {
                        ExpandSecondLevelData expandSecondLevelData1 = expandSecondLevelDataList.get(j);
                        if (expandSecondLevelData1.secondLevelTagIndex != expandSecondLevelData.secondLevelTagIndex) {
                            expandSecondLevelData1.isSelected = false;
                        }
                    }
                }

                mGridViewAdapter.notifyDataSetChanged();
                mOnItemClickListener.onSecondItemClick(expandSecondLevelData);
            }
        }
    }

    public void setOnFirstItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onFirstItemClick(ExpandFirstLevelData expandFirstLevelData, View view);

        void onSecondItemClick(ExpandSecondLevelData expandSecondLevelData);
    }

    /**
     * 关闭二级条目
     */
    public void closeGridView() {
        //清空状态
        for (int i = 0; i < mTextViewList.size(); i++) {
            mTextViewList.get(i).setSelectState(false);
        }
        //清空数据源
        if (mGridViewAdapter != null) {
            delFirstLevelItemHeight(false);
            mGridViewAdapter.clearData();
            mGridViewAdapter.notifyDataSetChanged();
            setGridViewHeight();
        }


    }

    /**
     * 打开二级条目
     *
     * @param expandFirstLevelData
     * @param view
     */
    public void openGridView(ExpandFirstLevelData expandFirstLevelData, View view) {
        if (mGridViewAdapter != null) {
            delFirstLevelItemHeight(true);
            mGridViewAdapter.setData(expandFirstLevelData.secondDataLevelList);
            mGridViewAdapter.notifyDataSetChanged();
            ((FirstLevelItemView) view).setSelectState(true);
            setGridViewHeight();
        }

    }


    private void delFirstLevelItemHeight(boolean isOpen) {
        LinearLayout.LayoutParams layoutParams;
        if (isOpen) {
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dip2px(mContext, 44.5f));
        } else {
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dip2px(mContext, 34));
        }
        for (int i = 0; i < mColumnDataLinerLayout.getChildCount(); i++) {
            mColumnDataLinerLayout.getChildAt(i).setLayoutParams(layoutParams);
        }

    }

    public void setSecondLevelTextViewColor(String selectedColor, String unSelectedColor) {
        this.mSecondSelectedColor = selectedColor;
        this.mSecondUnSelectedColor = unSelectedColor;
    }


    /**
     * 设置一级标题的textview的左右边距
     *
     * @param left
     * @param right
     */
    public void setItemSpace(int left, int right) {
        this.mLeft = left;
        this.mRight = right;
    }

    //将二级页签选择状态清空
    public void clearSecondLevelButtonState() {
        List<ExpandSecondLevelData> expandSecondLevelDataList = mGridViewAdapter.getExpandSecondLevelDataList();
        if (expandSecondLevelDataList != null) {
            for (int j = 0; j < expandSecondLevelDataList.size(); j++) {
                ExpandSecondLevelData expandSecondLevelData1 = expandSecondLevelDataList.get(j);
                expandSecondLevelData1.isSelected = false;
            }
        }
        mGridViewAdapter.notifyDataSetChanged();
    }


    public void setFirstLevelTextViewColor(String selectedColor, String unSelectedColor) {
        this.mSelectedColor = selectedColor;
        this.mUnSelectedColor = unSelectedColor;
    }

    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
