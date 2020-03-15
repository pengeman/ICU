# -- coding: UTF-8 --
from __future__ import print_function
import random


# 十以内减法
def print10():
    for i in range(0, 1000):
        a = random.randint(3, 10)
        b = random.randint(1, a)
        print(a, '-', b, '=   ')


# 二十以内减法
def print20():
    for i in range(0, 1020):
        a = random.randint(10, 20)
        b = random.randint(1, 10)
        print(a, '-', b, '=   ')


# 二十以内减法 10以上
def print20b():
    for i in range(0, 1020):
        a = random.randint(10, 20)
        b = random.randint(1, a)
        print(a, '-', b, '=   ')


def getTyp():
    # st =['++','--','+-','-+']
    return random.randint(0, 3)


##=['++' ]
def calc0():
    a = random.randint(0, 18)
    b = random.randint(1, 19 - a)
    c = random.randint(1, 20 - a - b)
    rst = str(a) + '+' + str(b) + '+' + str(c) + ' ='
    return rst


##=[ '--' ]
def calc1():
    a = random.randint(10, 20)
    b = random.randint(0, a - 1)
    c = random.randint(1, a - b)
    rst = str(a) + '-' + str(b) + '-' + str(c) + ' ='
    return rst


##=[ '+-']
def calc2():
    a = random.randint(0, 19)
    b = random.randint(1, 20 - a)
    c = random.randint(0, a + b)
    rst = str(a) + '+' + str(b) + '-' + str(c) + ' ='
    return rst


##=[ '-+']
def calc3():
    a = random.randint(10, 20)
    b = random.randint(1, a - 1)
    c = random.randint(1, 20 - (a - b))
    rst = str(a) + '-' + str(b) + '+' + str(c) + ' ='
    return rst


# 二十以内混合运算
def print20mix():
    for i in range(0, 1000):
        calctyp = getTyp()
        if calctyp == 0:
            print(calc0())
        elif calctyp == 1:
            print(calc1())
        elif calctyp == 2:
            print(calc2())
        elif calctyp == 3:
            print(calc3())


def get20mix():
    res = []
    for i in range(0, 1000):
        calctyp = getTyp()
        n = eval("calc" + str(calctyp) + "()")
        res.append(n)
    return res


## 20以内混合运算

if __name__ == '__main__':
    res = get20mix()
    book = []
    for i in range(0, len(res)):
        if (i % 100 == 0):
            book.append([])  # new page
            print("\n")
            print("page " + str(len(book)))

        if (i % 4 == 0):
            book[-1].append([])  # new row
            print()
            print("row " + str(len(book[-1])),end=" || ")

        book[-1][-1].append(res[i])
        print(res[i], end=" | ")

    # print(book)
