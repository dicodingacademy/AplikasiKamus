package com.dicoding.picodiploma.kamus.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.picodiploma.kamus.R;
import com.dicoding.picodiploma.kamus.adapter.WordAdapter;
import com.dicoding.picodiploma.kamus.database.WordHelper;
import com.dicoding.picodiploma.kamus.model.WordModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndoFragment extends Fragment {

    RecyclerView rv_indo;
    WordAdapter wordAdapter;
    WordHelper wordHelper;

    public IndoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indo, container, false);
        rv_indo = view.findViewById(R.id.rv_indo);

        wordHelper = new WordHelper(getActivity());
        wordAdapter = new WordAdapter();
        rv_indo.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_indo.setAdapter(wordAdapter);

        wordHelper.open();

        // Ambil semua data mahasiswa di database
        ArrayList<WordModel> wordModels = wordHelper.getAllData();

        wordHelper.close();

        wordAdapter.setData(wordModels);
        return view;
    }

}
