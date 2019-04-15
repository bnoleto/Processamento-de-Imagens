package codigos;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Funcoes {
	private String filename;
	private Mat img_entrada;
	
	double[] branco = {255.0,255.0,255.0};
	double[] preto = {0.0,0.0,0.0};
	
	private boolean[][] elemento_estruturante = {
			 {false,	true,	false},
			 {true,		true,	true},
			 {false,	true,	false},
	 };
	/*
	private boolean[][] elemento_estruturante = {
			 {false,	false,	true,	 false,		false},
			 {false,	false,	true,	 false,		false},
			 {true,		true,	true,	 true,		true},
			 {false,	false,	true,	 false,		false},
			 {false,	false,	true,	 false,		false},
	 };*/
	
	int ponto_central = elemento_estruturante.length/2;
	
	boolean[][] clone (boolean[][] imagem){
		boolean[][] nova_imagem = new boolean[imagem.length][imagem[0].length];
		
		for(int i = 0; i < nova_imagem.length; i++) {
			for(int j = 0; j < nova_imagem[i].length; j++) {
				nova_imagem[i][j] = imagem[i][j];
			}
		}
		
		return nova_imagem;
	}
	
	public Funcoes(String filename) {
		this.filename = filename;
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		img_entrada = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
		
	}
	
	public void esqueletizacao() {
		boolean[][] img_saida = operacao_esqueletizacao(converter_bool(img_entrada, branco));
		
		Mat mat_saida = converter_mat(img_saida, branco, preto);
		
		EntradaSaida.abrir_arquivo("saída", mat_saida);
	}
	
	private boolean[][] converter_bool(Mat imagem, double[] cor_primaria){
		
		boolean[][] resultado = new boolean[imagem.rows()][imagem.cols()];
		
		for(int linha = 0; linha < imagem.rows(); linha++) {
			for(int coluna = 0; coluna < imagem.cols(); coluna++) {
				double[] pixel_atual = imagem.get(linha, coluna);
				
				resultado[linha][coluna] = comparar_pixels(pixel_atual, cor_primaria);
			}
		}
		
		return resultado;
		
	}
	
	private Mat converter_mat(boolean[][] imagem, double[] cor_primaria,double[] cor_secundaria){
		
		Mat resultado = new Mat(imagem.length,imagem[0].length, img_entrada.type());
		
		for(int linha = 0; linha < resultado.rows(); linha++) {
			for(int coluna = 0; coluna < resultado.cols(); coluna++) {
				
				if(imagem[linha][coluna]) {
					resultado.put(linha, coluna, cor_primaria);
				} else {
					resultado.put(linha, coluna, cor_secundaria);
				}
			}
		}
		
		return resultado;
		
	}

	// 	abre a imagem original no desktop pra comparação
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
	
	private boolean elemento_encaixa(boolean[][] imagem, int linha, int coluna) {
		
		for(int i_estruturante = -ponto_central; i_estruturante <= ponto_central; i_estruturante++) {
			for(int j_estruturante = -ponto_central; j_estruturante <= ponto_central; j_estruturante++) {
				try {
					if(elemento_estruturante[i_estruturante+ponto_central][j_estruturante+ponto_central] == true &&
							imagem[linha+i_estruturante][coluna+j_estruturante] == false) {
						return false;
					}	
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
		
	}
	
	int qtd_pixels(boolean[][] img){
		int qtd = 0;
		
		for(int i = 0; i< img.length; i++) {
			for(int j = 0; j< img[i].length; j++) {
				if(img[i][j]) {
					qtd++;
				}
			}
		}
		
		return qtd;
	}
	
	private boolean[][] erodir(boolean[][] img){
	
		boolean[][] resultado = new boolean[img.length][img[0].length];
		
		for(int i = 0; i < resultado.length; i++) {
			for(int j = 0; j < resultado[i].length; j++) {
				
				// irá preencher o ponto [i,j] apenas se o elemento estruturante
				// estiver totalmente contido naquela posição
				resultado[i][j] = elemento_encaixa(img, i, j);
			}
		}
		
		return resultado;
	}
	
	private boolean[][] dilatar(boolean[][] img){
		
		boolean[][] resultado = clone(img);
		
		for(int i = 0; i < resultado.length; i++) {
			for(int j = 0; j < resultado[i].length; j++) {
				if(img[i][j] == true) {
					for(int i_estruturante = -ponto_central; i_estruturante <= ponto_central; i_estruturante++) {
						for(int j_estruturante = -ponto_central; j_estruturante <= ponto_central; j_estruturante++) {
							try {
								
								if(img[i+i_estruturante][j+j_estruturante] == false && elemento_estruturante[i_estruturante+ponto_central][j_estruturante+ponto_central] == true) {
									resultado[i+i_estruturante][j+j_estruturante] = true;	
								}
								
							} catch (ArrayIndexOutOfBoundsException e) {
								// irá ignorar caso o ponto da imagem esteja em algum dos limites
							}
						}
					}
				}
			}
		}
		
		return resultado;
	}
	
	private boolean[][] abertura(boolean[][] img){
		
		return dilatar(erodir(img));
	}
	
	private boolean[][] fechamento(boolean[][] img){
			
			return erodir(dilatar(img));
	}
	
	private boolean[][] operacao_esqueletizacao(boolean[][] img){
		
		boolean[][] erodido = erodir(img);
		
		//EntradaSaida.abrir_arquivo("saída", converter_mat(operacao_subtracao(img, abertura(img)), branco, preto));
		if(qtd_pixels(erodido) > 0) {
			return operacao_or(operacao_subtracao(img, abertura(img)), operacao_esqueletizacao(erodido));
		}
		
		
		return operacao_subtracao(img, abertura(img));
		
	}
	
	private boolean[][] operacao_or(boolean[][] img1, boolean[][] img2){
		
		if(img1.length != img2.length || img1[0].length != img2[0].length) {
			//throw new Exception("Tamanhos das imagens a serem comparadas são diferentes!");
		}
		
		boolean[][] img_saida = new boolean[img1.length][img1[0].length];
		for(int linha = 0; linha < img_saida.length; linha++) {
			for(int coluna = 0; coluna < img_saida[0].length; coluna++) {
				
				boolean pixel_atual_img1 = img1[linha][coluna];
				boolean pixel_atual_img2 = img2[linha][coluna];
				
				img_saida[linha][coluna] = (pixel_atual_img1 || pixel_atual_img2);
				
			}
		}
		
		return img_saida;
	}
	
	private boolean[][] operacao_and(boolean[][] img1, boolean[][] img2){
		
		if(img1.length != img2.length || img1[0].length != img2[0].length) {
			//throw new Exception("Tamanhos das imagens a serem comparadas são diferentes!");
		}
		
		boolean[][] img_saida = new boolean[img1.length][img1[0].length];
		for(int linha = 0; linha < img_saida.length; linha++) {
			for(int coluna = 0; coluna < img_saida[0].length; coluna++) {
				
				boolean pixel_atual_img1 = img1[linha][coluna];
				boolean pixel_atual_img2 = img2[linha][coluna];
				
				img_saida[linha][coluna] = (pixel_atual_img1 && pixel_atual_img2);
				
			}
		}
		
		return img_saida;
	}
	
	private boolean[][] operacao_subtracao(boolean[][] img1, boolean[][] img2){
		
		if(img1.length != img2.length || img1[0].length != img2[0].length) {
//			throw new Exception("Tamanhos das imagens a serem comparadas são diferentes!");
		}
		
		boolean[][] img_saida = new boolean[img1.length][img1[0].length];
		
		for(int linha = 0; linha < img_saida.length; linha++) {
			for(int coluna = 0; coluna < img_saida[0].length; coluna++) {
				
				boolean pixel_atual_img1 = img1[linha][coluna];
				boolean pixel_atual_img2 = img2[linha][coluna];
				
				if(pixel_atual_img1 && !pixel_atual_img2) {
					img_saida[linha][coluna] = true;	
				} else {
					img_saida[linha][coluna] = false;
				}
			}
		}
		
		return img_saida;
	}
}