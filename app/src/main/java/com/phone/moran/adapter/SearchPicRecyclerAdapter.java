package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.model.Picture;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 搜索pic的recycler
 */
public class SearchPicRecyclerAdapter extends BaseRecyclerAdapter<Picture> {


    public SearchPicRecyclerAdapter(Context context, List<Picture> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.search_picture_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Picture picture = mData.get(position);

        try {
            mHolder.namePic.setText(picture.getTitle());

            if(itemClickListener != null) {
                mHolder.picLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(v, position, picture);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PictureViewHolder extends BaseRecyclerHolder {
        @BindView(R.id.name_picture)
        TextView namePic;
        @BindView(R.id.pic_LL)
        LinearLayout picLL;
        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}
