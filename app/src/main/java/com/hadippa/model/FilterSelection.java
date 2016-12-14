package com.hadippa.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by HP on 14-12-2016.
 */

public class FilterSelection implements Serializable {

    public int activityType;
    public String activityId;

    public FilterSelection() {
    }

    public FilterSelection(int activityType, String activityId) {
        this.activityType = activityType;
        this.activityId = activityId;
    }


}
