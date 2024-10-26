package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	int opcode,rs1,rd,aluResult,loadVal;

	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch) {
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}

	public void performMA() {
		if(EX_MA_Latch.isMA_enable()) {
			if(!MA_RW_Latch.isRW_enable()){
				MA_RW_Latch.setRW_enable(true);
			}
			opcode = EX_MA_Latch.getOpcode();
			rs1 = EX_MA_Latch.getRs1();
			rd = EX_MA_Latch.getRd();
			aluResult = EX_MA_Latch.getAluResult();
//			System.out.println("MEMORYA "+opcode);
			if (opcode == 23) { // Store
//				System.out.println("address");
				containingProcessor.getMainMemory().setWord(aluResult, containingProcessor.getRegisterFile().getValue(rs1));
			}
			if (opcode == 22) { // load
				loadVal = containingProcessor.getMainMemory().getWord(aluResult);
				System.out.println("Load value is : " + loadVal);
				System.out.println("Rd is : "+ rd);
			}
			MA_RW_Latch.setAluResult(aluResult);
			MA_RW_Latch.setRd(rd);
			MA_RW_Latch.setOpcode(opcode);
			MA_RW_Latch.setLoadVal(loadVal);
			System.out.println("MA instruction : "+opcode);
//			EX_MA_Latch.setMA_enable(false);
//			MA_RW_Latch.setRW_enable(true);
		}
	}

}
