package me.iwf.photopicker.entity;

/**
 * Created by donglua on 15/6/30.
 */
public class Photo {

  private int id;
  private String path;
  private boolean isVideoThumb = false;//是否视频的缩略图
  private String thumb;//视频的缩略图

  public void setVideoThumb(boolean videoThumb) {
    isVideoThumb = videoThumb;
  }


  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public Photo(int id, String path) {
    this.id = id;
    this.path = path;
  }

  public Photo() {
  }

  public boolean isVideoThumb() {
    return isVideoThumb;
  }

  public void setIsVideoThumb(boolean isVideoThumb) {
    this.isVideoThumb = isVideoThumb;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Photo)) return false;

    Photo photo = (Photo) o;

    return id == photo.id;
  }

  @Override public int hashCode() {
    return id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
