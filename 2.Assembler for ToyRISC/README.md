# ToyRISC Assembler

This project is an assembler for the **ToyRISC ISA** that translates assembly programs into machine code, creating a binary object file. The assembler reads parsed input from an assembly program and generates the corresponding object file in binary format.

## Program Requirements

### Input
1. Full path to the assembly program.
2. Full path to the output object file to be created.

### Output
- Generates the object file at the specified location in binary format, containing:
  1. **Header:** The address of the first instruction (4 bytes).
  2. **Data:** All static data entries, each 4 bytes.
  3. **Text:** Encoded instructions, each 4 bytes.

## Assembly Process

Each instruction or operand in the assembly program is represented by objects that simplify translation:
- **Symbols:** Access the address of a label with `ParsedProgram.symtab.get(label_name)`.
- **Instructions:** Access a specific instruction using `ParsedProgram.getInstructionAt(addr)`.
- **Operands:** For each instruction, determine the operand type (register, immediate, or label) and its value.

### Example Encoding Process
Consider the instruction `load %x0, $a, %x4`. The assembler follows these steps:
- **Parse fields** from the instruction: operation (`load`), `rs1` (`x0`), `rd` (`x4`), `imm` (`0` for `$a`).
- **Generate binary** representation:
  - Operation `load`: `10110`
  - `rs1` (`x0`): `00000`
  - `rd` (`x4`): `00100`
  - `imm`: `00000000000000000`
- **Output encoded instruction** in 32-bit signed representation: `-1341652992`.

For a program like:
```assembly
.data
a
10
b
20
.text
load %x0, $a, %x4
end
```
The object file would contain:
1. `Header:` The address of the first instruction.
2. `Data Section:` Integers `10` and `20`.
3. `Text Section:` Encoded `load` and `end` instructions.

## Running the Assembler
#### 1. Compilation and JAR File Creation:
- Navigate to the folder containing `src` and `build.xml`.
- Run `ant` to compile the code.
- If there are no errors, create the JAR with:
```bash
ant make-jar
```
- This generates assembler.jar in the jars/ directory.

#### 2. Running the Assembler:
- Run the assembler with the command:

```bash
java -Xmx1g -jar <path-to-assembler.jar> <path-to-assembly-program> <path-to-object-file>
```
## Testing
1. **Test against Previous Programs:** Use the programs written in Assignment 1 as input to test the assembler.
2. **Testing the Zip Archive:** Verify the submission format using:
```bash
python test zip.py <path-to-zip-file>
```
## Troubleshooting
- **File Format:** Verify correct binary format of the object file with xxd.
- **Compilation Errors:** Ensure all files in src are included, and there are no syntax errors in the source code.