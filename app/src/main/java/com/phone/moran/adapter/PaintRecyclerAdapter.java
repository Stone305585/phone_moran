package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * 画单列表的recycler
 */
public class PaintRecyclerAdapter extends BaseRecyclerAdapter<Paint> {

    public PaintRecyclerAdapter(Context context, List<Paint> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.new_book_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Paint paint = mData.get(position);

        ImageLoader.displayImg(mContext, paint.getTitle_url(), mHolder.imageCover);
        mHolder.numCover.setText(String.valueOf(paint.getPicture_num()));
        mHolder.desCover.setText(paint.getPaint_title());
        mHolder.looksCover.setText(String.valueOf(paint.getRead_num()));

        if (itemClickListener != null) {
            mHolder.imageCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, paint);

                }
            });
        }
    }

    public class PictureViewHolder extends BaseRecyclerHolder {
        @BindView(R.id.image_cover)
        RoundedImageView imageCover;
        @BindView(R.id.des_cover)
        TextView desCover;
        @BindView(R.id.num_cover)
        TextView numCover;
        @BindView(R.id.looks_cover)
        TextView looksCover;
        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}
