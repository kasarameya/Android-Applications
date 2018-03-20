package com.tcs.remindmeapplication.services;

import java.util.ArrayList;

/**
 * Created by AVS on 24-Oct-15.
 */
public class RM_AutoCompleteObject {

    ArrayList<RM_AutoCompletePrediction> predictions=new ArrayList<>();
    String status;

    public ArrayList<RM_AutoCompletePrediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<RM_AutoCompletePrediction> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
