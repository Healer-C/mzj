package com.utile.strong_sun.view.bottom_menu_dialog;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utile.strong_sun.R;
import com.utile.strong_sun.view.bottom_menu_dialog.listener.ListItemClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by soul on 2016/11/10.
 */

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.MyViewHolder> {
    /**
     * top
     */
    private final int TYPE_0 = 0;
    /**
     * center
     */
    private final int TYPE_1 = 1;
    /**
     * bottom
     */
    private final int TYPE_2 = 2;

    private Context context;
    private Object[] array;
    private List<Object> list;

    private ListItemClickListener mListener;

    public DialogAdapter(Context context, Object[] array) {
        this.context = context;
        this.array = array;
        list = Arrays.asList(array);
    }

    @Override
    public DialogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = null;
        switch (viewType) {
            case TYPE_0:
                holder = new MyViewHolder(LayoutInflater.from(
                        context).inflate(R.layout.item_dialog_meun_top, parent,
                        false));
                break;
            case TYPE_1:
                holder = new MyViewHolder(LayoutInflater.from(
                        context).inflate(R.layout.item_dialog_meun_center, parent,
                        false));
                break;
            case TYPE_2:
                holder = new MyViewHolder(LayoutInflater.from(
                        context).inflate(R.layout.item_dialog_meun_bottom, parent,
                        false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(DialogAdapter.MyViewHolder holder, int position) {

        holder.tv_item.setText(Html.fromHtml((String) list.get(position)));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_0;
        else if (position == array.length - 1)
            return TYPE_2;
        else return TYPE_1;
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(ListItemClickListener listener) {
        this.mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null)
                mListener.onItemClick(view, getAdapterPosition(), (String) list.get(getAdapterPosition()));
        }
    }
}
