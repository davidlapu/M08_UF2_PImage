package cat.itb.m08_uf2_pimage.models;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class MarkerItem {
    private String title;
    private Bitmap img;
    private LatLng latLng;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
