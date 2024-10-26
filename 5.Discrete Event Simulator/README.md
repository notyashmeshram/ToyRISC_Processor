# ToyRISC Discrete Event Processor Simulator

This stage of the project upgrades the ToyRISC pipelined processor simulator to a **Discrete Event Simulator** (DES). The DES model manages events for various units asynchronously, simulating realistic latencies and concurrent operations within the processor. This setup allows for precise modeling of memory and functional unit latencies.

## Input
The program inputs remain the same:
1. **Configuration file**: XML file defining processor parameters.
2. **Statistics file**: Path where simulation statistics are saved.
3. **Object file**: Binary instructions to be executed by the simulator.

## Output
The output remains the statistics file, which now includes:
- The total number of cycles taken.
- The throughput of the processor in **instructions per cycle (IPC)**.

## New Source Files
- `Element.java`
- `Event.java`
- `EventQueue.java`
- `ExecutionCompleteEvent.java`
- `MemoryReadEvent.java`
- `MemoryResponseEvent.java`
- `MemoryWriteEvent.java`

## Modifications in `Simulator.java`

### Event Queue
Added `static EventQueue eventQueue;` as a data member in `Simulator.java`, and initialized it in the constructor:
```java
eventQueue = new EventQueue();
```
## Updated simulate() Loop
The main simulation loop now processes events between pipeline stages to handle asynchronous operations:

```java
while (not end of simulation) {
    performRW();
    performMA();
    performEX();
    eventQueue.processEvents();  // Processes events based on current clock cycle
    performOF();
    performIF();
    increment clock by 1;
}
```
### New Event Queue Getter
Added the following getter for `eventQueue`:

```java
public static EventQueue getEventQueue() {
    return eventQueue;
}
```
## Discrete Event Simulator Model
An event in this model is defined as:

```text
<event time, event type, requesting element, processing element, payload>
```
The event queue holds all events, ordered by event time. When the current clock reaches an event’s scheduled time, the event "fires," triggering the `handleEvent()` function of its processing element.

  - **Event Creation:** Events are created by units like the Instruction Fetch (IF) stage when memory is accessed or instructions are fetched.
  - **Event Handling:** When an event fires, the relevant `handleEvent()` function is called, which may create further events for the same or future cycles.

## Implementation Details

### Modeling Units as Event Elements
- Decide which processor units should receive events and implement the `Element` interface for them.
- Implement `handleEvent()` for each unit that processes events.

### Example Implementation: Instruction Fetch (IF) Stage
Here’s a simplified view of the performIF() method in the IF stage:

```java
public void performIF() {
    if (IFEnableLatch.isIFenable()) {
        if (IFEnableLatch.isIFbusy()) {
            return;
        }
        Simulator.getEventQueue().addEvent(
            new MemoryReadEvent(
                Clock.getCurrentTime() + Configuration.mainMemoryLatency,
                this,
                containingProcessor.getMainMemory(),
                containingProcessor.getRegisterFile().getProgramCounter()
            )
        );
        IFEnableLatch.setIFbusy(true);
    }
}
```
### Event Handling in MainMemory.java
The following code snippet shows `handleEvent()` in `MainMemory.java`, which processes a memory read request and generates a response:

```java
@Override
public void handleEvent(Event e) {
    if (e.getEventType() == EventType.MemoryRead) {
        MemoryReadEvent event = (MemoryReadEvent) e;
        Simulator.getEventQueue().addEvent(
            new MemoryResponseEvent(
                Clock.getCurrentTime(),
                this,
                event.getRequestingElement(),
                getWord(event.getAddressToReadFrom())
            )
        );
    }
}
```
## Functionalities Implemented
1. **Main Memory Latency:** Reflected main memory latency in both instruction fetches and load/store operations.
2. **Functional Unit Latencies:** Introducd latency for different functional units such as the ALU, multiplier, and divider. Implemented these delays by setting future event times for operations that involve these units.

## Testing
- Test the DES simulator with the same ToyRISC programs from the previous parts.
- Check that the results include the updated statistics, such as IPC and cycle count, reflecting the introduced latencies.
