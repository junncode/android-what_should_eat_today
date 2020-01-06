package com.ac.jun10.whattoeattoday;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private long fast = -1;
    private TextView fast0;
    private TextView fast1;
    private long price = -1;
    private TextView price0;
    private TextView price1;
    private long simple = -1;
    private TextView simple0;
    private TextView simple1;
    private long rice = -1;
    private TextView rice0;
    private TextView rice1;
    private long oiled = -1;
    private TextView oiled0;
    private TextView oiled1;
    private long meat = -1;
    private TextView meat0;
    private TextView meat1;
    private long exciting = -1;
    private TextView exciting0;
    private TextView exciting1;
    private long solo = -1;
    private TextView solo0;
    private TextView solo1;
    private long delivery = -1;
    private TextView delivery0;
    private TextView delivery1;
    private long soup = -1;
    private TextView soup0;
    private TextView soup1;

    private Button save;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ValueEventListener valueEventListener;

    private Intent intent;
    private int approach;
    private boolean userApproach = false;
    private List<UserDTO> userDTOs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        intent = getIntent();
        approach = intent.getIntExtra("approach", 0);
        if(approach == 0) //로그인 화면에서 접근
            userApproach = false;
        else if(approach == 1)  //홈 화면에서 접근
            userApproach = true;

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().setTitle("취향 고르기");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));

        fast0 = (TextView) findViewById(R.id.fast_0);
        fast1 = (TextView) findViewById(R.id.fast_1);
        fast0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fast0.setTextColor(0xFF03a9f4);
                fast1.setTextColor(0xFF000000);
                fast = 0;
            }
        });
        fast1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fast1.setTextColor(0xFF03a9f4);
                fast0.setTextColor(0xFF000000);
                fast = 1;
            }
        });
        price0 = (TextView) findViewById(R.id.price_0);
        price1 = (TextView) findViewById(R.id.price_1);
        price0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price0.setTextColor(0xFF03a9f4);
                price1.setTextColor(0xFF000000);
                price = 0;
            }
        });
        price1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price1.setTextColor(0xFF03a9f4);
                price0.setTextColor(0xFF000000);
                price = 1;
            }
        });
        simple0 = (TextView) findViewById(R.id.simple_0);
        simple1 = (TextView) findViewById(R.id.simple_1);
        simple0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simple0.setTextColor(0xFF03a9f4);
                simple1.setTextColor(0xFF000000);
                simple = 0;
            }
        });
        simple1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simple1.setTextColor(0xFF03a9f4);
                simple0.setTextColor(0xFF000000);
                simple = 1;
            }
        });
        rice0 = (TextView) findViewById(R.id.rice_0);
        rice1 = (TextView) findViewById(R.id.rice_1);
        rice0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rice0.setTextColor(0xFF03a9f4);
                rice1.setTextColor(0xFF000000);
                rice = 0;
            }
        });
        rice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rice1.setTextColor(0xFF03a9f4);
                rice0.setTextColor(0xFF000000);
                rice = 1;
            }
        });
        oiled0 = (TextView) findViewById(R.id.oiled_0);
        oiled1 = (TextView) findViewById(R.id.oiled_1);
        oiled0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oiled0.setTextColor(0xFF03a9f4);
                oiled1.setTextColor(0xFF000000);
                oiled = 0;
            }
        });
        oiled1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oiled1.setTextColor(0xFF03a9f4);
                oiled0.setTextColor(0xFF000000);
                oiled = 1;
            }
        });
        meat0 = (TextView) findViewById(R.id.meat_0);
        meat1 = (TextView) findViewById(R.id.meat_1);
        meat0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meat0.setTextColor(0xFF03a9f4);
                meat1.setTextColor(0xFF000000);
                meat = 0;
            }
        });
        meat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meat1.setTextColor(0xFF03a9f4);
                meat0.setTextColor(0xFF000000);
                meat = 1;
            }
        });
        exciting0 = (TextView) findViewById(R.id.exciting_0);
        exciting1 = (TextView) findViewById(R.id.exciting_1);
        exciting0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exciting0.setTextColor(0xFF03a9f4);
                exciting1.setTextColor(0xFF000000);
                exciting = 0;
            }
        });
        exciting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exciting1.setTextColor(0xFF03a9f4);
                exciting0.setTextColor(0xFF000000);
                exciting = 1;
            }
        });
        solo0 = (TextView) findViewById(R.id.solo_0);
        solo1 = (TextView) findViewById(R.id.solo_1);
        solo0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solo0.setTextColor(0xFF03a9f4);
                solo1.setTextColor(0xFF000000);
                solo = 0;
            }
        });
        solo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solo1.setTextColor(0xFF03a9f4);
                solo0.setTextColor(0xFF000000);
                solo = 1;
            }
        });
        delivery0 = (TextView) findViewById(R.id.delivery_0);
        delivery1 = (TextView) findViewById(R.id.delivery_1);
        delivery0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delivery0.setTextColor(0xFF03a9f4);
                delivery1.setTextColor(0xFF000000);
                delivery = 0;
            }
        });
        delivery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delivery1.setTextColor(0xFF03a9f4);
                delivery0.setTextColor(0xFF000000);
                delivery = 1;
            }
        });
        soup0 = (TextView) findViewById(R.id.soup_0);
        soup1 = (TextView) findViewById(R.id.soup_1);
        soup0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soup0.setTextColor(0xFF03a9f4);
                soup1.setTextColor(0xFF000000);
                soup = 0;
            }
        });
        soup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soup1.setTextColor(0xFF03a9f4);
                soup0.setTextColor(0xFF000000);
                soup = 1;
            }
        });

        save = (Button) findViewById(R.id.save_favorite_button);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(fast == -1 || price == -1 || simple == -1 || rice == -1 || oiled == -1 || meat == -1 || exciting == -1 || solo == -1 || delivery == -1 || soup == -1) {
                            Toast.makeText(FavoriteActivity.this, "모든 항목을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(dataSnapshot.child(auth.getUid()).exists() == false) {
                            UserDTO userDTO = new UserDTO();
                            userDTO.number = 0;
                            userDTO.uid = auth.getUid();
                            userDTO.xpos = 0;
                            userDTO.ypos = 0;
                            userDTO.fast = fast;
                            userDTO.price = price;
                            userDTO.style = 0;
                            userDTO.simple = simple;
                            userDTO.rice = rice;
                            userDTO.oiled = oiled;
                            userDTO.meat = meat;
                            userDTO.exciting = exciting;
                            userDTO.solo = solo;
                            userDTO.delivery = delivery;
                            userDTO.soup = soup;
                            userDTO.store1 = 0;
                            userDTO.store2 = 0;
                            userDTO.store3 = 0;
                            userDTO.store4 = 0;
                            userDTO.store5 = 0;
                            userDTO.store6 = 0;
                            userDTO.store7 = 0;
                            userDTO.store8 = 0;
                            userDTO.store9 = 0;
                            userDTO.store10 = 0;

                            database.getReference().child("users").child(auth.getUid()).setValue(userDTO);
                        }
                        else if(dataSnapshot.child(auth.getUid()).exists() == true) {
                            database.getReference().child("users").child(auth.getUid()).child("fast").setValue(fast);
                            database.getReference().child("users").child(auth.getUid()).child("price").setValue(price);
                            database.getReference().child("users").child(auth.getUid()).child("simple").setValue(simple);
                            database.getReference().child("users").child(auth.getUid()).child("rice").setValue(rice);
                            database.getReference().child("users").child(auth.getUid()).child("oiled").setValue(oiled);
                            database.getReference().child("users").child(auth.getUid()).child("meat").setValue(meat);
                            database.getReference().child("users").child(auth.getUid()).child("exciting").setValue(exciting);
                            database.getReference().child("users").child(auth.getUid()).child("solo").setValue(solo);
                            database.getReference().child("users").child(auth.getUid()).child("delivery").setValue(delivery);
                            database.getReference().child("users").child(auth.getUid()).child("soup").setValue(soup);
                        }

                        if(userApproach == false){ //로그인 화면에서 접근 시
                            Intent myintent = new Intent(FavoriteActivity.this, HomeActivity.class);
                            myintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myintent);
                            finish();
                        }
                        else if (userApproach == true){ //홈 화면에서 접근 시
                            finish();
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


    }

    @Override
    public void onStart(){
        super.onStart();
        database.getReference().child("users").addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(valueEventListener != null){
            database.getReference().child("users").removeEventListener(valueEventListener);
        }
    }
}
