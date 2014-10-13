public class KdTree 
{
    private Node root;
    
    public KdTree()
    {
        root = null;
    }
    
    public boolean isEmpty()
    {
        return root == null;
    }
    
    public int size()                               
    {
    	if (root == null) return 0;
        return root.size();
    }
    
    public void insert(Point2D p)
    {
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
    	if (root == null) return;
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
        
        private Point2D p;
        private Node left;
        private Node right;
        private boolean xDimension;
        private RectHV rect;
        
        public Node(Point2D p, boolean xDimension, RectHV rect)
        {
            this.p = p;
            this.left = null;
            this.right = null;
            this.xDimension = xDimension;
            this.rect = rect;
        }
        
        public int size()
        {
        	int size = 1;
        	if (left != null) size += left.size();
        	if (right != null) size += right.size();
        	return size;
        }
        
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
            Stack<Point2D> result = points;
            if (r.contains(p)) result.push(p);
            if (left != null) result = left.range(r, result);
            if (right != null) result = right.range(r, result);
            return result;
        }
        
        public Point2D nearest(Point2D target, Point2D temp)
        {
            Point2D result;
            if (temp != null)
            {
                result = temp;
                if (rect.distanceSquaredTo(target) > target.distanceSquaredTo(temp)) return temp;
                if (target.distanceSquaredTo(p) < target.distanceSquaredTo(temp)) result = p;
            }
            else
            {
                result = p;
            }
            if (left != null) result = left.nearest(target, result);
            if (right != null) result = right.nearest(target, result);
            return result;
        }
        
    }
    
	public static void main(String[] args)
	{
		KdTree set = new KdTree();
		for (int i=0;i<100000;i++)
		{
			try
			{
				Point2D p = new Point2D(StdRandom.uniform(), StdRandom.uniform()); 
				set.insert(p);
				System.out.println(i + ": " + set.size() + " : " + p.toString());
			}
			catch(Exception x)
			{
				System.out.println(i + ":" + x.getMessage());
			}
		}
		
		Point2D n = set.nearest(new Point2D(0, 0));
		System.out.println(set.contains(n));
		
		
		
	}
    
}