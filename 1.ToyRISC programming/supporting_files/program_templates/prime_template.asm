	.data
a:
	13
	.text
main:
	load %x0, $a, %x3
	subi %x3, 1, %x4
	addi %x0, 2, %x5
loop:
	div %x3, %x4, %x6
	beq %x31, %x0, notprime
	subi %x4, 1, %x4
	bgt %x4, %x5, loop
	addi %x10, 1, %x10
	end
notprime:
	subi %x10, 1, %x10
	end