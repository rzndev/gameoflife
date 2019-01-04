package ru.sbt.sandbox.barantsev;
/**
 * 
 * @author Alexander Barantsev
 *
 */
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import static ru.sbt.sandbox.barantsev.FieldCell.Status.Empty;
import static ru.sbt.sandbox.barantsev.FieldCell.Status.Live;

/**
 * Игровое поле "Игры жизнь"
 * @author Alexander Barantsev
 *
 */
@Component
public class GameModel {

	/**
	 * Объект синхронизации
	 */
	//private final Object sync = new Object();

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
		int result = 0;
		//synchronized (sync)
		{
			if (field == null)
				result = 0;
			else
				result = field.length;
		}
		return result;
	}

    /**
     * Количество столбцов в игровом поле
     * @return
     */
	public int getColumns()
	{
		int result = 0;
		//synchronized (sync)
		{
			if (field == null)
				result = 0;
			else
				result =  field[0].length;
		}
		return result;
	}

	/**
	 * Создание игрового поля 10x10
	 */
	public GameModel() {
    	this(10,10);
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
	protected void createGameField(int rows, int cols) {
		FieldCell[][] field = new FieldCell[rows][cols];
        Random rnd = new Random();
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++) {
                field[r][c] = new FieldCell();
                field[r][c].live = rnd.nextBoolean();
                if (field[r][c].live)
                    field[r][c].status = Live;
                else
                    field[r][c].status = Empty;
            }
        //synchronized (sync)
		{
			this.field = field;
		}
    }

	/**
	 * Сравнение поля класса field с аргументом other
	 * @param other Сравниваемое поле
	 * @return Возвращает true, если поля идентичны. false в противном случае
	 */
	private boolean isFieldsEqual(FieldCell[][] other) {
		boolean result= false;
		//synchronized (sync)
		{
			if (other == null)
				result = false;
			else if (other == field)
				result = true;
			else {
				result = true;
				loop:
				for (int r = 0; r < field.length; r++)
					for (int c = 0; c < field[0].length; c++)
						if (field[r][c].live != other[r][c].live) {
							result = false;
							break loop;
						}
			}
		}
		return result;
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
		boolean result = false;
		//synchronized (sync)
		{
			result = (field.length == other.field.length && field[0].length == other.field[0].length && isFieldsEqual(other.field));
		}
		return result;
	}

	/**
	 * Подсчет количества соседей заданной ячейки
	 * @param row Номер строки ячейки
	 * @param col Номер столбца ячейки
	 * @return Количество соседей
	 */
	private int getNeighbourCount(int row, int col) {
		int result = 0;
		//synchronized (sync) {
		{
			for (int r = -1; r <= 1; ++r)
				for (int c = -1; c <= 1; ++c) {
					if (r == 0 && c == 0) continue;
					int effectiveR = r + row;
					int effectiveC = c + col;
					if (effectiveR < 0) effectiveR = field.length + effectiveR;
					if (effectiveC < 0) effectiveC = field[0].length + effectiveC;
					if (effectiveR >= field.length) effectiveR = effectiveR - field.length;
					if (effectiveC >= field[0].length) effectiveC = effectiveC - field[0].length;
					if (field[effectiveR][effectiveC].live) ++result;
				}
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
		int rows = 0, cols = 0;
		//synchronized (sync) {
		{
			rows = field.length;
			cols = field[0].length;
		}
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
                        field[r][c].status = Live;
                    else
                        field[r][c].status = Empty;
                }
            }
		boolean result = isFieldsEqual(field);
		//synchronized (sync)
		{
			this.field = field;
		}
		return result;
	}

	/**
	 * Получить список изменений ячеек на игровом поле
	 * @return Возвращает список списка строк, в котором содержится информация по строкам и столбцам
	 */
	public List<List<String>> retrieveChanges() {
		List<List<String>> gameChanges = new ArrayList<>();
		if (null == field) {
			List<String> tmp = new ArrayList<>();
			tmp.add("cellempty");
			gameChanges.add(tmp);
			return gameChanges;
		}
		final int rowsCount = field.length;
		final int columnsCount = field[0].length;
        for(int r = 0; r < rowsCount; r++) {
			List<String> cols = new ArrayList<>();
			gameChanges.add(cols);
			for (int c = 0; c < columnsCount; c++) {
				switch (field[r][c].status) {
					case Live:
						cols.add("celllive");
						break;
					case Empty:
						cols.add("cellempty");
						break;
					case Burn:
						cols.add("cellburn");
						break;
					case Die:
						cols.add("celldie");
						break;
				}
			}
		}
        return gameChanges;
	}

	/**
	 * Получить состояние ячеек на игровом поле
	 * @return Возвращает список списка строк, в котором содержится информация по строкам и столбцам
	 */
	public List<List<String>> retrieveState() {
		List<List<String>> gameStatus = new ArrayList<>();
		if (null == field) {
			List<String> tmp = new ArrayList<>();
			tmp.add("cellempty");
			gameStatus.add(tmp);
			return gameStatus;
		}
		final int rowsCount = field.length;
		final int columnsCount = field[0].length;
		for(int r = 0; r < rowsCount; r++) {
			List<String> cols = new ArrayList<>();
			gameStatus.add(cols);
			for (int c = 0; c < columnsCount; c++) {
				if (field[r][c].live)
						cols.add("celllive");
				else
						cols.add("cellempty");
			}
		}
		return gameStatus;
	}
}
