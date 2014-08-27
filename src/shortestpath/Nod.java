

package shortestpath;

import java.util.ArrayList;

/**
 * Clasa nod contine interfata necesara reprezentarii unui nod al unui graf
 * @author Draghici
 */
    public class Nod implements Comparable<Nod>{
        //Numele nodului
        private final String name;
        //Lista cu muchiile adiacente nodului
        private ArrayList<Muchie> adjacencies;
        //Costul minim de la acest nod la altul din graf
        private double minDistance = Double.POSITIVE_INFINITY;
        //Nod anterior
        private Nod previous;
        /**
         * Constructor detaliat
         * @param argName Numele nodului
         */
        public Nod(String argName){ 
            name = argName; 
        }
        /**
         * Default constructor
         */
        public Nod(){ 
            name = "Nod"; 
        }
        /**
         * 
         * @return Numele nodului 
         */
        @Override
        public String toString(){ 
            return name; 
        }
        /**
         * Functia compara distanta minima dintre acest nod si unul primit ca argument
         * @param other Nodul cu care este comparat acesta
         * @return Rezultatul comparatiei
         */
        @Override
        public int compareTo(Nod other){
            return Double.compare(minDistance, other.minDistance);
        }
        /**
         * Getter pentru lista muchiilor
         * @return Lista muchiilor adiacente acestui nod
         */
        public ArrayList <Muchie> getAdjacencies(){
            return this.adjacencies;
        }
        /**
         * Getter pentru distanta minima de la acest nod la unul adiacent
         * @return Distanta minima
         */
        public double getMinDistance(){
            return this.minDistance;
        }
        /**
         * Getter pentru nodul anterior
         * @return Nodul anterior
         */
        public Nod getPrevious(){
            return this.previous;
        }
        /**
         * Setter pentru distanta minima de la acest nod la unul adiacent
         * @param arg Distanta minima
         */
        public void setMinDistance(double arg){
            this.minDistance = arg;
        }
        /**
         * Setter pentru nodul anterior
         * @param arg Nodul anterior
         */
        public void setPrevious(Nod arg){
            this.previous = arg;
        }
        /**
         * Setter pentru lista de muchii a acestui nod
         * @param arg Lista de muchii
         */
        public void setAdjacencies(ArrayList <Muchie> arg){
            this.adjacencies = arg;
        }
    
    
}