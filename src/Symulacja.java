import java.io.FileNotFoundException;
import java.io.PrintWriter;

class Symulacja {
    PrintWriter zapis;

    Symulacja(String nazwa) throws FileNotFoundException {
	zapis = new PrintWriter(nazwa + ".txt");
    }

    PrintWriter symulacjaDoPliku() throws FileNotFoundException {
	return zapis;
    }

    void dopiszDoPlikuLinie(String dopisz) throws FileNotFoundException {
	symulacjaDoPliku().println(dopisz);
    }

    void zakonczSymulacje() throws FileNotFoundException {
	symulacjaDoPliku().close();
    }
}
