import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

class Algorytmy {
    ArrayList<Process> listaWszystkichProcesow = new ArrayList<Process>();
    ArrayList<Process> listaDostepnychProcesow = new ArrayList<Process>();
    ArrayList<Process> listaSkonczonychProcesow = new ArrayList<Process>();

    void FCFS(ArrayList<Process> listaProcesow, Symulacja sym) throws FileNotFoundException {
	listaWszystkichProcesow = listaProcesow;
	int zegar = 0;
	int sumaOczekwiania = 0;
	int iloscProcesow = listaWszystkichProcesow.size();
	while (listaWszystkichProcesow.size() > 0 || listaDostepnychProcesow.size() > 0) {
	    szukajNowychProcesow(zegar);
	    Collections.sort(listaDostepnychProcesow);
	    if (listaDostepnychProcesow.size() > 0) {
		Process p = listaDostepnychProcesow.get(0);
		p.dodajCzasOczekiwaniaProcesu(zegar - p.czasWejscia() - p.czasOczekiwania());
		sym.dopiszDoPlikuLinie(String.format("%7s%6d%10s%3d%20s%5d%20s%5d%20s%5d", "Zegar: ", zegar, "Id: ",
			p.idProcesu(), "Czas trwania: ", p.dlugoscProcesu(), "Czas pojawienia: ", p.czasWejscia(),
			"Czas Oczekiwania", p.czasOczekiwania()));
		zegar += p.dlugoscProcesu();
		dodajCzasOczekiwania(p, zegar);
		listaSkonczonychProcesow.add(p);
		listaDostepnychProcesow.remove(p);
	    } else
		zegar++;
	}
	sumaOczekwiania = sumaOczekiwan();
	sym.dopiszDoPlikuLinie(String.format("%30s%6f", "Sredni czas oczekiwania: ", (float) sumaOczekwiania
		/ iloscProcesow));
    }

    void SJF(ArrayList<Process> listaProcesow, Symulacja sym) throws FileNotFoundException {
	listaWszystkichProcesow = listaProcesow;
	int zegar = 0;
	int sumaOczekwiania = 0;
	int iloscProcesow = listaWszystkichProcesow.size();
	while (listaWszystkichProcesow.size() > 0 || listaDostepnychProcesow.size() > 0) {
	    szukajNowychProcesow(zegar);
	    Collections.sort(listaDostepnychProcesow);
	    if (listaDostepnychProcesow.size() > 0) {
		Process p = wybierzNajkrotszy();
		p.dodajCzasOczekiwaniaProcesu(zegar - p.czasWejscia() - p.czasOczekiwania());
		sym.dopiszDoPlikuLinie(String.format("%7s%4d%5s%3d%20s%5d%20s%5d%25s%5d%20s%7d", "Zegar: ", zegar,
			"Id: ", p.idProcesu(), /* ".", p.fazy.get(0).idFazy(), */
			"Czas trwania: ", p.dlugoscProcesu(), "Czas pojawienia: ", p.czasWejscia(), "Pozosta³y czas fazy: ",
			p.fazy.get(0).dlFazy(), "Czas Oczekiwania", p.czasOczekiwania()));
		zegar += p.fazy.get(0).dlFazy();
		dodajCzasOczekiwania(p, zegar);
		p.fazy.remove(0);
		if (p.fazy.size() <= 0) {
		    listaSkonczonychProcesow.add(p);
		    listaDostepnychProcesow.remove(p);
		}
	    } else
		zegar++;
	}
	sumaOczekwiania = sumaOczekiwan();
	sym.dopiszDoPlikuLinie(String.format("%30s%6f", "Sredni czas oczekiwania: ", (float) sumaOczekwiania
		/ iloscProcesow));
    }

    void SJFzWywlaszczeniem(ArrayList<Process> listaProcesow, Symulacja sym) throws FileNotFoundException {
	listaWszystkichProcesow = listaProcesow;
	int zegar = 0;
	int sumaOczekwiania = 0;
	int iloscProcesow = listaWszystkichProcesow.size();
	while (listaWszystkichProcesow.size() > 0 || listaDostepnychProcesow.size() > 0) {
	    szukajNowychProcesow(zegar);
	    Collections.sort(listaDostepnychProcesow);
	    if (listaDostepnychProcesow.size() > 0) {
		Process p = wybierzNajkrotszy();
		sym.dopiszDoPlikuLinie(String.format("%7s%4d%5s%3d%20s%5d%20s%5d%25s%5d%20s%7d", "Zegar: ", zegar, "Id: ",
			p.idProcesu(), /* ".", p.fazy.get(0).idFazy(), */
			"Czas trwania: ", p.dlugoscProcesu(), "Czas pojawienia: ", p.czasWejscia(), "Pozosta³y czas fazy: ",
			p.fazy1.get(0).dlFazy(), "Czas Oczekiwania", p.czasOczekiwania()));
		zegar++;
		dodajCzasOczekiwania(p, zegar);
		p.fazy1.get(0).zmienDlFazy(1);
		if (p.fazy1.get(0).dlFazy() <= 0)
		    p.fazy1.remove(0);
		if (p.fazy1.size() <= 0) {
		    listaSkonczonychProcesow.add(p);
		    listaDostepnychProcesow.remove(p);
		}
	    } else
		zegar++;
	}
	sumaOczekwiania = sumaOczekiwan();
	sym.dopiszDoPlikuLinie(String.format("%30s%6f", "Sredni czas oczekiwania: ", (float) sumaOczekwiania
		/ iloscProcesow));
    }

