package codigos.filtros;

import java.util.ArrayList;

import org.opencv.core.Mat;

public class Semente {
private double[] cor;
	
	private ArrayList<Coordenada> coordenadas_pertencentes = new ArrayList<Coordenada>();
	private double[] media_rgb;
	
	Semente(Mat imagem, double[] cor){
		
		this.cor = cor;
		
		calcular_rgb_medio();
	}
	
	public void set_cor(double[] rgb) {
		this.cor = rgb;
	}
	
	public double[] get_media_rgb() {
		return this.media_rgb;
	}
	
	public double[] get_cor() {
		return this.cor;
	}
	
	private void calcular_rgb_medio() {
		
		// irá pegar todos os pixels pertencentes à semente, e calculará a média da cor
		
		double[] soma = {0,0,0};
		
		for(Coordenada coord : coordenadas_pertencentes) {
			for(int i = 0; i < 3; i++) {
				soma[i] += coord.get_cor()[i];
			}
		}
		
		double[] media = {soma[0]/coordenadas_pertencentes.size(),
				soma[1]/coordenadas_pertencentes.size(),
				soma[2]/coordenadas_pertencentes.size()};
		
		this.media_rgb = media;
	}
	
	public void add(Coordenada coord) {
		
		// irá adicionar a coordenada coord à esta semente
		
		coordenadas_pertencentes.add(coord);
		calcular_rgb_medio();
	}
	
	public void swap(Coordenada coord, Semente destino) {
		
		// irá fazer a troca de uma coordenada pertencente à esta semente, para a semente destino
		
		coordenadas_pertencentes.remove(coord);
		calcular_rgb_medio();
		
		destino.add(coord);
	}
}
