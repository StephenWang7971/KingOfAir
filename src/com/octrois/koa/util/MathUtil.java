package com.octrois.koa.util;

import android.graphics.RectF;

public class MathUtil {

	public static boolean inside(float x, float y, RectF rect) {

		return x >= rect.left && x <= rect.right && y >= rect.top
				&& y <= rect.bottom;
	}

	public static boolean isConflicted(RectF source, RectF dest) {

		return inside(source.left, source.top, dest)
				|| inside(source.left, source.bottom, dest)
				|| inside(source.right, source.top, dest)
				|| inside(source.right, source.bottom, dest)
				|| inside(dest.left, dest.top, source)
				|| inside(dest.left, dest.bottom, source)
				|| inside(dest.right, dest.top, source)
				|| inside(dest.right, dest.bottom, source);
	}
}
