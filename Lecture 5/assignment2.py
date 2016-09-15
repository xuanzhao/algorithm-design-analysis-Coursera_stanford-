from collections import defaultdict
from functools import reduce

import pdb

from heapq import heappop, heappush




def dijkstra(G, source=1):
	
	def relax(u, v, weight):
		if distance_to_source[v] > distance_to_source[u] + weight:
			# if v in [h[1] for h in heap]:
			# 	heap.remove((distance_to_source[v], v))
			distance_to_source[v] = distance_to_source[u] + weight
			heappush(heap, (distance_to_source[v], v))
			# print 'distance_to_source is ', distance_to_source
			# print 'after relax ,the heap is', heap
			prev_path[v] = u

	# initial
	prev_path = defaultdict(lambda: 'NAN')
	heap = [] 
	heappush(heap, (0, source))

	distance_to_source = defaultdict(lambda: float('inf'))
	distance_to_source[source] = 0

	#pdb.set_trace()
	while heap:

		u = heap.pop()[1]

		for vw in G[u]:
			v, w = vw[0], vw[1]
			relax(u, v, w)

	return prev_path, distance_to_source

def readfile(f='dijkstraData.txt'):

	graph = {}
	with open(f, 'r') as f:
		for line in f.readlines():
			v = line.split()[0]
			edges = line.split()[1:]
			graph[int(v)] =  [tuple(map(int, uw.split(','))) for uw in edges]

	return graph

def main():
	
	
	G1 = {1:((2, 3), (3, 3)), 2:((3, 1), (4, 2)), 3:((4,50), ), 4:((2, 2), (3, 50), )}
	G2 = {1:((2, 3), (3, 5)), 2:((3, 1), (4, 2)), 3:((4,50), ), 4:((2, 2), (3, 50), )}
	G3 = {1:((2, 8), (3, 15)), 2:((1, 7), (3, 4), (4, 5)), 3:((1, 12), ), 4:((3, 5), )}
	G4 = {1:((2, 8), (3, 17)), 2:((1, 7), (3, 10), (4, 5)), 3:((1, 12), ), 4:((3, 3), )}
	G5 = {  1: ((2, 8), (3,10), (7,7)),
			2: ((1, 8), (3,3 ), (4,6)),
			3: ((7,11), (1,10), (2,3), (4,2), (5,1)),
			4: ((2, 6), (3,2 ), (5,1), (6,5)),
			5: ((3, 9), (6,4 ), (8,10)),
			6: ((5, 4), (4,5 ), (10,3)),
			7: ((1, 7), (3,11)),
			8: ((5,10), (9,1)),
			9: ((8, 1), ),
			10:((6, 3), (11,9), (12,2)),
			11:((10,9), (13,3)),
			12:((10,2), (13,1)),
			13:((12,1), (11,3))}
	graphs = [G1, G2, G3, G4, G5]
	for g in graphs:
		prev_path, source_to_v = dijkstra(g, 1)
		print 'the prev_path for v in the graph is :', prev_path
		print 'the distance with each v to source is :', source_to_v

	graph = readfile()
	prev_path, source_to_v = dijkstra(graph, 1)
	desired_v = [7,37,59,82,99,115,133,165,188,197]
	distances = [source_to_v[v]	for v in desired_v]
	print 'the distance of desired v to source 1 is:',distances

if __name__ == '__main__':

	main()