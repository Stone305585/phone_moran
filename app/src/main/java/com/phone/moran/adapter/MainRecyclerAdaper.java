package com.phone.moran.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phone.moran.R;
import com.phone.moran.model.Picture;
import com.phone.moran.tools.DensityUtils;
import com.phone.moran.tools.ImageLoader;
import com.phone.moran.tools.SLogger;
import com.phone.moran.tools.ScreenUtils;
import com.phone.moran.view.gallery.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/10/24.
 * <p>
 * 主页的镜像 recycler显示
 */

public class MainRecyclerAdaper extends RecyclerView.Adapter<BaseRecyclerHolder> {


    public List<Picture> mData = new ArrayList<>();
    public Context mContext;
    public BaseRecyclerAdapter.OnItemClickListener itemClickListener;
    public LayoutInflater mInflater;
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    private int mPagePadding = 30;
    private int mCardWidth = 262;//单个画
    private int playIndex;

    public MainRecyclerAdaper(Context context, List<Picture> datas, int playIndex) {
        this.mContext = context;
        this.mData = datas;
        mInflater = LayoutInflater.from(context);
        this.playIndex = playIndex;

        SLogger.d("<<", "--分辨率->>>" + DensityUtils.dip2px(1));
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.image_item_recycler, parent, false);
        return new MainHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, final int position) {
        final Picture painting = mData.get(position);
        final MainHolder mainHolder = (MainHolder) holder;
        if (position == 0) {
            int leftMargin = (ScreenUtils.getScreenWidth(mContext) - (int) (DensityUtils.dip2px(mCardWidth))) / 2;
            setViewMargin(mainHolder.itemView, leftMargin, 0, DensityUtils.dip2px(15), 0);
        } else if (position < mData.size() - 1) {
            setViewMargin(mainHolder.itemView, DensityUtils.dip2px(15), 0, DensityUtils.dip2px(15), 0);
        } else {
            int rightMargin = (ScreenUtils.getScreenWidth(mContext) - (int) (DensityUtils.dip2px(mCardWidth))) / 2;
            setViewMargin(mainHolder.itemView, DensityUtils.dip2px(15), 0, rightMargin, 0);
        }
//        else if (position == mData.size() - 1) {
//            int rightMargin = (ScreenUtils.getScreenWidth(mContext) - (int) (DensityUtils.dip2px(mCardWidth))) / 2;
//            setViewMargin(mainHolder.itemView, 0, 0, rightMargin, 0);
//        }

        if (itemClickListener != null) {
            mainHolder.imageRecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, painting);
                }
            });
        }

        ImageLoader.displayImg(mContext, painting.getDetail_url(), mainHolder.imageRecycler);
    }


    /**
     * 没有正在进行
     */
    public class MainHolder extends BaseRecyclerHolder {

        @BindView(R.id.image)
        ImageView imageRecycler;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    public int getItemCount() {

        return mData.size();

    }


    // 点击事件接口
    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T model);

        void onItemLongClick(View view, int position, T model);
    }

    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<Picture> getmData() {
        return mData;
    }

    public void setmData(List<Picture> mData) {
        this.mData = mData;
    }

    private void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }
}
