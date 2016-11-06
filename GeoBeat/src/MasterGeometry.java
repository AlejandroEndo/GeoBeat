import processing.core.PApplet;
import processing.core.PVector;

public abstract class MasterGeometry {

	protected PApplet app;

	protected PVector pos;
	protected PVector vel;

	protected float tam;

	public MasterGeometry(PApplet app, PVector pos, float tam) {
		this.app = app;
		this.pos = pos;
		this.tam = tam;
		
	}

	public abstract void draw();

	public abstract void mover();

	public PVector getPos() {
		return pos;
	}

	public float getTam() {
		return tam;
	}

	public void setTam(float tam) {
		this.tam = tam;
	}

	public void setVel(PVector vel) {
		this.vel = vel;
	}
	
	public PVector getVel() {
		return vel;
	}

}
