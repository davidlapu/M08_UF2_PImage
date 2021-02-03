package cat.itb.m08_uf2_pimage.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import cat.itb.m08_uf2_pimage.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    View rootView;
    MapView mapView;
    GoogleMap gMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = rootView.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        //gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setMinZoomPreference(15);
        googleMap.setMaxZoomPreference(18);

        LatLng itb = new LatLng(41.4533,2.1857094);

        CameraPosition camera = new CameraPosition.Builder()
                .target(itb)
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
    }
}