package com.example.saifil.geotoll;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    private FirebaseAuth mFirebaseAuth;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment,container,false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        btn = view.findViewById(R.id.Signout_id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFirebaseAuth != null) {
                    mFirebaseAuth.signOut();

                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
