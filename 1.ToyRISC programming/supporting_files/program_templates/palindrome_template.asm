	.data
a:
	1212
	.text
main:
	load %x0, $a, %x3
	addi %x3, 0, %x6
	divi %x3, 10, %x20
	beq %x20, %x0, pal
loop:
	divi %x3, 10, %x4
	muli %x5, 10, %x5
	add %x31, %x5, %x5
	addi %x4, 0, %x3
	beq %x4, %x0, next
	jmp loop
next:
	beq %x5, %x6, pal
npal:
	subi %x10, 1, %x10
	end
pal:
	addi %x10, 1, %x10
	end