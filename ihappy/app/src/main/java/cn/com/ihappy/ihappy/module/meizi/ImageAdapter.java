package cn.com.ihappy.ihappy.module.meizi;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.beans.meizi.MeituBean;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageItemHolder> {

    private ArrayList<MeituBean> mData;
    private Context mContext;

    public ImageAdapter(ArrayList<MeituBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ImageItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        //加载item 布局文件
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image_list, parent, false);
        return new ImageItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageItemHolder imageItemHolder, int position) {
        MeituBean meituBean = this.mData.get(position);
        Glide.with(mContext).load(meituBean.image_src).into(imageItemHolder.img_icon);
    }


    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    static class ImageItemHolder extends RecyclerView.ViewHolder {
        ImageView img_icon;

        public ImageItemHolder(@NonNull View itemView) {
            super(itemView);
            img_icon = itemView.findViewById(R.id.image_item);
        }
    }
}
