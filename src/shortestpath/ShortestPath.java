/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shortestpath;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Main class
 * @author Draghici
 */
public class ShortestPath extends JFrame {

    
    private JTextArea instructionsFile,
                      infoFile,
                      instructionsUser,
                      output,
                      error;
    private Font fileFont;
    private JTextField fileName,
                       input;
    private JScrollPane outputPane,
                        errorPane;
    private JButton submitInputFileName,
                    dijkstra,
                    bellmanFord;
    private boolean hasFile = false, canDijkstra = true;
    ArrayList <ArrayList>  dataMatrix, auxList;
    ArrayList <Nod> nodeVector;
    
    /**
     * Default constructor
     */
    public ShortestPath (){
        this.setLayout(null);
        dataMatrix = new ArrayList();
        nodeVector = new ArrayList();
        fileName = new JTextField();
        input = new JTextField();
        output = new JTextArea();
        error = new JTextArea();
        errorPane = new JScrollPane(error);
        outputPane = new JScrollPane(output);
        fileFont = new Font ("Times New Roman", Font.BOLD, 14);
        instructionsFile = new JTextArea();
        infoFile = new JTextArea();
        instructionsUser = new JTextArea();
        submitInputFileName = new JButton ("Citeste");
        dijkstra = new JButton ("Dijkstra");
        bellmanFord = new JButton ("BellmanFord");
        initGUI();
    }
    
