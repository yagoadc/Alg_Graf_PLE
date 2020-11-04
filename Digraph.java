import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import javax.swing.text.StyledEditorKit.BoldAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.io.Serializable;

public class Digraph implements Serializable {
    protected HashMap<Integer, Vertex> vertex_set;
    protected Integer tempo;
    private Boolean acyclic;
    static int inf = 2147483647;

    public Digraph() {
        vertex_set = new HashMap<Integer, Vertex>();
        acyclic = null;
    }

    public boolean add_vertex(final int id) {
        if (this.vertex_set.get(id) == null) {
            final Vertex v = new Vertex(id);
            vertex_set.put(v.id, v);
            return true;
        } else {
            System.out.println("Já existe vértice com esse número");
            return false;
        }

    }

    /*
     * public void add_arc( final Integer id1, final Integer id2) { final Vertex v1
     * = vertex_set.get(id1); final Vertex v2 = vertex_set.get(id2);
     * v1.add_neighbor( v2 ); }
     */

    public void add_arc_w(Integer id1, Integer id2, Integer weight) { // arcos com pesos
        add_arc(id1, id2);
        Vertex v1 = vertex_set.get(id1);
        v1.add_weight(id2, weight);
    }

    public void add_arc(Integer id1, Integer id2) {
        try {
            Vertex v1 = vertex_set.get(id1);
            Vertex v2 = vertex_set.get(id2);
            v1.add_neighbor(v2);
        } catch (Exception e) {
            this.add_vertex(id1);
            this.add_vertex(id2);
            Vertex v1 = vertex_set.get(id1);
            Vertex v2 = vertex_set.get(id2);
            v1.add_neighbor(v2);
        }
    }

    public void add_arc1(Integer id1, Integer id2) {
        this.add_vertex(id1);
        Vertex v1 = vertex_set.get(id1);
        this.add_vertex(id2);
        Vertex v2 = vertex_set.get(id2);
        v1.add_neighbor(v2);
    }

    public void add_edge(final Integer id1, final Integer id2) {
        final Vertex v1 = vertex_set.get(id1);
        final Vertex v2 = vertex_set.get(id2);
        v1.add_neighbor(v2);
        v2.add_neighbor(v1);

        // ou
        // add_arc( id1, id2 );
        // add_arc( id2, id1 );
    }

    public void del_vertex(final int id) {
        if (vertex_set.containsKey(id)) {
            final Vertex v_remove = vertex_set.get(id);
            final HashMap<Integer, Vertex> meus_vizinhos = v_remove.nbhood;
            for (final Integer key_id : meus_vizinhos.keySet()) {
                this.vertex_set.get(key_id.intValue()).nbhood.remove(id);
            }
            this.vertex_set.remove(id);
        } else {
            System.out.printf("Não existe vértice com essa id");
        }

    }

    // Assistir gravação aula (03/09), melhorar o for
    public void compact() {
        final int n = vertex_set.size();
        final int[] present = new int[n + 1];
        final Vertex[] stranges = new Vertex[n];
        for (int i = 1; i <= n; i++) {
            present[i] = 0;
        }
        int qst = 0;
        for (final Vertex v1 : vertex_set.values()) {
            if (v1.id <= n)
                present[v1.id] = 1;
            else
                stranges[qst++] = v1;
        }
        int i = 1;
        for (int pairs = 0; pairs < qst; i++) {
            if (present[i] == 0)
                present[pairs++] = i;
        }
        for (i = 0; i < qst; i++) {
            final int old_id = stranges[i].id;
            stranges[i].id = present[i];
            for (final Vertex v1 : vertex_set.values()) {
                if (v1.nbhood.get(old_id) != null) {
                    v1.nbhood.remove(old_id);
                    v1.nbhood.put(stranges[i].id, stranges[i]);
                }
            }
        }
    }

    public int max_degree() { // Grau do vértice que possui maior número de arestas incidentes a ele.
        int max = -1;
        int aux = 0;
        for (final Vertex v : vertex_set.values()) {
            aux = v.grau_saida();
            if (aux > max) {
                max = aux;
            }
        }
        return max;
    }

