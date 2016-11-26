# Your task in this problem is to run the greedy algorithm that schedules 
# jobs in decreasing order of the difference (weight - length). 
# Recall from lecture that this algorithm is not always optimal. 
# IMPORTANT: if two jobs have equal difference (weight - length), 
# you should schedule the job with higher weight first. 
# Beware: if you break ties in a different way, you are likely to get
# the wrong answer. 

# Your task now is to run the greedy algorithm that schedules jobs 
# (optimally) in decreasing order of the ratio (weight/length). 
# In this algorithm, it does not matter how you break ties. 
 
# You should report the sum of weighted completion times of the 
# resulting schedule --- a positive integer.


def sort_jobs(jobs, key):

	if key=='dif':
		return sorted(jobs, key=lambda j: (j[0]-j[1], j[0]), reverse=True)

	if key=='ratio':
		return sorted(jobs, key=lambda j: j[0]/float(j[1]), reverse=True)


def cal_cost(jobs, key):

	current_time = 0
	accu_cost = 0

	sorted_jobs = sort_jobs(jobs, key)

	for j in sorted_jobs:
		current_time += j[1]
		accu_cost += j[0] * current_time 

	return accu_cost


def main():

	jobs = []

	with open('jobs.txt', 'r') as f:
		next(f)
		for line in f:
			line = map(int,line.strip().split())
			jobs.append(line)


	print 'creat schedule with (weight-length) is :'
	print cal_cost(jobs, key='dif')


	print 'creat schedule with (weight/ratio) is :'
	print cal_cost(jobs, key='ratio')


if __name__ == '__main__':
	main()




