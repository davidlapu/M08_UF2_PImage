package cat.itb.m08_uf2_pimage.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import cat.itb.m08_uf2_pimage.R;

public class MarkerListFragment extends Fragment {

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TabLayout tabLayout = getActivity().findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new onTabSelectedListener());
        return inflater.inflate(R.layout.fragment_marker_list, container, false);
    }

    class onTabSelectedListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getPosition() == 0) {
                NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_markerListFragment_to_mapsFragment);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }
}