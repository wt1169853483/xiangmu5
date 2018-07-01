package com.example.mytex.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mytex.R;
import com.example.mytex.bean.NewBean;
import com.example.mytex.view.MainActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * Created by BoyLucky on 2018/5/29.
 */

public class XrlvAdapter extends XRecyclerView.Adapter {
    private Context context;
    private List<NewBean.DataBean> data;
    private final int TYPE = 1;
    private final int TYPE2 = 2;
    private XrlvItemClicked xrlvItemClicked;

    public void setXrlvItemClicked(XrlvItemClicked xrlvItemClicked){
        this.xrlvItemClicked = xrlvItemClicked;
    }

    public XrlvAdapter(Context context, List<NewBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    public  void loadMore(List<NewBean.DataBean> data1){
        if (data!=null){
            data.addAll(data1);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.xrl_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }else if (viewType == TYPE2){
            View view = LayoutInflater.from(context).inflate(R.layout.xrl_item2, parent, false);
            ViewHolder2 viewHolder2 = new ViewHolder2(view);
            return viewHolder2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder2){
            ((ViewHolder2) holder).title.setText(data.get(position).getTopic());
            Glide.with(context).load(data.get(position).getMiniimg().get(0).getSrc()).into(((ViewHolder2) holder).img1);
            ((ViewHolder2) holder).ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    xrlvItemClicked.onItemLongLicked(v,position);
                    return true;
                }
            });
        }else if (holder instanceof ViewHolder){
            ((ViewHolder) holder).title.setText(data.get(position).getTopic());
            if (data.get(position).getMiniimg()!=null&&data.get(position).getMiniimg().size()>0){
                if (data.get(position).getMiniimg().size() == 1){
                    Glide.with(context).load(data.get(position).getMiniimg().get(0).getSrc()).into(((ViewHolder) holder).img1);
                    Glide.with(context).load(data.get(position).getMiniimg().get(0).getSrc()).into(((ViewHolder) holder).img2);
                    Glide.with(context).load(data.get(position).getMiniimg().get(0).getSrc()).into(((ViewHolder) holder).img3);
                }else if (data.get(position).getMiniimg().size() == 2){
                    Glide.with(context).load(data.get(position).getMiniimg().get(0).getSrc()).into(((ViewHolder) holder).img1);
                    Glide.with(context).load(data.get(position).getMiniimg().get(1).getSrc()).into(((ViewHolder) holder).img2);
                    Glide.with(context).load(data.get(position).getMiniimg().get(0).getSrc()).into(((ViewHolder) holder).img3);
                }else if (data.get(position).getMiniimg().size() == 3){
                    Glide.with(context).load(data.get(position).getMiniimg().get(0).getSrc()).into(((ViewHolder) holder).img1);
                    Glide.with(context).load(data.get(position).getMiniimg().get(1).getSrc()).into(((ViewHolder) holder).img2);
                    Glide.with(context).load(data.get(position).getMiniimg().get(2).getSrc()).into(((ViewHolder) holder).img3);
                }
            }
            ((ViewHolder) holder).ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    xrlvItemClicked.onItemLongLicked(v,position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3 == 2 ? TYPE2 : TYPE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
            private TextView title;
            private ImageView img1,img2,img3;
            private LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView img1;
        private LinearLayout ll;
        public ViewHolder2(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title1);
            img1 = itemView.findViewById(R.id.img1);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
