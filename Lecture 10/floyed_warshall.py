from time import time
from pdb import set_trace

def Floyed_Warshall(num_edges, edges):
	"""Given the graph nodes(labeled from 1 to 'num_nodes') and the graph edges
    (in form of {(tail, head): edge_dist}), computes the all pairs shortest
    path if graph does not contain negative cycle.
    'current_array[i][j]' and 'last_array[i][j]' keep track of the shortest path
    with 'k' edges and 'k - 1' edges respectively. Since both arrays actually point
    to the same array, when 'current_array[i][j]' gets updated, so does 'last_array
    [i][j]'.
    In any iteration k, 'current_array[i][i] < 0' detects a negative cycle.
    Running time of O(n*n*n) where n is number of nodes in graph."""
	
	
	inf = float('inf')
	#no need to do value swap, OK to have both point to same array
	#whenever 'current_array' updated, so does 'last_array'
	current_array = [[inf for j in range(num_edges+1)] for i in range(num_edges+1)]
	last_array = current_array
	
	for i in range(num_edges+1):
		for j in range(num_edges+1):
			if i==j :
				last_array[i][j] = 0
			elif (i, j) in edges:
				last_array[i][j] = edges[(i, j)]
	
	for k in range(1, num_edges+1):
		for i in range(1, num_edges+1):
			for j in range(1, num_edges+1):
				current_array[i][j] = min(last_array[i][j], 
										  last_array[i][k] + last_array[k][j])
	
				if i==j:
					if current_array[i][j] < 0:
						return 'Graph has negative cycle'
	
	set_trace()
	# return the shortest shortest path between tow vertex
	return min(min(current_array, key=lambda x: min(x)))

def main():

	edges = {}
	with open('test.txt', 'r') as f:
		num_vertex, num_edges = map(int, f.next().strip().split())
		for line in f:
			tail, head, dist = map(int, line.strip().split())
			edges[(tail, head)] = dist

	start = time()
	shortest_path = Floyed_Warshall(num_edges, edges)
	print 'the shortest_path is ', shortest_path
	print 'total time is ', time() - start


if __name__ == '__main__':
    main()

