package com.kadirkertis.githubrepos.screens.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.kadirkertis.githubrepos.R;

/**
 * Created by Kadir Kertis on 7.8.2017.
 */

public class ReposItemView extends FrameLayout {

    public ReposItemView(@NonNull Context context) {
        super(context);
        inflate(context, R.layout.list_item_repos,this);
    }
}
