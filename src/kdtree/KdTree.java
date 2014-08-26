public class KdTree 
{
	private int size;
	private Node root;
	
	public KdTree()
	{
		size = 0;
		root = null;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	public int size()                               
	{
		return size;
	}
	
	public void insert(Point2D p)
	{
		size++;
		if (root != null)
		{
			root.insert(p);
		}
		else
		{
			root = new Node(p, true, new RectHV(0, 0, 1, 1));
		}
	}
	
	public boolean contains(Point2D p)              
	{
		return !(root == null) && root.contains(p);  
	}
	
	public void draw()                              
	{
		root.draw();
	}
	
	public Iterable<Point2D> range(RectHV rect)     
	{
		if (root == null) return new Stack<Point2D>();
		return root.range(rect, new Stack<Point2D>());
	}
	
	public Point2D nearest(Point2D p)               
	{
		if (root == null) return null;
		return root.nearest(p, null);
	}
	
	private static class Node 
	{
		public Node(Point2D p, boolean xDimension, RectHV rect)
		{
			this.p = p;
			this.left = null;
			this.right = null;
			this.xDimension = xDimension;
			this.rect = rect;
		}
		
		private Point2D p;
		private Node left;
		private Node right;
		private boolean xDimension;
		private RectHV rect;
		
		public boolean contains(Point2D q)
		{
			if (p.compareTo(q) == 0) return true;
			if (left != null && left.contains(q)) return true;
			return right != null && right.contains(q);
		}
		
		public void draw()
		{
			p.draw();
			if (left != null) left.draw();
			if (right != null) right.draw();
		}
		
		public void insert(Point2D q)
		{
			if (xDimension)
			{
				if (q.x() < p.x())
				{
					if (left == null) left = new Node(q, !xDimension, new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax()));
					else left.insert(q);
				}
				else
				{
					if (right == null) right = new Node(q, !xDimension, new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax()));
					else right.insert(q);
				}
			}
			else
			{
				if (q.y() < p.y())
				{
					if (left == null) left = new Node(q, !xDimension, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y()));
					else left.insert(q);
				}
				else
				{
					if (right == null) right = new Node(q, !xDimension, new RectHV(rect.xmin(), p.y(),  rect.xmax(), rect.ymax()));
					else right.insert(q);
				}
			}
		}
		
		public Stack<Point2D> range(RectHV r, Stack<Point2D> points)
		{
			if (!r.intersects(rect)) return points;
			if (r.contains(p)) points.push(p);
			if (left != null) points = left.range(r, points);
			if (right != null) points = right.range(r, points);
			return points;
		}
		
		public Point2D nearest(Point2D q, Point2D temp)
		{
			Point2D result;
			if (temp != null)
			{
				result = temp;
				if (rect.distanceSquaredTo(q) > q.distanceSquaredTo(temp)) return temp;
				if (q.distanceSquaredTo(q) < q.distanceSquaredTo(temp)) result = q;
			}
			else
			{
				result = q;
			}
			if (left != null) result = left.nearest(q, result);
			if (right != null) result = right.nearest(q, result);
			return result;
		}
	}
	
}