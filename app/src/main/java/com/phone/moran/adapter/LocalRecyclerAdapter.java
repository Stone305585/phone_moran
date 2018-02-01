package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.model.LocalPaintArray;
import com.phone.moran.model.Paint;
import com.phone.moran.tools.ImageLoader;
import com.phone.moran.view.roundedimageview.RoundedImageView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 画单列表的recycler
 */
public class LocalRecyclerAdapter extends BaseRecyclerAdapter<LocalPaintArray> {

    Set<View> views = new HashSet<>();

    public LocalRecyclerAdapter(Context context, List<LocalPaintArray> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.local_recycler_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final LocalPaintArray localPaintArray = mData.get(position);

        //------第一幅---------------
        if (localPaintArray.getPaint1() != null) {
            mHolder.paint1LL.setVisibility(View.VISIBLE);
            mHolder.paintTitle1.setText(localPaintArray.getPaint1().getPaint_title());

            if (localPaintArray.getPaint1().getTitle_url() == null && localPaintArray.getPaint1().getPicture_info().size() != 0)
                ImageLoader.displayImg(mContext, localPaintArray.getPaint1().getPicture_info().get(0).getPicture_url(), mHolder.paint1);
            else if (localPaintArray.getPaint1().getTitle_url() != null)
                ImageLoader.displayImg(mContext, localPaintArray.getPaint1().getTitle_url(), mHolder.paint1);
        } else {
            mHolder.paint1LL.setVisibility(View.INVISIBLE);
        }

        //------第二幅---------------
        if (localPaintArray.getPaint2() != null) {
            mHolder.paint2LL.setVisibility(View.VISIBLE);
            mHolder.paintTitle2.setText(localPaintArray.getPaint2().getPaint_title());

            if (localPaintArray.getPaint2().getTitle_url() == null && localPaintArray.getPaint2().getPicture_info().size() != 0)
                ImageLoader.displayImg(mContext, localPaintArray.getPaint2().getPicture_info().get(0).getPicture_url(), mHolder.paint2);
            else if (localPaintArray.getPaint2().getTitle_url() != null)
                ImageLoader.displayImg(mContext, localPaintArray.getPaint2().getTitle_url(), mHolder.paint2);
        } else {
            mHolder.paint2LL.setVisibility(View.INVISIBLE);
        }


        //------第三幅---------------
        if (localPaintArray.getPaint3() != null) {
            mHolder.paint3LL.setVisibility(View.VISIBLE);
            mHolder.paintTitle3.setText(localPaintArray.getPaint3().getPaint_title());

            if (localPaintArray.getPaint3().getTitle_url() == null && localPaintArray.getPaint3().getPicture_info().size() != 0)
                ImageLoader.displayImg(mContext, localPaintArray.getPaint3().getPicture_info().get(0).getPicture_url(), mHolder.paint3);
            else if (localPaintArray.getPaint3().getTitle_url() != null)
                ImageLoader.displayImg(mContext, localPaintArray.getPaint3().getTitle_url(), mHolder.paint3);
        } else {
            mHolder.paint3LL.setVisibility(View.INVISIBLE);
        }

        if (mBandListener != null) {

            //------第一幅---------------
            mHolder.paint1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.onItemClick(position, 0, v, localPaintArray.getPaint1());
                }
            });

            mHolder.paint1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mHolder.close1.setVisibility(View.VISIBLE);
                    views.add(mHolder.close1);
                    mBandListener.onLongClick(position, 0, mHolder.close1, localPaintArray.getPaint1());
                    return true;
                }
            });

            mHolder.close1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.onCloseClick(position, 0, v, localPaintArray.getPaint1());
                }
            });

            //------第二幅---------------
            mHolder.paint2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.onItemClick(position, 1, v, localPaintArray.getPaint2());
                }
            });

            mHolder.paint2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mHolder.close2.setVisibility(View.VISIBLE);
                    views.add(mHolder.close2);
                    mBandListener.onLongClick(position, 1, mHolder.close2, localPaintArray.getPaint2());
                    return true;
                }
            });

            mHolder.close2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.onCloseClick(position, 1, v, localPaintArray.getPaint2());
                }
            });

            //------第三幅---------------
            mHolder.paint3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.onItemClick(position, 2, v, localPaintArray.getPaint3());
                }
            });

            mHolder.paint3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mHolder.close3.setVisibility(View.VISIBLE);
                    views.add(mHolder.close3);
                    mBandListener.onLongClick(position, 2, mHolder.close3, localPaintArray.getPaint3());
                    return true;
                }
            });
            mHolder.close3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.onCloseClick(position, 2, v, localPaintArray.getPaint3());
                }
            });
        }

        mHolder.itemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (View v1 : views) {
                    v1.setVisibility(View.GONE);
                }
            }
        });

        mHolder.localBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (View v1 : views) {
                    v1.setVisibility(View.GONE);
                }
            }
        });
    }

    public class PictureViewHolder extends BaseRecyclerHolder {

        @BindView(R.id.paint_1)
        RoundedImageView paint1;
        @BindView(R.id.paint_title1)
        TextView paintTitle1;
        @BindView(R.id.paint_1_LL)
        LinearLayout paint1LL;
        @BindView(R.id.paint_2)
        RoundedImageView paint2;
        @BindView(R.id.paint_title2)
        TextView paintTitle2;
        @BindView(R.id.paint_2_LL)
        LinearLayout paint2LL;
        @BindView(R.id.paint_3)
        RoundedImageView paint3;
        @BindView(R.id.paint_title3)
        TextView paintTitle3;
        @BindView(R.id.paint_3_LL)
        LinearLayout paint3LL;
        @BindView(R.id.close_1)
        ImageView close1;
        @BindView(R.id.close_2)
        ImageView close2;
        @BindView(R.id.close_3)
        ImageView close3;
        @BindView(R.id.item_LL)
        TextView itemLL;
        @BindView(R.id.local_bg)
        ImageView localBg;

        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

    public interface BandListener {
        void onItemClick(int pos, int itemPos, View v, Paint localPaint);

        void onLongClick(int pos, int itemPos, View v, Paint localPaint);

        void onCloseClick(int pos, int itemPos, View v, Paint localPaint);
    }

    public BandListener mBandListener;

    public BandListener getmBandListener() {
        return mBandListener;
    }

    public void setmBandListener(BandListener mBandListener) {
        this.mBandListener = mBandListener;
    }
}
