	.data
n:
	10
	.text
main:
	load %x0, $n, %x3
	addi %x0, 0, %x4
	addi %x0, 1, %x5
	addi %x0, 65535, %x6
	store %x4, $n, %x6
	subi %x6, 1, %x6
	store %x5, $n, %x6
loop:
	subi %x6, 1, %x6
	subi %x3, 1, %x3
	add %x4, %x5, %x7
	addi %x5, 0, %x4
	addi %x7, 0, %x5
	store %x7, $n, %x6
	bgt %x3, %x0, loop
	end