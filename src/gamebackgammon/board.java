/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamebackgammon;

import java.util.ArrayList;

/**
 *
 * @author suleyman
 */
class board {
    private char myColor;
    static char [][]table = new char[30][12];
    char [][]copiedTable = new char [30][12];
    ArrayList<Integer> filledMy = new ArrayList<Integer>();
    ArrayList<Integer> filledYour = new ArrayList<Integer>();
    
    board() {
        this.myColor='X';
    }
    board(char myColor) {
        this.myColor=myColor;
    }
    void setMyColor(char ch) {
        myColor=ch;
    }
    char getMyColor() {
        return myColor;
    }
    char getYourColor() {
        if(getMyColor()=='X')
            return 'O';
        return 'X';
    }
    ArrayList<Integer> getFilledMy() {
        filledMy.removeAll(filledMy);
        findFilledCordinates(getMyColor());
        return filledMy;
    }
    ArrayList<Integer> getFilledYour() {
        filledYour.removeAll(filledYour);
        findFilledCordinates(getYourColor());
        return filledYour;
    }
    void LoadTable()
    {
       
        char RivalColor = ' ';
        if (getMyColor() == 'X')
            RivalColor = 'O';
        else if(getMyColor() == 'O')
            RivalColor = 'X';
        else
            System.out.println("hata");

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 12; j++) {
                table[i][j]='.';
            }
        }
        for (int i = 0; i < 2; i++) {
            table[i][0] = getMyColor();
            table[29-i][0] = RivalColor; 
        }
        for (int i = 0; i < 5; i++) {
            table[i][5] = RivalColor;
            table[29-i][11] = RivalColor;
            table[29 - i][5] = getMyColor();
            table[i][11] = getMyColor();
        }
        for (int i = 0; i < 3; i++) {
            table[i][7] = RivalColor;
            table[29 - i][7] = getMyColor();
        }
        copyTable();
    }
    boolean CheckRowEmpty(int Row) {
        for(int i=0;i<12;i++) {
            if (table[Row][i] != '.')
                return true;
        }
        return false;
    }
    void copyTable() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 12; j++) {
                copiedTable[i][j]=table[i][j];
            }
        }
      
    }
    void reCopyTable() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 12; j++) {
                table[i][j]=copiedTable[i][j];
            }
        }
    }
    void findFilledCordinates(char ch) {
        for (int i = 0; i < 12; i++) {
            if(ch==getMyColor()) {
                if(table[0][i]==ch)
                    filledMy.add(24-i);
                if(table[29][i]==ch)
                    filledMy.add(i+1);
                
            } else if(ch==getYourColor()){
                if(table[0][i]==ch)
                    filledYour.add(24-i);
                if(table[29][i]==ch)
                    filledYour.add(i+1);
            }
        }
    }
    void PrintTable()
    {
        System.out.println("                               O\n");
        System.out.print("   ");
        for (int i = 24; i > 12; i--)
        {
            if (i < 10)
                System.out.print(i+"    ");
            else {
                if(i==19)
                    System.out.print(i+"    ");
                else
                    System.out.print(i+"   ");
            }
        }
        System.out.println();
        System.out.print(" --------------------------------------------------");
        System.out.println("-----------");
        for (int i = 0; i < 30; i++) {  
            if (CheckRowEmpty(i)||i==15) {
                System.out.print(" ");
                for (int j = 0; j < 12; j++) {
                    if(j==0)
                        System.out.print("| "+table[i][j]+"    ");
                    else if(j==11)
                        System.out.print(table[i][j]+" |");
                    else if(j==5)
                        System.out.print(table[i][j]+"  |  ");
                    else
                        System.out.print(table[i][j]+"    ");
                }
                if(i!=29) {
                    System.out.print("\n | ");
                    System.out.print("                            |       ");
                    System.out.println("                      |");
                }else
                    System.out.println();
            }
        }
        System.out.print(" --------------------------------------------------");
        System.out.println("-----------");
        System.out.print("   ");
        for (int i = 1; i <= 12; i++) {
            if(i<10) {
                if(i==6)
                    System.out.print(i+"     ");
                else
                    System.out.print(i+"    ");
            }
            else
                System.out.print(i+"   ");
        }
        System.out.print("\n\n");
        System.out.print("                               X\n");
    }
    public String toString() {
        String a="         ";
        String b=" \b\b\b\b\b\b\b\b";
        String nokta="";
        String result="";

        result+="   ";
        for (int i = 24; i > 12; i--)
        {
            if (i < 10)
                result+=i+"        \b";
            else {
                if(i==19)
                    result+=i+"        \b\b\b\b\b\b";
                else
                    result+=i+"      \b";
            }
        }
        result+="\n";
        result+=" ---------------------------------------------------------------";
        result+="---------------------------------------------------------------\n";
        for (int i = 0; i < 30; i++) {  
            if (CheckRowEmpty(i)||i==15) {
                result+=" ";
                for (int j = 0; j < 12; j++) {
                    if(table[i][j]=='.')
                        nokta=table[i][j]+"\b";
                    else if(table[i][j]=='X')
                        nokta=table[i][j]+" ";
                    else
                        nokta=table[i][j]+"";
                    if(j==0)
                        result+="| "+nokta+"\b\b\b\b\b";
                    else if(j==11)
                        result+=nokta+"\b\b|\b";
                    else if(j==5)
                        result+=nokta+"\b\b\b\b\b|\b\b\b\b\b";
                    else
                        result+=nokta+"\b\b\b\b\b";
                }
                if(i!=29) {
                    result+="\n |\b";
                    result+="\t\t\b\b\b"+b+"|";
                    result+="\t\t\t\b\b\b\b\b\b\b\b\b|\n";
                }else
                    result+="\n";
            }
        }
        result+=" ---------------------------------------------------------------";
        result+="---------------------------------------------------------------\n";
        result+="   ";
        for (int i = 1; i <= 12; i++) {
            if(i<10) {
                if(i==6)
                    result+=i+"       \b\b\b\b\b\b";
                else
                    result+=i+"   \b\b\b\b";
            }
            else
                result+=i+"  \b\b\b";
        }
        result+="\n\n";
        result+="\n";
        
        return result;
    }
}
