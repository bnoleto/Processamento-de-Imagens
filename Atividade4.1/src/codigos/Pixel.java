package codigos;

public class Pixel {

	private double x, y;
	private double[] rgb;
	
	Pixel(){
		
	}
	
	Pixel(int x, int y, double[] cor){
		this.x = x;
		this.y = y;
		this.rgb = cor;
	}
	
	Pixel(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public double[] get_cor() {
		return this.rgb;
	}
	
	public void set_cor(double[] cor) {
		this.rgb = cor;
	}
	
	public double get_x() {
		return this.x;
	}
	
	public double get_y() {
		return this.y;
	}
	
	public Pixel get_pixel() {
		return this;
	}
	
	public void set_coordenadas(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
