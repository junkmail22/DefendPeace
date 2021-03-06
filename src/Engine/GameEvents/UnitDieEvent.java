package Engine.GameEvents;

import Engine.XYCoord;
import Terrain.MapMaster;
import UI.MapView;
import UI.Art.Animation.GameAnimation;
import Units.Unit;

public class UnitDieEvent implements GameEvent
{
  private Unit unit;

  public UnitDieEvent( Unit unit )
  {
    this.unit = unit;
  }

  @Override
  public GameAnimation getEventAnimation(MapView mapView)
  {
    return mapView.buildUnitDieAnimation();
  }

  @Override
  public void sendToListener(GameEventListener listener)
  {
    listener.receiveUnitDieEvent( this );
  }

  @Override
  public void performEvent(MapMaster gameMap)
  {
    // Set HP to 0. One could make a UnitDieEvent on a healthy
    // unit, and we don't want any ambiguity after the fact.
    unit.damageHP(unit.getHP()+1);

    // Remove the Unit from the map and from the CO list.
    gameMap.removeUnit(unit);
    unit.CO.units.remove(unit);
  }

  @Override
  public XYCoord getStartPoint()
  {
    return new XYCoord(unit.x, unit.y);
  }

  @Override
  public XYCoord getEndPoint()
  {
    return new XYCoord(unit.x, unit.y);
  }
}
