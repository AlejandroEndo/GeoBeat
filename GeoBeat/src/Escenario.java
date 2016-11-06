import processing.core.PApplet;
import processing.core.PConstants;

public class Escenario {

	private PApplet app;

	private float prof;

	public Escenario(PApplet app, float prof) {
		this.app = app;
		this.prof = prof;
	}

	public void draw() {
		app.fill(360, 50);
		app.stroke(360);
		app.beginShape(PConstants.QUADS);
		// fondo
		app.vertex(0, 0, prof);
		app.vertex(app.width, 0, prof);
		app.vertex(app.width, app.height, prof);
		app.vertex(0, app.height, prof);

		// Pared Izquierda
		app.vertex(0, 0, 0);
		app.vertex(0, 0, prof);
		app.vertex(0, app.height, prof);
		app.vertex(0, app.height, 0);

		// Pared Derecha
		app.vertex(app.width, 0, 0);
		app.vertex(app.width, 0, prof);
		app.vertex(app.width, app.height, prof);
		app.vertex(app.width, app.height, 0);

		// Techo
		app.vertex(0, 0, 0);
		app.vertex(0, 0, prof);
		app.vertex(app.width, 0, prof);
		app.vertex(app.width, 0, 0);

		// Suelo
		app.vertex(0, app.height, 0);
		app.vertex(0, app.height, prof);
		app.vertex(app.width, app.height, prof);
		app.vertex(app.width, app.height, 0);

		app.endShape();
	}
}
