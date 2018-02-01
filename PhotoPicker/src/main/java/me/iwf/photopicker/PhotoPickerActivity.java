package me.iwf.photopicker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.entity.Photo;
import me.iwf.photopicker.event.OnItemCheckListener;
import me.iwf.photopicker.event.OnPhotoClickListener;
import me.iwf.photopicker.fragment.ImagePagerFragment;
import me.iwf.photopicker.fragment.PhotoPickerFragment;
import me.iwf.photopicker.fragment.VideoPickerFragment;

import static android.widget.Toast.LENGTH_LONG;

public class PhotoPickerActivity extends AppCompatActivity {
    public static final String PHOTOPICKER = "photopicker";
    public static final int PHOTO = 0;
    public static final int VIDEO = 1;

    private PhotoPickerFragment pickerFragment;
    private VideoPickerFragment videoPickerFragment;
    private ImagePagerFragment imagePagerFragment;

    public final static String EXTRA_MAX_COUNT = "MAX_COUNT";
    public final static String EXTRA_SHOW_CAMERA = "SHOW_CAMERA";
    public final static String EXTRA_SHOW_GIF = "SHOW_GIF";
    public final static String KEY_SELECTED_PHOTOS = "SELECTED_PHOTOS";

    private MenuItem menuDoneItem;

    public final static int DEFAULT_MAX_COUNT = 9;

    private int maxCount = DEFAULT_MAX_COUNT;

    /**
     * to prevent multiple calls to inflate menu
     */
    private boolean menuIsInflated = false;

    private boolean showGif = false;
    private int selecteCount = 0;

    private TextView cancleTxt;
    private ImageView back;
    private int pageFlag = 0;//当前页面展示视频缩略图还是图片


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("<<", "-->?OnCreate!PHOTOPICKER");

        pageFlag = getIntent().getIntExtra(PHOTOPICKER, 0);

        boolean showCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        boolean showGif = getIntent().getBooleanExtra(EXTRA_SHOW_GIF, false);
        setShowGif(showGif);

        setContentView(pageFlag == PHOTO ? R.layout.activity_photo_picker : R.layout.activity_video_picker);

        back = (ImageView) findViewById(R.id.back);
        cancleTxt = (TextView) findViewById(R.id.tv_next);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cancleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(R.string.picker_images);


        //初始化工具类
        PhotoUtils.initUtils(this);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setElevation(25);
        }

        maxCount = getIntent().getIntExtra(EXTRA_MAX_COUNT, DEFAULT_MAX_COUNT);
