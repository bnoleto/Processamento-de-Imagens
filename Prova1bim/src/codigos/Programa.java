package codigos;

class OpenCV {
	Funcoes funcoes = new Funcoes();
	
	public void main() {
		
		funcoes.executar("peixe_poucocontraste.jpg", 27);
		
	}
}
	
public class Programa {
	public static void main(String[] args) {
		
		// opencv 4.0.1 com compilador mingw-w64
		System.load("C:/opencv_java401/libopencv_java401.dll");
		
		new OpenCV().main();
	}
}
