import random
import copy
import time
import logging
import sys

logging.basicConfig(level=logging.DEBUG)
#logging.basicConfig(level=logging.INFO)
random.seed(int(time.time()))

def remove_duplicated_edge(A):
    B = copy.copy(A)
    for v in A:
        for adjacency in A[v]:
            if v in A[adjacency]: A[adjacency].remove(v)
                #  print "Remove (%d, %d)" % (adjacency, v)
    return A



def main():
	A = {}
	with open('test_1.txt', 'r') as f:
		for line in f.readlines():
			l = line.replace('\n','').replace('\r', '').split()
			v = int(l[0])
			A[v] = [int(i) for i in l[1:] if i.isdigit()]


	vextice_len = len(A)
	logging.debug('=' * 80)
	logging.debug('Orginaly: %s', str(A))

	remove_duplicated_edge(A)
	print "after remove_duplicated_edge, A has " + str(len(A)) + "vertex."

if __name__ == '__main__':
	main()

# def main():

# 	dic = {}

# 	with open('test_1.txt', 'r') as f:
# 		for line in f.readlines():
# 			l = line.replace('\n','').replace('\r', '').split()
# 			v = int(l[0])
# 			dic[v] = [int(i) for i in l[1:] if i.isdigit()]

