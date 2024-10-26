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
	addi %x0, 1, %x12
	load %x0, $n, %x8
main1:
	load %x0, $a, %x3
	addi %x0, 0, %x6
	addi %x0, 0, %x10
	add %x0, %x8, %x9
	subi %x8, 1, %x8
	beq %x8, %x0, endl
loop1:
	addi %x6, 1, %x6
	load %x6, $a, %x4
	bgt %x4, %x3, swap
	jmp noswap
swap:
	addi %x4, 0, %x5
	addi %x3, 0, %x4
	addi %x5, 0, %x3
	store %x3, $a, %x10
	addi %x10, 1, %x10
	store %x4, $a, %x10
	addi %x4, 0, %x3
	subi %x9, 1, %x9
	beq %x9, %x12, main1
	jmp loop1
noswap:
	store %x3, $a, %x10
	addi %x10, 1, %x10
	store %x4, $a, %x10
	addi %x4, 0, %x3
	subi %x9, 1, %x9
	beq %x9, %x12, main1
	jmp loop1
endl:
	end