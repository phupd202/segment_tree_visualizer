
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class SegTreeViz extends Visual {

	int a[];
	int n = 6;
	int mxe = 0;
	int calBuild = 0;
	static int r = 0;
	int gridSize = 80, gridLen = 0;
	public static Display display;

	Node nodes[];
	int animSpeed = 500;

	// Segment tree được khởi tạo với 4 tham số, titile, width, height, FPS
	public SegTreeViz(String title, int width, int height, int FPS) {
		super(title, width, height, FPS, display = new Display(title, width, height));
		r = 0;
		this.gridLen = width / gridSize;

		this.nodes = new Node[4 * n]; // số nút tối đa trong cây là 4 * n
		a = new int[] { 1, 3, -2, 8, -7, 10, 2, 22 }; // mảng khởi tạo ban đầu
		new Thread() {
			public void run() {
				try {
					Thread.sleep(animSpeed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				display.bMin.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						getArray();
						display.qr.setText("");
						display.bqr.setBackground(Color.white);
						display.bMax.setBackground(Color.white);
						display.bMin.setBackground(Color.cyan);
						calBuild = 3;
						build(a, 1, 0, n - 1, 1, 1, 3);

					}
				});

				display.bMax.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						getArray();
						display.qr.setText("");
						display.bqr.setBackground(Color.white);
						display.bMin.setBackground(Color.white);
						display.bMax.setBackground(Color.yellow);
						calBuild = 2;
						build(a, 1, 0, n - 1, 1, 1, 2);

					}
				});

				display.bqr.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						display.qr.setText("");
						display.bqr.setBackground(Color.pink);
						getQuery();
					}
				});

			}
		}.start();
	}

	void getArray() {
		String lines = display.t1.getText();
		String[] strs = lines.trim().split(" ");
		// System.out.println(strs.length);

		if (strs.length > 0) {
			n = strs.length;
			a = new int[n];
			nodes = new Node[4 * n];
			for (int i = 0; i < strs.length; i++) {
				a[i] = Integer.parseInt(strs[i]);
			}
		}
	}

	void getQuery() {
		try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 4 * n; i++) {
			if (this.nodes[i] != null)
				nodes[i].ons = 0;
		}

		String linesl = display.tl.getText();
		String linesr = display.tr.getText();
		int l = Integer.parseInt(linesl);
		int r = Integer.parseInt(linesr);
		// System.out.println(l);
		// System.out.println(r);
		int ans = Querry(1, 0, n - 1, l, r, calBuild);
		String s = Integer.toString(ans);
		display.qr.setText("Result = " + s);
	}

	void build(int a[], int v, int l, int r, int x, int y, int id) {
		try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		nodes[v] = new Node(0, l + x, r + x + 1, y);
		if (l == r) {
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nodes[v].val = a[l];
			nodes[v].set = true;
		} else {
			int tm = (l + r) / 2;
			build(a, v * 2, l, tm, x, y + 2, id);
			build(a, v * 2 + 1, tm + 1, r, x, y + 2, id);
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (id == 0) { // min trong 2 nút con
				nodes[v].val = min(nodes[v * 2].val, nodes[v * 2 + 1].val);
			} else { // tìm max trong 2 nút con
				nodes[v].val = max(nodes[v * 2].val, nodes[v * 2 + 1].val);
			}

			nodes[v].c1 = nodes[2 * v].mid;
			nodes[v].c2 = nodes[2 * v + 1].mid;
			nodes[v].set = true;
		}
	}

	// thực hiện truy vấn
	int Querry(int v, int l, int r, int qs, int qe, int id) {
		nodes[v].ons = 1;

		// nếu nằm ngoài khoảng truy vấn trả vê Integer.MAX_VALUE hoặc Integer.MIN_VALUE
		if (r < qs || l > qe) {
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			nodes[v].ons = 0;

			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(id == 0) {
				return Integer.MIN_VALUE;
			} else {
				return Integer.MAX_VALUE;
			}
		}

		// nếu nằm trong khoảng truy vấn
		if (l >= qs && r <= qe) {
			return nodes[v].val;
		}

		// đệ quy cho 2 nút con
		int tm = (l + r) / 2;
		int cans = 0;
		if (id == 0) { // nếu id = 0, tìm min
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cans = min(Querry(2 * v, l, tm, qs, qe, id), Querry(2 * v + 1, tm + 1, r, qs, qe, id));
		} else { // nếu id = 1, tìm max
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cans = max(Querry(2 * v, l, tm, qs, qe, id), Querry(2 * v + 1, tm + 1, r, qs, qe, id));
		} 

		nodes[v].ons = 0;
		try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return cans;
	}

	@Override
	public void tick() {

	}

	// vẽ các đối tượng lên màn hình 
	@Override
	public void render(Graphics g, Graphics2D g2) {
		g.setColor(new Color(49, 49, 49));
		g.fillRect(0, 0, width, height);

		// duyệt mảng, nếu nút khác null thì vẽ ra màn hình
		for (int i = 0; i < 4 * n; i++) {
			if (nodes[i] != null)
				nodes[i].render(g2, nodes[i].ons); // nếu t[i].ons = 1, nút sé được highlight màu tím
		}
	}

	int max(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	int min(int a, int b) {
		if (a < b) {
			return a;
		} else {
			return b;
		}
	}

	class Node {
		int val;
		int l, r, y; // [l, r] và toạ độ vị trí y
		float mid;
		float c1, c2;
		boolean set = false;
		Font f;
		int k = 0;
		int ons = 0; // ons = 1 thì sẽ highlight nút đó lên màn hình

		Node(int val, int l, int r, int y) {
			this.val = val;
			this.l = l;
			this.r = r;
			this.y = y;
			mid = ((float) l + r) / 2f;
			c1 = c2 = mid;
			f = new Font("Serif", Font.PLAIN, 34);
		}

		// Render nút lên màn hình
		void render(Graphics2D g, int cons) {
			g.setColor(new Color(81, 172, 51));
			g.setStroke(new BasicStroke(4));

			g.drawOval(l * gridSize - 5, y * gridSize + 50, (r - l) * gridSize - 20, gridSize);

			if (cons == 1) {
				g.setColor(Color.magenta);
				g.setStroke(new BasicStroke(4));

				g.drawOval(l * gridSize - 5, y * gridSize + 50, (r - l) * gridSize - 20, gridSize);
				g.setColor(new Color(81, 172, 51));

			}
			// g.drawRect(10 , 10, 50, 60);

			if (set) {

				String toDraw = Integer.toString(val);
				FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
				int w = (int) f.getStringBounds(toDraw, frc).getWidth();
				int h = (int) f.getStringBounds(toDraw, frc).getHeight();
				g.setFont(f);
				g.drawString(toDraw, (int) (mid * gridSize) - w - 5, y * gridSize + gridSize / 2 + h + 10);
			}

			if (c1 != mid) {
				g.drawLine((int) (mid * gridSize) - 10, (y + 1) * gridSize + 50, (int) (c1 * gridSize) - 20,
						(y + 2) * gridSize + 47);
			}
			if (c2 != mid) {
				g.drawLine((int) (mid * gridSize) - 10, (y + 1) * gridSize + 50, (int) (c2 * gridSize) - 20,
						(y + 2) * gridSize + 47);
			}

		}

	}
}