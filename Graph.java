import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;

public class Graph {
    protected HashMap<Integer,Vertex> vertex_set;
    protected Integer tempo;

    public Graph() {
        vertex_set = new HashMap<Integer,Vertex>();
    }
    
    public boolean add_vertex( final int id) {
        if ( this.vertex_set.get(id) == null) {
            final Vertex v = new Vertex( id );
            vertex_set.put( v.id, v );
            return true;
        } else{
            System.out.println("Já existe vértice com esse número");
            return false;
        }
    
    }
 
    public void add_arc( final Integer id1, final Integer id2) {
        final Vertex v1 = vertex_set.get(id1);
        final Vertex v2 = vertex_set.get(id2);
        v1.add_neighbor( v2 );
    }

    public void add_edge( final Integer id1, final Integer id2) {
        final Vertex v1 = vertex_set.get(id1);
        final Vertex v2 = vertex_set.get(id2);
        v1.add_neighbor( v2 );
        v2.add_neighbor( v1 );

// ou
//        add_arc( id1, id2 );
//        add_arc( id2, id1 );
    }

    public void del_vertex ( final int id ) {
        if ( vertex_set.containsKey(id)) {
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
		final int [ ] present = new int[n+1];
		final Vertex [ ] stranges = new Vertex[n];
		for( int i = 1; i <= n; i++) {
			present[ i ] = 0;
		}
		int qst = 0;
        for( final Vertex v1 : vertex_set.values() ) {
			if( v1.id <= n )
				present[ v1.id ] = 1;
			else
				stranges[ qst++ ] = v1;	
		}
		int i = 1;
		for( int pairs = 0; pairs < qst; i++ ) {
			if( present[ i ] == 0)		
				present[ pairs++ ] = i;
		}
		for( i = 0; i < qst; i++) {
			final int old_id = stranges[ i ].id;
			stranges[ i ].id = present[ i ];
			for( final Vertex v1 : vertex_set.values() ) {
				if( v1.nbhood.get( old_id ) != null ) {
					v1.nbhood.remove( old_id );
					v1.nbhood.put( stranges[ i ].id, stranges[ i ] );
				}
			}
		}
    }

    public int max_degree() { // Grau do vértice que possui maior número de arestas incidentes a ele.
        int max = -1;
        int aux = 0;
        for(final Vertex v : vertex_set.values()){
            aux = v.grau_saida();
            if (aux > max){
                max = aux;
            }
        }
        return max;
    }

    public boolean is_undirected() {
        for( final Vertex v1 : vertex_set.values()) {
            for( final Vertex v2 : v1.nbhood.values()) {
                if (v2.nbhood.get(v1.id) == null)
                    return false;
            }
        }
        return true;
    }

    public Graph subjacent() {
        final Graph g2 = new Graph();
        for( final Vertex v11 : this.vertex_set.values()) {
            g2.add_vertex( v11.id );
        }
        for( final Vertex v11 : this.vertex_set.values()) {
            for( final Vertex v12 : v11.nbhood.values()) {
                final Vertex v21 = g2.vertex_set.get( v11.id );
                final Vertex v22 = g2.vertex_set.get( v12.id );
                v21.add_neighbor( v22 );
                v22.add_neighbor( v21 );
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
            for ( final Integer v : id ) { // Pesquisar como pegar um id qualquer do set, sem fazer isso 
                g_sub.BFS(v);
                break;
            }

            for (  final Integer i  : id ) {
                final Vertex v = g_sub.vertex_set.get(i);
                if ( v.dist == null) {
                    return false;
                }
            }

            return true;
        }

        for ( final Integer v : this.vertex_set.keySet() ) { // Pesquisar como pegar um id qualquer do set, sem fazer isso
            this.BFS(v);
            break;
        }

        for (  final Integer j  : this.vertex_set.keySet() ) {
            final Vertex v = this.vertex_set.get(j);
            if ( v.dist == null) {
                return false;
            }
        }
        return true;
    }

    public int count_components() {
        final Queue<Vertex> nao_descobertos = new LinkedList<Vertex>();
        int componentes = 0;
        
        for ( final Integer v : this.vertex_set.keySet() ) { // Pesquisar como pegar um id qualquer do set, sem fazer isso 
            this.BFS(v);
            break;
        }

        for (  final Integer j  : this.vertex_set.keySet() ) {
            final Vertex v = this.vertex_set.get(j);
            if ( v.dist == null) {
                nao_descobertos.add(v);
            }
        }

        while ( !nao_descobertos.isEmpty()) {
            final Vertex aux = nao_descobertos.poll();
            if ( aux.dist == null ) {
                this.BFS(aux.id); 
            }     
        }

        for (  final Integer j  : this.vertex_set.keySet() ) {
            final Vertex v = this.vertex_set.get(j);
            if ( v.dist == 0) {
                componentes++; 
            }
        }
        
        return componentes;
        
	}
    
    public void BFS( final Integer id_raiz ) {

        final Vertex raiz = vertex_set.get( id_raiz );
        raiz.dist = 0;

        final Queue<Vertex> lista = new LinkedList<Vertex>();
        lista.add( raiz );

        Vertex atual;

        while ((atual = lista.poll()) != null) {
            for( final Vertex viz : atual.nbhood.values() ) {
                if( viz.dist == null ) {
                    viz.discover( atual );
                    lista.add( viz );
                }
            }
        }
    }

    public void DFS( ) {
        
        for ( final Vertex v : this.vertex_set.values()) {
            v.parent = null;
        }
        
        this.tempo = 0;

        for ( final Vertex v1 : this.vertex_set.values()) {
            if ( v1.d_inicial == null) {
                this.DFS_vist(v1);
            }
        }

    }

    public void DFS_vist( final Vertex v ) {

        tempo = tempo + 1;

        v.d_inicial = tempo;

        for ( final Vertex v_linha : v.nbhood.values()){
            if ( v_linha.d_inicial == null) {
                v_linha.parent = v;
                this.DFS_vist(v_linha);
            }
        }

        tempo = tempo + 1;
        v.d_final = tempo;

    }

    public boolean eh_aciclico () {
        
        this.DFS();

        for ( final Vertex v : this.vertex_set.values() ) {
            for ( final Vertex v_linha : v.nbhood.values()){
                 if (v_linha.d_final > v.d_final ) {
                     return false;
                 }
            }
        }

        return true;
    }

    // Melhorar os print do grafo e também vértices
    public void print() {
        System.out.printf("\n\n Grafo, grau máximo %d", this.max_degree());

        if( this.is_undirected() )
            System.out.println("\n\nNão direcionado");
        else
            System.out.println("\n\nDirecionado");

        for( final Vertex v : vertex_set.values())
            v.print();
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