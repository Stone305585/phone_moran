package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.model.Paint;
import com.phone.moran.tools.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 我的收藏弹出的pop
 */
public class MineCollectAdapter extends BaseRecyclerAdapter<Paint> {


    public MineCollectAdapter(Context context, List<Paint> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.mine_collect_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Paint paint = mData.get(position);

        if (paint.isMyDefault()) {
            mHolder.collectCover.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.add_collect));
        } else {
            if (paint.getPicture_info().size() == 0) {
                mHolder.collectCover.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.recyclerview_item));
            } else {
                if (paint.getTitle_url() == null)
                    ImageLoader.displayImg(mContext, paint.getPicture_info().get(0).getPicture_url(), mHolder.collectCover);
                else
                    ImageLoader.displayImg(mContext, paint.getTitle_url(), mHolder.collectCover);
            }
        }

        mHolder.collectTitle.setText(paint.getPaint_title());
        mHolder.collectNum.setText(String.valueOf(paint.getPicture_info().size()) + "张");

        if (itemClickListener != null) {
            mHolder.collectLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, paint);

                }
            });
        }
    }

    public class PictureViewHolder extends BaseRecyclerHolder {
        @BindView(R.id.collect_cover)
        ImageView collectCover;
        @BindView(R.id.collect_title)
        TextView collectTitle;
        @BindView(R.id.collect_LL)
        LinearLayout collectLL;
        @BindView(R.id.collect_num)
        TextView collectNum;

        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}
