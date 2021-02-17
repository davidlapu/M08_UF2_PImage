package cat.itb.m08_uf2_pimage.models;

import com.google.android.gms.maps.model.LatLng;

public class MarkerItem {
    private String id;
    private String urlfoto;
    private String title;
    private LatLng latLng;

    public MarkerItem() {
    }

    public MarkerItem(String id, String urlfoto, String title, LatLng latLng) {
        this.id = id;
        this.urlfoto = urlfoto;
        this.title = title;
        this.latLng = latLng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
