import sys
from time import time
sys.setrecursionlimit(20000)

def knapsack(num_item, size, items):

	dp = {}

	def pack(nItem, limit):
		if (nItem, limit) not in dp:
			if nItem == 0:
				dp[(nItem, limit)] = 0
			elif items[nItem][1] > limit:
				dp[(nItem, limit)] = pack(nItem-1, limit)
			else:
				dp[(nItem, limit)] = max(pack(nItem-1, limit),
									pack(nItem-1, limit-items[nItem][1]) + items[nItem][0])

		return dp[(nItem, limit)]

	return pack(num_item, size)


def main():

	items = [[0, 0]]
	with open('knapsack1.txt') as f:
		size, num_item = map(int, f.next().strip().split())
		for line in f:
			items.append(map(int, line.strip().split()))

	start = time()
	max_value = knapsack(num_item, size, items)
	print 'the knapsack could get max value is ', max_value
	print 'use time is ', time() - start



if __name__ == '__main__':
	main()