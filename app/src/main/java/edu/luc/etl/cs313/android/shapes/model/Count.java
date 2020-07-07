package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

	// TODO entirely your job
	int groupCount = 0;

	/**
	 * onPolygon is a visitor that returns a value of 1 when sent a polygon shape,
	 * since a polygon contains exactly shape.
	 * @param p is the polgon sent
	 * @return 1
	 */
	@Override
	public Integer onPolygon(final Polygon p) {
		return 1;
	}

	/**
	 * onCircole is a visitor that returns a value of 1 when sent a circle shape, since a cirle
	 * contains exatly one shape.
	 * @param c is the circle sent
	 * @return 1
	 */
	@Override
	public Integer onCircle(final Circle c) {
		return 1;
	}

	/**
	 * onGroup is a visitor that takes a group object and returns the number of
	 * unique shapes within the group. groupCount is initialized at 0, then incremented by the
	 * return value of each of the individual shapes in the list of shapes within group g. The for loop
	 * visits each shape in the list of group g, and returns their distinct counts to be added. If the shape
	 * in the group g is itself a group, it recursively calls itself until an integer is returned
	 * @param g is the group that has its shape count returned
	 * @return integer number of shapes within g, groupCount
	 */
	@Override
	public Integer onGroup(final Group g) {

		groupCount = 0;

		for (int i = 0; i < g.getShapes().size(); i++){

			groupCount += g.getShapes().get(i).accept(this);

		}
		return groupCount;
	}

	/**
	 * onRectangle is a visitor that returns a value of 1 when sent a rectangle shape, since a rectangle
	 * contains exatly one shape.
	 * @param q  rectangle shape
	 * @return 1
	 */
	@Override
	public Integer onRectangle(final Rectangle q) {
		return 1;
	}

	/**
	 * onOutline is a visitor that first unwraps the decorated shape to determine what shape or group
	 * is to be counted, then it calls accept on that shape or group, returning the value these methods return
	 * @param o outline to be unwrapped and counted
	 * @return integer return value of the shape or groups within the outline object
	 */

	@Override
	public Integer onOutline(final Outline o) {
		return o.getShape().accept(this);
	}

	/**
	 * Like onOutline, onFill is a visitor that first unwraps the decorated shape to determine what shape or group
	 * is to be counted, then it calls accept on that shape or group, returning the value these methods return
	 * @param c filled shape to be unwrapped and counted
	 * @return integer return value of the shape or groups within the fill object
	 */

	@Override
	public Integer onFill(final Fill c) {
		return c.getShape().accept(this);
	}

	/**
	 *Like both onFill and onOutline, onLocation is a visitor that first unwraps the decorated shape to determine what shape or group
	 *is to be counted, then it calls accept on that shape or group, returning the value these methods return
	 * @param l location to be unwrapped and counted
	 * @return integer value of the shape or groups within the fill object
	 */

	@Override
	public Integer onLocation(final Location l) {

		return l.getShape().accept(this);

	}

	/**
	 * onStrokeColor, like onFill, onOutline, and onLocation, is a visitor that first unwraps the
	 * decorated shape to determine what shape or group is to be counted, then it calls accept on
	 * that shape or group, returning the value these methods return
	 * @param c strokecolor shape to be unwrapped and counted
	 * @return integer value of the shape or groups within the fill object
	 */
	@Override
	public Integer onStrokeColor(final StrokeColor c) {
		return c.getShape().accept(this);
	}
}
