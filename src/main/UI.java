package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {

	GamePanel gp;
	Font arial, arialBig;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	double playTime;
	DecimalFormat format = new DecimalFormat("#0.00"); 
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial = new Font("Arial", Font.PLAIN, 40);
		arialBig = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	//Desenhar a interface na tela
	public void draw(Graphics2D g2) {
		
		//Checar se o player finalizou o jogo
		if(gameFinished == true) {
			g2.setFont(arial);
			g2.setColor(Color.white);
			
			String text;
			int textLength;
			int x;
			int y;
			
			text = "You found the treasure!";
			//Pegar a largura do texto
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Your Time is: " +format.format(playTime)+"!";
			
			//Pegar a largura do texto
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + gp.tileSize*4;
			g2.drawString(text, x, y);
			
			g2.setFont(arialBig);
			g2.setColor(Color.yellow);
			

			text = "Congratulations!";
			//Pegar alargura do texto
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + gp.tileSize*2;
			g2.drawString(text, x, y);
			gp.gameThread = null;
		}
		//Caso o jogo não tenha terminado
		else {
			//Selecionar a fonte e a cor, não se deve instanciar objetos dentro do loop
			g2.setFont(arial);
			g2.setColor(Color.white);
			//Mostrar a quantidade de chaves, nesse método o eixo y é levado 
			//em consideração a parte de baixo do texto
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			g2.drawString("X "+gp.player.hasKey, 72, 65);
			
			//Mostrar o tempo de jogo
			playTime +=  (double) 1/60;
			g2.drawString("Time:"+format.format(playTime), gp.tileSize*11, 65);
			
			//Mostrar a mensagem caso haja interação
			if(messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(30f));
				g2.drawString(message, gp.tileSize*6, gp.screenHeight-50);
				
				
				//Definir o tempo que a imagem será mostrada
				messageCounter++;
				if(messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
				
			}
		}
		
	}
}
