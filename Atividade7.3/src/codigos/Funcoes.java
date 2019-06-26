package codigos;

import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import telas.Tela;

public class Funcoes {
	private Mat imagem_original;
	
	// abre a imagem original no desktop pra comparação
	public void abrir_img(String filename) {
		
		new Tela(filename, imagem_original);
		
	}
	
	public void abrir_img(String filename, Mat imagem) {
		
		new Tela(filename, imagem);
		
	}
	
	public void executar(String filename) {
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		
		if(System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			
			// workaround para abrir a imagem no windows (evitar ler o caminho como "/C://.../...")
			imagem_original = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
		} else {
			imagem_original = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath());
		}
		
		codigos.filtros.Funcoes filtros = new codigos.filtros.Funcoes();
		
		Random random = new Random();
		Mat imagem_saida = filtros.k_means(imagem_original, random.nextInt(51));
		//Mat imagem_saida = filtros.k_means(imagem_original, 20);
		
		abrir_img(filename);
		abrir_img("1. K-means", imagem_saida);

	}
}