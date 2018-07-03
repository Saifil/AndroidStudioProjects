package com.example.saifil.recyclerviewapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private Context mCtx; //to create a view to our pokemon details card
    private List<PokemonDetails> pokemonList; //to have a list of items to display

    public PokemonAdapter(Context mCtx, List<PokemonDetails> pokemonList) {
        this.mCtx = mCtx;
        this.pokemonList = pokemonList;
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.mycard_layout,null);
        return new PokemonViewHolder(view);

    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {

        PokemonDetails pokemon = pokemonList.get(position);
        holder.txtView.setText(pokemon.getName());
        holder.imgView.setImageDrawable(mCtx.getResources().getDrawable(pokemon.getImgID()));
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView;
        TextView txtView;

        public PokemonViewHolder(View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.img_view);
            txtView = itemView.findViewById(R.id.txt_view);
        }
    }

}
