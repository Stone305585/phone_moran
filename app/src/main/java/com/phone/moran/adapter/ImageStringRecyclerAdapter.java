package com.phone.moran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phone.moran.R;
import com.phone.moran.model.Picture;
import com.phone.moran.tools.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 搜索pic的recycler
 */
public class ImageStringRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {


    public List<Picture> mData = new ArrayList<>();
    public Context mContext;
    public BaseRecyclerAdapter.OnItemClickListener itemClickListener;
    public LayoutInflater mInflater;

    public ImageStringRecyclerAdapter(Context context, List<Picture> datas) {
        this.mContext = context;
        this.mData = datas;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.image_item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Picture picture = mData.get(position);

        try {
            ImageLoader.displayImg(mContext, picture.getPicture_url(), mHolder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(itemClickListener != null) {
            mHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(mHolder.itemView, position, picture);
                }
            });
        }
    }


    @Override
    public int getItemCount() {

       return Integer.MAX_VALUE;

    }


    public class PictureViewHolder extends BaseRecyclerHolder {
        @BindView(R.id.image)
        ImageView image;
        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }


    // 点击事件接口
    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T model);

        void onItemLongClick(View view, int position, T model);
    }

    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
