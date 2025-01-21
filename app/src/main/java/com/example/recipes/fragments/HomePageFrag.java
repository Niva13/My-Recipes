package com.example.recipes.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.recipes.DataCallback;
import com.example.recipes.R;
import com.example.recipes.activities.MainActivity;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFrag extends Fragment {

    private ArrayList<DataModel>dataset=new ArrayList<>();
    private ArrayList<DataModel> favoritesSet=new ArrayList<>();
    private CustomeAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        });
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFrag newInstance(String param1, String param2) {
        HomePageFrag fragment = new HomePageFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_frag, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();


        Button AddRecipe = view.findViewById(R.id.BuAddRecipe);
        Button AddRecipePic = view.findViewById(R.id.BuAddRecipePic);
        Button AddRecipeUrl = view.findViewById(R.id.BuAddRecipeUrl);
        Button Favorites = view.findViewById(R.id.BuFavorites);
        RecyclerView RecipeRecycleView = view.findViewById(R.id.RVRecipes);

        mainActivity.getDataSet(HomePageFrag.this.getContext(), view, new DataCallback() {
            @Override
            public void onDataReady(ArrayList<DataModel> data) {
                if (data != null) {
                    dataset = data;

                    adapter = new CustomeAdapter (HomePageFrag.this.getContext(),dataset);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(HomePageFrag.this.getContext());

                    if (RecipeRecycleView == null) {

                    }
                    else{
                        RecipeRecycleView.setLayoutManager(layoutManager);
                        RecipeRecycleView.setItemAnimator(new DefaultItemAnimator());
                        RecipeRecycleView.setAdapter(adapter);
                    }

                } else {

                }
            }
        });




        mainActivity.getFavoriteSet(HomePageFrag.this.getContext(), view, new DataCallback() {
            @Override
            public void onDataReady(ArrayList<DataModel> data) {
                if (data != null)
                {
                    favoritesSet = data;
                }
            }
        });



        EditText SearchRecipe = view.findViewById(R.id.ETSearchRecipe);

        AddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homePageFrag_to_newRecipesFrag);
            }
        });

        AddRecipePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homePageFrag_to_newRecipePicFrag);
            }
        });


        AddRecipeUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homePageFrag_to_newRecipeUrlFrag);

            }
        });

        SearchRecipe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        Favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(dataset.isEmpty())){
                Navigation.findNavController(view).navigate(R.id.action_homePageFrag_to_favoritesFrag);
                }
                else {
                    Toast.makeText(mainActivity, "There are not recipes at all!", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }


}