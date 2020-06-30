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
		if(c.getShape() instanceof Rectangle){
			Rectangle outlineRectangle = (Rectangle)c.getShape();
			final int width = outlineRectangle.getWidth();
			final int height = outlineRectangle.getHeight();

			return new Location(0, 0, new Rectangle(width, height));
		}

		if(c.getShape() instanceof Circle){
			Circle outlineCircle = (Circle)c.getShape();
			final int radius = outlineCircle.getRadius();
			return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
		}

		if (c.getShape() instanceof Polygon) {
			//complete for onPolygon, then use here as well
		}

		return null;
	}

	@Override
	public Location onOutline(final Outline o) {

		//grabbed shape from outline object, ran test for rect, cast object to rect, then return
		//same line of code we have for onRectangle, maybe we just call onRectangle tho

		if(o.getShape() instanceof Rectangle){
			Rectangle outlineRectangle = (Rectangle)o.getShape();
			final int width = outlineRectangle.getWidth();
			final int height = outlineRectangle.getHeight();

			return new Location(0, 0, new Rectangle(width, height));
		}

		if(o.getShape() instanceof Circle){
			Circle outlineCircle = (Circle)o.getShape();
			final int radius = outlineCircle.getRadius();
			return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
		}

		if (o.getShape() instanceof Polygon) {
			//complete for onPolygon, then use here as well
		}

		return null;
	}

	@Override
	public Location onPolygon(final Polygon s) {
		return null;
	}
}
