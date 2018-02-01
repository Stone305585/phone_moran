package com.phone.moran.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phone.moran.R;
import com.phone.moran.activity.PaintActivity;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.adapter.RecentGridRecyclerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.event.AddRecentEvent;
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
 * Use the {@link RecentPaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentPaintFragment extends BaseFragment {
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

    RecentGridRecyclerAdapter localRecyclerAdapter;
    private List<Paint> list = new ArrayList<>();


    public RecentPaintFragment() {
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
    public static RecentPaintFragment newInstance(String param1, String param2) {
        RecentPaintFragment fragment = new RecentPaintFragment();
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

        localRecyclerAdapter = new RecentGridRecyclerAdapter(getActivity(), list);
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

        initRecentCollect();

        recyclerMineP.setItemAnimator(new DefaultItemAnimator());
        recyclerMineP.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerMineP.setAdapter(localRecyclerAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();

        localRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Intent intent = new Intent(getActivity(), PaintActivity.class);
                intent.putExtra(Constant.PAINT_ID, ((Paint)model).getPaint_id());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });
    }

    /**
     * 填充本地的最近的list
     */
    private void initRecentCollect() {

        list.clear();

        if (diskLruCacheHelper.getAsSerializable(Constant.LOCAL_RECENT + userId) != null) {
            ArrayList<Paint> localPaints = ((LocalPaints) diskLruCacheHelper.getAsSerializable(Constant.LOCAL_RECENT + userId)).getPaints();
            list.addAll(localPaints);
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

    public void onEventMainThread(AddRecentEvent event) {

        initRecentCollect();
        localRecyclerAdapter.notifyDataSetChanged();
    }
}