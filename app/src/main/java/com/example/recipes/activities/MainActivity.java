package com.example.recipes.activities;
import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private User theUser;

    private String U_id;


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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(U_id).child("recipes").child(recepie.getName());


        myRef.setValue(recepie.getIngredients());
    }


    public void readData(ArrayList<DataModel> dataSet) {
        // Read from the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        U_id = user.getUid();

        DatabaseReference myRef = database.getReference("users").child(U_id);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);

                RecyclerView RV = findViewById(R.id.RVRecipes);
                //RV.setAdapter();


                /*if (value != null) {
                    callback.onDataReceived(value);  // Pass the user data to the callback
                }*/

                /*TextView Hello_user = findViewById(R.id.textView);
                Hello_user.setText("Email: "+value.getEmail());*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

}
