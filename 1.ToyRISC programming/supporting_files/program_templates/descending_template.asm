	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	load %x0, $n, %x20
	addi %x20, 0, %x9
loop0:
	addi %x20, 0, %x8
	subi %x9, 1, %x9
	beq %x9, 0, endl
loop:
	load %x5, $a, %x3
	addi %x5, 1, %x5
	load %x5, $a, %x4
	bgt %x4, %x3, swap
noswap:
	store %x3, $a, %x7
	addi %x7, 1, %x7
	store %x4, $a, %x7
	subi %x8, 1, %x8
	bgt %x8, %x0, loop0
	jmp loop
swap:
	addi %x4, 0, %x6
	addi %x3, 0, %x4
	store %x6, $a, %x7
	addi %x7, 1, %x7
	store %x4, $a, %x7
	subi %x8, 1, %x8
	bgt %x8, %x0, loop0
	jmp loop
endl:
	end