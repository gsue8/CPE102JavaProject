import processing.core.PImage;

import java.util.List;

public class Wyvern 
   extends MobileAnimatedActor
{
	private static final int FLARE_ANIMATION_RATE = 100;
	
	public Wyvern(String name, Point position, int rate, int animation_rate,
		      List<PImage> imgs)
	{
		super(name, position, rate, animation_rate, imgs);
	}
	
	protected boolean canPassThrough(WorldModel world, Point pt)
	{
	      return !world.isOccupied(pt);
	}
	
	protected boolean move(WorldModel world, WorldEntity creature)
	{
		if(creature == null)
		{
			return false;
		}
		if(adjacent(getPosition(), creature.getPosition()))
		{
			creature.remove(world);
			return true;
		}
		else
		{
	         world.moveEntity(this, nextPosition(world, creature.getPosition()));
	         return false;
		}
	}
	

	public Action createAction(WorldModel world, ImageStore imageStore)
	   {
	      Action[] action = { null };
	      action[0] = ticks -> {
	         removePendingAction(action[0]);

	         WorldEntity target = world.findNearest(getPosition(), Creature.class);
	         long nextTime = ticks + 200;
	         
	        //
	         if(target != null)
	         {
	        	 Point tpt = target.getPosition();
	        	 if(move(world, target))
	        	 {
	        		 Flare flare = createFlare(world, tpt, ticks, imageStore);
	        		 world.addEntity(flare);
	        		 nextTime = nextTime + 50;
	        	 }
	         }
	         scheduleAction(world, this, createAction(world, imageStore),
	            nextTime);
	         
	      };
	      return action[0];
	   }
	
	 private Flare createFlare(WorldModel world, Point pt, long ticks,
			      ImageStore imageStore)
	 {
		 Flare flare = new Flare("flare", pt, FLARE_ANIMATION_RATE,
			         imageStore.get("flare"));
		 flare.schedule(world, ticks, imageStore);
		 return flare;
	 }	
}
