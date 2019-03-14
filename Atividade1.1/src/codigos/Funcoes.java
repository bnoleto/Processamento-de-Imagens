package codigos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Funcoes {
	private String filename;
	private Mat imagem;
	
	public Funcoes(String filename) {
		this.filename = filename;
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		imagem = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
	}
	
	// abre a imagem original no desktop pra comparação
	public void abrir_img_original() {
		
		abrir_arquivo(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
		
	}
	
	// função para abrir no desktop o arquivo especificado
	private void abrir_arquivo(String filename) {
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private double[] converter_grayscale(double[] pixel_entrada) {
		double media = (pixel_entrada[0]+pixel_entrada[1]+pixel_entrada[2])/3;
		
		double pixel_saida[] = new double[3];
		
		pixel_saida[0] = media; // R
		pixel_saida[1] = media; // G
		pixel_saida[2] = media; // B
		
		return pixel_saida;
	}
	
	private double[] converter_binarizado(double[] pixel_entrada, int limiar) {
		
		double pixel_saida[] = new double[3];
		
		// como converterá para escala de cinza antes, os valores de r, g e b serão os mesmos
		if (converter_grayscale(pixel_entrada)[0] >= limiar) {
			pixel_saida[0] = 255;
			pixel_saida[1] = 255;
			pixel_saida[2] = 255;
			
		} else {
			pixel_saida[0] = 0;
			pixel_saida[1] = 0;
			pixel_saida[2] = 0;
		}
		
		return pixel_saida;
	}
	
	public void escala_cinza() {
		System.out.println("\nRodando Escala de Cinza");

		// percorrerá cada pixel da matriz e aplicará a função de converter para escala de cinza em cada um
		for(int linha = 0; linha < imagem.rows(); linha++) {
			for(int coluna = 0; coluna< imagem.cols(); coluna++) {
				imagem.put(linha, coluna, converter_grayscale(imagem.get(linha, coluna)));
			}
		}
		
		System.out.println(String.format("Escrevendo gray_%s", filename));
		Imgcodecs.imwrite("gray_"+filename, imagem);
		
		abrir_arquivo("gray_"+filename);
	}
  
	public void binarizador(int limiar) {
		System.out.println("\nRodando Binarizador com limiar " + limiar);
		
		// percorrerá cada pixel da matriz e aplicará a função de binarizar em cada um
		for(int linha = 0; linha < imagem.rows(); linha++) {
			for(int coluna = 0; coluna< imagem.cols(); coluna++) {
				imagem.put(linha, coluna, converter_binarizado(imagem.get(linha, coluna),limiar));
			}
		}
		
		System.out.println(String.format("Escrevendo bin_%s", filename));
		Imgcodecs.imwrite("bin_"+filename, imagem);
		
		abrir_arquivo("bin_"+filename);
	  }
}