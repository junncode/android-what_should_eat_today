package com.ac.jun10.whattoeattoday;

import android.support.v7.app.AppCompatActivity;

public class PubActivity extends AppCompatActivity {

   /* private RecyclerView recyclerView;

    private List<CafeDTO> cafeDTOs = new ArrayList<>();
    private List<String> cafeNumberLists = new ArrayList<>();
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().setTitle("학교 앞 술집");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.cafe_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CafeRecyclerViewAdapter cafeRecyclerViewAdapter = new CafeRecyclerViewAdapter();
        recyclerView.setAdapter(cafeRecyclerViewAdapter);

        database.getReference().child("pubs").addValueEventListener(new ValueEventListener() {
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
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

}
