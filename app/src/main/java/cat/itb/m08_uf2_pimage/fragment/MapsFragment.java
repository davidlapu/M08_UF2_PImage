package cat.itb.m08_uf2_pimage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cat.itb.m08_uf2_pimage.R;
import cat.itb.m08_uf2_pimage.adapters.MarkerInfoWindowAdapter;
import cat.itb.m08_uf2_pimage.models.MarkerItem;
import cat.itb.m08_uf2_pimage.utils.DDBBUtils;

public class MapsFragment extends Fragment implements OnMapReadyCallback, TabLayout.OnTabSelectedListener {

    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;
    private NavController navController;
    private TabLayout tabLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        tabLayout = getActivity().findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);
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
        gMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(getLayoutInflater(), requireContext()));

        LatLng initialLatLng;
        MapsFragmentArgs args = MapsFragmentArgs.fromBundle(getArguments());
        if (args.getLatitude() != null && args.getLatitude() != null) {
            initialLatLng = new LatLng(args.getLatitude(), args.getLongitude());
        } else {
            initialLatLng = new LatLng(41.4533,2.1857094);
        }

        CameraPosition camera = new CameraPosition.Builder()
                .target(initialLatLng)
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

        gMap.setOnMapLongClickListener(this::mapLongClicked);

        DDBBUtils.getMarkerRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    MarkerItem item = s.getValue(MarkerItem.class);
                    MarkerOptions marker = new MarkerOptions();

                    marker.position(new LatLng(item.getLatitude(), item.getLongitude()));
                    marker.title(item.getTitle());
                    marker.snippet(item.getDescription() + "$spl$" + item.getUrlfoto());

                    gMap.addMarker(marker);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void mapLongClicked(LatLng latLng) {
        NavDirections action = MapsFragmentDirections.actionMapsFragmentToFormNewMarkerFragment(latLng);
        navController.navigate(action);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 1) {
            navController.navigate(R.id.action_mapsFragment_to_markerListFragment);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onStop() {
        super.onStop();
        tabLayout.removeOnTabSelectedListener(this);
    }
}