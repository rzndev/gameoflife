package ru.sbt.sandbox.barantsev;
/**
 * 
 * @author Alexander Barantsev
 *
 */
import java.util.Arrays;
import java.util.Random;

/**
 * Игровое поле "Игры жизнь"
 * @author Alexander Barantsev
 *
 */
public final class GameModel {
	
	
	/**
	 * Игровое поле
	 */
	private boolean[][] field;
	
	public int getRows()
	{
		if (field == null) return 0;
		return field.length;
	}
	
	public int getColumns()
	{
		if (field == null) return 0;
		return field[0].length;
	}
	
	/**
	 * Создание нового игрового поля и заполнение случайными значениями
	 * @param rows Количество строк
	 * @param cols Количество столбцов
	 */
	public GameModel(int rows, int cols) {
	    createGameField(rows, cols);
	}

    /**
     * Создание нового игрового поля и заполнение случайными значениями
     * @param rows Количество строк
     * @param cols Количество столбцов
     */
	private void createGameField(int rows, int cols) {
        field = new boolean[rows][cols];
        Random rnd = new Random();
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++)
                field[r][c] = rnd.nextBoolean();
    }

	/**
	 * Сравнение поля класса field с полем other
	 * @param other Сравниваемое поле
	 * @return Возвращает true, если поля идентичны. false в противном случае
	 */
	private boolean isFieldsEqual(boolean[][] other) {
		if (other == null) return false;
		if (other == field) return true;
		for(int r = 0; r < field.length; r++)
			if (!Arrays.equals(field[r], other[r]))
				return false;
		return true;
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
		GameModel other = (GameModel)obj;
		if (field.length != other.field.length || field[0].length != other.field[0].length) return false;
		return isFieldsEqual(other.field);
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
				int effectiveR = r + row;
				int effectiveC = c + col;
				if (effectiveR < 0) effectiveR = field.length + effectiveR;
				if (effectiveC < 0) effectiveC = field[0].length + effectiveC;
				if (effectiveR >= field.length) effectiveR = effectiveR - field.length;
				if (effectiveC >= field[0].length) effectiveC = effectiveC - field[0].length;
				if (field[effectiveR][effectiveC]) ++result;
			}
		return result;
	}
	
	/**
	 * Определение наличия жизни в ячейке на следующем поколении
	 * @param row Номер строки ячейки
	 * @param col Номер столбца ячейки
	 * @return false - Жизни нет, true - Жизнь есть
	 */
	public boolean isLive(int row, int col) {
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

	public boolean isAllDead() {
		for(int r = 0; r < field.length; r++)
			for(int c = 0; c < field[0].length; c++)
				if (field[r][c]) return false;
		return true;
	}

	/**
	 * Создание нового поколения
	 * @return Возвращает true, если поколение не изменилось. false - в поколении произошли изменения
	 */
	public boolean step() {
		int rows = field.length;
		int cols = field[0].length;
		boolean[][] field = new boolean[rows][cols];
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < cols; c++)
				field[r][c] = isLive(r, c);
		boolean result = isFieldsEqual(field);
		this.field = field;
		return result;
	}
}
