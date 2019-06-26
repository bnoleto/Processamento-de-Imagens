package codigos.filtros;

import java.util.Scanner;

import org.opencv.core.Mat;

public class Funcoes {
	private Mat img_entrada;
	
	double[] branco = {255.0,255.0,255.0};
	double[] vermelho = {0.0,0.0,255.0};
	double[] preto = {0.0,0.0,0.0};
	
	double[][] matriz_r1 = {
			{-1,-2,-1},
			{0,0,0},
			{1,2,1},
	};
	
	double[][] matriz_r2 = {
			{-1,0,1},
			{-2,0,2},
			{-1,0,1},
	};

	
	public Funcoes() {
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		
		
	}
	
	private int calcular_R(int x, int y, double[][] matriz) {
		
		int r = 0;
		
		for(int i = -1; i<=1; i++) {
			for(int j = -1; j<=1; j++) {
				try {
					r += ((img_entrada.get(x+i, y+j)[0]+
							img_entrada.get(x+i, y+j)[1]+
							img_entrada.get(x+i, y+j)[2])/3)*matriz[i+1][j+1]; 	// pegará escala de cinza e multiplicará
																				// pelo respectivo valor da matriz
				} catch (NullPointerException e) {
					// irá ignorar o passo caso esteja nas bordas da imagem
				}
				
			}	
		}
		
		return r;
	}
	
	public Mat sobel(Mat imagem, int limiar) {
		img_entrada = imagem;
		
		Mat img_saida = new Mat(imagem.rows(), imagem.cols(), imagem.type());
		
		for(int i = 0; i < img_entrada.rows(); i++) {
			for(int j = 0; j < img_entrada.cols(); j++) {
				
				// cálculo da distância euclidiana
				if(Math.sqrt(Math.pow(calcular_R(i, j, matriz_r1), 2) + Math.pow(calcular_R(i, j, matriz_r2),2)) > limiar) {
					img_saida.put(i, j, branco);
				} else {
					img_saida.put(i, j, preto);
				}
				
			}
		}
		
		return img_saida;
	}
	
}