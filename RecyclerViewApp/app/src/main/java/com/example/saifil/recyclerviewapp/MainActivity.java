package com.example.saifil.recyclerviewapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PokemonAdapter pokemonAdapter;
    List<PokemonDetails> pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true); //set the size as fixed
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add items to be displayed
        pokemonList.add(
                new PokemonDetails(1,R.drawable.articuno,"ARTICUNO")
        );
        pokemonList.add(
                new PokemonDetails(1,R.drawable.moltres,"MOLTRES")
        );
        pokemonList.add(
                new PokemonDetails(1,R.drawable.zapdos,"ZAPDOS")
        );

        //initiate the adapter
        pokemonAdapter = new PokemonAdapter(this,pokemonList);
        recyclerView.setAdapter(pokemonAdapter); //set the adapter

    }
}
