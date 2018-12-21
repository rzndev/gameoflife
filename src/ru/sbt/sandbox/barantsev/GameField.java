package ru.sbt.sandbox.barantsev;
/**
 * 
 * @author Alexander Barantsev
 *
 */
import java.util.Random;

/**
 * Игровое поле "Игры жизнь"
 * @author panasonic
 *
 */
public final class GameField {
	
	/**
	 * Создание нового игрового поля и заполнение случайными значениями
	 * @param rows Количество строк
	 * @param cols Количество столбцов
	 */
	public GameField(int rows, int cols) {
		field = new boolean[rows][cols];
		Random rnd = new Random();
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < cols; c++)
				field[r][c] = rnd.nextBoolean();
	}
	
	/**
	 * Создание нового поколения на основе другого игрового поля
	 * @param prev Игровое поле, на основании которого генерируется новое поколение
	 */
	public GameField(GameField prev) {
		int rows = prev.field.length;
		int cols = prev.field[0].length;
		field = new boolean[rows][cols];
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < cols; c++)
				field[r][c] = prev.isLive(r, c);
	}
	
	/**
	 * Получить игровое поле
	 * @return
	 */
	public boolean[][] getField() {
		return field;
	}
	
	/**
	 * Сравнение игровых полей
	 * @return true, если конфигурация идентична, false, если конфигурация отличается
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		GameField other = (GameField)obj;
		if (field.length != other.field.length) return false;
		if (field[0].length != other.field[0].length) return false;
		for(int r = 0; r < field.length; r++) 
			for(int c = 0; c < field.length; c++)
				if (field[r][c] != other.field[r][c])
					return false;
 	    return true;
	}

	/**
	 * Подсчет количества соседей заданной ячейки
	 * @param row Номер строки ячейки
	 * @param col Номер столбца ячейки
	 * @return Количество соседей
	 */
	private int getNeighbourCount(int row, int col) {
		int result = 0;
		for(int r = -1; r <= 1; ++r)
			for(int c = -1; c <= 1; ++c)
			{
				if (r == 0 && c == 0) continue;
				int effective_r = r + row;
				int effective_c = c + col;
				if (effective_r < 0) continue;
				if (effective_c < 0) continue;
				if (effective_r >= field.length) continue;
				if (effective_c >= field[0].length) continue;
				if (field[effective_r][effective_c]) ++result;
			}
		return result;
	}
	
	/**
	 * Определение наличия жизни в ячейке на следующем поколении
	 * @param row Номер строки ячейки
	 * @param col Номер столбца ячейки
	 * @return false - Жизни нет, true - Жизнь есть
	 */
	private boolean isLive(int row, int col) {
		boolean current_cell = field[row][col];
		int neighbourCount = getNeighbourCount(row, col);
		
		if (current_cell) {
			if (neighbourCount == 2 || neighbourCount == 3) 
				return true;
			else
				return false;
			
		} else {
			if (neighbourCount == 3)
				return true;
		
		}
		return false;
	}
	
	
	/**
	 * Игровое поле
	 */
	private boolean[][] field;
}
