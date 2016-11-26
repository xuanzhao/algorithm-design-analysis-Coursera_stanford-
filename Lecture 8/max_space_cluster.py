from time import time

def cluster(num_nodes, desired_num_clusters, sorted_edges, nodes_set):
	"""Given 'sorted_edges' as lists of [head, tail, edge_dist] sorted
	by edge_dist in increasing order, and the 'nodes_set' as dicts of 
	{node: [parent, rank]}, compute a max-spacing 'desired_num_clusters'
	clustering out of 'num_clusters' single nodes. Algorithm is based on 
	Kruskal's minimum spanning tree algorithm.
	"""
	
	num_clusters = num_nodes
	while num_clusters > desired_num_clusters:
		node_a, node_b, edge_dist = sorted_edges.pop()
		while is_sameRoot(node_a, node_b, nodes_set):
			node_a, node_b, edge_dist = sorted_edges.pop()
		union_set(node_a, node_b, nodes_set)
		num_clusters -= 1
			
	node_a, node_b, edge_dist = sorted_edges.pop()
	while is_sameRoot(node_a, node_b, nodes_set):
		node_a, node_b, edge_dist = sorted_edges.pop()
	
	return node_a, node_b, edge_dist

def is_sameRoot(node_a, node_b, nodes_set):

	a_root = find_root(node_a, nodes_set)
	b_root = find_root(node_b, nodes_set)
	return a_root == b_root

def find_root(node, nodes_set):

	# path compress
	def loop(node, nodes_set):
		if node != nodes_set[node][0]:
			node_rank = nodes_set[node][1]
			nodes_set[node] = (loop(nodes_set[node][0], nodes_set), node_rank)
		return nodes_set[node][0]

	root = loop(node, nodes_set)
	return root

def union_set(node_a, node_b, nodes_set):

	a_root = find_root(node_a, nodes_set)
	b_root = find_root(node_b, nodes_set)

	a_rank = nodes_set[a_root][1]
	b_rank = nodes_set[b_root][1]

	# merge by rank
	if a_rank > b_rank:
		nodes_set[b_root] = (a_root, b_rank)
	elif a_rank < b_rank:
		nodes_set[a_root] = (b_root, a_rank)
	else:
		nodes_set[a_root] = (b_root, a_rank)
		nodes_set[b_root] = (b_root, b_rank+1)


def make_nodes_set(num_nodes):
	# {node: [parent, rank]}
	return {i: (i, 0) for i in range(1, num_nodes + 1)}




def main():
	
	edges = []
	# load file
	with open('clustering1.txt') as f:
		num_nodes = int(f.next())
		for line in f:
			line = map(int, line.strip().split())
			edges.append(line)
	
	sorted_edges = sorted(edges, key=lambda x: x[2], reverse=True)
	
	desired_num_clusters = 4
	nodes_set = make_nodes_set(num_nodes)
	
	print('start cluster nodes...')
	start = time()

	node_a, node_b, edge_dist = cluster(num_nodes, desired_num_clusters, sorted_edges, nodes_set)
	
	print('cluster to 4 , current minimum distance between %d and %d is %d' % (node_a, node_b, edge_dist))


if __name__ == '__main__':
	print('start cluster nodes...')
	start = time()
	main()
	print('end cluster node, use time', time()-start)