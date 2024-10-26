package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	int aluResult, rd, opcode, loadVal;


	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public void performRW() {

		if(MA_RW_Latch.isRW_enable()) {
			//TODO
//			containingProcessor.getRegisterFile().setValue(27,0);
			opcode = MA_RW_Latch.getOpcode();
			aluResult = MA_RW_Latch.getAluResult();
			rd = MA_RW_Latch.getRd();

			loadVal = MA_RW_Latch.getLoadVal();
//			System.out.println("last "+opcode);
			if(opcode==29){
				//REMOVE
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.endPc);
				//REMOVE
				Simulator.setSimulationComplete(true);
			} else if(opcode== 22){
				System.out.println("RW: Load value is : " + loadVal);
				System.out.println("RW: Rd is : "+ rd);
//				System.out.println(rd);
				containingProcessor.getRegisterFile().setValue(rd,loadVal);
			} else if(opcode <= 21){
//				System.out.println(rd);
				containingProcessor.getRegisterFile().setValue(rd,aluResult);
			}
//			MA_RW_Latch.setRW_enable(false);
//			IF_EnableLatch.setIF_enable(true);
			System.out.println("RW instruction : "+opcode);
		}
	}

}
