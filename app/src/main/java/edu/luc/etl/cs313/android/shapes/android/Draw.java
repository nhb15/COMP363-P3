package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas;
		this.paint = paint;
		paint.setStyle(Style.STROKE);
	}

	/**
	 * onCircle is a visitor that sets the draw action of the canvas at the top left of the canvas and paints a circle
	 * from the radius of the circle object.
	 * @param c circle object to be drawn
	 * @return null
	 */
	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	/**
	 * onStrokeColor is a visitor that saves the paint color of the canvas before setting the paint color
	 * to that set as the color in the StrokeColor decorater object, calling accept on the shape wrapped in the
	 * StrokeColor object, and then setting the canvas color back to the original color, stored in saveColor. This
	 * ensures that all the shapes wrapped in this stroke color are drawn in the specified color, while the canvas
	 * still remembers the color used before this object was drawn.
	 * @param c strokeColor object to be unwrapped and drawn
	 * @return null
	 */

	@Override
	public Void onStrokeColor(final StrokeColor c) {

		int saveColor = paint.getColor();

		paint.setColor(c.getColor());

		c.getShape().accept(this);

		paint.setColor(saveColor);

		return null;
	}

	/**
	 * onFill first saves the canvas paint style to saveStyle, then sets both the stroke color and the
	 * color fill for the canvas to the values stored within the onFill object. The shapes within the onFill object
	 * are then unwrapped and drawn via the accept method. Finally, the canvas fill and stroke style are returned
	 * to the saveStyle state.
	 * @param f onFill object to be unwrapped and drawn
	 * @return null
	 */
	@Override
	public Void onFill(final Fill f) {
		Style saveStyle = paint.getStyle();

		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		f.getShape().accept(this);
		paint.setStyle(saveStyle);
		return null;
	}

	/**
	 * onGroup draws each shape within it by redirecting method calls to their respective visitor or decorator within Draw
	 * Each type of Shape is then handled by its own method to outsource the correct functionality
	 * This in turn works for groups within groups as a form of recursion
	 * @param g is the group
	 * @return null
	 */
	@Override
	public Void onGroup(final Group g) {

		for (int i = 0; i < g.getShapes().size(); i++){
			g.getShapes().get(i).accept(this);
		}
		return null;
	}

	/**
	 * onLocation draws a "Location" which entails translating position on the canvas and drawing the shape encased inside
	 * The positional move is taken based on the state of the Location, and the shape is drawn by the respective
	 * visitor function. The position on the canvas is then returned to the original place.
	 * @param l the location
	 * @return null
	 */
	@Override
	public Void onLocation(final Location l) {

		canvas.translate(l.getX(), l.getY());

		l.getShape().accept(this);

		canvas.translate(-(l.getX()),-(l.getY()) );
		return null;
	}

	/**
	 * onRectangle is a visitor that sets the draw action of the canvas at the top left of the
	 * canvas and paints a rectangle from the height and width of the rectangle object.
	 * @param r rectangle to be drawn
	 * @return null
	 */

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	/**
	 * onOutline draws an Outlined figure on the canvas.
	 * First, this method saves the current Style set up. Then, we convert it to an outline, draw the shape
	 * by visiting the appropriate method, and then reset the Style to the original state.
	 * @param o the Outline
	 * @return null
	 */
	@Override
	public Void onOutline(Outline o) {

		Style saveStyle = paint.getStyle();

		paint.setStyle(Paint.Style.STROKE);

		o.getShape().accept(this);

		paint.setStyle(saveStyle);
		return null;
	}

	/**
	 * onPolygon is a visitor that takes a polygon shape and builds a list of lines to be drawn by
	 * taking the points from the polygon shape list and adding them to an int array that is then sent
	 * to to the canvas via the drawLines method. The list of points to be drawn has the first point connecting
	 * to the last point so as to close the polygon shape
	 * @param s polygon shape to be drawn
	 * @return null
	 */

	@Override
	public Void onPolygon(final Polygon s) {

		//an N sized polygon has N lines
		//canvas.drawLines requires 4 float values to draw one line ((x,y) of each endpoint), so the size of the pt array is 4*n

		int ptSize = s.getPoints().size();
		final float[] pts = new float[4 * ptSize];
		int j = 0;//J holds the actual pts array index
		for (int i = 0; i < (4 * ptSize); i = i + 4){

			pts[i] = s.getPoints().get(j).getx();
			pts[i+1] = s.getPoints().get(j).gety();

			//If we're on the last point, we don't want to grab the "next" one to avoid index error
			if (j != (ptSize - 1)) {
				pts[i + 2] = s.getPoints().get(j + 1).getx();
				pts[i + 3] = s.getPoints().get(j + 1).gety();
				j++;
			}
		}

		//Now we need to add the first X/Y to draw the very last line connecting the last point to the first one
		pts[pts.length - 2] = s.getPoints().get(0).getx();
		pts[pts.length - 1] = s.getPoints().get(0).gety();

		canvas.drawLines(pts, paint);

		return null;
	}
}
