import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

public class Music {

	private PApplet app;

	private Minim minim;
	private AudioPlayer song;
	private AudioInput in;
	private FFT fft;

	private float promedio;
	private float constante;
	private float primer;
	private float comparador;

	private float min = 100;
	private float max;

	public Music(PApplet app) {
		this.app = app;

		minim = new Minim(app);

		in = minim.getLineIn();

		song = minim.loadFile("../data/freaks.wav");

		song.loop();

		fft = new FFT(song.bufferSize(), song.sampleRate());
	}

	public void vals() {
		fft.forward(song.mix);

		primer = comparador;
		for (int i = 0; i < song.bufferSize() - 1; i++) {
			constante += song.mix.get(i) * 500;
		}
		comparador = constante / song.mix.size();

		promedio = PApplet.abs(primer - comparador);

		if (promedio < min && promedio != 0) {
			min = promedio;
		}
		if (promedio > max) {
			max = promedio;
		}
		// System.out.println("min=" + min + " max=" + max);
	}

	public float getPromedio() {
		return promedio;
	}

	public float getMax() {
		return max;
	}

	public float getMin() {
		return min;
	}

}
