"""
Graph represented through adjacency sets. 
Example node representation:
{node1 : {connected_node_1 : weight_1, connected_node_2 : weight_2, ... ,connected_node_n : weight_n}}
"""
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
			result = []
			result.append(path)
			result.append(distance)
			return result
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


class Directed_Graph(Graph):

	def __init__(self):
		super().__init__()

	def add_edge(self, source, destination, weight):
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