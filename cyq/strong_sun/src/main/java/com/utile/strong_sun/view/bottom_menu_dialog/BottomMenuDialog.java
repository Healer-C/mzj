package com.utile.strong_sun.view.bottom_menu_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utile.strong_sun.R;
import com.utile.strong_sun.utiles.DividerItemDecoration;
import com.utile.strong_sun.view.bottom_menu_dialog.listener.DialogItemListener;
import com.utile.strong_sun.view.bottom_menu_dialog.listener.ListItemClickListener;

import java.io.Serializable;

/**
 * Created by soul on 2016/11/10.
 */

public class BottomMenuDialog extends Dialog {

    private Context context;
    private Object[] array;

    private boolean canCancel = true;
    private boolean shadow = true;
    private boolean firstCanChoose = true;

    private DialogItemListener mListener;

    DialogAdapter adapter;
    BottomMenuDialog dialog;

    public BottomMenuDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BottomMenuDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public BottomMenuDialog(Context context, boolean firstCanChoose) {
        super(context);
        this.context = context;
        this.firstCanChoose = firstCanChoose;
    }

    public BottomMenuDialog setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
        return this;
    }

    public BottomMenuDialog setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    public BottomMenuDialog setData(Object[] array) {
        this.array = array;
        return this;
    }

    public BottomMenuDialog setOnItemClickListener(DialogItemListener listener) {
        this.mListener = listener;
        return this;
    }

    public BottomMenuDialog build() {
        dialog = new BottomMenuDialog(
                context, shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.Animation_Bottom_Rising);

        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.BOTTOM);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_menu, null);
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (array == null)
            throw new NullPointerException("array为空");
        else {
            RecyclerView recyclerView = view.findViewById(R.id.list);
            recyclerView.setHasFixedSize(true);
            //设置布局管理器
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            //添加分割线
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.line_divider);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, drawable, 1));

            adapter = new DialogAdapter(context, array);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new ListItemClickListener() {
                @Override
                public void onItemClick(View view, int position, Serializable data) {

                    if (position != 0 || firstCanChoose) {
                        if (mListener != null) {
                            mListener.onItemClick((String) data);
                            dialog.dismiss();
                        }
                    }

                }
            });
        }

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(canCancel);
        dialog.setCancelable(canCancel);
        return dialog;
    }
}
