from collections import defaultdict
import sys
import threading


def findSCC(graph):

	def get_Grev(graph):

		Grev = defaultdict(list)
		for v in graph:
			adj_v = graph.get(v)
			for u in adj_v:
				Grev[u].append(v)

		return Grev

	def DFS_loop1(Grev):

		for i in Grev:
			if not explored_loop1[i]:
				DFS1(Grev, i)

	def DFS1(Grev, i):

		explored_loop1[i] = True

		if Grev.get(i):
			for j in Grev.get(i):
				if not explored_loop1[j]:
					DFS1(Grev, j)

		current_T[0] += 1
		explored_finished_time[i] = current_T[0]

	def DFS_loop2(graph):

		# reorder vextex by the DFS finished time in first round explore
		des_order = sorted(explored_finished_time,
							key=lambda x: explored_finished_time[x],
							reverse=True)
		for i in des_order:
			if not explored_loop2[i]:
				currnet_S[0] = i
				DFS2(graph, i)

	def DFS2(graph, i):

		explored_loop2[i] = True
		leaders[currnet_S[0]].append(i) # SCCs = nodes with the same "leader"

		if graph.get(i):
			for j in graph.get(i):
				if not explored_loop2[j]:
					DFS2(graph, j)
	

	currnet_S = [None]  # for recording current leaders in the second round DFS
	current_T = [0]     # for recording current vextice finish time in the first round DFS

	explored_loop1 = defaultdict(bool)
	explored_loop2 = defaultdict(bool)
	explored_finished_time = {}  # "finishing time" of each v in first round exploring
	leaders = defaultdict(list)

	# get finish order of each node
	Grev = get_Grev(graph)  # Grev is graph with all arcs reversed
	DFS_loop1(Grev)         # Goal: compute "magical ordering" of nodes
							# means get right vertex serach order in search SCCs

	# discorver the SCCs one-by-one
	DFS_loop2(graph)  # processing nodes in decreasing order of finishing times

	return leaders  # return SCC graph

def main():

	graphs = ['test1.txt', 'test2.txt', 'test3.txt','test4.txt','test5.txt','SCC.txt']
	for g in graphs:
		graph = defaultdict(list)		
		with open(g, 'r') as f:
			for line in f.readlines():
				arc = line.strip().split()
				tl, hd = int(arc[0]), int(arc[1])
				graph[tl].append(hd) # get AdjList of the graph

		# get SCC graph for input graph
		scc_G = findSCC(graph)

		# calculate the size of each SCC in SCC_Graph 
		sccG_size = map(lambda x: len(x), scc_G.values())
		sorted_sccG = sorted(sccG_size, reverse=True)

		# get the top5 ssc
		top5 = [0] * 5
		for i in range(5):
			try:
				top5[i] = sorted_sccG[i]
			except:
				break
		print 'the top 5 scc size is :', top5

	# graph = defaultdict(list)
	# with open('test2.txt', 'r') as f:
	# 	for line in f.readlines():
	# 		arc = line.strip().split()
	# 		tl, hd = int(arc[0]) , int(arc[1])
	# 		graph[tl].append(hd)

	# # get SCC graph for input graph
	# scc_G = findSCC(graph)

	# # calculate the size of each SCC in SCC_Graph 
	# scc_size = map(lambda x: len(x), scc_G.values())
	# sorted_sccG = sorted(scc_size, reverse=True)

	# # get the top5 ssc
	# top5 = [0] * 5
	# for i in range(5):
	# 	try:
	# 		top5[i] = sorted_sccG[i]
	# 	except:
	# 		break
	# print 'the top 5 scc size is :', top5

if __name__ == '__main__':
	sys.setrecursionlimit(2 ** 20)
	# main()
	threading.stack_size(67108864) 
	thread = threading.Thread(target = main)
	thread.start() 