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
	
	public void zoom_in_quadrado(int tamanho_quadrado) {
		
		String filename_prefixo = "zoom-in_quadrado_";
		
		System.out.println("\nAplicando Zoom-in Quadrado");
		
		// criará um novo mat com seu tamanho multiplicado pelo tamanho_quadrado
		Mat img_saida = new Mat(img_entrada.rows()*tamanho_quadrado,img_entrada.cols()*tamanho_quadrado,img_entrada.type());

		// percorrerá cada pixel da matriz e aplicará a função de converter para escala de cinza em cada um
		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				
				// laços para "saltar" e preencher os pixels com base no tamanho_quadrado
				
				/*		[i][j]
				 * 		. -> pixel pré-existente
				 * 		* -> pixel replicado
				 * 
				 * 		tamanho 2
				 * 		.[0][0] | *[0][1]
				 * 		*[1][0] | *[1][1]
				 * 
				 * 		tamanho 4
				 * 		.[0][0] | *[0][1] | *[0][2] | *[0][3]
				 * 		*[1][0] | *[1][1] | *[1][2] | *[1][3]
				 * 		*[2][0] | *[2][1] | *[2][2] | *[2][3]
				 * 		*[3][0] | *[3][1] | *[3][2] | *[3][3]	
				 * 
				 */
				for(int i_pixel = 0; i_pixel < tamanho_quadrado; i_pixel++) {
					for(int j_pixel = 0; j_pixel < tamanho_quadrado; j_pixel++) {
						img_saida.put(linha*tamanho_quadrado+i_pixel, coluna*tamanho_quadrado+j_pixel, img_entrada.get(linha, coluna));
					}
				}
			}
		}
		
		escrever_arquivo(filename_prefixo, img_saida);
		
		abrir_arquivo(filename_prefixo+filename, img_saida);
	}
	
	private double[][][] media_entre_pixels(double[] pixel_pivo, double[] pixel_h, double[] pixel_v, int distancia) {
		
		double[][][] cjto_pixels = new double[distancia+1][distancia+1][3];
		
		cjto_pixels[0][0] = pixel_pivo;
		
		for(int atual = 1; atual <= distancia; atual++) {
			for(int i = atual; i <= distancia; i++) {
				for(int j = atual; j <= distancia; j++) {
				
					cjto_pixels[i][j][0] = ((pixel_pivo[0]+cjto_pixels[atual][atual][0])/(distancia+1))*i;
					cjto_pixels[i][j][1] = ((pixel_pivo[1]+cjto_pixels[atual][atual][1])/(distancia+1))*i;
					cjto_pixels[i][j][2] = ((pixel_pivo[2]+cjto_pixels[atual][atual][2])/(distancia+1))*i;
				}
			}
		}
		
		return cjto_pixels;
		
	}
	
	public void zoom_in_linear(int tamanho_quadrado, char orientacao) {
		
		String filename_prefixo = "zoom-in_linear_";
		
		System.out.println("\nAplicando Zoom-in Linear");
		
		int tamanho_h = 1, tamanho_v = 1;
		
		if(orientacao == 'v') {
			tamanho_v = tamanho_quadrado;
		} else if (orientacao == 'h'){
			tamanho_h = tamanho_quadrado;
		}
		
		// criará um novo mat com seu tamanho multiplicado pelo tamanho_quadrado
		Mat img_saida = new Mat(img_entrada.rows()*tamanho_v,img_entrada.cols()*tamanho_h,img_entrada.type());

		// percorrerá cada pixel da matriz e aplicará a função de converter para escala de cinza em cada um
		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				
				// laços para "saltar" e preencher os pixels com base no tamanho_quadrado
				
				/*		[i][j]
				 * 		. -> pixel pré-existente
				 * 		* -> pixel replicado
				 * 
				 * 		tamanho 2
				 * 		.[0][0] | *[0][1]
				 * 		*[1][0] | *[1][1]
				 * 
				 * 		tamanho 4
				 * 		.[0][0] | *[0][1] | *[0][2] | *[0][3]
				 * 		*[1][0] | *[1][1] | *[1][2] | *[1][3]
				 * 		*[2][0] | *[2][1] | *[2][2] | *[2][3]
				 * 		*[3][0] | *[3][1] | *[3][2] | *[3][3]	
				 * 
				 */
				for(int i_pixel = 0; i_pixel < tamanho_quadrado; i_pixel++) {
					for(int j_pixel = 0; j_pixel < tamanho_quadrado; j_pixel++) {
						img_saida.put(linha*tamanho_v+i_pixel, coluna*tamanho_h+j_pixel, img_entrada.get(linha, coluna));
					}
				}
			}
		}
		
		escrever_arquivo(filename_prefixo+orientacao+"_", img_saida);
		
		abrir_arquivo(filename_prefixo+orientacao+"_"+filename, img_saida);
	}
}