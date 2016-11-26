def knapsack(num_item, size, items):

	A = [[None for i in range(size + 1)] for j in range(num_item + 1)]
	#A = [[None] * (size+1)] * (num_item+1) is wrong
	#  AA[1][2] = 5
	# [[1, 1, 5, 1, 1, 1, 1],
	#  [1, 1, 5, 1, 1, 1, 1],
	#  [1, 1, 5, 1, 1, 1, 1],
	#  [1, 1, 5, 1, 1, 1, 1],
	#  [1, 1, 5, 1, 1, 1, 1]]
	# Because AA just have one row, other rows just a copy

	for i in range(size+1):
		A[0][i] = 0

	for i in range(1, num_item+1):
		for x in range(0, size+1):
			
			current_val, current_size = items[i]
			if x-current_size < 0:
				A[i][x] = A[i-1][x]
			else:
				A[i][x] = max(A[i-1][x], A[i-1][x-current_size] + current_val)

	return A[num_item][size]


def main():
	items = [[0,0]]
	with open('knapsack_big.txt') as f:
		size, num_item = map(int, f.next().strip().split())
		for line in f:
			items.append(map(int, line.strip().split()))

	return knapsack(num_item, size, items)


if __name__ == '__main__':
	print main()