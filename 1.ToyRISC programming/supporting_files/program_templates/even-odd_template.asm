	.data
a:
	11
	.text
main:
	load %x0, $a, %x3
	divi %x3, 2, %x4
	addi %x0, 1, %x5
	beq %x31, %x5, isodd
	subi %x10, 1, %x10
	end
isodd:
	addi %x10, 1, %x10
	end