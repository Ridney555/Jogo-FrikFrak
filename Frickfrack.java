import java.util.Scanner;

public class Frickfrack{
    private static String[][]tab = new String[3][3];
    private int i = 1;
    private int jogador = 1;

    public static void inicial(){
       for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                tab[i][j] =" ";
            }
       }
    }

    public static void jogar(String peca, int linha, int coluna){
        tab[linha][coluna] = peca;

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
        //as 3 primeiras jogadas 
        for(int i=0; i<3; i++){
            //jogador X
            System.out.println("jogador 'X' :");
            System.out.println("linha: ");
            int linha = ler.nextInt();
            System.out.println("coluna: ");
            int coluna = ler.nextInt();
            jogar("X",linha , coluna);
            tabuleiro();
            //jogador O
            System.out.println("jogador 'O' :");
            System.out.println("linha: ");
            linha = ler.nextInt();
            System.out.println("coluna: ");
            coluna = ler.nextInt();
            jogar("O",linha , coluna);
            tabuleiro();
        }
    }

    public void verificarEstadoPartida(){
        for(int i=0; i <=3; i++){

            //verificar a colunas 
            if(tab[0][1] == tab[1][1] && tab[0][1] == tab[2][1] && tab[0][1] != " "){
               if(jogador == 0){
               tabuleiro();
               System.out.println("vencedor e o 'X'"); 
               }
            }else{
                System.out.println("Vencedor e o 'O'");
            }
        }
        i++;      //passa para a proxima rodada 
    }
}