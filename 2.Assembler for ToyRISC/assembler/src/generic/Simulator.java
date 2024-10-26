package generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;


// import generic.Operand.OperandType;


public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	
	public static void assemble(String objectProgramFile)
	{
		//TODO your assembler code
		//1. open the objectProgramFile in binary mode
		try{
			FileOutputStream file = new FileOutputStream (objectProgramFile);
			//2. write the firstCodeAddress to the file
			int a = ParsedProgram.firstCodeAddress;
			String ans;
			HashMap<String, String> opcodeMap = new HashMap<>();
			opcodeMap.put("add", "00000");
			opcodeMap.put("addi", "00001");
			opcodeMap.put("sub", "00010");
			opcodeMap.put("subi", "00011");
			opcodeMap.put("mul", "00100");
			opcodeMap.put("muli", "00101");
			opcodeMap.put("div", "00110");
			opcodeMap.put("divi", "00111");
			opcodeMap.put("and", "01000");
			opcodeMap.put("andi", "01001");
			opcodeMap.put("or", "01010");
			opcodeMap.put("ori", "01011");
			opcodeMap.put("xor", "01100");
			opcodeMap.put("xori", "01101");
			opcodeMap.put("slt", "01110");
			opcodeMap.put("slti", "01111");
			opcodeMap.put("sll", "10000");
			opcodeMap.put("slli", "10001");
			opcodeMap.put("srl", "10010");
			opcodeMap.put("srli", "10011");
			opcodeMap.put("sra", "10100");
			opcodeMap.put("srai", "10101");
			opcodeMap.put("load", "10110");
			opcodeMap.put("store", "10111");
			opcodeMap.put("jmp", "11000");
			opcodeMap.put("beq", "11001");
			opcodeMap.put("bne", "11010");
			opcodeMap.put("blt", "11011");
			opcodeMap.put("bgt", "11100");
			opcodeMap.put("end", "11101");

			file.write(ByteBuffer.allocate(4).putInt(a).array());
		//3. write the data to the file
			for(int i = 0;i<ParsedProgram.data.toArray().length; i++)
			{
				int x = ParsedProgram.data.get(i);
				file.write(ByteBuffer.allocate(4).putInt(x).array());
			}
		//4. assemble one instruction at a time, and write to the file
		 for(int j=0 ; j<ParsedProgram.code.toArray().length; j++)
		 {
			ans = "";
			Instruction current = ParsedProgram.code.get(j);
			int pc =  current.getProgramCounter();
			if (current.getOperationType() == Instruction.OperationType.end) {
				ans += (String.format("%-32s", opcodeMap.get(current.operationType.toString())).replaceAll(" ", "0"));
				// System.out.println(ans);
				// continue;
			}
			else{
				ans += opcodeMap.get((current.operationType.toString()));
				// System.out.println("initial opcode : "+ans);
				int opcode = Integer.parseInt(ans,2);
				if(opcode<=20 && opcode%2 ==0) //check R3 Type
				{
					a = current.getSourceOperand1().getValue();
					ans += String.format("%5s", Integer.toBinaryString(a)).replaceAll(" ", "0");
					a = current.getSourceOperand2().getValue();
					ans += String.format("%5s", Integer.toBinaryString(a)).replaceAll(" ", "0");
					a = current.getDestinationOperand().getValue();
					ans += String.format("%5s", Integer.toBinaryString(a)).replaceAll(" ", "0");
					for(int i =0;i<=11;i++)
					ans+="0";
					// continue;
				}
				else if(opcode == 24)//check for jmp
				{
					ans+="00000";
					a = ParsedProgram.symtab.get(current.getDestinationOperand().getLabelValue())-pc;
					String offset = Integer.toBinaryString(a);
					// offset = String.format("%22s", offset).replaceAll(" ", "0");
					if (offset.length() > 22) {
						offset = offset.substring(offset.length() - 22);
					} else {
						offset = String.format("%22s", offset).replaceAll(" ", "0");
					}
					ans+= offset;
					// continue;
				}
				else //check R2I Type
				{
					if(opcode >= 25 && opcode <=28)
					{
						// System.out.println("opcode : " + ans);
						a = current.getSourceOperand1().getValue();
						// System.out.println("rs1 : "+a);
						ans += String.format("%5s", Integer.toBinaryString(a)).replaceAll(" ", "0");
						a = current.getSourceOperand2().getValue();
						// System.out.println("rd : " +a);
						ans += String.format("%5s", Integer.toBinaryString(a)).replaceAll(" ", "0");
						a = ParsedProgram.symtab.get(current.getDestinationOperand().getLabelValue())-pc;
						String offset = Integer.toBinaryString(a);
						if (offset.length() > 17) {
							offset = offset.substring(offset.length() - 17);
						} else {
							offset = String.format("%17s", offset).replaceAll(" ", "0");
						}
						// System.out.println("offset : "+offset);
						ans+= offset;
						// continue;
					}
					else{
						a = current.getSourceOperand1().getValue();
						ans += String.format("%5s", Integer.toBinaryString(a)).replaceAll(" ", "0");
						a = current.getDestinationOperand().getValue();
						ans += String.format("%5s", Integer.toBinaryString(a)).replaceAll(" ", "0");
						a = current.getSourceOperand2().getValue();
						ans += String.format("%17s", Integer.toBinaryString(a)).replaceAll(" ", "0");
						// continue;
					}
				}
		 	}
			int instInIntegerForm =(int) Long.parseLong(ans, 2);
			byte[] instInBinaryArray = ByteBuffer.allocate(4).putInt(instInIntegerForm).array();
			file.write(instInBinaryArray);
		 }
		//5. close the file
		file.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}