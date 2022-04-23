The system generates numbers of the form $2^k \cdot 3^m \cdot 5^n$ in ascending order. It has 4 kinds of threads:
* **multiply** - multiplies input by number: 2, 3, 5
* **merge** - sorts the input
* **copy** - copies the input
* **print** - prints the input

![](img/diagram.png)

In the initial state, copy thread has a single input 1. 

# Implementation details of concurrency

1. Copy task is not allowed to start before merge task is complete.
2. Multiply tasks are not allowed to start before merge task is complete and not allowed to finish before copy task is complete.
3. Merge task is not allowed to start before all multiply tasks are complete.

# Output

```txt
1 2 3 4 5 6 8 9 10 12 15 16 18 20 24 25 27 30 32 36 40 45 48 50 54 60 64 72 75 80 81 90 96 100
108 120 125 128 135 144 150 160 162 180 192 200 216 225 240 243 250 256 270 288 300 320 324
360 375 384 400 405 432 450 480 486 500 512 540 576 600 625 640 648 675 720 729 750 768 800
810 864 900 960 972 1000 1024 ...
```
