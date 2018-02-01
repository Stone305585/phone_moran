package me.iwf.photopicker.entity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by stone on 2016/5/21.
 */
public class Video {
    private int id;
    private String title;
    private String album;
    private String artist;
    private String displayName;
    private String mimeType;
    private String path;
    private long size;
    private long duration;
    private Photo capture;
    private File thumbFile;

    /**
     *
     */
    public Video() {
        super();
    }

    /**
     * @param id
     * @param title
     * @param album
     * @param artist
     * @param displayName
     * @param mimeType
     * @param path
     * @param size
     * @param duration
     */
    public Video(int id, String title, String album, String artist,
                 String displayName, String mimeType, String path, long size,
                 long duration, File thumbFile) {
        super();
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
        this.duration = duration;


        capture = new Photo(id, path);
        capture.setIsVideoThumb(true);

        if(!thumbFile.exists()) {
            Bitmap b = getVideoThumbnail(path);
            thumbFile = saveBitmapFile(b, thumbFile);
            capture.setThumb(thumbFile.getAbsolutePath());
        } else {
            capture.setThumb(thumbFile.getAbsolutePath());
        }

    }

    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            Log.d("<<", "video file--->>" + filePath);
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * bitmap----》file
     *
     * @param bitmap
     */
    public File saveBitmapFile(Bitmap bitmap, File file) {

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, bos);
            bos.flush();
            bos.close();
            bitmap.recycle();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Photo getCapture() {
        return capture;
    }

    public void setCapture(Photo capture) {
        this.capture = capture;
    }
}