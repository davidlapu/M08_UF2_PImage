package cat.itb.m08_uf2_pimage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import cat.itb.m08_uf2_pimage.R;
import cat.itb.m08_uf2_pimage.fragment.MarkerListFragmentDirections;
import cat.itb.m08_uf2_pimage.models.MarkerItem;
import cat.itb.m08_uf2_pimage.utils.DDBBUtils;

public class MarkerAdapter extends FirebaseRecyclerAdapter<MarkerItem, MarkerAdapter.MarkerHolder> {
    private final Context context;
    private final NavController navController;

    public MarkerAdapter(@NonNull FirebaseRecyclerOptions<MarkerItem> options, Context context, NavController navController) {
        super(options);
        this.context = context;
        this.navController = navController;
    }

    @Override
    protected void onBindViewHolder(@NonNull MarkerHolder holder, int position, @NonNull MarkerItem model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public MarkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.marker_item, parent, false);
        return new MarkerHolder(v);
    }

    public class MarkerHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView textViewName, textViewDescription;
        private final ShapeableImageView imageView;

        public MarkerHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewMarkerName);
            textViewDescription = itemView.findViewById(R.id.textViewMarkerDescription);
            imageView = itemView.findViewById(R.id.imageViewMarkerImage);

        }



        public void bind(MarkerItem model) {
            textViewName.setText(model.getTitle());
            textViewDescription.setText(model.getDescription());
            Picasso.with(context).load(model.getUrlfoto()).into(imageView);
            itemView.setOnClickListener(v -> {
                MarkerListFragmentDirections.ActionMarkerListFragmentToMapsFragment action = MarkerListFragmentDirections.actionMarkerListFragmentToMapsFragment();
                action.setLatitude(model.getLatitude());
                action.setLongitude(model.getLongitude());
                navController.navigate(action);
            });
        }
    }

    public void deleteItem(int position) {
        DDBBUtils.deleteMarker(getItem(position).getId());
    }

    public Context getContext() {
        return context;
    }
}