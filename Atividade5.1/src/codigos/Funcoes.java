package codigos;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Funcoes {
	private String filename;
	private Mat img_entrada;
	private ArrayList<boolean[][]> operacoes = new ArrayList<boolean[][]>();
	
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
	
	//private boolean[][] imagem;
	
	
	boolean[][] clone (boolean[][] imagem){
		boolean[][] nova_imagem = new boolean[imagem.length][imagem[0].length];
		
		for(int i = 0; i < nova_imagem.length; i++) {
			for(int j = 0; j < nova_imagem[0].length; j++) {
				nova_imagem[i][j] = imagem[i][j];
			}
		}
		
		return nova_imagem;
	}
	
	public Funcoes(String filename) {
		this.filename = filename;
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		img_entrada = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
		
		EntradaSaida.abrir_arquivo(filename, img_entrada);
		
		
		
		boolean[][] imagem = converter_bool(img_entrada, branco);
	
		
		try {
			esqueletizacao(imagem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean[][] img_saida = operacoes.get(0);
		
		for(int i = 1; i < operacoes.size(); i++) {
			//EntradaSaida.abrir_arquivo("saída", converter_mat(operacoes.get(i), branco, preto));
			try {
				img_saida = operacao_or(img_saida, operacoes.get(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Mat mat_saida = converter_mat(img_saida, branco, preto);
		
		EntradaSaida.abrir_arquivo("saída", mat_saida);
	}
	
	private boolean[][] converter_bool(Mat imagem, double[] cor_primaria){
		
		boolean[][] resultado = new boolean[imagem.cols()][imagem.rows()];
		
		for(int linha = 0; linha < imagem.rows(); linha++) {
			for(int coluna = 0; coluna < imagem.cols(); coluna++) {
				double[] pixel_atual = imagem.get(linha, coluna);
				
				resultado[coluna][linha] = comparar_pixels(pixel_atual, cor_primaria);
			}
		}
		
		return resultado;
		
	}
	
	private Mat converter_mat(boolean[][] imagem, double[] cor_primaria,double[] cor_secundaria){
		
		//boolean[][] resultado = new boolean[imagem.cols()][imagem.rows()];
		
		Mat resultado = new Mat(imagem.length,imagem[0].length, img_entrada.type());
		
		for(int linha = 0; linha < resultado.rows(); linha++) {
			for(int coluna = 0; coluna < resultado.cols(); coluna++) {
				
				if(imagem[coluna][linha]) {
					resultado.put(linha, coluna, cor_primaria);
				} else {
					resultado.put(linha, coluna, cor_secundaria);
				}
			}
		}
		
		return resultado;
		
	}
/*
	// 	abre a imagem original no desktop pra comparação
	public void abrir_img_original() {
		
		EntradaSaida.abrir_arquivo(filename, img_entrada);
		
	}
	*/
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
							imagem[coluna+j_estruturante][linha+i_estruturante] != true) {
						return false;
					}	
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
		
	}
	
	private void preencher(boolean[][] imagem, int linha, int coluna) {
		
		for(int i_estruturante = -ponto_central; i_estruturante <= ponto_central; i_estruturante++) {
			for(int j_estruturante = -ponto_central; j_estruturante <= ponto_central; j_estruturante++) {
				
				
				if(elemento_estruturante[i_estruturante+ponto_central][j_estruturante+ponto_central]) {
					imagem[coluna+j_estruturante][linha+i_estruturante] = elemento_estruturante[i_estruturante+ponto_central][j_estruturante+ponto_central];	
				}
				
			}
		}
	}
	
	public void esqueletizacao(boolean[][] img) throws Exception{
		
		EntradaSaida.abrir_arquivo("saída", converter_mat(img, branco, preto));
		
		boolean[][] clone_img = clone(img);
		boolean[][] resultado = new boolean[img.length][img[0].length];
		
		boolean preencheu = false;
		
		for(int i = 0; i < resultado.length; i++) {
			for(int j = 0; j < resultado[i].length; j++) {
				
				if(elemento_encaixa(clone_img, i, j)) {
					preencher(resultado,i,j);
					preencheu = true;
				}
			}
		}
		
		
		
		operacoes.add(operacao_subtracao(clone_img, resultado));
		
		if(operacoes.size()<10) {
			
			esqueletizacao(resultado);
		}
		
		

	}
	
	private boolean[][] operacao_or(boolean[][] img1, boolean[][] img2) throws Exception {
		
		if(img1.length != img2.length || img1[0].length != img2[0].length) {
			throw new Exception("Tamanhos das imagens a serem comparadas são diferentes!");
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
	
	private boolean[][] operacao_and(boolean[][] img1, boolean[][] img2) throws Exception {
		
		if(img1.length != img2.length || img1[0].length != img2[0].length) {
			throw new Exception("Tamanhos das imagens a serem comparadas são diferentes!");
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
	
	private boolean[][] operacao_subtracao(boolean[][] img1, boolean[][] img2) throws Exception {
		
		if(img1.length != img2.length || img1[0].length != img2[0].length) {
			throw new Exception("Tamanhos das imagens a serem comparadas são diferentes!");
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