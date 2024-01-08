// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen
// by writing 'black' in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen by writing
// 'white' in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// pseudo code
// LOOP
// 	IF KBD != 0:
// 		// black
// 		addr = SCREEN
//		i = 0
// 		LOOPA
// 		IF i < 8192:
// 			R[addr] = -1
// 			addr += 1
// 			i += 1
// 			GOTO LOOPA
// 	ELSE:
// 		// white
// 		addr = SCREEN
//		i = 0
// 		LOOPB
// 		IF i < 8192:
// 			R[addr] = 0
// 			addr += 1
// 			i += 1
// 			GOTO LOOPB
// 	GOTO LOOP

(LOOP)
	@KBD
	D=M
	@WHITE
	D;JEQ // if KBD == 0 goto WHITE

	// black
	@SCREEN
	D=A
	@addr
	M=D // addr=SCREEN
	@i
	M=0 // i = 0

	(LOOPA)
		@addr
		A=M
		M=-1 // R[addr] = 0
		@addr
		M=M+1 // addr += 1
		@i
		M=M+1 // i += 1

		@8192
		D=A
		@i
		D=D-M
		@LOOPA
		D;JGT // if i < 8192

	@LOOP
	0;JMP // goto LOOP

(WHITE)
	// white
	@SCREEN
	D=A
	@addr
	M=D // addr=SCREEN
	@i
	M=0 // i = 0
	(LOOPB)
		@addr
		A=M
		M=0 // R[addr] = 0
		@addr
		M=M+1 // addr += 1
		@i
		M=M+1 // i += 1

		@8192
		D=A
		@i
		D=D-M
		@LOOPB
		D;JGT // if i < 8192

	@LOOP
	0;JMP // goto LOOP