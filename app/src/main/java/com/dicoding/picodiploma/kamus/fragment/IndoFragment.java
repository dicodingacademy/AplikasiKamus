package com.dicoding.picodiploma.kamus.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dicoding.picodiploma.kamus.R;
import com.dicoding.picodiploma.kamus.adapter.WordAdapter;
import com.dicoding.picodiploma.kamus.database.WordHelper;
import com.dicoding.picodiploma.kamus.model.WordModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndoFragment extends Fragment {

    public IndoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indo, container, false);
        RecyclerView rv_indo = view.findViewById(R.id.rv_indo);
        EditText edt_indo = view.findViewById(R.id.edt_indo);
        WordHelper wordHelper = new WordHelper(getActivity());
        final WordAdapter wordAdapter = new WordAdapter(getActivity());
        edt_indo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                wordAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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
