package com.example.kisan.Disease;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetector;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.timqi.sectorprogressview.ColorfulRingProgressView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.CAMERA;


public class DiseaseDetection extends AppCompatActivity {

    TextView text;
    TextureView textureView;
    CameraManager cameraManager;
    int cameraFacing;
    private Size previewSize;
    private String cameraId;
    private CameraDevice.StateCallback stateCallback;
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;
    CameraDevice cameraDevice;
    TextureView.SurfaceTextureListener surfaceTextureListener;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder captureRequestBuilder;
    ImageView cameraButton, close, gallaryImage, gallaryButton;
    LinearLayout llResults;
    ColorfulRingProgressView chartView;
    ProgressDialog dialog;

    FirebaseVisionObjectDetectorOptions options;
    FirebaseVisionObjectDetector objectDetector;

    FirebaseVisionOnDeviceAutoMLImageLabelerOptions labelerOptions;
    FirebaseVisionImageLabeler labeler;
    private int PICK_IMG = 34343;
    public boolean isProcessingComplete = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_disease_detection);
        /*CheckPermission();*/

        text = findViewById(R.id.text1);
        textureView = findViewById(R.id.texture_view);
        close = findViewById(R.id.close);
        llResults = findViewById(R.id.ll_results);
        chartView = findViewById(R.id.percentage_view);
        gallaryButton = findViewById(R.id.gallary_btn);
        gallaryImage = findViewById(R.id.gallary_pic);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        cameraFacing = CameraCharacteristics.LENS_FACING_BACK;
        cameraButton = findViewById(R.id.camera_btn);

        setStateCallBacks();
        setSurfaceTextureListener();
        configureHostedModelSource();
        configureLocalModelSource();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llResults.setVisibility(View.INVISIBLE);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallaryImage.setVisibility(View.INVISIBLE);
                takePicture();
            }
        });

        gallaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picGallaryPhoto();
            }
        });

    }

    private void picGallaryPhoto() {

        startActivityForResult(getPickImageChooserIntent(), PICK_IMG);
    }


    private void setStateCallBacks() {
        stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice cameraDevice) {
                DiseaseDetection.this.cameraDevice = cameraDevice;
                createPreviewSession();
            }

            @Override
            public void onDisconnected(CameraDevice cameraDevice) {
                cameraDevice.close();
                DiseaseDetection.this.cameraDevice = null;
            }

            @Override
            public void onError(CameraDevice cameraDevice, int error) {
                cameraDevice.close();
                DiseaseDetection.this.cameraDevice = null;
            }
        };
    }

    private void setSurfaceTextureListener() {
        surfaceTextureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                setUpCamera();
                openCamera();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        };
        textureView.setSurfaceTextureListener(surfaceTextureListener);
    }

    private void createPreviewSession() {
        try {
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(previewSurface);

            cameraDevice.createCaptureSession(Collections.singletonList(previewSurface),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            if (cameraDevice == null) {
                                return;
                            }

                            try {
                                CaptureRequest captureRequest = captureRequestBuilder.build();
                                DiseaseDetection.this.cameraCaptureSession = cameraCaptureSession;
                                DiseaseDetection.this.cameraCaptureSession.setRepeatingRequest(captureRequest,
                                        null, backgroundHandler);
                                //Log.d("a_response", "onConfigured: "+textureView.getBitmap());

                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {

                        }
                    }, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void openCamera() {


        try {
            if (ActivityCompat.checkSelfPermission(this, CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                cameraManager.openCamera(cameraId, stateCallback, backgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setUpCamera() {
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics =
                        cameraManager.getCameraCharacteristics(cameraId);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
                        cameraFacing) {
                    StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(
                            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    previewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];
                    this.cameraId = cameraId;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openBackgroundThread() {
        backgroundThread = new HandlerThread("camera_background_thread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    private void configureHostedModelSource() {

        final FirebaseAutoMLRemoteModel remoteModel =
                new FirebaseAutoMLRemoteModel.Builder("CornLeaves_01").build();
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Success.
                    }
                });
        final FirebaseAutoMLLocalModel localModel = new FirebaseAutoMLLocalModel.Builder()
                .setAssetFilePath("dataset/manifest.json")
                .build();

        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isDownloaded) {
                        FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder optionsBuilder;
                        if (isDownloaded) {
                            optionsBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel);
                        } else {
                            optionsBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel);
                        }
                        FirebaseVisionOnDeviceAutoMLImageLabelerOptions options = optionsBuilder
                                .setConfidenceThreshold(0.0f)
                                // Evaluate your model in the Firebase console
                                // to determine an appropriate threshold.
                                .build();

                        try {
                            labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
                        } catch (FirebaseMLException e) {
                            // Error.
                        }
                    }
                });
    }

    private void configureLocalModelSource() {
        // [Ml kit_local_model_source]
    }

    @Override
    protected void onResume() {
        super.onResume();
        openBackgroundThread();
        if (textureView.isAvailable()) {
            setUpCamera();
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeCamera();
        closeBackgroundThread();
    }

    private void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }

        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void closeBackgroundThread() {
        if (backgroundHandler != null) {
            backgroundThread.quitSafely();
            backgroundThread = null;
            backgroundHandler = null;
        }
    }

    private void processImg(Image bitmap) throws CameraAccessException {

        final FirebaseVisionImage image = FirebaseVisionImage.fromMediaImage(bitmap,
                getRotationCompensation(cameraId, DiseaseDetection.this, DiseaseDetection.this));

        try {
            labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            // Task completed successfully
                            // ...
                            dialog.dismiss();

                            float predictions = 0;
                            String names = "";
                            for (FirebaseVisionImageLabel label : labels) {
                                String name = label.getText();
                                float confidence = label.getConfidence() * 100;
                                if (predictions == 0) {
                                    predictions = confidence;
                                    names = name;
                                } else if (confidence > predictions) {
                                    predictions = confidence;
                                    names = name;
                                }

                            }
                            Log.d("result", "onSuccess: prediction: " + predictions + " name: " + names);
                            if (predictions > 60) {


                                int percentage = (int) predictions;
                                chartView.setPercent(predictions);
                                text.setText(names + " " + percentage + "%");

                                llResults.setVisibility(View.VISIBLE);
                                Toast.makeText(DiseaseDetection.this, "name " + names + " confidence" + percentage, Toast.LENGTH_SHORT).show();

                            } else {
                                chartView.setPercent(0);
                                text.setText("Sorry! Couldn't predict.");

                                llResults.setVisibility(View.VISIBLE);
                            }

                            isProcessingComplete = true;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                            isProcessingComplete = true;
                            dialog.dismiss();
                            chartView.setPercent(0);
                            text.setText("Sorry! Couldn't predict.");

                            llResults.setVisibility(View.VISIBLE);
                            Log.d("result", "onFailure: couldn't predict");
                        }
                    });
        } catch (Exception e) {
            Log.d("result", "onSuccess: prediction: " + e.getMessage());
            isProcessingComplete = true;
            dialog.dismiss();
            Toast.makeText(DiseaseDetection.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // [START get_rotation]
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    // [END get_rotation]
    /*
     * Get the angle by which an image must be rotated given the device's current orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // On most devices, the sensor orientation is 90 degrees, but for some
        // devices it is 270 degrees. For devices with a sensor orientation of
        // 270, rotate the image an additional 180 ((270 + 270) % 360) degrees.
        CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        // Return the corresponding FirebaseVisionImageMetadata rotation value.
        int result;
        switch (rotationCompensation) {
            case 0:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                Log.e("a_response", "Bad rotation value: " + rotationCompensation);
        }
        return result;
    }

    protected void takePicture() {
        if (null == cameraDevice) {
            Log.e("a_res", "cameraDevice is null");
            return;
        }
        dialog = new ProgressDialog(DiseaseDetection.this);
        dialog.setMessage("Processing the image");
        dialog.show();
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if (characteristics != null) {
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.YUV_420_888);
            }
            int width = 640;
            int height = 480;
            if (jpegSizes != null && 0 < jpegSizes.length) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.YUV_420_888, 3);
            List<Surface> outputSurfaces = new ArrayList<Surface>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            // Orientation
            int rotation = getWindowManager().getDefaultDisplay().getRotation();

            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
            final File file = new File(Environment.getExternalStorageDirectory() + "/pic.jpg");
            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;
                    try {
                        image = reader.acquireLatestImage();
                        Log.d("result", "onImageAvailable: " + image.toString());

                        processImg(image);
                    } catch (Exception e) {
                        Log.d("result", "onImageAvailable: " + e.getMessage());

                    } finally {
                        image.close();
                    }
                }

                private void save(byte[] bytes) throws IOException {
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream(file);
                        output.write(bytes);
                    } finally {
                        if (null != output) {
                            output.close();
                        }
                    }
                }
            };
            reader.setOnImageAvailableListener(readerListener, backgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    Toast.makeText(DiseaseDetection.this, "Saved:" + file, Toast.LENGTH_SHORT).show();
                    createPreviewSession();
                }
            };
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, backgroundHandler);

                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }
            }, backgroundHandler);
        } catch (CameraAccessException e) {
            Log.d("result", "takePicture: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void updatePreview() {
    }

    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "gallary.png"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        String selectedImagePath = null;
        if (resultCode == RESULT_OK && requestCode == PICK_IMG) {
            dialog = new ProgressDialog(DiseaseDetection.this);
            dialog.setMessage("Processing the image");
            dialog.show();
            String filePath = getImageFilePath(data);
            if (filePath != null) {
                ByteArrayOutputStream byteArrayOutputStreamObject;
                byteArrayOutputStreamObject = new ByteArrayOutputStream();
                bitmap = BitmapFactory.decodeFile(filePath); // load
                bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 800, true);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStreamObject);
                byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
                //covertedIMG = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
                //UploadImage();
                gallaryImage.setVisibility(View.VISIBLE);
                gallaryImage.setImageBitmap(bitmap);
                //preferencesHelper.setProfileImg(covertedIMG);

            }
            assert data != null;
            if (data.getData() != null) {

                processImgFromGallary(data.getData());

            }

        }

    }

    private void processImgFromGallary(Uri uri) {
        FirebaseVisionImage image;
        try {
            image = FirebaseVisionImage.fromFilePath(DiseaseDetection.this, uri);
            labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            // Task completed successfully
                            // ...
                            dialog.dismiss();
                            float predictions = 0;
                            String names = "";
                            for (FirebaseVisionImageLabel label : labels) {
                                String name = label.getText();
                                float confidence = label.getConfidence() * 100;
                                if (predictions == 0) {
                                    predictions = confidence;
                                    names = name;
                                } else if (confidence > predictions) {
                                    predictions = confidence;
                                    names = name;
                                }

                                //frameImg.setImageBitmap(image.getBitmap());
                            }
                            if (predictions > 40) {

                                int percentage = (int) predictions;
                                chartView.setPercent(predictions);
                                text.setText(names + " " + percentage + "%");
//                                Animation animation = AnimationUtils.loadAnimation(ObjectDetectionSample.this, R.anim.slide_from_bottom);
//                                llResults.setAnimation(animation);
                                llResults.setVisibility(View.VISIBLE);
                                Toast.makeText(DiseaseDetection.this, "name " + names + " confidence" + percentage, Toast.LENGTH_SHORT).show();

                            } else {
                                chartView.setPercent(0);
                                text.setText("Sorry! Couldn't predict.");
//                                Animation animation = AnimationUtils.loadAnimation(ObjectDetectionSample.this, R.anim.slide_from_bottom);
//                                llResults.setAnimation(animation);
                                llResults.setVisibility(View.VISIBLE);
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                            dialog.dismiss();
                            chartView.setPercent(0);
                            text.setText("Sorry! Couldn't predict.");
//                            Animation animation = AnimationUtils.loadAnimation(ObjectDetectionSample.this, R.anim.slide_from_bottom);
//                            llResults.setAnimation(animation);
                            llResults.setVisibility(View.VISIBLE);
                        }
                    });

        } catch (Exception e) {
            dialog.dismiss();
            Toast.makeText(DiseaseDetection.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("result", "processImgFromGallary: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    /*public void CheckPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",getPackageName(),null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }*/
}