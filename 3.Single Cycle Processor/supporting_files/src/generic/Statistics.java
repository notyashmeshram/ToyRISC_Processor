package generic;

import java.io.PrintWriter;

public class Statistics {

	// TODO add your statistics here
	static int statics;
	static int dynamics;
	static int cycles;
	static float inst_per_cycle;
	static float frequency;


	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);

			writer.println("Static Instructions: " + statics);
			writer.println("Dynamic Instructions: " + dynamics);
			writer.println("Cycles Taken: " + cycles);
			writer.println("Instruction Per Cycle: " + inst_per_cycle);
			writer.println("Frequency: " + frequency + " Gega Hertz");

			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}

	public static void setNumberOfStaticInstructions(int statics) {
		Statistics.statics = statics;
	}

	public static int getNumberOfStaticInstructions() {
		return statics;
	}

	public static void setNumberOfDynamicInstructions(int dynamics) {
		Statistics.dynamics = dynamics;
	}

	public static int getNumberOfDynamicInstructions() {
		return dynamics;
	}

	public static void setNumberOfCycles(int cycles) {
		Statistics.cycles = cycles;
	}

	public static int getNumberOfCycles() {
		return cycles;
	}

	public static void setIPC(float inst_per_cycle) {
		Statistics.inst_per_cycle = inst_per_cycle;
	}

	public static float getIPC() {
		return inst_per_cycle;
	}

	public static void setFrequency(float frequency) {
		Statistics.frequency = frequency;
	}

	public static float getFrequency() {
		return frequency;
	}
}