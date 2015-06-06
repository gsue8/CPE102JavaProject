import processing.core.PImage;

import java.util.List;

public class Creature 
   extends MobileAnimatedActor
{
	private int QUAKE_ANIMATION_RATE = 100;
	public Creature(String name, Point position, int rate, int animation_rate,
		      List<PImage> imgs)
	{
		super(name, position, rate, animation_rate, imgs);
	}
	
	protected boolean canPassThrough(WorldModel world, Point pt)
	{
	      return !world.isOccupied(pt);
	}
	
	protected boolean move(WorldModel world, WorldEntity minernotfull)
	{
		if(minernotfull == null)
		{
			return false;
		}
		if(adjacent(getPosition(), minernotfull.getPosition()))
		{
			minernotfull.remove(world);
			return true;
		}
		else
		{
	         world.moveEntity(this, nextPosition(world, minernotfull.getPosition()));
	         return false;
		}
	}
	

	public Action createAction(WorldModel world, ImageStore imageStore)
	   {
	      Action[] action = { null };
	      action[0] = ticks -> {
	         removePendingAction(action[0]);

	         WorldEntity target = world.findNearest(getPosition(), MinerNotFull.class);
	         //long nextTime = ticks + getRate();
	         long nextTime = ticks+100;
	      if(target != null)
	      {  
	    	  Point tpt = target.getPosition();
	         if(move(world, target))
	         {
	        	 Quake quake = createQuake(world, tpt, ticks, imageStore);
	        	 world.addEntity(quake);
	        	 nextTime = nextTime + 100;
	        	 
	         }
	         scheduleAction(world, this, createAction(world, imageStore),
	            nextTime);
	      }   
	      };
	      return action[0];
	   }
	
	private Quake createQuake(WorldModel world, Point pt, long ticks,
		      ImageStore imageStore)
		   {
		      Quake quake = new Quake("quake", pt, QUAKE_ANIMATION_RATE,
		         imageStore.get("quake"));
		      quake.schedule(world, ticks, imageStore);
		      return quake;
		   }

}
