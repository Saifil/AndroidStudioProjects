package com.example.saifil.recyclermecardview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] name = {"ARTICUNO","MOLTRES","ZAPDOS"};

    private int[] img ={
            R.drawable.articuno,
            R.drawable.moltres,
            R.drawable.zapdos
    };

    class ViewHolder extends RecyclerView.ViewHolder {

        public int currItem;
        public ImageView itemImage;
        public TextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.img_id);
            itemText = itemView.findViewById(R.id.txt_id);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return  viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemText.setText(name[position]);
        holder.itemImage.setImageResource(img[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }
}
