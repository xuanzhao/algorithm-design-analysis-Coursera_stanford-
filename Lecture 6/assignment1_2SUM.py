# The file contains 1 million integers, both positive and negative 
# (there might be some repetitions!).This is your array of integers, 
# with the ith row of the file specifying the ith entry of the array.

# Your task is to compute the number of target values t in the interval 
# [-10000,10000] (inclusive) such that there are distinct numbers x,y in 
# the input file that satisfy x+y=t. (NOTE: ensuring distinctness requires 
# a one-line addition to the algorithm from lecture.)

#Write your numeric answer (an integer between 0 and 20001) in the space provided.

import sys
import threading
import pdb
from time import time

def cal_2SUM_hash(array, interval):

	def cal_2SUM(dic, array, t):
		for a in array:
			b = t - a
			if b in dic and a != b:
				return 1
		return 0

	dic = set(array)
	sum_count = 0
	for t in interval:
		if cal_2SUM(dic, array, t):
			sum_count += 1
			print 'current count:', sum_count
	return sum_count


def binearySearch(lst, x):
	
	def loop(lst, l, r, x):

		if l > r: return None
		mid = (l + r) //2
		# mid = l + (r-l)>>1

		if x == lst[mid]: return mid
		elif x < lst[mid]:
			return loop(lst, l, mid-1, x)
		else:
			return loop(lst, mid+1, r, x)

	#pdb.set_trace()
	idx = loop(lst, 0, len(lst)-1, x)
	return idx



def cal_2SUM_biSearch(sorted_array, interval):

	count = 0
	for t in interval:
		for a in sorted_array:
			b = t - a
			i = binearySearch(sorted_array, b)
			if i and a != b:
				count += 1
				print (a,b,t)
				print 'current count:', count
				break

	return count



def main():

	# array = []
	# with open('algo1-programming_prob-2sum.txt', 'r') as f:
	# 	for line in f.readlines():
	# 		array.append(int(line))

	with open('algo1-programming_prob-2sum.txt', 'r') as f:
		lines = f.read().splitlines()
	
	array = map(int, lines)
	sorted_array = sorted(array)
	interval_hash = range(-10000, 10000+1)
	interval_bisec = range(2500, 4000+1)
	
	start_hash = time()
	count = cal_2SUM_hash(array, interval_hash)
	print 'The number of t = a + b in [-10000,10000] is :', count
	print 'hash method cost time :', time()-start_hash

	start_bisearch = time()
	count = cal_2SUM_biSearch(sorted_array, interval_bisec)
	print 'The number of t = a + b in [-10000, 10000] is :', count
	print 'binearySearch method cost time :', time() - start_bisearch

	# test = [1,2,3,4,5,6,7,8,9,10]
	# count = cal_2SUM_hash(test, [6])
	# print count
	# count = cal_2SUM_biSearch(test, [6])
	# print count

if __name__ == '__main__':
    threading.stack_size(67108864) # 64MB stack
    sys.setrecursionlimit(2 ** 20) # approx 1 million recursions
    thread = threading.Thread(target = main) # instantiate thread object
    thread.start() # run program at target
    #main()
