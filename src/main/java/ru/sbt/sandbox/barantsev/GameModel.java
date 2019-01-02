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
	private FieldCell[][] field;


	int numberOfReads = 0;

    /**
     *  Количество обращений к модели игры. Используется для отображения при
     *  каждом нечетном обращении состояния изменения поля
     */
	public int getNumberOfReads() {
	    return numberOfReads++;
    }

    /**
     * Количество строк в игровом поле
     * @return
     */
	public int getRows()
	{
		if (field == null) return 0;
		return field.length;
	}

    /**
     * Количество столбцов в игровом поле
     * @return
     */
	public int getColumns()
	{
		if (field == null) return 0;
		return field[0].length;
	}

    /**
     * Получить состояние ячейки: есть жизнь / нет жизни
     * @param row Строка
     * @param col Столбец
     * @return true - в ячейке есть жизнь, false - в ячейке нет жизни
     */
	public boolean getCell(int row, int col) {
        if (null == field) return false;
        if (row >= field.length || col >= field[0].length || row < 0 || col < 0) return false;
        return field[row][col].live;
    }

    /**
     * Получить состояние ячейки: есть жизнь / нет жизни
     * @param row Строка
     * @param col Столбец
     * @return состояние изменений в ячейке
     */
    public FieldCell.Status getCellStatus(int row, int col) {
        if (null == field) return FieldCell.Status.Empty;
        if (row >= field.length || col >= field[0].length || row < 0 || col < 0) return FieldCell.Status.Empty;
        return field[row][col].status;
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
        field = new FieldCell[rows][cols];
        Random rnd = new Random();
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++) {
                field[r][c] = new FieldCell();
                field[r][c].live = rnd.nextBoolean();
                if (field[r][c].live)
                    field[r][c].status = FieldCell.Status.Live;
                else
                    field[r][c].status = FieldCell.Status.Empty;
            }
    }

	/**
	 * Сравнение поля класса field с полем other
	 * @param other Сравниваемое поле
	 * @return Возвращает true, если поля идентичны. false в противном случае
	 */
	private boolean isFieldsEqual(FieldCell[][] other) {
		if (other == null) return false;
		if (other == field) return true;
		for(int r = 0; r < field.length; r++)
			for (int c = 0; c < field[0].length; c++)
			    if (field[r][c].live  != other[r][c].live)	return false;
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
				if (field[effectiveR][effectiveC].live) ++result;
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
		boolean current_cell = field[row][col].live;
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
	 * Создание нового поколения
	 * @return Возвращает true, если поколение не изменилось. false - в поколении произошли изменения
	 */
	public boolean step() {
		int rows = field.length;
		int cols = field[0].length;
		FieldCell[][] field = new FieldCell[rows][cols];
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < cols; c++) {
			    field[r][c] = new FieldCell();
                field[r][c].live = isLive(r, c);
                if (field[r][c].live != this.field[r][c].live) {
                    if (field[r][c].live)
                        field[r][c].status = FieldCell.Status.Burn;
                    else
                        field[r][c].status = FieldCell.Status.Die;
                } else //(field[r][c].live != this.field[r][c].live)
                {
                    if (field[r][c].live)
                        field[r][c].status = FieldCell.Status.Live;
                    else
                        field[r][c].status = FieldCell.Status.Empty;
                }
            }
		boolean result = isFieldsEqual(field);
		this.field = field;
		return result;
	}
}
