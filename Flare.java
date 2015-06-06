import processing.core.PImage;
import java.util.List;

public class Flare
   extends AnimatedActor
{
   private static final int FLARE_DURATION = 1000;
   private static final int FLARE_STEPS = 5;

   public Flare(String name, Point position, int animation_rate,
      List<PImage> imgs)
   {
      super(name, position, FLARE_DURATION, animation_rate, imgs);
   }

   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
         removePendingAction(action[0]);
         remove(world);
      };
      return action[0];
   }

   protected void scheduleAnimation(WorldModel world)
   {
      Actor.scheduleAction(world, this,
         createAnimationAction(world, FLARE_STEPS), getAnimationRate());
   }
}
