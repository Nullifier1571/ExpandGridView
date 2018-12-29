package com.nullifier.expandgridview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nullifier.expandgridview.R;


/**
 * Created by nullifier on 2018/2/2.
 */

public class SecondLevelItemView extends RelativeLayout {
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private TextView tv_second_item;

    public SecondLevelItemView(Context context) {
        super(context);
        init(context);
    }

    public SecondLevelItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SecondLevelItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View rootView = View.inflate(context, R.layout.second_level_item_view, this);
        tv_second_item = (TextView) rootView.findViewById(R.id.tv_second_item);

    }

    public void setSecondLevelItemWidthAndHeight(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }
}
