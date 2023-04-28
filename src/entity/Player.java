package entity;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler key;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler key) {
		this.gp = gp;
		this.key = key;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize/2);
		screenY = gp.screenHeight / 2 - (gp.tileSize/2);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize *21;
		speed = 4;
		direction = "down";
	}
	
	//Método para carregar os sprites do jogador
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		//Checar se alguma tecla está pressionada e então realizar o update
		if(key.upPressed == true || key.downPressed == true 
		   || key.leftPressed == true || key.rightPressed ==true) {
			//Atualiar a posição do jogador
			if(key.upPressed == true) {
				direction = "up";
				worldY -= speed;
			}
			else if(key.downPressed == true) {
				direction = "down";
				worldY += speed;
			}
			if(key.leftPressed == true) {
				direction = "left";
				worldX -= speed;
			}
			else if(key.rightPressed == true) {
				direction = "right";
				worldX += speed;
			}
			
			spriteCounter++;
			if(spriteCounter > 15) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}else {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
			
	}
	
	public void draw(Graphics2D g2){
		
//				//seta a cor para realizar os desenhos
//				g2.setColor(Color.white);
//				
//				//Criar um retangulo e o preenche com a cor setada
//				g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum==1) {
				image = up1;
			}else {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum==1) {
				image = down1;
			}else {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum==1) {
				image = left1;
			}else {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum==1) {
				image = right1;
			}else {
				image = right2;
			}
			break;
		}
		
		//Método para desenhar uma imagem na tela
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
