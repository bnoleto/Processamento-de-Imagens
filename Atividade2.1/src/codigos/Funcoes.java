package codigos;

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
		
		javax.swing.JFrame frame = new javax.swing.JFrame(filename);
		frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new javax.swing.JLabel(new javax.swing.ImageIcon(HighGui.toBufferedImage(imagem))));
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private void escrever_arquivo(String prefixo, Mat imagem) {
		System.out.println(String.format("Escrevendo "+ prefixo + filename));
		Imgcodecs.imwrite(prefixo+filename, imagem);
	}
	
	public void zoom_in_quadrado() {
		
		String filename_prefixo = "zoom-in_quadrado_";
		
		System.out.println("\nAplicando Zoom-in Quadrado");
		
		Mat img_saida = new Mat(img_entrada.rows()*2,img_entrada.cols()*2,img_entrada.type());

		for(int linha = 0; linha < img_saida.rows(); linha++) {
			for(int coluna = 0; coluna < img_saida.cols(); coluna++) {
							
				img_saida.put(linha, coluna, img_entrada.get(linha/2, coluna/2));

			}
		}
		
		escrever_arquivo(filename_prefixo, img_saida);
		
		abrir_arquivo(filename_prefixo+filename, img_saida);
	}
	
	private double[] media_entre_pixels(double[] pixel_1, double[] pixel_2) {
		
		double[] pixel_resultado = new double[3];
		
		for(int i = 0; i<3; i++) {
			pixel_resultado[i] = (pixel_1[i]+pixel_2[i])/2;	
		}
		
		return pixel_resultado;
		
	}
	
	public void zoom_in_linear() {
		
		String filename_prefixo = "zoom-in_linear_";
		
		System.out.println("\nAplicando Zoom-in Linear");

		Mat img_saida = new Mat(img_entrada.rows()*2,img_entrada.cols()*2,img_entrada.type());

		// 1ª passagem - expandir pixels
		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				img_saida.put(linha*2, coluna*2, img_entrada.get(linha, coluna));
			}
		}
		
		
		// 2ª passagem - horizontal
		for(int linha = 0; linha < img_saida.rows(); linha+=2) {
			for(int coluna = 0; coluna < img_saida.cols()-2; coluna+=2) {
				
				img_saida.put(linha, coluna+1, media_entre_pixels(
						img_saida.get(linha, coluna),
						img_saida.get(linha, coluna+2)
						));	
	
			}
		}
		
		// 3ª passagem - vertical
		for(int linha = 0; linha < img_saida.rows()-2; linha+=2) {
			for(int coluna = 0; coluna < img_saida.cols(); coluna++) {				
				
				img_saida.put(linha+1, coluna, media_entre_pixels(
						img_saida.get(linha, coluna),
						img_saida.get(linha+2, coluna)
						));	
			}
		}
		
		escrever_arquivo(filename_prefixo, img_saida);
		
		abrir_arquivo(filename_prefixo+filename, img_saida);
	}
}