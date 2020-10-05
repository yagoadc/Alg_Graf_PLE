import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.List;

public class AlgGrafos {
    public static void main(String args[]) {
        
        Scanner scan1 = new Scanner(System.in);
        
        String line1 = "\n\n 0 Sair \n 1 Print \n 2 Ler de arquivo \n 3 Escrever em arquivo \n 4 Adicionar vértice";
        String line2 = "\n 5 Adicionar aresta \n 6 Excluir vértice \n 7 BFS \n 8 Subjacente \n 9 Compactar"; 
        String line3 = "\n 10 É conexo ? \n 11 Conta_componentes \n 12 DFS \n 13 Ordenação Topologica ";
        String line4 = "\n 14 Reverter arcos \n 15 CFC \n 16 É bipartido?\n 17 Comp. biconexas \n 18 Entrada arquivo texto \n Escolha a opção: ";
        String menu = line1 + line2 + line3 + line4;
        
        //Digraph g1 = new Digraph();
        Digraph dg1 = new Digraph();
        
        while( true ) {
            System.out.printf( menu );
            int choice = scan1.nextInt();
            switch( choice ) {
                case 0:
                    return;
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
                    String line5 = "\n\n 1 Adiciona aresta \n 2 Adiciona arco \n 0 Sair \n Escolha a opção: ";
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
                    dg1.compact();
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
                case 16:
                    if ( dg1.eh_bipartido( dg1 ) ) {
                        System.out.println("\n\nGrafo é bipartido.");
                    } else {
                        System.out.println("\n\nGrafo não é bipartido.");
                    }
                    break;
                case 17:
                    g2 = dg1.subjacent( );
                    g2.bicon_comp( );
                    break;
                case 18:
					FileGraph fg1 = new FileGraph( );
					dg1 = fg1.open_text( );
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
