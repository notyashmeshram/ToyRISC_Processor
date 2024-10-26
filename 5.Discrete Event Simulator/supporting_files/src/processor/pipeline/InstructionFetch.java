package processor.pipeline;


import processor.Clock;
import generic.Element;
import generic.Event;
import generic.MemoryResponseEvent;
import generic.Simulator;
import generic.Statistics;
import generic.*;
import processor.Processor;
import processor.pipeline.EX_IF_LatchType;
import processor.pipeline.IF_EnableLatchType;
import processor.pipeline.IF_OF_LatchType;

public class InstructionFetch implements Element {

	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	int PC;

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performIF()
	{

		if(IF_EnableLatch.isIF_enable() && !IF_EnableLatch.isBusy())
		{
			IF_OF_Latch.setisWaiting(IF_EnableLatch.isWaiting());

			if(!IF_EnableLatch.isWaiting())
			{
				if(containingProcessor.getIsBranchTaken())
				{
					containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getBranchPC());
					containingProcessor.setIsBranchTaken(false);
				}
				PC = containingProcessor.getRegisterFile().getProgramCounter();
				Event event = new MemoryReadEvent(Clock.getCurrentTime(), this, containingProcessor.getMainMemory(), PC);
				Simulator.getEventQueue().addEvent(event);
				IF_EnableLatch.setIsBusy(true);
			}
			else
			{
				IF_OF_Latch.setOF_enable(true);
			}
		}
	}

	@Override
	public void handleEvent(Event e)
	{
		if(IF_OF_Latch.isBusy())
		{
			e.setEventTime(Clock.getCurrentTime()+1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			if(e.getEventType() == Event.EventType.MemoryResponse)
			{
				MemoryResponseEvent event = (MemoryResponseEvent) e;
				int instruction = event.getValue();
				IF_OF_Latch.setPc(PC);
				IF_OF_Latch.setInstruction(instruction);
				Statistics.setNumberOfDynamicInstructions(Statistics.getNumberOfDynamicInstructions() + 1);
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter() + 1);
				IF_EnableLatch.setIsBusy(false);
				IF_OF_Latch.setOF_enable(true);
			}
		}
	}
}
