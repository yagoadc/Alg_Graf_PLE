import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.List;

public class AlgGrafos {

    static Digraph dg1;
    static int inf = 2147483647;

    public static void main(String args[ ]) {
        dg1 = new Digraph();
        main_menu( );
	}	
    public static void main_menu() {
        
        Scanner scan1 = new Scanner(System.in);
        
        String line1 = "\n\n 0 Sair \n 1 Print \n 2 Ler de arquivo \n 3 Escrever em arquivo \n 4 Adicionar vértice";
        String line2 = "\n 5 Adicionar aresta \n 6 Excluir vértice \n 7 Entrada arquivo texto \n 8 Algoritmos \n Escolha a opção: "; 
       
        String menu = line1 + line2;

        boolean goon = true;
        
        while( goon ) {
            System.out.printf( menu );
            int choice = scan1.nextInt();
            switch( choice ) {
                case 0:
                    goon = false;
                    break;
                case 1:
                    dg1.print();
                    break;
                case 2:
                    dg1 = read( );
                    break;
                case 3:
                    write( dg1 );
                    break;
                case 4:

                    int quantidade = 0;
                    while ( quantidade <= 0) {
                        String line_c4 = "\n\n Digite a quantidade de vertices a ser inserido: ";
                        System.out.printf( line_c4 );
                        quantidade = scan1.nextInt();
                    }
                    
                    int count = 0;
                    while (count < quantidade) {
                        String line4_1 = "\n\n Digite um id válido ( id >= 1 ) e não repetido: ";
                        System.out.printf( line4_1);
                        int id_vertice = scan1.nextInt();
                        if (id_vertice >= 1){
                            if(dg1.add_vertex( id_vertice )) {
                                System.out.printf( " Vértice %d adicionado. ", id_vertice );
                                count++;
                            }    
                        } else {
                            System.out.println( "Id não válido!" );
                        }
                    }
                    break;
                    
                case 5:
                    String line5 = "\n\n 1 Adiciona aresta \n 2 Adiciona arco \n 3 Adiciona arco com pesos \n 0 Sair \n Escolha a opção: ";
                    System.out.printf( line5 );
                    int opcao = scan1.nextInt();
                    switch (opcao) {
                        case 1:
                            line5 = "\n\nDigite o id dos vertices A e B seguidos e entre espaço: ";
                            System.out.printf( line5 );
                            int id_a = scan1.nextInt();
                            int id_b = scan1.nextInt();
                            dg1.add_edge(id_a,id_b);
                            break;
                        case 2:
                            line5 = "\n\nDigite o id dos vertices A e B seguidos e entre espaço: ";
                            System.out.printf( line5 );
                            int p_a = scan1.nextInt();
                            int p_b = scan1.nextInt();
                            dg1.add_arc(p_a,p_b);
                            break;
                       
                        case 3:  // IMPLEMENTAR case arco com pesos. FALTA ADEQUADAR LEITURA DE ARQUIVO PARA ARCOS COM PESOS
                            // Exemplo Dijkstra
                            /*dg1.add_vertex( 1 );
                            dg1.add_vertex( 2 );
                            dg1.add_vertex( 3 );
                            dg1.add_vertex( 4 );
                            dg1.add_vertex( 5 );                     
                            dg1.add_arc_w(1,2,10);
                            dg1.add_arc_w(1,3,5);
                            dg1.add_arc_w(2,3,2);
                            dg1.add_arc_w(2,4,1);
                            dg1.add_arc_w(3,2,3);
                            dg1.add_arc_w(3,5,2);
                            dg1.add_arc_w(3,4,9);
                            dg1.add_arc_w(4,5,4);
                            dg1.add_arc_w(5,1,7);
                            dg1.add_arc_w(5,4,6);*/
                            // Exemplo DSP
                            dg1.add_vertex( 1 );
                            dg1.add_vertex( 2 );
                            dg1.add_vertex( 3 );
                            dg1.add_vertex( 4 );
                            dg1.add_vertex( 5 );
                            dg1.add_vertex( 6 );
                            dg1.add_arc_w(1,2,5);
                            dg1.add_arc_w(1,3,3);
                            dg1.add_arc_w(2,3,2);
                            dg1.add_arc_w(2,4,6);
                            dg1.add_arc_w(3,4,7);
                            dg1.add_arc_w(3,5,4);
                            dg1.add_arc_w(3,6,2);
                            dg1.add_arc_w(4,5,-1);
                            dg1.add_arc_w(4,6,1);
                            dg1.add_arc_w(5,6,-2);
                            break;
                        case 4:
                            dg1.add_vertex( 1 );
                            dg1.add_vertex( 2 );
                            dg1.add_vertex( 3 );
                            dg1.add_vertex( 4 );
                            dg1.add_vertex( 5 );
                            dg1.add_arc_w(1,2,3);
                            dg1.add_arc_w(1,3,8);
                            dg1.add_arc_w(1,5,-4);
                            dg1.add_arc_w(2,4,1);
                            dg1.add_arc_w(2,5,7);
                            dg1.add_arc_w(3,2,4);
                            dg1.add_arc_w(4,1,2);
                            dg1.add_arc_w(4,3,-5);
                            dg1.add_arc_w(5,4,6);
                            dg1.FloydWarshall();
                            break;
                        case 0:
                            return;

                    }  
                    break;
                case 6:
					System.out.print("Vértice a excluir: ");
					int id = scan1.nextInt();
					dg1.del_vertex( id );
                    break;
                case 7:
					FileGraph fg1 = new FileGraph( );
					dg1 = fg1.open_text( );
                    break;
                case 8:
					algorithms( );
                
                }
            }   
        
    }

