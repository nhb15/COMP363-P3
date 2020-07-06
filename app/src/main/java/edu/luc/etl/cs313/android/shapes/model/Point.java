package edu.luc.etl.cs313.android.shapes.model;

/**
 * A point, implemented as a location without a shape.
 */

public class Point extends Location {


	public int getx(){
		return this.x;
	}
	public int gety(){
		return this.y;
	}


	/**
	 * We can instantiate a point as a circle with radius 0. Each point will have a position on the canvas.
	 * @param x is the left-right position
	 * @param y is the up-down position
	 */
	public Point(final int x, final int y) {
		super(x, y, new Circle(0));
		assert x >= 0;
		assert y >= 0;
	}
}
