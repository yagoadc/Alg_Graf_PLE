import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;

public class Graph {
    protected HashMap<Integer,Vertex> vertex_set;

    public Graph() {
        vertex_set = new HashMap<Integer,Vertex>();
    }
    
    public boolean add_vertex( int id) {
        if ( this.vertex_set.get(id) == null) {
            Vertex v = new Vertex( id );
            vertex_set.put( v.id, v );
            return true;
        } else{
            System.out.println("Já existe vértice com esse número");
            return false;
        }
    
    }
 
    public void add_arc( Integer id1, Integer id2) {
        Vertex v1 = vertex_set.get(id1);
        Vertex v2 = vertex_set.get(id2);
        v1.add_neighbor( v2 );
    }

    public void add_edge( Integer id1, Integer id2) {
        Vertex v1 = vertex_set.get(id1);
        Vertex v2 = vertex_set.get(id2);
        v1.add_neighbor( v2 );
        v2.add_neighbor( v1 );

// ou
//        add_arc( id1, id2 );
//        add_arc( id2, id1 );
    }

    public void del_vertex ( int id ) {
        if ( vertex_set.containsKey(id)) {
            Vertex v_remove = vertex_set.get(id);
            HashMap<Integer, Vertex> meus_vizinhos = v_remove.nbhood;
            for (Integer key_id : meus_vizinhos.keySet()) {
                this.vertex_set.get(key_id.intValue()).nbhood.remove(id);
            }
            this.vertex_set.remove(id);
        } else {
            System.out.printf("Não existe vértice com essa id");
        }
        
    }
    // Assistir gravação aula (03/09), melhorar o for
    public void compact() {
		int n = vertex_set.size();
		int [ ] present = new int[n+1];
		Vertex [ ] stranges = new Vertex[n];
		for( int i = 1; i <= n; i++) {
			present[ i ] = 0;
		}
		int qst = 0;
        for( Vertex v1 : vertex_set.values() ) {
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
			int old_id = stranges[ i ].id;
			stranges[ i ].id = present[ i ];
			for( Vertex v1 : vertex_set.values() ) {
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
        for(Vertex v : vertex_set.values()){
            aux = v.grau_saida();
            if (aux > max){
                max = aux;
            }
        }
        return max;
    }

    public boolean is_undirected() {
        for( Vertex v1 : vertex_set.values()) {
            for( Vertex v2 : v1.nbhood.values()) {
                if (v2.nbhood.get(v1.id) == null)
                    return false;
            }
        }
        return true;
    }

    public Graph subjacent() {
        Graph g2 = new Graph();
        for( Vertex v11 : this.vertex_set.values()) {
            g2.add_vertex( v11.id );
        }
        for( Vertex v11 : this.vertex_set.values()) {
            for( Vertex v12 : v11.nbhood.values()) {
                Vertex v21 = g2.vertex_set.get( v11.id );
                Vertex v22 = g2.vertex_set.get( v12.id );
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
            Set<Integer> id = g_sub.vertex_set.keySet();
            for ( Integer v : id ) { // Pesquisar como pegar um id qualquer do set, sem fazer isso 
                g_sub.BFS(v);
                break;
            }

            for (  Integer i  : id ) {
                Vertex v = g_sub.vertex_set.get(i);
                if ( v.dist == null) {
                    return false;
                }
            }

            return true;
        }

        for ( Integer v : this.vertex_set.keySet() ) { // Pesquisar como pegar um id qualquer do set, sem fazer isso
            this.BFS(v);
            break;
        }

        for (  Integer j  : this.vertex_set.keySet() ) {
            Vertex v = this.vertex_set.get(j);
            if ( v.dist == null) {
                return false;
            }
        }
        return true;
    }

    public int count_components() {
        Queue<Vertex> nao_descobertos = new LinkedList<Vertex>();
        int componentes = 0;
        
        for ( Integer v : this.vertex_set.keySet() ) { // Pesquisar como pegar um id qualquer do set, sem fazer isso 
            this.BFS(v);
            break;
        }

        for (  Integer j  : this.vertex_set.keySet() ) {
            Vertex v = this.vertex_set.get(j);
            if ( v.dist == null) {
                nao_descobertos.add(v);
            }
        }

        while ( !nao_descobertos.isEmpty()) {
            Vertex aux = nao_descobertos.poll();
            if ( aux.dist == null ) {
                this.BFS(aux.id); 
            }     
        }

        for (  Integer j  : this.vertex_set.keySet() ) {
            Vertex v = this.vertex_set.get(j);
            if ( v.dist == 0) {
                componentes++; 
            }
        }
        
        return componentes;
        
	}
    
    public void BFS( Integer id_raiz ) {

        Vertex raiz = vertex_set.get( id_raiz );
        raiz.dist = 0;

        Queue<Vertex> lista = new LinkedList<Vertex>();
        lista.add( raiz );

        Vertex atual;

        while ((atual = lista.poll()) != null) {
            for( Vertex viz : atual.nbhood.values() ) {
                if( viz.dist == null ) {
                    viz.discover( atual );
                    lista.add( viz );
                }
            }
        }
    }

    public void print() {
        System.out.printf("\n\n Grafo, grau máximo %d", this.max_degree());

        if( this.is_undirected() )
            System.out.println("\n\nNão direcionado");
        else
            System.out.println("\n\nDirecionado");

        for( Vertex v : vertex_set.values())
            v.print();
    }
}

