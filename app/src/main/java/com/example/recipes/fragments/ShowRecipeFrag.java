package com.example.recipes.fragments;

import android.Manifest;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.recipes.DataCallback;
import com.example.recipes.Ingredient;
import com.example.recipes.R;
import com.example.recipes.Recepie;
import com.example.recipes.activities.MainActivity;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowRecipeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowRecipeFrag extends Fragment {

    private ImageView photo;
    private String photoPath;

    boolean isRated;
    private Recepie recepie;


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
        ImageView trash = view.findViewById(R.id.delete);

        TextView recipeNameTextView = view.findViewById(R.id.RecipeName);
        TextView recipeDetailsTextView = view.findViewById(R.id.TheRecipe);
        photo = view.findViewById(R.id.imageView);
        ScrollView scrollView = view.findViewById(R.id.scrollView);
        Button close = view.findViewById(R.id.buttonClose);
        Bundle args = getArguments();

        if (args != null) {
            Object details = args.get("recipeObject");
            DataModel model = (DataModel) details;
            recepie = model.getRecepie();
            isRated = recepie.isFavorites();

            if(isRated){
                star.setImageResource(R.drawable.yellow_star);
            }else{
                star.setImageResource(R.drawable.empy_star);
            }
            recipeNameTextView.setText(recepie.getName());

            if(!(recepie.getURL() == null))
            {
                String W = recepie.getURL();
                Log.d("result" , W);

                if(!(W == null)) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(W));
                    startActivity(browserIntent);
                }

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
                    recipeDetailsTextView.setText(recipeDetailsTextView.getText().toString() + Ingredient.getIngName() +"   "+ Ingredient.getAmount() + "   " +Ingredient.getUnit() + "\n");
                }
            }
        }

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isRated = !isRated;


                MainActivity mainActivity = (MainActivity) getActivity();
                if(isRated){
                    star.setImageResource(R.drawable.yellow_star);
                    recepie.setFavorites(true);
                    mainActivity.addFavoriteRecipes(recepie);

                }else{
                    star.setImageResource(R.drawable.empy_star);
                    recepie.setFavorites(false);
                    mainActivity.RemoveFavoriteRecipes(recepie, new DataCallback() {
                        @Override
                        public void onDataReady(ArrayList<DataModel> data) {

                        }
                    });

                }

            }

        });

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.DeleteRecepie(recepie, new DataCallback() {
                    @Override
                    public void onDataReady(ArrayList<DataModel> data) {
                        try{
                            Navigation.findNavController(v).navigate(R.id.action_showRecipeFrag_to_homePageFrag);
                        }
                        catch (Exception e)
                        {

                        }
                    }
                });
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