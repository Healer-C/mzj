package com.utile.strong_sun.view.bottom_menu_dialog.listener;

import android.view.View;

import java.io.Serializable;

/**
 * Created by soul on 2016/11/10.
 */

public interface ListItemClickListener {
    /**
     *
     * @param view
     * @param position
     * @param data
     */
    public void onItemClick(View view, int position, Serializable data);
}
