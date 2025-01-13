package com.example.recipes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.DataCallback;
import com.example.recipes.R;
import com.example.recipes.activities.MainActivity;

import java.util.ArrayList;
import com.example.recipes.DataCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFrag extends Fragment  {

    private ArrayList<DataModel> FavoriteDataSet;

    private CustomeAdapter adapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritesFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFrag newInstance(String param1, String param2) {
        FavoritesFrag fragment = new FavoritesFrag();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();

        RecyclerView Favorite = view.findViewById(R.id.RVFavorite);

        mainActivity.getFavorite(this.getContext(), view, new DataCallback() {
            @Override
            public void onDataReady(ArrayList<DataModel> data) {
                if (data != null)
                {

                    FavoriteDataSet = data;
                    adapter = new CustomeAdapter (FavoritesFrag.this.getContext(),FavoriteDataSet);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(FavoritesFrag.this.getContext());

                    if (Favorite == null) {

                    }
                    else{
                        Favorite.setLayoutManager(layoutManager);
                        Favorite.setItemAnimator(new DefaultItemAnimator());
                        Favorite.setAdapter(adapter);
                    }

                }

            }
        });





        return view;
    }
}