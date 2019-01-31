package com.dicoding.picodiploma.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WordModel implements Parcelable {

    private int id;
    private String word;
    private String descrition;

    public WordModel(String word, String descrition) {
        this.word = word;
        this.descrition = descrition;
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

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.descrition);
    }

    public WordModel() {
    }

    protected WordModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.descrition = in.readString();
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
