package ru.sbt.sandbox.barantsev;
/**
 * 
 * @author Alexander Barantsev
 *
 */
import java.util.Random;

/**
 * ������� ���� "���� �����"
 * @author panasonic
 *
 */
public final class GameField {
	
	
	/**
	 * ������� ����
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
	 * �������� ������ �������� ���� � ���������� ���������� ����������
	 * @param rows ���������� �����
	 * @param cols ���������� ��������
	 */
	public GameField(int rows, int cols) {
		field = new boolean[rows][cols];
		Random rnd = new Random();
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < cols; c++)
				field[r][c] = rnd.nextBoolean();
	}
	
	/**
	 * �������� ������ ��������� �� ������ ������� �������� ����
	 * @param prev ������� ����, �� ��������� �������� ������������ ����� ���������
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
	 * ��������� ������� �����
	 * @return true, ���� ������������ ���������, false, ���� ������������ ����������
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
	 * ������� ���������� ������� �������� ������
	 * @param row ����� ������ ������
	 * @param col ����� ������� ������
	 * @return ���������� �������
	 */
	private int getNeighbourCount(int row, int col) {
		int result = 0;
		for(int r = -1; r <= 1; ++r)
			for(int c = -1; c <= 1; ++c)
			{
				if (r == 0 && c == 0) continue;
				int effectiveR = r + row;
				int effectiveC = c + col;
				if (effectiveR < 0) effectiveR = field.length - effectiveR;
				if (effectiveC < 0) effectiveC = field[0].length - effectiveC;
				if (effectiveR >= field.length) effectiveR = effectiveR - field.length;
				if (effectiveC >= field[0].length) effectiveC = effectiveC - field[0].length;
				if (field[effectiveR][effectiveC]) ++result;
			}
		return result;
	}
	
	/**
	 * ����������� ������� ����� � ������ �� ��������� ���������
	 * @param row ����� ������ ������
	 * @param col ����� ������� ������
	 * @return false - ����� ���, true - ����� ����
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
	

}
