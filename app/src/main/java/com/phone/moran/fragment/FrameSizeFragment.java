package com.phone.moran.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.tools.SLogger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentSizeListener} interface
 * to handle interaction events.
 * Use the {@link FrameSizeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrameSizeFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.frame_size_0)
    TextView frameSize0;
    @BindView(R.id.frame_size_2)
    FrameLayout frameSize2;
    @BindView(R.id.frame_size_5)
    FrameLayout frameSize5;
    @BindView(R.id.frame_size_7)
    FrameLayout frameSize7;
    @BindView(R.id.frame_size_10)
    FrameLayout frameSize10;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentSizeListener mListener;

    public FrameSizeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrameSizeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FrameSizeFragment newInstance(String param1, String param2) {
        FrameSizeFragment fragment = new FrameSizeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_frame_size, container, false);
        unbinder = ButterKnife.bind(this, v);
        setListener();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int color) {
        if (mListener != null) {
            mListener.onFragmentSizeInteraction(color);
        }
    }

    public void setmListener(OnFragmentSizeListener fragmentSizeListener) {
        mListener = fragmentSizeListener;
    }

    @Override
    protected void setListener() {
        super.setListener();
        frameSize0.setOnClickListener(this);
        frameSize2.setOnClickListener(this);
        frameSize5.setOnClickListener(this);
        frameSize7.setOnClickListener(this);
        frameSize10.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        if(mListener == null)
            return;
        switch (v.getId()) {
            case R.id.frame_size_0:
                mListener.onFragmentSizeInteraction(0);
                break;
            case R.id.frame_size_2:

                mListener.onFragmentSizeInteraction(1);
                break;
            case R.id.frame_size_5:

                mListener.onFragmentSizeInteraction(2);
                break;
            case R.id.frame_size_7:

                mListener.onFragmentSizeInteraction(3);
                break;
            case R.id.frame_size_10:
                mListener.onFragmentSizeInteraction(4);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentSizeListener {
        // TODO: Update argument type and name
        void onFragmentSizeInteraction(int size);
    }
}
