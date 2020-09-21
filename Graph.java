import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;

public class Graph extends Digraph {
  
    @Override public void add_arc( Integer id1, Integer id2) {
		System.out.println("Operação não permitida: Adição de arco.");
     }
    
	@Override public boolean eh_aciclico( ) {
		// fazer
		return true;
	}
}
