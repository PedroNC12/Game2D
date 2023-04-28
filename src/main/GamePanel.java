package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	//Configurações de tela
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; 
	public final int screenHeight = tileSize * maxScreenRow;
	
	//Configurações de mundo
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//Definir o FPS do jogo
	int FPS = 60;
	
	KeyHandler key = new KeyHandler();
	Thread gameThread;
	public Player player = new Player(this, key);
	TileManager tileM = new TileManager(this);
	
	
	//Criar o painel do jogo
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.darkGray);
		this.setDoubleBuffered(true);
		this.addKeyListener(key);
		this.setFocusable(true);
	}
	
	//Método que irá iniciar a thread
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

/*	Ambos os próximos métodos podem ser usados para criar o loop do jogo
 * @Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDraw = System.nanoTime() + drawInterval;
		
		
		//Esse loop será o núcleo do jogo
		while(gameThread != null) {
			
			//Atualizar informações, como a posição do jogador
			update();
			
			//Atualizar a imagem da tela
			repaint();
			
			//Checar o tempo restante até a próxima execução
			try {
				double remainingTime = nextDraw - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDraw += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
*/	
	
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime =System.nanoTime();
		long currentTime;
//		long timer = 0;
//		int drawCount = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
//			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
//				drawCount++;
			}
			
//			if(timer >= 1000000000) {
//				System.out.println("FPS: " +drawCount);
//				drawCount = 0;
//				timer = 0;
//			}
		}
	}
	//Método para atualizar as informações
	public void update() {
		player.update();
	}
	
	//Método para desenhar a imagem non painel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//A classe Graphic2D possui um controle mais sofisticado para gráficos 2D
		Graphics2D g2 = (Graphics2D)g;
		
		//É importante que o tile venha antes do player, pois é desenhado em camadas
		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose();
	}
}
