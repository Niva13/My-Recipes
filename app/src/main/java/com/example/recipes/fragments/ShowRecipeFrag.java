package com.example.recipes.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

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
        ImageView photo = view.findViewById(R.id.imageView);
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

                /*webView.setWebViewClient(new WebViewClient() {
                                    @SuppressWarnings("deprecation")
                                    @Override
                                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                        Toast.makeText(ShowRecipeFrag.this.getContext(), description, Toast.LENGTH_SHORT).show();
                                    }
                                    @TargetApi(android.os.Build.VERSION_CODES.M)
                                    @Override
                                    public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                                        // Redirect to deprecated method, so you can use it in all SDK versions
                                        onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                                    }
                                });*/

            }else if(!(recepie.getPhotoPath() == null)){

                scrollView.setVisibility(View.GONE);
                photo.setVisibility(View.VISIBLE);
                photo.setImageResource(R.drawable.yellow_star);



            }else{

                ArrayList<Ingredient> Ingredients = new ArrayList<>();
                Ingredients = recepie.getIngredients();

                for(Ingredient Ingredient : Ingredients) {
                    recipeDetailsTextView.setText(recipeDetailsTextView.getText().toString() + Ingredient.getIngName() +" "+
                            Ingredient.getAmount() + " " +Ingredient.getUnit() + "\n\n");


                }

            }

            //String  recipePhotoPath = args.getString("recipeName");



            //recipeDetailsTextView.setText(recipeURL+ "\n" +recipePhotoPath);

        }


        // Retrieve the data from the Bundle
        /*if (bundle != null) {
            String recipeTitle = bundle.getString("recipeTitle");
            //String recipeDetails = getArguments().getString("recipeDetails");

            // Set the data to the views
            recipeNameTextView.setText(recipeTitle);
            //recipeDetailsTextView.setText(recipeDetails);
        }*/


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

}