# ToyRISC Processor Simulator Project

This project implements a complete processor simulator for a custom RISC architecture called **ToyRISC**. The simulator progresses from basic assembly programming to the integration of advanced simulation components, including pipelining, caching, and discrete-event simulation, to model the internal workings of a processor.

## Project Overview

The ToyRISC Processor Simulator project is divided into six stages, each progressively enhancing the functionality of the simulator:

1. **ToyRISC Assembly Programming**: Writing programs in ToyRISC assembly to become familiar with the instruction set and overall architecture.
2. **Assembler Development**: Implementing an assembler that translates ToyRISC assembly code into machine code for simulator execution.
3. **Single-Cycle Processor Simulation**: Creating a single-cycle processor model that executes each instruction in one cycle.
4. **Pipelined Processor Simulation**: Introducing a pipelined core model to improve instruction throughput.
5. **Discrete Event Simulator**: Upgrading the pipeline model to a discrete-event simulator to manage event-driven stages and simulate latencies accurately.
6. **Cache Integration**: Adding instruction and data caches to model memory access times and analyze cache-related performance.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Setup and Execution](#setup-and-execution)
- [Detailed Stages](#detailed-stages)
  - [Stage 1: ToyRISC Assembly Programming](#stage-1-toyrisc-assembly-programming)
  - [Stage 2: Assembler Development](#stage-2-assembler-development)
  - [Stage 3: Single-Cycle Processor Simulation](#stage-3-single-cycle-processor-simulation)
  - [Stage 4: Pipelined Processor Simulation](#stage-4-pipelined-processor-simulation)
  - [Stage 5: Discrete Event Simulator](#stage-5-discrete-event-simulator)
  - [Stage 6: Cache Integration](#stage-6-cache-integration)
- [Testing](#testing)


## Features

- **ToyRISC Assembly Language Support**: Full support for ToyRISC assembly programming and execution.
- **Assembler**: Translates assembly code to machine code for processing.
- **Single-Cycle and Pipelined Processor Simulation**: Simulates different processor designs to understand trade-offs in performance and cycle time.
- **Discrete Event-Driven Simulation**: Enables accurate modeling of latencies between stages and memory.
- **Caching**: Instruction and data caches allow performance analysis and memory optimization.

## Setup and Execution

1. **Clone the Repository**: Clone the project repository:
    ```bash
    git clone https://gitea.iitdh.ac.in:443/rajshekar.k/CS311_assignment3.git
    ```

2. **Compile the Simulator**: Compile the Java files to prepare for running the simulator:
    ```bash
    javac -d bin src/**/*.java
    ```

3. **Run the Simulator**: Run the simulator with paths to the configuration file, statistics file, and object file:
    ```bash
    java -cp bin src.generic.Simulator <config-file-path> <statistics-file-path> <object-file-path>
    ```

## Detailed Stages

### Stage 1: ToyRISC Assembly Programming
This stage involves writing programs in ToyRISC assembly to familiarize with the architecture's instruction set. Programs developed here act as benchmarks for later stages.

### Stage 2: Assembler Development
A custom **ToyRISC Assembler** was developed to convert assembly code to machine code. This component reads the assembly instructions and outputs a binary file, enabling the processor simulator to execute ToyRISC programs.

### Stage 3: Single-Cycle Processor Simulation
A single-cycle processor model executes each instruction in one clock cycle. The simulator reads the configuration, loads the program, and generates a statistics file showing the number of cycles taken and instructions executed.

Key Highlights:
- **Program Loading**: Loads instructions and data into memory and initializes registers.
- **Execution**: Simulates instruction execution with a fixed-cycle approach.
- **Output**: Provides execution statistics.

### Stage 4: Pipelined Processor Simulation
The single-cycle processor was upgraded to a **pipelined model**. Each instruction goes through multiple stages, allowing for multiple instructions to be processed simultaneously, increasing throughput.

Key Features:
- **Pipeline Stages**: Includes stages for Instruction Fetch (IF), Operand Fetch (OF), Execution (EX), Memory Access (MA), and Write-Back (RW).
- **Interlocks**: Implements data and control interlocks to handle dependencies and avoid hazards.
- **Performance Metrics**: Outputs the number of cycles, stalls due to data hazards, and mispredicted branches.

### Stage 5: Discrete Event Simulator
This stage incorporates a **discrete-event simulation model**. The simulator introduces latency by processing stages as asynchronous events, handled through an event queue.

Key Features:
- **Event Queue**: Maintains events with specific timestamps to control stage processing.
- **Latency Modeling**: Configurable latencies for each stage and for main memory, with responses managed asynchronously.
- **Performance Tracking**: Measures and reports performance in terms of instructions per cycle.

### Stage 6: Cache Integration
Caches are added to the memory system to simulate realistic memory latencies and analyze the effect of caches on performance.

Features:
- **L1 Instruction Cache (L1i-cache)**: Placed between the Instruction Fetch stage and main memory.
- **L1 Data Cache (L1d-cache)**: Placed between the Memory Access stage and main memory.
- **Configurable Caches**: Supports configurable sizes (16B, 128B, 512B, 1kB) and latencies (1-4 cycles) for performance analysis.
- **Performance Analysis**: Compares performance across benchmarks by varying cache sizes and measuring instructions per cycle.

## Testing
Testing involves running a set of predefined ToyRISC programs and verifying:
- **Correct Output**: Each stage’s output matches expected results.
- **Performance Metrics**: Collected statistics, such as cycle count and cache hit rates, are compared against benchmarks.