    void Rotacyjny(ArrayList<Process> listaProcesow, Symulacja sym, int kwant) throws FileNotFoundException {
	listaWszystkichProcesow = listaProcesow;
	int zegar = 0;
	int sumaOczekwiania = 0;
	int iloscProcesow = listaWszystkichProcesow.size();
	while (listaWszystkichProcesow.size() > 0 || listaDostepnychProcesow.size() > 0) {
	    szukajNowychProcesow(zegar);
	    if (listaDostepnychProcesow.size() > 0) {
		Process p = listaDostepnychProcesow.get(0);
//		p.dodajCzasOczekiwaniaProcesu(zegar - p.czasWejscia() - p.czasOczekiwania());
		sym.dopiszDoPlikuLinie(String.format("%7s%4d%5s%3d%20s%5d%20s%5d%25s%5d%20s%7d", "Zegar: ", zegar,
			"Id: ", p.idProcesu(), /*".", p.fazy.get(0).idFazy(),*/ "Czas trwania: ", p.dlugoscProcesu(),
			"Czas pojawienia: ", p.czasWejscia(), "Pozosta³y czas fazy: ", p.fazy2.get(0).dlFazy(),  "Czas Oczekiwania", p.czasOczekiwania()));
		
		if (p.fazy2.get(0).dlFazy() > kwant) {
		    p.fazy2.get(0).zmienDlFazy(kwant);
		    listaDostepnychProcesow.remove(p);
		    listaDostepnychProcesow.add(p);
		    zegar += kwant;
		    dodajCzasOczekiwania(p, zegar);
		}
		else if (p.fazy2.get(0).dlFazy() <= kwant) {
		    zegar += p.fazy2.get(0).dlFazy();
		    dodajCzasOczekiwania(p, zegar);
		    p.fazy2.remove(0);
		}
		if (p.fazy2.size() <= 0) {
		    listaSkonczonychProcesow.add(p);
		    listaDostepnychProcesow.remove(p);
		}
	    } else
		zegar++;
	}
//	for (int i = 0; i < listaSkonczonychProcesow.size(); i++)
//	     System.out.println(listaSkonczonychProcesow.get(i).czasOczekiwania());
	    sumaOczekwiania = sumaOczekiwan();
	    sym.dopiszDoPlikuLinie(String.format("%30s%6f", "Sredni czas oczekiwania: ", (float) sumaOczekwiania
		    / iloscProcesow));
    }

    Process wybierzNajkrotszy() {
	Process p = listaDostepnychProcesow.get(0);
	for (int i = 0; i < listaDostepnychProcesow.size(); i++)
	    if (listaDostepnychProcesow.get(i).dlugoscProcesu() < p.dlugoscProcesu())
		p = listaDostepnychProcesow.get(i);
	return p;
    }

    void szukajNowychProcesow(int zegar) {
	for (int i = listaWszystkichProcesow.size() - 1; i >= 0; i--) {
	    if (listaWszystkichProcesow.get(i).czasWejscia() <= zegar) {
		listaDostepnychProcesow.add(listaWszystkichProcesow.get(i));
		listaWszystkichProcesow.remove(listaWszystkichProcesow.get(i));
	    }
	}
    }

    void dodajCzasOczekiwania(Process p, int zegar) {
	for (int i = 0; i < listaDostepnychProcesow.size(); i++)
	    if (listaDostepnychProcesow.get(i) != p)
		listaDostepnychProcesow.get(i).dodajCzasOczekiwaniaProcesu(
			zegar - listaDostepnychProcesow.get(i).czasWejscia()
				- listaDostepnychProcesow.get(i).czasOczekiwania());
    }

    int sumaOczekiwan() {
	int suma = 0;
	for (int i = 0; i < listaSkonczonychProcesow.size(); i++)
	    suma += listaSkonczonychProcesow.get(i).czasOczekiwania();
	return suma;
    }
}
