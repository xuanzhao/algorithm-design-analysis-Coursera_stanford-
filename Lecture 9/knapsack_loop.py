from time import time
import numpy as np


def knapsack(num_item, size, items):

	A = np.zeros((num_item+1, size+1), dtype=int)

	A[:, 0] = 0

	for i in range(1, num_item+1):
		for x in range(0, size+1):

			current_val, current_size = items[i]
			if x-current_size < 0:
				A[i, x] = A[i-1, x]
			else:
				A[i, x] = max(A[i-1, x], A[i-1, x-current_size] + current_val)

	return A[num_item, size]



def main():

	items = [[0, 0]]
	with open('knapsack1.txt') as f:
		size, num_item = map(int, f.next().strip().split())
		for line in f:
			items.append(map(int, line.strip().split()))

	start = time()
	print knapsack(num_item, size, items)
	print time() - start



if __name__ == '__main__':
	main()