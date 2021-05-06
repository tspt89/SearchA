import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    static Estado init, fin, actual;
    static ArrayList<Estado> open, closed, all;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        open = new ArrayList();
        closed = new ArrayList();
        all = new ArrayList();
        int n;

        n = Integer.parseInt(sc.nextLine());
        init = new Estado(n, sc);
        open.add(init);
        all.add(init);
        fin = new Estado(n, sc);
        init.print();
        System.out.println("-----------");
        fin.print();
        System.out.println("-----------");

        actual = init;
        ArrayList<Estado> children;
        do {
            System.out.println("Entra al ciclo actual vs final : " + actual.id + " - " + actual.compareToOther(fin));
            children = createChildren(actual); //Create the corresponding children
            sort(children); //Sorts the children according to f(n)
            all.addAll(children);
            open.addAll(children); //Add all the children to the open states
            sort(open);
            children.clear(); //Clears the arraylist


            open.remove(actual); //Removes the actual node from the open nodes


            closed.add(actual); //And move it to the close nodes
            actual = open.get(0);

            if (actual.compareToOther(fin)) {
                open.remove(actual);
                closed.add(actual);
            }


        } while (!actual.compareToOther(fin));

        closed.forEach(estado ->
                System.out.println(estado.id + " - " + estado.move));

        all.forEach(estado ->
                estado.print());
    }

    public static ArrayList<Estado> createChildren(Estado parent) {
        ArrayList<Estado> res = new ArrayList();
        char[] m = {'u', 'd', 'l', 'r'};
        for (char c : m) {
            Estado tmp = moveState(parent, c);
            if (tmp != null) {
                if (!compareWithOtherNode(tmp, all)) {
                    res.add(tmp);
                }
            }
        }
        return res;
    }

    //true -> equal, false -> not equal
    public static boolean compareWithOtherNode(Estado e, ArrayList<Estado> all) {
        Iterator<Estado> it = all.iterator();

        while (it.hasNext()) {
            Estado ac = it.next();
            if (e.compareToOther(ac)) {
                System.out.println("Se omite repetido");
                System.out.println("###########Nuevo###############");
                e.print();
                System.out.println("-----------All-------------");
                ac.print();
                System.out.println("#############################");

                return true;
            }

        }
        return false;
    }

    public static void sort(ArrayList<Estado> estados) {
        Collections.sort(estados, Collections.reverseOrder());
    }

    public static Estado moveState(Estado e, char m) {
        int n = e.getN();
        int[] z = e.getEmptySpace();
        Estado newMove = new Estado(n, e.getG() + 1).copyFrom(e);

        if (z[0] >= 0 && z[0] < n - 1 && m == 'u') { // Up
            newMove.matrix[z[0]][z[1]] = newMove.matrix[z[0] + 1][z[1]];
            newMove.matrix[z[0] + 1][z[1]] = 0;
            newMove.move = 'u';

        } else if (z[0] > 0 && z[0] <= n - 1 && m == 'd') { // Down
            newMove.matrix[z[0]][z[1]] = newMove.matrix[z[0] - 1][z[1]];
            newMove.matrix[z[0] - 1][z[1]] = 0;
            newMove.move = 'd';

        } else if (z[1] >= 0 && z[1] < n - 1 && m == 'l') { // Left
            newMove.matrix[z[0]][z[1]] = newMove.matrix[z[0]][z[1] + 1];
            newMove.matrix[z[0]][z[1] + 1] = 0;
            newMove.move = 'l';

        } else if (z[1] > 0 && z[1] <= n - 1 && m == 'r') { // Right
            newMove.matrix[z[0]][z[1]] = newMove.matrix[z[0]][z[1] - 1];
            newMove.matrix[z[0]][z[1] - 1] = 0;
            newMove.move = 'r';

        } else {
            return null;
        }

        return newMove.compareToFinal(fin);
    }
}
