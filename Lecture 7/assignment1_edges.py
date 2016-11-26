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


from time import time
import pdb

def prim_MST(edges):

	def extract_min(V):
		#pdb.set_trace()
		assert S , 's is empty'
		candis1 = filter(lambda e: e[0] in S and e[1] in V, edges)
		candis2 = filter(lambda e: e[1] in S and e[0] in V, edges)
		candis = candis1 + candis2

		return min(candis, key=lambda e: e[2])

	# initial_MST
	V = {v for edge in edges for v in edge[:2]}
	S = set([V.pop()])
	cost = []

	while V:

		u,v,w = extract_min(V)

		if u in S:
			S.add(v)
			V.remove(v)
		elif v in S:
			S.add(u)
			V.remove(u)

		cost.append(w)

	return cost, sum(cost)

def main():

	edges = []
	with open('edges.txt', 'r') as f:
		n, m = map(int,f.next().strip().split())
		for line in f:
			line = map(int, line.strip().split())
			edges.append(line)

	print 'Prim MST get cost is:'
	print prim_MST(edges)


if __name__ == '__main__':
	start = time()
	main()
	print time()-start
