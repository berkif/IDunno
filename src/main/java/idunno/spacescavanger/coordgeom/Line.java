package idunno.spacescavanger.coordgeom;

import idunno.spacescavanger.strategy.CommonMethods;

public class Line {

	private Point startPoint;
	private Point innerPoint;
	private int length;
	private Point endPoint;

	// Rakétánál tudjuk a kilövés helyét (új rakéta megjelenésénél az owner előző
	// pozija), a hatótávját és az éppen aktuális pontját, ezekböl kell kiszámolni a
	// végpontot mivel nem végtelen egyenesről beszélünk
	public Line(Point startPoint, Point innerPoint, int length) {
		this.startPoint = startPoint;
		this.innerPoint = innerPoint;
		this.length = length;
		this.endPoint = calculateEndPoint();
	}
	public Line(Point startPoint, Point innerPoint) {
		this.startPoint = startPoint;
		this.innerPoint = innerPoint;
		this.length =(int) startPoint.distance(innerPoint);
		this.endPoint = innerPoint;
	}

	private Point calculateEndPoint() {
		double distanceStartAndInner = CommonMethods.distanceBetweenTwoPoint(startPoint, innerPoint);
		double endX = innerPoint.x()
				+ (innerPoint.x() - startPoint.x()) / distanceStartAndInner * (length - distanceStartAndInner);
		double endY = innerPoint.y()
				+ (innerPoint.y() - startPoint.y()) / distanceStartAndInner * (length - distanceStartAndInner);
		return new Point(endX, endY);
	}
	
	public int getLength() {
		return length;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Point getInnerPoint() {
		return innerPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}
	
	@Override
	public String toString() {
	    return "Line [startPoint=" + startPoint + ", endpoint=" + endPoint + ", length=" + length + "]";
	}

}
