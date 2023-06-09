
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.RenderingHints;

public abstract class Visual implements Runnable {
	private final int SECOND_IN_NS = 1000000000;
	private int FPS;

	public String title;
	public int width, height;
	public Display display;
	
	public KeyManager keyManager;
	
	private BufferStrategy bs;
	private Graphics g;
	
	public Visual(String title, int width, int height, int FPS,Display display) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.FPS = FPS;
		this.display=display;
		keyManager = new KeyManager();
		display.getFrame().addKeyListener(keyManager);
	}
	
	@Override
	public void run() {
		
		double nsPerTick = SECOND_IN_NS / FPS;
		long lastTime = System.nanoTime(), now;
		double delta = 0;
		long timer = 0;
		int frames = 0;
		
		while (true) {
			now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			timer += now - lastTime;
			lastTime = now;
			
			mTick();
			if (delta >= 1) {
				mRender();
				frames++;
				delta--;
			}
			
			if (timer >= SECOND_IN_NS) {
				display.updateFPS(frames);
				frames = 0;
				timer = 0;
			}
		}
	}
	
    public abstract void tick();
	public abstract void render(Graphics g, Graphics2D g2);
	
	private void mRender() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			bs = display.getCanvas().getBufferStrategy();
		}
		
		g = bs.getDrawGraphics();

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.clearRect(0, 0, width, height);
		
		g.clearRect(0, 0, width, height);
		
		render(g, g2);

		bs.show();
		g.dispose();
	}

	private void mTick()
	{
		keyManager.tick();
		tick();
	}
	
}