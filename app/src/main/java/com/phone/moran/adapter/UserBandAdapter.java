package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 管理员被通知的 用户列表，可以移除和同意//TODO 这里需要知道用户的状态，哪些是同意的，那些是可以移除的
 */
public class UserBandAdapter extends BaseRecyclerAdapter<User> {

    public UserBandAdapter(Context context, List<User> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.user_bind_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final User user = mData.get(position);

        mHolder.wifiName.setText(user.getNick_name());

        if (mBandListener != null) {
            mHolder.removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.remove(position, v, user);
                }
            });

            mHolder.approveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.approve(position, v, user);
                }
            });
        }
    }

    public class PictureViewHolder extends BaseRecyclerHolder {

        @BindView(R.id.user_name)
        TextView wifiName;
        @BindView(R.id.remove)
        TextView removeBtn;
        @BindView(R.id.approve)
        TextView approveBtn;

        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

    public interface BandListener{
        void remove(int pos, View v, User user);
        void approve(int pos, View v, User user);
    }

    public BandListener mBandListener;

    public BandListener getmBandListener() {
        return mBandListener;
    }

    public void setmBandListener(BandListener mBandListener) {
        this.mBandListener = mBandListener;
    }
}
