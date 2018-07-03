package com.example.saifil.volleyrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {

    private String[] data;

    public DemoAdapter(String[] data) {
        this.data = data;
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_layout,parent,false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        String title = data[position];
        holder.textView.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public DemoViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_view_id);
            textView = itemView.findViewById(R.id.txt_view_id);
        }
    }
}
