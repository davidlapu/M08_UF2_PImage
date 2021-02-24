package cat.itb.m08_uf2_pimage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import cat.itb.m08_uf2_pimage.R;

public class MarkerInfoWindowAdapter implements InfoWindowAdapter {
    private final View viewContents;
    private final Context context;

    public MarkerInfoWindowAdapter(LayoutInflater inflater, Context context) {
        viewContents = inflater.inflate(R.layout.marker_info_window, null);
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        MaterialTextView textViewTitle, textViewDescription;
        ShapeableImageView imageView;

        textViewTitle = viewContents.findViewById(R.id.infoTitle);
        textViewDescription = viewContents.findViewById(R.id.infoDescription);
        imageView = viewContents.findViewById(R.id.infoImg);

        textViewTitle.setText(marker.getTitle());
        textViewDescription.setText(marker.getSnippet());
        //Picasso.with(context).load(marker.get).into(imageView);

        return viewContents;
    }
}
