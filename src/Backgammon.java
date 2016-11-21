/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamebackgammon;

import static gamebackgammon.Artificial.game1;
import static gamebackgammon.char_client.game1;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;


/**
 *
 * @author suleyman
 */
public class Backgammon {
    private String Name;
    private int eatenX=0;
    private int eatenY=0;
    private int yourTurn=1;
    ArrayList<Integer> zarList = new ArrayList<Integer>();
    Movement bestMovement;
    board myBoard;
    String input="";
    public static final String[] selection = { "Artficial Intellgince",
        "With frind on this Pc", "With frind on different Pc" };
    int [] zar = new int[2];
    

    Backgammon(String name) {
        Name=name;
        myBoard = new board();
    }
    Backgammon(String name,char myStone) {
        Name=name;
        myBoard = new board(myStone);
    }
    board getBoard() {
        return myBoard;
    }
    int getYourTurn() {
        return yourTurn;
    }
    void setYourTurn(int order) {
        yourTurn=order;
    }

    void MakeMove(char stone, int isArtificial) {
        int NowColumn=-1, NowRow=-1;
        int AfterColumn=-1, AfterRow=-1;
        int Now,After;
        String message="";
        int []Coordinate=new int [2];
        int []moveLocations=new int [4];
        ArrayList<Movement> movements= new ArrayList<Movement>();
        int zarIndex;
        int flag = 0;
        boolean status=true;
        
        if(stone==getBoard().getMyColor())
            setYourTurn(1);
        else
            setYourTurn(0);
        
        movements = generateMovement();
        if(!checkIsPass()) {
            if(isArtificial==1) {       //artificial
                moveLocations = findLocation(bestMovement.getMoveNow(), bestMovement.getMoveAfter());
                flag = isWrongMovement(moveLocations,stone);
                zarIndex=checkZarDistance(bestMovement.getMoveNow(), bestMovement.getMoveAfter(),stone);
                zarList.remove(zarIndex);
            }else {                     //else
                do{
                    do{  
                        do{
                        int i=0;
                            message="";
                            JFrame frame = new JFrame("JOptionPane showMessageDialog example");
                            message =  JOptionPane.showInputDialog(frame,"User "+stone+" Enter a movement"); 
                            if(message==null) {
                                System.err.println("Closing the game...");
                                System.exit(0);   
                            }
                            if(!message.equals("")) {
                                StringTokenizer st = new StringTokenizer(message," ");   
                                while (st.hasMoreElements()) {
                                    Coordinate[i]=Integer.parseInt(st.nextToken());
                                    i++;
                                }
                            }
                        }while(!(Coordinate[0]<25&&Coordinate[0]>0)||!(Coordinate[1]<25&&Coordinate[1]>0));
                        
                        Now = Coordinate[0];
                        After = Coordinate[1];
                        zarIndex=checkZarDistance(Now,After,stone);
                        if(zarIndex==-1){
                            JFrame frame1 = new JFrame("JOptionPane showMessageDialog example");
                            JOptionPane.showMessageDialog(frame1, "Wrong input!");
                            
                            System.out.println("----Wrong input!----");
                        }
                    }while(zarIndex==-1);
                    moveLocations = findLocation(Now, After);       
                    Movement mov = new Movement(Now, After, stone);
                    status = checkIsWrongMovement(movements, mov);
                    flag = isWrongMovement(moveLocations,stone);
                    if(status) {
                        JFrame frame = new JFrame("JOptionPane showMessageDialog example");
                        JOptionPane.showMessageDialog(frame, "Wrong Movement!");
                        System.out.println("----Wrong Movement!----");
                    }
                            
                }while(status);
                zarList.remove(zarIndex);
            }
            //zarList.remove(zarIndex);
            input+=message+" ";
            moveCoin(moveLocations, flag, stone); 
            getBoard().copyTable();
            getBoard().PrintTable();
            if(zarList.size()!=0)
                MakeMove(stone, isArtificial);
        }
    }

    void makeOpponentMove(String inputCoordinate, char color) {
        int flag = 0;
        int zarIndex=0,i=0,j=0;
        int []moves = new int [8];
        int []moveLocations=new int [4];
        StringTokenizer st = new StringTokenizer(inputCoordinate," ,");
        while (st.hasMoreElements()) {
            moves[i]=Integer.parseInt((String) st.nextElement());
            i++;
        }
        while (i>j) {      
            System.out.println("move :"+moves[j]+"-"+moves[j+1]);
            
            zarIndex=checkZarDistance(25-moves[j],25-moves[j+1],color);
            moveLocations = findLocation(25-moves[j],25-moves[j+1]);  
            flag = isWrongMovement(moveLocations,color);
            moveCoin(moveLocations, flag, color); 
            
            j+=2;
        }
        getBoard().copyTable();
        getBoard().PrintTable();
    }
    
    void moveCoin(int [] moveLocations, int flag, char ch) {
        if(flag==2){
            getBoard().table[moveLocations[0]][moveLocations[1]] = '.';
            getBoard().table[moveLocations[2]-1][moveLocations[3]] = ch;
        }else if(flag==3) {
            getBoard().table[moveLocations[0]][moveLocations[1]] = '.';
            getBoard().table[moveLocations[2]+1][moveLocations[3]] = ch;
        }else if(flag==5){                  //tas yer
            if(ch=='X')
                eatenX++;
            else
                eatenY++;
            getBoard().table[moveLocations[0]][moveLocations[1]] = '.';
            getBoard().table[moveLocations[2]][moveLocations[3]] = ch;
        }else {
            getBoard().table[moveLocations[0]][moveLocations[1]] = '.';
            getBoard().table[moveLocations[2]][moveLocations[3]] = ch;
        }    
    }

    ArrayList<Movement> generateMovement() {
        ArrayList<Movement> movePoss = new ArrayList<Movement>();        
        movePoss = generatePossibilities();
        
        for(int i=0;i<movePoss.size();i++) {
            int [] locs = new int[4];
            locs = findLocation(movePoss.get(i).getMoveNow(), movePoss.get(i).getMoveAfter());
            int flag = isWrongMovement(locs,movePoss.get(i).getMoveChar());
            moveCoin(locs, flag, movePoss.get(i).getMoveChar());
            givePoint(movePoss.get(i));
            getBoard().reCopyTable();
            
        }
        findBestMovement(movePoss);
        return movePoss;
    }

        int [] input() {
        int[] Coordinate = new int[2];
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter Now coordinate :");
        Coordinate[0] = in.nextInt();
        System.out.print("Please enter After coordinate :");
        Coordinate[1] = in.nextInt();
        
        return Coordinate;
    }



    public static void main(String[] args) throws InterruptedException 
    {
        Backgammon game1 = new Backgammon("slymn");
        
        game1.getBoard().LoadTable();
        game1.getBoard().PrintTable();
        JFrame frame = new JFrame("Input Dialog Example 3");
        String gameSelection = (String) JOptionPane.showInputDialog(frame, 
        "How do you begin to game?",
        "Game Selection",
        JOptionPane.QUESTION_MESSAGE, 
        null, 
        selection, 
        selection[0]);
        if(gameSelection=="Artficial Intellgince") {
            Artificial.main(args);            
            System.err.println("Artficial Intellgince");        
        }else if(gameSelection=="With frind on this Pc") {
            Manual.main(args);
        }else if(gameSelection=="With frind on different Pc") {
            chat_server.main(args);     
            System.err.println("With frind on different Pc");
        }else {
            System.err.println("Exitting..");
        }        
        System.exit(0);
        }
}