package com.phone.moran.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.phone.moran.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentColorListener} interface
 * to handle interaction events.
 * Use the {@link FrameColorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrameColorFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.frame_color_1)
    FrameLayout frameColor1;
    @BindView(R.id.frame_color_2)
    FrameLayout frameColor2;
    @BindView(R.id.frame_color_3)
    FrameLayout frameColor3;
    @BindView(R.id.frame_color_4)
    FrameLayout frameColor4;
    @BindView(R.id.frame_color_5)
    FrameLayout frameColor5;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentColorListener mListener;

    public FrameColorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrameColorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FrameColorFragment newInstance(String param1, String param2) {
        FrameColorFragment fragment = new FrameColorFragment();
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

        View v = inflater.inflate(R.layout.fragment_frame_color, container, false);
        unbinder = ButterKnife.bind(this, v);
        setListener();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int color) {
        if (mListener != null) {
            mListener.onFragmentColorInteraction(color);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        frameColor1.setOnClickListener(this);
        frameColor2.setOnClickListener(this);
        frameColor3.setOnClickListener(this);
        frameColor4.setOnClickListener(this);
        frameColor5.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentColorListener) {
            mListener = (OnFragmentColorListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        if(mListener == null)
            return;

        frameColor5.setBackground(null);
        frameColor4.setBackground(null);
        frameColor3.setBackground(null);
        frameColor2.setBackground(null);
        frameColor1.setBackground(null);

        switch (v.getId()) {
            case R.id.frame_color_1:
                frameColor1.setBackground(getResources().getDrawable(R.mipmap.frame_selected));
                mListener.onFragmentColorInteraction(1);
                break;
            case R.id.frame_color_2:
                frameColor2.setBackground(getResources().getDrawable(R.mipmap.frame_selected));
                mListener.onFragmentColorInteraction(2);
                break;
            case R.id.frame_color_3:
                frameColor3.setBackground(getResources().getDrawable(R.mipmap.frame_selected));
                mListener.onFragmentColorInteraction(3);
                break;
            case R.id.frame_color_4:
                frameColor4.setBackground(getResources().getDrawable(R.mipmap.frame_selected));
                mListener.onFragmentColorInteraction(4);
                break;
            case R.id.frame_color_5:
                frameColor5.setBackground(getResources().getDrawable(R.mipmap.frame_selected));
                mListener.onFragmentColorInteraction(5);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    public interface OnFragmentColorListener {
        // TODO: Update argument type and name
        void onFragmentColorInteraction(int color);
    }


    public OnFragmentColorListener getmListener() {
        return mListener;
    }

    public void setmListener(OnFragmentColorListener mListener) {
        this.mListener = mListener;
    }
}
