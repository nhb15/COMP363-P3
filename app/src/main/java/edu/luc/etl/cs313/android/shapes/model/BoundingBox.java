package edu.luc.etl.cs313.android.shapes.model;

import java.util.ArrayList;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	/**
	 * onCircle generates a bounding box rectangle starting from the upper left of the circle
	 * @param c is the circle to be bounded
	 * @return the Location encasing the rectangle
	 */
	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	/**
	 * onFill decorator calls the bounding box visitor for the correct shape inside it
	 * @param f is the fill to be bounded
	 * @return the Location of the bounding box for the shape we outsource to
	 */
	@Override
	public Location onFill(final Fill f) {

		return f.getShape().accept(this);
	}

	/**
	 * onGroup calls the bounding box visitor for the correct shapes inside of it
	 * Once the bounding box is obtained, we find the minimum X and Y's for each box AND the maximum
	 * X and Y's (width of boxes considered) in order to create the overall-encasing box
	 * @param g is the group to dig through
	 * @return the overall group bounding box
	 */
	@Override
	public Location onGroup(final Group g) {

		//BoundingBoxList will hold the bounding box from each element in the group
		ArrayList<Location> boundingBoxList = new ArrayList<Location>();

		for (int i = 0; i < g.getShapes().size(); i++){

			//Let's visit the corresponding shape's method in order to retrieve the bounding box for that shape
			Location locShape = g.getShapes().get(i).accept(this);

			boundingBoxList.add(locShape);
		}

		//The xMin and yMin can be initialized to the first element's value
		int xMin = boundingBoxList.get(0).getX();
		int yMin = boundingBoxList.get(0).getY();

		//The first xMax and yMax can be initialized the same way
		int yMax = yMin;
		int xMax = xMin;

		for (int i = 0; i < g.getShapes().size(); i++){

			//We KNOW the bounding box list holds rectangles, but we need this statement to typecast it for the getWidth/getHeight methods
			if (boundingBoxList.get(i).getShape() instanceof Rectangle) {
				Rectangle rect = (Rectangle) boundingBoxList.get(i).getShape();

				//If xMin is GREATER than the value in the group's element, we need to update xMin to match the element's state
				if (xMin > boundingBoxList.get(i).getX()){
					xMin = boundingBoxList.get(i).getX();
				}

				//If yMin is GREATER than the value in the group's element, we need to update yMin to match the element's state
				if (yMin > boundingBoxList.get(i).getY()){
					yMin = boundingBoxList.get(i).getY();
				}

				//If xMax is LESS THAN the value in the group's element, we need to update xMax to match the element's state
				if (xMax < (boundingBoxList.get(i).getX() + rect.getWidth())){
					xMax = (boundingBoxList.get(i).getX() + rect.getWidth());
				}

				//If yMax is LESS THAN the value in the group's element, we need to update yMax to match the element's state
				if (yMax < (boundingBoxList.get(i).getY() + rect.getHeight())){
					yMax = (boundingBoxList.get(i).getY() + rect.getHeight());
				}

			}
		}

		//The bounding box can now be returned, starting at xMin, yMin, with width being the difference between xMin and xMax, and the height being the difference between yMin and yMax.
		return new Location(xMin, yMin, new Rectangle((xMax - xMin), (yMax - yMin)));
	}

	/**
	 * onLocation grabs the bounding box for the shape encased inside of it and cross-references the location from that bounding box and the location passed in
	 * For example, if a location's shape is a circle and that circle's bounding box has X-location -50, but the X-location passed in is 200, the returned box will have X-location 150.
	 * @param l is the location passed in
	 * @return the bounding box for the location passed in
	 */
	@Override
	public Location onLocation(final Location l) {

		Location shapeLoc = l.getShape().accept(this);

		int x = shapeLoc.getX();
		int y = shapeLoc.getY();

		//In order to compare the relative location of the shape to the real X/Y of the Location passed in, we need to add the two:
		int realX = l.getX() + x;
		int realY = l.getY() + y;
		return new Location(realX, realY, shapeLoc.getShape());

	}

	/**
	 * onRectangle supplies the bounding box for a rectangle
	 * @param r is the rectangle passed in
	 * @return the Location for the bounding box of the rectangle (which is just the rectangle itself encased)
	 */
	@Override
	public Location onRectangle(final Rectangle r) {
		//The bounding box of a rectangle is the rectangle itself in a Location of relative 0, 0
		return new Location(+0, +0, r);

	}

	/**
	 * onStrokeColor is a decorator that returns the bounding box of the shape encased inside it
	 * @param c is the StrokeColor
	 * @return the bounding box of the shape encased inside it
	 */
	@Override
	public Location onStrokeColor(final StrokeColor c) {

		return c.getShape().accept(this);
	}

	/**
	 * onOutline is a decorator that returns the bounding box of the shape encased inside it
	 * @param o is the Outline
	 * @return the bounding box of the shape encased inside it
	 */
	@Override
	public Location onOutline(final Outline o) {

		return o.getShape().accept(this);
	}

	/**
	 * onPolygon utilizes onGroup to create a bounding box for the group of points within a polygon
	 * @param s is the polygon provided
	 * @return the bounding box for the polygon
	 */
	@Override
	public Location onPolygon(final Polygon s) {

		//Since a polygon is just a group of points, we can utilize onGroup to avoid repeating much of the same code. 
		return onGroup(s);

	}
}
