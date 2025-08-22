# Solutions to each exercise
even if trivial, this will serve a proof of memory 
also should show thought process for clarity (and to reread?) 

## E: C1
- Purpose: looks for a password
- Solution: its a 'strncmp' with a hardcoded string for the password
    - if the cond `var1 == 0` then the code passes
    - `var1` is a strncmp with a hardcoded string 
    - the correct string is 'hackadayu'

- ~~Can't test solution on windows because I don't know what the file type is...~~
    - but I know the solution is correct
    - runs on my debian laptop, can confirm is correct

## E: C2
- Purpose: another password checker... has more requirements
- Solution: String has to be longer than 5 characters, first char must be 'h' and fourth char must be 'u'
    - my chosen solution was 'h...u..'

## E: C3 
- Purpose: another string checker, different logic 
- Solution: String has to be longer than 5 characters, the characters in the third position minus the fourth position must equal 0x20 (32)
    - used '..R2......'

## E: C4
- Purpose: checks string with some heavier logic
- Solution: String has to be >= 10, string manipulation of the hardcoded 'hackaday-u', ascii cypher, gets letter from ith position then adds 2 to its ascii value and thats the string
    - answer: 'jcemcfc{/w'

- different idea would be to patch the file such that it jumps into the solution but thats not for this ig 

## E: nasm_crack
- Purpose: another password check 
- Solution: look in data header to see stored values, password was stored (partially?) and used that as input 
     - answer: supersecret

## E: SimpleKeyGen
- Purpose: reverse serial function, input correct password for correct serial 
- Solution: look into the 'CheckSerial' function and reverse it
    - answer: abcdefghijklmnop

## E: skele 
- Purpose: password checker with bitwise logic 
- Solution: rewrote the bitwise logic to reverse answer
    - answer: {bogiya}

## E: control-flow-1
- Questions from slides: 
    - compare statements? ifs: 4 cmp: ? 
    - values?  2nd input < first input | 2nd input << 1 < first input | (2nd input + first input) - first input < 100 
    - success?
- Solution: do the math
    - answer:  101, 100
    - solved via brute force and slight pattern matching, unsure what the actual math is
        - fixed, the goal wasn't to satisfy all ifs but to see which needed to be true and others false 

## E: loop-example-1
- Questions from slides:
    - number of times loop is run: 0 < 15... 15? 
    - what is the loop doing: adding a counter that needs to be 8
    - success?
- Solution: String needs to be 15 chars with 8 chars between '@' and '['
    - answer: "AAAAAAAA}}}}}}}"

## E: variables-example
- Questions:
    - number of globals? 2 
    - number of locals? 3
    - success? Failed, unable to reverse the Xor, however I could brute force all possible combination of bytes
- Solution: a string that is >= 8 with specific chars for the first 8 chars
    - answer: 
    - POST Source: For the most part, did understand most of the logic, even the XorMe hex number, problem was with implementation with solver that I wrote 

## E: func-example-1
- Questions:
    - functions discovered? 3 
    - locals vars in each function: various
    - take args? yes 
- Solution: numbers work apparently, unsure of the specifics.. but calloc with (long)(int) casts are a clue
    - another being the different domains for the local counters
	- answer: 123

## E: heap-example-1
- Questions:
    - mem alloc? 12 char bytes (12 1 byte) 
    - difference? its there
- Solution: string of length 12 of captial letters only 
    - answer:  AOAOAOAOAOAO

## E: array-example
- Question:
    - array count? (scope) some global array of pointers? its an array of pointers that point to strings
    - succes?
- Solution: index 1-5 where passcode is something, brute forced 1, coded the solution for 0,2-4. lol
    - 0: gbhjccxdm 
    - 1: dincvqmn
    - 2: mqqmanm 
    - 3: igbei`miegb
    - 4: aaenqf

## E: structs 
- Question:
    - members in struct? 4 or 5?
    - members reprentation?  stuff
    - recreate the struct? no, used solution + source
- Solution: key != 0, 7 < username,password < 598,
    - things to note / things I learned while reading solution:
        - can edit function signatures to get better viewing of functions
        - structs can have unused fields and you have to accomodate that via alignments
        - thats why the "int var [2]" was so weird, because it wasn't an arr but a struct (obv from exercise) 
    - answer: 1 abcdegfh MPORQSTV 

## E: syscall 
- Question:
    - num of syscall and what? 4 syscalls and 1 'fileDescriptor' 
    - purpose? seems to open file and want to write some stuff 
    - entry point? at _start or entry 
- Solution: not a password checker so ig I pass or something

## E: files
- Question:
    - difference? from what? a lot of new things ig
    - ops? quite the number of ops, thers also 'read' and 'open' ops 
    - success? 
- Solution: this is out of my will of interest, this would take forever to read and solve,
    - files: uname.x | key.y | pword.z 
    - bytes: 8       | 4     | 8
    - answer: [ "nnnnnnnn" | 2001 | "%%%%%%%%" ] on Windows
    - linux: []
    - both this and 'pointers' have memory things that I am currently unable to do in Rust...
        - Rust's safetly really makes the conversions a bit more annoying, anyhow 
        - 'Read' in C has no conversion, reads raw bytes
        - linux v windows apparently has different internal systems (duh) so my solution on Windows does not work on my debian device 
        - a recompiled version of source on Windows works with [ "nnnnnnnn" | 2001 | "%%%%%%%%" ] following the format of the files above. 
        - patching the file to print both locals show the same string, idk what solves it... maybe ill write a manual breaker later

## E: pointers 
- Question: 
    - members in struct? 5 
    - members representation? ints and chars* 
    - num of pointers? 2?
- Solution: Key != 0, 7 < name,pass < 598, unable to write a solver in Rust so...  

## E: P-code
- Question:
    - how are they different | are they similar 
- Solution: maybe I've skimmed this who knows, it builds general knowledge and exposure to possible patterns
