package cat.itb.m08_uf2_pimage.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;

import cat.itb.m08_uf2_pimage.R;
import cat.itb.m08_uf2_pimage.models.MarkerItem;
import cat.itb.m08_uf2_pimage.utils.DDBBUtils;
import cat.itb.m08_uf2_pimage.utils.ImageUtils;

import static android.app.Activity.RESULT_OK;

public class FormNewMarkerFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private ShapeableImageView imageView;
    private NavController navController;
    private TextInputEditText textInputEditTextTitle, textInputEditTextDescription;
    private MaterialButton buttonFormSave;
    private ImageUtils imageUtils;
    private File url;
    private LatLng latLng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        imageUtils = new ImageUtils(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().findViewById(R.id.tabLayout).setVisibility(View.GONE);

        View v = inflater.inflate(R.layout.fragment_form_new_marker, container, false);

        textInputEditTextTitle = v.findViewById(R.id.textInputEditTextTitle);
        textInputEditTextDescription = v.findViewById(R.id.textInputEditTextDescription);
        buttonFormSave = v.findViewById(R.id.buttonFormSave);
        imageView = v.findViewById(R.id.shapeableImageView);

        buttonFormSave.setOnClickListener(this::selectImg);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        latLng = FormNewMarkerFragmentArgs.fromBundle(requireArguments()).getLatLng();
    }

    private void selectImg(View view) {
        CropImage.startPickImageActivity(requireContext(), this);
    }

    private void changeMarker() {
        buttonFormSave.setText(R.string.save);
        buttonFormSave.setOnClickListener(this::saveMarker);
    }

    private void saveMarker(View view) {
        buttonFormSave.setEnabled(false);
        MarkerItem item = new MarkerItem();
        try {
            Task<Uri> task = DDBBUtils.subirImagen(imageUtils.comprimirImagen(url), latLng);
            item.setDescription(textInputEditTextDescription.getText().toString());
            item.setTitle(textInputEditTextTitle.getText().toString());
            item.setLatitude(latLng.latitude);
            item.setLongitude(latLng.longitude);

            task.addOnCompleteListener(task1 -> {
                Uri downloadUri = task1.getResult();

                String urlFoto = downloadUri.toString();
                item.setUrlfoto(urlFoto);
                DDBBUtils.uploadMarker(item);
                navigateMap();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        else if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(requireContext(), data);
            imageUtils.recortarImagen(imageUri, FormNewMarkerFragment.this);

            changeMarker();
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                url = new File(result.getUri().getPath());
                Picasso.with(requireContext()).load(url).into(imageView);

                changeMarker();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().findViewById(R.id.tabLayout).setVisibility(View.VISIBLE);
    }

    private void navigateMap() {
        navController.navigate(R.id.action_formNewMarkerFragment_to_mapsFragment);
    }
}