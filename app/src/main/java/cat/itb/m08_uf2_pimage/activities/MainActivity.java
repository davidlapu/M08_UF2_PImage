package cat.itb.m08_uf2_pimage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import cat.itb.m08_uf2_pimage.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this , R.id.nav_host_fragment);

        tabLayout = findViewById(R.id.tabLayout);

    }
}