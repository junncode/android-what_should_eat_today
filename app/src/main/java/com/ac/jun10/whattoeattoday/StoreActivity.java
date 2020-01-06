package com.ac.jun10.whattoeattoday;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    Intent intent;
    int storeNumber;
    String storeNumberToString;

    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private String imageRefString;
    private StorageReference storeImageRef;
    private FirebaseDatabase database;
    private DatabaseReference roofReference;
    private DatabaseReference storeReference;

    String findedStoreName;
    String findedStoreStyleWord;
    long findedStoreRank;
    double findedStoreScore;
    String findedStoreLocation;
    String findedStorePhoneNum;

    private ImageView storeImage;
    private TextView storeName;
    private TextView storeStyleWord;
    private TextView storeRank;
    private TextView storeScore;
    private TextView storeLocation;
    private TextView storePhoneNum;

    private static final int GALLERY_CODE = 10;
    private Button reviewPickPicButton;
    private boolean reviewPicSelected = false;
    private String reviewPicPath;
    private ImageView reviewSelectedPic;
    private TextView reviewCurrentUserId;
    private EditText reviewText;
    private long reviewScore;
    private ImageView reviewHeart1;
    private ImageView reviewHeart2;
    private ImageView reviewHeart3;
    private ImageView reviewHeart4;
    private ImageView reviewHeart5;
    private Button reviewUploadButton;

    private RecyclerView recyclerView;
    private List<ReviewDTO> reviewDTOs = new ArrayList<>();
    private List<String> reviewNumberLists = new ArrayList<>();
    private ValueEventListener makeNewStoreScore;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
//getIntExtra로 식당 번호를 받아오기
        intent = getIntent();
        storeNumber = intent.getIntExtra("storeNumber", 0);
        storeNumberToString = String.valueOf(storeNumber);
//firebase auth와 storage, database 얻어오기
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://whattoeattoday-8d99f.appspot.com");
//database 찾아오기
        database = FirebaseDatabase.getInstance();
        roofReference = database.getReference();
        storeReference = roofReference.child("stores").child(storeNumberToString);
//ActionBar 설정
        storeReference.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findedStoreName = dataSnapshot.getValue(String.class);
                getSupportActionBar().setTitle(findedStoreName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF03a9f4));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//식당 이미지 출력
        storeImage = (ImageView)findViewById(R.id.store_image);
        storeReference.child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageRefString = dataSnapshot.getValue(String.class);
                Glide.with(getApplicationContext()).load(imageRefString).into(storeImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
//식당 랭킹 출력
        storeRank = (TextView)findViewById(R.id.store_rank_textView);
        storeReference.child("rank").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findedStoreRank = dataSnapshot.getValue(long.class);
                storeRank.setText(String.valueOf(findedStoreRank));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
//식당 점수 출력
        storeScore = (TextView)findViewById(R.id.store_score_textView);
        storeReference.child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findedStoreScore = dataSnapshot.getValue(Double.class);
                storeScore.setText(String.valueOf(findedStoreScore));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
//식당 이름 출력
        storeName = (TextView) findViewById(R.id.store_name_textView);
        storeReference.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findedStoreName = dataSnapshot.getValue(String.class);
                storeName.setText(findedStoreName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
//식당 스타일 출력
        storeStyleWord = (TextView) findViewById(R.id.store_style_textView);
        storeReference.child("styleWord").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findedStoreStyleWord = dataSnapshot.getValue(String.class);
                storeStyleWord.setText(String.valueOf(findedStoreStyleWord));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
//식당 위치 출력
        storeLocation = (TextView) findViewById(R.id.store_location_textView);
        storeReference.child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findedStoreLocation = dataSnapshot.getValue(String.class);
                storeLocation.setText(String.valueOf(findedStoreLocation));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
//식당 번호 출력
        storePhoneNum = (TextView) findViewById(R.id.store_phone_textView);
        storeReference.child("phoneNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                findedStorePhoneNum = dataSnapshot.getValue(String.class);
                storePhoneNum.setText(String.valueOf(findedStorePhoneNum));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

//리뷰 사진 가져오기
        reviewPickPicButton = (Button) findViewById(R.id.review_pick_pic_button);
        reviewPickPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewPicSelected = true;
                Intent picIntent = new Intent(Intent.ACTION_PICK);
                picIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(picIntent, GALLERY_CODE);
            }
        });
        reviewSelectedPic = (ImageView)findViewById(R.id.review_selected_pic);
