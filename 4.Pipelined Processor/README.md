# ToyRISC Pipelined Processor Simulator

This project extends the ToyRISC single-cycle processor simulator by upgrading it to a pipelined core model. The pipelined simulator processes instructions concurrently across multiple stages, improving performance but introducing the need to handle hazards and interlocks.

## Input
The program takes the same three input files as in the previous assignment:
1. **Configuration file**: Path to the XML configuration file (currently unused, reserved for future assignments).
2. **Statistics file**: Path for the output statistics file that logs details of the simulation, including the number of cycles and stalls encountered.
3. **Object file**: Binary file with instructions to be executed by the simulator.

## Output
The simulator generates a **statistics file** containing:
- The total number of cycles taken to complete the simulation.
- The number of stalls due to data hazards in the Operand Fetch (OF) stage.
- The count of instructions that entered the pipeline on a wrong branch path.
- Any additional custom statistics (optional) added in `Statistics.java`.

## Setup and Execution

### Step 1: Update `simulate()` Function
The `simulate()` function in `Simulator.java` now follows a pipelined structure:
- Implement a loop in `simulate()` where each cycle performs the following sequence:
  1. **performRW** (Read-Write): Write back results to registers.
  2. **performMA** (Memory Access): Access memory if needed.
  3. **performEX** (Execute): Execute the instruction.
  4. **performOF** (Operand Fetch): Fetch operands.
  5. **performIF** (Instruction Fetch): Fetch the next instruction.
- After performing these operations, increment the clock by 1.

### Step 2: Handle Invalid Latch Contents
Each pipeline stage operates on outputs from the previous cycle's stage. Ensure that:
- If a latch (e.g., `MA-RW latch`) has invalid data, the stage does nothing during that cycle.

### Step 3: Implemented Data and Control Interlocks
- **Data Interlocks**: Implemented logic to detect and resolve data hazards by stalling the pipeline where necessary. For example, if the OF stage depends on data from an instruction in the EX stage, stall the pipeline until the dependency is resolved.
- **Control Interlocks**: Implemented control hazard handling to flush or stall instructions on a wrong branch path.

## Implementation Details

### Pipeline Hazards
The pipelined model introduces two main types of hazards:
1. **Data Hazards**: Occur when instructions in the pipeline depend on results from previous instructions. To handle data hazards:
   - Detect hazards in the OF stage and introduce stalls as needed.
2. **Control Hazards**: Occur due to branching. If a branch prediction is incorrect, flush the incorrect instructions from the pipeline.

### Simulation Completion
The simulation ends when the `end` instruction has passed through the entire pipeline. Call `setSimulationComplete()` from `Simulator.java` at this point.

## Testing and Submission

### Testing
1. Run the test cases from the previous assignment to ensure backward compatibility.
2. Verify the results of each ToyRISC program using the updated pipelined simulator. Track metrics including:
   - Total cycle count.
   - The number of stalls in the OF stage due to data hazards.
   - The count of mispredicted branch instructions.
