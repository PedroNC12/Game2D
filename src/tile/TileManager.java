package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Método para carregar o mapa baseado em um arquivo de texto
	public void loadMap(String path) {
		try {
			InputStream is = getClass().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			//Esse loop realiza a leitura do arquivo linha por linha
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line =  br.readLine();
				
				//Esse loop realiza a leitura de cada número nessa linha
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
//		Essa é a forma manual de se fazer, porém não é muito eficiente
//		g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
//		g2.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize, null);
//		g2.drawImage(tile[2].image, 96, 0, gp.tileSize, gp.tileSize, null);
		
		//Atributos para definir a posição dos quadrados
		int worldCol = 0;
		int worldRow = 0;
		
		
		//Loop que vai de fato desenhar os tiles no cenário
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			
			//Posição do quadrado em relação ao mapa do mundo
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			//Posição do tile em relação à tela, ou melhor em relação à posição do jogador
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY -gp.player.worldY + gp.player.screenY;
			
			//Garantir que apenas sejam desenhados os tiles que apareçam na tela
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			
			worldCol++;
			
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
				
			}
		}
	}
}
