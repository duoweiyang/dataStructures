import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Duo-Wei Yang
 * @userid dyang305
 * @GTID 903213022
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                                         Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input can't be null.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Vertex to begin the bfs on "
                    + "doesn't exist in the graph.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        List<Vertex<T>> vertices = new ArrayList<>();
        vertices.add(start);

        Queue<Vertex<T>> nodes = new LinkedList<>();
        nodes.add(start);
        while (!nodes.isEmpty()) {
            Vertex<T> search = nodes.remove();
            List<VertexDistance<T>> adjVertices = adjList.get(search);
            for (int i = 0; i < adjVertices.size(); i++) {
                VertexDistance<T> vd = adjVertices.get(i);
                Vertex<T> child = vd.getVertex();
                if (!vertices.contains(child)) {
                    nodes.add(child);
                    vertices.add(child);
                }
            }
        }
        return vertices;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                                       Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Vertex to begin the bfs on "
                    + "doesn't exist in the graph.");
        }
        List<Vertex<T>> vertices = new ArrayList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList =
                graph.getAdjList();
        Set<Vertex<T>> vertexSet = new HashSet<>();
        dfs(adjList, start, vertexSet, vertices);
        return vertices;
    }

    /**
     * Private helper method for depthFirstSearch
     *
     * @param adjList the adjacency List
     * @param start vertex we start from
     * @param vertexSet set of vertices visited
     * @param vertices vertices visited
     * @param <T> generic type of elements stored
     */
    private static <T> void dfs(Map<Vertex<T>,
            List<VertexDistance<T>>> adjList, Vertex<T> start,
                                Set<Vertex<T>> vertexSet,
                                List<Vertex<T>> vertices) {
        if (!vertexSet.contains(start)) {
            vertexSet.add(start);
            vertices.add(start);
            List<VertexDistance<T>> vdpairList = adjList.get(start);
            for (VertexDistance<T> vdpair : vdpairList) {
                Vertex<T> child = vdpair.getVertex();
                dfs(adjList, child, vertexSet, vertices);
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from {@code start}, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from {@code start} to every
     *          other node in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Vertex to begin the bfs on "
                    + "doesn't exist in the graph.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList =
                graph.getAdjList();

        Map<Vertex<T>, Integer> dijkstra = new HashMap<>();

        for (Vertex<T> vertex : adjList.keySet()) {
            dijkstra.put(vertex, Integer.MAX_VALUE);
        }

        dijkstra.put(start, 0);

        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        pq.add(new VertexDistance<T>(start, 0));

        while (!pq.isEmpty()) {
            VertexDistance<T> curr = pq.remove();
            List<VertexDistance<T>> vdPairs =
                    graph.getAdjList().get(curr.getVertex());
            for (VertexDistance<T> vd : vdPairs) {
                int dist = curr.getDistance() + vd.getDistance();
                if (dist < dijkstra.get(vd.getVertex())) {
                    dijkstra.put(vd.getVertex(), dist);
                    pq.add(new VertexDistance<>(vd.getVertex(),
                            dist));
                }
            }
        }
        return dijkstra;
    }


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        Queue<Edge<T>> edges = new PriorityQueue<>(graph.getEdges());
        Set<Edge<T>> result = new HashSet<>();
        DisjointSet<Vertex<T>> ds = new DisjointSet<Vertex<T>>(
                graph.getAdjList().keySet());

        while (!edges.isEmpty())  {
            Edge<T> edge = edges.remove();
            Vertex<T> u = ds.find(edge.getU());
            Vertex<T> v = ds.find(edge.getV());
            if (!u.equals(v))    {
                ds.union(u, v);
                result.add(edge);
                result.add(new Edge<T>(edge.getV(), edge.getU(),
                        edge.getWeight()));
            }
        }

        int vSize = graph.getAdjList().keySet().size();
        if (result.size() == 2 * (vSize - 1))   {
            return result;
        } else  {
            return null;
        }
    }
}
