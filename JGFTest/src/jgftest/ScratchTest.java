/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgftest;

import jgf.sequential.sparsematmult.JGFSparseMatmultBench;

/**
 *
 * @author yoshiki
 */
public class ScratchTest {

    public static void main(String[] args) {
        JGFSparseMatmultBench smm = new JGFSparseMatmultBench();
        smm.JGFrun(0);
    }
}
