package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

	// TODO entirely your job
	int groupCount = 0;
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

		System.out.print(g.getShapes().size());

		/**
		 * NOTES:
		 * So in looking at this, when we go through the shapes in the list, ALL of them are locations since that's how they start out in fixtures.
		 * SO, I think we need to look at onLocation since location can house groups...I don't think onGroup even gets called as it is now for those "groups" groupMiddle and groupComplex. *
		 *b
		 */

		for (int i = 0; i < g.getShapes().size(); i++){

			Shape groupShape = g.getShapes().get(i);

			System.out.print(groupShape instanceof Location);


			if (groupShape instanceof Group){
				//groupShape.getShapes();
				//I think we need some type of recursion here since theoretically we could have infinite groups within groups
				System.out.print("test");
				groupCount += this.onGroup((Group)groupShape);
			}
			else{
				groupCount++;
			}
		}

		return groupCount;

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

		System.out.println(l.getShape());
		return 1;
	}

	@Override
	public Integer onStrokeColor(final StrokeColor c) {
		return 1;
	}
}
