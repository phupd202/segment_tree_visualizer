
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Cursor;

import javax.swing.JFrame;

public class Display {
	private String title;
	private int width, height;
	Button bSum, bMax, bMin, bXor, bqr;
	JFrame frame;
	TextField t1, tl, tr;
	Label qr;
	private Canvas canvas;

	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;

		frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		Label txt = new Label("Segment Tree Visualiser", Label.CENTER);
		txt.setForeground(Color.white);
		txt.setBackground(Color.red);
		Font myFont = new Font("Serif", Font.BOLD, 20);
		txt.setFont(myFont);
		txt.setBounds(0, 0, 1000, 50);
		frame.add(txt);

		// tạo các nút
		bMin = new Button("Range Min");
		bMax = new Button("Range Max");
		bqr = new Button("Range Querry");

		// làm cho nút có thể nhấn được
		bMin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bMax.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bqr.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		Label inp = new Label("Input Array: ", Label.CENTER);
		inp.setForeground(Color.PINK);
		inp.setFont(myFont);
		inp.setBackground(new Color(49, 49, 49));
		inp.setBounds(300, 64, 150, 40);

		t1 = new TextField("1 3 2 8 7 10 2 2", 5);
		frame.add(inp);

		t1.setBounds(450, 72, 200, 25);
		frame.add(t1);

		// tạo textfield nhập giá trị của l
		tl = new TextField("", 4); // tạo textfield
		tl.setBounds(825, 400, 55, 20); // đặt vị trí, kích thước cho buttion l
		frame.add(tl); // thêm vào khung frame
		Label inpl = new Label("l: ", Label.CENTER); // tạo label cho textfield
		inpl.setForeground(Color.ORANGE);
		inpl.setFont(myFont);
		inpl.setBackground(new Color(49, 49, 49));
		inpl.setBounds(775, 400, 50, 40); // dặt ví trị cho label
		frame.add(inpl); // thêm vào frame

		// toạ textfield để nhập giá trị của r
		tr = new TextField("", 4); // tạo textfield với độ dài bằng 4
		tr.setBounds(925, 400, 55, 20);
		frame.add(tr);
		Label inpr = new Label("r: ", Label.CENTER);
		inpr.setForeground(Color.ORANGE);
		inpr.setFont(myFont);
		inpr.setBackground(new Color(49, 49, 49));
		inpr.setBounds(875, 400, 50, 40);
		frame.add(inpr);

		qr = new Label("", Label.CENTER);
		qr.setForeground(Color.MAGENTA);
		qr.setFont(new Font("Serif", Font.BOLD, 25));
		qr.setBackground(new Color(49, 49, 49));
		qr.setBounds(780, 600, 250, 50);
		frame.add(qr);

		// set the position for the button in frame
		bMax.setBounds(900, 200, 80, 30);
		bMin.setBounds(900, 250, 80, 30);
		bqr.setBounds(860, 550, 80, 30); // buttion range query

		// add button to the frame
		frame.add(bMin);
		frame.add(bMax);
		frame.add(bqr);

		Label vis = new Label("Option :", Label.CENTER);
		vis.setForeground(Color.ORANGE);
		vis.setFont(myFont);
		vis.setBackground(new Color(49, 49, 49));
		vis.setBounds(810, 100, 100, 30);
		frame.add(vis);

		Label Querry = new Label("Begin Querry: ", Label.CENTER);
		Querry.setForeground(Color.ORANGE);
		Querry.setFont(myFont);
		Querry.setBackground(new Color(49, 49, 49));
		Querry.setBounds(810, 450, 100, 30);
		frame.add(Querry);

		// setup canvas
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);

		frame.add(canvas);
		frame.pack();
	}

	public void updateFPS(int fps) {
		frame.setTitle(title + " FPS: " + fps);
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public JFrame getFrame() {
		return frame;
	}

	public Canvas getCanvas() {
		return canvas;
	}

}