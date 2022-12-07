import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

/*
Kroki do wykonania w dodawaniu:
1. Odwrócenie liczb - musimy wyrównać rozmiar tablic, a łatwiej jest dopisać 0 na końcu
2. Sprawdzenie długości tablic, wybranie dłużeszej
3. Zmiana z tablic na listy - łatwiej dopisywać dane do list
4. Wyrównanie długości list - dopisanie 0 do krótszej listy
5. Dodawanie liczb
6. Odejmowanie liczb
 */

public class Main {

    public static void main(String[] args) {

        //wpisanie liczb
        int[] tableA = {1,2,3};
        int[] tableB = {2,4,6};

        //zmiana tablic na listy (zeby dodawać 0 do listy)
        ArrayList<Integer> listA = convertToList(tableA);
        ArrayList<Integer> listB = convertToList(tableB);

        //odwrócenie liczb
        listA = reverseList(listA);
        listB = reverseList(listB);

        //int[] reversedTableA = reverseTable(tableA);
        //int[] reversedTableB = reverseTable(tableB);

        //sprawdzenie długości dłuższej liczby
        int tableLength = calculateLength(tableA,tableB);

        //wyrównanie długości list
        ArrayList<Integer> eqsListA = fulfillment(tableLength, listA);      //equal size Lists
        ArrayList<Integer> eqsListB = fulfillment(tableLength, listB);

        /*
        dodawanie list i odwracanie list
        */

        ArrayList<Integer> sum = add(eqsListA, eqsListB);
        sum = reverseList(sum); //odwrócenie wyniku

        /*
        odejmowanie list i odwracanie list
        */
        ArrayList<Integer> diff = sub(eqsListA, eqsListB);
        diff =reverseList(diff); //odwrócenie wyniku

        System.out.println("Pierwsza liczba");
        for (int i = 0; i<tableA.length; i++){
            System.out.print(tableA[i]);
        }
        System.out.println();

        System.out.println("Druga liczba");
        for (int i = 0; i< tableB.length; i++){
            System.out.print(tableB[i]);
        }
        System.out.println();

        //wyświetlenie wyniku
        System.out.println("Wynik dodawania");
        for (int i=0; i<sum.size(); i++){
            System.out.print(sum.get(i));
        }
        System.out.println();

        System.out.println("Wynik odejmowania");
        for (int i=0; i<diff.size(); i++){
            System.out.print(diff.get(i));
        }
        System.out.println();


        ArrayList<Integer> multi = multiply(eqsListA, eqsListB);
        System.out.println("Suma mnożenia");
        for (int i=0; i<multi.size(); i++){
            System.out.print(multi.get(i));
        }


    }

    //obliczenie długości dłuższej tabeli
    public static int calculateLength(int[] a, int[] b){
        int length = Math.max(a.length, b.length);
        return length;
    }

    //zmiana tablicy na listę
    public static ArrayList<Integer> convertToList (int[] table){

        ArrayList<Integer> list = new ArrayList<>();

        for (int i=0; i<table.length; i++){
            list.add(table[i]);
            //System.out.println(list.get(i));
        }
        return list;
    }

    //dopełnienie krótszej listy zerami
    public static ArrayList<Integer> fulfillment (int diff, ArrayList<Integer> list){               //diff = max length

        int i = 0;
        while (i<diff-list.size()){ //iteratorem jest list.size() - on się zmienia w każdym przejściu
            list.add(0);
        }
        return list;
    }

    //dodawanie dwóch list i zwrócenie wyniku
    public static ArrayList<Integer> add (ArrayList<Integer> listA, ArrayList<Integer> listB){

        int carry = 0;
        ArrayList<Integer> resultList= new ArrayList<>();

        for (int i = 0; i<listA.size(); i++){
            resultList.add((listA.get(i)+listB.get(i)+carry)%10); //dodanie liczb i wzięcie tylko jedności
            carry = Math.floorDiv(listA.get(i)+listB.get(i)+carry,10); //zapisanie cześci dzięsiętnej do dodania w następnej linii
        }               // 8/10=0.8 ~~ 0  18/10=1.8 ~~ 1

        if (carry>0){
            resultList.add(carry);
        } //jeżeli jeszcze na koniec została część dziesiętna, dopisujemy ją

        return resultList;
    }

    //odejmowanie dwóch liczb i zwrócenie wyniku
    public static ArrayList<Integer> sub (ArrayList<Integer> listA, ArrayList<Integer> listB){

        int carry = 0;
        int tmp = 0;
        ArrayList<Integer> resultList= new ArrayList<>();

        for (int i=0; i<listA.size(); i++){
            tmp = listA.get(i)-listB.get(i)-carry;
            carry = 0;
            if (tmp < 0){ //jeżeli różnica byłaby mniejsza od 0
                tmp += 10; //dodajemy 10
                carry = 1; //zapożyczamy 1
            }
            resultList.add(tmp);
        }

        resultList = removeZeros(resultList); //usuwanie zer na końcu listy


        return resultList;
    }

