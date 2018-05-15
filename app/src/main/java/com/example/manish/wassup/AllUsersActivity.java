package com.example.manish.wassup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllUsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView muserList;

    private DatabaseReference mdb_ref;
    private FirebaseAuth mAuth;
    private String mCurrent_UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        Fresco.initialize(this);

        mToolbar=(Toolbar)findViewById(R.id.allusers_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mCurrent_UID = mAuth.getCurrentUser().getUid();

        mdb_ref = FirebaseDatabase.getInstance().getReference().child("Users");

        muserList = (RecyclerView)findViewById(R.id.users_list);
        muserList.setHasFixedSize(true);
        muserList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<UsersData,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UsersData, UsersViewHolder>(
                UsersData.class,
                R.layout.users_single_layout,
                UsersViewHolder.class,
                mdb_ref        ) {

            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, UsersData users, int position) {

                final String user_uid = getRef(position).getKey();

                if(user_uid.equals(mCurrent_UID)) {
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

                }
                    viewHolder.setName(users.getName());
                    viewHolder.setStatus(users.getStatus());
                    viewHolder.setImage(users.getImage());


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent profileIntent = new Intent(AllUsersActivity.this,ProfileActivity.class);
                        profileIntent.putExtra("user_id",user_uid);
                        startActivity(profileIntent);


                    }
                });
            }
        };
        muserList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name){
            TextView UserNameView = (TextView) mView.findViewById(R.id.user_name);
            UserNameView.setText(name);
        }

        public void setStatus(String status){
            TextView UserNameView = (TextView) mView.findViewById(R.id.user_status);
            UserNameView.setText(status);
        }

        public void setImage(String thumb_image){
            SimpleDraweeView UserImage = (SimpleDraweeView) mView.findViewById(R.id.user_img_fresco);
                UserImage.setImageURI(thumb_image);        }
    }
}
