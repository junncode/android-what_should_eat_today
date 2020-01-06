package com.ac.jun10.whattoeattoday;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CafeActivity extends AppCompatActivity {

    private Intent intent;
    private String activityTitle;
    private String databaseName;

    private RecyclerView recyclerView;

    private List<CafeDTO> cafeDTOs = new ArrayList<>();
    private List<String> cafeNumberLists = new ArrayList<>();
    private FirebaseDatabase database;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);
//인텐트에서 표시할 화면이 카페인지 술집인지 알아낸다
        intent = getIntent();
        activityTitle = intent.getStringExtra("activityTitle");
        databaseName = intent.getStringExtra("databaseName");

        database = FirebaseDatabase.getInstance();

        getSupportActionBar().setTitle(activityTitle);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.cafe_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CafeRecyclerViewAdapter cafeRecyclerViewAdapter = new CafeRecyclerViewAdapter();
        recyclerView.setAdapter(cafeRecyclerViewAdapter);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cafeDTOs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CafeDTO cafeDTO = snapshot.getValue(CafeDTO.class);
                    cafeDTOs.add(cafeDTO);
                }
                cafeRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
    @Override
    public void onStart(){
        super.onStart();
        database.getReference().child(databaseName).orderByChild("score").addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(valueEventListener != null){
            database.getReference().child(databaseName).orderByChild("score").removeEventListener(valueEventListener);
        }
    }
//ActionBar 백버튼 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        return true;
    }
//RecyclerViewAdapter
    class CafeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cafe,viewGroup,false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
            ((CustomViewHolder)viewHolder).cafeRankTextView.setText(String.valueOf(position+1));
            Glide.with(viewHolder.itemView.getContext()).load(cafeDTOs.get(cafeDTOs.size()-position-1).image).into(((CustomViewHolder)viewHolder).cafeImageView);
            ((CustomViewHolder)viewHolder).cafeNameTextView.setText(cafeDTOs.get(cafeDTOs.size()-position-1).name);
            ((CustomViewHolder)viewHolder).cafePropertyTextView.setText(cafeDTOs.get(cafeDTOs.size()-position-1).styleWord);
            ((CustomViewHolder)viewHolder).cafeScoreTextView.setText(String.valueOf(cafeDTOs.get(cafeDTOs.size()-position-1).score));

            ((CustomViewHolder)viewHolder).cafeNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(getApplicationContext(), CafeStoreActivity.class);
                    intent.putExtra("cafeNum", cafeDTOs.size()-position-1);
                    startActivity(intent);*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return cafeDTOs.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder{

            TextView cafeRankTextView;
            ImageView cafeImageView;
            TextView cafeNameTextView;
            TextView cafePropertyTextView;
            TextView cafeScoreTextView;

            public CustomViewHolder(View view){
                super(view);
                cafeRankTextView = (TextView) view.findViewById(R.id.cafe_rank_textView);
                cafeImageView = (ImageView) view.findViewById(R.id.cafe_image_imageView);
                cafeNameTextView = (TextView) view.findViewById(R.id.cafe_name_textView);
                cafePropertyTextView = (TextView) view.findViewById(R.id.cafe_property_textView);
                cafeScoreTextView = (TextView) view.findViewById(R.id.cafe_score_textView);
            }
        }
    }
}
