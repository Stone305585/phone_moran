package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.model.Mood;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/18.
 * <p/>
 * recycler adapter
 */
public class MoodRoundGridAdapter extends BaseRecyclerAdapter<Mood> {

    private int flag = 0;//0:大的，1：小的

    public MoodRoundGridAdapter(Context context, List<Mood> datas, int flag) {
        super(context, datas);

        this.flag = flag;
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        if (flag == 0)
            return new PictureViewHolder(mInflater.inflate(R.layout.xinqing_circle_item, parent, false));
        else
            return new PictureViewHolder(mInflater.inflate(R.layout.add_mood_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Mood mood = mData.get(position);

        mHolder.imageGrid.setImageDrawable(mContext.getResources().getDrawable(mood.getRes_id()));
        mHolder.titleGrid.setText(mood.getMood_name());

        if (itemClickListener != null) {
            mHolder.imageGrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, mood);

                }
            });
        }
    }

    public class PictureViewHolder extends BaseRecyclerHolder {
        @BindView(R.id.image_xinqing)
        ImageView imageGrid;
        @BindView(R.id.text_xinqing)
        TextView titleGrid;
        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}