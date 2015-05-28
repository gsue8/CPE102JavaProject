import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class MobileAnimatedActor
   extends AnimatedActor
{
   protected List<Point> path;
   protected List<Point> visited;
   
   public MobileAnimatedActor(String name, Point position, int rate,
      int animation_rate, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
      this.path = new ArrayList();
      this.visited = new ArrayList();
   }

   public boolean dfs(Point start, Point end, WorldModel world, 
		   List<Point> path, List<Point> visited) 
   {

	   if(!(world.withinBounds(start)))
	   {
		   
		   /*System.out.println(start.y);
		   System.out.println("x from bound");
		   System.out.println(start.x);
		   System.out.println("not in bounds");*/
		   return false;
	   }
	   if((start.y == end.y) && (start.x == end.x))
	      {
	    	 path.add(0, start);
	    	// System.out.println("goal" + start.x +"," + start.y);
	    	 //System.out.println("reached goal");
	    	 //visited.add(0, start);
	         return true;
	      }
	      if(!canPassThrough(world, start))
	      //if(world.isOccupied(start))
	      {
	    	 if(!(getPosition().x == start.x && getPosition().y == start.y))
	    	 {
	    		 return false;
	    	 }
	    	 //System.out.println("occupied at:" +start.x+","+start.y);
	      }
	      for(int i = 0; i<visited.size(); i++)
	      {
	    	  if(visited.get(i).x == start.x && visited.get(i).y == start.y)
	    	  {
	    		/*System.out.print(start.y);
	    	 	System.out.print(start.x);
	    	 	System.out.println("visited");*/
	    		return false;
	    	  }
	      }

	      visited.add(start);
	    /*  System.out.println(" after visited");
	      System.out.println(start.x);
	      System.out.println(start.y);*/
	     // System.out.println("visited:" + visited.size());
	    
	      boolean found = dfs(new Point((start.x)-1, (start.y)), end, world, path, visited) ||
	                 dfs(new Point((start.x), (start.y)-1), end, world, path, visited) ||
	                 dfs(new Point((start.x)+1, (start.y)), end, world, path, visited) ||
	                 dfs(new Point((start.x), (start.y)+1), end, world, path, visited);
	
	     
	      if(found)
	      {
	    	  path.add(start);
	    	 // System.out.println("found");
	      }
	      return found; 
   }
      

   
   protected Point nextPosition(WorldModel world, Point dest_pt, List<Point> path, List<Point> visited)
   {
	  path.clear();
	  visited.clear();
	  /* if(world.withinBounds(getPosition()))
	   {
		   System.out.println("inbounds");
	   }
	   System.out.println(getPosition().y);
	   System.out.println(getPosition().x);
	   System.out.println(dest_pt.x);
	   System.out.println(dest_pt.y);
	   dfs(getPosition(), dest_pt, world, path, visited);
	   System.out.println("visitedsize");
	   //System.out.println(visited.size());
	   Point pos = new Point(getPosition().x, getPosition().y);
	   
	   System.out.println("endpt" + dest_pt.x +"," +dest_pt.y);
	   System.out.println("visited:" + visited.size());*/
	  //System.out.println("startpt:" + getPosition().x +","+ getPosition().y);
	   dfs(getPosition(), dest_pt, world, path, visited);
	   int i = path.size()-1;
	   //System.out.println(i);
	  System.out.println("pathsize:" + path.size());
	 // System.out.println("i:" + i);
	  if(path.size() == 0)
	  {
		  return getPosition();
	  }
	  /*System.out.println(path.size());
	  System.out.println("inlist" + path.get(i).x +","+ path.get(i).y);
	  System.out.println("last:" + path.get(i).x +","+ path.get(i).y);
	  System.out.println("last-1:" + path.get(i-1).x +","+ path.get(i-1).y);
	  System.out.println("last-2:" + path.get(i-2).x +","+ path.get(i-2).y);
	  System.out.println("last-3:" + path.get(i-3).x +","+ path.get(i-3).y);
	  System.out.println("first" + path.get(0).x + "," + path.get(0).y);*/
	  // Point new_pt = new Point(path.get(i-1).x, path.get(i-1).y);
	   Point new_pt = new Point(path.get(i-1).x, path.get(i-1).y);
	  // System.out.println("first step" +new_pt.x + "," + new_pt.y);
	   return new_pt;
	   
	/*  int horiz = Integer.signum(dest_pt.x - getPosition().x);
      Point new_pt = new Point(getPosition().x + horiz, getPosition().y);

      if (horiz == 0 || !canPassThrough(world, new_pt))
      {
         int vert = Integer.signum(dest_pt.y - getPosition().y);
         new_pt = new Point(getPosition().x, getPosition().y + vert);

         if (vert == 0 || !canPassThrough(world, new_pt))
         {
            new_pt = getPosition();
         }
      }
      return new_pt;*/
   }

   protected static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && abs(p1.x - p2.x) == 1);
   }

   protected abstract boolean canPassThrough(WorldModel world, Point new_pt);
}
