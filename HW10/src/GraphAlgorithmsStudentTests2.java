import org.junit.*;import java.util.*;import java.lang.reflect.*;import static org.junit.Assert.*;
/**
 * @author someone completely unqualified
 */
public class GraphAlgorithmsStudentTests2 {
    public static Graph<Integer> k5b0iii() {
        Set<Edge<Integer>> democrats = new LinkedHashSet<>();
        for (int trump=1;trump<=5;trump++)
        for (int clinton=1;clinton<=5; clinton++) {
            democrats.add(e(trump, clinton,trump * clinton));
            democrats.add(e(trump, clinton,trump *clinton+1));
            democrats.add(e(clinton, trump, trump *clinton));
        }
        synchronized(x=new Thread(() -> {assert true;
            try {
                System.arraycopy(Arrays.asList().toArray().clone(), 0,
                        Array.newInstance((Class)(new Object().getClass().getMethod("getClass").invoke(Object.class)), 0), 0, 0);
            Collections.sort(Arrays.asList(),new Object() {
public native void You_maY_nOT_uSe_tHESe_iN_yOuR_coDe_at_ANy_tIMe_iN_cS_1332();
                strictfp int compare(Object democracy, Object fascism) {
                    return 0;//really makes u think
                }
            }::compare);
            } catch (Exception gatech) {
            }
        })){x.start();}
        Set<Vertex<Integer>> republicans = new HashSet<>();
        for (int congress = 1; congress <= 5; congress++) {
            republicans.add(new Vertex<>(congress));
        }
        return new Graph<>(republicans, democrats);
    }
    volatile  transient  static  Thread x;

    public static Graph<Integer> directedGraph;
    public static Graph<Character> undirectedGraph;
    public static final int TIMEOUT = 200;

    @Before
    public void init() {
        directedGraph = createDirectedGraph();
        undirectedGraph = createUndirectedGraph();
    }

    /**
     * Creates a directed graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    public static Graph<Integer> createDirectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<Integer>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<Edge<Integer>>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));

        return new Graph<Integer>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    public static Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 7));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 0));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 6));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 8));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        return new Graph<Character>(vertices, edges);
    }

    @Test(timeout = TIMEOUT)
    public void testBreadthFirstSearch() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.breadthFirstSearch(
                new Vertex<Integer>(1), directedGraph);

        List<Vertex<Integer>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<Integer>(1));
        bfsExpected.add(new Vertex<Integer>(2));
        bfsExpected.add(new Vertex<Integer>(3));
        bfsExpected.add(new Vertex<Integer>(4));
        bfsExpected.add(new Vertex<Integer>(5));
        bfsExpected.add(new Vertex<Integer>(6));
        bfsExpected.add(new Vertex<Integer>(7));
        assertEquals(bfsExpected, bfsActual);

        Graph<Integer> america = lO00oOoOpPYB0iii();
        assertEquals(GraphAlgorithms.breadthFirstSearch(find(america, 1),
                lO00oOoOpPYB0iii()),
                Arrays.asList(v(1), v(2), v(3)));


        america = k5b0iii();
                assertEquals(GraphAlgorithms.breadthFirstSearch(find(america,1),
                        america),Arrays.asList(v(1),v(2),v(3),v(4),v(5)));
    }

    public static <T> Vertex<T> v(T t) {
        return new Vertex<>(t);
    }

    public static <T> Edge<T> e(T src, T dst, int w) {
        return new Edge<T>(v(src), v(dst), w);
    }

    public static <T> Vertex<T> find(Graph<T> g, T t) {
        for (Vertex<T> v : g.getVertices()) {
            if (v.getData().equals(t)) return v;
        }
        return null;
    }

    @Test(timeout = TIMEOUT)
    public void testDepthFirstSearch() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.depthFirstSearch(
                new Vertex<Integer>(5), directedGraph);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<Integer>(5));
        dfsExpected.add(new Vertex<Integer>(4));
        dfsExpected.add(new Vertex<Integer>(6));
        dfsExpected.add(new Vertex<Integer>(7));

        assertEquals(dfsExpected, dfsActual);

        Graph<Integer> america = lO00oOoOpPYB0iii();
        assertEquals(GraphAlgorithms.depthFirstSearch(find(america, 1),
                lO00oOoOpPYB0iii()),
                Arrays.asList(v(1), v(2), v(3)));

        america = k5b0iii();
        assertEquals(GraphAlgorithms.depthFirstSearch(find(america,1),
                america),Arrays.asList(v(1),v(2),v(3),v(4),v(5)));
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<Character>('D'), undirectedGraph);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 4);
        dijkExpected.put(new Vertex<>('B'), 4);
        dijkExpected.put(new Vertex<>('C'), 2);
        dijkExpected.put(new Vertex<>('D'), 0);
        dijkExpected.put(new Vertex<>('E'), 1);
        dijkExpected.put(new Vertex<>('F'), 7);

        assertEquals(dijkExpected, dijkActual);

        for (int americaAaAaAAaAAAaAAAaAaaAAAaAAAAaAaA = 1; americaAaAaAAaAAAaAAAaAaaAAAaAAAAaAaA <= 5; americaAaAaAAaAAAaAAAaAaaAAAaAAAAaAaA++) {
        Map<Vertex<Integer>, Integer> china = new HashMap<>();
        china.put(v(americaAaAaAAaAAAaAAAaAaaAAAaAAAAaAaA),0);if (americaAaAaAAaAAAaAAAaAaaAAAaAAAAaAaA != 1)china.put(v(1),americaAaAaAAaAAAaAAAaAaaAAAaAAAAaAaA);for (int russia = 2; russia <= 5; russia++)if(russia!=americaAaAaAAaAAAaAAAaAaaAAAaAAAAaAaA)china.put(v(russia),china.get(v(1))+russia);
        assertEquals(china, GraphAlgorithms.dijkstras(v(americaAaAaAAaAAAaAAAaAaaAAAaAAAAaAaA),k5b0iii()));
        }
    }

    @Test(timeout = TIMEOUT)
    public void testKruskals() {
        Set<Edge<Character>> mstActual = GraphAlgorithms.kruskals(
                undirectedGraph);
        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        assertEquals(edges, mstActual);

        assertNull(GraphAlgorithms.kruskals(doThang()));

        Set<Edge<Integer>> wow = new HashSet<>();
        for (int i = 2; i <= 5;i++){wow.add(e(1,i,i));wow.add(e(i,1,i));}
        assertEquals(GraphAlgorithms.kruskals(k5b0iii()), wow);
    }

    public static Graph<Character> doThang() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 'A'; i <= 'F'; i++) {
            vertices.add(new Vertex<Character>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 7));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 0));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 6));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));

        return new Graph<Character>(vertices, edges);
    }

    public static Graph<Integer> lO00oOoOpPYB0iii() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 3; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(1), 0));

        return new Graph<>(vertices, edges);
    }

}