    public boolean is_undirected() {
        for (Vertex v1 : vertex_set.values()) {
            for (Vertex v2 : v1.nbhood.values()) {
                if (v2.nbhood.get(v1.id) == null) {
                    // this.dirigivel = false;
                    return false;
                }

            }
        }
        // this.dirigivel = true;
        return true;
    }

    public Graph subjacent() {
        final Graph g2 = new Graph();
        for (final Vertex v11 : this.vertex_set.values()) {
            g2.add_vertex(v11.id);
        }
        for (final Vertex v11 : this.vertex_set.values()) {
            for (final Vertex v12 : v11.nbhood.values()) {
                final Vertex v21 = g2.vertex_set.get(v11.id);
                final Vertex v22 = g2.vertex_set.get(v12.id);
                v21.add_neighbor(v22);
                v22.add_neighbor(v21);
            }
        }
        return g2;
    }

    public boolean is_connected() {
        // Modularizar para tirar repitição de código
        if (!this.is_undirected()) {
            Graph g_sub = new Graph();
            g_sub = this.subjacent();
            final Set<Integer> id = g_sub.vertex_set.keySet();
            for (final Integer v : id) { // Pesquisar como pegar um id qualquer do set, sem fazer isso
                g_sub.BFS(v);
                break;
            }

            for (final Integer i : id) {
                final Vertex v = g_sub.vertex_set.get(i);
                if (v.dist == null) {
                    return false;
                }
            }

            return true;
        }

        for (final Integer v : this.vertex_set.keySet()) { // Pesquisar como pegar um id qualquer do set, sem fazer isso
            this.BFS(v);
            break;
        }

        for (final Integer j : this.vertex_set.keySet()) {
            final Vertex v = this.vertex_set.get(j);
            if (v.dist == null) {
                return false;
            }
        }
        return true;
    }

    public int count_components() {
        final Queue<Vertex> nao_descobertos = new LinkedList<Vertex>();
        int componentes = 0;

        for (final Integer v : this.vertex_set.keySet()) { // Pesquisar como pegar um id qualquer do set, sem fazer isso
            this.BFS(v);
            break;
        }

        for (final Integer j : this.vertex_set.keySet()) {
            final Vertex v = this.vertex_set.get(j);
            if (v.dist == null) {
                nao_descobertos.add(v);
            }
        }

        while (!nao_descobertos.isEmpty()) {
            final Vertex aux = nao_descobertos.poll();
            if (aux.dist == null) {
                this.BFS(aux.id);
            }
        }

        for (final Integer j : this.vertex_set.keySet()) {
            final Vertex v = this.vertex_set.get(j);
            if (v.dist == 0) {
                componentes++;
            }
        }

        return componentes;

    }

    public void BFS(Integer id_raiz) {

        Vertex raiz = vertex_set.get(id_raiz);
        raiz.dist = 0;
        Queue<Vertex> lista = new LinkedList<Vertex>();
        lista.add(raiz);

        Vertex atual;

        while ((atual = lista.poll()) != null) {
            for (Vertex viz : atual.nbhood.values()) {
                if (viz.dist == null) {
                    viz.discover(atual);
                    lista.add(viz);
                }

            }
        }
    }

    public void DFS(List<Vertex> ordering) {

        if (ordering == null) {
            ordering = new ArrayList<Vertex>();
            ordering.addAll(vertex_set.values());
        }
        acyclic = true;
        for (Vertex v : this.vertex_set.values()) {
            v.parent = null;
        }

        this.tempo = 0;

        for (Vertex v1 : ordering)
            if (v1.d_inicial == null)
                DFS_visit(v1);
    }

    private void DFS_visit(Vertex v) {
        tempo = tempo + 1;
        v.d_inicial = tempo;
        // v_linha são os vertices vizinhos de v
        for (final Vertex v_linha : v.nbhood.values()) {
            if (v_linha.d_inicial == null) {
                v_linha.parent = v;
                this.DFS_visit(v_linha);
            } else if (v_linha.d_inicial < v.d_inicial) {
                acyclic = false;
                // encontrar os vértices que formam esse ciclo
            }
        }

        tempo = tempo + 1;
        v.d_final = tempo;

    }

