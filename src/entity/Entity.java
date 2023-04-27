package entity;

import java.awt.image.BufferedImage;

public class Entity {

	//Atributos para cuidar do movimento do jogador
	public int x, y;
	public int speed;
	
	//Descreve uma imagem com um buffer de imagens acessível
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	//String que define a direção que a entidade está virada
	public String direction;
	
	//Atributos para realizar a contagem de sprites para animação
	public int spriteCounter = 0;
	public int  spriteNum = 1;
}
