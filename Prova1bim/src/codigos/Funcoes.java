package codigos;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import codigos.binarizacao.*;
import codigos.esqueletizacao.*;
import codigos.rotacao.*;
import codigos.equalizacao.*;

public class Funcoes {
	private String filename;
	private Mat imagem;
	
	// abre a imagem original no desktop pra comparação
	public void abrir_img_original() {
		
		EntradaSaida.abrir_arquivo("IMAGEM ORIGINAL", imagem);
		
	}
	
	public void executar(String filename, int angulo) {
		this.filename = filename;
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		imagem = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
		
		abrir_img_original();
		
		codigos.equalizacao.Funcoes equalizar = new codigos.equalizacao.Funcoes();
		codigos.binarizacao.Funcoes binarizar = new codigos.binarizacao.Funcoes();
		codigos.rotacao.Funcoes rotacionar = new codigos.rotacao.Funcoes();
		codigos.esqueletizacao.Funcoes esqueletizar = new codigos.esqueletizacao.Funcoes();
		
		// equalizará a imagem colorida
		imagem = equalizar.equalizar(imagem);
		EntradaSaida.abrir_arquivo("1. Equalizado", imagem);
		
		// binarizador será executado com limiar 100
		imagem = binarizar.binarizador(imagem, 100);
		EntradaSaida.abrir_arquivo("2. Binarizado", imagem);
		
		// fundo da esqueletização definida como branco
		imagem = esqueletizar.esqueletizacao(imagem);
		EntradaSaida.abrir_arquivo("3. Esqueletizado", imagem);
		imagem = rotacionar.rotacionar(imagem, angulo);
		
		EntradaSaida.abrir_arquivo("4. Rotacionado (resultado)", imagem);
		
	}
	
	private void escrever_arquivo(String prefixo, Mat imagem) {
		System.out.println(String.format("Escrevendo "+ prefixo + filename));
		Imgcodecs.imwrite(prefixo+filename, imagem);
	}
}