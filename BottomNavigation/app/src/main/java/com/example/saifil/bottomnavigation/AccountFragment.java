package com.example.saifil.bottomnavigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AccountFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment,container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Toast.makeText(getActivity(), "Value set", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Null", Toast.LENGTH_LONG).show();
        }

        return view;
    }

}
