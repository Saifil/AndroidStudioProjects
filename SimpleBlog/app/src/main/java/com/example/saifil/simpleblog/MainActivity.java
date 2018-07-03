package com.example.saifil.simpleblog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBlogList;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBlogList = findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));


        Log.i("TAG","was 0");

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("blog");

        FirebaseRecyclerOptions<Blog> options = new FirebaseRecyclerOptions.Builder<Blog>()
                .setQuery(query,Blog.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Blog,BlogViewHolder>(options) {

            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.blog_row,null);

                Log.i("TAG","was 1");
                return new BlogViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(BlogViewHolder holder, int position, Blog model) {

                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());

                Log.i("TAG","was 2");
            }
        };
        mBlogList.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //starts the recycler view and fills the main activity with cards
        adapter.startListening();

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title) {

            TextView postTitle = mView.findViewById(R.id.post_title_id);
            postTitle.setText(title);
        }

        public void setDesc(String desc) {

            TextView postDesc = mView.findViewById(R.id.post_dec_id);
            postDesc.setText(desc);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //used for menu bar

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //user selects item from menu

        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this,PostActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
