package processor.pipeline;

public class RW_LatchType {

	int rd = -1;
	boolean isBubbled = false;

	public RW_LatchType()
	{
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public boolean isBubbled() {
		return isBubbled;
	}

	public void setIsBubbled(boolean isBubbled) {
		this.isBubbled = isBubbled;
	}
}