	.data
n:
	10
	.text
main:
	load %x0, $n, %x7
	addi %x3, 0, %x3
	addi %x4, 1, %x4
	addi %x6, 65535, %x6
loop:
	store %x3, $n, %x6
	add %x3, %x4, %x5
	addi %x4, 0, %x3
	addi %x5, 0, %x4
	subi %x7, 1, %x7
	subi %x6, 1, %x6
	bgt %x7, %x0, loop
	store %x3, $n, %x6
	end