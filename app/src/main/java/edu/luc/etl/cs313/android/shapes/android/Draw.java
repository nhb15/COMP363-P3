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

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStrokeColor(final StrokeColor c) {
		//canvas.drawColor(c.getColor());

		int saveColor = paint.getColor();

		paint.setColor(c.getColor());

		c.getShape().accept(this);

		paint.setColor(saveColor);

		return null;
	}

	@Override
	public Void onFill(final Fill f) {
		Style saveStyle = paint.getStyle();
		//should it be FILL_AND_STROKE?
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		f.getShape().accept(this);
		paint.setStyle(saveStyle);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {

		//visit each child to draw it i guess
		//get simpler ones working first

		for (int i = 0; i < g.getShapes().size(); i++){
			g.getShapes().get(i).accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) {

		canvas.translate(l.getX(), l.getY());

		l.getShape().accept(this);

		canvas.translate(-(l.getX()),-(l.getY()) );
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {

		Style saveStyle = paint.getStyle();

		paint.setStyle(Paint.Style.STROKE);

		o.getShape().accept(this);

		paint.setStyle(saveStyle);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {

		//can use onGroup

		//an N sized polygon has N lines
		//canvas.drawLines requires 4 float values to draw one line ((x,y) of each endpoint), so the size of the pt array is 4*n

		int ptSize = s.getPoints().size();
		final float[] pts = new float[4 * ptSize];
		int j = 0;//J holds the actual pts array index
		for (int i = 0; i < (4 * ptSize); i = i + 4){

			pts[i] = s.getPoints().get(j).getx();
			pts[i+1] = s.getPoints().get(j).gety();

			if (j != (ptSize - 1)) {
				pts[i + 2] = s.getPoints().get(j + 1).getx();
				pts[i + 3] = s.getPoints().get(j + 1).gety();
				j++;
			}
		}

		pts[pts.length - 2] = s.getPoints().get(0).getx();
		pts[pts.length - 1] = s.getPoints().get(0).gety();




		/**
		for (int i = 0; i < (4 * s.getPoints().size()); i = i++){
			System.out.println(pts[i] + ", " );

		}
		/**
		int j = 0;
		for (int i = 0; i < ptSize; i = i++){

			pts[j] = s.getPoints().get(i).getx();
			pts[++j] = s.getPoints().get(i).gety();

			pts[++j] = s.getPoints().get(i+1).getx();
			pts[++j] = s.getPoints().get(i+1).gety();
			j++;

		}
		System.out.println(pts);

		//pts[(4 * ptSize)-4] = s.getPoints().get((4*ptSize)-4).getx();
		*/
		canvas.drawLines(pts, paint);


		return null;
	}
}
