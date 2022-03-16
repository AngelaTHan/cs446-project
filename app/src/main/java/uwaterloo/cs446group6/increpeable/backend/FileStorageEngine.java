package uwaterloo.cs446group6.increpeable.backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import uwaterloo.cs446group6.increpeable.NotifyActivity;

public class FileStorageEngine {
    private StorageReference imagesRef;
    private final String LOG_TAG = "FileStorageEngine";
    long FIFTY_MEGABYTE = 1024 * 1024 * 50; // max MBs to download

    public FileStorageEngine () {
        imagesRef = FirebaseStorage.getInstance().getReference().child("images");
    }
    public void downloadImage (ImageView imageView, String imageName, NotifyActivity currentActivity) {
        System.out.println (imageName);
        StorageReference imageRef = imagesRef.child(imageName);
        imageRef.getBytes(FIFTY_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
                Log.i(LOG_TAG, "Image Download Succeed: " + imageName);
                currentActivity.notifyActivity(ReturnFromFunction.GET_IMAGE_VIEW_BY_NAME);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(LOG_TAG, "Image Download Failed: " + exception.getMessage());
            }
        });
    }

    public void uploadImage(ImageView imageView, String imageName, NotifyActivity currentActivity) {
        // Convert imageView to byte stream
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Upload byte stream
        StorageReference imageRef = imagesRef.child(imageName + ".jpg");
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(LOG_TAG, "Image Upload Failed: " + exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(LOG_TAG, "Image Upload Succeed: " + imageName);
                currentActivity.notifyActivity(ReturnFromFunction.UPLOAD_IMAGEVIEW);
            }
        });
    }
}
