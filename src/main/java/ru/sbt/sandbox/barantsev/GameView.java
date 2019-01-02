package ru.sbt.sandbox.barantsev;
/**
 *
 * @author Alexander Barantsev
 *
 */
public class GameView {
    /**
     * Текущее игровое поле
     */
    private GameModel gameModel = null;

    /**
     * Инициализация игрового поля
     * @param gameModel Модель игры жизнь
     */
    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    /**
     * Выполнить генерацию следующего поколения
     * @return true, если поколение не изменилось, false, если изменилось
     */

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
        int rows = gameModel.getRows();
        int cols = gameModel.getColumns();
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
                if (gameModel.isLive(r, c))
                    sb.append("<img src=\"livecell.png\" alt=\"*\" border=0 height=32 width=32>");//sb.append('*');
                else
                    sb.append("<img src=\"emptycell.png\" alt=\" \" border=0 height=32 width=32>");//sb.append('*');//sb.append(' ');
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
