package cat.itb.m08_uf2_pimage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import cat.itb.m08_uf2_pimage.R;
import cat.itb.m08_uf2_pimage.models.MarkerItem;

public class MarkerAdapter extends FirebaseRecyclerAdapter<MarkerItem, MarkerAdapter.MarkerHolder> {

    public MarkerAdapter(@NonNull FirebaseRecyclerOptions<MarkerItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MarkerHolder holder, int position, @NonNull MarkerItem model) {
        holder.textViewName.setText(model.getTitle());
    }

    @NonNull
    @Override
    public MarkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.marker_item, parent, false);
        return new MarkerHolder(v);
    }

    public class MarkerHolder extends RecyclerView.ViewHolder {
        MaterialTextView textViewName;
        ShapeableImageView imageView;

        public MarkerHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewMarkerName);
            imageView = itemView.findViewById(R.id.imageViewMarkerImage);
        }
    }
}