package processor.pipeline;

public class RW_LatchType {

    int rd = -1;
    boolean isWaiting = false;

    public RW_LatchType()
    {
    }

    public int getRd() {
        return rd;
    }

    public void setRd(int rd) {
        this.rd = rd;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setisWaiting(boolean isWaiting) {
        this.isWaiting = isWaiting;
    }
}