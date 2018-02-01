package me.iwf.photopicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.R;
import me.iwf.photopicker.entity.Photo;
import me.iwf.photopicker.entity.PhotoDirectory;
import me.iwf.photopicker.event.OnItemCheckListener;
import me.iwf.photopicker.event.OnPhotoClickListener;
import me.iwf.photopicker.utils.MediaStoreHelper;

/**
 * Created by donglua on 15/5/31.
 */
public class PhotoGridAdapter extends SelectableAdapter<PhotoGridAdapter.PhotoViewHolder> {
    public static final boolean VIDEOFLAG = false;
    private static final int VIDEO_MAX_SIZE = 20 * 1024 * 1024; //M

    private LayoutInflater inflater;

    private Context mContext;

    private OnItemCheckListener onItemCheckListener = null;
    private OnPhotoClickListener onPhotoClickListener = null;
    private View.OnClickListener onCameraClickListener = null;

    public final static int ITEM_TYPE_CAMERA = 100;
    public final static int ITEM_TYPE_PHOTO = 101;

    private boolean hasCamera = true;

    private final int imageSize;
    private List<Photo> videoPhotos = new ArrayList<>();
    private boolean photoFlag = true;
    private String videoPath;

    public PhotoGridAdapter(Context context, List<PhotoDirectory> photoDirectories) {

        this.photoDirectories = photoDirectories;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;

        imageSize = widthPixels / 3;
    }

    public PhotoGridAdapter(Context context, List<Photo> photos, boolean flag) {


        photoFlag = flag;
        Log.d("<<", "--->>>>photo size-->" + photos.size() + "--flag-->>" + photoFlag);
        this.videoPhotos = photos;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        imageSize = widthPixels / 3;

    }


    @Override
    public int getItemViewType(int position) {
        return (showCamera() && position == 0) ? ITEM_TYPE_CAMERA : ITEM_TYPE_PHOTO;
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_photo, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(itemView);
        if (viewType == ITEM_TYPE_CAMERA) {
            holder.vSelected.setVisibility(View.GONE);
            holder.ivPhoto.setScaleType(ImageView.ScaleType.CENTER);
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCameraClickListener != null) {
                        onCameraClickListener.onClick(view);
                    }
                }
            });
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (getItemViewType(position) == ITEM_TYPE_PHOTO) {

            List<Photo> photos;
            if (photoFlag) {
                photos = getCurrentPhotos();
            } else {
                photos = videoPhotos;
            }
            final Photo photo;

            if (showCamera()) {
                photo = photos.get(position - 1);
            } else {
                photo = photos.get(position);
            }

            if (photoFlag) {
                Glide.with(mContext)
                        .load(new File(photo.getPath()))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .override(imageSize, imageSize)
                        .placeholder(R.drawable.ic_photo_black_48dp)
                        .error(R.drawable.ic_broken_image_black_48dp)
                        .into(holder.ivPhoto);
            } else {
                if (position == 0)
                    Log.d("<<", "video Path-->>" + photo.getPath());
                Glide.with(mContext)
                        .load(new File(photo.getThumb()))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .override(imageSize, imageSize)
                        .placeholder(R.drawable.ic_photo_black_48dp)
                        .error(R.drawable.ic_broken_image_black_48dp)
                        .into(holder.ivPhoto);

            }

            final boolean isChecked = isSelected(photo);

            holder.vSelected.setSelected(isChecked);
            holder.ivPhoto.setSelected(isChecked);
            if (photo.isVideoThumb()) {
                holder.videoFlag.setVisibility(View.VISIBLE);
            } else
                holder.videoFlag.setVisibility(View.GONE);

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPhotoClickListener != null) {
                        onPhotoClickListener.onClick(view, position, showCamera());
                    }
                }
            });
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!photoFlag) {
                        File video = new File(photo.getPath());
                        if (video.length() > VIDEO_MAX_SIZE) {
                            Toast.makeText(mContext, "视频文件不能大于20M", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    boolean isEnable = true;

                    if (onItemCheckListener != null) {
                        isEnable = onItemCheckListener.OnItemCheck(position, photo, isChecked,
                                getSelectedPhotos().size());
                    }
                    if (isEnable) {
                        toggleSelection(photo);
                        notifyItemChanged(position);
                    }
                }
            });

        } else {
            holder.ivPhoto.setImageResource(R.drawable.camera);
        }
    }


    @Override
    public int getItemCount() {
        if (photoFlag) {
            int photosCount =
                    photoDirectories.size() == 0 ? 0 : getCurrentPhotos().size();
            if (showCamera()) {
                return photosCount + 1;
            }
            return photosCount;
        } else {
            return videoPhotos.size();
        }

    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vSelected;
        private ImageView videoFlag;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            videoFlag = (ImageView) itemView.findViewById(R.id.video_flag);
        }
    }


    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }


    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }


    public void setOnCameraClickListener(View.OnClickListener onCameraClickListener) {
        this.onCameraClickListener = onCameraClickListener;
    }


    public ArrayList<String> getSelectedPhotoPaths() {
        ArrayList<String> selectedPhotoPaths = new ArrayList<>(getSelectedItemCount());

        for (Photo photo : selectedPhotos) {
            selectedPhotoPaths.add(photo.getPath());
        }

        return selectedPhotoPaths;
    }


    public void setShowCamera(boolean hasCamera) {
        this.hasCamera = hasCamera;
    }


    public boolean showCamera() {
        return (hasCamera && currentDirectoryIndex == MediaStoreHelper.INDEX_ALL_PHOTOS);
    }
}
