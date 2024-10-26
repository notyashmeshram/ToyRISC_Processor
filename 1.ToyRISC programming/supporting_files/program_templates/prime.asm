	.data
a:
	39
	.text
main:
	load %x0, $a, %x3
	load %x0, $a, %x4
	addi %x0, 3, %x7
	addi %x0, 2, %x8
loop1:
	subi %x4, 1, %x4
	div %x3, %x4, %x5
	beq %x31, %x0, notprime
	bgt %x4, %x8, loop1
endl:
	addi %x10, 1, %x10
	end
notprime:
	subi %x10, 1, %x10
	end