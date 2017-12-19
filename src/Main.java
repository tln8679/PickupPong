import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;

	public Main() {
		initUI();
	}
	
	private void initUI() {
		add(new Table());
		
		setResizable(false);
		pack();
		
		setTitle("Pong!");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame ex = new Main();
				ex.setVisible(true);
			}
		});
	}
		
	}

