package cat.itb.m08_uf2_pimage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;

import cat.itb.m08_uf2_pimage.R;
import cat.itb.m08_uf2_pimage.adapters.MarkerAdapter;
import cat.itb.m08_uf2_pimage.adapters.SwipeToDeleteCallback;
import cat.itb.m08_uf2_pimage.models.MarkerItem;
import cat.itb.m08_uf2_pimage.utils.DDBBUtils;

public class MarkerListFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private NavController navController;
    private MarkerAdapter adapter;
    private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tabLayout = requireActivity().findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);

        View v = inflater.inflate(R.layout.fragment_marker_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewMarker);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        FirebaseRecyclerOptions<MarkerItem> options = new FirebaseRecyclerOptions.Builder<MarkerItem>()
                .setQuery(DDBBUtils.getMarkerRef(), MarkerItem.class).build();
        adapter = new MarkerAdapter(options, requireContext(), navController);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new SwipeToDeleteCallback(adapter)).attachToRecyclerView(recyclerView);

        return v;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            navController.navigate(R.id.action_markerListFragment_to_mapsFragment);
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        tabLayout.removeOnTabSelectedListener(this);
        adapter.stopListening();
    }
}