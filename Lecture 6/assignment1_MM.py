# -*- coding: UTF-8 -*-

# The goal of this problem is to implement the "Median Maintenance" algorithm 
# (covered in the Week 5 lecture on heap applications). The text file contains 
# a list of the integers from 1 to 10000 in unsorted order; you should treat 
# this as a stream of numbers, arriving one by one. Letting xi denote the ith 
# number of the file, the kth median mk is defined as the median of the numbers 
# x1,…,xk. (So, if k is odd, then mk is ((k+1)/2)th smallest number among x1,…,xk; 
# if k is even, then mk is the (k/2)th smallest number among x1,…,xk.)

# In the box below you should type the sum of these 10000 medians, modulo 10000 
# (i.e., only the last 4 digits). That is, you should compute 
# (m1+m2+m3+⋯+m10000)mod10000.


# def heap_max(lst):

# 	def heapify(lst):

# 	def pop_max(lst):


from time import time
from heapq import heapify, heappop, heappush
from pdb import set_trace

heap_min = []
heap_max = []


def cal_median(stream):

	global heap_min, heap_max

	k = len(stream)
	if k == 1: 
		heappush(heap_max, -stream[0])
		return -heap_max[0]

	mid = -heap_max[0]

	if mid < stream[-1]:
		heappush(heap_min, stream[-1])
		if len(heap_min) > len(heap_max):
			elem = heappop(heap_min)
			heappush(heap_max, -elem)
	
	else:
		heappush(heap_max, -stream[-1])
		if len(heap_max) > len(heap_min) + 1:
			elem = -heappop(heap_max)
			heappush(heap_min, elem)

	return -heap_max[0]


def main():

	stream = []
	medians = []

	# test = [83, 87, 23, 58, 18, 36, 63, 71, 33, 50, 82, 84, 70, 21, 65, 55, 28,
 #       90, 81, 65]
	# for i in test:
	# 	stream.append(i)
	# 	medians.append(cal_median(stream))

	# print test
	# print medians

	
	with open('Median.txt', 'r') as f:
		for line in f:
			num = int(line.strip())
			stream.append(num)
			medians.append(cal_median(stream))

	print 'the sum of medians is :', sum(medians)
	answer = sum(medians) % 10000
	print 'the sum(medians) %10000 is :', answer

if __name__ == '__main__':
	start = time()
	main()
	print time() - start