package ru.sbt.sandbox.barantsev;
/**
 * 
 * @author Alexander Barantsev
 *
 */
public class GameConsole {
	
	/**
	 * Инициализация игрового поля
	 * @param rows Количество строк
	 * @param cols Количество столбцов
	 */
	public GameConsole(int rows, int cols) {
		gameField = new GameField(rows, cols);
	}
	
	/**
	 * Выполнить генерацию следующего поколения
	 * @return true, если поколение не изменилось, false, если изменилось
	 */
	public boolean step() {
		
		GameField next = new GameField(gameField);
		boolean result = next.equals(gameField);
		gameField = next;
		return result;
	}
	
	/**
	 * Формирование строки для вывода
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
	 * Текущее игровое поле
	 */
	private GameField gameField = null;
}
