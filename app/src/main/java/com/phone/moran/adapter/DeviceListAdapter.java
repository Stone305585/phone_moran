package com.phone.moran.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.model.DeviceInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 画单列表的recycler
 */
public class DeviceListAdapter extends BaseRecyclerAdapter<DeviceInfo> {


    public DeviceListAdapter(Context context, List<DeviceInfo> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.device_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final DeviceInfo user = mData.get(position);

        mHolder.wifiName.setText(user.getDevice_name());

        if(user.getFlag() == 1) {
            mHolder.checkBox.setChecked(true);
        }

        if (mBandListener != null) {
            mHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBandListener.remove(position, v, user);
                }
            });

            mHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mBandListener.check(position, mHolder.checkBox, isChecked, user);
                }
            });

        }

        if(itemClickListener != null) {
            mHolder.wifiLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, user);
                }
            });
        }


    }

    public class PictureViewHolder extends BaseRecyclerHolder {

        @BindView(R.id.wifi_name)
        TextView wifiName;
        @BindView(R.id.delete)
        TextView delete;
        @BindView(R.id.wifi_LL)
        LinearLayout wifiLL;
        @BindView(R.id.check_device)
        CheckBox checkBox;

        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

    public interface BandListener {
        void remove(int pos, View v, DeviceInfo user);

        void check(int pos, CheckBox v, boolean isCheck, DeviceInfo device);
    }

    public BandListener mBandListener;

    public BandListener getmBandListener() {
        return mBandListener;
    }

    public void setmBandListener(BandListener mBandListener) {
        this.mBandListener = mBandListener;
    }
}
