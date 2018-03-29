"""
Graph represented through adjacency sets. 
Example node representation:
{node1 : {connected_node_1 : weight_1, connected_node_2 : weight_2, ... ,connected_node_n : weight_n}}
"""
import math

class Graph:

	def __init__(self):
		self.graph = {}		

	def add_node(self, value):
		if value not in self.graph:
			self.graph.update({value : {}})
			return True

	def contains(self, value):
		return value in self.graph

	def size(self):
		return len(self.graph)


	def add_edge(self, source, destination, weight):
		#implemented differently for directed and undirected graphs
		pass

	def remove_node(self, target):
		#implemented differently for directed and undirected graphs
		pass

	def get_path(self, source, destination):
		"""
		if a path exists between source and destination, returns a list with
		the first element being a list of the nodes in the path, second element
		is the total weight between the two nodes. Only returns one possible path, 
		not necessarily the minimum weight path. 
		"""
		if source not in self.graph or destination not in self.graph:
			return 
		path = []
		visited = set()
		distance = self.__get_path_helper(path, 0, visited, source, destination)
		if distance:
			return [path,distance]			
		else:
			return

	def __get_path_helper(self, current_path, current_distance, visited, current_node, destination):
		#depth-first-search
		#returns distance (total weight) between nodes and updates path list throughout traversal
		if current_node == destination:
			current_path.append(current_node)
			return current_distance
		else:
			current_path.append(current_node)
			visited.add(current_node)
			for key in self.graph.get(current_node):
				if key not in visited:
					distance = self.__get_path_helper(current_path, current_distance + self.graph.get(current_node).get(key), visited, key, destination)
					if distance:
						return distance
					else:
						current_path.pop()


	def dijkstra(self, source, destination):
		"""
		My implementation of dijkstra's shortest path algorithm to find the minimum weight
		path between two nodes. Returns list with first item being the path as a list of nodes 
		in order, and the second item being the total weight of the shortest path
		"""
		if source not in self.graph or destination not in self.graph:
			return
		shortest_paths = {}
		visited = set()
		predecessors = {}
		for node in self.graph:
			shortest_paths[node] = math.inf
		shortest_paths[source] = 0
		current_node = source
		while len(visited) < len(self.graph):
			for node in self.graph[current_node]:
				if node not in visited:
					if shortest_paths[current_node] + self.graph[current_node][node] < shortest_paths[node]:
						shortest_paths[node] = shortest_paths[current_node] + self.graph[current_node][node]
						predecessors[node] = current_node
			visited.add(current_node)
			next_node = None
			for node in self.graph:
				if node not in visited:
					if next_node is None:
						next_node = node
					elif shortest_paths[node] < shortest_paths[next_node]:
						next_node = node
			current_node = next_node
		if shortest_paths[destination] is math.inf:
			return [[],math.inf]
		stack = []
		stack.append(destination)
		current_node = destination
		while predecessors.get(current_node, None) is not None:
			current_node = predecessors.get(current_node, None)
			stack.append(current_node)
		path = []
		for i in range(len(stack)):
			path.append(stack.pop())
		return [path,shortest_paths[destination]]


class Directed_Graph(Graph):

	def __init__(self):
		super().__init__()

	def add_edge(self, source, destination, weight):
		#can't allow for negative weights, or else dijkstra won't work
		if weight < 0:
			return
		#if source or destination are not already in graph, they are added
		if source not in self.graph:
			self.graph.update({source : {}})
			print(source + " added to graph")
		if destination not in self.graph:
			self.graph.update({destination : {}})
			print(destination + " added to graph")

		self.graph.get(source).update({destination : weight})

	def remove_node(self, target):
		removed = self.graph.pop(target, None)
		if removed is not None:
			#need to search entire graph for nodes that have target as a destination, then remove it
			for node in self.graph:
				self.graph.get(node).pop(target, None)


class Undirected_Graph(Graph):

	def __init__(self):
		super().__init__()

	def add_edge(self, source, destination, weight):
		if weight < 0:
			return
		if source not in self.graph:
			self.graph.update({source : {}})
			print(source + " added to graph")
		if destination not in self.graph:
			self.graph.update({destination : {}})
			print(destination + " added to graph")

		#adds edge to and from source and destination, since the graph is undirected
		self.graph.get(source).update({destination : weight})
		self.graph.get(destination).update({source : weight})

	def remove_node(self, target):
		removed = self.graph.pop(target, None)
		if removed is not None:
			#only need to search nodes that are in the target's adjacency list, since graph is undirected
			for node in removed:
				self.graph.get(node).pop(target, None)


#sample undirected graph used for testing found at https://www.geeksforgeeks.org/wp-content/uploads/Fig-0.jpg 

test_undirected_graph = Undirected_Graph()

for i in range(9):
	test_undirected_graph.add_node(i)

test_undirected_graph.add_edge(0,1,4)
test_undirected_graph.add_edge(0,7,8)
test_undirected_graph.add_edge(1,7,11)
test_undirected_graph.add_edge(7,8,7)
test_undirected_graph.add_edge(7,6,1)
test_undirected_graph.add_edge(1,2,8)
test_undirected_graph.add_edge(2,8,2)
test_undirected_graph.add_edge(8,6,6)
test_undirected_graph.add_edge(6,5,2)
test_undirected_graph.add_edge(2,5,4)
test_undirected_graph.add_edge(2,3,7)
test_undirected_graph.add_edge(3,5,14)
test_undirected_graph.add_edge(3,4,9)
test_undirected_graph.add_edge(5,4,10)

print(test_undirected_graph.dijkstra(0,8))


