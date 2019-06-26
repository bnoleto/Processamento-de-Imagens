package codigos;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import telas.Tela;

public class Funcoes {
	private Mat imagem_original;
	
	// abre a imagem original no desktop pra comparação
	public void abrir_img(String filename) {
		
		Tela frame = new Tela(filename, imagem_original);
		
		frame.mostrar();
		
	}
	
	public void executar(String filename) {
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		
		if(System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			
			// workaround para abrir a imagem no windows (evitar ler o caminho como "/C://.../...")
			imagem_original = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
		} else {
			imagem_original = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath());
		}
		
		abrir_img(filename);

	}
}