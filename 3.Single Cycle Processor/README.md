# ToyRISC Single Cycle Processor Simulator

This project is a simulator for a single-cycle processor implementing the ToyRISC architecture. The primary goal of this simulator is to emulate the ToyRISC processor, processing and executing instructions from an input object file and outputting simulation statistics. This project serves as a foundation for future assignments where additional features and files will be added to expand the processor's capabilities.

## Input
The program expects three main inputs:
1. **Configuration file**: The path to the XML configuration file, which describes the processor’s parameters. (Currently, this file is not extensively used, but it will be in later stages of the project).
   - Sample Path: `src/configuration/config.xml`
2. **Statistics file**: The path where the output statistics will be saved. This file logs details of the simulation, including the number of instructions executed and the number of cycles taken.
3. **Object file**: The binary file containing instructions to be executed by the simulator.

## Output
The simulator generates a **statistics file** containing:
- The total number of instructions executed
- The total number of cycles taken
- Any additional custom statistics (optional) added in `Statistics.java`

## Setup and Execution

### Step 1: Load the Program
Implement the `loadProgram` function in `Simulator.java` to initialize the program:
- Set the `PC` (Program Counter) to the address of the first instruction in memory.
- Initialize `x0 = 0`, `x1 = 2^16 - 1`, and `x2 = 2^16 - 1` as per the assignment specifications.
- The main memory should store the global data and instructions based on the object file.

### Step 2: Run the Simulation
The primary function, `simulate()` (in `Simulator.java`), executes the simulation in five stages. To complete the simulation:
1. Implement the five stages as methods in `Simulator.java`.
2. Add the missing pipeline latches and ensure the stages pass data correctly through the latches.
3. The simulation should end when an `end` instruction passes through the IF stage.

### Step 3: Set Statistics
When the simulation completes, update the statistics (number of instructions and cycles) using `Statistics.java`.

## Implementation Details

### Processor Pipeline
The simulation pipeline includes five stages:
1. **Instruction Fetch (IF)**: Fetch and decode the instruction.
2. **Operand Fetch (OF)**: Implement the logic to fetch operands.
3. **Execution (EX)**: Execute the instruction logic.
4. **Memory (MEM)**: Handle memory operations.
5. **Write-Back (WB)**: Update registers with execution results.

Each stage is interconnected with latches that store data between stages, allowing the simulation to move instructions through the pipeline. For example, the `IF_OF_LatchType` connects the IF and OF stages.

### Address Space Layout
Store global data and instructions according to the memory layout defined in assignment 1. Check memory contents with:
```java
processor.getMainMemory().getContentsAsString(<starting-address>, <ending-address>);
```
### Simulation Completion
The simulation ends when the `end` instruction completes the IF stage. Call `setSimulationComplete()` from `Simulator.java` at this point.

### Testing
1. **ToyRISC Programs**: Test the simulator with sample ToyRISC programs provided in the project. Each program includes:
 - Object codes.
 - Expected processor states.
 - Hash values representing expected final states.
2. **Automated Testing:** Run the following command to verify the simulator output:
```bash
python test_zip.py <path_to_zip_file>
```
3. Ensure the hash values match the expected output to verify correctness.