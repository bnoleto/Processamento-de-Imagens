package codigos;

class OpenCV {
	Funcoes funcoes = new Funcoes("img1.png");
	
	public void main() {
		
//		funcoes.abrir_img_original();
		/*
		funcoes.operacao_and();
		funcoes.operacao_or();
		*/
		
	}
}
	
public class Programa {
	public static void main(String[] args) {
		
		// opencv 4.0.1 com compilador mingw-w64
		System.load("C:/opencv_java401/libopencv_java401.dll");
		
		new OpenCV().main();
	}
}
