package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

	// TODO entirely your job
	//would this just use group and check types?? confused how this could even work.

	@Override
	public Integer onPolygon(final Polygon p) {
		return 1;
	}

	@Override
	public Integer onCircle(final Circle c) {
		return 1;
	}

	@Override
	public Integer onGroup(final Group g) {
		return -1;

		//for loop to count individual children
	}

	@Override
	public Integer onRectangle(final Rectangle q) {
		return 1;
	}

	@Override
	public Integer onOutline(final Outline o) {
		return 1;
	}

	@Override
	public Integer onFill(final Fill c) {
		return 1;
	}

	@Override
	public Integer onLocation(final Location l) {
		return 1;
	}

	@Override
	public Integer onStrokeColor(final StrokeColor c) {
		return 1;
	}
}
