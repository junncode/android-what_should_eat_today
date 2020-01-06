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

public class StoreRankActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<StoreDTO> storeDTOs = new ArrayList<>();
    private List<String> storeNumberLists = new ArrayList<>();
    private FirebaseDatabase database;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_rank);

        database = FirebaseDatabase.getInstance();

        getSupportActionBar().setTitle("학교앞 음식점");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.store_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final StoreRankRecyclerViewAdapter storeRankRecyclerViewAdapter = new StoreRankRecyclerViewAdapter();
        recyclerView.setAdapter(storeRankRecyclerViewAdapter);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storeDTOs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    StoreDTO storeDTO = snapshot.getValue(StoreDTO.class);
                    storeDTOs.add(storeDTO);
                }
                storeRankRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        database.getReference().child("stores").orderByChild("score").addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(valueEventListener != null){
            database.getReference().child("stores").orderByChild("score").removeEventListener(valueEventListener);
        }
    }

    //ActionBar 백버튼 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
    //RecyclerViewAdapter
    class StoreRankRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cafe,viewGroup,false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
            ((CustomViewHolder)viewHolder).cafeRankTextView.setText(String.valueOf(position+1));
            Glide.with(viewHolder.itemView.getContext()).load(storeDTOs.get(storeDTOs.size()-position-1).image).into(((CustomViewHolder)viewHolder).cafeImageView);
            ((CustomViewHolder)viewHolder).cafeNameTextView.setText(storeDTOs.get(storeDTOs.size()-position-1).name);
            ((CustomViewHolder)viewHolder).cafePropertyTextView.setText(storeDTOs.get(storeDTOs.size()-position-1).styleWord);
            ((CustomViewHolder)viewHolder).cafeScoreTextView.setText(String.valueOf(storeDTOs.get(storeDTOs.size()-position-1).score));

            ((CustomViewHolder)viewHolder).cafeNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int storeNumber = (int)storeDTOs.get(storeDTOs.size()-position-1).number;
                    Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                    intent.putExtra("storeNumber", storeNumber);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return storeDTOs.size();
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
