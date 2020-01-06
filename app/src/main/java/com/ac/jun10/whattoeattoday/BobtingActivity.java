package com.ac.jun10.whattoeattoday;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BobtingActivity extends AppCompatActivity {

    private Button recommendBobting;
    private Button randomBobting;

    private FirebaseDatabase database;
    private ValueEventListener orderUser;
    private List<UserDTO> userDTOs = new ArrayList<>();
    private ValueEventListener orderRank;
    private List<StoreDTO> storeDTOs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bobting);

        getSupportActionBar().setTitle("밥 팅");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();
        orderUser = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserDTO userDTO = snapshot.getValue(UserDTO.class);
                    userDTOs.add(userDTO);
                }
                int order = 0;
                long userOrder = 0;
                for(userOrder = 0; userOrder<userDTOs.size(); userOrder++){
                    database.getReference().child("users").child(userDTOs.get(order).uid).child("number").setValue(userOrder % userDTOs.size());
                    order++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        orderRank = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StoreDTO storeDTO = snapshot.getValue(StoreDTO.class);
                    storeDTOs.add(storeDTO);
                }
                int order = 0;
                long rankOrder = 0;
                for(rankOrder = 0; rankOrder<storeDTOs.size(); rankOrder++){
                    if(storeDTOs.get(order).name == null){
                        order++;
                    }else{
                    database.getReference().child("stores").child(String.valueOf(storeDTOs.get(order).number)).child("rank").setValue(storeDTOs.size() - rankOrder);
                    order++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        recommendBobting = (Button)findViewById(R.id.bobting_1_recommendbobting_button);
        randomBobting = (Button)findViewById(R.id.bobting_1_randombobting_button);

        recommendBobting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecommendActivity.class);
                startActivity(intent);
                finish();
            }
        });

        randomBobting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RandomActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        database.getReference().child("users").orderByKey().addListenerForSingleValueEvent(orderUser);
        database.getReference().child("stores").orderByChild("score").addListenerForSingleValueEvent(orderRank);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
