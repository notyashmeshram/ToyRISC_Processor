package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int aluResult, rd, opcode, loadVal;

	public int getAluResult() {
		return aluResult;
	}

	public void setAluResult(int aluResult) {
		this.aluResult = aluResult;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public int getLoadVal() {
		return loadVal;
	}

	public void setLoadVal(int loadVal) {
		this.loadVal = loadVal;
	}

	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

}
