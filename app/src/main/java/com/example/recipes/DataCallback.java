package com.example.recipes;

import com.example.recipes.fragments.DataModel;
import java.util.ArrayList;

public interface DataCallback {
    void onDataReady(ArrayList<DataModel> data);
}
