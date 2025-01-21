package com.example.recipes.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;
import com.example.recipes.DataCallback;
import com.example.recipes.R;
import com.example.recipes.Recepie;
import com.example.recipes.User;
import com.example.recipes.fragments.DataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private User theUser;
    private String U_id;

    private ArrayList<DataModel> favorites = new ArrayList<DataModel>();

    ArrayList<DataModel> dataSet = new ArrayList<DataModel>();


    private boolean isFirstTime = true;
    private  boolean isFirstTimeFavorites = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
    }


    public void register(View view)
    {
        String email = (((EditText) findViewById(R.id.editTextTextEmailAddress)).getText().toString());
        String password = (((EditText) findViewById(R.id.PasswordinputReg)).getText().toString());
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            writeData();

                            Toast.makeText(MainActivity.this, "reg OK", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_registerFrag_to_loginFrag);

                        } else {
                            Toast.makeText(MainActivity.this, "reg Fail", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



    public void changePassword (View view)
    {
        String email = (((EditText) findViewById(R.id.Emailinput)).getText().toString());

        if(email.isEmpty()){
            Toast.makeText(this,"You need to enter email.",Toast.LENGTH_LONG).show();
        }
        else {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this,"Password reset email sent.",Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(this,"Error: " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }



    public void login(View view)
    {
        String email2 = (((EditText) findViewById(R.id.Emailinput)).getText().toString());
        String password = (((EditText) findViewById(R.id.Passwordinput)).getText().toString());
        if(email2.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"You need to enter email and password",Toast.LENGTH_LONG).show();
        }else {
            mAuth.signInWithEmailAndPassword(email2, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Loging OK", Toast.LENGTH_LONG).show();
                                Navigation.findNavController(view).navigate(R.id.action_loginFrag_to_homePageFrag);
                            } else {
                                Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void writeData()
    {
        String phone,email;
        phone = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();
        email = ((EditText)findViewById(R.id.editTextTextEmailAddress)).getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String U_Id = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(U_Id);

        User userObj = new User(phone,email);

        myRef.setValue(userObj);
    }

    public void saveRecepie(Recepie recepie) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(recepie.getIngredients()!=null) {
            if(!(dataExists(recepie.getName(), dataSet))) {
                DatabaseReference myRef = database.getReference("users").child(U_id).child("recipesM").child(recepie.getName());
                myRef.setValue(recepie);
            }else{
                Toast.makeText(this,"there is already this name in the database",Toast.LENGTH_LONG).show();
            }
        }
        else if (recepie.getPhotoPath()!=null) {
            if(!(dataExists(recepie.getName(), dataSet))) {
                DatabaseReference myRef = database.getReference("users").child(U_id).child("recipesP").child(recepie.getName());
                myRef.setValue(recepie);
                Toast.makeText(this,"The photo has been saved in the database",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"there is already this name in the database",Toast.LENGTH_LONG).show();
            }
        }
        else {
            if (!(dataExists(recepie.getName(), dataSet))) {
                DatabaseReference myRef = database.getReference("users").child(U_id).child("recipesURL").child(recepie.getName());
                myRef.setValue(recepie);
            }else{
                Toast.makeText(this,"there is already this name in the database",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void readData(Context context, View v, DataCallback callback)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        U_id = user.getUid();

        DatabaseReference myRef = database.getReference("users").child(U_id).child("recipesM");
        DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesP");
        DatabaseReference myRef2 = database.getReference("users").child(U_id).child("recipesURL");


        AtomicInteger completedRequests = new AtomicInteger(0);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!isFirstTime) {
                        isFirstTime = false;
                        dataSet.clear();
                    }

                    for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                        if (!dataExists(recipeSnapshot.getKey(), dataSet)) {
                            Recepie value = recipeSnapshot.getValue(Recepie.class);
                            dataSet.add(new DataModel(recipeSnapshot.getKey(), value));
                            Log.d("RecyclerView", "Added: " + recipeSnapshot.getKey());
                        }
                    }


                if (completedRequests.incrementAndGet() == 3) {
                    callback.onDataReady(dataSet);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Failed to read value", error.toException());
            }
        };

        try {
            myRef.addValueEventListener(listener);
            myRef1.addValueEventListener(listener);
            myRef2.addValueEventListener(listener);
        } catch (Exception e) {
            Log.e("Firebase", "Error reading data", e);
        }

    }


    private boolean dataExists(String recipeKey, ArrayList<DataModel> dataSet) {
        for (DataModel model : dataSet) {
            if (model.getName().equals(recipeKey)) {
                return true;
            }
        }
        return false;
    }


    public void getDataSet(Context context, View view, DataCallback callback) {
        readData(context, view ,new DataCallback() {
            @Override
            public void onDataReady(ArrayList<DataModel> data) {

                if (data != null && !data.isEmpty()) {
                    dataSet = data;
                    callback.onDataReady(dataSet);
                } else {
                    callback.onDataReady(null);
                }
            }
        });
    }





    public void readFavorites(Context context, View v, DataCallback callback)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        U_id = user.getUid();

        DatabaseReference myRef = database.getReference("users").child(U_id).child("favorites");


        AtomicInteger completedRequests = new AtomicInteger(0);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!isFirstTimeFavorites) {
                    isFirstTimeFavorites = false;
                    favorites.clear();
                }

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    if (!dataExists(recipeSnapshot.getKey(), favorites)) {
                        Recepie value = recipeSnapshot.getValue(Recepie.class);
                        favorites.add(new DataModel(recipeSnapshot.getKey(), value));
                        Log.d("RecyclerView", "Added: " + recipeSnapshot.getKey());
                    }
                }


                if (completedRequests.incrementAndGet() == 1) {

                    callback.onDataReady(favorites);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Failed to read value", error.toException());
            }
        };

        try {
            myRef.addValueEventListener(listener);
        } catch (Exception e) {
            Log.e("Firebase", "Error reading data", e);
        }

    }




    public void getFavoriteSet(Context c, View v, DataCallback callback) {
        readFavorites(c, v, new DataCallback() {
            @Override
            public void onDataReady(ArrayList<DataModel> data) {
                if (data != null && !data.isEmpty()) {
                    favorites = data;
                    callback.onDataReady(favorites);
                } else {
                    callback.onDataReady(null);
                }
            }
        });


    }

    public void addFavoriteRecipes(Recepie recepie) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(U_id).child("favorites").child(recepie.getName());
        myRef.setValue(recepie);
        if(recepie.getIngredients() != null){
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesM").child(recepie.getName());
            myRef1.setValue(recepie);
        }else if(recepie.getPhotoPath() != null){
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesP").child(recepie.getName());
            myRef1.setValue(recepie);
        }else{
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesURL").child(recepie.getName());
            myRef1.setValue(recepie);
        }


    }

    public void RemoveFavoriteRecipes(Recepie recepie,DataCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(U_id).child("favorites").child(recepie.getName());
        myRef.removeValue();
        if(recepie.getIngredients() != null){
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesM").child(recepie.getName());

            myRef1.setValue(recepie);
        }else if(recepie.getPhotoPath() != null){
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesP").child(recepie.getName());
            myRef1.setValue(recepie);
        }else{
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesURL").child(recepie.getName());
            myRef1.setValue(recepie);
        }

        deleteFavorite(recepie.getName());
        callback.onDataReady(favorites);
    }


    public void deleteFavorite(String name){
        for(DataModel dataModel: favorites){
            if(dataModel.getRecepie().getName().equals(name)){
                favorites.remove(dataModel);
            }
        }
        for(DataModel dataModel: dataSet)
        {
            if(dataModel.getRecepie().getName().equals(name))
            {
                dataModel.getRecepie().setFavorites(false);
            }
        }
    }




    public void DeleteRecepie(Recepie recepie,DataCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(U_id).child("favorites").child(recepie.getName());
        myRef.removeValue();
        if(recepie.getIngredients() != null){
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesM").child(recepie.getName());
            myRef1.removeValue();

        }else if(recepie.getPhotoPath() != null){
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesP").child(recepie.getName());
            myRef1.removeValue();

        }else{
            DatabaseReference myRef1 = database.getReference("users").child(U_id).child("recipesURL").child(recepie.getName());
            myRef1.removeValue();

        }
        deleteRecepie(recepie.getName());
        callback.onDataReady(dataSet);
    }

    public void deleteRecepie(String name){
        for(DataModel dataModel: dataSet){
            if(dataModel.getRecepie().getName().equals(name)){
                dataSet.remove(dataModel);
            }
        }
        for(DataModel dataModel: favorites)
        {
            if(dataModel.getRecepie().getName().equals(name)){
                favorites.remove(dataModel);
            }
        }
    }
}
