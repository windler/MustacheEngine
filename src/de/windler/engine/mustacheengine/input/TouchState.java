package de.windler.engine.mustacheengine.input;

import android.graphics.Point;

/**
 * 
 * @author Nico Windler, Michael Janssen
 * 
 */
public class TouchState extends InputState {

	private Point pointStart = null;
	private Point pointEnd = null;

	public TouchState(Point start, Point end, float dspWidthRatio,
			float dspHeightRatio, int dspHeight) {

		pointStart = new Point((int) (start.x * dspWidthRatio), dspHeight
				- (int) ((float) end.y * dspHeightRatio));
		pointEnd = new Point((int) (end.x * dspWidthRatio), dspHeight
				- (int) ((float) start.y * dspHeightRatio));
	}

	public void setPointEnd(Point pointEnd) {
		this.pointEnd = pointEnd;
	}

	public Point getPointEnd() {
		return pointEnd;
	}

	public void setPointStart(Point pointStart) {
		this.pointStart = pointStart;
	}

	public Point getPointStart() {
		return pointStart;
	}

}
