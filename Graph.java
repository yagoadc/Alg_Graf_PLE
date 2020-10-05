import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.Stack;

public class Graph extends Digraph {

	private Stack<Vertex> st1;
	
	public Graph() {
		st1 = new Stack<Vertex>( );
    }

    @Override public void add_arc( Integer id1, Integer id2) {
		System.out.println("Operação não permitida: Adição de arco.");
     }
    
	@Override public boolean eh_aciclico( ) {
		// fazer
		return true;
	}

	// Verificar se precisa melhorar o bipartido do Digrafo com esse abaixo.

	/*@Override public boolean is_bipartite( ) {
		for( Vertex v1 : vertex_set.values( ) ) {
			if( v1.ind_set == 0 ) {
				v1.ind_set = 1;
				if( ! bipartite_visit( v1 ) )
					return false;
			}
		}
		System.out.print( "\n É bipartido " );
		return true;
	}*/

	/*private boolean bipartite_visit( Vertex v1 ) {
		for( Vertex v2 : v1.nbhood.values( ) ) {
			if( v2.ind_set == 0 ) {
				v2.ind_set = - v1.ind_set;
				if( ! bipartite_visit( v2 ) ) 
					return false;
			}
			if( v2.ind_set == v1.ind_set ) {
				// encontrar um ciclo ímpar
				System.out.printf( "\n Não é bipartido " + v1.id + " " + v2.id );
				return false;
			}
		}
		return true;
	}*/

	public void bicon_comp( ) {
		tempo = 0;
		for( Vertex v1 : vertex_set.values( ) ) {
			if( v1.dist == null ) {				
				bicon_comp_visit( v1 );
				if (v1.nbhood.size() > 1) {
					System.out.printf(" A raiz %d é uma articulação", v1.id);
				}
			}
		}

	}

    private void bicon_comp_visit( Vertex v1 ) {
		v1.dist = ++tempo;
		v1.low = v1.dist;
		for( Vertex neig : v1.nbhood.values( ) ) {
			if( neig.dist == null ) {
				st1.push( v1 );
				st1.push( neig );
				neig.parent = v1;
				bicon_comp_visit( neig );
				if( neig.low >= v1.dist )
					this.desempilha( v1, neig );
				if( neig.low < v1.low )
					v1.low = neig.low;
			}
			else if( neig != v1.parent ) {
				if( neig.dist < v1.dist ) {
					st1.push( v1 );
					st1.push( neig );
				}
			}
		}
	}

	private void desempilha( Vertex cut_vertex, Vertex aux ) {
		if( st1.empty( ) )
			return;
		System.out.println( "\n Bloco: " );
		Vertex v1 = this.st1.pop( );
		Vertex v2 = this.st1.pop( );
		System.out.println( v1.id );
		System.out.print( v2.id );
		while( v1 != cut_vertex || v2 != aux ) {
			if( st1.empty( ) )
				return;
			v1 = this.st1.pop( );
			v2 = this.st1.pop( );
			System.out.println( v1.id );
			System.out.print( v2.id );
		}
	}

}
