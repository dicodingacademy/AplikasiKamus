package com.dicoding.picodiploma.kamus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        TextView tvWord = findViewById(R.id.tv_word);
        TextView tvDescription = findViewById(R.id.tv_description);

        WordModel wordModel = getIntent().getParcelableExtra("word");
        tvWord.setText(wordModel.getWord());
        tvDescription.setText(wordModel.getDescription());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
