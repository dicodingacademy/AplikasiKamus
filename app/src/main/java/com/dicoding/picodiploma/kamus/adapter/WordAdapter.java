package com.dicoding.picodiploma.kamus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.picodiploma.kamus.DetailActivity;
import com.dicoding.picodiploma.kamus.R;
import com.dicoding.picodiploma.kamus.model.WordModel;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private final ArrayList<WordModel> listWord = new ArrayList<>();
    private final Context context;
    private final ArrayList<WordModel> baseList = new ArrayList<>();

    public WordAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<WordModel> listWord) {

        if (listWord.size() > 0) {
            this.listWord.clear();
        }

        this.listWord.addAll(listWord);
        this.baseList.addAll(listWord);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.txtWord.setText(listWord.get(holder.getAdapterPosition()).getWord());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = listWord.get(holder.getAdapterPosition()).getWord();
                String description = listWord.get(holder.getAdapterPosition()).getDescription();
                WordModel wordModel = new WordModel(word,description);
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("word",wordModel);
                context.startActivity(i);
            }
        });
    }

    /***
     * Gunakan method ini untuk filtering Recyclerview
     * @param text digunakan untuk mengirim kata yang akan di filter pada RecyclerView
     */
    public void filter(String text) {
        listWord.clear();
        if(text.isEmpty()){
            listWord.addAll(baseList);
        } else{
            text = text.toLowerCase();
            for(WordModel item: baseList){
                if(item.getWord().toLowerCase().contains(text)){
                    listWord.add(item);
                }
            }
        }
        notifyDataSetChanged();
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
        private final TextView txtWord;

        ViewHolder(View itemView) {
            super(itemView);
            txtWord = itemView.findViewById(R.id.txt_word);
        }
    }
}
