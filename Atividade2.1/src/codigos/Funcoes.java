package codigos;

import javax.swing.JFrame;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
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
		
		abrir_arquivo(filename, img_entrada);
		
	}
	
	// função para abrir no desktop o arquivo especificado
	private void abrir_arquivo(String filename, Mat imagem) {
		
		HighGui.imshow(filename, imagem);
		HighGui.waitKey();
		
	}
	
	public void zoom_in_quadrado(int tamanho_quadrado) {
		System.out.println("\nAplicando Zoom-in Quadrado");
		
		// criará um novo mat com seu tamanho multiplicado pelo tamanho_quadrado
		Mat img_saida = new Mat(img_entrada.rows()*tamanho_quadrado,img_entrada.cols()*tamanho_quadrado,img_entrada.type());

		// percorrerá cada pixel da matriz e aplicará a função de converter para escala de cinza em cada um
		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				
				// laços para "saltar" e preencher os pixels com base no tamanho_quadrado 
				for(int i_pixel = 0; i_pixel < tamanho_quadrado; i_pixel++) {
					for(int j_pixel = 0; j_pixel < tamanho_quadrado; j_pixel++) {
						img_saida.put(linha*tamanho_quadrado+i_pixel, coluna*tamanho_quadrado+j_pixel, img_entrada.get(linha, coluna));
					}
				}
			}
		}
		
		System.out.println(String.format("Escrevendo zoom-in_qd_%s", filename));
		Imgcodecs.imwrite("zoom-in_qd_"+filename, img_saida);
		
		abrir_arquivo("zoom-in_qd_"+filename, img_saida);
	}
	
	public void zoom_in_linear(int tamanho_quadrado) {
		System.out.println("\nAplicando Zoom-in Linear");
		
		// criará um novo mat com seu tamanho multiplicado pelo tamanho_quadrado
		Mat img_saida = new Mat(img_entrada.rows()*tamanho_quadrado,img_entrada.cols()*tamanho_quadrado,img_entrada.type());

		// percorrerá cada pixel da matriz e aplicará a função de converter para escala de cinza em cada um
		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				
				// laços para "saltar" e preencher os pixels com base no tamanho_quadrado
				for(int i_pixel = 0; i_pixel < tamanho_quadrado; i_pixel++) {
					for(int j_pixel = 0; j_pixel < tamanho_quadrado; j_pixel++) {
						img_saida.put(linha*tamanho_quadrado+i_pixel, coluna*tamanho_quadrado+j_pixel, img_entrada.get(linha, coluna));
					}
				}
			}
		}
		
		System.out.println(String.format("Escrevendo zoom-in_ln_%s", filename));
		Imgcodecs.imwrite("zoom-in_ln_"+filename, img_saida);
		
		abrir_arquivo("zoom-in_ln_"+filename, img_saida);
	}
}