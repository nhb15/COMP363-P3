package edu.luc.etl.cs313.android.shapes.model;

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

		Shape shapeFill = f.getShape();
		//What's the correct way to do this? What if fill holds a group or something? 
		//Maybe we should add a clause to check if there is a stroke color, group, or outline
		//then we can call onOutline, onGroup, or OnStrokeColor there. Though we will also want those
		//calls to be added to onStrokeColor and onOutline to handle the same situation

		 if (shapeFill instanceof Rectangle){
		 	Location loc = onRectangle((Rectangle)shapeFill);

		 	return loc;
		 }
		 else if (shapeFill instanceof Circle){
		 	Location loc = onCircle((Circle)shapeFill);

		 	return loc;
		 }
		 else if (shapeFill instanceof Polygon){
		 	Location loc = onPolygon((Polygon)shapeFill);

		 	return loc;
		 }
		 else {
		 	return null;
		 }
	}

	@Override
	public Location onGroup(final Group g) {

		return null;
	}

	@Override
	public Location onLocation(final Location l) {

		//Location already has the shape inside of it, so we can just return the location that is input.
		return l;
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		final int width = r.getWidth();
		final int height = r.getHeight();

		return new Location(0, 0, new Rectangle(width, height));

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
		for (int i = 0; i <s.getPoints().size(); i++){
			s.getPoints().get(i).getx(); //gets x coordinate from first set of points in list of points
			
		}
		return null;
	}
}
