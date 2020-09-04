import java.util.HashMap;

public class Vertex {
    protected Integer id;
    protected HashMap<Integer,Vertex> nbhood;

    public Vertex ( int id ) {
        this.id = id;
        nbhood = new HashMap<Integer,Vertex>();
    }
    
    public void add_neighbor( Vertex viz ) {
        nbhood.put(viz.id, viz);
    }

    public int grau_saida () {
        // grau de saída se direcionado
        int valor = this.nbhood.size();
        return valor;
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
        System.out.print("\nId do vertice = " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id );
    }
}