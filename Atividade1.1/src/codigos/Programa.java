package codigos;

class OpenCV {
	Funcoes funcoes = new Funcoes("kingsman.jpg");
	
	public void main() {
		
		funcoes.abrir_img_original();
		
		funcoes.escala_cinza();
		funcoes.binarizador(100);
		
	}
}
	
public class Programa {
	public static void main(String[] args) {
		
		// opencv 4.0.1 com compilador mingw-w64
		System.load("C:/opencv_java401/libopencv_java401.dll");
		
		new OpenCV().main();
	}
}
