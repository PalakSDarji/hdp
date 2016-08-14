package com.hadippa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Palak on 13-08-2016.
 */

public class FilterModel {

    @SerializedName("filterName")
    private String filterName;
    @SerializedName("color")
    private int color;
    @SerializedName("checked")
    private boolean checked;

    @Override
    public String toString() {
        return "FilterModel{" +
                "filterName='" + filterName + '\'' +
                ", color=" + color +
                ", checked=" + checked +
                '}';
    }

    public FilterModel(String filterName, int color, boolean checked) {
        this.filterName = filterName;
        this.color = color;
        this.checked = checked;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
