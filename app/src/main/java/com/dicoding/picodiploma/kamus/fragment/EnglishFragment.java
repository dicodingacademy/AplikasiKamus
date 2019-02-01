package com.dicoding.picodiploma.kamus.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dicoding.picodiploma.kamus.R;
import com.dicoding.picodiploma.kamus.adapter.WordAdapter;
import com.dicoding.picodiploma.kamus.database.WordHelper;
import com.dicoding.picodiploma.kamus.model.WordModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishFragment extends Fragment {

    public EnglishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_english, container, false);

        RecyclerView rvEnglish = view.findViewById(R.id.rv_english);
        EditText etEnglish = view.findViewById(R.id.edt_english);
        final WordHelper wordHelper = new WordHelper(getActivity());
        final WordAdapter wordAdapter = new WordAdapter(getActivity());

        etEnglish.addTextChangedListener(new TextWatcher() {
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

        rvEnglish.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvEnglish.setAdapter(wordAdapter);

        wordHelper.open();

        // Ambil semua data Kamus English di database
        ArrayList<WordModel> wordModels = wordHelper.getAllDataEng();

        wordHelper.close();

        wordAdapter.setData(wordModels);

        return view;
    }

}
