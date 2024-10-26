	.data
a:
	12321
	.text
main:
	load %x0, $a, %x3
    addi %x3, 0, %x5
	divi %x3, 10, %x4
	beq %x4, %x0, pal
next:
	divi %x3, 10, %x3
    muli %x6, 10, %x6
    add %x6, %x31, %x6
    beq %x3, %x0, check
    jmp next
check:
	beq %x6, %x5, pal
npal:
	subi %x10, 1, %x10
	jmp endl
pal:
	addi %x10, 1, %x10
endl:
	end