    public List<Vertex> get_list_roots() {
        List<Vertex> list_roots = new ArrayList<Vertex>();
        for (Vertex v1 : vertex_set.values()) {
            if (v1.parent == null)
                list_roots.add(v1);
        }
        return list_roots;
    }

    public boolean eh_aciclico() {

        if (acyclic != null)
            return acyclic;
        DFS(null);
        return acyclic;
    }

    public List<Vertex> topological_sorting() {
        if (!eh_aciclico()) {
            System.out.printf("\n\n O grafo contém ciclo!!");
            return null;
        }
        List<Vertex> ts_vertex_set = new ArrayList<Vertex>();
        for (Vertex v1 : vertex_set.values())
            ts_vertex_set.add(v1);
        Collections.sort(ts_vertex_set);
        /*
         * System.out.printf("\n\n Ordenação topológica \n"); for ( Vertex v1 :
         * ts_vertex_set ) System.out.printf("\n id: " + v1.id + " f: " + v1.d_final );
         */
        return ts_vertex_set;
    }

    public Digraph reverse() {
        Digraph d2 = new Digraph();
        for (Vertex v11 : this.vertex_set.values()) {
            d2.add_vertex(v11.id);
        }
        for (Vertex v11 : this.vertex_set.values()) {
            for (Vertex v12 : v11.nbhood.values()) {
                Vertex v21 = d2.vertex_set.get(v11.id);
                Vertex v22 = d2.vertex_set.get(v12.id);
                v22.add_neighbor(v21);
            }
        }
        return d2;
    }

    public void CFC() {
        DFS(null);

        List<Vertex> vertex_set1 = new ArrayList<Vertex>();
        for (Vertex v1 : vertex_set.values())
            vertex_set1.add(v1);
        Collections.sort(vertex_set1);

        Digraph d2 = this.reverse();

        List<Vertex> vertex_set2 = new ArrayList<Vertex>();
        for (Vertex v1 : vertex_set1) {
            Vertex v2 = d2.vertex_set.get(v1.id);
            vertex_set2.add(v2);
        }

        d2.DFS(vertex_set2);

        List<Vertex> list_roots = d2.get_list_roots();

        for (Vertex v1 : d2.vertex_set.values()) {
            v1.root = v1.get_root();
            System.out.print("\n v1 " + v1.id + " " + v1.root.id);
        }

        for (Vertex v1 : list_roots) {
            System.out.print("\n Outra CFC: ");
            for (Vertex v2 : d2.vertex_set.values()) {
                if (v2.root == v1)
                    System.out.print(" " + v2.id);
            }
        }
    }

    public boolean is_bipartite() {
        Graph g1 = this.subjacent();
        if (!g1.is_bipartite())
            return false;
        return true;
    }

    private void Initialize_Single_Source(Vertex s) {
        for (Vertex ve1 : vertex_set.values()) {
            // maximum value of int
            ve1.dist = 2147483647;
            ve1.parent = null;
        }

        s.dist = 0;
        // this.vertex_set.get(s.id).dist = 0;

    }

    private void relax(Vertex ve1, Vertex ve2) {
        if (ve1.dist == 2147483647)
            return;
        if (ve2.dist > ve1.dist + ve1.arc_weights.get(ve2.id)) {
            ve2.dist = ve1.dist + ve1.arc_weights.get(ve2.id);
            ve2.parent = ve1;
        }
    }

    public boolean Bellmann_Ford(int id) {
        Vertex s = vertex_set.get(id);
        Initialize_Single_Source(s);

        for (int i = 1; i < vertex_set.size(); i++) // Se conexo O( n + m )
            for (Vertex ve1 : vertex_set.values())
                for (Vertex ve2 : ve1.nbhood.values())
                    relax(ve1, ve2);

        for (Vertex ve1 : vertex_set.values())
            for (Vertex ve2 : ve1.nbhood.values()) {
                if (ve1.dist != 2147483647)
                    if (ve2.dist > ve1.dist + ve1.arc_weights.get(ve2.id))
                        System.out.print("Grafo possui ciclo negativo!");
                        return false;
            }
        return true;
    }

