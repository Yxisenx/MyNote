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







# 2. Programs Are Translated by Other Programs into Different Forms



1. In [computer science](https://en.wikipedia.org/wiki/Computer_science), a **high-level programming language** is a [programming language](https://en.wikipedia.org/wiki/Programming_language) with strong [abstraction](https://en.wikipedia.org/wiki/Abstraction_(computer_science)) from the details of the [computer](https://en.wikipedia.org/wiki/Computer). In contrast to [low-level programming languages](https://en.wikipedia.org/wiki/Low-level_programming_language), it may use [natural language](https://en.wikipedia.org/wiki/Natural_language) *elements*, be easier to use, or may automate (or even hide entirely) significant areas of computing systems (e.g. [memory management](https://en.wikipedia.org/wiki/Memory_management)), making the process of developing a program simpler and more understandable than when using a lower-level language. The amount of abstraction provided defines how "high-level" a programming language is.



```shell
linux> gcc -o hello hello.c
```

![image-20240202093852679](C:\Users\Administrator\Desktop\note\System\CSAPP NOTES\images\Chapter 1. A Tour of computer systems\the-compilation-system.png)

2. 



# 3. It Pays to Understand How Compilation Systems Work



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

