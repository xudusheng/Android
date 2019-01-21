package cn.com.ihappy.ihappy.module.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.ihappy.ihappy.R;
import cn.com.ihappy.ihappy.beans.video.HtmlVideoBean;

public class VideoAdapter extends BaseAdapter {
    List<HtmlVideoBean> videoList = new ArrayList<>();
    MainVideoFragment mContext;

    public VideoAdapter(MainVideoFragment context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {

        return this.videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        LayoutInflater inflater = this.mContext.getLayoutInflater();

        view = inflater.inflate(R.layout.item_main_video, null);

        HtmlVideoBean htmlVideoBean = this.videoList.get(position);
        TextView title = view.findViewById(R.id.item_video_title);
        title.setText(htmlVideoBean.getName());

        ImageView pic = view.findViewById(R.id.item_video_image);
        Glide.with(mContext)
                .load(htmlVideoBean.getImageurl())
                .into(pic);

        return view;
    }
}
