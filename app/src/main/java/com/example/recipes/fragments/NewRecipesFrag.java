package com.example.recipes.fragments;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.recipes.Ingredient;
import com.example.recipes.R;
import com.example.recipes.Recepie;
import com.example.recipes.activities.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewRecipesFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRecipesFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //private Spinner unit;
    private Spinner NumofItems;
    int Num_Of_Items=0;
    private Recepie recipe;
    private ArrayList<Ingredient>Ingredients;
    private String NumOfIng;
    private int last_id;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewRecipesFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewRecipesFrag newInstance(String param1, String param2) {
        NewRecipesFrag fragment = new NewRecipesFrag();
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
        last_id=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.new_recipes_frag, container, false);
        Ingredients = new ArrayList<Ingredient>();
        EditText RecepieTitle = view.findViewById(R.id.EtRecipeName);
        Button SaveRecipe = view.findViewById(R.id.SaveRecipe);
        Button SaveNumItems = view.findViewById(R.id.SaveNumItems);
        NumofItems = view.findViewById(R.id.numOfItems);
        //unit = view.findViewById(R.id.Unit);
        LinearLayout ll2 = view.findViewById(R.id.LL2);


        ArrayList<String> UnitList = new ArrayList<>();
        UnitList.add("Cup");
        UnitList.add("kg");
        UnitList.add("gr");
        UnitList.add("ml");
        UnitList.add("teaspoon");
        UnitList.add("spoon");

        ArrayAdapter<String> UnitAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, UnitList);

        UnitAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        SaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (RecepieTitle.getText().toString().isEmpty()) {
                    Toast.makeText(NewRecipesFrag.this.getContext(),"You need to write a name of the recepie",Toast.LENGTH_LONG).show();
                } else {

                    int k = last_id;

                    if(k!=0) {
                        int resId1 = 0, resId2 = 0, resId3 = 0;
                        int t = 1;

                        for (int i = 1; i <= k; i++) {
                            resId1 = t;
                            t++;
                            resId2 = t;
                            t++;
                            resId3 = t;
                            t++;

                            EditText DName = view.findViewById(resId1);
                            EditText DAmount = view.findViewById(resId2);
                            Spinner DUnit = view.findViewById(resId3);

                            String name = DName.getText().toString();
                            String amount = DAmount.getText().toString();
                            String unit = DUnit.getSelectedItem().toString();

                            Ingredient ingredient = new Ingredient(name, amount, unit);
                            Ingredients.add(ingredient);
                        }

                        recipe = new Recepie(RecepieTitle.getText().toString(), Ingredients);
                        mainActivity.saveRecepie(recipe);

                        Navigation.findNavController(v).navigate(R.id.action_newRecipesFrag_to_homePageFrag);


                    }
                }
            }
        });

        ArrayList<String> ItemsList = new ArrayList<>();
        for(int i =1; i<=20;i++){
            ItemsList.add(String.valueOf(i));
        }


        NumofItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NumOfIng = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> ItemsAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, ItemsList);

        ItemsAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        NumofItems.setAdapter(ItemsAdapter);


        SaveNumItems.setOnClickListener(new View.OnClickListener() { // FIX PROBLEM OF ID'S FOR THE XML
            @Override
            public void onClick(View v) {
                Context context=NewRecipesFrag.this.getContext();
                int hefresh = 0;



                if(Num_Of_Items != 0){
                    hefresh = Num_Of_Items;
                }


                Num_Of_Items=Integer.valueOf(NumofItems.getSelectedItem().toString());
                if(hefresh != 0){
                    if((Num_Of_Items - hefresh) > 0) {
                        for (int i = Num_Of_Items; i < (Num_Of_Items+(Num_Of_Items-hefresh)); i++)
                        {

                            LinearLayout L = new LinearLayout(context);
                            L.setOrientation(LinearLayout.HORIZONTAL);
                            //L.setId(i);


                            int resId1 = View.generateViewId();
                            String generatedIdName = String.valueOf(resId1);

                            EditText name = new EditText(context);
                            name.setHint(generatedIdName);
                            name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT , 1));
                            name.setId(resId1);


                            int resId2 = View.generateViewId();
                            String generatedIdAmount = String.valueOf(resId2);

                            EditText amount = new EditText(context);
                            amount.setHint(generatedIdAmount);
                            amount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                            amount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT , 1));
                            amount.setId(resId2);


                            int resId3 = View.generateViewId();
                            String generatedIdSpinner = String.valueOf(resId3);

                            Spinner spinner = new Spinner(context);
                            spinner.setAdapter(UnitAdapter);
                            spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT , 1));
                            spinner.setId(resId3);


                            L.addView(name);
                            L.addView(amount);
                            L.addView(spinner);
                            ll2.addView(L);

                        }
                    }else{
                        for(int i =hefresh-1; i>= Num_Of_Items;i--){
                            ll2.removeView(ll2.getChildAt(i));
                        }
                    }
                }else{
                    for (int i = 0; i < Num_Of_Items; i++) {

                        LinearLayout L = new LinearLayout(context);
                        L.setOrientation(LinearLayout.HORIZONTAL);
                        //L.setId(i);


                        int resId1 = View.generateViewId();
                        String generatedIdName = String.valueOf(resId1);

                        EditText name = new EditText(NewRecipesFrag.this.getContext());
                        name.setHint(generatedIdName);
                        name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT , 1));
                        name.setId(resId1);



                        int resId2 = View.generateViewId();
                        String generatedIdAmount = String.valueOf(resId2);

                        EditText amount = new EditText(context);
                        amount.setHint(generatedIdAmount);
                        amount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                        amount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT , 1));
                        amount.setId(resId2);



                        int resId3 = View.generateViewId();
                        String generatedIdSpinner = String.valueOf(resId3);

                        Spinner spinner = new Spinner(context);
                        spinner.setAdapter(UnitAdapter);
                        spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT , 1));
                        spinner.setId(resId3);


                        L.addView(name);
                        L.addView(amount);
                        L.addView(spinner);
                        ll2.addView(L);

                    }
                }

                last_id = Num_Of_Items+last_id;
            }
        });



        return view;
    }
}