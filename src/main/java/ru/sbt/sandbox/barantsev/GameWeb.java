package ru.sbt.sandbox.barantsev;
/**
 *
 * @author Alexander Barantsev
 *
 */
public class GameWeb {
    /**
     * Текущее игровое поле
     */
    private GameField gameField = null;

    /**
     * Инициализация игрового поля
     * @param rows Количество строк
     * @param cols Количество столбцов
     */
    public GameWeb(int rows, int cols) {
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
        sb.append("<!DOCTYPE HTML>");
        sb.append("<meta http-equiv=\"refresh\" content=\"1\">");
        sb.append("<HTML>");
        sb.append("<TITLE>Game of life</TITLE>");
        sb.append("<BODY>");
        sb.append("<TABLE>");
        int rows = gameField.getRows();
        int cols = gameField.getColumns();
        int r,c;
        sb.append("<TR>");
        for(c = 0; c < cols + 2; c++) sb.append("<TD>=</TD>");
        sb.append("</TR>");
        for(r = 0; r < rows; r++) {
              sb.append("<TR>");
            sb.append("<TD>!</TD>");
            if (r > 0) sb.append('\n');
            for(c = 0; c < cols; c++) {
                sb.append("<TD>");
                if (gameField.isLive(r, c))
                    sb.append('*');
                else
                    sb.append(' ');
                sb.append("</TD>");
            }
            sb.append("<TD>!</TD>");
            sb.append("</TR>");
        }
        sb.append("<TR>");
        for(c = 0; c < cols + 2; c++) sb.append("<TD>=</TD>");
        sb.append("</TR>");
        sb.append("</TABLE>");
        sb.append("</BODY>");
        sb.append("</HTML>");
        return sb.toString();
    }
}
