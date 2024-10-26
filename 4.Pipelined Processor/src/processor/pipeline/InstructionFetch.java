package processor.pipeline;

import conflict.Conflict;
import generic.Statistics;
import processor.Processor;

public class InstructionFetch {

	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;

	Conflict conflict;

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		conflict = new Conflict();
	}

	private String convertTo32bit(String input){
		String ans = input;
		while(ans.length()<32){
			ans = "0"+ans;
		}
		return ans;
	}
	public void performIF() {
		if(IF_EnableLatch.isIF_enable()) {
			if(!IF_OF_Latch.isOF_enable()){
				IF_OF_Latch.setOF_enable(true);
			}

			if(containingProcessor.isBranchTaken){
				System.out.println("Branch Taken NoP");
				IF_OF_Latch.setInstruction(0);
				containingProcessor.isBranchTaken = false;
				containingProcessor.noOfNops++;
				return;
			}
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			if(containingProcessor.forceInstruction){
				System.out.println("FORCED");
				IF_OF_Latch.setInstruction(newInstruction);
				System.out.println("PC : "+ currentPC);
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
				containingProcessor.forceInstruction=false;
				return;
			}
			if(containingProcessor.noOfNops != 0){
				Statistics.dataHazards++;
				System.out.println("Conflict NoP");
				IF_OF_Latch.setInstruction(0);
				containingProcessor.noOfNops--;
				if(containingProcessor.noOfNops==0){
					containingProcessor.forceInstruction=true;
				}
				return;
			}
//			System.out.println("instruction : "+ convertTo32bit(Integer.toBinaryString(newInstruction)));
//			System.out.println("PC : "+ currentPC);
			if(currentPC == containingProcessor.firstPc){
				IF_OF_Latch.setInstruction(newInstruction);
				System.out.println("PC : "+ currentPC);
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
				return;
			} else if (currentPC==containingProcessor.firstPc+1) {
				int lastInstruction = containingProcessor.getMainMemory().getWord(currentPC-1);
				String newinstructionInStringForm = Integer.toBinaryString(newInstruction);
				newinstructionInStringForm = convertTo32bit(newinstructionInStringForm);
				String lastInstructionInStringForm = Integer.toBinaryString(lastInstruction);
				lastInstructionInStringForm = convertTo32bit(lastInstructionInStringForm);
				if(conflict.isConflict(lastInstructionInStringForm,newinstructionInStringForm)){
					containingProcessor.noOfNops+=2;
				}

			}
			else {
				int lastInstruction = containingProcessor.getMainMemory().getWord(currentPC - 1);
				String newinstructionInStringForm = Integer.toBinaryString(newInstruction);
				newinstructionInStringForm = convertTo32bit(newinstructionInStringForm);
				String lastInstructionInStringForm = Integer.toBinaryString(lastInstruction);
				lastInstructionInStringForm = convertTo32bit(lastInstructionInStringForm);

				int lastInstruction2 = containingProcessor.getMainMemory().getWord(currentPC - 2);
				String lastInstructionInStringForm2 = Integer.toBinaryString(lastInstruction2);
				lastInstructionInStringForm2 = convertTo32bit(lastInstructionInStringForm2);

				if (conflict.isConflict(lastInstructionInStringForm, newinstructionInStringForm) || conflict.isConflict(lastInstructionInStringForm2, newinstructionInStringForm)) {
					containingProcessor.noOfNops += 2;
				}
			}
			if(containingProcessor.noOfNops != 0){
				Statistics.dataHazards++;
				System.out.println("Conflict NoP");
				IF_OF_Latch.setInstruction(0);
				containingProcessor.noOfNops--;
				if(containingProcessor.noOfNops==0){
					containingProcessor.forceInstruction=true;
				}
				return;
			}
//			System.out.println(newInstruction);
			IF_OF_Latch.setInstruction(newInstruction);
			System.out.println("PC : "+ currentPC);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);

//			IF_EnableLatch.setIF_enable(false);
//			IF_OF_Latch.setOF_enable(true);
		}
	}

}
