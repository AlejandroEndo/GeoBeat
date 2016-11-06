import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Diamond extends MasterGeometry {

	private PApplet app;

	private PVector pos;
//	private PVector vel;
	private PVector acel;

	private float r;
	private float escala;

	private float velMax;
	private float maxForce;

	private float prof;

	public Diamond(PApplet app, PVector pos, float tam) {
		super(app, pos, tam);
		this.app = app;

		pos = new PVector(app.random(50, app.width - 50), app.random(50, app.height - 50), app.random(-1000, -2450));
		this.pos = pos;

		escala = 5;

		prof = -2500;

		velMax = 5;
		maxForce = 0.1f;

		vel = new PVector(app.random(-5, 5), app.random(-5, 5), app.random(-5, 5));
		acel = new PVector();
	}

	@Override
	public void draw() {
		if (tam > 50)
			tam -= 0.5f;
		
		r += 0.005f;
		app.pushMatrix();
		app.translate(pos.x, pos.y, pos.z);
		app.rotateX(r);
		app.rotateY(r);
		app.rotateZ(r);
		app.stroke(360);
		app.fill(360);

		app.beginShape(PConstants.TRIANGLE);
		// Parte positiva Diamante
		app.vertex(-tam, -tam, 0);
		app.vertex(tam, -tam, 0);
		app.vertex(0, 0, tam * 2);

		app.vertex(tam, -tam, 0);
		app.vertex(tam, tam, 0);
		app.vertex(0, 0, tam * 2);

		app.vertex(tam, tam, 0);
		app.vertex(-tam, tam, 0);
		app.vertex(0, 0, tam * 2);

		app.vertex(-tam, tam, 0);
		app.vertex(-tam, -tam, 0);
		app.vertex(0, 0, tam * 2);

		// Parte negativa Diamante
		app.vertex(-tam, -tam, 0);
		app.vertex(tam, -tam, 0);
		app.vertex(0, 0, -tam * 2);

		app.vertex(tam, -tam, 0);
		app.vertex(tam, tam, 0);
		app.vertex(0, 0, -tam * 2);

		app.vertex(tam, tam, 0);
		app.vertex(-tam, tam, 0);
		app.vertex(0, 0, -tam * 2);

		app.vertex(-tam, tam, 0);
		app.vertex(-tam, -tam, 0);
		app.vertex(0, 0, -tam * 2);

		app.endShape();

		app.popMatrix();

		orbita();
		mover();

	}

	private void orbita() {
		for (int i = 0; i < 8; i++) {
			app.pushMatrix();
			app.translate(pos.x, pos.y, pos.z);
			app.rotateX(72 * i + r);
			app.rotateY(72 * i - r);
			app.rotateZ(72 * i + r);
			app.pushMatrix();
			app.rotateZ(72 * i + r + tam * 0.05f);
			atomico();
			app.popMatrix();
			app.noFill();
			// app.ellipse(0, 0, tam * 5, tam * 5);
			app.popMatrix();
		}
	}

	private void atomico() {
		app.pushMatrix();
		app.fill(0);
		// x - 350, y - 350
		app.translate(tam * 2, 0, 0);
		// app.rotateZ(r + 5f + tam * 0.5f);

		app.beginShape(PConstants.TRIANGLE);

		// Parte positiva Diamante
		app.vertex(-tam / escala, -tam / escala, 0);
		app.vertex(tam / escala, -tam / escala, 0);
		app.vertex(0, 0, tam * 2 / escala);

		app.vertex(tam / escala, -tam / escala, 0);
		app.vertex(tam / escala, tam / escala, 0);
		app.vertex(0, 0, tam * 2 / escala);

		app.vertex(tam / escala, tam / escala, 0);
		app.vertex(-tam / escala, tam / escala, 0);
		app.vertex(0, 0, tam * 2 / escala);

		app.vertex(-tam / escala, tam / escala, 0);
		app.vertex(-tam / escala, -tam / escala, 0);
		app.vertex(0, 0, tam * 2 / escala);

		// Parte negativa Diamante
		app.vertex(-tam / escala, -tam / escala, 0);
		app.vertex(tam / escala, -tam / escala, 0);
		app.vertex(0, 0, -tam * 2 / escala);

		app.vertex(tam / escala, -tam / escala, 0);
		app.vertex(tam / escala, tam / escala, 0);
		app.vertex(0, 0, -tam * 2 / escala);

		app.vertex(tam / escala, tam / escala, 0);
		app.vertex(-tam / escala, tam / escala, 0);
		app.vertex(0, 0, -tam * 2 / escala);

		app.vertex(-tam / escala, tam / escala, 0);
		app.vertex(-tam / escala, -tam / escala, 0);
		app.vertex(0, 0, -tam * 2 / escala);

		app.endShape();

		app.popMatrix();
	}

	@Override
	public void mover() {
		vel.add(acel);
		vel.limit(velMax);
		pos.add(vel);
		acel.mult(0);

		if (pos.x > app.width - 100) {
			PVector desired = new PVector(velMax * -1, vel.y, vel.z);
			PVector director = PVector.sub(desired, vel);
			director.limit(maxForce);
			acel.add(director);
		}

		if (pos.x < 100) {
			PVector desired = new PVector(velMax, vel.y, vel.z);
			PVector director = PVector.sub(desired, vel);
			director.limit(maxForce);
			acel.add(director);
		}

		if (pos.y > app.height - 100) {
			PVector desired = new PVector(vel.x, velMax * -1, vel.z);
			PVector director = PVector.sub(desired, vel);
			director.limit(maxForce);
			acel.add(director);
		}

		if (pos.y < 100) {
			PVector desired = new PVector(vel.x, velMax, vel.z);
			PVector director = PVector.sub(desired, vel);
			director.limit(maxForce);
			acel.add(director);
		}

		if (pos.z < prof + 100) {
			PVector desired = new PVector(vel.x, vel.y, velMax);
			PVector director = PVector.sub(desired, vel);
			director.limit(maxForce);
			acel.add(director);
		}

		if (pos.z > -100 - 100) {
			PVector desired = new PVector(vel.x, vel.y, velMax * -1);
			PVector director = PVector.sub(desired, vel);
			director.limit(maxForce);
			acel.add(director);
		}
	}

	public void setTam(float tam) {
		this.tam = tam;
	}

	public PVector getPos() {
		return pos;
	}
	
	public void setPos(PVector pos) {
		this.pos = pos;
	}
	
	 @Override
	 public void setVel(PVector vel) {
	 this.vel=vel;
	 }
	
	 @Override
	 public PVector getVel() {
	 return vel;
	 }
}
