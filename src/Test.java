import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Test {
    static ArrayList<Process> listaProcesow = new ArrayList<Process>();
    static ArrayList<Process> listaProcesow1 = new ArrayList<Process>();
    static ArrayList<Process> listaProcesow2 = new ArrayList<Process>();
    static ArrayList<Process> listaProcesow3 = new ArrayList<Process>();

    static Random generator = new Random();
    static int sredni = 10;
    static int odchylenie = 5;

    static void wczytywanie(String plik) throws IOException {
	BufferedReader reader = new BufferedReader(new FileReader(plik));
	String line = null;
	while ((line = reader.readLine()) != null) {
	    Scanner scanner = new Scanner(line);
	    while (scanner.hasNext()) {
		scanner.useDelimiter(",|;|\"");
		int id = scanner.nextInt();
		int czasWejscia = scanner.nextInt();
		int dlugosc = scanner.nextInt();
		listaProcesow.add(new Process(id, 1, dlugosc, czasWejscia));
		System.out.println("Wczytano proces o ID: " + id + "  dlugosci: " + dlugosc + "  czasie wejscia: "
			+ czasWejscia);
	    }
	    scanner.close();
	}
	reader.close();
    }

    static void stworzProcesy(int iloscProcesow, int maxIloscFaz, int maxDlugoscFaz) {
	for (int i = 0; i < iloscProcesow; i++) {
	    int czasWejscia = (int) (sredni + odchylenie * generator.nextGaussian());
	    if (czasWejscia > 0)
		listaProcesow.add(new Process(i, generator.nextInt(maxIloscFaz) + 1,
			generator.nextInt(maxDlugoscFaz) + 1, czasWejscia));
	    else
		listaProcesow.add(new Process(i, generator.nextInt(maxIloscFaz) + 1,
			generator.nextInt(maxDlugoscFaz) + 1, generator.nextInt(20) + 1));
	}
    }

    static void stworzProcesyBezFaz(int iloscProcesow, int maxDlugoscFaz) {
	for (int i = 0; i < iloscProcesow; i++) {
	    int czasWejscia = (int) (sredni + odchylenie * generator.nextGaussian());
	    if (czasWejscia > 0)
		listaProcesow.add(new Process(i, 1, generator.nextInt(maxDlugoscFaz) + 1, czasWejscia));
	    else
		listaProcesow.add(new Process(i, 1, generator.nextInt(maxDlugoscFaz) + 1, generator.nextInt(20) + 1));
	}
    }

    public static void main(String args[]) throws IOException {
//	wczytywanie("przyklad.csv");
//	stworzProcesy(10,10,20);
	stworzProcesyBezFaz(80, 55);
	for (Process x : listaProcesow)
	    x.kopiowanieListyFaz();
	listaProcesow1 = (ArrayList<Process>) listaProcesow.clone();
	listaProcesow2 = (ArrayList<Process>) listaProcesow1.clone();
	listaProcesow3 = (ArrayList<Process>) listaProcesow2.clone();
	
	Symulacja sym = new Symulacja("FCFS");
	Algorytmy alg = new Algorytmy();
	alg.FCFS(listaProcesow, sym);
	sym.zakonczSymulacje();

//	Symulacja sym1 = new Symulacja("SJF");
//	Algorytmy alg1 = new Algorytmy();
//	alg1.SJF(listaProcesow1, sym1);
//	sym1.zakonczSymulacje();

//	Symulacja sym2 = new Symulacja("SJFzWywlaszczeniem");
//	Algorytmy alg2 = new Algorytmy();
//	alg2.SJFzWywlaszczeniem(listaProcesow2, sym2);
//	sym2.zakonczSymulacje();

//	Symulacja sym3 = new Symulacja("Rotacyjny");
//	Algorytmy alg3 = new Algorytmy();
//	alg3.Rotacyjny(listaProcesow3, sym3, 20);
//	sym3.zakonczSymulacje();
    }
}
