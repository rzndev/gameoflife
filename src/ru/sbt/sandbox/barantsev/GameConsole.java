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
		boolean[][] cur_field = gameField.getField();
		int rows = cur_field.length;
		int cols = cur_field[0].length;
		for(int r = 0; r < rows; r++) {
			if (r > 0) sb.append('\n');
			for(int c = 0; c < cols; c++) {
				if (cur_field[r][c])
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
