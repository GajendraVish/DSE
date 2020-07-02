package com.imageprovider.imagePicker;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import android.util.Log;

import com.bhaskar.constants.AndroidConstants;
import com.imageprovider.ImagePickerProvider;
import com.imageprovider.imageCompression.ImageCompression;
import com.imageprovider.imageCompression.ImageCompressionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class ImagePicker {
    private Activity activity;
    private Fragment fragment;
    private boolean isCompress = true, isCamera = true, isGallery = true;
    public static final int SELECT_IMAGE = 121;
    private ImageCompressionListener imageCompressionListener;

    private final String TAG = "ImagePicker";

    public ImagePicker withActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public ImagePicker withFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public ImagePicker chooseFromCamera(boolean isCamera) {
        this.isCamera = isCamera;
        return this;
    }

    public ImagePicker chooseFromGallery(boolean isGallery) {
        this.isGallery = isGallery;
        return this;
    }

    public ImagePicker withCompression(boolean isCompress) {
        this.isCompress = isCompress;
        return this;
    }

    public void start() {
        if (activity != null && fragment != null) {
            throw new IllegalStateException("Cannot add both activity and fragment");
        } else if (activity == null && fragment == null) {
            throw new IllegalStateException("Activity and fragment both are null");
        } else {
            if (!checkPermission()) {
//                throw new IllegalStateException("Write External Permission not found");
                askReadWriteStoragePermission();
            } else {
                if (!isCamera && !isGallery) {
                    throw new IllegalStateException("select source to pick image");
                } else {
                    if (activity != null)
                        activity.startActivityForResult(getPickImageChooserIntent(), SELECT_IMAGE);
                    else
                        fragment.startActivityForResult(getPickImageChooserIntent(), SELECT_IMAGE);
                }
            }
        }
    }

    private boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        int readExternalStoragePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeExternalStoragePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return currentAPIVersion < Build.VERSION_CODES.M || (readExternalStoragePermission == PackageManager.PERMISSION_GRANTED && writeExternalStoragePermission == PackageManager.PERMISSION_GRANTED);
    }

    private void askReadWriteStoragePermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, AndroidConstants.EXTERNAL_STORAGE_PERMISSION);
    }

    private Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = activity != null ? activity.getPackageManager() : fragment.getActivity().getPackageManager();

        if (isCamera) {
            // collect all camera intents
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
            for (ResolveInfo res : listCam) {
                Intent intent = new Intent(captureIntent);
                intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                intent.setPackage(res.activityInfo.packageName);
                if (outputFileUri != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                }
                allIntents.add(intent);
            }
        }

        if (isGallery) {
            // collect all gallery intents
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
            for (ResolveInfo res : listGallery) {
                Intent intent = new Intent(galleryIntent);
                intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                intent.setPackage(res.activityInfo.packageName);
                allIntents.add(intent);
            }
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = activity != null ? activity.getExternalFilesDir("") : fragment.getActivity().getExternalFilesDir("");
        if (getImage != null) {
            //outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
            outputFileUri = ImagePickerProvider.getUriForFile(activity != null ? activity : fragment.getActivity(),
                    activity != null ? activity.getApplicationContext().getPackageName() + ".imagepickerprovider" : fragment.getActivity().getApplicationContext().getPackageName() + ".provider",
                    new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    private String getPickImageResultFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;
        //Log.e("data", +"");
        /*if (data != null) {
            isCamera = false;
            String action = data.getAction();
            isCamera = action != null && action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        } else {
            isCamera = true;
        }*/

        //Log.e("isCamera", isCamera ? "true" : "false");
        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getRealPathFromURI(data.getData());
        //return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = activity != null ?
                activity.getContentResolver().query(contentUri, proj, null, null, null) : fragment.getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getImageFilePath(Intent data) {
        try {
            if (!isCompress)
                return getPickImageResultFilePath(data);
            else {
                new ImageCompression(activity != null ? activity : fragment.getActivity(),
                        getPickImageResultFilePath(data), imageCompressionListener).execute();
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
            return null;
        }
    }

    public void addOnCompressListener(ImageCompressionListener imageCompressionListener) {
        this.imageCompressionListener = imageCompressionListener;
    }
}
