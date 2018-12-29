package com.nullifier.expandgridview.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nullifier.expandgridview.R;


/**
 * Created by nullifier on 2018/2/1.
 * 一级标题的item
 */

public class FirstLevelItemView extends RelativeLayout {
    private Context mContext;
    private TextView mLableTextView;
    private ImageView mIndecatorImageView;
    private String mSelectedColor = "#ff552e";
    private String mUnSelectedColor = "#666666";


    public FirstLevelItemView(Context context) {
        super(context);
        init(context);
    }

    public FirstLevelItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FirstLevelItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View rootView = View.inflate(mContext, R.layout.first_level_item_view, this);
        mLableTextView = (TextView) rootView.findViewById(R.id.tv_item);
        mIndecatorImageView = (ImageView) rootView.findViewById(R.id.iv_indecator);
    }

    public void setText(String text) {
        mLableTextView.setText(text + " +");
    }

    /**
     * 设置一级标题的选择状态
     *
     * @param isSelected
     */
    public void setSelectState(boolean isSelected) {
        if (isSelected) {
            mIndecatorImageView.setVisibility(VISIBLE);
            mLableTextView.setTextColor(Color.parseColor(mSelectedColor));
        } else {
            mIndecatorImageView.setVisibility(GONE);
            mLableTextView.setTextColor(Color.parseColor(mUnSelectedColor));
        }
    }

    public void setFirstLevelTextViewColor(String selectedColor, String unSelectedColor) {
        this.mSelectedColor = selectedColor;
        this.mUnSelectedColor = unSelectedColor;
    }

    /**
     * 填充一个空view
     */
    public void fillBlockView() {
        mIndecatorImageView.setVisibility(INVISIBLE);
        mLableTextView.setVisibility(INVISIBLE);
    }
}
