package main.java.vladimir.seis.segystream.SEGYTempEdit;

public class TrimLawSingleValue {
    int  datasetValue;
    int sampleValue;
    double dataValue;
    int x;
    int y;


    public TrimLawSingleValue(int datasetValue, int sampleValue, double dataValue, int x, int y) {
        this.datasetValue = datasetValue;
        this.sampleValue = sampleValue;
        this.dataValue = dataValue;
        this.x = x;
        this.y = y;
    }

    public int getSampleValue() {
        return sampleValue;
    }

    public void setSampleValue(int sampleValue) {
        this.sampleValue = sampleValue;
    }

    public double getDataValue() {
        return dataValue;
    }

    public void setDataValue(double dataValue) {
        this.dataValue = dataValue;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
