import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;

public class Graph {
    private HashMap<Integer,Vertex> vertex_set;

    public Graph() {
        vertex_set = new HashMap<Integer,Vertex>();
    }
    
    public void add_vertex( int id) {
        if ( this.vertex_set.get(id) == null) {
            Vertex v = new Vertex( id );
            vertex_set.put( v.id, v );
        } else{
            System.out.println("Já existe vértice com esse número");
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
            //System.out.println("Tam: "+this.vertex_set.size());
            Vertex v_remove = vertex_set.get(id);
            HashMap<Integer, Vertex> meus_vizinhos = v_remove.nbhood;
            for (Integer key_id : meus_vizinhos.keySet()) {
                this.vertex_set.get(key_id.intValue()).nbhood.remove(id);
            }
            this.vertex_set.remove(id);
            //System.out.println("Tam: "+this.vertex_set.size());
        } else {
            System.out.printf("Não existe vértice com esse número");
        }
        
    }

    public void compact() {
        int count = 1;
        for (Vertex v : this.vertex_set.values()) {
            Vertex v_replace = new Vertex(count);
            v_replace.nbhood = v.nbhood;
            if ( v.id != count) { // Antes de mudar o id do vertex, muda o id de apontador de meus vizinhos, para mim. 
                for ( Vertex v_aux : this.vertex_set.values()){
                    if(v_aux.nbhood.containsKey(v.id)){
                        v_aux.nbhood.replace(v.id, v_replace);
                    }
                }
            }
            this.vertex_set.replace(v.id, v_replace);
            count++;
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
        
        ArrayList<Integer> comp_encontrada = new ArrayList<>(); 
        Queue<Integer> v_grafo = new LinkedList<>(); 
        Set<Integer> v = vertex_set.keySet();
        
        for ( Integer id : v) {
            v_grafo.add(id);
        }

        comp_encontrada = BFS(v_grafo.remove());

        if ( comp_encontrada.size() == v.size()) {
            return true;
        }
        return false;
    }
    
    public ArrayList<Integer> BFS( Integer id_raiz ) {
        //Vertex raiz = vertex_set.get( id_raiz );
        ArrayList<Integer> vertices_encontrados = new ArrayList<>(vertex_set.size());
        Queue<Integer> fila = new LinkedList<>();
        
        vertices_encontrados.add(id_raiz);
        fila.add(id_raiz); 

        //System.out.println("Elementos encontrados "+ vertices_encontrados); 
        //System.out.println("Elementos da fila "+ fila); 

        while ( !fila.isEmpty() ) {

            //int head = fila.peek(); 
            //System.out.println("Cabeça da fila-"+ head); 
            Integer id_v = fila.remove();

            Vertex v = vertex_set.get( id_v );
            Set<Integer> v_nbhood = v.nbhood.keySet();
            for ( Integer id : v_nbhood) {
                if(!vertices_encontrados.contains(id)){
                    fila.add(id);
                    vertices_encontrados.add(id);
                }
            }

            //System.out.println("Elements encontrados:"+ vertices_encontrados);
            //System.out.println("Elementos da fila "+ fila);  

        }

        return vertices_encontrados;

    }

    public void print() {
        System.out.printf("Grafo, grau máximo %d  \n", this.max_degree());

        if( this.is_undirected() )
            System.out.println("\nNão direcionado");
        else
            System.out.println("\nDirecionado");

        if( this.is_connected() )
            System.out.println("\nConexo");
        else
            System.out.println("\nNão_conexo");

        for ( Vertex v : vertex_set.values()){
            v.print();
            System.out.println();
        }
        /*for(int i = 1; i <= vertex_set.size(); i++)
            vertex_set.get(i).print();
            System.out.println();*/
    }
}

