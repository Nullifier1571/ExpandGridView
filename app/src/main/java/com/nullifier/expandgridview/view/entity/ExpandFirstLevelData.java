package com.nullifier.expandgridview.view.entity;

import java.util.List;

/**
 * Created by nullifier on 2018/2/1.
 */

public class ExpandFirstLevelData {
    public String tagName;
    public int tagIndex;
    public boolean isSelected;
    public List<ExpandSecondLevelData> secondDataLevelList;

    @Override
    public String toString() {
        return "ExpandFirstLevelData{" +
                "tagName='" + tagName + '\'' +
                ", tagIndex=" + tagIndex +
                ", isSelected=" + isSelected +
                ", secondDataLevelList=" + secondDataLevelList +
                '}';
    }
}
