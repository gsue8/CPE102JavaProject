import processing.core.PImage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MinerNotFull
   extends Miner
{
  // protected LinkedList<Point> pathy;
   //protected List<Point> visitedy;
   public MinerNotFull(String name, Point position, int rate,
      int animation_rate, int resource_limit, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, resource_limit,
         0, Ore.class, imgs);
      //this.pathy = new LinkedList();
     // this.visitedy = new ArrayList();
   }

   public String toString()
   {
      return String.format("miner %s %d %d %d %d %d", getName(),
         getPosition().x, getPosition().y, getResourceLimit(),
         getRate(), getAnimationRate());
   }

   protected Miner transform(WorldModel world)
   {
      if (getResourceCount() < getResourceLimit())
      {
         return this;
      }
      else
      {
         return new MinerFull(getName(), getPosition(), getRate(),
            getAnimationRate(), getResourceLimit(), getImages());
      }
   }

   protected boolean move(WorldModel world, WorldEntity ore)
   {
      if (ore == null)
      {
         return false;
      }

      if (adjacent(getPosition(), ore.getPosition()))
      {
         setResourceCount(getResourceCount() + 1);
         ore.remove(world);
         return true;
      }
      else
      {
         world.moveEntity(this, nextPosition(world, ore.getPosition(), path, visited));
         return false;
      }
   }
}
