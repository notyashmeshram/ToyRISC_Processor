# ToyRISC ISA Assembly Programs

This repository contains five assembly programs written in ToyRISC ISA for various tasks. Each program is saved in its own `.asm` file with specific functionality and output requirements.

## Programs

### 1. Even or Odd Check
- **File:** `even-odd.asm`
- **Description:** Checks if a given number is even or odd.
- **Output:** 
  - Stores `1` in register `x10` if the number is odd.
  - Stores `-1` in register `x10` if the number is even.

### 2. Descending Sort
- **File:** `descending.asm`
- **Description:** Sorts an array of numbers in descending order in-place.

### 3. Fibonacci Sequence
- **File:** `fibonacci.asm`
- **Description:** Places the first `n` Fibonacci numbers in memory, starting from address `2^16 - 1` and decrementing for each subsequent number.

### 4. Palindrome Check
- **File:** `palindrome.asm`
- **Description:** Checks if a given number is a palindrome.
- **Output:** 
  - Stores `1` in register `x10` if the number is a palindrome.
  - Stores `-1` in register `x10` if it is not.

### 5. Prime Check
- **File:** `prime.asm`
- **Description:** Checks if a given number is prime.
- **Output:** 
  - Stores `1` in register `x10` if the number is prime.
  - Stores `-1` in register `x10` if it is not.

## Testing Instructions

1. **Run Each Program:**
   - Use `emulator.jar` to test each `.asm` file.
   - Command: 
     ```bash
     java -jar <path-to-emulator.jar> <path-to-assembly-file> <starting-address> <ending-address>
     ```
   - Verify output matches the specifications above.

2. **Automated Testing:**
   - Use `python test each.py <path-to-assembly-file>` to test each file.
   - Ensure all tests pass to confirm correct functionality.

### Troubleshooting Tips
- Follow ToyRISC syntax and remove any empty lines in `.asm` files.
- Confirm all automated tests pass to ensure logical accuracy.

---


