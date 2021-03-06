package com.ac.jun10.whattoeattoday;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RandomActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ValueEventListener valueEventListener;
    private List<StoreDTO> storeDTOs = new ArrayList<>();
    private List<UserDTO> userDTOs = new ArrayList<>();

    public int clickCount = 0;
    public long thisStoreVisitNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        getSupportActionBar().setTitle("랜 덤 밥 팅");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView recommendText = (TextView) findViewById(R.id.recommend_text);

        final LinearLayout firstPage = (LinearLayout) findViewById(R.id.first_page);
        final ImageView storeImage = (ImageView) findViewById(R.id.store_image);
        final TextView storeRank = (TextView)findViewById(R.id.store_rank_textView);
        final TextView storeScore = (TextView)findViewById(R.id.store_score_textView);
        final TextView storeName = (TextView) findViewById(R.id.store_name_textView);
        final TextView storeStyleWord = (TextView) findViewById(R.id.store_style_textView);
        final TextView storeLocation = (TextView) findViewById(R.id.store_location_textView);
        final TextView storePhoneNum = (TextView) findViewById(R.id.store_phone_textView);

        final LinearLayout secondPage = (LinearLayout) findViewById(R.id.second_page);
        final ImageView storeImage2 = (ImageView) findViewById(R.id.store_image2);
        final TextView storeRank2 = (TextView)findViewById(R.id.store_rank_textView2);
        final TextView storeScore2 = (TextView)findViewById(R.id.store_score_textView2);
        final TextView storeName2 = (TextView) findViewById(R.id.store_name_textView2);
        final TextView storeStyleWord2 = (TextView) findViewById(R.id.store_style_textView2);
        final TextView storeLocation2 = (TextView) findViewById(R.id.store_location_textView2);
        final TextView storePhoneNum2 = (TextView) findViewById(R.id.store_phone_textView2);

        final LinearLayout thirdPage = (LinearLayout) findViewById(R.id.third_page);
        final ImageView storeImage3 = (ImageView) findViewById(R.id.store_image3);
        final TextView storeRank3 = (TextView)findViewById(R.id.store_rank_textView3);
        final TextView storeScore3 = (TextView)findViewById(R.id.store_score_textView3);
        final TextView storeName3 = (TextView) findViewById(R.id.store_name_textView3);
        final TextView storeStyleWord3 = (TextView) findViewById(R.id.store_style_textView3);
        final TextView storeLocation3 = (TextView) findViewById(R.id.store_location_textView3);
        final TextView storePhoneNum3 = (TextView) findViewById(R.id.store_phone_textView3);

        final LinearLayout fourthPage = (LinearLayout) findViewById(R.id.fourth_page);
        final ImageView storeImage4 = (ImageView) findViewById(R.id.store_image4);
        final TextView storeRank4 = (TextView)findViewById(R.id.store_rank_textView4);
        final TextView storeScore4 = (TextView)findViewById(R.id.store_score_textView4);
        final TextView storeName4 = (TextView) findViewById(R.id.store_name_textView4);
        final TextView storeStyleWord4 = (TextView) findViewById(R.id.store_style_textView4);
        final TextView storeLocation4 = (TextView) findViewById(R.id.store_location_textView4);
        final TextView storePhoneNum4 = (TextView) findViewById(R.id.store_phone_textView4);

        final LinearLayout fifthPage = (LinearLayout) findViewById(R.id.fifth_page);
        final ImageView storeImage5 = (ImageView) findViewById(R.id.store_image5);
        final TextView storeRank5 = (TextView)findViewById(R.id.store_rank_textView5);
        final TextView storeScore5 = (TextView)findViewById(R.id.store_score_textView5);
        final TextView storeName5 = (TextView) findViewById(R.id.store_name_textView5);
        final TextView storeStyleWord5 = (TextView) findViewById(R.id.store_style_textView5);
        final TextView storeLocation5 = (TextView) findViewById(R.id.store_location_textView5);
        final TextView storePhoneNum5 = (TextView) findViewById(R.id.store_phone_textView5);

        final ImageButton prevButton = (ImageButton) findViewById(R.id.prev_button);
        final ImageButton nextButton = (ImageButton) findViewById(R.id.next_button);
        final Button goButton = (Button) findViewById(R.id.recommend_go_button);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //가게 데이터 모두 storeDTOs에 저장
                for(DataSnapshot snapshot : dataSnapshot.child("stores").getChildren()){
                    StoreDTO storeDTO = snapshot.getValue(StoreDTO.class);
                    storeDTOs.add(storeDTO);
                }
                //유져 데이터 모두 userDTOs에 저장
                for(DataSnapshot snapshot : dataSnapshot.child("users").getChildren()) {
                    UserDTO userDTO = snapshot.getValue(UserDTO.class);
                    userDTOs.add(userDTO);
                }
