package edu.louisiana.cacs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;

import edu.louisiana.cacs.csce450GProject.Parser;

/**
 *
 * @author sriharsha218
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
    	System.out.println("Hello World from Main");
    	Parser myParser = new Parser(args[0]);
        myParser.parse();
    }
    
}
