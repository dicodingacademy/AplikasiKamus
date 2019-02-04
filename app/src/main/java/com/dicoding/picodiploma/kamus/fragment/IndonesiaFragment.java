package com.dicoding.picodiploma.kamus.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dicoding.picodiploma.kamus.KamusActivity;
import com.dicoding.picodiploma.kamus.MainActivity;
import com.dicoding.picodiploma.kamus.R;
import com.dicoding.picodiploma.kamus.adapter.WordAdapter;
import com.dicoding.picodiploma.kamus.database.WordHelper;
import com.dicoding.picodiploma.kamus.model.WordModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndonesiaFragment extends Fragment {

    public IndonesiaFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_indonesia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvIndonesia = view.findViewById(R.id.rv_indo);
        EditText etIndonesia = view.findViewById(R.id.edt_indo);
        WordHelper wordHelper = new WordHelper(getActivity());
        final WordAdapter wordAdapter = new WordAdapter(getActivity());
        etIndonesia.addTextChangedListener(new TextWatcher() {
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


        rvIndonesia.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvIndonesia.setAdapter(wordAdapter);

        wordHelper.open();

        // Ambil semua data Kamus Indo di database
        ArrayList<WordModel> wordModels = wordHelper.getAllData();

        wordHelper.close();

        wordAdapter.setData(wordModels);
    }
}
