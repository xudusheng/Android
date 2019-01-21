package cn.com.ihappy.ihappy.module.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import cn.com.ihappy.ihappy.beans.news.NewsBean;

public class NewsAdapter extends RecyclerView.Adapter {
    private List<NewsBean>newsBeanList;
    private Context mContext;

    NewsAdapter(Context mContext, List<NewsBean> newsBeanList) {
        this.mContext = mContext;
        this.newsBeanList = newsBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
