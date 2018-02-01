package me.iwf.photopicker.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.R;
import me.iwf.photopicker.adapter.PhotoGridAdapter;
import me.iwf.photopicker.adapter.PopupDirectoryListAdapter;
import me.iwf.photopicker.entity.Photo;
import me.iwf.photopicker.entity.PhotoDirectory;
import me.iwf.photopicker.entity.Video;
import me.iwf.photopicker.provider.VideoProvider;
import me.iwf.photopicker.utils.ImageCaptureManager;

import static android.app.Activity.RESULT_OK;

/**
 * Created by donglua on 15/5/31.
 */
public class VideoPickerFragment extends Fragment {

    private ImageCaptureManager captureManager;
    private PhotoGridAdapter photoGridAdapter;

    private PopupDirectoryListAdapter listAdapter;
    private List<Photo> photos;

    private int SCROLL_THRESHOLD = 30;
    TextView selectedNumTxt;
    private Button addBtn;
    private ProgressDialog dialog;


    public static VideoPickerFragment NewInstance(int pageFlag) {
        VideoPickerFragment fragment = new VideoPickerFragment();


        Bundle args = new Bundle();
        args.putInt(PhotoPickerActivity.PHOTOPICKER, pageFlag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageFlag = getArguments().getInt(PhotoPickerActivity.PHOTOPICKER);
        }
        dialog = new ProgressDialog(getActivity());

        photos = new ArrayList<>();
        photoGridAdapter = new PhotoGridAdapter(getActivity(), photos, PhotoGridAdapter.VIDEOFLAG);

        captureManager = new ImageCaptureManager(getActivity());
        new LoadVideo().start();

    }

    private int pageFlag = 0;

    /**
     * 这里设置是否显示所有视频，reset Adapter
     *
     * @param pageFlag
     */
    public void setPageFlag(int pageFlag) {
        this.pageFlag = pageFlag;
        //TODO resetAdapter

    }

    class LoadVideo extends Thread {
        @Override
        public void run() {
            VideoProvider videoProvider = new VideoProvider(getActivity());
            PhotoDirectory photoDirectory = new PhotoDirectory();
            photoDirectory.setId("video");

            List<Video> videos = videoProvider.getList();
            for (Video item : videos) {
                photos.add(item.getCapture());
            }
            if (getActivity() != null && !getActivity().isFinishing())
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (photoGridAdapter != null){
                            photoGridAdapter.notifyDataSetChanged();
                            dialog.dismiss();

                        }

                    }
                });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        final View rootView = inflater.inflate(R.layout.fragment_photo_picker, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_photos);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photoGridAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        dialog.show();

        final Button btSwitchDirectory = (Button) rootView.findViewById(R.id.button);
        //视频暂时去掉选择
        btSwitchDirectory.setVisibility(View.GONE);
        addBtn = (Button) rootView.findViewById(R.id.ok_btn);
        final ListPopupWindow listPopupWindow = new ListPopupWindow(getActivity());
        listPopupWindow.setWidth(ListPopupWindow.MATCH_PARENT);
        listPopupWindow.setAnchorView(btSwitchDirectory);
        listPopupWindow.setAdapter(listAdapter);
        listPopupWindow.setModal(true);
        listPopupWindow.setDropDownGravity(Gravity.BOTTOM);
        listPopupWindow.setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);

/*        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();

                PhotoDirectory directory = directories.get(position);

                btSwitchDirectory.setText(directory.getName());

                photoGridAdapter.setCurrentDirectoryIndex(position);
                photoGridAdapter.notifyDataSetChanged();
            }
        });*/

        /**
         * 缩略图点击事件没有
         */
/*        photoGridAdapter.setOnPhotoClickListener(new OnPhotoClickListener() {
            @Override
            public void onClick(View v, int position, boolean showCamera) {
                final int index = showCamera ? position - 1 : position;

                List<String> photos = photoGridAdapter.getCurrentPhotoPaths();

                int[] screenLocation = new int[2];
                v.getLocationOnScreen(screenLocation);
                ImagePagerFragment imagePagerFragment =
                        ImagePagerFragment.newInstance(photos, index, screenLocation, v.getWidth(),
                                v.getHeight(), new ImagePagerFragment.CloseFragmentListener() {
                                    @Override
                                    public void closeFragment() {

                                    }
                                });

                ((PhotoPickerActivity) getActivity()).addImagePagerFragment(imagePagerFragment);
            }
        });*/

//        photoGridAdapter.setOnCameraClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Intent intent = captureManager.dispatchTakePictureIntent();
//                    startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        btSwitchDirectory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listPopupWindow.isShowing()) {
                    listPopupWindow.dismiss();
                } else if (!getActivity().isFinishing()) {
                    listPopupWindow.setHeight(Math.round(rootView.getHeight() * 0.8f));
                    listPopupWindow.show();
                }
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Log.d(">>> Picker >>>", "dy = " + dy);
                if (Math.abs(dy) > SCROLL_THRESHOLD) {
                    Glide.with(getActivity()).pauseRequests();
                } else {
                    Glide.with(getActivity()).resumeRequests();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getActivity()).resumeRequests();
                }
            }
        });

        selectedNumTxt = (TextView) rootView.findViewById(R.id.selected_num_txt);
        selectedNumTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("-->>", "haha");
            }
        });


        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            captureManager.galleryAddPic();
//            if (directories.size() > 0) {
//                String path = captureManager.getCurrentPhotoPath();
//                PhotoDirectory directory = directories.get(INDEX_ALL_PHOTOS);
//                directory.getPhotos().add(INDEX_ALL_PHOTOS, new Photo(path.hashCode(), path));
//                directory.setCoverPath(path);
//                photoGridAdapter.notifyDataSetChanged();
//            }
        }
    }


    public PhotoGridAdapter getPhotoGridAdapter() {
        return photoGridAdapter;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        captureManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        captureManager.onRestoreInstanceState(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    public ArrayList<String> getSelectedPhotoPaths() {
        return photoGridAdapter.getSelectedPhotoPaths();
    }

    public TextView getSeletedNumTXT() {
        return selectedNumTxt;
    }

    public Button getAddBtn() {
        return addBtn;
    }
}
