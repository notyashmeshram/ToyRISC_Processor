	.data
a:
	10
	.text
main:
	load %x0, $a, %x3
	divi %x3, 2, %x4
	addi %x0, 1, %x5
	beq %x31, %x5, odd
	subi %x10, 1, %x10
	jmp end
odd:
	addi %x10, 1, %x10
	end
end:
	end