import java.util.TreeSet;

public class PointSET 
{
    private TreeSet<Point2D> set; 
	
	public PointSET()
	{
		set = new TreeSet<Point2D>();
	}
	
	public boolean isEmpty()
	{
		return set.isEmpty();
	}
	
	public int size()                               
	{
		return set.size();
	}
	
	public void insert(Point2D p)
	{
		set.add(p);
	}
	
	public boolean contains(Point2D p)              
	{
		return set.contains(p);
	}
	
	public void draw()                              
	{
		while (set.iterator().hasNext())
		{
			set.iterator().next().draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect)     
	{
		Stack<Point2D> points = new Stack<Point2D>();
		while (set.iterator().hasNext())
		{
			Point2D p = set.iterator().next();
			if (rect.contains(p))
			{
				points.push(p);
			}
		}
		return points;
	}
	
	public Point2D nearest(Point2D p)               
	{
		Point2D result = set.iterator().next();
		double distance = p.distanceSquaredTo((result));
		while (set.iterator().hasNext())
		{
			Point2D nextPoint = set.iterator().next();
			double temp = p.distanceSquaredTo(nextPoint);
			if (temp < distance)
			{
				distance = temp;
				result = nextPoint;
			}
		}
		return result;
	}
	
}