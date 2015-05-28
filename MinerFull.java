import processing.core.PImage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MinerFull
   extends Miner
{
	//protected List<Point> uh;
	//protected List<Point> vuh;
   public MinerFull(String name, Point position, int rate,
      int animation_rate, int resource_limit, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, resource_limit,
         resource_limit, Blacksmith.class, imgs);
     // this.uh = new ArrayList<Point>();
   }

   protected Miner transform(WorldModel world)
   {
      return new MinerNotFull(getName(), getPosition(), getRate(),
         getAnimationRate(), getResourceLimit(), getImages());
   }

   protected boolean move(WorldModel world, WorldEntity smith)
   {
      if (smith == null)
      {
         return false;
      }

      if (adjacent(getPosition(), smith.getPosition()))
      {
         setResourceCount(0);
         return true;
      }
      else
      {
         world.moveEntity(this, nextPosition(world, smith.getPosition(), path, visited ));
         return false;
      }
   }
}
