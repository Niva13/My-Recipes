package com.example.recipes.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.recipes.R;
import com.example.recipes.Recepie;
import com.example.recipes.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewRecipeUrlFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRecipeUrlFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewRecipeUrlFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewRecipeUrlFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static NewRecipeUrlFrag newInstance(String param1, String param2) {
        NewRecipeUrlFrag fragment = new NewRecipeUrlFrag();
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
        View view =  inflater.inflate(R.layout.fragment_new_recipe_url, container, false);
        EditText NameET = view.findViewById(R.id.Name);
        EditText URLET = view.findViewById(R.id.URL);
        Button SaveRecipe = view.findViewById(R.id.SaveRecipeByURL);
        MainActivity mainActivity = (MainActivity) getActivity();

        SaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = NameET.getText().toString();
                String URL = URLET.getText().toString().replace(".m" , "");


                if(Name.isEmpty() || URL.isEmpty())
                {
                    Toast.makeText(mainActivity, "Fill all the fields!", Toast.LENGTH_LONG).show();
                }
                else {
                    Recepie recepie = new Recepie(Name, URL, "");
                    mainActivity.saveRecepie(recepie);

                    Toast.makeText(mainActivity, "Recepie saved", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.action_newRecipeUrlFrag_to_homePageFrag);
                }
            }
        });



        return view;
    }
}