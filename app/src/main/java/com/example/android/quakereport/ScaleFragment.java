package com.example.android.quakereport;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScaleFragment extends Fragment {

    View rootView;
    ImageView imageView;

    public ScaleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.scale_activity, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        imageView.setImageResource(R.drawable.scale);
        return rootView;
    }

}
