import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class AlgGrafos {
    public static void main(String args[]) {
        
        Scanner scan1 = new Scanner(System.in);
        
        String line1 = "\n\n 0 Sair \n 1 Print \n 2 Ler de arquivo \n 3 Escrever em arquivo \n 4 Adicionar vértice";
        String line2 = "\n 5 Adicionar aresta \n 6 Excluir vértice \n 7 BFS \n 8 Subjacente \n 9 Compactar \n 10 É conexo ? \n 11 Conta_componentes \n 12 DFS \n Escolha a opção: ";
        String menu = line1 + line2;
        
        Graph g1 = new Graph();
        
        while( true ) {
            System.out.printf( menu );
            int choice = scan1.nextInt();
            switch( choice ) {
                case 0:
                    return;
                case 1:
                    g1.print();
                    break;
                case 2:
                    g1 = read( );
                    break;
                case 3:
                    write( g1 );
                    break;
                case 4:

                    int quantidade = 0;
                    while ( quantidade <= 0) {
                        String line4 = "\n\n Digite a quantidade de vertices a ser inserido: ";
                        System.out.printf( line4 );
                        quantidade = scan1.nextInt();
                    }
                    
                    int count = 0;
                    while (count < quantidade) {
                        String line4_1 = "\n\n Digite um id válido ( id >= 1 ) e não repetido: ";
                        System.out.printf( line4_1);
                        int id_vertice = scan1.nextInt();
                        if (id_vertice >= 1){
                            if(g1.add_vertex( id_vertice )) {
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
                            g1.add_edge(id_a,id_b);
                            break;
                        case 2:
                            line5 = "\n\nDigite o id dos vertices A e B seguidos e entre espaço: ";
                            System.out.printf( line5 );
                            int p_a = scan1.nextInt();
                            int p_b = scan1.nextInt();
                            g1.add_arc(p_a,p_b);
                            break;
                        case 0:
                            return;

                    }  
                    break;
                case 6:
					System.out.print("Vértice a excluir: ");
					int id = scan1.nextInt();
					g1.del_vertex( id );
					break;
                case 7:
                    System.out.print("Vértice a ser raiz: ");
                    int raiz = scan1.nextInt();
                    if ( g1.vertex_set.containsKey(raiz)){
                        g1.BFS(raiz);
                    } else {
                        System.out.print("Vértice não contido no grafo!");
                    }                  
                    break;
                case 8:
                    Graph g2 = g1.subjacent();
                    g2.print();
                    break;
                case 9:
                    g1.compact();
                    break;
                case 10:
                    if (g1.is_connected()){
                        System.out.println("\n\nGrafo é conexo.");
                    } else {
                        System.out.println("\n\nGrafo é desconexo.");
                    }
                    break;
                case 11:
                    System.out.printf("\n\nComponentes: %d", g1.count_components());
                    break;
                case 12:
                    g1.DFS();
                    g1.print_dfs();
            }
        }
        
    }
    
    private static void write( Graph g1 ) {
        try
        {
            FileOutputStream arquivoGrav = new FileOutputStream("myfiles/saida.dat");
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

    private static Graph read( ) {
        Graph g1 = null;
        try
        {
            FileInputStream arquivoLeitura = new FileInputStream("myfiles/saida.dat");
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
            g1 = (Graph) objLeitura.readObject();
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
