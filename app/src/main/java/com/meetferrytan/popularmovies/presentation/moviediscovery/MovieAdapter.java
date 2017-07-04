package com.meetferrytan.popularmovies.presentation.moviediscovery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.meetferrytan.popularmovies.R;
import com.meetferrytan.popularmovies.data.entity.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ferrytan on 7/4/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEWTYPE_DATA = 0;
    public static final int VIEWTYPE_LOAD_MORE = 1;
    private Context mContext;
    private List<Movie> mData;
    private ItemClickListener mListener;
    private boolean loadMoreEnabled;

    public MovieAdapter(@NonNull Context context, @NonNull List<Movie> data, @NonNull ItemClickListener listener) {
        this.mContext = context;
        this.mData = data;
        this.mListener = listener;
        setLoadMoreEnabled(true);
    }

    public boolean isLoadMoreEnabled() {
        return loadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.loadMoreEnabled = loadMoreEnabled;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEWTYPE_DATA:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
                return new MovieViewHolder(view);
            case VIEWTYPE_LOAD_MORE:
                int padding = mContext.getResources().getDimensionPixelSize(R.dimen.padding);
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(0, padding, 0, padding);
                return new LoadMoreViewHolder(textView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEWTYPE_DATA:
                ((MovieViewHolder)holder).bindView(mData.get(position));
                break;
            case VIEWTYPE_LOAD_MORE:
                ((LoadMoreViewHolder)holder).bindView(loadMoreEnabled?R.string.load_more_enabled:R.string.load_more_disabled);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position<mData.size())
            return VIEWTYPE_DATA;
        else
            return VIEWTYPE_LOAD_MORE;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        ImageView mImgPoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(final Movie movie){
            Glide.with(mContext)
                    .load(movie.getPosterImageFullUrl())
                    .placeholder(R.drawable.placeholder)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(mImgPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, movie);
                }
            });
        }
    }

    private class LoadMoreViewHolder extends RecyclerView.ViewHolder{
        LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

        void bindView(int messageRes){
            TextView textView = (TextView)itemView;
            textView.setText(messageRes);
            switch (messageRes) {
                case R.string.load_more_enabled:
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mListener.onLoadMoreDataClick();
                        }
                    });
                    break;
                case R.string.load_more_disabled:
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    textView.setOnClickListener(null);
                    break;
            }
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, Movie movie);
        void onLoadMoreDataClick();
    }

    public void addItems(List<Movie> newItems){
        mData.addAll(newItems);
        notifyItemRangeChanged(mData.size(), newItems.size() + 1);
    }
}
