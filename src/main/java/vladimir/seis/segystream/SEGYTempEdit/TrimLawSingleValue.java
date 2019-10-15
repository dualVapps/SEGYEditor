package vladimir.seis.segystream.SEGYTempEdit;


public class TrimLawSingleValue{
    int reelNumber;
    int  datasetValue;
    int sampleValue;
    double dataValue;
    double x;
    double y;


    public TrimLawSingleValue( double x, double y, int reelNumber, int datasetValue, int sampleValue, double dataValue) {

        this.x = x;
        this.y = y;
        this.reelNumber = reelNumber;
        this.datasetValue = datasetValue;
        this.sampleValue = sampleValue;
        this.dataValue = dataValue;

    }

    public TrimLawSingleValue(int reelNumber,int datasetValue, int sampleValue) {

        this.x = -1;
        this.y = -1;
        this.reelNumber = reelNumber;
        this.datasetValue = datasetValue;
        this.sampleValue = sampleValue;
        this.dataValue = -1;
    }



    public TrimLawSingleValue(double x, double y) {
        this.x = x;
        this.y = y;
        this.reelNumber = -1;
        this.datasetValue = -1;
        this.sampleValue = -1;
        this.dataValue = -1;


    }

    public int getReelNumber() {
        return reelNumber;
    }

    public void setReelNumber(int reelNumber) {
        this.reelNumber = reelNumber;
    }

    public int getDatasetValue() {
        return datasetValue;
    }

    public void setDatasetValue(int datasetValue) {
        this.datasetValue = datasetValue;
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

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
