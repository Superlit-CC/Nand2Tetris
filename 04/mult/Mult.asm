// Desc. R[2] = R[0] * R[1]

// pseudo code
// LOOP
//  temp = R[1]
// 	IF temp > 0
// 		R[2] += R[0]
// 		temp -= 1
// 		GOTO LOOP
// 	ELSE
// 		GOTO STOP
// STOP

	@R1
	D=M
	@temp
	M=D // temp = R[1]

(LOOP)
	@temp
	D=M
	@END
	D;JLE // if temp <= 0 goto stop

	@R2
	D=M
	@R0
	D=D+M
	@R2
	M=D // R[2] += R[0]
	@temp
	M=M-1 // temp -= 1
	
	@LOOP
	0;JMP // goto loop

(END)
	@END
	0; JMP