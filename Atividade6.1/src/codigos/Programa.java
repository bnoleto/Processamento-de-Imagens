package codigos;

import org.opencv.core.Core;

class OpenCV {
	Funcoes funcoes = new Funcoes();
	
	public void main() {
		
		funcoes.executar("peixe_colorido.jpg");
		
	}
}
	
public class Programa {
	public static void main(String[] args) {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		new OpenCV().main();
	}
}
