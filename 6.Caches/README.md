# ToyRISC Processor Simulator with Caches

This final stage of the ToyRISC processor simulator project adds a **memory cache system** to enhance memory access performance. Two level-1 caches are implemented:
- An **L1 instruction cache (L1i-cache)** between the Instruction Fetch (IF) stage and main memory.
- An **L1 data cache (L1d-cache)** between the Memory Access (MA) stage and main memory.

Each cache is configurable in terms of size, associativity, and latency, simulating a real-world cache system.

## Cache Configurations
The caches are fully associative with a **line size of 4B** and a **write-through policy**. Latency varies by cache size as follows:

| Cache Size | Latency |
|------------|---------|
| 16B        | 1 cycle |
| 128B       | 2 cycles |
| 512B       | 3 cycles |
| 1kB        | 4 cycles |

### Classes Introduced
- **Cache**: A generic cache class that is instantiated as both the L1i-cache and L1d-cache.
- **CacheLine**: Represents an individual cache line with fields for data (4B array) and a tag for address matching.

## Modifications in the Simulation

### Cache Class Overview
The `Cache` class, located in the `processor/memorysystem` package, is designed to support reading and writing operations with latency and write-through behavior. Key methods include:
- **cacheRead(int address)**: Checks for the requested line in the cache. If found, it returns the value to the pipeline. If not, it calls `handleCacheMiss(int address)`.
- **cacheWrite(int address, int value)**: Checks if the line is present in the cache. If so, it writes the value to both the cache line and main memory (write-through). If the line is not present, `handleCacheMiss(int address)` is called.
- **handleCacheMiss(int address)**: Requests the main memory for the missing cache line.
- **handleResponse()**: Processes the response from main memory, inserting the new line into the cache. If a pending read or write exists, it completes the action accordingly.

### Cache System Integration in `Simulator.java`
1. **L1i-cache**: Handles instruction fetches from the IF stage. If an instruction is not in the cache, it retrieves the line from main memory.
2. **L1d-cache**: Manages data access during load/store operations in the MA stage. It similarly retrieves data from main memory on a cache miss.

## Performance Analysis
### Experimental Procedure
1. **L1i-cache Variations**:
   - Fixed the L1d-cache size at 1kB.
   - Varied the size of the L1i-cache (16B, 128B, 512B, 1kB) and observed performance in terms of instructions per cycle (IPC) across benchmark programs.
2. **L1d-cache Variations**:
   - Fixed the L1i-cache size at 1kB.
   - Varied the size of the L1d-cache (16B, 128B, 512B, 1kB) and measured IPC for the same benchmarks.

### Data Analysis
- Plotted IPC results for each cache configuration across different benchmarks.
- Analyzed performance trends, correlating benchmark behavior with cache size and latency.

## Implementation

- **Cache Structure**: Created an array of `CacheLine` objects in the `Cache` class, sized to hold the required cache lines.
- **Cache Line Replacement**: As caches are fully associative, implemented a least-recently-used (LRU) replacement policy.


## Testing and Submission
### Testing
- Test the implementation with benchmarks used in previous assignments.
- Ensure the hash output matches expected results to verify functional correctness.
