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
import com.phone.moran.activity.XinqingActivity;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.adapter.MoodRoundGridAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalMoods;
import com.phone.moran.model.Mood;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoodCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoodCFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    private MoodRoundGridAdapter moodRoundGridAdapter;
    private List<Mood> moodList = new ArrayList<>();

    LocalMoods localMoods;

    public MoodCFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoodCFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoodCFragment newInstance(String param1, String param2) {
        MoodCFragment fragment = new MoodCFragment();
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
        localMoods = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MOOD + userId);

        moodList.addAll(localMoods.getMoods());

        moodRoundGridAdapter = new MoodRoundGridAdapter(getActivity(), moodList, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_xinqing, container, false);
        initView(v);

        setListener();
        return v;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        recyclerView = view.findViewById(R.id.xinqing_recycler);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(moodRoundGridAdapter);

    }

    @Override
    protected void setListener() {
        super.setListener();

        moodRoundGridAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {

                Mood mood = (Mood) model;

                Intent intent = new Intent(getActivity(), XinqingActivity.class);
                intent.putExtra(Constant.PAINT_ID, mood.getMood_id());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();
    }
}
