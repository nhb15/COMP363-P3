package edu.luc.etl.cs313.android.shapes.model;

import java.util.List;

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

		groupCount = 0;

		for (int i = 0; i < g.getShapes().size(); i++){

			groupCount += g.getShapes().get(i).accept(this);

		}
		return groupCount;

		/**
		groupCount = 0;
		System.out.print(g.getShapes().size());

		/**
		 * NOTES:
		 * So in looking at this, when we go through the shapes in the list, ALL of them are locations since that's how they start out in fixtures.
		 * SO, I think we need to look at onLocation since location can house groups...I don't think onGroup even gets called as it is now for those "groups" groupMiddle and groupComplex. *
		 *b


		for (int i = 0; i < g.getShapes().size(); i++){

			Shape groupShape = g.getShapes().get(i);

			System.out.print(groupShape instanceof Location);

			/*if (groupShape instanceof Location){
				//debugging this reveals that this clause is unnecessary, as when we send the Location object
				//(line39 from fixtures) we check to see if that sent object is a location or a group
				//but we clearly send a location. We need to find a way to check what kind of shape(s)
				//are in the location within the onLocation method

				groupCount+= onLocation((Location)groupShape);
			}


			if (groupShape instanceof Location){
				Location locShape = (Location)groupShape;
				Shape shapeInLoc = locShape.getShape();
				if (shapeInLoc instanceof Group){

				}
			}

			if (groupShape instanceof Group) {
				groupCount+= onGroup((Group) groupShape);
			}

			else{
				groupCount++;
			}
		}

		return groupCount;

		//for loop to count individual children
		*/
	}

	@Override
	public Integer onRectangle(final Rectangle q) {
		return 1;
	}

	@Override
	public Integer onOutline(final Outline o) {
		return o.getShape().accept(this);
	}

	@Override
	public Integer onFill(final Fill c) {
		return c.getShape().accept(this);
	}

	@Override
	public Integer onLocation(final Location l) {

		System.out.println(l.getShape());

		return l.getShape().accept(this);
		/**

		if (l.getShape() instanceof Group){
			//we pass a onLocation to this for complex fixture
			//so we never enter this if statement, hence the return of 3 not six.

			return onGroup((Group)l.getShape());

			//group is a list of shapes, location is a shape, so I think we need a tighter
			//distinction here rather than group vs location, since the magenta polygon and other
			//shapes in the complex group come from a location with a group within that
			//maybe we need a check after we determine it is a location to see if that location
			//has a group within it? I'm unsure how to check is the shape object in the location class
			//is a group or a unique shape, which I think is what will get countComplex working

		}

		else {
			return 1;
		}
		 */
	}

	@Override
	public Integer onStrokeColor(final StrokeColor c) {
		return c.getShape().accept(this);
	}
}
