package com.example.android.myapplication.Model;

public class SpnModels {
    private String mModelName = "";
    private int mModelConstant = 0;

    public SpnModels(String modelName, int modelConstant) {
        mModelName = modelName;
        mModelConstant = modelConstant;
    }

    public int getModelConstant() {
        return mModelConstant;
    }

    @Override
    public String toString() {
        return mModelName;
    }
}
