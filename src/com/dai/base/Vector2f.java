package com.dai.base;

public class Vector2f {

	private float x;
	private float y;

	public Vector2f(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float dot(Vector2f r) {
		return x * r.x + y * r.y;
	}

	public Vector2f normailize() {
		float length = length();
		x /= length;
		y /= length;
		return this;
	}

	public Vector2f rotate(float angle) {

		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.x, y + r.y);
	}

	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.x, y - r.y);
	}

	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.x, y * r.y);
	}

	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.x, y / r.y);
	}

	@Override
	public String toString() {
		return "X:" + x + ", Y:" + y;
	}
}
