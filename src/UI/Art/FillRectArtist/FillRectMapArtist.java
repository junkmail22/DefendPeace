package UI.Art.FillRectArtist;

import java.awt.Color;
import java.awt.Graphics;

import Engine.GameInstance;
import Engine.Path;
import Engine.Path.PathNode;
import Terrain.Environment;
import Terrain.GameMap;
import UI.MapView;

public class FillRectMapArtist
{
  private int tileSizePx;

  public static final Color COLOR_GRASS = new Color(186, 255, 124);
  public static final Color COLOR_CITY = Color.GRAY;
  public static final Color COLOR_FACTORY = Color.DARK_GRAY;
  public static final Color COLOR_FOREST = Color.GREEN;
  public static final Color COLOR_SEA = Color.BLUE;
  public static final Color COLOR_MOUNTAIN = new Color(101, 40, 26);
  public static final Color COLOR_REEF = new Color(212, 144, 56);
  public static final Color COLOR_ROAD = Color.LIGHT_GRAY;
  public static final Color COLOR_SHOAL = new Color(240, 209, 77);
  public static final Color HIGHLIGHT_MOVE = new Color(255, 255, 255, 160);
  public static final Color HIGHLIGHT_ATTACK = new Color(255, 0, 0, 160);
  public static final Color COLOR_CURSOR = new Color(253, 171, 77, 200);

  private GameInstance myGame;
  private GameMap gameMap;

  public FillRectMapArtist(GameInstance game)
  {
    myGame = game;
    gameMap = game.gameMap;
  }

  public void setView(MapView view)
  {
    tileSizePx = view.getTileSize();
  }

  public void drawMap(Graphics g)
  {
    for( int w = 0; w < gameMap.mapWidth; ++w )
    {
      for( int h = 0; h < gameMap.mapHeight; ++h )
      {
        if( gameMap.isLocationValid(w, h) )
        {
          drawLocation(g, gameMap.getLocation(w, h), w * tileSizePx, h * tileSizePx);
        }
        else
        {
          System.out.println("Error! Tile is null! (" + w + ", " + h + ")");
        }
      }
    }
  }

  private void drawLocation(Graphics g, Terrain.Location locus, int x, int y)
  {
    Environment tile = locus.getEnvironment();
    Color tileColor = tile.terrainType.getMainColor();

    g.setColor(tileColor);
    g.fillRect(x, y, tileSizePx, tileSizePx);
  }

  public void drawCursor(Graphics g)
  {
    g.setColor(COLOR_CURSOR);
    g.fillRect(myGame.getCursorX() * tileSizePx, myGame.getCursorY() * tileSizePx, tileSizePx, tileSizePx);
  }

  public void drawMovePath(Graphics g, Path path)
  {
    g.setColor(COLOR_CURSOR);
    for( PathNode p : path.getWaypoints() )
    {
      g.fillRect(p.x * tileSizePx, p.y * tileSizePx, tileSizePx, tileSizePx);
    }
  }

  public void drawHighlights(Graphics g)
  {
    for( int w = 0; w < gameMap.mapWidth; ++w )
    {
      for( int h = 0; h < gameMap.mapHeight; ++h )
      {
        if( gameMap.isLocationValid(w, h) )
        {
          Terrain.Location locus = gameMap.getLocation(w, h);
          if( locus.isHighlightSet() )
          {
            if( locus.getResident() != null && locus.getResident().CO.isEnemy(myGame.activeCO) )
            {
              g.setColor(HIGHLIGHT_ATTACK);
            }
            else
            {
              g.setColor(HIGHLIGHT_MOVE);
            }
            g.fillRect(w * tileSizePx, h * tileSizePx, tileSizePx, tileSizePx);
          }
        }
      }
    }
  }
}
