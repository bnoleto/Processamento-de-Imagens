package codigos;

class OpenCV {
	Funcoes funcoes = new Funcoes("lena.png");
	
	public void main() {
		
		funcoes.abrir_img_original();
		
		funcoes.zoom_in_quadrado(2);
		funcoes.zoom_in_linear(2, 'v');
		funcoes.zoom_in_linear(2, 'h');
		
	}
}
	
public class Programa {
	public static void main(String[] args) {
		
		// opencv 4.0.1 com compilador mingw-w64
		System.load("C:/opencv_java401/libopencv_java401.dll");
		
		new OpenCV().main();
	}
}
