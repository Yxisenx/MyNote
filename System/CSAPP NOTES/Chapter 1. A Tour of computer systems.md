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
4. All information in a system—including disk files, programs stored in memory, user data stored in memory, and data transferred across a net work—is represented as bunch of bits. The only thing that distinguishes different data objects is the context in which we view them.







# 2. Programs Are Translated by Other Programs into Different Forms



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

