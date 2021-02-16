package cat.itb.m08_uf2_pimage.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import cat.itb.m08_uf2_pimage.R;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;


public class MainFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private Button buttonTakePicture, buttonUpload, buttonSelect;
    private ImageView imageView;
    private StorageReference storageReference;
    private DatabaseReference imgref;
    private Bitmap thumbBitmap;
    private String nameImage;
    private byte[] thumb_byte;
    private File url;
    private ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");
        imgref = FirebaseDatabase.getInstance().getReference().child("Fotos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        buttonTakePicture = v.findViewById(R.id.buttonTakePicture);
        buttonUpload = v.findViewById(R.id.buttonUploadPicture);
        buttonSelect = v.findViewById(R.id.buttonSelect);
        imageView = v.findViewById(R.id.imageView);
        progressBar = v.findViewById(R.id.progressBar);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonTakePicture.setOnClickListener(this::buttonTakePicture);
        buttonSelect.setOnClickListener(this::buttonSelect);
        buttonUpload.setOnClickListener(this::buttonUpload);
    }

    private void buttonUpload(View view) {
        comprimirImagen();
        subirImagen();
    }

    private void buttonSelect(View view) {
        CropImage.startPickImageActivity(requireContext(), this);
    }

    private void buttonTakePicture(View view) {
        dispatchTakePictureIntent();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
            recortarImagen(imageUri);

        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                url = new File(result.getUri().getPath());
                Picasso.with(requireContext()).load(url).into(imageView);
            }
        }
    }

    private void recortarImagen(Uri imageUri) {
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(640, 480)
                .setAspectRatio(2, 1)
                .start(requireContext(), MainFragment.this);
    }

    private void comprimirImagen() {
        try {
            thumbBitmap = new Compressor(requireContext())
                    .setMaxHeight(125)
                    .setMaxWidth(125)
                    .setQuality(90)
                    .compressToBitmap(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        thumb_byte = byteArrayOutputStream.toByteArray();
    }

    private void subirImagen() {
        progressBar.setVisibility(View.VISIBLE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        nameImage = timestamp + ".jpg";

        StorageReference ref = storageReference.child(nameImage);
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("clave1", "valor1")
                .setCustomMetadata("clave2", "valor2")
                .build();
        UploadTask uploadTask = ref.putBytes(thumb_byte, metadata);
        Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()){
                throw Objects.requireNonNull(task.getException());
            }
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            Uri downloadUri = task.getResult();
            imgref.push().child("urlfoto").setValue(downloadUri.toString());
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), "Imagen subida", Toast.LENGTH_SHORT).show();
        });

    }


}