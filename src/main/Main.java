package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		//Criar o frame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Blue Boy Adventure");
		
		//Adicionar o painel ao frame
		GamePanel panel = new GamePanel();
		frame.add(panel);
		
		//Seta o tamanho desse frame para o tamanho preferido do painel
		frame.pack();
	
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		panel.startGameThread();
	}
}
