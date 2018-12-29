package com.nullifier.expandgridview.view.entity;

/**
 * Created by nullifier on 2018/2/1.
 */

public class ExpandSecondLevelData {
    public String tagName;
    public int secondLevelTagIndex;
    public int firstLevelTagIndex;
    public boolean isSelected;

    @Override
    public String toString() {
        return "ExpandSecondLevelData{" +
                "tagName='" + tagName + '\'' +
                ", secondLevelTagIndex=" + secondLevelTagIndex +
                ", firstLevelTagIndex=" + firstLevelTagIndex +
                ", isSelected=" + isSelected +
                '}';
    }
}
