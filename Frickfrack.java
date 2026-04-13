import java.util.Random;
import java.util.Scanner;


public class Frickfrack{
    private static String[][]tab = new String[3][3];
    private static boolean pecasColocadas = true;   //para colocar e true epara mover e false
    //quantas pecas ja foram colocadas para cada um, no 3 no maximo para cada peca
    private static int pecaXColocadas = 0;
    private static int pecaOColocadas = 0;

    //limite para que nao haja jogadas infinitas 
    private static final int limite_jogadas = 1000;
    private static Random rand = new Random();

    public static void inicial(){
       for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                tab[i][j] =" ";
            }
       }  
        pecasColocadas = true;
       pecaXColocadas = 0;
       pecaOColocadas = 0;
    }

    private static boolean dentro(int linha, int coluna){
        return linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3;
    }

    public static boolean jogar(String peca, int linha, int coluna){
        //ta verifica si linha e coluna sta dento limite
        if(linha < 0 || linha > 2 || coluna < 0 || coluna > 2){
            System.out.println("Posicao invalida. Escolha uma linha e coluna entre 0 e 2.");
            return false;
        }
        //ta verifica si quel espaco la ja foi jugado
        if(tab[linha][coluna].equals(" ")){
            tab[linha][coluna] = peca;
            
            if(peca.equals("X")){
                pecaXColocadas++;
            }else{
                pecaOColocadas++;
            }

            if(pecaXColocadas == 3 && pecaOColocadas == 3) {
                pecasColocadas = false; //agora passa para fase de mover as pecas
            }

            System.out.println("Agora e a vez do outro jogador");
            return true;
        }else{
            System.out.println("Esse espaco ja foi jogado, escolha outra");
            return false;
        }
    }

    //aqui veremos qual e posicao de uma peca e qual sera a sua posicao depois(origem e Destino) 
    public static boolean mover(String peca, int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino){
        if(!dentro(linhaOrigem, colunaOrigem) || !dentro(linhaDestino, colunaDestino)){
            System.out.println("posicao errada, tem que ser entre 0 a 2");
            return false;     
        }

        if(linhaOrigem == linhaDestino && colunaOrigem == colunaDestino) {
            System.out.println("a origem e destino das pecas nao podem ser iguais");
            return false;
        }

        if(!tab[linhaOrigem][colunaOrigem].equals(peca)) {
            System.out.println("voce so pode mover uma peca sua para outro espaco");
            return false;
        }

        if(!tab[linhaDestino][colunaDestino].equals(" ")) {
            System.out.println("Esse espaco tem de estar vazio");
            return false;
        }

        tab[linhaOrigem][colunaOrigem] = " ";
        tab[linhaDestino][colunaDestino] = peca;

        System.out.println("Agora e a vez do outro jogador");
        return true;
    }


    public static void tabuleiro(){
        System.out.println("| "+tab[0][0]+"  | "+tab[0][1]+"  | "+tab[0][2]+ "  | ");
        System.out.println("+----+----+----+");
        System.out.println("| "+tab[1][0]+"  | "+tab[1][1]+"  | "+tab[1][2]+ "  | ");
        System.out.println("+----+----+----+");
        System.out.println("| "+tab[2][0]+"  | "+tab[2][1]+"  | "+tab[2][2]+ "  | ");
        System.out.println("+----+----+----+");
    }

    public static void main(String[] args){
       Scanner ler = new Scanner(System.in);

        inicial();
        boolean qualVencedor = false;

        // as 3 primeiras jogadas 
        for(int i=0; i<3 && !qualVencedor; i++){
            // jogador X
            boolean jogadaValida;
            do{
                System.out.println("jogador 'X' :");
                System.out.println("linha: ");
                int linha = ler.nextInt();
                System.out.println("coluna: ");
                int coluna = ler.nextInt();
                jogadaValida = jogar("X", linha, coluna);
            }while(!jogadaValida);
            tabuleiro();
            if(verificarVencedor()){
                qualVencedor = true;
                break;
            }

            // jogador O
            do{
                System.out.println("jogador 'O' :");
                System.out.println("linha: ");
                int linha = ler.nextInt();
                System.out.println("coluna: ");
                int coluna = ler.nextInt();
                jogadaValida = jogar("O", linha, coluna);
            }while(!jogadaValida);
            tabuleiro();
            if(verificarVencedor()){
                qualVencedor = true;
                break;
            }
        }
         for (int jogadas = 1; jogadas <= limite_jogadas && !qualVencedor; jogadas++) {
            boolean vezX = false;
            String pecaAtual = vezX ? "X" : "O";

            System.out.println("\nJogada " + jogadas + " | Vez do jogador: " + pecaAtual);
            System.out.println("fase atual: " + (pecasColocadas ? "colocar" : "mover"));
            tabuleiro();

            boolean jogadaValida;

            if(pecasColocadas) {
                //colocar a peca
                do{
                    System.out.println("jogador" + pecaAtual + "colocar");
                    System.out.println("linha [0-2]: ");
                    int linha = ler.nextInt();
                    System.out.println("coluna [0-2]: ");
                    int coluna = ler.nextInt();
                    jogadaValida = jogar(pecaAtual, linha, coluna);
                }while(!jogadaValida);

            }else{
                //mover a peca
                do{
                    System.out.println("jogador" + pecaAtual + "mover");
                    System.out.println("Escolha a origem - linha [0-2]: ");
                    int lOrig = ler.nextInt();
                    System.out.println("Escolha a origem - coluna [0-2]: ");
                    int cOrig = ler.nextInt();

                    System.out.println("escolha o destino - linha [0-2]: ");
                    int lDest = ler.nextInt();
                    System.out.println("escolha o destino - coluna [0-2]: ");
                    int cDest = ler.nextInt();

                    jogadaValida = mover(pecaAtual, lOrig, cOrig, lDest, cDest);
                 }while(!jogadaValida);
            }

            //verifica o vencedor apos cada jogada
            if(verificarVencedor()) {
                qualVencedor = true;
                break;
            }

            //troca a vez da peca
            vezX = !vezX;
        }

        if(!qualVencedor){
            System.out.println("\nchegou no limite de jogadas e nao teve vencendor (" + limite_jogadas + ")");
            System.out.println("Da empate, nhos torna Juga");
        }
    }

    //verificarVencedor e satatic para que possa ser cahmado pelo metodo main
    public static boolean verificarVencedor(){
        if(tab[0][0].equalsIgnoreCase("X") && tab[0][1].equalsIgnoreCase("X") && tab[0][2].equalsIgnoreCase("X")){
            System.out.println("O 'X' e o vencedor");
            return true;
        }else if(tab[0][0].equalsIgnoreCase("O") && tab[0][1].equalsIgnoreCase("O") && tab[0][2].equalsIgnoreCase("O")){
                System.out.println("O 'O' e o vencedor");
                return true;
        }else if(tab[1][0].equalsIgnoreCase("X") && tab[1][1].equalsIgnoreCase("X") && tab[1][2].equalsIgnoreCase("X")){
                System.out.println("O 'X' e o vencedor");
                return true;
        }else if(tab[1][0].equalsIgnoreCase("O") && tab[1][1].equalsIgnoreCase("O") && tab[1][2].equalsIgnoreCase("O")){
                System.out.println("O 'O' e o vencedor"); 
                return true;
        }else if(tab[2][0].equalsIgnoreCase("X") && tab[2][1].equalsIgnoreCase("X") && tab[2][2].equalsIgnoreCase("X")){
            System.out.println("O 'X' e o vencedor");
            return true;
        }else if(tab[2][0].equalsIgnoreCase("O") && tab[2][1].equalsIgnoreCase("O") && tab[2][2].equalsIgnoreCase("O")){
            System.out.println("O 'O' e o vencedor");
            return true;
        }else if(tab[0][0].equalsIgnoreCase("X") && tab[1][0].equalsIgnoreCase("X") && tab[2][0].equalsIgnoreCase("X")){
            System.out.println("O 'X' e o vencedor");
            return true;
        }else if(tab[0][0].equalsIgnoreCase("O") && tab[1][0].equalsIgnoreCase("O") && tab[2][0].equalsIgnoreCase("O")){
            System.out.println("O 'O' e o vencedor");
            return true;
        }else if(tab[0][1].equalsIgnoreCase("X") && tab[1][1].equalsIgnoreCase("X") && tab[2][1].equalsIgnoreCase("X")){
            System.out.println("O 'X' e o vencedor");
            return true;
        }else if(tab[0][1].equalsIgnoreCase("O") && tab[1][1].equalsIgnoreCase("O") && tab[2][1].equalsIgnoreCase("O")){
            System.out.println("O 'O' e o vencedor");
            return true;
        }else if(tab[0][2].equalsIgnoreCase("X") && tab[1][2].equalsIgnoreCase("X") && tab[2][2].equalsIgnoreCase("X")){
            System.out.println("O 'X' e o vencedor");
            return true;
        }else if(tab[0][2].equalsIgnoreCase("O") && tab[1][2].equalsIgnoreCase("O") && tab[2][2].equalsIgnoreCase("O")){
            System.out.println("O 'O' e o vencedor");
            return true;
        }else if(tab[0][0].equalsIgnoreCase("X") && tab[1][1].equalsIgnoreCase("X") && tab[2][2].equalsIgnoreCase("X")){
            System.out.println("O 'X' e o vencedor");
            return true;
        }else if(tab[0][0].equalsIgnoreCase("O") && tab[1][1].equalsIgnoreCase("O") && tab[2][2].equalsIgnoreCase("O")){
            System.out.println("O 'O' e o vencedor");
            return true;
        }else if(tab[2][0].equalsIgnoreCase("X") && tab[1][1].equalsIgnoreCase("X") && tab[0][2].equalsIgnoreCase("X")){
            System.out.println("O 'X' e o vencedor");
            return true;
        }else if(tab[2][0].equalsIgnoreCase("O") && tab[1][1].equalsIgnoreCase("O") && tab[0][2].equalsIgnoreCase("O")){
            System.out.println("O 'O' e o vencedor");
            return true;
        }
        return false;
    }

    
}