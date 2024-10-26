package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int instruction;
	private int rs1,rd,rs2,imm,opcode;

	public int getRs1() {
		return rs1;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public void setRs1(int rs1) {
		this.rs1 = rs1;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public int getRs2() {
		return rs2;
	}

	public void setRs2(int rs2) {
		this.rs2 = rs2;
	}

	public int getImm() {
		return imm;
	}

	public void setImm(int imm) {
		this.imm = imm;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	public void setValues(int rs1,int rs2,int rd, int imm, int opcode){
		setOpcode(opcode);
		setImm(imm);
		setRd(rd);
		setRs2(rs2);
		setRs1(rs1);
	}
	public int getInstruction(){
		return instruction;
	}



	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}



}
