def sort_and_count(array):
	if len(array) == 1: return (array, 0)
	else:
		mid = len(array) / 2
		(B, X) = sort_and_count(array[:mid])
		(C, Y) = sort_and_count(array[mid:])
		(D, Z) = CountSplitInv(B, C)
	
		return (D ,X + Y + Z)




def CountSplitInv(left, right):
	D = []
	i, j = 0, 0
	count = 0

	while i < len(left) and j < len(right):
		if left[i] <= right[j]: 
			key = left[i]
			i += 1
		else:
			key = right[j]
			j += 1
			count += (len(left) - i)
		D.append(key)

	D += left[i:]
	D += right[j:]

	return D, count



if __name__ == "__main__":
	import numpy as np
	nums = np.loadtxt('./IntegerArray.txt',  dtype=int)
	nums = nums.tolist()
	print 'sum_inversion_number is ',sort_and_count(nums)[1]