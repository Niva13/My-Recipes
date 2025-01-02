package com.example.recipes.activities;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.Recepie;
import com.example.recipes.User;
import com.example.recipes.fragments.CustomeAdapter;
import com.example.recipes.fragments.DataModel;
import com.example.recipes.fragments.HomePageFrag;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private User theUser;
    private String U_id;

    private RecyclerView RV;
    private CustomeAdapter adapter;
    private List<DataModel> DS;


    private boolean isFirstTime = true;
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

        //FirebaseDatabase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(U_id).child("recipes").child(recepie.getName());


        myRef.setValue(recepie.getIngredients());
    }


    public void readData(Context context)
    {
        // Read from the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        U_id = user.getUid();

        DatabaseReference myRef = database.getReference("users").child(U_id).child("recipes");
        try {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    LinearLayoutManager layoutManager= new LinearLayoutManager(context);
                    RecyclerView Recipes = findViewById(R.id.RVRecipes);
                    ArrayList<DataModel> dataSet  = new ArrayList<>();
                    Recipes.setLayoutManager(layoutManager);
                    Recipes.setItemAnimator(new DefaultItemAnimator());


                    if(!isFirstTime){
                        isFirstTime = false;
                        dataSet.clear(); // Clear the dataset to avoid duplicates
                    }

                    for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                        Map<String,Object> recepie = new HashMap<>();
                        recepie.put(recipeSnapshot.getKey(),recipeSnapshot.getValue());
                        if (recepie != null) {
                            dataSet.add(new DataModel(recipeSnapshot.getKey(), recipeSnapshot.getValue()));
                        }
                    }
                    adapter= new CustomeAdapter(context, dataSet);
                    Recipes.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
        }catch (Exception e){

        }
    }

    public void filterList(String s){
        adapter.filter(s);
    }


    public void getFavorite(Context c) {


    }
}
