

package shortestpath;

/**
 * Clasa muchie contine interfata necesara reprezentarii unei muchii a unui graf
 * @author MatCoding
 */
public class Muchie {
    //tinta
    private final Nod target;
    //adancime
    private final double weight;
    /**
     * Constructor detaliat
     * @param argTarget Nodul catre care duce aceasta muchie
     * @param argWeight Costul parcurgerii acestei muchii (sau adancimea muchiei)
     */
    public Muchie(Nod argTarget, double argWeight){ 
        target = argTarget; 
        weight = argWeight; 
    }
    
    /**
     * Getter pentru nodul tinta
     * @return Nodul tinta
     */
    public Nod getTarget(){
        return this.target;
    }
    
    /**
     * Getter pentru adancimea muchiei
     * @return Adancimea muchiei
     */
    public double getWeight(){
        return this.weight;
    }
    
}
