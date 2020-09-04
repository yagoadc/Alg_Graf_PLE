public class AlgGrafos {
    public static void main(String args[]) {
        
        Graph g1 = new Graph();
        g1.add_vertex(1);
        g1.add_vertex(2);
        g1.add_vertex(3);
        g1.add_vertex(4);
        g1.add_vertex(5);
        g1.add_edge(1,2);
        g1.add_edge(1,3);
        g1.add_edge(3,4);
        g1.add_edge(4,2);
        g1.add_arc(4,5);
        //g1.add_arc(5,4);
        g1.print();
        Graph g2 = g1.subjacent();
        g2.print();
       // System.out.println("Grau máximo: " + g1.max_degree());
        //System.out.println("É não direcionado?: " + g1.is_undirected());
       // System.out.println("BFS: " + g1.BFS(4));
       // System.out.println("É conexo?: " + g1.is_connected());
        //g1.subjacent();
        //System.out.println("É não direcionado?: " + g1.is_undirected());
        g2.del_vertex(4);
        //g1.del_vertex(3);
        g2.print();
        g2.compact();
        g2.print();
        
    }
}
