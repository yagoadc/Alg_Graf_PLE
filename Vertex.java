import java.util.HashMap;
import java.io.Serializable;
import java.lang.Comparable;

public class Vertex  implements Serializable, Comparable<Vertex> {
    protected Integer id;
    protected HashMap<Integer,Vertex> nbhood;
    protected Vertex parent;
    protected Integer dist, d_inicial, d_final ;

    public Vertex ( int id ) {
        this.id = id;
        nbhood = new HashMap<Integer,Vertex>();
        parent = null;
        dist = null;
        d_inicial = null;
        d_final = null;
    }

    @Override public int compareTo( Vertex otherVertex ) {
		if( otherVertex.d_final > this.d_final)
			return 1;
		else
			return -1;
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

    public void print() {
        System.out.print("\nId do vértice " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id );
        if( d_inicial != null )
			System.out.print(". d " + d_final + ", f " + d_final );
        if( parent != null)
            System.out.print(". Pai " + parent.id + ", distância " + dist );
        else if ( dist == null )
            System.out.print(". Não alcançável pela raiz");
        else
            System.out.print(". Raiz, distância" + dist);
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