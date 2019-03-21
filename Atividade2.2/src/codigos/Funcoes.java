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
	
	public void zoom_out_quadrado() {
		
		String filename_prefixo = "zoom-out_quadrado_";
		
		System.out.println("\nAplicando Zoom-out Quadrado");
		
		Mat img_saida = new Mat(img_entrada.rows()/2,img_entrada.cols()/2,img_entrada.type());

		for(int linha = 0; linha < img_entrada.rows(); linha+=2) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna+=2) {
							
				img_saida.put(linha/2, coluna/2, img_entrada.get(linha, coluna));

			}
		}
		
		escrever_arquivo(filename_prefixo, img_saida);
		
		abrir_arquivo(filename_prefixo+filename, img_saida);
	}
	
	private double[] media_entre_pixels(double[][] pixels) {
		
		double[] pixel_resultado = new double[3];
		
		int qtd_pixels = 0;
		
		for(int i = 0; i< pixels.length; i++) {
			// assegurará que não tentará tirar a média de pixels nulos
			
			if(pixels[i] == null) {
				break;	
			};
			
			
			qtd_pixels++;
		}

		for(int j = 0; j< qtd_pixels; j++) {
			for(int i = 0; i<3; i++) {
				pixel_resultado[i] += pixels[j][i];
			}	
		}
		for(int i = 0; i<3; i++) {
			pixel_resultado[i] /= qtd_pixels;
		}	
		
		
		return pixel_resultado;
		
	}
	
	public void zoom_out_linear() {
		
		String filename_prefixo = "zoom-out_linear_";
		
		System.out.println("\nAplicando Zoom-out Linear");

		Mat img_saida = new Mat(img_entrada.rows()/2,img_entrada.cols()/2,img_entrada.type());

		for(int linha = 0; linha < img_entrada.rows(); linha+=2) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna+=2) {
				
				double[][] cjto_pixels = {
						img_entrada.get(linha, coluna),
						img_entrada.get(linha, coluna+1),
						img_entrada.get(linha+1, coluna),
						img_entrada.get(linha+1, coluna+1),
						
				};
				
				img_saida.put(linha/2, coluna/2, media_entre_pixels(cjto_pixels));
			}
		}
		
		escrever_arquivo(filename_prefixo, img_saida);
		
		abrir_arquivo(filename_prefixo+filename, img_saida);
	}
}