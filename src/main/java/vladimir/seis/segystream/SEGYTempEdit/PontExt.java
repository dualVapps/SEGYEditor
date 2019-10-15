package vladimir.seis.segystream.SEGYTempEdit;

import java.awt.*;

public class PontExt extends Point { //TODO Delete if not nes.
    private int reelNumber;

    public PontExt(int reelNumber) {
        this.reelNumber = reelNumber;
    }

    public PontExt(Point p, int reelNumber) {
        super(p);
        this.reelNumber = reelNumber;
    }

    public PontExt(int x, int y, int reelNumber) {
        super(x, y);
        this.reelNumber = reelNumber;
    }

    public int getReelNumber() {
        return reelNumber;
    }

    public void setReelNumber(int reelNumber) {
        this.reelNumber = reelNumber;
    }


}
