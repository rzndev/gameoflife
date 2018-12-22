package ru.sbt.sandbox.barantsev;
/**
 * 
 * @author Alexander Barantsev
 *
 */
public class GameConsole {
	
	/**
	 * ������������� �������� ����
	 * @param rows ���������� �����
	 * @param cols ���������� ��������
	 */
	public GameConsole(int rows, int cols) {
		gameField = new GameField(rows, cols);
	}
	
	/**
	 * ��������� ��������� ���������� ���������
	 * @return true, ���� ��������� �� ����������, false, ���� ����������
	 */
	public boolean step() {
		
		GameField next = new GameField(gameField);
		boolean result = next.equals(gameField);
		gameField = next;
		return result;
	}
	
	/**
	 * ������������ ������ ��� ������
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int rows = gameField.getRows();
		int cols = gameField.getColumns();
		for(int r = 0; r < rows; r++) {
			if (r > 0) sb.append('\n');
			for(int c = 0; c < cols; c++) {
				if (gameField.isLive(r, c))
					sb.append('*');
				else
					sb.append(' ');
			}
		}
		sb.append('\n');
		return sb.toString();
	}
	
	/**
	 * ������� ������� ����
	 */
	private GameField gameField = null;
}
