# Hackaday U - Introduction to Reverse Engineering with Ghidra

This repository contains the exercises and resource materials for the Hackaday-U Course: Introduction to Reverse Engineering with Ghidra.

The purpose of this course is to provide an overview of how to use Ghidra and how to reverse engineer x86_64 ELF binaries for Linux. 

Links to the course materials, slides and video sessions can be found [here](https://hackaday.io/project/172292-introduction-to-reverse-engineering-with-ghidra)

# Dockerfile Instructions

This Docker container can be used to run all of the exercises included in the hackaday-u course.

* [Install Docker](https://docs.docker.com/get-docker/)

* Build the container:
    * ```build . -t hackaday-u```

* Run the container:
    * ```docker  run --rm -it hackaday-u /bin/bash``` 


# Session Two Exercises

These exercises are designed to illusrate the principals that are covered in the course materials, for session two we have the following exercises:

* control-flow-1
* loop-example-1
* variables-example
* func-example-1
* array-example

**Note** You will need to mark these binaries as executable before trying to run them, ex: ```chmod u+x control-flow-1```

Each of these exercises are designed to accept user input from the command line - when the proper input is provided you will get a Success or Congratulatory message.

For example, ```control-flow-1``` takes two values as an input, so you would run it as: ```./control-flow-1 value1 value2```

It is up to you to determine the format and number of these arguments for each exercise by reverse engineering them - if you get stuck please feel free to reach out on the [hackaday.io](https://hackaday.io/project/172292-introduction-to-reverse-engineering-with-ghidra) page, or reach out to me on [twitter](https://twitter.com/wrongbaud/).

# Session Three Exercises

These exercises are designed to illusrate the principals that are covered in the course materials, for session three we have the following exercises:

* structs
* pointers
* syscall
* files

**Note** You will need to mark these binaries as executable before trying to run them, ex: ```chmod u+x structs```

Each of these exercises are designed to accept user input from the command line - when the proper input is provided you will get a Success or Congratulatory message.

**Remember** you can provide non ascii input as follows:

```./pointers `python -c 'print "\xDE\xAD\xBE\xEF"'` ```

This example will provide the value 0xDEADBEEF into argv[1]

**Hint**: For the files exercise, you may need to edit binary files, to do this you can use any standard hex editor, below are some reccomendations:
* 010Editor
* HxD
* xxd

It is up to you to determine the format and number of these arguments for each exercise by reverse engineering them - if you get stuck please feel free to reach out on the [hackaday.io](https://hackaday.io/project/172292-introduction-to-reverse-engineering-with-ghidra) page, or reach out to me on [twitter](https://twitter.com/wrongbaud/).

### Modifications 

This is a modification of the general README and the ones per session. There is no README for session one which include the following files:
	
* c1
* c2
* c3
* c4
* nasm_crack
* SimpleKeyGen
* skele

All these are essentially different password checkers. The other files should be in either session two or three. 

The files here are the binaries used to dissemable and reverse. Source and original README can be found at the creators [github](https://hackaday.io/project/172292-introduction-to-reverse-engineering-with-ghidra)