//    pickerFragment = PhotoPickerFragment.NewInstance(pageFlag);
//    getSupportFragmentManager().beginTransaction().replace(R.id.photoPickerFragment, pickerFragment).commit();

        if (pageFlag == PHOTO)
            doPhotoThings(showCamera);
        else
            doVideoThings(showCamera);
    }

    private void doPhotoThings(boolean showCamera) {

        Log.d("<<", "--->>>>>>DOPHOTOTHING" + showCamera);
        pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentById(R.id.photoPickerFragment);

        pickerFragment.getPhotoGridAdapter().setShowCamera(showCamera);

        pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
            @Override
            public boolean OnItemCheck(int position, Photo photo, final boolean isCheck, int selectedItemCount) {

                int total = selectedItemCount + (isCheck ? -1 : 1);
                selecteCount = total;
                menuDoneItem.setEnabled(total > 0);
                if (total > 0) {
                    pickerFragment.getSeletedNumTXT().setVisibility(View.VISIBLE);
                    pickerFragment.getSeletedNumTXT().setText(String.valueOf(total));
                    pickerFragment.getAddBtn().setTextColor(Color.BLACK);
                } else {
                    pickerFragment.getSeletedNumTXT().setVisibility(View.GONE);
                    pickerFragment.getAddBtn().setTextColor(Color.parseColor("#9b9b9b"));
                }
                if (maxCount <= 1) {
                    pickerFragment.getSeletedNumTXT().setVisibility(View.GONE);
                    List<Photo> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
                    if (!photos.contains(photo)) {
                        photos.clear();
                        pickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
                    }
                    return true;
                }

                if (total > maxCount) {
                    Toast.makeText(getActivity(), getString(R.string.picker_over_max_count_tips, maxCount),
                            LENGTH_LONG).show();
                    return false;
                }
                menuDoneItem.setTitle(getString(R.string.picker_done_with_count, total, maxCount));
                return true;
            }
        });

        pickerFragment.getBackBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pickerFragment.getAddBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selecteCount == 0) {
                    return;
                }
                Intent intent = new Intent();
                ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
                intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        pickerFragment.getPhotoGridAdapter().setOnPhotoClickListener(new OnPhotoClickListener() {
            @Override
            public void onClick(View v, int position, boolean showCamera) {
//                if (selecteCount == 0) {
//                    return;
//                }
                System.out.println("---111111111111-->" + pickerFragment.getPhotoGridAdapter().getCurrentPhotos().get(position));
                if(showCamera) {
                    if(position != 0) {
                        Intent intent = new Intent();
//                        ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
                        ArrayList<String> selectedPhotos = new ArrayList<>();
                        selectedPhotos.add(pickerFragment.getPhotoGridAdapter().getCurrentPhotos().get(position - 1).getPath());
                        intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    Intent intent = new Intent();
                    ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
                    intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

    }

    private void doVideoThings(boolean showCamera) {
        Log.d("<<", "--->>>>>>DOVIDEOTHING" + showCamera);

        videoPickerFragment = (VideoPickerFragment) getSupportFragmentManager().findFragmentById(R.id.videoPickerFragment);

        videoPickerFragment.getPhotoGridAdapter().setShowCamera(showCamera);

        videoPickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
            @Override
            public boolean OnItemCheck(int position, Photo photo, final boolean isCheck, int selectedItemCount) {

                int total = selectedItemCount + (isCheck ? -1 : 1);
                if(total > maxCount)
                    total = maxCount;
                selecteCount = total;
                menuDoneItem.setEnabled(total > 0);
                if (total > 0) {
                    videoPickerFragment.getSeletedNumTXT().setVisibility(View.VISIBLE);
                    videoPickerFragment.getSeletedNumTXT().setText(String.valueOf(total));
                    videoPickerFragment.getAddBtn().setTextColor(Color.BLACK);
                } else {
                    videoPickerFragment.getSeletedNumTXT().setVisibility(View.GONE);
                    videoPickerFragment.getAddBtn().setTextColor(Color.parseColor("#9b9b9b"));
                }
                if (maxCount <= 1) {
                    videoPickerFragment.getSeletedNumTXT().setVisibility(View.GONE);
                    List<Photo> photos = videoPickerFragment.getPhotoGridAdapter().getSelectedPhotos();
                    if (!photos.contains(photo)) {
                        photos.clear();
                        videoPickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
                    }
                    return true;
                }

                if (total > maxCount) {
                    Toast.makeText(getActivity(), getString(R.string.picker_over_max_count_tips, maxCount),
                            LENGTH_LONG).show();
                    return false;
                }
                menuDoneItem.setTitle(getString(R.string.picker_done_with_count, total, maxCount));
                return true;
            }
        });
        videoPickerFragment.getAddBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selecteCount == 0) {
                    return;
                }
                Intent intent = new Intent();
                ArrayList<String> selectedPhotos = videoPickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
                intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    /**
     * Overriding this method allows us to run our exit animation first, then exiting
     * the activity when it complete.
     */
    @Override
    public void onBackPressed() {
        if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
            imagePagerFragment.runExitAnimation(new Runnable() {
                public void run() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    }
                }
            });
        } else {
            super.onBackPressed();
        }
    }


    public void addImagePagerFragment(ImagePagerFragment imagePagerFragment) {
        this.imagePagerFragment = imagePagerFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, this.imagePagerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!menuIsInflated) {
            getMenuInflater().inflate(R.menu.menu_picker, menu);
            menuDoneItem = menu.findItem(R.id.done);
            menuDoneItem.setEnabled(false);
            menuIsInflated = true;
            return true;
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        if (item.getItemId() == R.id.done) {
            Intent intent = new Intent();
            ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
            intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public PhotoPickerActivity getActivity() {
        return this;
    }

    public boolean isShowGif() {
        return showGif;
    }

    public void setShowGif(boolean showGif) {
        this.showGif = showGif;
    }
}
