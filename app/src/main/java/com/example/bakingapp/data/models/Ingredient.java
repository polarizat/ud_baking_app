package com.example.bakingapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    public static final String KEY_EXTRA_INGREDIENTS = "ingredients";

    @SerializedName(value = "ingredient")
    @ColumnInfo(name = "ingredient")
    private String name;
    private String quantity;
    private String measure;

    public Ingredient(String name, String quantity, String measure) {
        this.name = name;
        this.quantity = quantity;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.quantity);
        dest.writeString(this.measure);
    }

    protected Ingredient(Parcel in) {
        this.name = in.readString();
        this.quantity = in.readString();
        this.measure = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
