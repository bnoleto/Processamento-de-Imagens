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
	
	private int calcular_luminosidade_media(int x, int y) {
		
		// irá calcular a média de luminosidade do pixel atual + todos os vizinhos
		
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
		
		// converterá a imagem de RGB para HSL
		
		Mat img_saida = new Mat(imagem.rows(), imagem.cols(), imagem.type()); 
		
		Imgproc.cvtColor(imagem, img_saida, Imgproc.COLOR_BGR2HLS);
		
		return img_saida;
		
	}
	
	private Mat converter_para_rgb(Mat imagem) {
		
		// converterá a imagem de HSL para RGB
		
		Mat img_saida = new Mat(imagem.rows(), imagem.cols(), imagem.type()); 
		
		Imgproc.cvtColor(imagem, img_saida, Imgproc.COLOR_HLS2BGR);
		
		return img_saida;
		
	}
	
	private double[] substituir_luminosidade(double[] hsl, double nova_lum) {
		
		// irá pegar um pixel em HSL, e substituirá o seu valor de luminosidade pelo valor especificado por nova_lum 
		
		double[] novo_hsl = new double[3];
		
		// opencv ordena HSL como HLS
		
		novo_hsl[0] = hsl[0];
		novo_hsl[1] = nova_lum;
		novo_hsl[2] = hsl[2];
		
		return novo_hsl;
	}
	
	public Mat media(Mat imagem) {
		
		// função principal do filtro de média
		
		img_entrada = imagem;
		
		Mat img_saida = converter_para_hsl(imagem);
		
		for(int i = 0; i < img_entrada.rows(); i++) {
			for(int j = 0; j < img_entrada.cols(); j++) {
				double[] pixel_atual = img_saida.get(i, j);
				
				img_saida.put(i, j, substituir_luminosidade(pixel_atual, calcular_luminosidade_media(i, j)));
			}
		}
		
		return converter_para_rgb(img_saida);
	}
	
	private double obter_elemento_mediano(int x, int y) {
		
		// irá ordenar os pixels por sua luminosidade de modo crescente e retornará o elemento posicionado na mediana da lista

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
		
		// função principal do filtro de mediana
		
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