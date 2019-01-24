package cn.com.ihappy.ihappy.module.meizi;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.beans.meizi.MeituBean;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageItemHolder> {

    private ArrayList<MeituBean> mData;
    private Context mContext;
    private static OnItemClickListener mOnItemClickListener;


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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ImageItemHolder imageItemHolder, int position) {

        //用于绑定事件
        imageItemHolder.position = position;
        MeituBean meituBean = this.mData.get(position);
        Glide.with(mContext)
                .load(meituBean.image_src)
                .into(imageItemHolder.img_icon);
    }


    @Override
    public int getItemCount() {
        return this.mData.size();
    }


    static class ImageItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_icon;
        int position;

        public ImageItemHolder(@NonNull View itemView) {
            super(itemView);
            img_icon = itemView.findViewById(R.id.image_item);
            img_icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(img_icon, this.position);
            }
        }
    }

    // 自定义点击事件
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
