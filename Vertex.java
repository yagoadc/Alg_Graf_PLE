import java.util.HashMap;
import java.io.Serializable;
import java.lang.Comparable;

public class Vertex  implements Serializable, Comparable<Vertex> {
    protected Integer id;
    protected HashMap<Integer,Vertex> nbhood;
    protected HashMap<Integer,Integer> arc_weights;
    // parent: refere-se a qual busca?
    protected Vertex parent, root;
    protected Integer dist, d_inicial, d_final, cor, low;
    // independent set if bipartite: 0, -1, or 1
    protected int ind_set;

    public Vertex ( int id ) {
        this.id = id;
        nbhood = new HashMap<Integer,Vertex>();
        arc_weights = new HashMap<Integer,Integer>( );

        parent = root = null;
        dist = null;
        d_inicial = null;
        d_final = null;
        cor = -1;
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

    protected void add_weight( Integer id_nb, Integer weight ) {
        arc_weights.put( id_nb, weight );
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

    protected Vertex get_root( ) {
		if( parent == null )
			root = this;
		else
			root = parent.get_root( );
		return root;
	}

    public void print() {
        System.out.print("\nId do vértice " + id + ", Cor: " + this.cor +", Vizinhança: " );
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