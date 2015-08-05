package juliosanchez.twodgame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 3;
	public static final String NAME = "Game";
	
	private JFrame frame;
	
	public boolean running = false;
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer().getData());
	
	public Game(){
		//Set the size of your canva to one size so you cant change it
		//setting up dimensions for game screen
		setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		frame = new JFrame(NAME);
		
		//deafult close operation, what happens when the JFrame is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		//add extended canvas to the JFrame
		frame.add(this,	BorderLayout.CENTER);
		frame.pack(); //size's everything correctly
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	//useful so when can call this function from the appl
	private synchronized void start() {
		// TODO Auto-generated method stub
		//thread is an instance of runnable, when it starts it will run the run function
		//this is where we wil have our main loop, so this thread dont take away
		//from the first thread , multi-staking
		running = true;
		new Thread(this).start();
	}
	
	private synchronized void stop() {
		// TODO Auto-generated method stub
		running = false;
	}
	
	@Override
	public void run() {
		//will limit the update/sec so its the same in all systems OS
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		int frames =0;
		
		long lastTimer = System.currentTimeMillis();
		double delta =0;
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime)/nsPerTick;
			lastTime = now; 
			boolean shouldRender = true;
			
			while(delta >= 1){
				//this basically delay's the frames
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(shouldRender){
			frames++;
			render();
			}
			
			if(System.currentTimeMillis()-lastTimer >= 1000){
				lastTimer += 1000;
				System.out.println(frames + " "+ ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick(){
		//will update the game variables and logic
		tickCount++;
	}
	
	public void render(){
		//will print out what the logic in tick will print out
	}
	
	public static void main(String[] args){
		
		new Game().start();
		
	}
	
	
}
