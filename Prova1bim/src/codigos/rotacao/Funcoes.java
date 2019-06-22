package codigos.rotacao;

import java.util.ArrayList;

import org.opencv.core.Mat;

public class Funcoes {
	private Mat img_entrada;
	private ArrayList<Pixel> lista_pixels;
	
	public Funcoes() {
	}
	
	private void popular_lista() {
		lista_pixels = new ArrayList<Pixel>();
		
		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				double[] pixel_atual = img_entrada.get(linha, coluna);
				
				lista_pixels.add(new Pixel(coluna, linha, pixel_atual));
			}
		}
	}
	
	boolean comparar_pixels (double[] pixel1, double[] pixel2) {
		for(int i =0; i< 3; i++) {
			if(pixel1[i] != pixel2[i]) {
				return false;
			}
		}
		return true;
	}
	
	private void limpar_mat(Mat imagem) {
		double[] preto = {0,0,0};
		
		for(int linha = 0; linha < imagem.rows(); linha++) {
			for(int coluna = 0; coluna < imagem.cols(); coluna++) {
				imagem.put(linha, coluna, preto);
			}
		}
	}
	
	public Mat rotacionar(Mat imagem, double angulo) {
		
		img_entrada = imagem;
		popular_lista();
		
		System.out.println("\nRotacionando em " + angulo + " graus.");
		
		Mat img_saida = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());
		
		Pixel centro_massa = new Pixel(img_entrada.cols()/2,img_entrada.rows()/2);
				
		new MatrizTransformacao(lista_pixels, centro_massa).rotacao(angulo);
		
		limpar_mat(img_saida);
		
		for(int i = 0; i< lista_pixels.size(); i++) {
			
			Pixel pixel_atual = lista_pixels.get(i);
			if(pixel_atual.get_x() >= 0 && pixel_atual.get_y() >= 0 &&
					pixel_atual.get_x() <= img_entrada.cols() && pixel_atual.get_y() <= img_entrada.rows()) {
				img_saida.put((int)pixel_atual.get_y(), (int)pixel_atual.get_x(), pixel_atual.get_cor());

			}
		}
		
		return img_saida;
	}
	
	public Mat transladar(double x, double y) {
		System.out.println("\nTransladando em X:" + x + " Y:" + y);
		
		Mat img_saida = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());
		
		Pixel centro_massa = new Pixel(img_entrada.cols()/2,img_entrada.rows()/2);
				
		new MatrizTransformacao(lista_pixels, centro_massa).translacao(x, y);
		
		limpar_mat(img_saida);
		
		for(int i = 0; i< lista_pixels.size(); i++) {
			
			Pixel pixel_atual = lista_pixels.get(i);
			if(pixel_atual.get_x() >= 0 && pixel_atual.get_y() >= 0) {
				img_saida.put((int)pixel_atual.get_y(), (int)pixel_atual.get_x(), pixel_atual.get_cor());
			}
		}
		
		return img_saida;
		
	}
	/*
	public void complemento() {
		System.out.println("\nAplicando Função Complemento");
		
		Mat img_saida = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());
		for(int linha = 0; linha < img_saida.rows(); linha++) {
			for(int coluna = 0; coluna < img_saida.cols(); coluna++) {
				
				double[] pixel_atual = img_entrada.get(linha, coluna);
				
				double[] preto = {0,0,0};
				double[] branco = {255,255,255};
				
				if(comparar_pixels(pixel_atual, preto)) {
					img_saida.put(linha, coluna, branco);	
				} else {
					img_saida.put(linha, coluna, preto);
				}
			}
		}
		
		EntradaSaida.escrever_arquivo("complemento.png", img_saida);
		
		EntradaSaida.abrir_arquivo("complemento.png", img_saida);
	}*/
		
}