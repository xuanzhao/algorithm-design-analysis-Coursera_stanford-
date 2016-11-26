from time import time


def cluster(orig_num_clusters, nodes_set):
    """Given implicit edge costs and the 'nodes_set' as dicts of {node: [parent, rank]},
    compute the largest value of 'num_clusters' so that the clustering has spacing at 
    least 3 bit flips
    """

    current_num_cluster = orig_num_clusters
    all_nodes = set(nodes_set.keys())

    for node in all_nodes:
        possible_nodes = flip_two_bit(node)
        actual_nodes = possible_nodes.intersection(all_nodes)

        # satisfied nodes that could be in current node's cluster
        for other_node in actual_nodes:
            if not is_same_root(node, other_node, nodes_set):
                union(node, other_node, nodes_set)
                current_num_cluster -= 1

    return current_num_cluster

def flip_two_bit(node):
    """
    At most we could flip two bit. we could get all potential node.
    """
    bits_list = list(node)
    out = set()
    bits_length = len(bits_list)

    for i in range(bits_length):
        for j in range(i,bits_length):
            new_node = bits_list[:]
            if i != j:
                new_node[i] = ('1' if node[i] == '0' else '0')
                new_node[j] = ('1' if node[j] == '0' else '0')
            else:
                new_node[i] = ('1' if node[i] == '0' else '0')
            out.add(''.join(new_node))

    return out

def is_same_root(node_a, node_b, nodes_set):

    a_root = find_root(node_a, nodes_set)
    b_root = find_root(node_b, nodes_set)
    return a_root == b_root

def find_root(node, nodes_set):

    def loop(node, nodes_set):
        if node != nodes_set[node][0]:
            nodes_set[node][0] = loop(nodes_set[node][0], nodes_set)
        return nodes_set[node][0]

    return loop(node, nodes_set)

def union(node_a, node_b, nodes_set):

    a_root = find_root(node_a, nodes_set)
    b_root = find_root(node_b, nodes_set)
    a_rank = nodes_set[a_root][1]
    b_rank = nodes_set[b_root][1]

    if a_rank > b_rank:
        nodes_set[b_root][0] = a_root
    elif a_rank < b_rank:
        nodes_set[a_root][0] = b_root
    else:
        nodes_set[a_root][0] = b_root
        nodes_set[b_root][1] = b_rank+1 


def main():
    
    start = time()
    # using bits to implicitly representate a node.
    nodes_set = {}
    with open('clustering_big.txt') as f:
        num_nodes, num_bits = map(int, f.next().strip().split())
        for line in f:
            node = ''.join(line.strip().split())
            nodes_set[node] = [node, 0]

    orig_num_clusters = len(nodes_set)
    number_cluster = cluster(orig_num_clusters, nodes_set)

    print('number cluster that the clustering has spacing least 3bit flips is %d' % number_cluster)
    print('cost time', time() - start)
if __name__ == '__main__':
    main()