package cat.itb.m08_uf2_pimage.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class ImageUtils {
    private final Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public byte[] comprimirImagen(File file) throws IOException {
        Bitmap thumbBitmap = new Compressor(context)
                .setMaxHeight(125)
                .setMaxWidth(125)
                .setQuality(90)
                .compressToBitmap(file);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void recortarImagen(Uri imageUri, Fragment fragment) {
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(640, 480)
                .setAspectRatio(2, 1)
                .start(context, fragment);
    }

}
