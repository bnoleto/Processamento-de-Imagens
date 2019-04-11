package codigos;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Funcoes {
	private String filename1, filename2;
	private Mat img_entrada1, img_entrada2;
	
	public Funcoes(String filename1, String filename2) {
		this.filename1 = filename1;
		this.filename2 = filename2;
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		img_entrada1 = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename1).getPath().substring(1));
		img_entrada2 = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename2).getPath().substring(1));
	
	}
	
	// abre a imagem original no desktop pra comparação
		public void abrir_img_original() {
			
			EntradaSaida.abrir_arquivo(filename1, img_entrada1);
			EntradaSaida.abrir_arquivo(filename2, img_entrada2);
			
		}
		
		
		boolean comparar_pixels (double[] pixel1, double[] pixel2) {
			for(int i =0; i< 3; i++) {
				if(pixel1[i] != pixel2[i]) {
					return false;
				}
			}
			return true;
		}
		
		public void operacao_and() {
			System.out.println("\nAplicando Função AND");
			
			Mat img_saida = new Mat(img_entrada1.rows(),img_entrada1.cols(),img_entrada1.type());
			for(int linha = 0; linha < img_saida.rows(); linha++) {
				for(int coluna = 0; coluna < img_saida.cols(); coluna++) {
					
					double[] pixel_atual_img1 = img_entrada1.get(linha, coluna);
					double[] pixel_atual_img2 = img_entrada2.get(linha, coluna);
					
					double[] preto = {0,0,0};
					double[] branco = {255,255,255};
					
					if(comparar_pixels(pixel_atual_img1, preto) && comparar_pixels(pixel_atual_img2, preto)) {
						img_saida.put(linha, coluna, preto);	
					} else {
						img_saida.put(linha, coluna, branco);
					}
				}
			}
			
			EntradaSaida.escrever_arquivo("funcao_AND.png", img_saida);
			
			EntradaSaida.abrir_arquivo("funcao_AND.png", img_saida);
		}
		
		public void operacao_or() {
			System.out.println("\nAplicando Função OR");
			
			Mat img_saida = new Mat(img_entrada1.rows(),img_entrada1.cols(),img_entrada1.type());
			for(int linha = 0; linha < img_saida.rows(); linha++) {
				for(int coluna = 0; coluna < img_saida.cols(); coluna++) {
					
					double[] pixel_atual_img1 = img_entrada1.get(linha, coluna);
					double[] pixel_atual_img2 = img_entrada2.get(linha, coluna);

					double[] preto = {0.0,0.0,0.0};
					double[] branco = {255.0,255.0,255.0};
					
					if(comparar_pixels(pixel_atual_img1, preto) || comparar_pixels(pixel_atual_img2, preto)) {
						img_saida.put(linha, coluna, preto);	
					} else {
						img_saida.put(linha, coluna, branco);
					}
				}
			}
			
			EntradaSaida.escrever_arquivo("funcao_OR.png", img_saida);
			
			EntradaSaida.abrir_arquivo("funcao_OR.png", img_saida);
		}
}