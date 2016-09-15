import math
import copy
import random

import logging
import pdb

logging.basicConfig(level=logging.DEBUG)

def find_cut(A):

	if len(A) <=2: 
		#print 'A is :', A
		return A
	else:
		vecA = random.choice(A.keys())
		vecB = random.choice(A[vecA])

		
		#print 'vecA:', vecA, 'vecB:',vecB
		#print A 

		merge_lst = A[vecA] + A[vecB]

		A[vecA] = filter(lambda x: x != vecA and x != vecB, merge_lst)
		del A[vecB]

		for vs in A.values():
			for i, v in enumerate(vs):
				if v == vecB:
					vs[i] = vecA

		#print A
		#logging.debug('current A is: %s' % A)
		return find_cut(A)

def min_cut(A):

	trials = int(len(A)**2 * math.log(len(A)))
	min_cross = float('inf')
	cut = None

	for i in range(trials):
		graph = copy.deepcopy(A)
		trial = find_cut(graph)
		cut_edges = len(trial.values()[0])

		print 'current cut_edges :', cut_edges
		#print 'cut graph is :', trial
		if cut_edges < min_cross:
			min_cross = cut_edges
			cut = trial

	return cut, min_cross

def main():

	# A = {}
	# with open('test_1.txt', 'r') as f:
	# 	for line in f.readlines():
	# 		lst = map(int,line.strip().split())
	# 		A[lst[0]] = lst[1:]


	# cut, min_cross = min_cut(A)
	# print cut, min_cross

	A = {}
	with open('kargerMinCut.txt', 'r') as f:
		for line in f.readlines():
			lst = map(int, line.strip().split())
			A[lst[0]] = lst[1:]

	cut, min_cross = min_cut(A)

	print min_cross

if __name__ == '__main__':

	main()


