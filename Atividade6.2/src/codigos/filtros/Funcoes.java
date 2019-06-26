package codigos.filtros;

import java.util.ArrayList;
import java.util.Collections;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Funcoes {
	private Mat img_entrada;
	
	double[] branco = {255.0,255.0,255.0};
	double[] vermelho = {0.0,0.0,255.0};
	double[] preto = {0.0,0.0,0.0};

	
	public Funcoes() {
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		
		
	}
	
	private int calcular_media(int x, int y) {
		
		int qtd_pixels = 0;
		int soma = 0;
		
		for(int i = -1; i<=1; i++) {
			for(int j = -1; j<=1; j++) {
				try {
					soma += (img_entrada.get(x+i, y+j)[0]+
							img_entrada.get(x+i, y+j)[1]+
							img_entrada.get(x+i, y+j)[2])/3; // pegará escala de cinza
					qtd_pixels++;
				} catch (NullPointerException e) {
					// irá ignorar o passo caso esteja nas bordas da imagem
				}
				
			}	
		}
		
		return soma / qtd_pixels;
	}
	
	private Mat converter_para_hsl(Mat imagem) {
		
		Mat img_saida = new Mat(imagem.rows(), imagem.cols(), imagem.type()); 
		
		Imgproc.cvtColor(imagem, img_saida, Imgproc.COLOR_BGR2HLS);
		
		return img_saida;
		
	}
	
	private Mat converter_para_rgb(Mat imagem) {
		
		Mat img_saida = new Mat(imagem.rows(), imagem.cols(), imagem.type()); 
		
		Imgproc.cvtColor(imagem, img_saida, Imgproc.COLOR_HLS2BGR);
		
		return img_saida;
		
	}
	
	private double[] substituir_luminosidade(double[] hsl, double nova_lum) {
		
		double[] novo_hsl = new double[3];
		
		// opencv ordena HSL como HLS
		
		novo_hsl[0] = hsl[0];
		novo_hsl[1] = nova_lum;
		novo_hsl[2] = hsl[2];
		
		return novo_hsl;
	}
	
	public Mat media(Mat imagem) {
		img_entrada = imagem;
		
		Mat img_saida = converter_para_hsl(imagem);
		
		for(int i = 0; i < img_entrada.rows(); i++) {
			for(int j = 0; j < img_entrada.cols(); j++) {
				double[] pixel_atual = img_saida.get(i, j);
				
				img_saida.put(i, j, substituir_luminosidade(pixel_atual, calcular_media(i, j)));
			}
		}
		
		return converter_para_rgb(img_saida);
	}
	
	private double obter_elemento_mediano(int x, int y) {

		ArrayList<Double> elementos = new ArrayList<Double>();

		for(int i = -1; i<=1; i++) {
			for(int j = -1; j<=1; j++) {
				try {
					
					double elemento = (img_entrada.get(x+i, y+j)[0]+
							img_entrada.get(x+i, y+j)[1]+
							img_entrada.get(x+i, y+j)[2])/3;	// escala de cinza do pixel
					
					elementos.add(elemento);
					
				} catch (NullPointerException e) {
					// irá ignorar o passo caso esteja nas bordas da imagem
				}
				
			}	
		}
		
		// ordenará os elementos do arraylist
		Collections.sort(elementos);
		
		return elementos.get((elementos.size()-1)/2);	// retornará o elemento central do conjunto
	}
	
	public Mat mediana(Mat imagem) {
		img_entrada = imagem;
		
		Mat img_saida = converter_para_hsl(imagem);
		
		for(int i = 0; i < img_entrada.rows(); i++) {
			for(int j = 0; j < img_entrada.cols(); j++) {
				double[] pixel_atual = img_saida.get(i, j);
				
				img_saida.put(i, j, substituir_luminosidade(pixel_atual, obter_elemento_mediano(i, j)));
			}
		}
		
		return converter_para_rgb(img_saida);
	}
	
}