

package shortestpath;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;




/**
 * Contine algoritmii Dijkstra si Bellman-Ford pentru calcularea celui mai scurt drum de la un nod intr-un graf
 * @author Draghici
 */
public class Algoritmi{
    /**
     * Algoritmul Dijkstra
     * @param source Nodul sursa, pentru care vor fi calculate drumurile minime
     */
    public static void dijkstra(Nod source){
        source.setMinDistance(0.);
        PriorityQueue<Nod> vertexQueue = new PriorityQueue<Nod>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Nod u = vertexQueue.poll();

            // Pentru fiecare muchie pornind de la nodul u.
            for (Muchie e : u.getAdjacencies())
            {
                Nod v = e.getTarget();
                double weight = e.getWeight();
                double distanceThroughU = u.getMinDistance() + weight;
		if (distanceThroughU < v.getMinDistance()) {
		    vertexQueue.remove(v);
		    v.setMinDistance(distanceThroughU); 
		    v.setPrevious(u);
		    vertexQueue.add(v);
		}
            }
        }
    }
    
    /**
     * Algoritmul Bellman-Ford
     * @param source Nodul sursa pentru care vor fi calculate distantele minime
     * @param nodeVect Vectorul nodurilor
     */
    public static boolean bellmanFord(Nod source, ArrayList <Nod> nodeVect){
        
        source.setMinDistance(0);
        for(int i = 1; i < nodeVect.size(); i++){
            for(Nod u : nodeVect){
               for (Muchie e : u.getAdjacencies()){
                    Nod v = e.getTarget();
                    double weight = e.getWeight();
                    double distanceThroughU = u.getMinDistance() +     weight;
                    if (distanceThroughU < v.getMinDistance()) {
                        v.setMinDistance (distanceThroughU) ;
                        v.setPrevious (u);   
                    }
                }
            }
        }
        
        for(Nod u : nodeVect){
               for (Muchie e : u.getAdjacencies()){
                    Nod v = e.getTarget();
                    double weight = e.getWeight();
                    double distanceThroughU = u.getMinDistance() +     weight;
                    if (distanceThroughU < v.getMinDistance()) {
                           return false;
                    }
                }
            }
        
        return true;
        
    }

    /**
     * Functia construieste o lista cu nodurile prin care trebuie sa treci pentru a ajunge la nodul argument (tinta)
     * @param target Nodul tinta
     * @return Lista cu nodurile prin care s-a trecut
     */
    public static List<Nod> getShortestPathTo(Nod target){
        List<Nod> path = new ArrayList<Nod>();
        for (Nod vertex = target; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args){
        //debug code
    }
}