    //usuwanie zer
    public static ArrayList<Integer> removeZeros(ArrayList<Integer> list){
        for (int i=list.size()-1; i >= 0; i--) {
            if (list.size()==1){ //jeżeli długość wyniku jest równa 1, nie ma co usuwać
                break;
            }
            else if (list.get(i) == 0){ //jeżeli ostatnia cyfra to 0, usuń ją
                list.remove(i);
            }
            else //jeżeli ostatnia cyfra nie jest 0, to zakończ pętle
                break;
        }
        return list;
    }

    //mnożenie, przyjmuje równej długości, odwrócone liczby
    public static ArrayList<Integer> multiply (ArrayList<Integer> listA, ArrayList<Integer> listB){

        ArrayList<ArrayList<Integer>> aggrList = new ArrayList<>();         //aggregateList - List of Lists
        ArrayList<Integer> singleList = new ArrayList<>();                  //pojedyncza lista, wynikmnożenia przez jedną z cyfr
        int carry = 0;                                                      //flaga przeniesienia

        for (int i=0; i<listB.size(); i++){
            for (int j=0; j<listA.size(); j++){
                singleList.add((listB.get(i)*listA.get(j)+carry)%10);           //reszta z dzielenia
                carry = Math.floorDiv(listB.get(i)*listA.get(j)+carry,10);      //część dziesiątek z dzielenia przez 10
                //System.out.println(singleList.get(j));
            }

            if (carry!=0) {                                                     //Jeśli jest coś w carry to dodaj na koniec
                singleList.add(carry);
            }
            singleList =reverseList(singleList);                         //obracamy i wpisujemy do aggr w dobrej kolejnosci
            aggrList.add(new ArrayList<>(singleList));                          // Dodajemy listę do listy List

            carry = 0;                                                          //czyszczenie carry i single list
            singleList.clear();
        }
        // dopisanie zer na koncu sum czastkowych (mamy listy w dobrej kolejności)
        int j=0;
        for(int i=1;i<aggrList.size(); i++ )
        {
            while(j<i) {
                aggrList.get(i).add(0);
                j++;
            }
            j=0;
        }

        //odwrocenie list do wpisania zer na początku

        for(int i=0;i<aggrList.size(); i++){
            aggrList.set(i,reverseList(aggrList.get(i)));
        }
        //wyciągniecie najdłuższej długości, żeby wiedziec ile zer na początku dopisać
        int max = 0;
        for(int i=0;i<aggrList.size(); i++){
            max = Math.max(max,aggrList.get(i).size());
        }

        //dopisanie zer na poczatku (listy mamy w odwróconej kolejności)
        for(int i=0;i<aggrList.size(); i++){

            aggrList.set(i,fulfillment(max, aggrList.get(i)));
            System.out.println("lista cząstkowa odwrocona i z dobra dł "+aggrList.get(i));
        }


        //sumowanie list cząstkowych trzymanych w aggrList

        int length = 0;
        ArrayList<Integer> sum = new ArrayList<>();
        sum = fulfillment(max,sum);                                     //wyrównanie długości sumy konćowej, do najdłuższej listy

        for (int i = 0; i<aggrList.size(); i++){

            sum = new ArrayList<>(add(sum, aggrList.get(i)));               //dodajemy cząstkowe listy

            System.out.println("suma: " + sum); //można wyłaczyć

        }
        sum = reverseList(sum);                 //odwrócenie wyniku do dobrej postaci

        return sum;
    }

    //odwrócenie kolejności listy
    public static ArrayList<Integer> reverseList(ArrayList<Integer> list){
        ArrayList<Integer> reversedList = new ArrayList<>();
        int j = 0;

        //System.out.println("odwrocona lista");
        for (int i=list.size()-1; i >= 0; i--) {
            reversedList.add(list.get(i));
            //System.out.println(reversedList.get(j));
            j++;
        }
        return reversedList;
    }


}





















/*
    //odwrócenie kolejności tablicy
    public static int[] reverseTable(int[] table){
        int[] reversedTable = new int[table.length];
        int j = 0;

        for (int i=table.length-1; i >= 0; i--) {
            reversedTable[j]=table[i];
            j++;
        }
        return reversedTable;
    }

    //zmiana listy na tablicę
    public static int[] convertToTable (ArrayList<Integer> list){
        int[] table = new int[list.size()];

        for (int i = 0; i<list.size(); i++){
            table[i] = list.get(i);
        }
        return table;
    }
*/
