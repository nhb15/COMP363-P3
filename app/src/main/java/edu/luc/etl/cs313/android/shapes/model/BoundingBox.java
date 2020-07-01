package edu.luc.etl.cs313.android.shapes.model;

import java.util.ArrayList;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	public Location onFill(final Fill f) {

		return f.getShape().accept(this);
	}

	@Override
	public Location onGroup(final Group g) {

		ArrayList<Location> boundingBoxList = new ArrayList<Location>();
		//Currently, having issue with either sending a circle to boundingbox OR changing onLocation to visit the class will give us a rectangle BUT it gives us a negative x/y location value
		for (int i = 0; i < g.getShapes().size(); i++){
			Location locShape = g.getShapes().get(i).accept(this);

			boundingBoxList.add(locShape);
		}

		int xMin = boundingBoxList.get(0).getX();
		int yMin = boundingBoxList.get(0).getY();
		int yMax = yMin;
		int xMax = xMin;



		for (int i = 0; i < g.getShapes().size(); i++){

			if (boundingBoxList.get(i).getShape() instanceof Rectangle) {
				Rectangle rect = (Rectangle) boundingBoxList.get(i).getShape();

				if (xMax < (xMin + rect.getWidth())){
					xMax = (xMin + rect.getWidth());
				}

				if (yMax < (yMin+ rect.getHeight())){
					yMax = yMin + rect.getHeight();
				}


			}

		}

		return new Location(xMin, yMin, new Rectangle((xMax - xMin), (yMax - yMin)));

	}

	@Override
	public Location onLocation(final Location l) {

		if (l.getShape() instanceof Rectangle){
			return l;
		}
		else {
			return l.getShape().accept(this);
		}
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		final int width = r.getWidth();
		final int height = r.getHeight();

		return new Location(-0, -0, new Rectangle(width, height));

	}

	@Override
	public Location onStrokeColor(final StrokeColor c) {

		return c.getShape().accept(this);
	}

	@Override
	public Location onOutline(final Outline o) {

		return o.getShape().accept(this);
	}

	@Override
	public Location onPolygon(final Polygon s) {
		//use a loop to check th4e x and y coordinates, using min max for both x and y
		//can find the bounding box locations

		int xMax = s.getPoints().get(0).getx();
		int xMin = xMax;

		int yMax = s.getPoints().get(0).gety();
		int yMin = yMax;

		for (int i = 1; i <s.getPoints().size(); i++){
			int x = s.getPoints().get(i).getx(); //gets x coordinate from first set of points in list of points
			int y = s.getPoints().get(i).gety();

			if (xMax < x){
				xMax = x;
			}

			if (xMin > x){
				xMin = x;
			}

			if (yMax < y){
				yMax = y;
			}

			if (yMin > y){
				yMin = y;
			}
			
		}

		return new Location(xMin, yMin, new Rectangle((xMax - xMin), (yMax - yMin)));
	}
}
