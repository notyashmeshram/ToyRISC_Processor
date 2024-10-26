package processor.pipeline;

import generic.Misc;
import generic.Simulator;
import generic.Statistics;
import processor.Processor;
import processor.pipeline.IF_EnableLatchType;
import processor.pipeline.IF_OF_LatchType;
import processor.pipeline.OF_EX_LatchType;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}

	public void performOF()
	{
		IF_OF_Latch.setIsBusy(OF_EX_Latch.isBusy());

		if(IF_OF_Latch.isOF_enable() && !OF_EX_Latch.isBusy())
		{
			containingProcessor.getOF_EX_Latch().setisWaiting(IF_OF_Latch.isWaiting());

			if (!IF_OF_Latch.isWaiting())
			{
				String binaryInstruction = Integer.toBinaryString(IF_OF_Latch.getInstruction());
				while(binaryInstruction.length() < 32)
				{
					binaryInstruction = "0" + binaryInstruction;
				}
				int opcode = Integer.parseInt(binaryInstruction.substring(0, 5), 2);
				int imm = Misc.getIntFromBinaryString(binaryInstruction.substring(15, 32));
				int branchPC;
				if(opcode != 24)
				{
					int Imm = Misc.getIntFromBinaryString(binaryInstruction.substring(15, 32));
					branchPC = IF_OF_Latch.getPc()+Imm;
				}
				else
				{
					Integer rd = Integer.parseInt(binaryInstruction.substring(5, 10), 2);
					Integer Imm = Misc.getIntFromBinaryString(binaryInstruction.substring(10, 32));
					branchPC = IF_OF_Latch.getPc()+rd+Imm;
				}
				int rs1 = Integer.parseInt(binaryInstruction.substring(5, 10), 2);
				int op1 = containingProcessor.getRegisterFile().getValue(rs1);
				int rs2 = Integer.parseInt(binaryInstruction.substring(10, 15), 2);
				int op2 = containingProcessor.getRegisterFile().getValue(rs2);
				int rd;
				if(opcode <= 21 && opcode%2 == 0)
				{
					rd = Integer.parseInt(binaryInstruction.substring(15, 20), 2);
				}
				else if(opcode <= 21 && opcode%2 == 1)
				{
					rd = Integer.parseInt(binaryInstruction.substring(10, 15), 2);
				}
				else if(opcode >= 22 && opcode <= 23)
				{
					rd = Integer.parseInt(binaryInstruction.substring(10, 15), 2);
				}
				else if(opcode == 24)
				{
					rd = Integer.parseInt(binaryInstruction.substring(5, 10), 2);
				}
				else
				{
					rd = Integer.parseInt(binaryInstruction.substring(10, 15), 2);
				}
				OF_EX_Latch.setOpcode(opcode);
				OF_EX_Latch.setImm(imm);
				OF_EX_Latch.setOp1(op1);
				OF_EX_Latch.setOp2(op2);
				OF_EX_Latch.setPc(IF_OF_Latch.getPc());
				OF_EX_Latch.setRd(rd);
				OF_EX_Latch.setBranchPC(branchPC);

				boolean stall = false;
				if(opcode <= 21 && opcode%2 == 0)
				{
					int next_rd = containingProcessor.getEX_MA_Latch().getRd();
					boolean bubbled = containingProcessor.getEX_MA_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || next_rd == rs2 || 31 == rs1 || 31 == rs2))
					{
						stall = true;
					}
					next_rd = containingProcessor.getMA_RW_Latch().getRd();
					bubbled = containingProcessor.getMA_RW_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || next_rd == rs2 || 31 == rs1 || 31 == rs2))
					{
						stall = true;
					}

					next_rd = containingProcessor.getRW_Latch().getRd();
					bubbled = containingProcessor.getRW_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || next_rd == rs2 || 31 == rs1 || 31 == rs2))
					{
						stall = true;
					}

				}

				if((opcode <= 21 && opcode%2 == 1) || (opcode == 22))
				{
					int next_rd = containingProcessor.getEX_MA_Latch().getRd();
					boolean bubbled = containingProcessor.getEX_MA_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || 31 == rs1))
					{
						stall = true;
					}
					next_rd = containingProcessor.getMA_RW_Latch().getRd();
					bubbled = containingProcessor.getMA_RW_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || 31 == rs1))
					{
						stall = true;
					}

					next_rd = containingProcessor.getRW_Latch().getRd();
					bubbled = containingProcessor.getRW_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || 31 == rs1))
					{
						stall = true;
					}
				}

				if(opcode == 23 || (opcode >= 25 && opcode <= 28))
				{
					int next_rd = containingProcessor.getEX_MA_Latch().getRd();
					boolean bubbled = containingProcessor.getEX_MA_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || next_rd == rd || 31 == rd || 31 == rs1))
					{
						stall = true;
					}
					next_rd = containingProcessor.getMA_RW_Latch().getRd();
					bubbled = containingProcessor.getMA_RW_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || next_rd == rd || 31 == rd || 31 == rs1))
					{
						stall = true;
					}

					next_rd = containingProcessor.getRW_Latch().getRd();
					bubbled = containingProcessor.getRW_Latch().isWaiting();

					if(!bubbled && (next_rd == rs1 || next_rd == rd || 31 == rd || 31 == rs1))
					{
						stall = true;
					}
				}

				if(stall)
				{
					IF_OF_Latch.setIsBusy(true);
					OF_EX_Latch.setisWaiting(true);
					Statistics.setNumberOfStalls(Statistics.getNumberOfStalls() + 1);
					OF_EX_Latch.setEX_enable(true);
				}
				else
				{
					OF_EX_Latch.setEX_enable(true);
					IF_OF_Latch.setOF_enable(false);
				}
				if (opcode == 29)
				{
					containingProcessor.getIF_EnableLatch().setIF_enable(false);
				}
			}
			else
			{
				OF_EX_Latch.setEX_enable(true);
				IF_OF_Latch.setOF_enable(false);
			}
		}
	}
}
