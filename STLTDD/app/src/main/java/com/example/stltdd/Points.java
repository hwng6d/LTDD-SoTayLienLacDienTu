package com.example.stltdd;

public class Points {
    private double midsem_point;
    private double endsem_point;

    public Points() {
    }

    public Points(double midsem_point, double endsem_point) {
        this.midsem_point = midsem_point;
        this.endsem_point = endsem_point;
    }

    public double getMidsem_point() {
        return midsem_point;
    }

    public void setMidsem_point(double midsem_point) {
        this.midsem_point = midsem_point;
    }

    public double getEndsem_point() {
        return endsem_point;
    }

    public void setEndsem_point(double endsem_point) {
        this.endsem_point = endsem_point;
    }
}
