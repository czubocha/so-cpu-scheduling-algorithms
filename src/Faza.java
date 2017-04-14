class Faza {
    private int _idFazy;
    private int _dlFazy;

    Faza(int idFazy, int dlFazy) {
	_idFazy = idFazy;
	_dlFazy = dlFazy;
    }

    int dlFazy() {
	return _dlFazy;
    }

    int idFazy() {
	return _idFazy;
    }
    
    void zmienDlFazy(int n) {
	_dlFazy -= n;
    }
}
