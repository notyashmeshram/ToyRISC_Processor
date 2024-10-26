package processor.pipeline;

import generic.Statistics;
import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	private int  rs1,rs2,rd,imm,opcode,aluResult;


	public int getAluResult() {
		return aluResult;
	}

	public void setAluResult(int aluResult) {
		this.aluResult = aluResult;
	}

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performEX() {

		if(OF_EX_Latch.isEX_enable()) {
			int isBranchTaken = 0;
			if(!EX_MA_Latch.isMA_enable()){
				EX_MA_Latch.setMA_enable(true);
			}
			int diff=2;
			RegisterFile registerFile = containingProcessor.getRegisterFile();
			//TODO
			rs1 = OF_EX_Latch.getRs1();
			rs2 = OF_EX_Latch.getRs2();
			rd = OF_EX_Latch.getRd();
			imm = OF_EX_Latch.getImm();
			opcode = OF_EX_Latch.getOpcode();
//			System.out.println("EXECUTE "+opcode);
			switch (opcode) {
				case 0:
					aluResult = registerFile.getValue(rs1) + registerFile.getValue(rs2);
					break;
				case 1:
					aluResult = registerFile.getValue(rs1) + imm;
					break;
				case 2:
					aluResult = registerFile.getValue(rs1) - registerFile.getValue(rs2);
					break;
				case 3:
					aluResult = registerFile.getValue(rs1) - imm;
					break;
				case 4:
					aluResult = registerFile.getValue(rs1) * registerFile.getValue(rs2);
					break;
				case 5:
					aluResult = registerFile.getValue(rs1) * imm;
					break;
				case 6:
					aluResult = registerFile.getValue(rs1) / registerFile.getValue(rs2);
					int rem = registerFile.getValue(rs1) % registerFile.getValue(rs2);
					if(rem!=0){
						registerFile.setValue(31,rem);
					}
					break;
				case 7:
					aluResult = registerFile.getValue(rs1) / imm;
					int rem2 = registerFile.getValue(rs1) % imm;
					if(rem2!=0){
						registerFile.setValue(31,rem2);
					}
					break;
				case 8:
					aluResult = registerFile.getValue(rs1) & registerFile.getValue(rs2);
					break;
				case 9:
					aluResult = registerFile.getValue(rs1) & imm;
					break;
				case 10:
					aluResult = registerFile.getValue(rs1) | registerFile.getValue(rs2);
					break;
				case 11:
					aluResult = registerFile.getValue(rs1) | imm;
					break;
				case 12:
					aluResult = registerFile.getValue(rs1) ^ registerFile.getValue(rs2);
					break;
				case 13:
					aluResult = registerFile.getValue(rs1) ^ imm;
					break;
				case 14:
					if (registerFile.getValue(rs1) < registerFile.getValue(rs2)) {
						aluResult = 1;
					} else {
						aluResult = 0;
					}
					break;
				case 15:
					if (registerFile.getValue(rs1) < imm) {
						aluResult = 1;
					} else {
						aluResult = 0;
					}
					break;
				case 16:
					aluResult = registerFile.getValue(rs1) << registerFile.getValue(rs2);
					break;
				case 17:
					aluResult = registerFile.getValue(rs1) << imm;
					break;
				case 18:
					int rs1Positive = Math.abs(registerFile.getValue(rs1));
					aluResult = rs1Positive >> registerFile.getValue(rs2);
					break;
				case 19:
					int rs1Pos = Math.abs(registerFile.getValue(rs1));
					aluResult = rs1Pos >> imm;
				case 20:
					aluResult = registerFile.getValue(rs1) >> registerFile.getValue(rs2);
					break;
				case 21:
					aluResult = registerFile.getValue(rs1) >> imm;
					break;
				case 22:
					aluResult = registerFile.getValue(rs1) + imm;
//					System.out.println(registerFile.getValue(rs1) + " "+imm);
					break;
				case 23:
					aluResult = registerFile.getValue(rd) + imm;
					break;
				case 24:
					if(containingProcessor.noOfNops!=0){
						diff=1;
					}
//					System.out.println(imm);
					containingProcessor.getRegisterFile().setProgramCounter(registerFile.getProgramCounter() + imm-diff);
					isBranchTaken = 1;
					System.out.println("JUMP PROGRAM COUNTER : " + registerFile.getProgramCounter());
					break;
				case 25:
					if(containingProcessor.noOfNops!=0){
						diff=1;
					}
					if (registerFile.getValue(rs1) == registerFile.getValue(rd)) {
						containingProcessor.getRegisterFile().setProgramCounter(registerFile.getProgramCounter() + imm-diff);
						isBranchTaken = 1;
					}
					break;
				case 26:
					if(containingProcessor.noOfNops!=0){
						diff=1;
					}
					if (registerFile.getValue(rs1) != registerFile.getValue(rd)) {
						containingProcessor.getRegisterFile().setProgramCounter(registerFile.getProgramCounter() + imm-diff);
						isBranchTaken = 1;
					}
					break;
				case 27:
					if(containingProcessor.noOfNops!=0){
						diff=1;
					}
					if (registerFile.getValue(rs1) < registerFile.getValue(rd)) {
						containingProcessor.getRegisterFile().setProgramCounter(registerFile.getProgramCounter() + imm-diff);
						System.out.println("BLT PROGRAM COUNTER : " + registerFile.getProgramCounter());
						isBranchTaken = 1;
					}
					break;
				case 28:
					if(containingProcessor.noOfNops!=0){
						diff=1;
					}
					if (registerFile.getValue(rs1) > registerFile.getValue(rd)) {
						containingProcessor.getRegisterFile().setProgramCounter(registerFile.getProgramCounter() + imm-diff);
						isBranchTaken = 1;
					}
					break;
				case 29:
					break;
//				default:
//					System.out.println("Error in Execute.JAVA");
			}
			if(isBranchTaken == 1){
				containingProcessor.isBranchTaken = true;
				Statistics.wrongBranchPaths++;
			}
			EX_MA_Latch.setAluResult(aluResult);
			EX_MA_Latch.setRd(rd);
			EX_MA_Latch.setRs1(rs1);
			EX_MA_Latch.setOpcode(opcode);
			System.out.println("EX instruction : "+opcode);
//			OF_EX_Latch.setEX_enable(false);
//			EX_MA_Latch.setMA_enable(true);
		}
	}

}
