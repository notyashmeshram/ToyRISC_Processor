package processor.pipeline;

import generic.Simulator;
import processor.Processor;
import processor.pipeline.IF_EnableLatchType;
import processor.pipeline.MA_RW_LatchType;
import processor.pipeline.RW_LatchType;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	RW_LatchType RW_Latch;

	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch, RW_LatchType rW_Latch) {
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.RW_Latch = rW_Latch;
	}

	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			if (!MA_RW_Latch.isWaiting())
			{
				int opcode = MA_RW_Latch.getOpcode();
				int rd = MA_RW_Latch.getRd();
				int aluResult = MA_RW_Latch.getAluResult();
				int ldResult = MA_RW_Latch.getLdResult();

				if(opcode == 29)
				{
					Simulator.setSimulationComplete(true);
				}

				if(opcode>=0 && opcode <=21)
				{
					containingProcessor.getRegisterFile().setValue(rd, aluResult);
				}

				if(opcode == 22)
				{
					containingProcessor.getRegisterFile().setValue(rd, ldResult);
				}

				int r31 = MA_RW_Latch.getR31();
				if(opcode >= 0 && opcode <= 21 && r31 != -1)
				{
					containingProcessor.getRegisterFile().setValue(31, r31);
				}
			}
			RW_Latch.setRd(MA_RW_Latch.getRd());
			RW_Latch.setisWaiting(MA_RW_Latch.isWaiting());
			MA_RW_Latch.setRW_enable(false);
		}
	}
}
