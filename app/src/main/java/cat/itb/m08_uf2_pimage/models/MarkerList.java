package cat.itb.m08_uf2_pimage.models;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MarkerList {
    private static List<MarkerOptions> markerList;

    public MarkerList(List<MarkerOptions> list) {
        markerList = new ArrayList<>(list);
    }

    public static List<MarkerOptions> getMarkerList() {
        return markerList;
    }

}
