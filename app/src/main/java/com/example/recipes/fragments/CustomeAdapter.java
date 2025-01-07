package com.example.recipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.activities.MainActivity;
import com.example.recipes.fragments.DataModel;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;
    private ArrayList<DataModel> dataSetFull;
    private Context context;
    public CustomeAdapter(Context context, ArrayList<DataModel> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
        this.dataSetFull = new ArrayList<>(dataSet);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ListView ItemList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.TvName);
            //ItemList = itemView.findViewById(R.id.ItemList);
        }
    }
    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_pattern, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.MyViewHolder holder, int position) {
        DataModel dataModel = dataSet.get(position); // Reference the current DataModel
        holder.textViewName.setText(dataModel.getName());


        // Set an OnClickListener for the itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("recipeName", dataModel.getName());
                //bundle.putString("recipeDetails", dataModel.getTheRecipe().toString());


                Navigation.findNavController(v).navigate(R.id.action_homePageFrag_to_showRecipeFrag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filter(String query) {
        query = query.toLowerCase().trim();  // Clean up the query

        // If query is empty, reset the dataset to the original data
        if (query.isEmpty()) {
            dataSet.clear();
            dataSet.addAll(dataSetFull);  // Reset to the full dataset
        } else {
            ArrayList<DataModel> filteredList = new ArrayList<>();
            for (DataModel item : dataSetFull) {
                if (item.getName().toLowerCase().startsWith(query)) {
                    filteredList.add(item);  // Add item if it matches the query
                }
            }
            dataSet.clear();
            dataSet.addAll(filteredList);  // Update the dataset with the filtered data
        }

        notifyDataSetChanged();  // Notify the adapter to update the RecyclerView
    }
}