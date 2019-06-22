package codigos.binarizacao;

import org.opencv.core.Mat;

public class Funcoes {
	private Mat imagem;
	
	public Funcoes() {
		
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
  
	public Mat binarizador(Mat img_entrada, int limiar) {

		this.imagem = img_entrada;
		
		System.out.println("\nRodando Binarizador com limiar " + limiar);
		
		// percorrerá cada pixel da matriz e aplicará a função de binarizar em cada um
		for(int linha = 0; linha < imagem.rows(); linha++) {
			for(int coluna = 0; coluna< imagem.cols(); coluna++) {
				imagem.put(linha, coluna, converter_binarizado(imagem.get(linha, coluna),limiar));
			}
		}
		
		return imagem;
	  }
}