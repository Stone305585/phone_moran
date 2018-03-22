package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.model.Author;
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
public class SearchAuthorRecyclerAdapter extends BaseRecyclerAdapter<Author> {

    public SearchAuthorRecyclerAdapter(Context context, List<Author> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.search_author_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final Author author = mData.get(position);

        try {
            ImageLoader.displayImg(mContext, author.getImg_url(), mHolder.imageGrid);
            mHolder.nameAuthor.setText(author.getAuthro_name());

            if(itemClickListener != null) {
                mHolder.author_LL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(v, position, author);
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class PictureViewHolder extends BaseRecyclerHolder {
        @BindView(R.id.image_author)
        RoundedImageView imageGrid;
        @BindView(R.id.name_author)
        TextView nameAuthor;
        @BindView(R.id.author_LL)
        LinearLayout author_LL;
        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}
