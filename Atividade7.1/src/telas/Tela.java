package telas;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import codigos.filtros.Funcoes;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tela extends JFrame {

	private Funcoes filtros = new Funcoes();
	
	/**
	 * Create the frame.
	 */
	public Tela(String filename, Mat imagem) {
		
		setTitle(filename);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label_imagem = new javax.swing.JLabel(new javax.swing.ImageIcon(HighGui.toBufferedImage(imagem)));
		
		getContentPane().add(label_imagem);
		setResizable(false);
		pack();
		

		label_imagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				// por algum motivo o evento do mouse está passando X quando deveria ser Y
				// e vice-versa, troquei os 2 e por algum motivo funcionou
				
				filtros.pintar(imagem, arg0.getY(), arg0.getX(), filtros.random_rgb());
				
				label_imagem.setIcon(
						new javax.swing.ImageIcon(
								HighGui.toBufferedImage(imagem)
						)
				);
				
			}
		});

	}
	
	public void mostrar() {
		setVisible(true);
	}

}
