package codigos;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Funcoes {
	private String filename;
	private Mat imagem_original;
	
	// abre a imagem original no desktop pra comparação
	public void abrir_img_original() {
		
		EntradaSaida.abrir_arquivo("IMAGEM ORIGINAL", imagem_original);
		
	}
	
	public void executar(String filename) {
		this.filename = filename;
		
		if(System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
			
			// workaround para abrir a imagem no windows (evitar ler o caminho como "/C://.../...")
			imagem_original = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
		} else {
			imagem_original = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath());
		}
		
		abrir_img_original();
		
		codigos.filtros.Funcoes filtros = new codigos.filtros.Funcoes();
		
		Mat imagem1_media = filtros.media(imagem_original);
		EntradaSaida.abrir_arquivo("1. Filtro de Média", imagem1_media);
		
		Mat imagem1_mediana = filtros.mediana(imagem_original);
		EntradaSaida.abrir_arquivo("2. Filtro de Mediana", imagem1_mediana);
		
	}
	
	private void escrever_arquivo(String prefixo, Mat imagem) {
		System.out.println(String.format("Escrevendo "+ prefixo + filename));
		Imgcodecs.imwrite(prefixo+filename, imagem);
	}
}