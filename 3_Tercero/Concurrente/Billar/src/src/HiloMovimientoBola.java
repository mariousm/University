package src;

public class HiloMovimientoBola extends Thread {
	private Ball bola;
	private Board board;

	public HiloMovimientoBola() {
		// TODO Auto-generated constructor stub
	}

	public HiloMovimientoBola(Ball bola, Board board) {
		this.bola = bola;
		this.board = board;
	}

	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				bola.move();
				board.repaint();
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
		}
	}

	public void finalize() {
		interrupt();
	}
}
