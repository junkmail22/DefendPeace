package UI.Art.SpriteArtist;

import java.awt.Graphics;

import Engine.GameInstance;
import Units.Unit;

public class SpriteUnitArtist
{
  private GameInstance myGame;
  private SpriteMapView myView;
  int drawScale;

  public SpriteUnitArtist(GameInstance game, SpriteMapView view)
  {
    myGame = game;
    myView = view;
    drawScale = SpriteOptions.getDrawScale();
  }

  /**
   * Draws any applicable unit icons for the unit, at the specified real location.
   * "Real" means that the specified x and y are that of the game's
   * underlying data model, not of the draw-space.
   */
  public void drawUnitIcons(Graphics g, Unit unit, double x, double y)
  {
    // Convert "real" location into a draw-space location, then draw icons.
    int drawX = (int) (myView.getTileSize() * x);
    int drawY = (int) (myView.getTileSize() * y);
    SpriteLibrary.getMapUnitSpriteSet(unit).drawUnitIcons(g, myGame.activeCO, unit, drawX, drawY, drawScale);
  }

  /**
   * Allows drawing of a single unit, at a specified real location.
   * "Real" means that the specified x and y are that of the game's
   * underlying data model, not of the draw-space.
   */
  public void drawUnit(Graphics g, Unit unit, int x, int y, int animIndex)
  {
    // Convert "real" game-model location to a draw-space location.
    int drawX = (int) (myView.getTileSize() * x);
    int drawY = (int) (myView.getTileSize() * y);

    // Draw the unit at the specified location.
    SpriteLibrary.getMapUnitSpriteSet(unit).drawUnit(g, myGame.activeCO, unit, /*currentAction,*/
    animIndex, drawX, drawY, drawScale, myView.getFlipUnitFacing(unit.CO));
  }
}
