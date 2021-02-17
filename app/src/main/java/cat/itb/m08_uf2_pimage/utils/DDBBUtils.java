package cat.itb.m08_uf2_pimage.utils;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import cat.itb.m08_uf2_pimage.models.MarkerItem;

public class DDBBUtils {
    private final StorageReference storageReference;
    private final DatabaseReference imgref;
    private final DatabaseReference markerRef;

    public DDBBUtils() {
        storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");
        imgref = FirebaseDatabase.getInstance().getReference().child("Fotos");
        markerRef = FirebaseDatabase.getInstance().getReference("markers");
    }

    //TODO get uri
    private void subirImagen(byte[] img, LatLng latLng) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        String nameImage = timestamp + ".jpg";
        StorageReference ref = storageReference.child(nameImage);
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("lat", String.valueOf(latLng.latitude))
                .setCustomMetadata("lng", String.valueOf(latLng.longitude))
                .build();
        UploadTask uploadTask = ref.putBytes(img, metadata);
        Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            Uri downloadUri = task.getResult();
            imgref.push().child("urlfoto").setValue(downloadUri.toString());
        });

    }

    private void uploadMarker(MarkerItem markerItem) {
        String key = markerRef.push().getKey();
        markerItem.setId(key);
        markerRef.child(key).setValue(markerItem);
    }
}
