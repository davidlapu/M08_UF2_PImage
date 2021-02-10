package cat.itb.m08_uf2_pimage.models;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MarkerList {
    private static List<MarkerItem> markerList = new ArrayList<>();

    public MarkerList() {
    }

    public static List<MarkerItem> getMarkerList() {
        return markerList;
    }
}
