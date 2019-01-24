package cn.com.ihappy.ihappy.module.video;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.beans.meizi.MeituBean;
import cn.com.ihappy.ihappy.beans.video.HtmlVideoBean;
import cn.com.ihappy.ihappy.module.meizi.ImageAdapter;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoItemHolder>{
    List<HtmlVideoBean> videoList = new ArrayList<>();
    Context mContext;
    private static OnItemClickListener mOnItemClickListener;


    public VideoAdapter(Context context) {
        this.mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public VideoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //加载item 布局文件
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_video, parent, false);
        return new VideoItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoItemHolder viewHolder, int position) {

        //用于绑定事件
        viewHolder.position = position;
        HtmlVideoBean htmlVideoBean = this.videoList.get(position);
        Glide.with(mContext)
                .load(htmlVideoBean.getImageurl())
                .into(viewHolder.imageView);
        viewHolder.textView.setText(htmlVideoBean.getName());
    }

    @Override
    public int getItemCount() {
        return this.videoList.size();
    }


    static class VideoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        int position;

        public VideoItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_video_image);
            textView = itemView.findViewById(R.id.item_video_title);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(imageView, this.position);
            }
        }
    }


    // 自定义点击事件
    public void setOnItemClickListener(VideoAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
