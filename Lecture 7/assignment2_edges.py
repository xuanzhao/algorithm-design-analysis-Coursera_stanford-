# You should NOT assume that edge costs are positive, nor should you assume that 
# they are distinct.
 
# Your task is to run Prim's minimum spanning tree algorithm on this graph. 
# You should report the overall cost of a minimum spanning tree 
# --- an integer, which may or may not be negative.
 
 
# The simpler approach, which should already give you a healthy speed-up, 
# is to maintain relevant edges in a heap (with keys = edge costs). 
# The superior approach stores the unprocessed vertices in the heap, 
# as described in lecture. Note this requires a heap that supports deletions, 
# and you'll probably need to maintain some kind of mapping between vertices 
# and their positions in the heap.



import pdb
import heapq
from random import choice
from collections import defaultdict
from time import time


def Prim_MST(edges):

	def update_heap(idx, val):
		min_heap[idx] = val
		
		if idx % 2==0:
			parent = idx // 2 -1
		else : parent = idx // 2

		while idx > 1 and min_heap[parent] > min_heap[idx]:
			min_heap[idx], min_heap[parent] = min_heap[parent], min_heap[idx]
			idx = parent
			if idx % 2 == 0:
				parent = idx // 2 -1
			else: parent = idx // 2


	V = set(edges)
	s = V.pop()
	S = set([s])

	min_heap = []
	V_to_S = {}
	cost = []

	for v in V:
		adj_v = edges[v]
		to_S = filter(lambda v: v[0] in S, adj_v)
		if to_S:
			min_w = min(to_S, key=lambda v: v[1])
			min_heap.append((min_w[1], v))
			V_to_S[v] = min_w[1]
		else:
			min_heap.append((float('inf'), v))
			V_to_S[v] = float('inf')

	heapq.heapify(min_heap)

	#pdb.set_trace()
	while V:

		(c, v) = heapq.heappop(min_heap)  # v = (weight, v)
		cost.append(c)
		S.add(v)
		V.remove(v)

		adj_v = edges[v]
		for u, w in adj_v:
			if u in V and w < V_to_S[u]:
				idx = min_heap.index((V_to_S[u], u))
				val = (w, u)
				update_heap(idx, val)
				# min_heap.remove((V_to_S[u], u))
				# heapq.heappush(min_heap, (w, u))
				# heapq.heapify(min_heap)   # Cost lots of time to fix this bug, because the heapq is not heap order any more.
				V_to_S[u] = w
	#pdb.set_trace()
	return cost, sum(cost)


def main():

	edges = defaultdict(list)
	with open('edges.txt', 'r') as f:
		n, m = map(int,f.next().strip().split())
		for line in f:
			line = map(int, line.strip().split())
			head, tail, weight = line[0], line[1], line[2]
			edges[head].append((tail, weight))
			edges[tail].append((head, weight))


	print 'Prim MST get cost is:'
	print Prim_MST(edges)


if __name__ == '__main__':
	start = time()
	main()
	print time()-start