//firebase에서 유져의 번호 가져오기 (거리 구하기와 식당 순위 설정할 때 사용)
                String userNumString = dataSnapshot.child("users").child(auth.getUid()).child("number").getValue().toString();
                final int userNum = Integer.parseInt(userNumString);
//가져온 유져 번호로 현재 유져의 식당 순위 설정
                final int recommendRank[] = new int[storeDTOs.size()];
                for(int i=0; i<storeDTOs.size(); i++) { recommendRank[i] = (int)(Math.random()*10); }

                final long userVisit[][] = new long[userDTOs.size()][storeDTOs.size()];
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][0] = userDTOs.get(i).store1; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][1] = userDTOs.get(i).store2; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][2] = userDTOs.get(i).store3; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][3] = userDTOs.get(i).store4; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][4] = userDTOs.get(i).store5; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][5] = userDTOs.get(i).store6; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][6] = userDTOs.get(i).store7; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][7] = userDTOs.get(i).store8; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][8] = userDTOs.get(i).store9; }
                for(int i=0; i<userDTOs.size(); i++){ userVisit[i][9] = userDTOs.get(i).store10; }

                thisStoreVisitNum = userVisit[userNum][recommendRank[0]];
                Glide.with(getApplicationContext()).load(storeDTOs.get(recommendRank[0]).image).into(storeImage);
                storeRank.setText(String.valueOf(storeDTOs.get(recommendRank[0]).rank));
                storeScore.setText(String.valueOf(storeDTOs.get(recommendRank[0]).score));
                storeName.setText(storeDTOs.get(recommendRank[0]).name);
                storeStyleWord.setText(storeDTOs.get(recommendRank[0]).styleWord);
                storeLocation.setText(storeDTOs.get(recommendRank[0]).location);
                storePhoneNum.setText(storeDTOs.get(recommendRank[0]).phoneNum);

                Glide.with(getApplicationContext()).load(storeDTOs.get(recommendRank[1]).image).into(storeImage2);
                storeRank2.setText(String.valueOf(storeDTOs.get(recommendRank[1]).rank));
                storeScore2.setText(String.valueOf(storeDTOs.get(recommendRank[1]).score));
                storeName2.setText(storeDTOs.get(recommendRank[1]).name);
                storeStyleWord2.setText(storeDTOs.get(recommendRank[1]).styleWord);
                storeLocation2.setText(storeDTOs.get(recommendRank[1]).location);
                storePhoneNum2.setText(storeDTOs.get(recommendRank[1]).phoneNum);

                Glide.with(getApplicationContext()).load(storeDTOs.get(recommendRank[2]).image).into(storeImage3);
                storeRank3.setText(String.valueOf(storeDTOs.get(recommendRank[2]).rank));
                storeScore3.setText(String.valueOf(storeDTOs.get(recommendRank[2]).score));
                storeName3.setText(storeDTOs.get(recommendRank[2]).name);
                storeStyleWord3.setText(storeDTOs.get(recommendRank[2]).styleWord);
                storeLocation3.setText(storeDTOs.get(recommendRank[2]).location);
                storePhoneNum3.setText(storeDTOs.get(recommendRank[2]).phoneNum);

                Glide.with(getApplicationContext()).load(storeDTOs.get(recommendRank[3]).image).into(storeImage4);
                storeRank4.setText(String.valueOf(storeDTOs.get(recommendRank[3]).rank));
                storeScore4.setText(String.valueOf(storeDTOs.get(recommendRank[3]).score));
                storeName4.setText(storeDTOs.get(recommendRank[3]).name);
                storeStyleWord4.setText(storeDTOs.get(recommendRank[3]).styleWord);
                storeLocation4.setText(storeDTOs.get(recommendRank[3]).location);
                storePhoneNum4.setText(storeDTOs.get(recommendRank[3]).phoneNum);

                Glide.with(getApplicationContext()).load(storeDTOs.get(recommendRank[4]).image).into(storeImage5);
                storeRank5.setText(String.valueOf(storeDTOs.get(recommendRank[4]).rank));
                storeScore5.setText(String.valueOf(storeDTOs.get(recommendRank[4]).score));
                storeName5.setText(storeDTOs.get(recommendRank[4]).name);
                storeStyleWord5.setText(storeDTOs.get(recommendRank[4]).styleWord);
                storeLocation5.setText(storeDTOs.get(recommendRank[4]).location);
                storePhoneNum5.setText(storeDTOs.get(recommendRank[4]).phoneNum);

                firstPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                        intent.putExtra("storeNumber", recommendRank[0]+1);
                        startActivity(intent);
                    }
                });
                secondPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                        intent.putExtra("storeNumber", recommendRank[1]+1);
                        startActivity(intent);
                    }
                });
                thirdPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                        intent.putExtra("storeNumber", recommendRank[2]+1);
                        startActivity(intent);
                    }
                });
                fourthPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                        intent.putExtra("storeNumber", recommendRank[3]+1);
                        startActivity(intent);
                    }
                });
                fifthPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                        intent.putExtra("storeNumber", recommendRank[4]+1);
                        startActivity(intent);
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clickCount == 0){
                            recommendText.setText("추 천    2 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[1]];
                            firstPage.setVisibility(View.INVISIBLE);
                            clickCount++;
                        }
                        else if(clickCount == 1){
                            recommendText.setText("추 천    3 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[2]];
                            secondPage.setVisibility(View.INVISIBLE);
                            clickCount++;
                        }
                        else if(clickCount == 2){
                            recommendText.setText("추 천    4 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[3]];
                            thirdPage.setVisibility(View.INVISIBLE);
                            clickCount++;
                        }
                        else if(clickCount == 3){
                            recommendText.setText("추 천    5 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[4]];
                            fourthPage.setVisibility(View.INVISIBLE);
                            clickCount++;
                        }
                        else if(clickCount == 4){
                            recommendText.setText("추 천    1 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[0]];
                            firstPage.setVisibility(View.VISIBLE);
                            secondPage.setVisibility(View.VISIBLE);
                            thirdPage.setVisibility(View.VISIBLE);
                            fourthPage.setVisibility(View.VISIBLE);
                            clickCount = 0;
                        }
                    }
                });
                prevButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clickCount == 1){
                            recommendText.setText("추 천    1 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[0]];
                            firstPage.setVisibility(View.VISIBLE);
                            clickCount--;
                        }
                        else if(clickCount == 2){
                            recommendText.setText("추 천    2 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[1]];
                            secondPage.setVisibility(View.VISIBLE);
                            clickCount--;
                        }
                        else if(clickCount == 3){
                            recommendText.setText("추 천    3 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[2]];
                            thirdPage.setVisibility(View.VISIBLE);
                            clickCount--;
                        }
                        else if(clickCount == 4){
                            recommendText.setText("추 천    4 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[3]];
                            fourthPage.setVisibility(View.VISIBLE);
                            clickCount--;
                        }
                        else if(clickCount == 0){
                            recommendText.setText("추 천    5 위");
                            thisStoreVisitNum = userVisit[userNum][recommendRank[4]];
                            firstPage.setVisibility(View.INVISIBLE);
                            secondPage.setVisibility(View.INVISIBLE);
                            thirdPage.setVisibility(View.INVISIBLE);
                            fourthPage.setVisibility(View.INVISIBLE);
                            clickCount = 4;
                        }
                    }
                });
                goButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long visitNum = thisStoreVisitNum + 1;
                        String storeNum = "store" + (recommendRank[clickCount]+1);
                        database.getReference().child("users").child(auth.getUid()).child(storeNum).setValue(visitNum);

                        Toast.makeText(RandomActivity.this, "맛있게 식사하세요~", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                Toast.makeText(RandomActivity.this, String.valueOf(recommendRank[0])+","+ String.valueOf(recommendRank[1])+"," + String.valueOf(recommendRank[2])+","+ String.valueOf(recommendRank[3])+","+ String.valueOf(recommendRank[4]), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        database.getReference().addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(valueEventListener != null){
            database.getReference().removeEventListener(valueEventListener);
        }
    }
    //ActionBar 백버튼 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
