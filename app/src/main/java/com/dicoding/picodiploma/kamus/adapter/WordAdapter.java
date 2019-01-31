package com.dicoding.picodiploma.kamus.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.picodiploma.kamus.R;
import com.dicoding.picodiploma.kamus.model.WordModel;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private ArrayList<WordModel> listWord = new ArrayList<>();

    public WordAdapter() {
    }

    public void setData(ArrayList<WordModel> listWord) {

        if (listWord.size() > 0) {
            this.listWord.clear();
        }

        this.listWord.addAll(listWord);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txtWord.setText(listWord.get(i).getWord());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listWord.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWord;

        ViewHolder(View itemView) {
            super(itemView);

            txtWord = itemView.findViewById(R.id.txt_word);
        }
    }
}
