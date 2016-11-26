from time import time
from functools import reduce
from pdb import set_trace

def calc_median(lst):

	sorted_list = sorted(lst)

	k = len(lst)-1

	if k%2 == 1:
		return sorted_list[(k+1)//2]

	else : return sorted_list[k // 2]


def main():

	stream = []
	medians = []
	stream.append(0)

	# test = [83, 87, 23, 58, 18, 36, 63, 71, 33, 50, 82, 84, 70, 21, 65, 55, 28,
 #       90, 81, 65]
	# for i in test:
	# 	stream.append(i)
	# 	medians.append(calc_median(stream))

	# print test
	# print medians

	
	with open('Median.txt', 'r') as f:
		for line in f.readlines():
			num = int(line.strip())
			stream.append(num)
			medians.append(calc_median(stream))

	print 'sum median is:', sum(medians) # 46831213
	answer = reduce(lambda x,y: (x+y)%10000, medians)
	#answer = sum(medians) % 10000
	print 'medians calculate modules with 10000 is:', answer


if __name__ == '__main__':

	start = time()
	main()
	print time() - start

