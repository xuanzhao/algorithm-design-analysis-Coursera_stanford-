from time import time
from collections import defaultdict


from collections import defaultdict
from functools import reduce

from heapq import heappop, heappush

def Bellman_Ford(source, num_nodes, edges):
    """Given the source, the graph nodes(labeled from 1 to 'num_nodes') and the 
    graph edges(in form of {(tail, head): edge_dist}), computes the single source
    shortest path if graph does not contain negative cycle. 
    
    'current_array' and 'last_array' keep track of the shortest path with 'i' edges
    and with 'i - 1' edges respectively, after each iteration they swap value with 
    each other so that 'last_array' get updated. If in some iteration j(smaller than
    num_nodes - 1), 'current_array == last_array' holds, which means 'current_array'
    will not change in any later iteration, then we can safely halt and return. In
    order to check this early stop condition, value swap between the two arrays are
    needed.
    The outer loop ends at 'num_nodes' instead of 'num_nodes - 1', which means one 
    more iteration than needed to compute shortest path, so that negative circle can
    be detected. Any shortest path change after the additional iteration indicates
    the existence of negative circle in graph.
    
    Algorithm can work on graphs with negative edges, and has running time of O(nm),
    where n is the number of nodes and m is the number of edges"""

    inf = float('inf')
    bound = num_nodes+1

    tails = defaultdict(list)
    for (tail, head) in edges.keys():
        tails[head].append(tail)

    last_array = [inf for i in range(bound)]
    last_array[source] = 0
    current_array = [inf for i in range(bound)]

    for i in range(1, bound): # loop all vertex
        for head in range(1, bound):  # loopp all edges (all tails to head)
            tail_edges = [inf]
            for tail in tails[head]:
                tail_edges.append(last_array[tail] + edges[(tail, head)])
            current_array[head] = min(last_array[head], min(tail_edges))

        if current_array == last_array: #early stop & check nagative cycle
            return current_array
        else:
            last_array, current_array = current_array, last_array

    return False    # graph has negative circle


def dijkstra(G, source=1):
    
    def relax(u, v, weight):
        if distance_to_source[v] > distance_to_source[u] + weight:
            # if v in [h[1] for h in heap]:
            #   heap.remove((distance_to_source[v], v))
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

        u = heap.pop()[1] # minmium edge to source

        # after set u into the set of the shortest path graph, relax u to other edges
        for vw in G[u]:
            v, w = vw[0], vw[1]  # (edge, dist)
            relax(u, v, w)

    return distance_to_source



def Johnson(num_nodes, edges):
    """Given the graph nodes (labeled from 1 to num_nodes), and graph edges( in
    form of {(tail, head): edge_dist}, computes the all pairs shortest path if
    graph does not contain negative cycle.    
    An additional 'new_node' is added to the graph G, and then Bellman_Ford algorithm
    runs on the new graph G' to detect negative cycles, and computes the shortest 
    path form the 'new_node' to all nodes of G if no negative cycle exists. The 
    shortest path is then used to compute a weighted edge for each edge in G, so
    that with weighted edges, which are all non-negative, the fast Dijkstra's 
    algorithm can be used to compute single source shortest path for each source node.
    Note in Dijkstra, a PriorityQueue 'pq' requires a heap initialization for every 
    source. And different from the 'edges' that Johnson has as input, the 'edges' for
    Dijkstra is in a form of {tail:[(head, edge_dist), ...]}, so 'dijkstra_edges' needs
    to be constructed first.
    After Dijkstra's algorithm computes the shortest path, the shortest path distance
    is corrected by the shortest path from 'new_node' in G'.
    Algorithm has 1 invocation of Bellman_Ford(Big-O(nm)) and n invocations of Dijkstra
    (Big-O(mnlogn)), so the overall running time is O(mnlogn)."""

    all_pairs_shortest_path = {}
    new_node = num_nodes + 1
    new_edges = edges.copy()
    dijkstra_edges = defaultdict(list)
    #--------------------Bellman Ford------------------------
    for node in range(1, num_nodes + 1):
        new_edges[(new_node, node)] = 0
    bf = Bellman_Ford(new_node, num_nodes + 1, new_edges)
    if bf == False:
        return bf
    #--------------------------------------------------------
    #--------------weighted edges and Dijkstra edges-----------
    for ((tail, head), dist) in new_edges.items():
        weighted_dist = dist + bf[tail] - bf[head]
        dijkstra_edges[tail].append((head, weighted_dist))
    #-------------- Dijkstra run n times ----------------------
    for source in range(1, num_nodes+1):
        shortest_path_dict = dijkstra(dijkstra_edges, source)

        # calculate the original weight for each edge
        for (node, dist) in shortest_path_dict.items():
            all_pairs_shortest_path[(source, node)] = dist - bf[source] + bf[node]

    return min(all_pairs_shortest_path.values())


def main():
    
    edges = {}
    with open('g2.txt', 'r') as f:
        num_vertex, num_edges = map(int, f.next().strip().split())
        for line in f:
            tail, head, dist = map(int, line.strip().split())
            edges[(tail, head)] = dist
    
    start = time()
    shortest_path = Johnson(num_edges, edges)
    print 'the shortest_path is ', shortest_path
    print 'total time is ', time() - start


if __name__ == '__main__':
    main()