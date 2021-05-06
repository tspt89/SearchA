import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Estado implements Comparable<Estado> {
    private int n, f, g, h;
    int matrix[][];
    char move;
    UUID id;
    ArrayList<Estado> children;

    Estado(int n, Scanner sc){
        id = UUID.randomUUID();
        this.n = n;
        this.matrix = new int[n][n];
        for(int i=0; i < n; i++){
            String[] s = sc.nextLine().split(",");
            for(int j=0; j<n; j++){
                this.matrix[i][j] = Integer.parseInt(s[j]);
            }
        }
    }

    Estado(int n, int g){
        id = UUID.randomUUID();
        this.n = n;
        this.g = g;
        this.matrix = new int[n][n];
    }

    public int getN(){
        return this.n;
    }

    public int getG(){
        return this.g;
    }

    public void print(){
        System.out.printf("########### %s ###########\n",this.id);
        System.out.printf("n: %d, g: %d, h: %d, f: %d\n",this.n,this.g,this.h,this.f);
        for(int i=0; i < n; i++){
            for(int j=0; j < n; j++){
                System.out.print(this.matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("########################");
    }

    public int[] getEmptySpace(){
        int[] z = {-1,-1};
        for(int i=0; i < n; i++){
            for(int j=0; j<n; j++){
                if(matrix[i][j] == 0){
                    z[0] = i;
                    z[1] = j;
                    return z;
                }
            }
        }
        return z;
    }

    public Estado copyFrom(Estado e){
        for(int i=0; i < n; i++){
            for(int j=0; j < n; j++){
                int n = e.matrix[i][j];
                this.matrix[i][j] = n;
            }
        }
        return this;
    }

    public Estado compareToFinal(Estado f){
        System.out.println("COMPARE TO FINAL:");
        f.print();
        System.out.println("COMPARE TO ACTUAL:");
        this.print();
        this.h = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.printf("[%d][%d]: %d - %d",i,j,matrix[i][j],f.matrix[i][j]);
                if(matrix[i][j] == f.matrix[i][j]){
                    System.out.println("\t Entra");
                    h++;
                } else {
                    System.out.println("");
                }
            }
        }

        this.f = this.h - this.g;
        return this;
    }
    //true -> equal, false -> not equal
    public boolean compareToOther(Estado e){
        int n = e.getN();
        int count = 0;
        for(int i = 0; i < n; i++){
            for(int j=0; j < n; j++){
                if(this.matrix[i][j] == e.matrix[i][j]){
                    count++;
                }
            }
        }

        return count == (n*n);
    }


    @Override
    public int compareTo(Estado e) {
        return (this.f < e.f ? -1 :
                (this.f == e.f ? 0 : 1));
    }
}
