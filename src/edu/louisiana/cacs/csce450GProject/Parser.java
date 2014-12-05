package edu.louisiana.cacs.csce450GProject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author sriharsha218
 */
public class Parser {
	
	static String filename = "";
	public Parser(String fileName){
		System.out.println("File to parse : "+fileName);
		filename = fileName;
	}
	
    
    
    private static int charClass = -1;
	private static char []inp_stack = new char[100] ;
	private static char nextChar;
	private static int lexLen = 0;
	private static int nextToken = 0;
	
	//Character classes
	private static final int LETTER = 0;
	private static final int DIGIT = 1;
	private static final int UNKNOWN = 99;
	private static final int EOF = 999;
	
	private static BufferedReader br = null;
	private static Stack <String> st = new Stack <String>();
    private static Stack <String> rules = new Stack <String>();
	private static Queue <String> queueA = new LinkedList <String>();
    private static String action_val = "";
    private static Stack <String> arg1 = new Stack <String>();
    private static Stack <String> arg2 = new Stack <String>();
    private static Stack <String> arg3 = new Stack <String>();
    private static Stack <String> arg4 = new Stack <String>();
    private static Queue <String> argt = new LinkedList <String>();
    private static int p = 0;private static int q = 0;
    
    
    private static String Action[][] = new String [][]{{ "S5","-", "-","S4","-","-","1","2","3"},
                                                { "-","S6", "-","-","-","accept","-","-","-"},
                                                { "-","R2", "S7","-","R2","R2","-","-","-"},
                                                { "-","R4", "R4","-","R4","R4","-","-","-"},
                                                { "S5","-", "-","S4","-","-","8","2","3"},
                                                { "-","R6", "R6","-","R6","R6","-","-","-"},
                                                { "S5","-", "-","S4","-","-","-","9","3"},
                                                { "S5","-", "-","S4","-","-","-","-","10"},
                                                { "-","S6", "-","-","S11","-","-","-","-"},
                                                { "-","R1", "S7","-","R1","R1","-","-","-"},
                                                { "-","R3", "R3","-","R3","R3","-","-","-"},
                                                { "-","R5", "R5","-","R5","R5","-","-","-"}};
        
        
        
        
	

public static void parse () throws IOException {
		
			
		
		 try{
			 br = new BufferedReader(new FileReader(filename));
		 } 
		
		catch(Exception e){
			 System.out.println("FILE NOT FOUND!");
			 System.out.println("Current working dir: " + System.getProperty("user.dir"));
			 System.exit(-1);
		 }
		 
		 
		getChar();
		 do{
		    lex();
		 }while(nextToken != EOF);
		 br.close();     
     
		  String format = String.format("%-30s %-25s %-10s %-9s %-10s %-10s %-20s %-10s %-10s %-20s %-20s","","input","action","action","value","length","temp","goto","goto","Stack",""  );
		  System.out.println(format);
		  format = String.format("%-30s %-25s %-10s %-9s %-10s %-10s %-20s %-10s %-10s %-20s %-20s","Stack","tokens","lookup","value","of LHS","of RHS","stack","lookup","value","action","parse tree stack"  );
		  System.out.println(format);
       
        st.push("0");
        int inp = 0;
        do{
           String top = (String)queueA.peek();
	       switch(top){
	           case "id": inp = 0;break;
	           case "+": inp = 1;break;
	           case "*": inp = 2;break;
	           case "(": inp = 3;break;
	           case ")": inp = 4;break; 
	           case "$": inp = 5;break;
	           default: break;
	           }
		       String temp = st.peek();
		       int state = getDigit(temp);
		       action_val = Action_look(state, inp);		       
		       }while (!action_val.equals("accept"));
     printParseTree();     
        	
}

public static String Action_look (int state,int inp){
    String LHS ; String RHS;
    int goto_val;
    String ret_val ="";
    //String s = "", stq = "";
    String temp;
    
        switch (Action[state][inp] ){
            case "S4":String s = "", stq = "";
            	      for (String i : st) {
        		        stq = stq + i; 
        	           }
            		  for (String i : queueA) {
            		    s = s + i; 
            	      }
                      String format = String.format("%-30s %-25s %-10s %-10s %-10s %-10s %-20s %-10s %-10s %-20s ",stq,s, "["+state+","+queueA.element()+"]","S4","","","","","","push "+queueA.element()+"4");
              		  System.out.print(format);
              		  String t = queueA.peek();
          		      tree_stack(t);
          		      System.out.println();
                      st.push(queueA.remove() + "4");
                      ret_val = ""+"S4";
                      break;
            		  
            case "S5":s = ""; stq = ""; 
	            	  for (String i : st) {
	        		    stq = stq + i; 
	        	       }
	            	  for ( String i : queueA) {
	        		    s = s + i; 
	        	       }
                	  format = String.format("%-30s %-25s %-10s %-10s %-10s %-10s %-20s %-10s %-10s %-20s ",stq,s, "["+state+","+queueA.element()+"]","S5","","","","","","push "+queueA.element()+"5" );
            		  System.out.print(format);
            		  t = queueA.peek();
            		  tree_stack(t);
            		  System.out.println();
            		  st.push(queueA.remove() + "5");
                      ret_val = ""+"S5";
                      break;
                      
            case "S6":s = ""; stq = "";
		              for (String i : st) {
		        		 stq = stq + i; 
		        	     }
		              for ( String i : queueA) {
		        		s = s + i; 
		        	    }
                      format = String.format("%-30s %-25s %-10s %-10s %-10s %-10s %-20s %-10s %-10s %-20s ",stq,s, "["+state+","+queueA.element()+"]","S6","","","","","","push "+queueA.element()+"6"  );
                      System.out.print(format);
                      t = queueA.peek();
                      tree_stack(t);
            		  System.out.println();
                      st.push(queueA.remove() + "6");
                      ret_val = ""+"S6";                      
                      break;
                      
            case "S7":s = ""; stq = "";
			          for (String i : st) {
			            stq = stq + i; 
			        	}
			          for ( String i : queueA) {
			             s = s + i; 
			           }
                      format = String.format("%-30s %-25s %-10s %-10s %-10s %-10s %-20s %-10s %-10s %-20s ",stq,s, "["+state+","+queueA.element()+"]","S7","","","","","","push "+queueA.element()+"7");
                      System.out.print(format);
                      t = queueA.peek();
                      tree_stack(t);
                      System.out.println();
                      st.push(queueA.remove() + "7");
                      ret_val = ""+"S7";
                      break;
                      
            case "S11":s = ""; stq = "";
			          for (String i : st) {
			          stq = stq + i; 
			        	}
			           for ( String i : queueA) {
			             s = s + i; 
			              }
            		  format = String.format("%-30s %-25s %-10s %-10s %-10s %-10s %-20s %-10s %-10s %-20s",stq,s, "["+state+","+queueA.element()+"]","S11","","","","","","push "+queueA.element()+"11"  );
                      System.out.print(format);
                      t = queueA.peek();
                      tree_stack(t);
            		  System.out.println();
                      st.push(queueA.remove() + "11");
                      ret_val = ""+"S11";
                      break;
                
            case "R1":s = ""; stq = "";
			          for (String i : st) {
			        	stq = stq + i; 
			        	}
			          for ( String i : queueA) {
			        	s = s + i; 
			        	}
			          format = String.format("%-30s %-25s %-10s %-10s", stq,s,"["+state+","+queueA.element()+"]","R1");
			          System.out.print(format);
                      rules.push("E"); rules.push("E");rules.push("+");rules.push("T");
                      LHS = ""+rules.elementAt(0);
                      RHS = ""+rules.elementAt(1)+rules.elementAt(2)+rules.elementAt(3);
                      int len = 3;
                      st.pop();st.pop();st.pop();
                      temp = st.peek();
                      state = getDigit(temp);
                      inp = 6;
                      goto_val = goto_look(state, inp);
                      stq = "";
			          for (String i : st) {
			        	stq = stq + i; 
			        	}
                      format = String.format("%-10s %-10s %-20s %-10s %-10s %-20s",LHS,"3",stq,"["+state+","+LHS+"]",goto_val,"push E"+goto_val );
                      System.out.print(format);
                      red_stack(LHS,RHS,len);
                      System.out.println();
                      st.push("E"+Integer.toString(goto_val));
                      rules.clear();
                      ret_val = ""+"R1";
                      break;
                   
            case "R2":s = ""; stq = "";
		              for (String i : st) {
		        	    stq = stq + i; 
		        	    }
		              for ( String i : queueA) {
		        	    s = s + i; 
		        	    }
                      format = String.format("%-30s %-25s %-10s %-10s", stq,s,"["+state+","+queueA.element()+"]","R2");
                      System.out.print(format);
                      rules.push("E");rules.push("T");
                      LHS = ""+rules.elementAt(0);
                      RHS = ""+rules.elementAt(1);
                      len = 1;
                      st.pop();
                      //System.out.print(st+"\t");
                      temp = st.peek();
                      state = getDigit(temp);
                      inp = 6;
                      goto_val = goto_look(state, inp);
                      stq = "";
			          for (String i : st) {
			        	stq = stq + i; 
			        	}
                      format = String.format("%-10s %-10s %-20s %-10s %-10s %-20s ",LHS,"1",stq,"["+state+","+LHS+"]",goto_val,"push E"+goto_val);
                      System.out.print(format);
                      red_stack(LHS,RHS,len);
                      System.out.println();
                      st.push("E"+Integer.toString(goto_val));
                      rules.clear();
                      ret_val = ""+"R2";
                      break;
                      
            case "R3":s = ""; stq = "";
	                  for (String i : st) {
	        	        stq = stq + i; 
	        	        }
	                  for ( String i : queueA) {
	        	        s = s + i; 
	        	        }
            		  format = String.format("%-30s %-25s %-10s %-10s", stq,s,"["+state+","+queueA.element()+"]","R3");
                      System.out.print(format);
                      rules.push("T");rules.push("T");rules.push("*");rules.push("F");
                      LHS = ""+rules.elementAt(0);
                      RHS = ""+rules.elementAt(1)+rules.elementAt(2)+rules.elementAt(3);
                      len = 3;
                      st.pop();st.pop();st.pop();
                      temp = st.peek();
                      state = getDigit(temp);
                      inp = 7;
                      goto_val = goto_look(state, inp);
                      stq = "";
			          for (String i : st) {
			        	stq = stq + i; 
			        	}
                      format = String.format("%-10s %-10s %-20s %-10s %-10s %-20s",LHS,"3",stq,"["+state+","+LHS+"]",goto_val,"push T"+goto_val  );
                      System.out.print(format);
                      red_stack(LHS,RHS,len);
                      System.out.println();
                      st.push("T"+Integer.toString(goto_val));
                      rules.clear();
                      ret_val = ""+"R3";
                      break;
                      
            case "R4":s = ""; stq = "";
		              for (String i : st) {
		      	      stq = stq + i; 
		      	      }
		              for ( String i : queueA) {
		      	      s = s + i; 
		      	      }
                      format = String.format("%-30s %-25s %-10s %-10s", stq,s,"["+state+","+queueA.element()+"]","R4");
                      System.out.print(format);
                      rules.push("T");rules.push("F");
                      LHS = ""+rules.elementAt(0);
                      RHS = ""+rules.elementAt(1);
                      len = 1;
                      st.pop();
                      temp = st.peek();
                      state = getDigit(temp);
                      inp = 7;
                      goto_val = goto_look(state, inp);
                      stq = "";
			          for (String i : st) {
			        	stq = stq + i; 
			        	}
                      format = String.format("%-10s %-10s %-20s %-10s %-10s %-20s",LHS,"1",stq,"["+state+","+LHS+"]",goto_val,"push T"+goto_val );
                      System.out.print(format);
                      red_stack(LHS,RHS,len);
                      System.out.println();
                      st.push("T"+Integer.toString(goto_val));
                      rules.clear();
                      ret_val = ""+"R4";
                      break;
                      
            case "R5":s = ""; stq = "";   
            	      for (String i : st) {
		      	      stq = stq + i; 
		      	      }
		              for ( String i : queueA) {
		      	      s = s + i; 
		      	      }
                      format = String.format("%-30s %-25s %-10s %-10s", stq,s,"["+state+","+queueA.element()+"]","R5");
                      System.out.print(format);
                      rules.push("F");rules.push("(");rules.push("E");rules.push(")");
                      LHS = ""+rules.elementAt(0);
                      RHS = ""+rules.elementAt(1)+rules.elementAt(2)+rules.elementAt(3);
                      len = 3;
                      st.pop();st.pop();st.pop();
                      temp = st.peek();
                      state = getDigit(temp);                      inp = 8;
                      goto_val = goto_look(state, inp);
                      stq = "";
			          for (String i : st) {
			        	stq = stq + i; 
			        	}
                      format = String.format("%-10s %-10s %-20s %-10s %-10s %-20s ",LHS,"3",stq,"["+state+","+LHS+"]",goto_val,"push F"+goto_val  );
                      System.out.print(format);
                      red_stack(LHS,RHS,len);
                      System.out.println();
                      st.push("F"+Integer.toString(goto_val));
                      rules.clear();
                      ret_val = ""+"R5";
                      break;
                      
            case "R6":s = ""; stq = ""; 
            	      for (String i : st) {
		      	      stq = stq + i; 
		      	      }
		              for ( String i : queueA) {
		      	      s = s + i; 
		      	      }
                      format = String.format("%-30s %-25s %-10s %-10s", stq,s,"["+state+","+queueA.element()+"]","R6");
                      System.out.print(format);
                      rules.push("F");rules.push("id");
                      LHS = ""+rules.elementAt(0);
                      RHS = ""+rules.elementAt(1);
                      len = 1;
                      st.pop();
                      temp = st.peek();
                      state = getDigit(temp);
                      inp = 8;
                      goto_val = goto_look(state, inp);
                      stq = "";
			          for (String i : st) {
			        	stq = stq + i; 
			        	}
                      format = String.format("%-10s %-10s %-20s %-10s %-10s %-20s",LHS,"1",stq,"["+state+","+LHS+"]",goto_val,"push F"+goto_val );
                      System.out.print(format);
                      red_stack(LHS,RHS,len);
                      System.out.println();
                      st.push("F"+Integer.toString(goto_val));
                      rules.clear();
                      ret_val = ""+"R6";
                      break;
                      
            case "accept":s = ""; stq = ""; 
            	      for (String i : st) {
		      	      stq = stq + i; 
		      	      }
		              for ( String i : queueA) {
		      	      s = s + i; 
		      	      }
                      format = String.format("%-30s %-25s %-10s %-10s", stq,s,"["+state+","+queueA.element()+"]","accept");
                      System.out.print(format);
                      ret_val = ""+"accept";
                      break;
                      
                      default:  System.out.println("ungramatical language....."+"\t");
                                break;
                      }
          return ret_val;
         }	
	

public static int goto_look (int state,int inp){
    switch (Action[state][inp] ){
       case "1": return 1;
       case "2": return 2;
       case "3": return 3;
       case "8": return 8;
       case "9": return 9;
       case "10": return 10;
    }
        return 0;
}

    

public static void getChar(){
		int r;
		try{
			if((r = br.read()) != -1){
				nextChar = (char)r;
				if(Character.isAlphabetic(nextChar)){
					charClass = LETTER;
				}
				else if (Character.isDigit(nextChar)){
					charClass = DIGIT;
				}
				else {
					charClass = UNKNOWN;
				}
				
			}
			else {
			
				charClass = EOF;
				nextChar = '\0';
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}		
		
	}
	
	public static void addChar(){
		if(lexLen <= 98){
			inp_stack[lexLen++] = nextChar;
			inp_stack[lexLen] = 0;
		}
		else {
			System.out.println("Error: inp_stack is too long");
		}
	}
	
	public static void getNoNBlank(){
		while(Character.isWhitespace(nextChar)){
			getChar();
		}
	}
	
	public static int getDigit(String data){
		Pattern pt = Pattern.compile("[0-9]+");
	       Matcher mt = pt.matcher(new StringBuilder(data).reverse());
	       String num;
	       if(mt.find()){
	    	   num = new StringBuilder(mt.group(0)).reverse().toString();
	    	   return Integer.parseInt(num);
	       }else{ return -1;}
	}
	
	public static int lookup(char ch){
		switch(ch){
		case '(' : addChar();
			   nextToken = 1;
			   break;
		case ')' : addChar();
			   nextToken = 2;
			   break;
		case '+' : addChar();
			   nextToken = 3;
			   break;
		case '-' : addChar();
			   nextToken = 4;
			   break;
		case '=' : addChar();
			   nextToken = 5;
			   break;
		case '*' : addChar();
			   nextToken = 6;
			   break;
		case '/' : addChar();
			   nextToken = 7;
			   break;
			   
		default : addChar();
			  nextToken = 999;
			  break;
		}
		return nextToken;
	}
	
	
	public static int lex(){
		
		lexLen = 0;
		inp_stack = new char[100];
		getNoNBlank();
		switch(charClass){
		
		case LETTER : addChar();
			      getChar();
			      while(charClass == LETTER){
				addChar();
				getChar();
				}
			      nextToken = 1;
			      break;
		case DIGIT : addChar();
			     getChar();
			     while(charClass == DIGIT){
				addChar();
				getChar();
				}
			     nextToken = 8;
			     break;
		case UNKNOWN : lookup(nextChar);
			       getChar();
			       break;
		case EOF : nextToken = EOF;
			   inp_stack[0] = 'E';
			   inp_stack[1] = 'O';
			   inp_stack[2] = 'F';
			   inp_stack[3] = '\0';
					
		}
		String str = new String(inp_stack);
		queueA.add(str.trim());
		
		
		return nextToken;
	}
	
	
	
	
	public static void tree_stack(String t){
		if(t.equals("id")){
			p= p+1;
			if (p==2){
				arg2.push("id");
				print_stak(arg2);
				print_stak(arg1);
			}
			else if(p == 3){
				arg3.push("id");
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else if(p == 4){
				arg4.push("id");
				print_stak(arg4);
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else{
				arg1.push("id");
				print_stak(arg1);
			}
		}
		else{
		   if (p == 2){
				print_stak(arg2);
				print_stak(arg1);
			}
			else if(p == 3){
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else if(p == 4){
				print_stak(arg4);
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else{
			   print_stak(arg1);
			}
		}
	}	
	
	public static void red_stack(String lhs,String rhs,int length ){
		if(length == 1 && rhs.equals("id")){
			q= q+1;
			if (q==2){
				arg2.push("F");
				print_stak(arg2);
				print_stak(arg1);
			}
			else if(q == 3){
				arg3.push("F");
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else if(q == 4){
				arg4.push("F");
				print_stak(arg4);
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else{
				arg1.push("F");
				print_stak(arg1);
			}
		
		}	
	else if(length == 1 && !rhs.equals("id")){
			if (q==2){
				arg2.push(lhs);
				print_stak(arg2);
				print_stak(arg1);
			}
			else if(q == 3){
				arg3.push(lhs);
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else if(q == 4){
				arg4.push(lhs);
				print_stak(arg4);
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else{
				arg1.push(lhs);
				print_stak(arg1);
			}
		}
		else{
		   if (q == 2){
				print_stak(arg2);
				print_stak(arg1);
			}
			else if(q == 3){
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else if(q == 4){
				print_stak(arg4);
				print_stak(arg3);
				print_stak(arg2);
				print_stak(arg1);
				}
			else{
			   print_stak(arg1);
			}
		}
	}		
	
	
	public static void print_stak(Stack <String> arg){
		while(!arg.isEmpty()) {
            String v = arg.pop();
            argt.add(v);
            }
		    
		for (String tr : argt){
			if (!tr.equals("id")){
			 System.out.print("["+tr);
			}
			else{
			System.out.print(" "+tr);
			}
		}

		for (String tr : argt){
			if (!tr.equals("id")){
			System.out.print("]");
			}
		}
		//System.out.println();
	}
		
	
	public static void printParseTree(){
		System.out.println();
		 System.out.println();
		  System.out.println();
		  System.out.println("parse tree");
		  System.out.println("---------------");
		  for(String c: arg1){  
			System.out.println("/t"+ c); 
		  }
		  
			
	}
		
	

    
}
    
    
    
    
  
