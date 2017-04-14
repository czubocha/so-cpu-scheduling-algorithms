import java.util.ArrayList;
import java.util.Random;

class Process implements Comparable<Process> {
    ArrayList<Faza> fazy = new ArrayList<Faza>();
    ArrayList<Faza> fazy1 = new ArrayList<Faza>();
    ArrayList<Faza> fazy2 = new ArrayList<Faza>();

    void kopiowanieListyFaz() {
	fazy1 = (ArrayList<Faza>) fazy.clone();
	fazy2 = (ArrayList<Faza>) fazy1.clone();
    }

    private int _czasWejscia;
    private int _czasOczekiwania;
    private int _idProcesu;
    Random generator = new Random();

    Process(int id, int iloscFaz, int dlugosc, int czasWejscia) {
//	 for(int i = 0; i < iloscFaz + 1; i++) //do rotacyjnego
	fazy.add(new Faza(id, dlugosc));
	_idProcesu = id;
	_czasWejscia = czasWejscia;
	_czasOczekiwania = 0;
    }

    int idProcesu() {
	return _idProcesu;
    }

    int czasWejscia() {
	return _czasWejscia;
    }

    int czasOczekiwania() {
	return _czasOczekiwania;
    }

    void dodajCzasOczekiwaniaProcesu(int n) {
	_czasOczekiwania += n;
    }

    public int compareTo(Process p) {
	return Integer.compare(_czasWejscia, p._czasWejscia);
    }

    int dlugoscProcesu() {
	int suma = 0;
	for (int i = 0; i < fazy.size(); i++)
	    suma += fazy.get(i).dlFazy();
	return suma;
    }
}