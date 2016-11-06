import processing.core.PApplet;
import processing.core.PVector;

public class Helix extends MasterGeometry {

	private PApplet app;

	private PVector pos;
	private PVector acel;

	private PVector vertices[];
	private PVector verticesDos[];

	private float r;
	private float escala;
	private float velMax;
	private float maxForce;
	private float prof;

	private int pts;
	private int segments;
	private float latheAngle;
	private float angle;
	private float radius;

	public Helix(PApplet app, PVector pos, float tam) {
		super(app, pos, tam);
		this.app = app;

		pos = new PVector(app.random(50, app.width - 50), app.random(50, app.height - 50), app.random(-1000, -2450));
		this.pos = pos;

		// helix values
		pts = 6;
		segments = 12;
		latheAngle = 12;
		angle = 0;
		radius = 30;

		// general values
		escala = 4;

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
		app.fill(360, 100);
		helixDraw();
		app.popMatrix();

		mover();
		orbita();
	}

	private void helixDraw() {
		vertices = new PVector[pts + 1];
		verticesDos = new PVector[pts + 1];

		for (int i = 0; i <= pts; i++) {
			vertices[i] = new PVector();
			verticesDos[i] = new PVector();
			vertices[i].x = (tam - 10) + PApplet.sin(PApplet.radians(angle)) * radius;
			vertices[i].z = PApplet.cos(PApplet.radians(angle)) * radius - segments / 2;
			angle += 360.0 / pts;
		}

		// draw toroid
		latheAngle = 0;
		for (int i = 0; i <= segments; i++) {
			app.beginShape(PApplet.QUAD_STRIP);
			for (int j = 0; j <= pts; j++) {
				if (i > 0) {
					app.vertex(verticesDos[j].x, verticesDos[j].y, verticesDos[j].z);
				}
				verticesDos[j].x = PApplet.cos(PApplet.radians(latheAngle)) * vertices[j].x;
				verticesDos[j].y = PApplet.sin(PApplet.radians(latheAngle)) * vertices[j].x;
				verticesDos[j].z = vertices[j].z;
				// optional helix offset
				// vertices[j].z+=helixOffset;
				app.vertex(verticesDos[j].x, verticesDos[j].y, verticesDos[j].z);
			}
			// create extra rotation for helix
			latheAngle += 720.0 / segments;
			app.endShape();
		}

	}

	private void helixAtom() {
		vertices = new PVector[pts + 1];
		verticesDos = new PVector[pts + 1];

		for (int i = 0; i <= pts; i++) {
			vertices[i] = new PVector();
			verticesDos[i] = new PVector();
			vertices[i].x = ((tam - 10) / escala) + PApplet.sin(PApplet.radians(angle)) * radius - 10;
			vertices[i].z = PApplet.cos(PApplet.radians(angle)) * radius - 10 - segments / 2;
			angle += 360.0 / pts;
		}

		// draw toroid
		latheAngle = 0;
		for (int i = 0; i <= segments; i++) {
			app.beginShape(PApplet.QUAD_STRIP);
			for (int j = 0; j <= pts; j++) {
				if (i > 0) {
					app.vertex(verticesDos[j].x, verticesDos[j].y, verticesDos[j].z);
				}
				verticesDos[j].x = PApplet.cos(PApplet.radians(latheAngle)) * vertices[j].x;
				verticesDos[j].y = PApplet.sin(PApplet.radians(latheAngle)) * vertices[j].x;
				verticesDos[j].z = vertices[j].z;
				// optional helix offset
				// vertices[j].z+=helixOffset;
				app.vertex(verticesDos[j].x, verticesDos[j].y, verticesDos[j].z);
			}
			// create extra rotation for helix
			latheAngle += 720.0 / segments;
			app.endShape();
		}

	}

	private void orbita() {
		for (int i = 0; i < 8; i++) {
			app.pushMatrix();
			app.stroke(360);
			app.translate(pos.x, pos.y, pos.z);
			app.rotateX(72 * i + r);
			app.rotateY(72 * i - r);
			app.rotateZ(72 * i + r);
			app.pushMatrix();
			app.rotateZ(72 * i + r + tam * 0.05f);
			atomico();
			app.popMatrix();
			app.noFill();
			// app.ellipse(0, 0, 500, 500);
			app.popMatrix();
		}
	}

	private void atomico() {
		app.pushMatrix();
		app.fill(0);
		// x - 350, y - 350
		app.translate(tam * 2, 0, 0);
		// app.rotateZ(r + 5f + tam * 0.5f);
		helixAtom();
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

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public PVector getPos() {
		return pos;
	}

	@Override
	public void setVel(PVector vel) {
		this.vel = vel;
	}

	@Override
	public PVector getVel() {
		return vel;
	}
}