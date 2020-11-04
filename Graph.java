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
	private List<Vertex> part_A, part_B; //particoes A e B
	
	public Graph() {
		st1 = new Stack<Vertex>( );
		part_A = new ArrayList<Vertex>();
		part_B = new ArrayList<Vertex>();
    }

    @Override public void add_arc( Integer id1, Integer id2) {
		System.out.println("Operação não permitida: Adição de arco.");
     }
    
	@Override public boolean eh_aciclico( ) {
		// fazer
		return true;
	}

	// Verificar se precisa melhorar o bipartido do Digrafo com esse abaixo.
	@Override public boolean is_bipartite( ) {
		for( Vertex v1 : vertex_set.values( ) ) {
			if( v1.ind_set == 0 ) {
				v1.ind_set = 1;
				if( ! bipartite_visit( v1 ) )
					return false;
			}
		}
		System.out.print( "\n É bipartido \n" );
		return true;
	}

	private boolean bipartite_visit( Vertex v1 ) {
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
	}

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

	////////////////////  TRABALHO 2 - EMPARELHAMENTO MAXIMO //////////////////////////////////////////////////

	boolean acha_caminho_aumento( Vertex u ) {
		
		for ( Vertex v : u.nbhood.values() ) {
			
			if( v.seen) continue;

			v.seen = true;

			if ( v.emparelhado == null || acha_caminho_aumento( v.emparelhado) ) {
				u.emparelhado = v;
				v.emparelhado = u;
				return true;
			}
		}
		return false;
	}

	public void emp_max () { // O(V^2 + VE)
		
		for ( Vertex v : vertex_set.values()) { // Após passar pelo is_bipartite() , insere vertices nas partições distintas A e B.
			if ( v.ind_set == 1 ) {
				this.part_A.add(v);
			} else {
				part_B.add(v);
			}
		}

		int max_matching = 0;

		for ( Vertex v : part_A ) {
			if ( v.emparelhado == null) { // Se true, então v é um vertice livre.
				if ( acha_caminho_aumento(v))
					max_matching++;
			}
		}		

		System.out.printf("\n O tamanho do emparelhamento maximo = %d\n", max_matching);
		for (Vertex v : part_A) {
			if (v.emparelhado != null) System.out.printf("\n %d está emparelhado com %d\n", v.id, v.emparelhado.id);
		}
	}
	
	// A seguir, o algoritimo de Hopcroft_karp
	// Ele é mais eficiente, pois encontra um conjunto maximal de caminhos de aumentos. E em vez de aumentar 
	// o emparelhamento um por um, ele usa o conjunto para aumentar.  
	
	public Integer acha_caminhos_de_aumento() { // BFS adaptado

		Queue<Vertex> lista = new LinkedList<Vertex>();
		Integer k;

		k = 99999999; // Valor de k inicial representa infinito;

		for ( Vertex u : this.part_A ) {
			if ( u.emparelhado == null ) { // Se vertice u for livre, adiciono ele na lista 
				u.d = 0;
				lista.add(u);
			} else {
				u.d = 99999999;
			}
		}

		Vertex atual;
		while ( !lista.isEmpty() ) {
			atual = lista.poll();
			if ( atual.d < k) {
				for ( Vertex v : atual.nbhood.values()){
					if ( v.emparelhado == null && k == 99999999) {
						k = atual.d + 1;
					} else if ( v.emparelhado != null){  
						if ( v.emparelhado.d == 99999999 ) { //Se o vertice adj à v não estiver descoberto, insiro ele na fila.
							v.emparelhado.d = atual.d + 1;
							lista.add(v.emparelhado);
						}
					}
				}
			}
		}
		/*if ( k != 999999999 ){
			return k;
		}*/
		return k;
	}

	public boolean aumenta_emparelhamento ( Vertex v)  { // DFS adaptado
		for ( Vertex u : v.nbhood.values() ) {

			if ( u.emparelhado == null) { // condição de parada
				v.emparelhado = u;
				u.emparelhado = v;
				return true;
			}

			if ( v.emparelhado != null) {
				if (u.emparelhado.d == (v.emparelhado.d + 1)) {
					if ( aumenta_emparelhamento(u.emparelhado) ) {
						v.emparelhado = u;
						u.emparelhado = v;
						return true;
					}
				}
			}
			
		}

		// Se chegar aqui, significa que a partir de v, não consigo achar um caminho de aumento disjunto.
		v.d = 99999999; 
		return false;
	}
	
	public void hopcroft_karp( ) {   // O(RAIZ(V)(V+E))

		for ( Vertex v : vertex_set.values()) { // Após passar pelo is_bipartite() , insere vertices nas partições distintas A e B.
			if ( v.ind_set == 1 ) {
				this.part_A.add(v);
			} else {
				part_B.add(v);
			}
		}
	
		int max_matching = 0;
		while (acha_caminhos_de_aumento() != 99999999 ) {
			for (Vertex v : this.part_A)
				if ( v.emparelhado == null && aumenta_emparelhamento(v))
					max_matching++;
		}
		
		System.out.printf("\n Tamanho do emparelhamento maximo: %d\n", max_matching);
		
		for (Vertex v : this.part_A) {
			if (v.emparelhado != null){
			 System.out.printf("\n %d está emparelhado com %d\n", v.id, v.emparelhado.id);
			}
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
