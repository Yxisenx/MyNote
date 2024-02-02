[TOC]

# 1. Information Is Bits + Context

```c
# hello.c

#include <stdio.h>
int main()
{
    printf("hello, world\n");
    return 0;
}
```

![hello-c-ASCII-text-representation](C:\Users\Administrator\Desktop\note\System\CSAPP NOTES\images\Chapter 1. A Tour of computer systems\hello-c-ASCII-text-representation.png)

1. Program begins life as a *source program* (or *source file* ). **The source program is a sequence of bits, organized in 8-bit chunks called *bytes.***
2. Generally, modern computer systems represent text character using **ASCII** standard that represents each character with a unique byte-size integer value.
3. Files that consist exclusively of ASCII characters are known as *text files*. All other files are known as *binary files*.
4. All information in a system—including disk files, programs stored in memory, user data stored in memory, and data transferred across a net work—is represented as bunch of bits. The only thing that distinguishes different data objects is the ***context*** in which we view them.



1. 程序的生命周期是从一个源程序（或者说源文件）开始的。源程序实际上就是一个位（又称为比特）序列。8 个位被组织成一组，称为字节。
2. 通常来说，现代计算机系统使用ASCII标准表示文本字符，该标准使用唯一的字节大小整数值表示每个字符。
3. 只包含 ASCII 字符(或者其他字符集UTF-8等)的文件称为文本文件  。所有其他文件称为二进制文件  。
4. 系统中所有的信息——包括磁盘文件、内存中的程序、内存中存放的用户数据以及网络上传送的数据，都是由一串比特表示的。区分不同数据对象的唯一方法是我们读到这些数据对象时的上下文。



* Why is C so successful?

  - C was closely tied with the Unix operating system. Most of the Unix kernel and all of its supporting tools and libraries were written in C.

  - C is a small and simple language. The simplicity of C made it relatively easy to learn and to port to different computers.

  - C was designed for a proctical purpose.


* However, C also have weaknesses:

  - C pointers are a common source of confusion and programming errors.

  - C lacks explicit support for some abstractions, such as classes, objects and exceptions and exceptions.




# 2. Programs Are Translated by Other Programs into Different Forms



1. In [computer science](https://en.wikipedia.org/wiki/Computer_science), a **high-level programming language** is a [programming language](https://en.wikipedia.org/wiki/Programming_language) with strong [abstraction](https://en.wikipedia.org/wiki/Abstraction_(computer_science)) from the details of the [computer](https://en.wikipedia.org/wiki/Computer). In contrast to [low-level programming languages](https://en.wikipedia.org/wiki/Low-level_programming_language), it may use [natural language](https://en.wikipedia.org/wiki/Natural_language) *elements*, be easier to use, or may automate (or even hide entirely) significant areas of computing systems (`e.g`. [memory management](https://en.wikipedia.org/wiki/Memory_management)), making the process of developing a program simpler and more understandable than when using a lower-level language. The amount of abstraction provided defines how "high-level" a programming language is.



```shell
linux> gcc -o hello hello.c
```

![image-20240202093852679](C:\Users\Administrator\Desktop\note\System\CSAPP NOTES\images\Chapter 1. A Tour of computer systems\the-compilation-system.png)

2. A complier driver reads the program source and translates it into executable object file. The programs that perform the four phases(***preprocessor, compiler, assembler, and linker***) in sequence  are known collectively as the ***compilation system***.
   * **Preprocessing phase**: The preprocessor(`cpp`) transform the source program, which is in a text form, into a modified source program form according to directives begin with the `#` character. The result is typically ends with the `.i` suffix.
   * **Compilation phase**: The compiler(`cc1`) translates the `.i` file into a text file which contains an assembly-language program, which ends with the `.s` suffix.
   * **Assembly phase**: The assembler(`as`) translates the assembly-language program into machine-language instructions, and packages them in a the *relocatable object program*, the output file is ended with the `.o` suffix.
   * **Linking phase**: The linker(`ld`) handles a process which merges the source program (`.o` file) with other precompiled object file from other libraries. The result is the *executable object file*, which is ready to be loaded into memory and executed by the system.
   
   > <a href="https://en.wikipedia.org/wiki/Compiler" > Complier</a> also has some other phases.



1. 在计算机科学中，高级编程语言是一种与计算机硬件细节有很强抽象化的编程语言。与低级编程语言相对，高级编程语言可能借鉴自然语言的元素，它的使用更为方便，或者能够自动处理（甚至完全隐藏）计算系统的一些重要部分（如内存管理），这使得使用高级语言开发程序比使用低级语言更加简单，更易于理解。抽象的程度决定了一个编程语言的"高级"水平。
2. 编译器驱动读取程序源代码，并将其翻译成可执行的目标文件。按顺序执行四个阶段（预处理器、编译器、汇编器和链接器）的程序被统称为编译系统。
   * 预处理阶段：预处理器(`cpp`)将源程序（文本形式）根据以`#`字符开始的指令转换成修改过的源程序形式。结果通常以`.i`后缀结束。
   * 编译阶段：编译器(`cc1`)将`.i`文件翻译成包含汇编语言程序的文本文件，该文件以`.s`后缀结束。
   * 汇编阶段：汇编器(`as`)将汇编语言程序翻译成机器语言指令，并将它们打包在*可重定位的目标程序*中，输出文件以`.o`后缀结束。
   * 链接阶段：链接器(`ld`)处理一个过程，该过程将源程序（`.o`文件）与来自其他库的其他预编译目标文件合并。结果是*可执行的目标文件*，准备好被系统加载到内存并执行。

# 3. It Pays to Understand How Compilation Systems Work

Some important reasons why programmers need to understand how compilation systems work

* **Optimizing program performance**: With the basic understanding of machine-level code and how to compiler translates different C statements into machine code, we can make good coding decisions in our C programs. 
* **Understanding link-time errors**: Some of the most perplexing programming errors are related to the operation of the linker. 
* **Avoiding security holes**: With better understanding of how compilation system works, we may reduce the threat of our program being attacked.



程序员需要理解编译系统工作方式的一些重要原因包括：

- **优化程序性能**：通过基本理解机器级别的代码以及编译器如何将不同的C语句转换为机器代码，我们可以在编写C程序时做出更好的决策。
- **理解链接时的错误**：一些最难以理解的编程错误常常与链接器的操作有关。
- **防止安全漏洞**：对编译系统工作方式的深入理解可以帮助我们减少程序被攻击的风险。

# 4.  Processors Read and Interpret Instructions Stored in Memory



## 4.1 Hardware Organization of a System



## 4.2 Running the hello Program



# 5. Caches Matter



# 6. Storage Devices From a Hierarchy



# 7. The Operating System Manages the Hardware



## 7.1 Processes



## 7.2 Threads



## 7.3 Virtual Memory



## 7.4 Files



# 8. System Communicate With Other Systems Using Networks



# 9. Important Themes

## 9.1 Amdahl's Law



## 9.2 Concurrency and Parallelism



## 9.3 The Importance of Abstractions in Computer Systems



# 10. Summary

