package codigos;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Funcoes {
	private String filename;
	private Mat img_entrada;
	
	public Funcoes(String filename) {
		this.filename = filename;
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		img_entrada = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
	
	}
	
	// abre a imagem original no desktop pra comparação
		public void abrir_img_original() {
			
			EntradaSaida.abrir_arquivo(filename, img_entrada);
			
		}
		
		
		boolean comparar_pixels (double[] pixel1, double[] pixel2) {
			for(int i =0; i< 3; i++) {
				if(pixel1[i] != pixel2[i]) {
					return false;
				}
			}
			return true;
		}
		
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
		}
}