    // Tarefa 08/10 - Como grafo será aciclico, tirar verificação de ciclos
    // negativos e fazer ord. topologica
    // Dag Shortest Path
    public void DSP(int id) { // vertex s = fonte
        List<Vertex> vertex_ord = this.topological_sorting();
        Vertex s = vertex_set.get(id);
        Initialize_Single_Source(s);
        for (Vertex v1 : vertex_ord) {
            for (Vertex v2 : v1.nbhood.values()) {
                relax(v1, v2);
            }
        }
    }

    // Tarefa 08/10 - Implemantar o Dijkstra
    public void Dijkstra(int id) {

        Vertex s = vertex_set.get(id);
        Initialize_Single_Source(s);
        Queue<Vertex> q = new LinkedList<Vertex>();
        q.addAll(vertex_set.values());

        Vertex min = null;

        while (!q.isEmpty()) {

            // Extract_min()
            int aux = 2147483647;
            for (Vertex v1 : q) {
                if (v1.dist <= aux) {
                    aux = v1.dist;
                    min = v1;
                }
            }
            Vertex u = vertex_set.get(min.id);

            for (Vertex v2 : u.nbhood.values()) {
                relax(u, v2);
            }

            q.remove(min);
        }
    }

    // Tarefa 20/10 - Caminhos minimos para todos os pares ( Supomos que não existe nenhum ciclo de peso negativo)
    public void FloydWarshall() { // O(n^3)
        
        // Exemplo do livro
        /*int W[][] = { { 0, 3, 8, 99, -4 }, { 99, 0, 99, 1, 7 }, { 99, 4, 0, 99, 99 }, { 2, 99, -5, 0, 99 },
                { 99, 99, 99, 6, 0 } };*/

        int W [][]= getMatrizAdjcomPesos();
        int n = W.length;
        int[][] dist = new int[n][n];// Will represent the weight of the shortest path from vertex i to vertex j
        int i, j, k;

        dist = W;

        // Floyd-Warshall Algorithm 
        for (k = 0; k < n; k++)
            for (j = 0; j < n; j++)
                for (i = 0; i < n; i++)
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
        printMatrix(dist, n);
    }

    public void printMatrix(int dist[][], int n) {
        System.out.println("New Matrix: ");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (dist[i][j] == 99)
                    System.out.print("I   \t");
                else
                    System.out.print(dist[i][j] + "   \t");
            }
            System.out.println();
        }
    }

    public int[][] getMatrizAdjcomPesos(){
        int n_vertice = this.vertex_set.size();
        
		int adj[][]= new int[n_vertice][n_vertice];

        //inicializa matriz de adjacência
		for (int i = 0; i < n_vertice; i++) {
			for (int j = 0; j < n_vertice; j++) {
                if ( i == j) {
                    adj[i][j] = 0;
                } else {
                    adj[i][j]=99;
                }
			}
        }
        this.compact();
        for ( Vertex v : vertex_set.values() ) {
            int i = v.id;
            for ( Vertex v2 : v.nbhood.values()) {
                int j = v2.id;
                adj[i-1][j-1] = v.arc_weights.get(v2.id);
            }
        }
        
        printMatrix(adj, adj.length);
        return adj;
	}

    public void print() {
		System.out.print("\n\n -------------------------------");
        if( this.vertex_set.size() == 0 ) {
			System.out.printf("\n\n Conjunto de vértices vazio");
			System.out.print("\n\n -------------------------------");
			return;
		}
        System.out.printf("\n\n Grafo, grau máximo %d", this.max_degree());

        if( this.is_undirected() )
            System.out.println("\nNão direcionado");
        else
            System.out.println("\nDirecionado");

        for( Vertex v : vertex_set.values())
            v.print();
		System.out.print("\n\n -------------------------------");
    }

    public void print_dfs() {
        System.out.printf("\n\n Grafo, grau máximo %d", this.max_degree());

        if( this.is_undirected() )
            System.out.println("\n\nNão direcionado");
        else
            System.out.println("\n\nDirecionado");

        for( final Vertex v : vertex_set.values())
            v.print_dfs();
    }
}