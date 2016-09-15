from copy import copy
import random
import logging

logging.basicConfig(level=logging.INFO)


def partition(A, l, r, p):

	pivot = A[p]
	if not p is l: A[l], A[p] = A[p], A[l]

	i = l+1
	for j in range(l+1, r+1):
		if A[j] < pivot:
			A[j], A[i] = A[i], A[j]
			i += 1
	
	A[l], A[i-1] = A[i-1], A[l]
	
	return i-1, r-l

def quickSort_l(A):

	def quick_helper(A, l, r):
		if l >= r: return

		pivot, ct = partition(A, l, r, l)
		sum_ct.append(ct)
		quick_helper(A, l, pivot-1)
		quick_helper(A, pivot+1, r)
	
	sum_ct = []
	quick_helper(A, 0, len(A)-1)

	return A, sum(sum_ct)

def quickSort_r(A):

	# def loop(A, l, r):
	# 	if (l < r):
	# 		p, ct = partition(A, l, r, r)
	# 		sum_ct.append(ct)
	# 		loop(A, l, p-1)
	# 		loop(A, p+1, r)
	
	# sum_ct = []
	# loop(A, 0, len(A)-1)
	# return A, sum(sum_ct)
	
	def quick_helper(A, l, r):
		if l>= r: return

		pivot,ct = partition(A, l, r, r)
		sum_ct.append(ct)
		quick_helper(A, l, pivot-1)
		quick_helper(A, pivot+1, r)

	sum_ct = []
	quick_helper(A, 0, len(A)-1)

	return A, sum(sum_ct)


def quickSort_m(A):
	
	def find_median(A, l, r, m):
		lv = A[l]
		rv = A[r]
		mv = A[m]

		if (lv > mv and lv < rv) or (lv < mv and lv > rv): return l
		if (mv > lv and mv < rv) or (mv < lv and mv > rv): return m
		if (rv > lv and rv < mv) or (rv < lv and rv > mv): return r
		return r

	def quick_helper(A, l, r):
		if l >= r: return

		m = find_median(A, l, r, (l+r) / 2)
		pivot, ct = partition(A, l, r, m)
		sum_ct.append(ct)
		quick_helper(A, l, pivot-1)
		quick_helper(A, pivot+1, r)

	sum_ct = []
	quick_helper(A, 0, len(A)-1)	
	return A, sum(sum_ct)

def main():

	quick_fn = [quickSort_l, quickSort_r, quickSort_m]
	for fn in quick_fn:
		un_sorted = [3, 8, 2, 5, 1, 4, 7, 6]
		has_sorted = copy(un_sorted)
		has_sorted, comparisions = fn(has_sorted)
		if has_sorted != sorted(un_sorted):
			print '%s has fail to sort %s' % (fn.func_name, has_sorted)
		print fn.func_name, 'comparisions :', comparisions


	lst = []
	with open('QuickSort.txt', 'r') as f:
		for i in f.readlines():
			lst.append(int(i))

	quick_fn = [quickSort_l, quickSort_r, quickSort_m]
	for fn in quick_fn:
		has_sorted = copy(lst)
		has_sorted, comparisions = fn(has_sorted)
		if has_sorted != sorted(lst):
			print '%s has fail to sort list' % fn.func_name
		print fn.func_name, 'comparisions :', comparisions



if __name__ == '__main__':
	main()



