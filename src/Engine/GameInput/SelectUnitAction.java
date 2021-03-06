package Engine.GameInput;

import java.util.ArrayList;

import Engine.GameActionSet;

/************************************************************
 * State to allow selecting an action for a unit.           *
 ************************************************************/
class SelectUnitAction extends GameInputState<GameActionSet>
{
  // Don't provide a default value, or it will override the value
  // set by the call to initOptions() in the super-constructor.
  private ArrayList<GameActionSet> myUnitActions;

  public SelectUnitAction(StateData data)
  {
    super(data);
  }

  @Override
  protected OptionSet initOptions()
  {
    // Get the set of actions this unit could perform from the target location.
    myUnitActions = myStateData.unitActor.getPossibleActions(myStateData.gameMap, myStateData.path);

    return new OptionSet(myUnitActions.toArray());
  }

  @Override
  public GameInputState<?> select(GameActionSet menuOption)
  {
    GameInputState<?> next = this;

    // Find the set in myUnitActions. We iterate because it
    // 1) allows us to avoid a cast, and
    // 2) ensures that the provided menuOption is actually one we support.
    GameActionSet chosenSet = null;
    if( null != menuOption )
    {
      for( GameActionSet set : myUnitActions )
      {
        if( set == menuOption )
        {
          chosenSet = set;
          break;
        }
      }
    }

    if( null != chosenSet )
    {
      // Add the action set to our state data.
      myStateData.actionSet = chosenSet;

      // If the action type requires no target, then we are done collecting input.
      if( !chosenSet.isTargetRequired() )
      {
        next = new ActionReady(myStateData);
      }
      else
      {
        // We need more input before an action is ready; What kind of input depends on the type of action.
        switch (chosenSet.getSelected().getType())
        {
          case ATTACK:
            // We need to select a target to attack.
            next = new SelectActionTarget(myStateData);
            break;
          case UNLOAD:
            // We need to select a unit to unload.
            next = new SelectCargo(myStateData);
            break;
          default:
            break;
        }
      }
    }
    return next;
  }

  @Override
  public void back()
  {
    myStateData.actionSet = null;
  }
}