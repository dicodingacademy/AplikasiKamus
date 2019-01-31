package com.dicoding.picodiploma.kamus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dicoding.picodiploma.kamus.model.WordModel;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        TextView tvWord = findViewById(R.id.tvWord);
        TextView tvDesc = findViewById(R.id.tvDesc);

        WordModel wordModel = getIntent().getParcelableExtra("word");
        tvWord.setText(wordModel.getWord());
        tvDesc.setText(wordModel.getDescrition());
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
