package codigos;

import org.opencv.core.Core;

class OpenCV {
	Funcoes funcoes = new Funcoes();
	
	public void main() {
		
		//funcoes.executar("lena.png");
		funcoes.executar("lena_modif2.png");
		funcoes.executar("gray_lena.png");
		
	}
}
	
public class Programa {
	public static void main(String[] args) {
		
		// opencv 4.0.1 com compilador mingw-w64
		//System.load("C:/opencv_java401/libopencv_java401.dll");
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		new OpenCV().main();
	}
}
