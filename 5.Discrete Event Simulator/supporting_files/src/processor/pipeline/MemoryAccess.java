package processor.pipeline;

import generic.*;
import generic.Event.EventType;
import processor.Clock;
import processor.Processor;
import processor.pipeline.EX_MA_LatchType;
import processor.pipeline.MA_RW_LatchType;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	int address;
	int data;

	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}

	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable() && !EX_MA_Latch.isBusy())
		{
			MA_RW_Latch.setisWaiting(EX_MA_Latch.isWaiting());

			if(!EX_MA_Latch.isWaiting())
			{
				int opcode = EX_MA_Latch.getOpcode();

				if(opcode == 22)
				{
					address = EX_MA_Latch.getAluResult();
					Event event = new MemoryReadEvent(Clock.getCurrentTime(), this, containingProcessor.getMainMemory(), address);
					Simulator.getEventQueue().addEvent(event);
					EX_MA_Latch.setIsBusy(true);
				}
				else if(opcode == 23)
				{
					data = EX_MA_Latch.getOp1();
					address = EX_MA_Latch.getAluResult();
					Event event = new MemoryWriteEvent(Clock.getCurrentTime(), this, containingProcessor.getMainMemory(), address, data);
					Simulator.getEventQueue().addEvent(event);
					EX_MA_Latch.setIsBusy(true);
				}
				else
				{
					MA_RW_Latch.setRW_enable(true);
				}
				MA_RW_Latch.setAluResult(EX_MA_Latch.getAluResult());
				MA_RW_Latch.setOpcode(opcode);
				MA_RW_Latch.setRd(EX_MA_Latch.getRd());
				MA_RW_Latch.setR31(EX_MA_Latch.getR31());
				MA_RW_Latch.setPc(EX_MA_Latch.getPc());
				EX_MA_Latch.setMA_enable(false);
			}
			else
			{
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
		}
	}

	@Override
	public void handleEvent(Event e) {
		if(e.getEventType() == EventType.MemoryResponse)
		{
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			int result = event.getValue();
			MA_RW_Latch.setLdResult(result);
			EX_MA_Latch.setIsBusy(false);
			MA_RW_Latch.setRW_enable(true);
		}
		else if(e.getEventType() == EventType.MemoryResponse)
		{
			EX_MA_Latch.setIsBusy(false);
			MA_RW_Latch.setRW_enable(true);
		}
	}
}