    /**
     * Initializarea interfetei grafice
     */
    private void initGUI(){
        
        this.remove(instructionsFile);
        this.remove(fileName);
        this.remove(submitInputFileName);
        this.remove(errorPane);
        this.remove(infoFile);
        this.remove(input);
        this.remove(outputPane);
        this.remove(dijkstra);
        this.remove(bellmanFord);
        this.remove(instructionsUser);
       
       //1 
        fileName.setText("");
        fileName.setBounds (20, 
                            100, 
                            200, 
                            fileName.getPreferredSize().height);
        //2
        instructionsFile.setText("Numele fisierului de intrare:");
        instructionsFile.setEditable(false);
        instructionsFile.setOpaque(false);
        instructionsFile.setFocusable(false);
        instructionsFile.setBounds(20,
                               81,
                               instructionsFile.getPreferredSize().width,
                               instructionsFile.getPreferredSize().height);
        
        
        infoFile.setOpaque(false);
        infoFile.setFont(fileFont);
        infoFile.setEditable(false);
        infoFile.setFocusable(false);
        infoFile.setBounds( (500 - infoFile.getPreferredSize().width) /2 - infoFile.getPreferredSize().width/2,
                           150,
                           infoFile.getPreferredSize().width,
                           infoFile.getPreferredSize().height);
        
        instructionsUser.setOpaque(false);
        instructionsUser.setEditable(false);
        instructionsUser.setFocusable(false);
        instructionsUser.setText("Introdu nodul din care pornesti:");
        instructionsUser.setBounds(100,
                                   192,
                                   instructionsUser.getPreferredSize().width,
                                   instructionsUser.getPreferredSize().height);
        
        
        input.setBounds(50,
                        211,
                        150,
                        input.getPreferredSize().height);
        
        output.setEditable(false);
        outputPane.setBounds(50,
                             230,
                             300,
                             100);
        
        error.setEditable(false);
        error.setOpaque(false);
        errorPane.setBorder(null);
        errorPane.setBounds(0,
                            330,
                            490,
                            130);
        
        submitInputFileName.setBounds(300,
                                      100,
                                      submitInputFileName.getPreferredSize().width,
                                      submitInputFileName.getPreferredSize().height);
        
        dijkstra.setBounds(350,
                           240,
                           dijkstra.getPreferredSize().width,
                           dijkstra.getPreferredSize().height);
        
        bellmanFord.setBounds(350,
                              255 + dijkstra.getPreferredSize().height, 
                              bellmanFord.getPreferredSize().width,
                              bellmanFord.getPreferredSize().height);
        
        this.add(instructionsFile);
        this.add(fileName);
        this.add(submitInputFileName);
        this.add(errorPane);
        if(hasFile){
            this.add(infoFile);
            this.add(input);
            this.add(outputPane);
            if(canDijkstra) this.add(dijkstra);
            this.add(bellmanFord);
            this.add(instructionsUser);
        }
        
        submitInputFileName.addActionListener(new ActionListener() {
            ShortestPath parent;    

            @Override        
            public void actionPerformed(ActionEvent e){
                  if(!parent.getInputText().equals("")) 
                     parent.readFile();   
            } 
            
            private ActionListener initComponents(ShortestPath arg){
                parent = arg;
                return this;
            }
                
        }.initComponents(this)
                );
        
        dijkstra.addActionListener(new ActionListener() {
            ShortestPath parent;
            
            @Override
            public void actionPerformed(ActionEvent e){
                if(!input.getText().equals(""))
                    try{
                        Integer.parseInt(input.getText());
                        parent.calculeazaDistantaMinima("dijkstra");
                    } catch (NumberFormatException ex){
                        error.append("Textul introdus nu reprezinta un numar." + "\n");
                        input.setText("");
                        input.requestFocusInWindow();
                    }
            }
            private ActionListener initComponents(ShortestPath arg){
                parent = arg;
                return this;
            }
        }.initComponents(this));
        
        bellmanFord.addActionListener(new ActionListener() {
            ShortestPath parent;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!input.getText().equals(""))
                    try{
                        Integer.parseInt(input.getText());
                        parent.calculeazaDistantaMinima("bellman-ford");
                    } catch (NumberFormatException ex){
                        error.append("Textul introdus nu reprezinta un numar." + "\n");
                        input.setText("");
                        input.requestFocusInWindow();
                    }
            }
            
            private ActionListener initComponents(ShortestPath arg){
                parent = arg;
                return this;
            }
            
        }.initComponents(this));
        
        
        this.repaint();
        
    }
    
    /**
     * Functia calculeaza distanta minima de la nodul sursa la toate celelalte noduri din graf
     * @param type Tipul algoritmului folosit (Dijkstra sau Bellman-Ford)
     */
    private void calculeazaDistantaMinima(String type){
        output.setText("");
        error.setText("");
        int target = Integer.parseInt(input.getText()) - 1;
        if(target >= nodeVector.size()){
            error.setText("Numarul este prea mare" + "\n");
            input.setText("");
            input.requestFocusInWindow();
            return;
        }
        if(type.equals("dijkstra"))
            Algoritmi.dijkstra(nodeVector.get(target));
        else if(type.equals("bellman-ford"))
            Algoritmi.bellmanFord(nodeVector.get(target), nodeVector);
        for (Nod v : nodeVector){
            output.append("Distance to " + v + ": " + v.getMinDistance() + "\n");
            java.util.List <Nod> path = Algoritmi.getShortestPathTo(v);
            output.append("Path: " + path + "\n");
        }
        dataMatrix = copyMatrix(auxList);
        createNodeVector();
        input.setText("");
        input.requestFocusInWindow();
    }
    
    /**
     * Copiaza continutul matricei primita argument intr-una noua
     * @param arg Matricea argument
     * @return Matricea noua
     */
    private ArrayList copyMatrix(ArrayList <ArrayList> arg){
        ArrayList <ArrayList> nArrList = new ArrayList();
        for(int i = 0; i < arg.size(); i++){
            nArrList.add(new ArrayList ());
            for(int j = 0; j < arg.get(i).size(); j++)
                nArrList.get(i).add(arg.get(i).get(j));            
        }
        
        return nArrList;
        
    }
    
    /**
     * Creeaza vectorul cu nodurile grafului pornind de la matricea costurilor
     */
    private void createNodeVector(){
        nodeVector = new ArrayList();
        for(int i = 0; i < dataMatrix.size(); i++)
                nodeVector.add(new Nod("node" + Integer.toString(i+1)));
            for(int i = 0; i < dataMatrix.size(); i++){
                ArrayList <Muchie> edgeVect = new ArrayList();
                for(int j = 0; j < dataMatrix.get(i).size(); j++)
                    if((Integer)dataMatrix.get(i).get(j) != 0 )
                        edgeVect.add(new Muchie(nodeVector.get(j), (int)dataMatrix.get(i).get(j)));
                nodeVector.get(i).setAdjacencies(edgeVect);
            }
        
    }
    
    /**
     * Functia este apelata la apasarea butonului "Citeste"
     */
    private void readFile(){
        dataMatrix  = new ArrayList();
        String url = fileName.getText();
        BufferedReader in ;
        try{
            in = new BufferedReader (new FileReader(url));
        } catch (FileNotFoundException ex){
            error.append("Fisierul nu exista" + "\n");
            hasFile = false;
            initGUI();
            return;
        } 
          try{
            String line = new String();
            while((line = in.readLine()) != null){
                char charArr[] = line.toCharArray();
                //System.out.println(Double.parseDouble(line));
                ArrayList intArr = new ArrayList();
                for(int i = 0; i < charArr.length; i++){
                    if(Character.getNumericValue(charArr[i]) >= 0){
                        intArr.add(Character.getNumericValue(charArr[i]));
                        if(i<charArr.length - 1)
                            while(Character.getNumericValue(charArr[i+1]) >= 0){
                                intArr.set( (intArr.size() - 1), 
                                            (int)(intArr.get(intArr.size() - 1)) * 10 + Character.getNumericValue(charArr[i+1]));
                                i++;
                            }
                    }
                }
                dataMatrix.add(intArr);
              }
            auxList = copyMatrix(dataMatrix);
            createNodeVector();
                         
          } catch (IOException ex){
            error.append("Eroare la citirea din fisier." + "\n");
        } finally{
              infoFile.setText(url);
        input.setText("");
        output.setText("");
        hasFile = true;
        initGUI();
          }
        
        
    }
       
    /**
     * Returneaza textul scris in campul "fileName"
     * @return fileName.getText();
     */
    public String getInputText(){
        return fileName.getText();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ShortestPath.run(new ShortestPath(), 500, 500);
    }
    
    /**
     * Creaza fereastra
     * @param frame
     * @param width
     * @param height 
     */
    public static void run(JFrame frame, int width, int height){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setVisible(true);
    }
}
