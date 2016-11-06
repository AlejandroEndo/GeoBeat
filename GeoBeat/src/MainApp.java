import processing.core.PApplet;

public class MainApp extends PApplet {

	private Logica logica;

	public static void main(String[] args) {
		PApplet.main("MainApp");
	}

	@Override
	public void settings() {
		fullScreen(P3D);
//		size(1200, 700, P3D);
		//smooth();
	}

	@Override
	public void setup() {
		colorMode(HSB, 360, 100, 100);
		logica = new Logica(this);
		background(0);
	}

	@Override
	public void draw() {
//		background(0);
		logica.draw();
	}

}
