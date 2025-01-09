package com.example.recipes.fragments;

import static androidx.camera.video.internal.compat.Api23Impl.build;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipes.R;
import com.example.recipes.Recepie;
import com.example.recipes.User;
import com.example.recipes.activities.MainActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewRecipePicFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRecipePicFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ProcessCameraProvider cameraProvider;

    private boolean photoSaved = false;
    private String RName= "";
    private String RPath= "";


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ExecutorService cameraExecutor;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private String picName;
    private File photoFile;
    private Recepie recepie;
    private User user;

    public NewRecipePicFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewRecipePicFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static NewRecipePicFrag newInstance(String param1, String param2) {
        NewRecipePicFrag fragment = new NewRecipePicFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        cameraExecutor = Executors.newSingleThreadExecutor();
        Log.d("NewRecipePicFrag", "cameraExecutor initialized");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_recipe_pic, container, false);
        Button uploadPic = view.findViewById(R.id.BuAddPhoto);
        EditText recipeName = view.findViewById(R.id.EtRecipeNamePic);
        MainActivity mainActivity = (MainActivity)getActivity();

        // Check for camera permissions
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initializeCamera(view);  // Initialize CameraX if permission is granted
        } else {
            // Request permission
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA}, 1001);
        }

        uploadPic.setOnClickListener(v -> {
            if (imageCapture == null || cameraExecutor == null) {
                Toast.makeText(requireContext(), "Camera is not ready", Toast.LENGTH_SHORT).show();
                return;
            }

            picName = recipeName.getText().toString();

            if (picName.isEmpty()) // need to check if there is another recipe with the same name.
            {
                Toast.makeText(NewRecipePicFrag.this.getContext(),"This name is already exist",Toast.LENGTH_LONG).show();
            }
            else {
                picName = picName+".jpg";
                photoFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), picName);
                ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

                imageCapture.takePicture(options, cameraExecutor, new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(requireContext(), "Trying to save the photo in database!!" , Toast.LENGTH_SHORT).show());

                        String Name = picName.replace(".jpg", "");
                        String path = photoFile.getAbsolutePath();
                        path=path.replace("\n","");

                        recepie = new Recepie(Name,path);
                        try{
                            if(path != null)
                            {
                                mainActivity.saveRecepie(recepie);
                                //Navigation.findNavController(v).navigate(R.id.action_newRecipePicFrag_to_homePageFrag);
                                return;
                            }

                        }catch(Exception e){
                            Log.d("Error Navigation","there is a problem  with navigation");
                            return;
                        }

                        Log.d("Error Debug","End of try.");
                    }
                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(requireContext(), "Error capturing photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
    }



    public void initializeCamera(View view) {
        // Check if the view is valid (in case the fragment's view has been destroyed)
        if (view == null) return;

        // Retrieve the PreviewView from the layout (assuming you have a PreviewView in your XML)
        previewView = view.findViewById(R.id.previewView);

        // Get the ProcessCameraProvider instance asynchronously
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        // Add a listener to handle the CameraProvider once it's ready
        cameraProviderFuture.addListener(() -> {
            try {
                // Get the CameraProvider instance
                cameraProvider = cameraProviderFuture.get();

                // Bind camera use cases (Preview, ImageCapture, etc.) here
                bindCameraUseCases();
            } catch (ExecutionException | InterruptedException e) {
                // Handle any exceptions
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }


    public void bindCameraUseCases() {

        if (cameraProvider==null || previewView == null)
        {
            return;
        }
        // Build the Preview use case
        Preview preview = new Preview.Builder().build();

        // Set the surface provider to the PreviewView
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Create an ImageCapture use case (for capturing photos)
        imageCapture = new ImageCapture.Builder().build();

        // Bind the use cases to the camera provider and the lifecycle
        cameraProvider.bindToLifecycle(getViewLifecycleOwner(), CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) { // Camera permission request code
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initialize the camera
                initializeCamera(getView());
            } else {
                // Handle the case when permission is denied (e.g., show a message)
                Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show();
            }
        }

    }
}