    private static void algorithms( ) {
        Scanner scan1 = new Scanner(System.in);
 
        String line1 = "\n\n 0 Sair \n 1 Print \n 7 BFS \n 8 Subjacente \n 9 Compactar";
        String line2 =  "\n 10 É conexo ? \n 11 Conta_componentes \n 12 DFS \n 13 Ordenação Topologica ";
        String line3 =  "\n 14 Reverter arcos \n 15 CFC \n 16 É bipartido?\n 17 Comp. biconexas \n 18 Bellman Ford ";
        String line4 = " \n 19 DSP \n 20 Dijkstra \n Escolha a opção: ";
        String menu = line1 + line2 + line3 + line4;

        boolean goon = true;
 
        while( goon ) {
            System.out.printf( menu );
            int choice = scan1.nextInt();
            switch( choice ) {
                case 0:
  					goon = false;
                    break;
                case 1:
                    dg1.print();
                    break;
                case 7:
                    System.out.print("Vértice a ser raiz: ");
                    int raiz = scan1.nextInt();
                    if ( dg1.vertex_set.containsKey(raiz)){
                        dg1.BFS(raiz);
                    } else {
                        System.out.print("Vértice não contido no grafo!");
                    }                  
                    break;

                case 8:
                    Graph g2 = dg1.subjacent();
                    g2.print();
                    break;

                case 9:
					dg1.compact( );
                    break;
                    
                case 10:
                    if (dg1.is_connected()){
                        System.out.println("\n\nGrafo é conexo.");
                    } else {
                        System.out.println("\n\nGrafo é desconexo.");
                    }
                    break;

                case 11:
                    System.out.printf("\n\nComponentes: %d", dg1.count_components());
                    break;

                case 12:
                    dg1.DFS(null);
                    //g1.print_dfs();
                    break;

                case 13:
                    List<Vertex> ts_vertex_set = dg1.topological_sorting( );
                    System.out.printf("\n\n Ordenação topológica \n");
                    for ( Vertex v1 : ts_vertex_set )
                        System.out.printf("\n id: " + v1.id + " f: " + v1.d_final );
                    break;

                case 14:
                    Digraph d2 = dg1.reverse( );
                    d2.print( );		
                    break;

                case 15:
                   dg1.CFC( );
                   break;

                case 16:
                    /*if ( dg1.eh_bipartido( dg1 ) ) {
                        System.out.println("\n\nGrafo é bipartido.");
                    } else {
                        System.out.println("\n\nGrafo não é bipartido.");
                    }*/
                    dg1.is_bipartite( );
                    break;

                case 17:
                    g2 = dg1.subjacent( );
                    g2.bicon_comp( );
                    break;

                case 18:
                   dg1.Bellmann_Ford( 1 );
                   break;
                case 19:
                   dg1.DSP( 2 ); // Dag Shortest Path
                   break;
                case 20:
                    dg1.Dijkstra(1);
              }
         }
	} 
    
    private static void write( Digraph g1 ) {
        try
        {
            FileOutputStream arquivoGrav = new FileOutputStream("C:/Users/Yago/Desktop/UFRJ/ALG GRAF/Alg_Graf_PLE/myfiles/saida.dat");
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject( g1 );
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();
            System.out.println("Objeto gravado com sucesso!");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static Digraph read( ) {
        Digraph g1 = null;
        try
        {
            FileInputStream arquivoLeitura = new FileInputStream("C:/Users/Yago/Desktop/UFRJ/ALG GRAF/Alg_Graf_PLE/myfiles/saida.dat");
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
            g1 = (Digraph) objLeitura.readObject();
            objLeitura.close();
            arquivoLeitura.close();
            System.out.println("Objeto recuperado: ");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return g1;
    }
}
