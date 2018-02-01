package com.phone.moran.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phone.moran.R;
import com.phone.moran.activity.PaintActivity;
import com.phone.moran.adapter.LocalRecyclerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.event.AddMineEvent;
import com.phone.moran.model.LocalPaintArray;
import com.phone.moran.model.LocalPaints;
import com.phone.moran.model.Paint;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MinePaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MinePaintFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycler_mine_p)
    RecyclerView recyclerMineP;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LocalRecyclerAdapter localRecyclerAdapter;
    private List<LocalPaintArray> list = new ArrayList<>();


    public MinePaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MinePaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MinePaintFragment newInstance(String param1, String param2) {
        MinePaintFragment fragment = new MinePaintFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        localRecyclerAdapter = new LocalRecyclerAdapter(getActivity(), list);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mine_paint, container, false);
        unbinder = ButterKnife.bind(this, v);

        initView(v);
        setListener();
        return v;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        initLocalMine();

        recyclerMineP.setItemAnimator(new DefaultItemAnimator());
        recyclerMineP.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerMineP.setAdapter(localRecyclerAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();

        localRecyclerAdapter.setmBandListener(new LocalRecyclerAdapter.BandListener() {
            @Override
            public void onItemClick(int pos, int itemPos, View v, Paint localPaint) {

                if (goLogin()) {
                    return;
                }

                Intent intent = new Intent(getActivity(), PaintActivity.class);
                intent.putExtra(Constant.PAINT_TITLE, localPaint.getPaint_title());
                startActivity(intent);
            }

            @Override
            public void onLongClick(int pos, int itemPos, View v, Paint localPaint) {
//                if (closeView != null)
//                    closeView.setVisibility(View.GONE);
//                closeView = v;

            }

            @Override
            public void onCloseClick(int pos, int itemPos, View v, Paint localPaint) {
                LocalPaints localPaints = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MINE + userId);

                for (int i = 0; i < localPaints.getPaints().size(); i++) {
                    if (localPaints.getPaints().get(i).getPaint_title().equals(localPaint.getPaint_title())) {
                        localPaints.getPaints().remove(i);
                        break;
                    }
                }
                diskLruCacheHelper.put(Constant.LOCAL_MINE + userId, localPaints);

                dialog.show();
                initLocalMine();
                localRecyclerAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    private View closeView;

    /**
     * 填充本地的我的list
     */
    private void initLocalMine() {

        list.clear();

        if (diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MINE + userId) != null) {
            ArrayList<Paint> localPaints = ((LocalPaints) diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MINE + userId)).getPaints();

            int size = localPaints.size();

            //一共几行
            int rows = (int) (Math.ceil((double) size / 3));

            //剩下几个不够一行
            int rest = size % 3;

            if (rows > 1 && rest != 0) {
                for (int j = 0; j < rows - 1; j++) {
                    LocalPaintArray l = new LocalPaintArray();
                    l.setPaint1(localPaints.get(3 * j));
                    l.setPaint2(localPaints.get(3 * j + 1));
                    l.setPaint3(localPaints.get(3 * j + 2));
                    list.add(l);
                }

            } else if (rest == 0 && rows > 0) {
                for (int k = 0; k < rows; k++) {
                    LocalPaintArray l = new LocalPaintArray();
                    l.setPaint1(localPaints.get(3 * k));
                    l.setPaint2(localPaints.get(3 * k + 1));
                    l.setPaint3(localPaints.get(3 * k + 2));
                    list.add(l);
                }
            }

            if (rest > 0) {

                LocalPaintArray localPaintArray = new LocalPaintArray();

                if (rest > 0) {
                    localPaintArray.setPaint1(localPaints.get((rows - 1) * 3));
                }
                if (rest > 1) {
                    localPaintArray.setPaint2(localPaints.get((rows - 1) * 3 + 1));
                }
                if (rest > 2) {
                    localPaintArray.setPaint3(localPaints.get((rows - 1) * 3 + 2));
                }

                list.add(localPaintArray);
            }

        }

        if (list.size() == 0) {

            LocalPaintArray localPaintArray = new LocalPaintArray();
            Paint paint = new Paint();
            paint.setPaint_id(-1);
            paint.setTitle_detail_url("我的收藏");
            localPaintArray.setPaint1(paint);
            list.clear();
            list.add(localPaintArray);

        } else if (list.size() < 4) {
            LocalPaintArray localPaintArray1 = new LocalPaintArray();
            list.add(localPaintArray1);
            LocalPaintArray localPaintArray2 = new LocalPaintArray();
            list.add(localPaintArray2);
        } else if (list.size() < 7) {
            LocalPaintArray localPaintArray2 = new LocalPaintArray();
            list.add(localPaintArray2);
        }
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onEventMainThread(AddMineEvent event) {

        initLocalMine();
        localRecyclerAdapter.notifyDataSetChanged();
    }
}
