package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.Icon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Imagem{
	
	private Icon img166px;
	private Icon img500px;
	private JLabel label;
	
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
	
	public Mat zoom_out(Mat img_entrada, int largura) {
		
		double multiplicador = img_entrada.cols()/largura; 

		Mat img_saida = new Mat((int)(img_entrada.rows()/multiplicador),(int)(img_entrada.cols()/multiplicador),img_entrada.type());

		for(int linha = 0; linha < img_entrada.rows(); linha+=multiplicador) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna+=multiplicador) {
				
				double[][] cjto_pixels = {
						img_entrada.get(linha, coluna),
						img_entrada.get(linha, coluna+1),
						img_entrada.get(linha+1, coluna),
						img_entrada.get(linha+1, coluna+1),
						
				};
				
				img_saida.put((int)(linha/multiplicador), (int)(coluna/multiplicador), media_entre_pixels(cjto_pixels));
			}
		}
		
		return img_saida;
		
	}
	
	Imagem(Mat img, int op){
		
		img166px = new javax.swing.ImageIcon(HighGui.toBufferedImage(zoom_out(img, 166)));
		img500px = new javax.swing.ImageIcon(HighGui.toBufferedImage(zoom_out(img, 500)));
		
		if(op == 0) {
			label = new JLabel(img166px);	
		} else {
			label = new JLabel(img500px);
		}
		
	}
	
	Icon get_img_pequena() {
		return this.img166px;
	}
	Icon get_img_grande() {
		return this.img500px;
	}
	
	JLabel get_label() {
		return this.label;
	}
};

public class Janela extends JFrame {
	
	private Imagem img_entrada;
	private Imagem img1;
	private Imagem img2;
	private Imagem img3;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Janela(String filename, Mat img_entrada_mat, Mat img_1, Mat img_2, Mat img_3) {
		
		String tit1 = filename + " - " + img_entrada_mat.cols()+"x"+img_entrada_mat.rows();
		
		img_entrada = new Imagem(img_entrada_mat, 1);
		img1 = new Imagem(img_3, 0);
		img2 = new Imagem(img_2, 0);
		img3 = new Imagem(img_1, 0);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(750, 500, 750, 500);
		setTitle(tit1+" -> Original");
		
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setVerifyInputWhenFocusTarget(false);
		splitPane.setBorder(null);
		splitPane.setEnabled(false);
		contentPane.add(splitPane);
		
		splitPane.setLeftComponent(img_entrada.get_label());
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		img_entrada.get_label().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				img_entrada.get_label().setIcon(img_entrada.get_img_grande());
				setTitle(tit1+" -> Original");
			}
		});
		
		img1.get_label().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				img_entrada.get_label().setIcon(img1.get_img_grande());
				setTitle(tit1+" -> Canal R");
			}
		});
		panel.add(img1.get_label(), BorderLayout.NORTH);
		
		img2.get_label().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				img_entrada.get_label().setIcon(img2.get_img_grande());
				setTitle(tit1+" -> Canal G");
			}
		});
		panel.add(img2.get_label(), BorderLayout.CENTER);
		
		img3.get_label().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				img_entrada.get_label().setIcon(img3.get_img_grande());
				setTitle(tit1+" -> Canal B");
			}
		});
		panel.add(img3.get_label(), BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		
	}
}
