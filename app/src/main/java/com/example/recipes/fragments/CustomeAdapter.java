package com.example.recipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {
    private ArrayList<DataModel> dataSet;
    private ArrayList<DataModel> dataSetFull;
    private Context context;




    public CustomeAdapter(Context context, ArrayList<DataModel> DATASET) {
        this.context = context;
        this.dataSet = DATASET;
        this.dataSetFull = new ArrayList<>(dataSet);

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
        DataModel selectedRecipe = dataSet.get(position);
        holder.textViewName.setText(selectedRecipe.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("recipeName", selectedRecipe.getName());
                bundle.putSerializable("recipeObject", selectedRecipe.getDatamodel());

                try
                {
                    Navigation.findNavController(v).navigate(R.id.action_homePageFrag_to_showRecipeFrag, bundle);
                }
                catch (Exception e)
                {
                    Navigation.findNavController(v).navigate(R.id.action_favoritesFrag_to_showRecipeFrag, bundle);
                }
            }
        });


    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        Object details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.TvName);

        }
    }



    public void filter(String query) {
        query = query.toLowerCase().trim();

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