//리뷰 올리기 전 데이터 입력
        reviewCurrentUserId = (TextView)findViewById(R.id.review_current_user_id);
        reviewCurrentUserId.setText("id : " + auth.getCurrentUser().getEmail());
        reviewText = (EditText)findViewById(R.id.review_text);
        reviewScore = 3;
        reviewHeart1 = (ImageView) findViewById(R.id.review_heart1);
        reviewHeart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewScore = 1;
                reviewHeart1.setImageResource(R.drawable.red_heart);
                reviewHeart2.setImageResource(R.drawable.empty_red_heart);
                reviewHeart3.setImageResource(R.drawable.empty_red_heart);
                reviewHeart4.setImageResource(R.drawable.empty_red_heart);
                reviewHeart5.setImageResource(R.drawable.empty_red_heart);
            }
        });
        reviewHeart2 = (ImageView) findViewById(R.id.review_heart2);
        reviewHeart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewScore = 2;
                reviewHeart1.setImageResource(R.drawable.red_heart);
                reviewHeart2.setImageResource(R.drawable.red_heart);
                reviewHeart3.setImageResource(R.drawable.empty_red_heart);
                reviewHeart4.setImageResource(R.drawable.empty_red_heart);
                reviewHeart5.setImageResource(R.drawable.empty_red_heart);
            }
        });
        reviewHeart3 = (ImageView) findViewById(R.id.review_heart3);
        reviewHeart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewScore = 3;
                reviewHeart1.setImageResource(R.drawable.red_heart);
                reviewHeart2.setImageResource(R.drawable.red_heart);
                reviewHeart3.setImageResource(R.drawable.red_heart);
                reviewHeart4.setImageResource(R.drawable.empty_red_heart);
                reviewHeart5.setImageResource(R.drawable.empty_red_heart);
            }
        });
        reviewHeart4 = (ImageView) findViewById(R.id.review_heart4);
        reviewHeart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewScore = 4;
                reviewHeart1.setImageResource(R.drawable.red_heart);
                reviewHeart2.setImageResource(R.drawable.red_heart);
                reviewHeart3.setImageResource(R.drawable.red_heart);
                reviewHeart4.setImageResource(R.drawable.red_heart);
                reviewHeart5.setImageResource(R.drawable.empty_red_heart);
            }
        });
        reviewHeart5 = (ImageView) findViewById(R.id.review_heart5);
        reviewHeart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewScore = 5;
                reviewHeart1.setImageResource(R.drawable.red_heart);
                reviewHeart2.setImageResource(R.drawable.red_heart);
                reviewHeart3.setImageResource(R.drawable.red_heart);
                reviewHeart4.setImageResource(R.drawable.red_heart);
                reviewHeart5.setImageResource(R.drawable.red_heart);
            }
        });
//리뷰 올리기
        reviewUploadButton = (Button)findViewById(R.id.review_upload_button);
        makeNewStoreScore = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double storeScore = dataSnapshot.child("score").getValue(double.class);
                long storeScoredUserNum = dataSnapshot.child("scoreNum").getValue(long.class);
                double newStoreScore = Double.parseDouble(String.format("%.1f",((storeScore * storeScoredUserNum) + reviewScore) / (storeScoredUserNum+1)));
                database.getReference().child("stores").child(storeNumberToString).child("score").setValue(newStoreScore);
                database.getReference().child("stores").child(storeNumberToString).child("scoreNum").setValue(storeScoredUserNum+1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
        reviewUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reviewPicSelected == false){
                    upload(reviewPicPath, false);
                }else if(reviewPicSelected == true) {
                    upload(reviewPicPath, true);
                }
                database.getReference().child("stores").child(storeNumberToString).addListenerForSingleValueEvent(makeNewStoreScore);
            }
        });
