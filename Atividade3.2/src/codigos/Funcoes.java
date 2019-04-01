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
		
		private double[] converter_rgb_yiq(double[] rgb) {
			
			double[][] constantes_yiq = {
					{0.299,  0.587,  0.114},
					{0.595716, -0.274453, -0.321263},
					{0.211456, -0.522591,  0.311135}
			};
			
			double[] yiq = new double[3];
			
			for(int i = 0; i< 3; i++) {
				for(int j = 0; j< 3; j++) {
					yiq[i] += rgb[j]*constantes_yiq[i][j];	
					
					//System.out.println("yiq["+i+"] += rgb[" + j + "]*constantes_yiq[" + i + "][" + j + "]");
				}
			}
			
			return yiq;
		}
		
		private double[] converter_yiq_rgb(double[] yiq) {
			
			double[][] constantes_rgb = {
					{1,  0.9563,  0.621},
					{1, -0.2721, -0.6474},
					{1, -1.107,  1.7046}
			};
			
			double[] rgb = new double[3];
			
			for(int i = 0; i< 3; i++) {
				for(int j = 0; j< 3; j++) {
					rgb[i] += yiq[j]*constantes_rgb[i][j];	
				}
				rgb[i] = Math.round(rgb[i]);
			}
			
			return rgb;
		}
	
		public void equalizar() {
			String filename_prefixo = "equalizado_";
			System.out.println("\nAplicando Equalização do Histograma");
			
			Mat img_saida = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());
			
			//Histograma hist_entrada = new Histograma(img_entrada);
			int[] hist_entrada = new int[256];
			new Histograma(img_entrada).show_histograma("Entrada");
			
			int[] hist_saida = new int[256];
			double[] tabela_equalizacao = new double[256];
			
			
			int total_pixels = img_entrada.rows()*img_entrada.cols();
			
			System.out.println(total_pixels);
			
			//new Histograma(hist_saida).show_histograma("Hist. Esperado");
			
			for(int linha = 0; linha < img_entrada.rows(); linha++) {
				for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
					
					// converte de RGB para YIQ
					double[] pixel_atual = img_entrada.get(linha, coluna);
					
					pixel_atual = converter_rgb_yiq(pixel_atual);
					
					hist_entrada[(int)pixel_atual[0]]++;
					
					//System.out.print(pixel_atual[0] + "->");
					
					// altera o Y (luminosidade) para a nova intensidade relacionada na tabela
			//		pixel_atual[0] = tabela_equalizacao[(int) pixel_atual[0]];
					
					//System.out.println(pixel_atual[0]);
					
					// converte de volta pra RGB
			//		pixel_atual = converter_yiq_rgb(pixel_atual);
					
					img_saida.put(linha, coluna, pixel_atual);
	
				}
			}
			//Histograma hist_saida = new Histograma(img_saida);
			
			int somador = 0;
			
			for(int i = 0; i < 256; i++) {
				somador += hist_entrada[i];
				
				tabela_equalizacao[i] = (int)((double)somador/total_pixels*255);
				//System.out.println(i + " -> " + (int)((double)somador/total_pixels*255));
				
			}
			
			for(int linha = 0; linha < img_saida.rows(); linha++) {
				for(int coluna = 0; coluna < img_saida.cols(); coluna++) {
					
					// converte de RGB para YIQ
					double[] pixel_atual = img_saida.get(linha, coluna);
					
		//			pixel_atual = converter_rgb_yiq(pixel_atual);
					
					//System.out.print(pixel_atual[0] + "->");
					
					// altera o Y (luminosidade) para a nova intensidade relacionada na tabela
					pixel_atual[0] = tabela_equalizacao[(int) pixel_atual[0]];
					
					//System.out.println(pixel_atual[0]);
					
					// converte de volta pra RGB
					pixel_atual = converter_yiq_rgb(pixel_atual);
					
					img_saida.put(linha, coluna, pixel_atual);
	
				}
			}
			
			new Histograma(img_saida).show_histograma("Saída");
			
			
			EntradaSaida.escrever_arquivo(filename_prefixo+filename, img_saida);
			
			EntradaSaida.abrir_arquivo(filename_prefixo+filename, img_saida);
		}
}