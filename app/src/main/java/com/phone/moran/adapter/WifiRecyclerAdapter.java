package com.phone.moran.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/11/9.
 * <p/>
 * 画单列表的recycler
 */
public class WifiRecyclerAdapter extends BaseRecyclerAdapter<ScanResult> {

    public WifiRecyclerAdapter(Context context, List<ScanResult> datas) {
        super(context, datas);
    }

    @Override
    public BaseRecyclerHolder createViewHoldeHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(mInflater.inflate(R.layout.wifi_item, parent, false));
    }

    @Override
    public void showViewHolder(BaseRecyclerHolder holder, final int position) {

        final PictureViewHolder mHolder = (PictureViewHolder) holder;
        final ScanResult wifiInfo = mData.get(position);

        mHolder.wifiName.setText(wifiInfo.SSID);

        if (itemClickListener != null) {
            mHolder.wifiLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position, wifiInfo);

                }
            });
        }
    }

    public class PictureViewHolder extends BaseRecyclerHolder {

        @BindView(R.id.wifi_name)
        TextView wifiName;
        @BindView(R.id.wifi_LL)
        LinearLayout wifiLL;

        View v;

        public PictureViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            ButterKnife.bind(this, itemView);

        }
    }

}
