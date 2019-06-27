package codigos.filtros;

public class Coordenada {
	
	private int x;
	private int y;
	private double[] cor;
	
	Coordenada(int x, int y, double[] cor){
		this.x = x;
		this.y = y;
		this.cor = cor;
	}
	
	public int get_x() {
		return this.x;
	}
	
	public int get_y() {
		return this.y;
	}
	
	public double[] get_cor() {
		return this.cor;
	}
}
