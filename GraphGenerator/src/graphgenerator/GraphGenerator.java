/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphgenerator;

/**
 *
 * @author yoshiki
 */
public class GraphGenerator {

    public void kronecker(int scale, int edgefactor) {
        
        /* the number of vertices */
        int n = 2^scale;
        /* the number of edges */
        int m = edgefactor * n;
        
        /* initiator parameters */
        float a = 0.57f;
        float b = 0.19f;
        float c = 0.19f;
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GraphGenerator().kronecker(10, 16);
    }
        
    
}
