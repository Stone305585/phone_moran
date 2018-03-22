package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
 * 画单详情下面的recycler
 */
public class SearchPaintRecyclerAdapter extends BaseRecyclerAdapter<Paint> {



    public SearchPaintRecyclerAdapter(Context context, List<Paint> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.search_paint_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Paint paint = mData.get(position);

        try {
            ImageLoader.displayImg(mContext, paint.getTitle_url(), mHolder.imageAuthor);
            mHolder.nameAuthor.setText(paint.getPaint_title());
            mHolder.detailAuthor.setText(paint.getPicture_num() + mContext.getResources().getString(R.string.pages) + mContext.getResources().getString(R.string.play) + paint.getRead_num() + mContext.getResources().getString(R.string.times));

            if(itemClickListener != null) {
                mHolder.paintLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(v, position, paint);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class PictureViewHolder extends BaseRecyclerHolder {

        @BindView(R.id.image_author)
        RoundedImageView imageAuthor;
        @BindView(R.id.name_author)
        TextView nameAuthor;
        @BindView(R.id.detail_author)
        TextView detailAuthor;
        @BindView(R.id.paint_LL)
        LinearLayout paintLL;
        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}
