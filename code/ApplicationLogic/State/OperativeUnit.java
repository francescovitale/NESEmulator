package ApplicationLogic.State;

public class OperativeUnit {

	private volatile static OperativeUnit UO = null;			//Singleton

	//Costruttore privato
	private OperativeUnit() {}

	//Punto di ingresso globale all'istanza
	public static OperativeUnit getIstance() {
		if(UO==null) {
			synchronized(OperativeUnit.class) {
				if(UO==null) {
					UO = new OperativeUnit();
				}
			}
		}
		return UO;
	}
	public Byte fetch() {
		// TODO - implement OperativeUnit.fetch
		throw new UnsupportedOperationException();
	}

	public Boolean execute() {
		// TODO - implement OperativeUnit.execute
		throw new UnsupportedOperationException();
	}

	public Boolean addressingMode() {
		// TODO - implement OperativeUnit.addressingMode
		throw new UnsupportedOperationException();
	}

}