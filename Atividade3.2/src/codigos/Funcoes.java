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
	
	public void equalizar() {
		String filename_prefixo = "equalizado_";
		System.out.println("\nAplicando Equalização do Histograma");
		
		Mat img_saida = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());
		
		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				
				double[] pixel_atual = img_entrada.get(linha, coluna);
				
				//img_saida.put(linha, coluna, inverter_pixel(pixel_atual));

			}
		}
		
		new Histograma(img_entrada, filename);
		new Histograma(img_saida, filename_prefixo+filename);
		
		EntradaSaida.escrever_arquivo(filename_prefixo+filename, img_saida);
		
		EntradaSaida.abrir_arquivo(filename_prefixo+filename, img_saida);
	}
}