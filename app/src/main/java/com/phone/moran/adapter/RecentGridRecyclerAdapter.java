package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.phone.moran.R;
import com.phone.moran.model.Paint;
import com.phone.moran.tools.ImageLoader;
import com.phone.moran.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 画单详情下面的recycler
 */
public class RecentGridRecyclerAdapter extends BaseRecyclerAdapter<Paint> {


    public RecentGridRecyclerAdapter(Context context, List<Paint> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.grid_paint_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Paint paint = mData.get(position);

        ImageLoader.displayImg(mContext, paint.getTitle_url(), mHolder.imageGrid);

        if(itemClickListener != null) {
            mHolder.imageGrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, paint);

                }
            });
        }
    }

    public class PictureViewHolder extends BaseRecyclerHolder {
        @BindView(R.id.image_grid)
        RoundedImageView imageGrid;
        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}