//있는 리뷰 보여주기
        recyclerView = (RecyclerView) findViewById(R.id.store_review_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ReviewRecyclerViewAdapter reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter();
        recyclerView.setAdapter(reviewRecyclerViewAdapter);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewDTOs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ReviewDTO reviewDTO = snapshot.getValue(ReviewDTO.class);
                    reviewDTOs.add(reviewDTO);
                }
                reviewRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }

    @Override
    public void onStart(){
        super.onStart();
        database.getReference().child("stores").child(storeNumberToString).child("storeReview").orderByKey().addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(valueEventListener != null)
            database.getReference().child("stores").child(storeNumberToString).child("storeReview").orderByKey().removeEventListener(valueEventListener);
        if(makeNewStoreScore != null)
            database.getReference().child("stores").child(storeNumberToString).removeEventListener(makeNewStoreScore);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

//앨범에서 후기 사진 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == GALLERY_CODE){

            reviewPicPath = getPath(data.getData());
            File f = new File(reviewPicPath);
            reviewSelectedPic.setImageURI(Uri.fromFile(f));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getPath(Uri uri){

        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }
    private void upload(String uri, boolean reviewPicSelected){
        if (reviewPicSelected == false){
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.reviewPicUrl = null;
            reviewDTO.reviewText = reviewText.getText().toString();
            reviewDTO.reviewUid = auth.getCurrentUser().getUid();
            reviewDTO.reviewUserId = auth.getCurrentUser().getEmail();
            reviewDTO.score = reviewScore;
            database.getReference().child("stores").child(storeNumberToString).child("storeReview").push().setValue(reviewDTO);

            reviewSelectedPic.setImageDrawable(null);
            reviewText.setText("");
        }else if (reviewPicSelected == true) {
            Uri file = Uri.fromFile(new File(uri));
            StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(StoreActivity.this, "후기가 올라가지 않았습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();

                    ReviewDTO reviewDTO = new ReviewDTO();
                    reviewDTO.reviewPicUrl = downloadUrl.toString();
                    reviewDTO.reviewText = reviewText.getText().toString();
                    reviewDTO.reviewUid = auth.getCurrentUser().getUid();
                    reviewDTO.reviewUserId = auth.getCurrentUser().getEmail();
                    reviewDTO.score = reviewScore;
                    database.getReference().child("stores").child(storeNumberToString).child("storeReview").push().setValue(reviewDTO);

                    reviewSelectedPic.setImageDrawable(null);
                    reviewText.setText("");
                }
            });
        }
    }

    //RecyclerViewAdapter
    class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review,viewGroup,false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
            ((CustomViewHolder)viewHolder).reviewedUserId.setText("id : "+reviewDTOs.get(position).reviewUserId);
            ((CustomViewHolder)viewHolder).reviewedText.setText(reviewDTOs.get(position).reviewText);
            if(reviewDTOs.get(position).score == 1){
                ((CustomViewHolder)viewHolder).reviewedHeart1.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart2.setImageResource(R.drawable.empty_red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart3.setImageResource(R.drawable.empty_red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart4.setImageResource(R.drawable.empty_red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart5.setImageResource(R.drawable.empty_red_heart);
            }else if(reviewDTOs.get(position).score == 2){
                ((CustomViewHolder)viewHolder).reviewedHeart1.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart2.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart3.setImageResource(R.drawable.empty_red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart4.setImageResource(R.drawable.empty_red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart5.setImageResource(R.drawable.empty_red_heart);
            }else if(reviewDTOs.get(position).score == 3){
                ((CustomViewHolder)viewHolder).reviewedHeart1.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart2.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart3.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart4.setImageResource(R.drawable.empty_red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart5.setImageResource(R.drawable.empty_red_heart);
            }else if(reviewDTOs.get(position).score == 4){
                ((CustomViewHolder)viewHolder).reviewedHeart1.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart2.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart3.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart4.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart5.setImageResource(R.drawable.empty_red_heart);
            }else if(reviewDTOs.get(position).score == 5){
                ((CustomViewHolder)viewHolder).reviewedHeart1.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart2.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart3.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart4.setImageResource(R.drawable.red_heart);
                ((CustomViewHolder)viewHolder).reviewedHeart5.setImageResource(R.drawable.red_heart);
            }
            if (reviewDTOs.get(position).reviewPicUrl != null){
                Glide.with(viewHolder.itemView.getContext()).load(reviewDTOs.get(position).reviewPicUrl).into(((CustomViewHolder)viewHolder).reviewedPic);
            }
        }

        @Override
        public int getItemCount() {
            return reviewDTOs.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder{

            TextView reviewedUserId;
            TextView reviewedText;
            ImageView reviewedHeart1;
            ImageView reviewedHeart2;
            ImageView reviewedHeart3;
            ImageView reviewedHeart4;
            ImageView reviewedHeart5;
            ImageView reviewedPic;

            public CustomViewHolder(View view){
                super(view);
                reviewedUserId = (TextView) view.findViewById(R.id.reviewed_user_id);
                reviewedText = (TextView) view.findViewById(R.id.reviewed_text);
                reviewedHeart1 = (ImageView) view.findViewById(R.id.reviewed_heart1);
                reviewedHeart2 = (ImageView) view.findViewById(R.id.reviewed_heart2);
                reviewedHeart3 = (ImageView) view.findViewById(R.id.reviewed_heart3);
                reviewedHeart4 = (ImageView) view.findViewById(R.id.reviewed_heart4);
                reviewedHeart5 = (ImageView) view.findViewById(R.id.reviewed_heart5);
                reviewedPic = (ImageView) view.findViewById(R.id.reviewed_pic);
            }
        }
    }

}
