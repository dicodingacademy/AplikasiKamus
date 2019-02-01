package com.dicoding.picodiploma.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WordModel implements Parcelable {

    private int id;
    private String word;
    private String description;

    public WordModel(String word, String description) {
        this.word = word;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.description);
    }

    public WordModel() {
    }

    private WordModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.description = in.readString();
    }

    public static final Creator<WordModel> CREATOR = new Creator<WordModel>() {
        @Override
        public WordModel createFromParcel(Parcel source) {
            return new WordModel(source);
        }

        @Override
        public WordModel[] newArray(int size) {
            return new WordModel[size];
        }
    };
}
