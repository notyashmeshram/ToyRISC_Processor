package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	private int opcode, rs1,rs2,rd,imm;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}

	private String convertTo32bit(String input){
		String ans = input;
		while(ans.length()<32){
			ans = "0"+ans;
		}
		return ans;
	}

	public void performOF() {
		//TODO
		if(IF_OF_Latch.isOF_enable()) {
			if(!OF_EX_Latch.isEX_enable()){
				OF_EX_Latch.setEX_enable(true);
			}
			if(containingProcessor.isBranchTaken){
				System.out.println("Branch Taken NoP");
				OF_EX_Latch.setInstruction(0);
				OF_EX_Latch.setRs1(0);
				OF_EX_Latch.setRs2(0);
				OF_EX_Latch.setRd(0);
				OF_EX_Latch.setImm(0);
				OF_EX_Latch.setOpcode(0);
				return;
			}
			int instruction = IF_OF_Latch.getInstruction();
//			System.out.println("c");
//			System.out.println("instriction " +instruction );
			String instructionInStringForm = Integer.toBinaryString(instruction);
//			System.out.println(instructionInStringForm);
			instructionInStringForm = convertTo32bit(instructionInStringForm);
//			System.out.println(instructionInStringForm);
			opcode = Integer.parseInt(instructionInStringForm.substring(0,5),2);
			if(opcode==29){
				if(!containingProcessor.isEndTaken){
					containingProcessor.isEndTaken=true;
					containingProcessor.endPc=containingProcessor.getRegisterFile().getProgramCounter();
				}
			}
			if(opcode==24){
				rd = Integer.parseInt(instructionInStringForm.substring(5,10),2);
				imm = Integer.parseInt(instructionInStringForm.substring(10,32),2);
				if( imm > 2097151 ) {
					imm = -4194304 + imm;
				}
//				System.out.println("hii1 "+imm);
			} else if(opcode==22 || (opcode<24 && opcode%2==1) || opcode >24){
				rs1 = Integer.parseInt(instructionInStringForm.substring(5,10),2);
				rd = Integer.parseInt(instructionInStringForm.substring(10,15),2);
				imm = Integer.parseInt(instructionInStringForm.substring(15,32),2);
				if( imm > 65535 ) {
					imm = -131072 + imm;
				}
//				System.out.println("hiii2 "+imm);

			} else if(opcode<24 && opcode %2==0){
				rs1 = Integer.parseInt(instructionInStringForm.substring(5,10),2);
				rs2 = Integer.parseInt(instructionInStringForm.substring(10,15),2);
				rd = Integer.parseInt(instructionInStringForm.substring(15,20),2);
			}
			System.out.println("OF instruction : "+opcode);
//			System.out.println("a");
			OF_EX_Latch.setValues(rs1,rs2,rd,imm,opcode);
//			System.out.println("b");
//			System.out.println("opcode= " +opcode);
//			IF_OF_Latch.setOF_enable(false);
//			OF_EX_Latch.setEX_enable(true);
		}
	}
}
