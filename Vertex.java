import java.util.HashMap;
import java.io.Serializable;

public class Vertex {
    protected Integer id;
    protected HashMap<Integer,Vertex> nbhood;
    protected Vertex parent;
    protected Integer dist;
    protected Integer d_inicial;
    protected Integer d_final;

    public Vertex ( int id ) {
        this.id = id;
        nbhood = new HashMap<Integer,Vertex>();
        parent = null;
        dist = null;
        d_inicial = null;
        d_final = null;
    }
    
    public void add_neighbor( Vertex viz ) {
        nbhood.put(viz.id, viz);
    }

    public int grau_saida () {
        // grau de saída se direcionado
        int valor = this.nbhood.size();
        return valor;
    }

    public void discover( Vertex parent ) {
        this.parent = parent;
        this.dist = parent.dist + 1;
    }

    /*public Set<Integer> list_nbhood () {
        return this.nbhood.keySet();
    }*/

   /* public boolean is_nbhood ( int id ) {
        boolean bool = true;
        
        for( Vertex v : this.nbhood.values()){
            bool = v.nbhood.containsKey(id);
            if ( bool == false) {
                return bool;
            }

        }
        
        return bool; 
    }*/

   /* public boolean is_my_nbhood ( int id ) {
        boolean bool = true;   
        bool = this.nbhood.containsKey(id);
        if ( bool == false) {
            return bool;
        }
        return bool; 
    }*/
    
    public void print() {
        System.out.print("\nId do vértice " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id );
        if( parent != null)
            System.out.print(", pai " + parent.id + " distância " + dist );
        else if ( dist == null )
            System.out.print(", não alcançável pela raiz");
        else
            System.out.print(", raiz, distância" + dist);
    }

    public void print_dfs() {
        System.out.print("\nId do vértice " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id );
        if( parent != null)
            System.out.print(", pai " + parent.id + " d " + d_inicial + " f " + d_final );
        else
            System.out.print(", raiz, d " + d_inicial + " f " + d_final );
    }
}