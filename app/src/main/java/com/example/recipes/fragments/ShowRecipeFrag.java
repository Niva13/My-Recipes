package com.example.recipes.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipes.Ingredient;
import com.example.recipes.R;
import com.example.recipes.Recepie;
import com.example.recipes.activities.MainActivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowRecipeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowRecipeFrag extends Fragment {

    private ImageView photo;
    private String photoPath;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowRecipeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowRecipeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowRecipeFrag newInstance(String param1, String param2) {
        ShowRecipeFrag fragment = new ShowRecipeFrag();
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
            DataModel dataModel = (DataModel) getArguments().getSerializable("recipeObject");

            if (dataModel != null) {
                String recipeName = dataModel.getName();
                DataModel recipeDetails = dataModel.getDatamodel();

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        super.onViewCreated(view, savedInstanceState);

        ImageView star = view.findViewById(R.id.star);
        final boolean[] isRated = {false};

        TextView recipeNameTextView = view.findViewById(R.id.RecipeName);
        TextView recipeDetailsTextView = view.findViewById(R.id.TheRecipe);
        photo = view.findViewById(R.id.imageView);
        ScrollView scrollView = view.findViewById(R.id.scrollView);


        Button close = view.findViewById(R.id.buttonClose);


        Bundle args = getArguments();

        if (args != null) {
            String recipeName = args.getString("recipeName");
            Object details = args.get("recipeObject");


            DataModel model = (DataModel) details;
            Recepie recepie = model.getRecepie();

            recipeNameTextView.setText(recepie.getName());

            if(!(recepie.getURL() == null))
            {

                WebView webView = view.findViewById(R.id.webview);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }

                //String W = ("https://en.wikipedia.org/wiki/Labrador_Retriever");
                String W = recepie.getURL();
                webView.loadUrl(W);



            }else if(!(recepie.getPhotoPath() == null)){

                scrollView.setVisibility(View.GONE);
                photo.setVisibility(View.VISIBLE);
                photoPath = "file://"+recepie.getPhotoPath();



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                            == PackageManager.PERMISSION_GRANTED) {
                        initializePhoto(view);
                    } else {
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 101);
                    }
                }
                else {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        initializePhoto(view);
                    } else {
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                    }
                }





            }else{

                ArrayList<Ingredient> Ingredients = new ArrayList<>();
                Ingredients = recepie.getIngredients();

                for(Ingredient Ingredient : Ingredients) {
                    recipeDetailsTextView.setText(recipeDetailsTextView.getText().toString() + Ingredient.getIngName() +" "+
                            Ingredient.getAmount() + " " +Ingredient.getUnit() + "\n\n");


                }

            }



        }



        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRated[0] = !isRated[0];
                star.setImageResource(isRated[0] ? R.drawable.empy_star : R.drawable.yellow_star);

            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_showRecipeFrag_to_homePageFrag);
            }
        });

        return view;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with accessing the image
                initializePhoto(getView());
            } else {
                Toast.makeText(ShowRecipeFrag.this.getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializePhoto(View view) {
        Uri photoUri = Uri.parse(photoPath);
        photo.setImageURI(photoUri);
    }


}