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
		this.canvas = canvas; // FIXME attempt
		this.paint = paint; // FIXME attempt
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStrokeColor(final StrokeColor c) {
		//canvas.drawColor(c.getColor());
		paint.setColor(c.getColor());

		//FIXME: IS DRAW COLOR THE RIGHT CALL HERE? WHAT ARE WE DOING WITH C.GETSHAPE? WHAT ABOUT this.PAINT??
		//CORRECTION: I THINK SETCOLOR ON PAINT IS MORE LIKELY
		//Maybe we need to use c.getShape and then use a visitor to change the color?
		return null;
	}

	@Override
	public Void onFill(final Fill f) {

		//should it be FILL_AND_STROKE?
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {

		//visit each child to draw it i guess
		//get simpler ones working first
		return null;
	}

	@Override
	public Void onLocation(final Location l) {

		canvas.translate(l.getX(), l.getY());
		//FIXME: Not sure if translate is correct since it's the SHAPE's location not the cursor
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {

		paint.setStyle(Paint.Style.STROKE);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {

		//can use onGroup

		//an N sized polygon has N lines
		//canvas.drawLines requires 4 float values to draw one line ((x,y) of each endpoint), so the size of the pt array is 4*n

		final float[] pts = null;

		canvas.drawLines(pts, paint);
		return null;
	}
}
