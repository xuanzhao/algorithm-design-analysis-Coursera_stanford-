import bisect
from time import time



def two_sum(array, interval):
    """Returns the numbers from [-WIDTH, WIDTH] that can be obtained by
    summing up any two elements in 'array'."""

    result = set()
    low_b, high_b = interval[0], interval[1]

    for a in array:
        lower = bisect.bisect_left(array, low_b - a)
        upper = bisect.bisect_right(array, high_b - a)

        result |= set([a+b for b in array[lower:upper]])

    return result


def main():

    array = []
    with open('algo1-programming_prob-2sum.txt') as f:
        for line in f:
            num = int(line.strip())
            array.append(num)
    array.sort()

    interval = [-10000, 10000]
    return len(two_sum(array, interval))


if __name__ == '__main__':

    start = time()
    print main()
    print time() - start