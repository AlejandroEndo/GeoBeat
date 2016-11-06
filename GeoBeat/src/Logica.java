import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Logica {

	private PApplet app;

	private ArrayList<MasterGeometry> figuras = new ArrayList<>();

	private PVector init;

	private float light;

	private boolean cambio;

	// private Piramide piramide;
	// private Sphere esfera;
	// private Cubo cubo;
	// private Diamond diamante;

	private Escenario escena;

	private Music musica;

	public Logica(PApplet app) {
		this.app = app;

		cambio = true;

		init = new PVector();

		escena = new Escenario(app, -2500);
		musica = new Music(app);

		figuras.add(new Piramide(app, init, 100));
		figuras.add(new Sphere(app, init, 100));
		figuras.add(new Cubo(app, init, 100));
		figuras.add(new Diamond(app, init, 100));
		figuras.add(new Helix(app, init, 100));
	}

	public void draw() {

		if (app.frameCount % 500 == 0) {
			cambio = true;
		}

		if (light < 300)
			light += PApplet.map(musica.getPromedio(), 0, 93, 0, 3);
		if (light > 0)
			light -= 1f;
		// fondo y musica
		app.directionalLight(light, 0, 0, 0, 0, -1);
		escena.draw();
		app.directionalLight(light, 100, 50, 0, 0, -1);
		musica.vals();

		// pintar figuras
		for (int i = 0; i < figuras.size(); i++) {
			figuras.get(i).draw();
		}

		// Lineas que conectan
		for (int j = 0; j < figuras.size(); j++) {
			MasterGeometry a = figuras.get(j);
			for (int i = 0; i < figuras.size(); i++) {
				MasterGeometry b = null;
				if (i < figuras.size() - 1) {
					/**
					 * Area sensible tridimensional prueba
					 */
					// app.fill(360, 20);
					// app.pushMatrix();
					// app.translate(a.getPos().x, a.getPos().y, a.getPos().z);
					// app.sphere(100);
					// app.popMatrix();

					b = figuras.get(i + 1);
					app.line(a.getPos().x, a.getPos().y, a.getPos().z, b.getPos().x, b.getPos().y, b.getPos().z);

					// Evitar colision entre figuras
					if (PApplet.dist(a.getPos().x, a.getPos().y, a.getPos().z, b.getPos().x, b.getPos().y,
							b.getPos().z) < 200 && a != b) {
						if (cambio) {
							a.setVel(a.getVel().mult(-1));
							b.setVel(a.getVel().mult(-1));
							cambio = false;
						}
					}
				}

			}
		}

		// ubicador
		app.noFill();
		app.stroke(PApplet.map(musica.getPromedio(), 0, 93, 0, 360), 100, 100, 20);
		for (int i = 0; i < figuras.size(); i++) {
			MasterGeometry a = figuras.get(i);
			app.beginShape();
			app.vertex(0, 0, a.getPos().z);
			app.vertex(app.width, 0, a.getPos().z);
			app.vertex(app.width, app.height, a.getPos().z);
			app.vertex(0, app.height, a.getPos().z);
			app.endShape(PConstants.CLOSE);
		}

		// sets

		for (int i = 0; i < figuras.size(); i++) {
			MasterGeometry a = figuras.get(i);
			if (a.getTam() < 80)
				a.setTam(a.getTam() + PApplet.map(musica.getPromedio(), 0, 93, 0, 10));
		}
		// if (piramide.getTam() < 80)
		// piramide.setTam(piramide.getTam() + PApplet.map(musica.getPromedio(),
		// 0, 93, 0, 10));
		// esfera.setTam(PApplet.map(musica.getPromedio(), 0, 93, 50, 80));
		// cubo.setTam(PApplet.map(musica.getPromedio(), 0, 93, 50, 80));
		// diamante.setTam(PApplet.map(musica.getPromedio(), 0, 93, 50, 80));
		// max -3700 , min -100

		// esfera.setPos(new PVector(app.mouseX, app.mouseY, -100));
		// app.line(piramide.getX(), piramide.getY(), piramide.getZ(),
		// esfera.getPos().x, esfera.getPos().y,
		// esfera.getPos().z);
	}

}
