package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.model.Paint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 画单列表的recycler
 */
public class FilterRecyclerAdapter extends BaseRecyclerAdapter<Paint> {

    private boolean isMood;


    public FilterRecyclerAdapter(Context context, List<Paint> datas, boolean isMood) {
        super(context, datas);
        this.isMood = isMood;
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.filter_category_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Paint paint = mData.get(position);

        if(paint.isSelected()) {
            mHolder.selectedFilter.setVisibility(View.VISIBLE);
        } else {
            mHolder.selectedFilter.setVisibility(View.INVISIBLE);
        }
        mHolder.textFilter.setText(paint.getPaint_name());

        if (itemClickListener != null) {
            mHolder.filterLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, paint);

                }
            });
        }
    }

    public class PictureViewHolder extends BaseRecyclerHolder {
        @BindView(R.id.image_filter)
        ImageView imageFilter;
        @BindView(R.id.text_filter)
        TextView textFilter;
        @BindView(R.id.selected_filter)
        ImageView selectedFilter;
        @BindView(R.id.filter_LL)
        LinearLayout filterLL;

        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

            if(!isMood) {
                imageFilter.setVisibility(View.GONE);
            }
        }
    }

}
