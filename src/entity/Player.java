package entity;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler key;
	
	//Definir a posição do jogador na tela
	public final int screenX;
	public final int screenY;
	
	//Definir a quantidade de chaves que o jogador carrega
	public int hasKey = 0;
	
	public int counter;
	
	//Construtor da classe
	public Player(GamePanel gp, KeyHandler key) {
		this.gp = gp;
		this.key = key;
		
		//Definir a posição no centro da tela
		screenX = gp.screenWidth / 2 - (gp.tileSize/2);
		screenY = gp.screenHeight / 2 - (gp.tileSize/2);
		
		//Definir o tamanho e a posição do triângulo de colisão
		solidArea = new Rectangle(8, 16, 28, 28);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		//Definir a posição do jogador no mundo
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		
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
			//Atualizar a direção do jogador
			if(key.upPressed == true) {
				direction = "up";
			}
			else if(key.downPressed == true) {
				direction = "down";
			}
			if(key.leftPressed == true) {
				direction = "left";
			}
			else if(key.rightPressed == true) {
				direction = "right";
			}
			
			//Checar se o player está colidindo com algo
			collisionOn = false;
			gp.checker.checkTile(this);
			int objIndex = gp.checker.checkObject(this, true);
			pickUpObject(objIndex);
			
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			//Mudar o sprite do jogador para realizar a animação de andar
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
	
	public void pickUpObject(int i){
		//Se 999 não há contato com o objeto
		if(i != 999) {
			String objName = gp.obj[i].name;
			
			//Registrar a interação com o objeto
			switch(objName) {
			case "key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "door":
				if(hasKey > 0) {
					gp.playSE(3);
					gp.obj[i] = null;
					hasKey--;
					gp.ui.showMessage("Door opened!");
				}
				else {
					gp.ui.showMessage("You need a key!");
				}
				break;
			case "boots":
				gp.playSE(2);
				speed += 2;
				gp.obj[i] = null;
				gp.ui.showMessage("Speed up!");
				break;
			case "chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
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
		
		//Carregar os sprites de andar
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
