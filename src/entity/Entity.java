package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

	//Atributos para cuidar do movimento da entidade
	public int worldX, worldY;
	public int speed;
	
	//Descreve uma imagem com um buffer de imagens acessível
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	//String que define a direção que a entidade está virada
	public String direction;
	
	//Atributos para realizar a contagem de sprites para animação, e descobrir o fps
	public int spriteCounter = 0;
	public int  spriteNum = 1;
	
	//Cria um retângulo para definir a área de colisão
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
}
